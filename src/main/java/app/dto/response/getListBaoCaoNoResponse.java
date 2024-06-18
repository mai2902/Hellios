package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data

public class getListBaoCaoNoResponse {
    long Id;
    long phong_id;
    long noDau;
    long phatSinh;
    long noCuoi;
    Instant thang;
}
