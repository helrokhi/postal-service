package ru.postal.repository.mapper;

import jooq.db.tables.records.*;
import org.mapstruct.Mapper;
import ru.postal.dto.*;

@Mapper(componentModel = "spring")
public interface OfficeMapping {
    PostOfficeDto officeRecordToDto(OfficeRecord officeRecord);

    OfficeRecord postOfficeDtoToRecord(PostOfficeDto postOfficeDto);
}
