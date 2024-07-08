package ru.postal.repository;

import ru.postal.dto.PostOfficeDto;

public interface PostOfficeRepository {

    PostOfficeDto getPostOfficeDtoByIndex(String index);
}
