package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Data

public class createNguoiThueResponse {
    String hoTen;
    Instant ngaySinh;
    String cccd;
    String sdt;
}
