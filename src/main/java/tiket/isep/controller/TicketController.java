package tiket.isep.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiket.isep.entity.*;
import tiket.isep.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket createdTicket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.ok(createdTicket);
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveTicket(@RequestBody TicketReservationDTO reservationDTO) {
        try {
            Ticket t = ticketService.reserveTickets(reservationDTO);
            return ResponseEntity.ok("Ticket réservé avec succès !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDetailDTO> getTicketDetails(@PathVariable Integer ticketId) {
        TicketDetailDTO ticketDetail = ticketService.getTicketDetails(ticketId);
        return ResponseEntity.ok(ticketDetail);
    }


}
