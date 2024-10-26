package ru.practicum.yandex.orders;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrdersResponse {
    @SerializedName("success")
    private boolean isSuccess;
    private String name;
    @SerializedName("orders")
    private List<Order> orders = new ArrayList<>();
    private Order order;
    private Integer total;
    private Integer totalToday;
}