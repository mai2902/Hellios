package repository;

import entity.nguoiThue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nguoiThueRepository  extends JpaRepository<nguoiThue, Long> {
}
