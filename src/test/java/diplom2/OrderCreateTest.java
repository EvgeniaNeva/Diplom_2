package diplom2;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import ru.practicum.yandex.user.User;
import ru.practicum.yandex.user.UserCreateResponse;
import ru.practicum.yandex.orders.OrdersResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.yandex.ObjectGenerator;
import ru.practicum.yandex.network.OrderManager;
import ru.practicum.yandex.network.UserManager;

import static org.hamcrest.CoreMatchers.equalTo;

public class OrderCreateTest {

    private UserManager userManager;
    private OrderManager orderManager;
    private String accessToken;
    private User user;

    @Before
    public void setUp() {
        userManager = new UserManager();
        orderManager = new OrderManager();
        user = ObjectGenerator.generateUser();
    }

    @Step("Авторизация бользователя")
    private String loginUser (User user) {
        return userManager.loginUser(user)
                .extract()
                .body()
                .as(UserCreateResponse.class)
                .getAccessToken();
    }

    @Test
    @DisplayName("Создание заказа без авторизации с ингредиентами")
    public void testCreateOrderWithoutAuthorization() {
        orderManager.createOrder(ObjectGenerator.generateIngrients(), null)
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .extract().body().as(OrdersResponse.class);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией с ингредиентами")
    public void testCreateOrderWithAuthorization() {
        userManager.createUser(user);
        accessToken = loginUser (user);
        orderManager.createOrder(ObjectGenerator.generateIngrients(), accessToken)
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void testCreateOrderWithoutIngredients() {
        userManager.createUser(user);
        accessToken = loginUser (user);
        orderManager.createOrder(ObjectGenerator.generateEmptyIngrients(), accessToken)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"))
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void testCreateOrderWithIngredientsBadHash() {
        userManager.createUser(user);
        accessToken = loginUser (user);
        orderManager.createOrder(ObjectGenerator.generateWrongIngrients(), accessToken)
                .assertThat()
                .statusCode(500);
    }

    @After
    public void tearDown() {
        if (accessToken != null && !accessToken.isEmpty()) {
            userManager.deleteUser(accessToken);
        }
    }
}
