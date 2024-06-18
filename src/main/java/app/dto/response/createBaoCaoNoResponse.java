package app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Data

public class createBaoCaoNoResponse {
    long Id;
    long phong_id;
    long noDau;
    long phatSinh;
    long noCuoi;
    Instant thang;
}
