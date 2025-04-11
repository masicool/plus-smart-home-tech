package ru.yandex.practicum.commerce.dto.store;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Для пагинации и запросов
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pageable {
    @Min(value = 0, message = "Page should be 0 or more")
    int page;

    @Min(value = 1, message = "Size must be greater than zero")
    int size;

    String[] sort;
}
