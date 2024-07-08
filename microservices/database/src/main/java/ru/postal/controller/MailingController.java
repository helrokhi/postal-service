package ru.postal.controller;

import jooq.db.tables.records.MailingRecord;
import jooq.db.tables.records.OperationRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.postal.dto.*;
import ru.postal.service.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mailing")
public class MailingController {
    private final MailingService mailingService;
    private final OperationService operationService;

    @PostMapping("/create")
    public ResponseEntity<MailingDto> createMailing(@RequestBody RegMailingDto regMailingDto) {
        log.info("create mailing: {}", regMailingDto);
        boolean isSuccessful = false;

        Optional<MailingRecord> mailingRecord = mailingService.createMailing(regMailingDto);
        if (mailingRecord.isPresent()) {
            Long mailingId = mailingRecord.get().getId();
            Long statusId = mailingRecord.get().getStatusId();
            String officeIndex = regMailingDto.getOfficeIndex();
            try {
                Optional<OperationRecord> operationRecord = operationService
                        .createOperation(mailingId, statusId, officeIndex, officeIndex);
                isSuccessful = operationRecord.isPresent();
            } catch (Exception exception) {
                log.debug(exception.getMessage());
            }

            if (!isSuccessful) {
                mailingService.deleteMailing(mailingId);
            }
        }

        return isSuccessful ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MailingDto> getMailing(@PathVariable Long id) {
        log.info("get mailing by id {}", id);
        try {
            MailingDto mailingDto = mailingService.getMailing(id);
            return ResponseEntity.ok(mailingDto);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<OperationDto>> getHistory(@PathVariable Long id) {
        log.info("get history by id {}", id);
        try {
            List<OperationDto> posts = operationService.getOperationMovementHistory(id);
            return ResponseEntity.ok(posts);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getStatus(@PathVariable Long id) {
        log.info("get status by id {}", id);
        try {
            String status = mailingService.getStatus(id);
            return ResponseEntity.ok(status);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<MailingDto> updateStatus(
            @PathVariable Long id,
            @RequestParam("status") String status,
            @RequestParam("office") String office) {
        Long statusId = Long.valueOf(status);
        log.info("update status by id {} new status {} in office {}", id, statusId, office);

        try {
            MailingDto mailingDto = mailingService.updateStatusMailing(id, statusId, office);
            return ResponseEntity.ok(mailingDto);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
