package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class getListNguoiThueResponse {
    long id;
    String diaChi;
    Instant ngaySinh;
    String cccd;
    String sdt;
}
