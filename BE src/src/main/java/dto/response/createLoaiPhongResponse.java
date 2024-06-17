package dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class createLoaiPhongResponse {
    long id;
    String loai;
    long donGia;
}
