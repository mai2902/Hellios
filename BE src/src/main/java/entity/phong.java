package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "phong")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class phong {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phong_seq")
    @SequenceGenerator(
            name = "phong_seq",
            sequenceName = "phong_seq",
            allocationSize = 1
    )
    @Id
    private long id;
    @Column(name = "soPhong")
    private long sophong;
    @Column(name = "loaiPhong")
    private String loaiPhong;
    @Column(name = "tinhTrang")
    private String tinhTrang;
}