package tiket.isep.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tiket.isep.entity.*;
import tiket.isep.reposity.CategoryRepository;
import tiket.isep.reposity.TicketRepository;
import tiket.isep.users.models.entities.User;
import tiket.isep.users.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    public Ticket createTicket(TicketDTO ticketDTO) {
        Category category = categoryRepository.findByName(ticketDTO.getCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));

        Ticket ticket = new Ticket(ticketDTO.getName(), 1000, ticketDTO.getImage(), 80);
        ticket.setCategory(category);
        return ticketRepository.save(ticket);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Ticket reserveTickets(TicketReservationDTO reservationDTO) {
        if (reservationDTO.getQuantity() > 3) {
            throw new IllegalArgumentException("Vous ne pouvez réserver que 3 tickets à la fois.");
        }

        User user = userRepository.findByNomComplete(reservationDTO.getNomComplete())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setNomComplete(reservationDTO.getNomComplete());
                    newUser.setTelephone(reservationDTO.getPhone());
                    return userRepository.save(newUser);
                });

        if (user.isHasReserved()) {
            throw new IllegalArgumentException("Vous avez déjà réservé des tickets.");
        }

        Ticket ticket = ticketRepository.findByName(reservationDTO.getName())
                .orElseThrow(() -> new IllegalArgumentException("Ticket non trouvé"));

        if (ticket.getStockQuantity() < reservationDTO.getQuantity()) {
            throw new IllegalArgumentException("Stock insuffisant pour la réservation.");
        }

        ticket.setStockQuantity(ticket.getStockQuantity() - reservationDTO.getQuantity());
        ticket.setUser(user);
        user.setHasReserved(true);
        userRepository.save(user);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Category> getTicketsByCategory(Category category) {
        return categoryRepository.findAll();
    }

    public TicketDetailDTO getTicketDetails(Integer ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket non trouvé"));

        return new TicketDetailDTO(
                ticket.getName(),
                ticket.getPrice(),
                ticket.getImage(),
                ticket.getStockQuantity(),
                ticket.getCategory().getName()
        );
    }

    @Transactional
    public boolean deleteTicket(Integer id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteCategories(Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
