package repository;

import entity.baoCaoNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface baoCaoNoRepository  extends JpaRepository<baoCaoNo, Long> {
}
