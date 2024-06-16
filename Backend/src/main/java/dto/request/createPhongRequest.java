package dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data

public class createPhongRequest {
    long id;
    long soPhong;
    String loaiPhong;
    String tinhTrang;
}
