package app.repository;

import app.entity.phieuThue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface phieuThueRepository  extends JpaRepository<phieuThue, Long> {
}
