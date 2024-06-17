package entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private String nguoiThue_id;
    @Column(name = "ngayBatDau")
    private LocalDate ngayBatDau;
    @Column(name = "ngayKetThuc")
    private LocalDate ngayKetThuc;
    @ManyToOne
    @Column(name = "phong_id")
    private phong phong_id;
}
