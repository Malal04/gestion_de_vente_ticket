package tiket.isep.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiket.isep.entity.*;
import tiket.isep.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket createdTicket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.ok(createdTicket);
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        Category createdCategory = ticketService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories(Category category){
        return ResponseEntity.ok(ticketService.getTicketsByCategory(category));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
        boolean deleted = ticketService.deleteTicket(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("Categorie/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Integer id) {
        boolean deleted = ticketService.deleteCategories(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
