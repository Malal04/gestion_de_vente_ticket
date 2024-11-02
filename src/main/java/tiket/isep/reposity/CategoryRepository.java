package tiket.isep.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiket.isep.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}