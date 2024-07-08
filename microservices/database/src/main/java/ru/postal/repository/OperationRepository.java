package ru.postal.repository;

import jooq.db.tables.records.*;
import ru.postal.dto.*;

import java.util.List;
import java.util.Optional;

public interface OperationRepository {
    List<Long> getAllOperationIdByMailingId(Long mailingId);

    OperationDto getOperationDtoById(Long postalId);

    Optional<OperationRecord> insertOperation(OperationRecord operationRecord);
}
