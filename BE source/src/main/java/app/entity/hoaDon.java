package app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "hoaDon")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class hoaDon {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hoaDon_seq")
    @SequenceGenerator(
            name = "hoaDon_seq",
            sequenceName = "hoaDon_seq",
            allocationSize = 1
    )
    @Id
    private long id;
    @Column(name = "tienDien")
    private long tienDien;
    @Column(name = "tienNuoc")
    private long tienNuoc;
    @Column(name = "tongTien")
    private long tongTien;
    @Column(name = "donGiaa")
    private long donGia;
    @Column(name = "ngayLapHoaDon")
    private Instant ngayLapHoaDon;
    @Column(name = "soPhong")
    private long soPhong;
}