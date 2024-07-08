package ru.postal.repository.mapper;

import javax.annotation.processing.Generated;
import jooq.db.tables.records.OfficeRecord;
import org.springframework.stereotype.Component;
import ru.postal.dto.PostOfficeDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-08T17:07:31+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class OfficeMappingImpl implements OfficeMapping {

    @Override
    public PostOfficeDto officeRecordToDto(OfficeRecord officeRecord) {
        if ( officeRecord == null ) {
            return null;
        }

        PostOfficeDto.PostOfficeDtoBuilder postOfficeDto = PostOfficeDto.builder();

        postOfficeDto.id( officeRecord.getId() );
        postOfficeDto.index( officeRecord.getIndex() );
        postOfficeDto.title( officeRecord.getTitle() );
        postOfficeDto.address( officeRecord.getAddress() );

        return postOfficeDto.build();
    }

    @Override
    public OfficeRecord postOfficeDtoToRecord(PostOfficeDto postOfficeDto) {
        if ( postOfficeDto == null ) {
            return null;
        }

        OfficeRecord officeRecord = new OfficeRecord();

        officeRecord.setId( postOfficeDto.getId() );
        officeRecord.setIndex( postOfficeDto.getIndex() );
        officeRecord.setTitle( postOfficeDto.getTitle() );
        officeRecord.setAddress( postOfficeDto.getAddress() );

        return officeRecord;
    }
}
