package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data

public class getListBaoCaoNoResponse {
    long Id;
    long noDau;
    long phatSinh;
    long noCuoi;
    long soPhong;
    Instant thang;
}
