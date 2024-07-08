package ru.postal.repository.mapper;

import javax.annotation.processing.Generated;
import jooq.db.tables.records.MailingRecord;
import org.springframework.stereotype.Component;
import ru.postal.dto.MailingDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-08T17:07:31+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class MailingMappingImpl implements MailingMapping {

    @Override
    public MailingDto mailingRecordToDto(MailingRecord mailingRecord) {
        if ( mailingRecord == null ) {
            return null;
        }

        MailingDto.MailingDtoBuilder mailingDto = MailingDto.builder();

        mailingDto.id( mailingRecord.getId() );
        mailingDto.beneficiaryName( mailingRecord.getBeneficiaryName() );
        mailingDto.mailingTypeId( mailingRecord.getMailingTypeId() );
        mailingDto.beneficiaryOfficeIndex( mailingRecord.getBeneficiaryOfficeIndex() );
        mailingDto.beneficiaryAddress( mailingRecord.getBeneficiaryAddress() );
        mailingDto.statusId( mailingRecord.getStatusId() );
        mailingDto.officeIndex( mailingRecord.getOfficeIndex() );

        return mailingDto.build();
    }

    @Override
    public MailingRecord mailingDtoToRecord(MailingDto mailingDto) {
        if ( mailingDto == null ) {
            return null;
        }

        MailingRecord mailingRecord = new MailingRecord();

        mailingRecord.setBeneficiaryName( mailingDto.getBeneficiaryName() );
        mailingRecord.setMailingTypeId( mailingDto.getMailingTypeId() );
        mailingRecord.setBeneficiaryOfficeIndex( mailingDto.getBeneficiaryOfficeIndex() );
        mailingRecord.setBeneficiaryAddress( mailingDto.getBeneficiaryAddress() );
        mailingRecord.setStatusId( mailingDto.getStatusId() );
        mailingRecord.setOfficeIndex( mailingDto.getOfficeIndex() );

        return mailingRecord;
    }
}
