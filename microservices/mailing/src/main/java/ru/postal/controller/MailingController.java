package ru.postal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.postal.dto.*;
import ru.postal.service.MailingService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mailing")
public class MailingController {
    private final MailingService mailingService;

    @PostMapping("/create")
    public ResponseEntity<MailingDto> createMailing(@RequestBody RegMailingDto regMailingDto) {
        return mailingService.createMailing(regMailingDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MailingDto> getMailing(@PathVariable Long id) {
        return mailingService.getMailing(id);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<OperationDto>> getHistory(@PathVariable Long id) {
        return mailingService.getHistory(id);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getStatus(@PathVariable Long id) {
        return mailingService.getStatus(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<MailingDto> updateStatus(
            @PathVariable Long id,
            @RequestParam("status") String status,
            @RequestParam("office") String office) {
        return mailingService.updateStatus(id, status, office);
    }
}
