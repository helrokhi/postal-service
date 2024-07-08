package ru.postal.repository;

import jooq.db.tables.records.*;
import ru.postal.dto.*;

import java.util.Optional;

public interface MailingRepository {
    Optional<MailingRecord> insertMailing(MailingRecord mailingRecord);

    boolean updateMailing(MailingRecord mailingRecord, OperationRecord operationRecord);

    MailingDto getMailingDtoById(Long id);

    MailingRecord getMailingRecordById(Long id);

    Long getStatusTypeIdByMailingId(Long id);

    int deleteMailing(Long id);
}
