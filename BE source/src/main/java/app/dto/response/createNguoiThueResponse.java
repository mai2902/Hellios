package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Data

public class createNguoiThueResponse {
    long id;
    String hoTen;
    Instant ngaySinh;
    String cccd;
    String sdt;
}
