package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data

public class getOneBaoCaoNoResponse {
    long Id;
    long phong_id;
    long noDau;
    long noCuoi;
    Instant thang;
}
