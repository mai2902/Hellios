package app.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Data

public class updateNguoiThueRequest {
    String hoTen;
    Instant ngaySinh;
    String cccd;
    String sdt;
}
