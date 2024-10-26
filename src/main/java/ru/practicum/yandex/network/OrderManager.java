package ru.practicum.yandex.network;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import ru.practicum.yandex.Constants.Constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OrderManager extends NetworkManager {
    @Step("Создание заказа")
    public ValidatableResponse createOrder(String[] ingredients, String accessToken) {
        RequestSpecification requestSpec = request();

        if (ingredients != null && ingredients.length > 0) {
            Map<String, Object> body = new HashMap<>();
            body.put("ingredients", Arrays.asList(ingredients)); // Преобразуем массив в список
            requestSpec.body(body);
        }

        if (accessToken != null && accessToken.length() > 0) {
            requestSpec.header("Authorization", accessToken);
        }

        ValidatableResponse response = requestSpec.when()
                .post(Constants.ORDER_URL)
                .then();

        return response;
    }

    @Step("Получение заказов")
    public ValidatableResponse getOrders(String accessToken) {
        RequestSpecification requestSpec = request();

        if (accessToken != null && accessToken.length() > 0) {
            requestSpec.header("Authorization", accessToken);
        }

        ValidatableResponse response = requestSpec.when()
                .get(Constants.ORDER_URL)
                .then();

        return response;
    }
}
