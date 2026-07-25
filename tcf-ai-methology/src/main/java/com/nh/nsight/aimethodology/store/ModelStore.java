package com.nh.nsight.aimethodology.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nh.nsight.aimethodology.config.ModelStudioProperties;
import com.nh.nsight.aimethodology.model.BusinessModel;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 업무모델 저장소. H2(또는 설정 DB)에 JSON payload로 보관한다.
 * 최초 기동 시 DB가 비어 있으면 models.json(레거시) 또는 seed를 이관한다.
 */
@Service
public class ModelStore {

    private static final Logger log = LoggerFactory.getLogger(ModelStore.class);

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;
    private final ModelStudioProperties properties;
    private final BusinessModelRepository repository;

    public ModelStore(
            ObjectMapper objectMapper,
            ResourceLoader resourceLoader,
            ModelStudioProperties properties,
            BusinessModelRepository repository) {
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
        this.properties = properties;
        this.repository = repository;
    }

    @PostConstruct
    void init() throws IOException {
        if (repository.count() > 0) {
            log.info("업무모델 DB 로드: {}건", repository.count());
            return;
        }
        List<BusinessModel> seed = migrateFromLegacyJson().orElseGet(this::readSeedSafe);
        for (BusinessModel model : seed) {
            if (!StringUtils.hasText(model.getId())) {
                model.setId(UUID.randomUUID().toString());
            }
            repository.save(toEntity(model));
        }
        log.info("업무모델 DB 초기화: {}건 (seed/legacy)", seed.size());
    }

    @Transactional(readOnly = true)
    public List<BusinessModel> list() {
        List<BusinessModelEntity> rows = repository.findAllByOrderByUpdatedAtDesc();
        List<BusinessModel> models = new ArrayList<>(rows.size());
        for (BusinessModelEntity row : rows) {
            models.add(fromEntity(row));
        }
        return models;
    }

    @Transactional(readOnly = true)
    public List<BusinessModel> search(String query) {
        if (!StringUtils.hasText(query)) {
            return list();
        }
        String q = query.trim();
        List<BusinessModelEntity> rows =
                repository.findByServiceIdContainingIgnoreCaseOrServiceNameContainingIgnoreCaseOrDomainCodeContainingIgnoreCase(
                        q, q, q);
        List<BusinessModel> models = new ArrayList<>(rows.size());
        for (BusinessModelEntity row : rows) {
            models.add(fromEntity(row));
        }
        return models;
    }

    @Transactional(readOnly = true)
    public Optional<BusinessModel> get(String modelId) {
        return repository.findById(modelId).map(this::fromEntity);
    }

    @Transactional
    public BusinessModel save(BusinessModel model) {
        BusinessModel saved = copy(model);
        if (!StringUtils.hasText(saved.getId())) {
            saved.setId(UUID.randomUUID().toString());
        }
        repository.save(toEntity(saved));
        return copy(saved);
    }

    @Transactional
    public boolean delete(String modelId) {
        if (!repository.existsById(modelId)) {
            return false;
        }
        repository.deleteById(modelId);
        return true;
    }

    @Transactional
    public Optional<BusinessModel> duplicate(String modelId) {
        return get(modelId).map(model -> {
            model.setId(UUID.randomUUID().toString());
            model.setServiceId(nullToEmpty(model.getServiceId()) + ".copy");
            model.setTransactionCode("");
            model.setEventId("");
            model.setMethodName(nullToEmpty(model.getMethodName()) + "Copy");
            model.setAggregateName(nullToEmpty(model.getAggregateName()) + "Copy");
            return save(model);
        });
    }

    public BusinessModel loadSample() throws IOException {
        Resource resource = resourceLoader.getResource(properties.getSampleResource());
        try (InputStream in = resource.getInputStream()) {
            return objectMapper.readValue(in, BusinessModel.class);
        }
    }

    public long count() {
        return repository.count();
    }

    /**
     * classpath seed로 DB를 교체 적재한다. UI/운영에서 도메인 시드를 재반영할 때 사용.
     */
    @Transactional
    public List<BusinessModel> reseedFromClasspath() throws IOException {
        List<BusinessModel> seed = readSeed();
        repository.deleteAllInBatch();
        List<BusinessModel> saved = new ArrayList<>(seed.size());
        for (BusinessModel model : seed) {
            if (!StringUtils.hasText(model.getId())) {
                model.setId(UUID.randomUUID().toString());
            }
            repository.save(toEntity(model));
            saved.add(copy(model));
        }
        log.info("업무모델 DB reseed: {}건", saved.size());
        return saved;
    }

    private Optional<List<BusinessModel>> migrateFromLegacyJson() {
        Path dataFile = resolveLegacyDataFile();
        if (!Files.exists(dataFile)) {
            return Optional.empty();
        }
        try {
            byte[] bytes = Files.readAllBytes(dataFile);
            if (bytes.length == 0) {
                return Optional.empty();
            }
            List<BusinessModel> models = objectMapper.readValue(bytes, new TypeReference<>() {});
            log.info("레거시 JSON 이관: {} ({})", dataFile, models.size());
            return Optional.of(models);
        } catch (IOException ex) {
            log.warn("레거시 JSON 읽기 실패, seed 사용: {}", dataFile, ex);
            return Optional.empty();
        }
    }

    private Path resolveLegacyDataFile() {
        if (StringUtils.hasText(properties.getDataFile())) {
            return Path.of(properties.getDataFile()).toAbsolutePath().normalize();
        }
        return Path.of(System.getProperty("user.home"), "nsight-model-studio", "models.json")
                .toAbsolutePath()
                .normalize();
    }

    private List<BusinessModel> readSeedSafe() {
        try {
            return readSeed();
        } catch (IOException ex) {
            throw new IllegalStateException("seed 모델을 읽을 수 없습니다.", ex);
        }
    }

    private List<BusinessModel> readSeed() throws IOException {
        Resource seed = resourceLoader.getResource(properties.getSeedResource());
        if (seed.exists()) {
            try (InputStream in = seed.getInputStream()) {
                return objectMapper.readValue(in, new TypeReference<>() {});
            }
        }
        return List.of(loadSample());
    }

    private BusinessModelEntity toEntity(BusinessModel model) {
        BusinessModelEntity entity = new BusinessModelEntity();
        entity.setId(model.getId());
        entity.setProjectName(model.getProjectName());
        entity.setServiceId(model.getServiceId());
        entity.setServiceName(model.getServiceName());
        entity.setDomainCode(model.getDomainCode());
        entity.setAggregateName(model.getAggregateName());
        entity.setMethodName(model.getMethodName());
        entity.setOperation(model.getOperation());
        entity.setScreenId(model.getScreenId());
        try {
            entity.setPayload(objectMapper.writeValueAsString(model));
        } catch (IOException ex) {
            throw new IllegalStateException("모델 JSON 직렬화 실패: " + model.getId(), ex);
        }
        return entity;
    }

    private BusinessModel fromEntity(BusinessModelEntity entity) {
        try {
            BusinessModel model = objectMapper.readValue(entity.getPayload(), BusinessModel.class);
            if (!StringUtils.hasText(model.getId())) {
                model.setId(entity.getId());
            }
            return model;
        } catch (IOException ex) {
            throw new IllegalStateException("모델 JSON 역직렬화 실패: " + entity.getId(), ex);
        }
    }

    private BusinessModel copy(BusinessModel model) {
        return objectMapper.convertValue(model, BusinessModel.class);
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
