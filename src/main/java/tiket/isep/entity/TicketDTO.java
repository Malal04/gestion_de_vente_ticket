package tiket.isep.entity;


import lombok.Data;

@Data
public class TicketDTO {
    private String name;
    private Integer price;
    private String image;
    private Integer userId;
    private Integer categoryId;
}
