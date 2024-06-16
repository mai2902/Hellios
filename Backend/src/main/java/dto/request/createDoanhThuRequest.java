package dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Month;

@NoArgsConstructor
@Data

public class createDoanhThuRequest {
    long loaiPhong_id;
    Month thang;
    BigDecimal doanhThu;
}
