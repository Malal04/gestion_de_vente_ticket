package tiket.isep.utile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tiket.isep.entity.Category;
import tiket.isep.entity.Ticket;
import tiket.isep.reposity.CategoryRepository;
import tiket.isep.reposity.TicketRepository;

@Component
public class Utile implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println( "#######Initialisation des categories......." );

        Category category1 = new Category("Concert" );

        Category category2 = new Category("Sport" );

        categoryRepository.save(category1);

        categoryRepository.save(category2);

        System.out.println( "#######Initialisation des tickets......." );

        Ticket ticket1 = new Ticket("Ticket Concert De Wally ", 1000, null, 80);
        ticket1.setCategory(category1);

        Ticket ticket2 = new Ticket("Ticket Concert De Dip", 1000, null, 80);
        ticket2.setCategory(category1);

        Ticket ticket3 = new Ticket("Ticket de Match de GALA DFE & DBE", 1000, null, 80);
        ticket3.setCategory(category2);

        Ticket ticket4 = new Ticket("Ticket de Match de GALA DFE & DBE", 1000, null, 80);
        ticket3.setCategory(category2);

        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
        ticketRepository.save(ticket4);

    }

}
