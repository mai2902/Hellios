package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "loaiPhong")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class loaiPhong {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loaiphong_seq")
    @SequenceGenerator(
            name = "loaiphong_seq",
            sequenceName = "loaiphong_seq",
            allocationSize = 1
    )
    @Id
    private long id;
    @Column(name = "loai")
    private String loai;
    @Column(name = "donGia")
    private long donGia;
}
