package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data

public class createPhieuThueResponse {
    long id;
    String nguoiThue_id;
    Instant ngayBatDau;
    Instant ngayKetThuc;
    long phong_id;
}
