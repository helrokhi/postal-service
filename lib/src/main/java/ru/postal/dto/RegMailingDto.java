package ru.postal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegMailingDto {
    private String beneficiaryName;
    private String type;
    private String beneficiaryIndex;
    private String beneficiaryAddress;
    private String officeIndex;
}
