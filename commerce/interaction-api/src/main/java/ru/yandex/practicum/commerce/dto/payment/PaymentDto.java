package ru.yandex.practicum.commerce.dto.payment;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Информация об оплате
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDto {
    UUID paymentId; // идентификатор оплаты

    float totalPayment; // общая стоимость

    float deliveryTotal; // стоимость доставки

    float feeTotal; // стоимость налога
}
