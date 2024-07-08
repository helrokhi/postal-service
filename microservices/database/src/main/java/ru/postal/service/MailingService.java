package ru.postal.service;

import jooq.db.tables.records.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.postal.dto.*;
import ru.postal.dto.enums.*;
import ru.postal.repository.*;
import ru.postal.repository.mapper.*;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailingService {
    private final MailingRepository mailingRepository;
    private final MailingMapping mailingMapper;
    private final OperationMapping operationMapper;

    public Optional<MailingRecord> createMailing(RegMailingDto regMailingDto){
        log.info("create mailing: {}", regMailingDto);
        MailingDto mailingDto = setMailingDto(regMailingDto);
        MailingRecord mailingRecord = mailingMapper.mailingDtoToRecord(mailingDto);
        return mailingRepository.insertMailing(mailingRecord);
    }

    public MailingDto updateStatusMailing(Long mailingId, Long statusId, String outOfficeIndex) {
        log.info("update status mailing: {}, status {}", mailingId, statusId);

        MailingRecord mailingRecord = mailingRepository.getMailingRecordById(mailingId);
        String inOfficeIndex = mailingRecord.getOfficeIndex();
        mailingRecord.setStatusId(statusId);
        mailingRecord.setOfficeIndex(outOfficeIndex);

        OperationRecord operationRecord = setOperationRecord(
                mailingId,
                statusId,
                inOfficeIndex,
                outOfficeIndex);

        boolean isDone = mailingRepository.updateMailing(mailingRecord, operationRecord);
        return isDone? mailingRepository.getMailingDtoById(mailingId) : null;
    }

    public MailingDto getMailing(Long id) {
        log.info("get mailing by id {}", id);
        return mailingRepository.getMailingDtoById(id);
    }

    public String getStatus(Long id) {
        log.info("get status by id {}", id);
        Long statusId = mailingRepository.getStatusTypeIdByMailingId(id);

        return Objects.requireNonNull(Arrays.stream(OperationType.values())
                .filter(t -> t.getId().equals(statusId))
                .findFirst().orElse(null)).getName();
    }

    public int deleteMailing(Long id) {
        int result = mailingRepository.deleteMailing(id);
        log.info("delete mailing by id: {}", result);
        return result;
    }

    private MailingDto setMailingDto(RegMailingDto regMailingDto) {
        String beneficiaryName = regMailingDto.getBeneficiaryName();
        Long typeId = Long.valueOf(regMailingDto.getType());
        String beneficiaryIndex = regMailingDto.getBeneficiaryIndex();
        String beneficiaryAddress = regMailingDto.getBeneficiaryIndex();
        Long statusId = OperationType.REGISTRATION.getId();
        String officeIndex = regMailingDto.getOfficeIndex();

        log.info("set mailing dto {} {} {} {} {}", beneficiaryName, typeId, beneficiaryIndex, beneficiaryAddress, statusId);
        return MailingDto.builder()
                .beneficiaryName(beneficiaryName)
                .mailingTypeId(typeId)
                .beneficiaryOfficeIndex(beneficiaryIndex)
                .beneficiaryAddress(beneficiaryAddress)
                .statusId(statusId)
                .officeIndex(officeIndex)
                .build();
    }

    private OperationRecord setOperationRecord(Long mailingId,
                                               Long statusId,
                                               String inOfficeIndex,
                                               String outOfficeIndex) {
        return operationMapper.operationDtoToRecord(
                OperationDto.builder()
                        .mailingId(mailingId)
                        .inOfficeIndex(inOfficeIndex)
                        .outOfficeIndex(outOfficeIndex)
                        .operationTypeId(statusId)
                        .operationDate(OffsetDateTime.now())
                        .build());
    }
}
