package app.repository;

import app.entity.phong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface phongRepository  extends JpaRepository<phong, Long> {
}
