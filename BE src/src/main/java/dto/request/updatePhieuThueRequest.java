package dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data

public class updatePhieuThueRequest {
    String nguoiThue_id;
    Instant ngayBatDau;
    Instant ngayKetThuc;
    long phong_id;
}
