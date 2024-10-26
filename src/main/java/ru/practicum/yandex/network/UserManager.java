package ru.practicum.yandex.network;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practicum.yandex.Constants.Constants;
import io.restassured.specification.RequestSpecification;
import ru.practicum.yandex.user.User;

public class UserManager extends NetworkManager {

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return request()
                .body(user)
                .when()
                .post(Constants.USER_REGISTER_URL)
                .then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(User user) {
        return request()
                .body(user)
                .when()
                .post(Constants.USER_LOGIN_URL)
                .then();
    }

    @Step("Изменение пользователя")
    public ValidatableResponse changeUserData(User user, String accessToken) {
        RequestSpecification requestSpec = request();

        if (accessToken != null && accessToken.length() > 0) {
            requestSpec.header("Authorization", accessToken);
        }

        ValidatableResponse response = requestSpec
                .body(user)
                .when()
                .patch(Constants.USER_URL) // Предполагается, что для изменения данных используется PATCH
                .then();

        return response;
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        ValidatableResponse response = request()
                .header("Authorization", accessToken)
                .when()
                .delete(Constants.USER_URL) // Предполагается, что для изменения данных используется PATCH
                .then();
        return response;
    }
}