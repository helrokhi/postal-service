package ru.postal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationDto {
    private Long id;
    private Long mailingId;
    private String inOfficeIndex;
    private String outOfficeIndex;
    private Long operationTypeId;
    private OffsetDateTime operationDate;
}
