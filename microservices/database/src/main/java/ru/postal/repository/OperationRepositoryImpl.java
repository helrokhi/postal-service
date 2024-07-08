package ru.postal.repository;

import jooq.db.Tables;
import jooq.db.tables.records.OperationRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.postal.dto.*;
import ru.postal.repository.mapper.*;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OperationRepositoryImpl implements OperationRepository{
    private final DSLContext dsl;
    private final OperationMapping mapper;

    @Override
    public List<Long> getAllOperationIdByMailingId(Long mailingId) {
        return dsl.selectFrom(Tables.OPERATION)
                .where(Tables.OPERATION.MAILING_ID.eq(mailingId))
                .fetch()
                .map(OperationRecord::getId);
    }

    @Override
    public OperationDto getOperationDtoById(Long postalId) {
        return dsl.selectFrom(Tables.OPERATION)
                .where(Tables.OPERATION.ID.eq(postalId))
                .fetchOptional()
                .map(mapper::operationRecordToDto)
                .orElse(null);
    }

    @Override
    public Optional<OperationRecord> insertOperation(OperationRecord operationRecord) {
        return dsl
                .insertInto(Tables.OPERATION)
                .set(operationRecord)
                .returning()
                .fetchOptional();
    }
}
