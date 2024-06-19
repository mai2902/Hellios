package app.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "phieuThue")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class phieuThue {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phieuThue_seq")
    @SequenceGenerator(
            name = "phieuThue_seq",
            sequenceName = "phieuThue_seq",
            allocationSize = 1
    )
    @Id
    private long id;
    @Column(name = "nguoiThue_id")
    private long nguoiThue_id;
    @Column(name = "ngayBatDau")
    private Instant ngayBatDau;
    @Column(name = "ngayKetThuc")
    private Instant ngayKetThuc;
    @Column(name = "soPhong")
    private long soPhong;
    @ManyToOne
    @JoinColumn(name = "phong_id")
    private phong phong;
}
