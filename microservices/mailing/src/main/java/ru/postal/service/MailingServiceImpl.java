package ru.postal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.postal.client.DatabaseClient;
import ru.postal.dto.*;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {
    private final DatabaseClient databaseClient;

    public ResponseEntity<MailingDto> createMailing(RegMailingDto regMailingDto) {
        log.info("create mailing: {}", regMailingDto);
        return databaseClient.createMailing(regMailingDto);
    }

    public ResponseEntity<MailingDto> getMailing(Long id) {
        log.info("get mailing by id {}", id);
        return databaseClient.getMailing(id);
    }

    public ResponseEntity<List<OperationDto>> getHistory(Long id) {
        log.info("get history by id {}", id);
        return databaseClient.getHistory(id);
    }

    public ResponseEntity<String> getStatus(Long id) {
        log.info("get status by id {}", id);
        return databaseClient.getStatus(id);
    }

    public ResponseEntity<MailingDto> updateStatus(Long id, String status, String office) {
        Long statusId = Long.valueOf(status);
        log.info("update status by id {} new status {} in office {}", id, statusId, office);
        return databaseClient.updateStatus(id, status, office);
    }
}
