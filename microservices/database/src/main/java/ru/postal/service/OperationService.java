package ru.postal.service;

import jooq.db.tables.records.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.postal.dto.*;
import ru.postal.repository.*;
import ru.postal.repository.mapper.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperationService {
    private final OperationRepository operationRepository;
    private final OperationMapping operationMapper;

    public List<OperationDto> getOperationMovementHistory(Long mailingId) {
        List<Long> postalIds = operationRepository.getAllOperationIdByMailingId(mailingId);
        return postalIds.stream()
                .map(operationRepository::getOperationDtoById)
                .collect(Collectors.toList());
    }

    public Optional<OperationRecord> createOperation(Long mailingId,
                                                     Long statusId,
                                                     String inOfficeIndex,
                                                     String outOfficeIndex) {
        OperationDto operationDto = setOperationDto(mailingId, statusId, inOfficeIndex, outOfficeIndex);
        OperationRecord operationRecord = operationMapper.operationDtoToRecord(operationDto);
        return operationRepository.insertOperation(operationRecord);
    }

    private OperationDto setOperationDto(Long mailingId,
                                         Long statusId,
                                         String inOfficeIndex,
                                         String outOfficeIndex) {
        return OperationDto.builder()
                .mailingId(mailingId)
                .inOfficeIndex(inOfficeIndex)
                .outOfficeIndex(outOfficeIndex)
                .operationTypeId(statusId)
                .operationDate(OffsetDateTime.now())
                .build();
    }
}
