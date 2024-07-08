package ru.postal.repository;

import jooq.db.Tables;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.postal.dto.*;
import ru.postal.repository.mapper.*;

@Repository
@AllArgsConstructor
public class PostOfficeRepositoryImpl implements PostOfficeRepository {
    private final DSLContext dsl;
    private final OfficeMapping mapper;

    @Override
    public PostOfficeDto getPostOfficeDtoByIndex(String index) {
        return dsl.selectFrom(Tables.OFFICE)
                .where(Tables.OFFICE.INDEX.eq(index))
                .fetchOptional()
                .map(mapper::officeRecordToDto)
                .orElse(null);
    }
}
