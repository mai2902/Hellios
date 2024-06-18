package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Data

public class updateNguoiThueResponse {
    long id;
    String hoTen;
    Instant ngaySinh;
    String cccd;
    String sdt;
}
