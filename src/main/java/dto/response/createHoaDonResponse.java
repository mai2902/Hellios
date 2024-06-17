package dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Data

public class createHoaDonResponse {
    long id;
    long phong_id;
    long tienDien;
    long tienNuoc;
    long donGia;
    long tongTien;
    Instant ngayLapHoaDon;
}
