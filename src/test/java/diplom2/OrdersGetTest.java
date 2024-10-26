package diplom2;

import io.qameta.allure.junit4.DisplayName;
import ru.practicum.yandex.ObjectGenerator;
import ru.practicum.yandex.network.OrderManager;
import ru.practicum.yandex.user.User;
import ru.practicum.yandex.network.UserManager;
import ru.practicum.yandex.orders.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class OrdersGetTest {
    private static final String EXPECTED_MESSAGE = "You should be authorised";
    private UserManager userManager;
    private OrderManager orderManager;
    private String accessToken;
    private User user;
    private Order order;

    @Before
    public void setUp() {
        userManager = new UserManager();
        orderManager = new OrderManager();
        user = ObjectGenerator.generateUser();
    }

    @Test
    @DisplayName("Получение заказов без авторизации")
    public void testGetOrdersWithoutAuthorization() {
        orderManager.createOrder(ObjectGenerator.generateIngrients(), null)
                .assertThat()
                .statusCode(200).and()
                .body("success", equalTo(true));
        orderManager.getOrders(null)
                .assertThat()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .body("message", equalTo(EXPECTED_MESSAGE));
    }

    @Test
    @DisplayName("Получение заказов с авторизацией")
    public void testCreateOrderWithAuthorization() {
        userManager.createUser(user);
        accessToken = userManager.loginUser(user)
                .extract()
                .body()
                .jsonPath()
                .getString("accessToken");
        orderManager.createOrder(ObjectGenerator.generateIngrients(), accessToken)
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
        orderManager.getOrders(accessToken)
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @After
    public void tearDown() {
        if (accessToken != null && !accessToken.isEmpty()) {
            userManager.deleteUser(accessToken);
        }
    }
}
