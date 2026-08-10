package nhnis.ontology.loader;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import nhnis.ontology.ontology.OntologyRegistry;
import nhnis.ontology.store.OntologyStore;

/**
 * Bootstraps OntologyStore from curated YAML mappings (W7).
 * Skips _generated (already filtered by OntologyRegistry).
 */
@Component
@Order(1)
public class OntologyGraphBootstrap implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(OntologyGraphBootstrap.class);

    private final OntologyRegistry registry;
    private final YamlGraphLoader loader;
    private final TxRuntimeGraphLoader txRuntimeLoader;
    private final OntologyStore store;

    public OntologyGraphBootstrap(
            OntologyRegistry registry,
            YamlGraphLoader loader,
            TxRuntimeGraphLoader txRuntimeLoader,
            OntologyStore store) {
        this.registry = registry;
        this.loader = loader;
        this.txRuntimeLoader = txRuntimeLoader;
        this.store = store;
    }

    @Override
    public void run(ApplicationArguments args) {
        loadAll();
    }

    public int loadAll() {
        int programs = 0;
        int services = 0;
        for (Map<String, Object> doc : registry.listPrograms()) {
            String programId = String.valueOf(doc.get("programId"));
            String sourcePath = "ontology/mappings/" + programId + ".yml";
            services += loader.loadProgramMapping(doc, sourcePath);
            programs++;
        }
        int runtimeSteps = txRuntimeLoader.load(registry.runtimeBundle());
        log.info("YAML→Graph loaded: programs={}, services={}, runtimeSteps={}, concepts={}, relations={}",
                programs, services, runtimeSteps, store.allConcepts().size(), store.allRelations().size());
        return programs;
    }
}
