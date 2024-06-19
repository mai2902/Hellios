package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Data

public class getListHoaDonResponse {
    long id;
    long tienDien;
    long tienNuoc;
    long donGia;
    long tongTien;
    long soPhong;
    Instant ngayLapHoaDon;
}
