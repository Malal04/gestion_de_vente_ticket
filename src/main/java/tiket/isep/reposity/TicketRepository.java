package tiket.isep.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiket.isep.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}