package dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class getOneLoaiPhongResponse {
    long id;
    String loai;
    long donGia;
}
