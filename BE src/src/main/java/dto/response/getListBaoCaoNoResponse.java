package dto.response;

import entity.phong;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

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
