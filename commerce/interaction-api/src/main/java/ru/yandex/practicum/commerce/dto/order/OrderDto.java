package ru.yandex.practicum.commerce.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

/**
 * Представление заказа в системе.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {
    @NotNull
    UUID orderId; // идентификатор заказа

    UUID shoppingCartId; // идентификатор корзины

    @NotNull
    Map<UUID, Long> products; // отображение идентификатора товара на отобранное количество

    UUID paymentId; // идентификатор оплаты

    UUID deliveryId; // идентификатор доставки

    OrderState state; // статус заказа

    double deliveryWeight; // общий вес доставки

    double deliveryVolume; // общий объем доставки

    boolean fragile; // признак хрупкости заказа

    float totalPrice; // общая стоимость заказа

    float deliveryPrice; // стоимость доставки

    float productPrice; // стоимость товаров в заказе
}
