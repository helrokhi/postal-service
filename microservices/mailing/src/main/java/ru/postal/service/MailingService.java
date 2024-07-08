package ru.postal.service;

import org.springframework.http.ResponseEntity;
import ru.postal.dto.*;

import java.util.List;

public interface MailingService {
    ResponseEntity<MailingDto> createMailing(RegMailingDto regMailingDto);

    ResponseEntity<MailingDto> getMailing(Long id);

    ResponseEntity<List<OperationDto>> getHistory(Long id);

    ResponseEntity<String> getStatus(Long id);

    ResponseEntity<MailingDto> updateStatus(Long id, String status, String office);
}
