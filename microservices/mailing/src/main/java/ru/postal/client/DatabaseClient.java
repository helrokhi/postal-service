package ru.postal.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.postal.dto.*;

import java.util.List;

@FeignClient(name = "databaseClient", dismiss404 = true, url = "${database.url}" + "/mailing")
public interface DatabaseClient {
    @PostMapping("/create")
    ResponseEntity<MailingDto> createMailing(@RequestBody RegMailingDto regMailingDto);

    @GetMapping("/{id}")
    ResponseEntity<MailingDto> getMailing(@PathVariable Long id);

    @GetMapping("/{id}/history")
    ResponseEntity<List<OperationDto>> getHistory(@PathVariable Long id);

    @GetMapping("/{id}/status")
    ResponseEntity<String> getStatus(@PathVariable Long id);

    @PostMapping("/{id}")
    ResponseEntity<MailingDto> updateStatus(
            @PathVariable Long id,
            @RequestParam("status") String status,
            @RequestParam("office") String office);
}
