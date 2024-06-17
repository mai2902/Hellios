package repository;

import entity.loaiPhong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface loaiPhongRepository  extends JpaRepository<loaiPhong, Long> {
}
