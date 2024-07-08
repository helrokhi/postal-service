package ru.postal.dto.enums;

import lombok.Getter;

@Getter
public enum MailingType {
    LETTER(1L, "LETTER", "Письмо"),
    PARCEL(2L, "PARCEL", "Посылка"),
    PARCEL_POST(3L, "PARCEL_POST", "Бандероль"),
    CARD(4L, "CARD", "Открытка");

    private final Long id;
    private final String code;
    private final String name;

    MailingType(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
