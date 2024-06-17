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
@Table(name = "nguoiThue")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class nguoiThue {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nguoiThue_seq")
    @SequenceGenerator(
            name = "nguoiThue_seq",
            sequenceName = "nguoiThue_seq",
            allocationSize = 1
    )
    @Id
    private long id;
    @Column(name = "hoTen")
    private String hoTen;
    @Column(name = "diaChi")
    private String diaChi;
    @Column(name = "ngaySinh")
    private Instant ngaySinh;
    @Column(name = "cccd")
    private String cccd;
    @Column(name = "sdt")
    private String sdt;
}
