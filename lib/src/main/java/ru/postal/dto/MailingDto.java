package ru.postal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailingDto {
    private Long id;
    private String beneficiaryName;
    private Long mailingTypeId;
    private String beneficiaryOfficeIndex;
    private String beneficiaryAddress;
    private Long statusId;
    private String officeIndex;
}
