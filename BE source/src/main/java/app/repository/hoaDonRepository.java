package app.repository;

import app.entity.hoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface hoaDonRepository  extends JpaRepository<hoaDon, Long> {
}
