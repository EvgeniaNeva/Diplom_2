package ru.practicum.yandex.orders;
import lombok.Data;
import lombok.Setter;

@Data
public class Order {
    @Setter
    private String[] ingredients;
    private String _id;
    private String status;
    private Integer number;
    private String createdAt;
    private String updatedAt;
}
