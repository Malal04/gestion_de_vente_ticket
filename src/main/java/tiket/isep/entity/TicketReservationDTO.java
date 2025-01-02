package tiket.isep.entity;

import lombok.Data;

@Data
public class TicketReservationDTO {
    private String nomComplete;
    private String phone;
    private Integer quantity;
    private String name;
}
