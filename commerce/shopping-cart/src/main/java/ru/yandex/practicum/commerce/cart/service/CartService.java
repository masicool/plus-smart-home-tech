package ru.yandex.practicum.commerce.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.cart.feign.WarehouseClient;
import ru.yandex.practicum.commerce.cart.model.Cart;
import ru.yandex.practicum.commerce.cart.repository.CartRepository;
import ru.yandex.practicum.commerce.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.commerce.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.commerce.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.commerce.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.exception.ShoppingCartDeactivationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final WarehouseClient warehouseClient;

    @Transactional(readOnly = true)
    public ShoppingCartDto getShoppingCart(String username) {
        log.info("Getting shopping cart for user: {}", username);
        checkUser(username);
        Cart cart = getCartByUsername(username);
        ShoppingCartDto shoppingCartDto = modelMapper.map(cart, ShoppingCartDto.class);
        log.info("Shopping cart for user: {} has been received", username);
        return shoppingCartDto;
    }

    @Transactional
    public ShoppingCartDto addProductToShoppingCart(String username, Map<UUID, Long> products) {
        log.info("Adding product to shopping cart for user: {}", username);
        checkUser(username);
        if (products.isEmpty()) {
            return null;
        }
        Cart cart = cartRepository.findCartByUsername(username)
                .orElse(Cart.builder()
                        .username(username)
                        .products(new HashMap<>())
                        .isActive(true)
                        .build());

        log.info("Checking deactivate status for shopping cart {}", cart);
        // проверка на деактивацию корзины
        if (!cart.isActive()) {
            throw new ShoppingCartDeactivationException("Shopping cart is deactivated");
        }

        // добавляем товары в корзину
        cart.getProducts().putAll(products);
        // проверим кол-во товаров на складе из корзины
        // если не было исключения, значит кол-во товаров на складе достаточное
        try {
            log.info("Checking product quantity for shopping cart {}", cart);
            warehouseClient.checkProductQuantityEnoughForShoppingCart(modelMapper.map(cart, ShoppingCartDto.class));
            log.info("Product quantity for shopping cart {} has been checked", cart);
            ShoppingCartDto cartDto = modelMapper.map(cartRepository.save(cart), ShoppingCartDto.class);
            log.info("Products has been added to shopping cart {}", cartDto);
            return cartDto;
        } catch (Exception e) {
            throw new ProductInShoppingCartLowQuantityInWarehouse("Shopping cart has not passed the stock check");
        }
    }

    @Transactional
    public void deactivateCurrentShoppingCart(String username) {
        log.info("Deactivating shopping cart for user: {}", username);
        checkUser(username);
        Cart cart = getCartByUsername(username);
        cart.setActive(false);
        cartRepository.save(cart);
        log.info("Shopping cart for user: {} has been deactivated", username);
    }

    @Transactional
    public ShoppingCartDto removeFromShoppingCart(String username, Set<UUID> products) {
        log.info("Removing product from shopping cart for user: {}", username);
        checkUser(username);
        Cart cart = getCartByUsername(username);
        for (UUID productId : products) {
            if (!cart.getProducts().containsKey(productId)) {
                throw new NoProductsInShoppingCartException("No product with ID = " + productId + " in shopping cart");
            }
            cart.getProducts().remove(productId);
        }
        ShoppingCartDto shoppingCartDto = modelMapper.map(cartRepository.save(cart), ShoppingCartDto.class);
        log.info("Products from shopping cart {} has been removed", cart);
        return shoppingCartDto;
    }

    @Transactional
    public ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request) {
        log.info("Changing shopping cart product quantity for user: {}", username);
        checkUser(username);
        Cart cart = getCartByUsername(username);
        Map<UUID, Long> currProducts = cart.getProducts();
        if (!currProducts.containsKey(request.getProductId())) {
            throw new NoProductsInShoppingCartException("No product with ID = " + request.getProductId() + " in shopping cart");
        }
        currProducts.put(request.getProductId(), request.getNewQuantity());
        ShoppingCartDto shoppingCartDto = modelMapper.map(cartRepository.save(cart), ShoppingCartDto.class);
        log.info("Products from shopping cart {} has been changed", cart);
        return shoppingCartDto;
    }

    private void checkUser(String username) {
        if (username.isBlank()) {
            throw new NotAuthorizedUserException("Username is blank");
        }
    }

    private Cart getCartByUsername(String username) {
        return cartRepository.findCartByUsername(username)
                .orElseThrow(() -> new NoProductsInShoppingCartException("No cart found for username = " + username));
    }
}
