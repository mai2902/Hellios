package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "baoCaoNo")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class baoCaoNo {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "baoCaoNo_seq")
    @SequenceGenerator(
            name = "baoCaoNo_seq",
            sequenceName = "baoCaoNo_seq",
            allocationSize = 1
    )
    @Id
    private long Id;
    @OneToOne
    @Column(name = "phong_id")
    private phong phong_id;
    @Column(name = "noDau")
    private long noDau;
    @Column(name = "phatSinh")
    private long phatSinh;
    @Column(name = "noCuoi")
    private long noCuoi;
    @Column(name = "thang")
    private Instant thang;
}
