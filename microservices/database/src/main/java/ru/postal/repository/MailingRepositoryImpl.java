package ru.postal.repository;

import jooq.db.Tables;
import jooq.db.tables.records.*;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.postal.dto.*;
import ru.postal.repository.mapper.*;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class MailingRepositoryImpl implements MailingRepository {
    private final DSLContext dsl;
    private final MailingMapping mapper;

    private final DataSourceTransactionManager transactionManager;

    @Override
    public Optional<MailingRecord> insertMailing(MailingRecord mailingRecord) {
        return dsl
                .insertInto(Tables.MAILING)
                .set(mailingRecord)
                .returning()
                .fetchOptional();
    }

    @Override
    public boolean updateMailing(MailingRecord mailingRecord, OperationRecord operationRecord) {
        boolean isDone = false;
        TransactionStatus transactionStatus = transactionManager
                .getTransaction(new DefaultTransactionDefinition());
        try {
            dsl
                    .update(Tables.MAILING)
                    .set(dsl.newRecord(Tables.MAILING, mailingRecord))
                    .where(Tables.MAILING.ID.eq(mailingRecord.getId()))
                    .execute();

            dsl
                    .insertInto(Tables.OPERATION)
                    .set(operationRecord)
                    .execute();

            transactionManager.commit(transactionStatus);
            isDone = true;

        } catch (DataAccessException exception) {
            transactionManager.rollback(transactionStatus);
        }
        return isDone;
    }

    @Override
    public MailingDto getMailingDtoById(Long id) {
        return dsl.selectFrom(Tables.MAILING)
                .where(Tables.MAILING.ID.eq(id))
                .fetchOptional()
                .map(mapper::mailingRecordToDto)
                .orElse(null);
    }

    @Override
    public MailingRecord getMailingRecordById(Long id) {
        return dsl.selectFrom(Tables.MAILING)
                .where(Tables.MAILING.ID.eq(id))
                .fetchOptional()
                .orElse(null);
    }

    @Override
    public Long getStatusTypeIdByMailingId(Long id) {
        return dsl.selectFrom(Tables.MAILING)
                .where(Tables.MAILING.ID.eq(id))
                .fetchOptional()
                .map(MailingRecord::getStatusId)
                .orElse(null);
    }

    @Override
    public int deleteMailing(Long id) {
        return dsl
                .deleteFrom(Tables.MAILING)
                .where(Tables.MAILING.ID.eq(id))
                .execute();
    }
}
