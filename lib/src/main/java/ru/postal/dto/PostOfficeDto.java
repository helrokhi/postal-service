package ru.postal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostOfficeDto {
    private Long id;
    private String index;
    private String title;
    private String address;
}
