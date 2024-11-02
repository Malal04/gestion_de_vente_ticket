package tiket.isep.entity;

import lombok.Data;

@Data
public class TicketReservationDTO {
    private String username;
    private String phone;
    private Integer quantity;
    private Integer ticketId;
}
