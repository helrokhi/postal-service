package ru.postal.repository.mapper;

import jooq.db.tables.records.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.postal.dto.*;

@Mapper(componentModel = "spring")
public interface OperationMapping {
    OperationDto operationRecordToDto(OperationRecord operationRecord);

    @Mapping(target = "id", ignore = true)
    OperationRecord operationDtoToRecord(OperationDto operationDto);
}
