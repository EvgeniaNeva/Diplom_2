package ru.practicum.yandex;

import io.qameta.allure.Step;
import org.jetbrains.annotations.Contract;
import ru.practicum.yandex.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

public class ObjectGenerator {

    @Step("Генерирование пользователя")
    public static @NotNull User generateUser() {
        final User user = new User();
        user.withParams(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com",
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10));
        return user;
    }

    @Step("Генерирование пользователя без email")
    public static @NotNull User generateUserWithOutEmail() {
        final User user = new User();
        user.withOutEmail(RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10));
        return user;
    }

    @Step("Генерирование пользователя без пароля")
    public static @NotNull User generateUserWithOutPassword() {
        final User user = new User();
        user.withOutPassword(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com",
                RandomStringUtils.randomAlphanumeric(10));
        return user;
    }

    @Step("Генерирование пользователя без имени")
    public static @NotNull User generateUserWithOutName() {
        final User user = new User();
        user.withOutName(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com",
                RandomStringUtils.randomAlphanumeric(10));
        return user;
    }

    public static @NotNull User generateUserWithOutParams() {
        return new User();
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull String @NotNull [] generateIngrients() {
        return new String[]{"61c0c5a71d1f82001bdaaa7a"};
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull String @NotNull [] generateEmptyIngrients() {
        return new String[]{};
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull String @NotNull [] generateWrongIngrients() {
        return new String[]{"wrong"};
    }
}