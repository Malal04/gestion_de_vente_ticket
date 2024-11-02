package tiket.isep.entity;

import lombok.Data;

@Data
public class TicketDetailDTO {
    private String name;
    private Integer price;
    private String image;
    private Integer stockQuantity;
    private String categoryName;

    public TicketDetailDTO(String name, Integer price, String image, Integer stockQuantity, String categoryName) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.stockQuantity = stockQuantity;
        this.categoryName = categoryName;
    }
}
