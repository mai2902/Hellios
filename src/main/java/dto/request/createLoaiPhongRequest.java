package dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data

public class createLoaiPhongRequest {
    String loai;
    long donGia;
}
