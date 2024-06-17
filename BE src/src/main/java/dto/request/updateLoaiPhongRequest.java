package dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class updateLoaiPhongRequest {
    String loai;
    long donGia;
}
