package ru.postal.dto.enums;

import lombok.Getter;

@Getter
public enum OperationType {
    REGISTRATION(1L, "REGISTRATION", "Регистрация почтового отправления"),
    ARRIVAL(2L, "ARRIVAL", "Прибытие в промежуточное почтовое отделения"),
    DEPARTURE(3L, "DEPARTURE", "Убытие из почтового отделения"),
    RECEIPT(4L, "RECEIPT", "Получение адресатом");
    private final Long id;
    private final String code;
    private final String name;

    OperationType(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
