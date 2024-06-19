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
    @ManyToOne
    @JoinColumn(name = "phong_id")
    private phong phong_id;
    @Column(name = "tienDien")
    private long tienDien;
    @Column(name = "tienNuoc")
    private long tienNuoc;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "donGia", referencedColumnName = "id")
    private loaiPhong donGia;
    @Column(name = "tongTien")
    private long tongTien;
    @Column(name = "ngayLapHoaDon")
    private Instant ngayLapHoaDon;
}