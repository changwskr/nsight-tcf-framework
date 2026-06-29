package com.nh.nsight.marketing.eb.application.service;

import com.nh.nsight.marketing.eb.client.EpOnlineClient;
import com.nh.nsight.marketing.eb.config.EbEventPublishProperties;
import com.nh.nsight.marketing.eb.persistence.dao.EbEventDao;
import com.nh.nsight.marketing.eb.support.EbEventStatus;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EbEventPublishService {
    private static final Logger log = LoggerFactory.getLogger(EbEventPublishService.class);

    private final EbEventPublishProperties properties;
    private final EbEventDao eventDao;
    private final EpOnlineClient epClient;

    public EbEventPublishService(EbEventPublishProperties properties,
                                 EbEventDao eventDao,
                                 EpOnlineClient epClient) {
        this.properties = properties;
        this.eventDao = eventDao;
        this.epClient = epClient;
    }

    public void publishReadyEvents() {
        System.out.println("---------- [EB-BATCH] START publishReadyEvents (EbEventPublishService.publishReadyEvents) ----------");
        try {
            if (!properties.isEnabled()) {
                System.out.println("[EB-BATCH] SKIP enabled=false (EbEventPublishService.publishReadyEvents) 배치 비활성");
                return;
            }
            List<Map<String, Object>> events = eventDao.selectReadyEvents(properties.getBatchSize());
            if (events.isEmpty()) {
                System.out.println("[EB-BATCH] SKIP READY 이벤트 없음 (EbEventPublishService.publishReadyEvents)");
                return;
            }
            log.info("EB event publish batch start count={}", events.size());
            System.out.println("[EB-BATCH] READY 이벤트 " + events.size()
                    + "건 EP 전송 시작 (EbEventPublishService.publishReadyEvents)");
            for (Map<String, Object> event : events) {
                publishOne(event);
            }
            System.out.println("[EB-BATCH] READY 이벤트 " + events.size()
                    + "건 처리 완료 (EbEventPublishService.publishReadyEvents)");
        } finally {
            System.out.println("---------- [EB-BATCH] END publishReadyEvents (EbEventPublishService.publishReadyEvents) ----------");
        }
    }

    private void publishOne(Map<String, Object> event) {
        String eventId = stringValue(event, "EVENT_ID", "eventId");
        String userId = stringValue(event, "USER_ID", "userId");
        System.out.println("  >> [EB-EVENT] START publishOne (EbEventPublishService.publishOne) eventId="
                + eventId + " userId=" + userId);
        String status = EbEventStatus.FAIL;
        try {
            boolean success = epClient.sendUserEvent(properties.getEpOnlineUrl(), event);
            status = success ? EbEventStatus.SENT : EbEventStatus.FAIL;
            eventDao.updateEventStatus(eventId, status);
            log.info("EB event publish eventId={} status={}", eventId, status);
        } catch (Exception e) {
            eventDao.updateEventStatus(eventId, EbEventStatus.FAIL);
            log.warn("EB event publish failed eventId={} message={}", eventId, e.getMessage());
            System.out.println("  >> [EB-EVENT] ERROR publishOne (EbEventPublishService.publishOne) eventId="
                    + eventId + " message=" + e.getMessage());
        } finally {
            System.out.println("  >> [EB-EVENT] END publishOne (EbEventPublishService.publishOne) eventId="
                    + eventId + " status=" + status);
        }
    }

    private String stringValue(Map<String, Object> row, String upperKey, String camelKey) {
        Object value = row.get(upperKey);
        if (value == null) {
            value = row.get(camelKey);
        }
        return value == null ? "" : String.valueOf(value);
    }
}
