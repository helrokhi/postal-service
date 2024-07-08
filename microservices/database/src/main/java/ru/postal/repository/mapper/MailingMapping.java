package ru.postal.repository.mapper;

import jooq.db.tables.records.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.postal.dto.*;

@Mapper(componentModel = "spring")
public interface MailingMapping {
    MailingDto mailingRecordToDto(MailingRecord mailingRecord);

    @Mapping(target = "id", ignore = true)
    MailingRecord mailingDtoToRecord(MailingDto mailingDto);
}
