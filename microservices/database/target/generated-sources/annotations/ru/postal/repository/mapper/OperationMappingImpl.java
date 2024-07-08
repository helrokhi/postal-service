package ru.postal.repository.mapper;

import javax.annotation.processing.Generated;
import jooq.db.tables.records.OperationRecord;
import org.springframework.stereotype.Component;
import ru.postal.dto.OperationDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-08T17:07:31+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class OperationMappingImpl implements OperationMapping {

    @Override
    public OperationDto operationRecordToDto(OperationRecord operationRecord) {
        if ( operationRecord == null ) {
            return null;
        }

        OperationDto.OperationDtoBuilder operationDto = OperationDto.builder();

        operationDto.id( operationRecord.getId() );
        operationDto.mailingId( operationRecord.getMailingId() );
        operationDto.inOfficeIndex( operationRecord.getInOfficeIndex() );
        operationDto.outOfficeIndex( operationRecord.getOutOfficeIndex() );
        operationDto.operationTypeId( operationRecord.getOperationTypeId() );
        operationDto.operationDate( operationRecord.getOperationDate() );

        return operationDto.build();
    }

    @Override
    public OperationRecord operationDtoToRecord(OperationDto operationDto) {
        if ( operationDto == null ) {
            return null;
        }

        OperationRecord operationRecord = new OperationRecord();

        operationRecord.setMailingId( operationDto.getMailingId() );
        operationRecord.setInOfficeIndex( operationDto.getInOfficeIndex() );
        operationRecord.setOutOfficeIndex( operationDto.getOutOfficeIndex() );
        operationRecord.setOperationTypeId( operationDto.getOperationTypeId() );
        operationRecord.setOperationDate( operationDto.getOperationDate() );

        return operationRecord;
    }
}
