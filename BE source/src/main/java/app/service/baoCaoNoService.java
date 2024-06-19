package app.service;

import app.common.RestResponse;
import app.dto.request.createBaoCaoNoRequest;
import app.dto.request.updateBaoCaoNoRequest;
import app.dto.response.createBaoCaoNoResponse;
import app.dto.response.createPhieuThueResponse;
import app.dto.response.getListBaoCaoNoResponse;
import app.dto.response.getOneBaoCaoNoResponse;
import app.dto.response.updateBaoCaoNoResponse;
import app.entity.baoCaoNo;
import app.entity.phong;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import app.repository.baoCaoNoRepository;
import app.repository.loaiPhongRepository;
import app.repository.phongRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class baoCaoNoService {
    @Autowired
    private final baoCaoNoRepository BaoCaoNoRepository;
    private final phongRepository PhongRepository;
    private final loaiPhongRepository LoaiPhongRepository;
    private final ModelMapper mapper;

    public RestResponse<List<getListBaoCaoNoResponse>> GetListBaoCaoNo() {
        List<baoCaoNo> dsBaoCaoNo = BaoCaoNoRepository.findAll();

        return RestResponse.<List<getListBaoCaoNoResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(dsBaoCaoNo.stream()
                        .map(user -> mapper.map(user, getListBaoCaoNoResponse.class))
                        .collect(Collectors.toList()))
                .build();
    }

    public RestResponse<getOneBaoCaoNoResponse> GetOneBaoCaoNo(Long id) {
        Optional<baoCaoNo> BaoCaoNo = BaoCaoNoRepository.findById(id);
        if (BaoCaoNo.isPresent()) {
            getOneBaoCaoNoResponse res = mapper.map(BaoCaoNo, getOneBaoCaoNoResponse.class);
            return RestResponse.<getOneBaoCaoNoResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(res)
                    .build();
        } else {
            return null;
        }
    }

    public RestResponse<createBaoCaoNoResponse> CreateBaoCaoNo(createBaoCaoNoRequest BaoCaoNo) {

        List<phong> phongs = PhongRepository.findAll();
        Optional<phong> ph = Optional.empty();
        for (phong p : phongs) {
            if (p.getSoPhong() == BaoCaoNo.getPhong_id()) {
                ph = Optional.ofNullable(p);
            }
        }
        if (ph.isEmpty()) {
            return RestResponse.<createBaoCaoNoResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        baoCaoNo BaoCaoNoMoi = BaoCaoNoRepository.save(mapper.map(BaoCaoNo, baoCaoNo.class));
        return RestResponse.<createBaoCaoNoResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(mapper.map(BaoCaoNoMoi, createBaoCaoNoResponse.class))
                .build();
    }

    public RestResponse<updateBaoCaoNoResponse> UpdateBaoCaoNo(updateBaoCaoNoRequest BaoCaoNo, Long id) {
        Optional<baoCaoNo> BaoCaoNoCu = BaoCaoNoRepository.findById(id);
        if (BaoCaoNoCu.isPresent()) {
            if (BaoCaoNo.getPhong_id() != 0) {
                List<phong> phongs = PhongRepository.findAll();
                Optional<phong> ph = Optional.empty();
                for (phong p : phongs) {
                    if (p.getSoPhong() == BaoCaoNo.getPhong_id()) {
                        ph = Optional.ofNullable(p);
                    }
                }
                if (ph.isEmpty()) {
                    return RestResponse.<updateBaoCaoNoResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .build();
                }
            }
            if (BaoCaoNo.getNoDau() > 0) {
                BaoCaoNoCu.get().setNoDau(BaoCaoNo.getNoDau());
            }
            if (BaoCaoNo.getNoCuoi() > 0) {
                BaoCaoNoCu.get().setNoCuoi(BaoCaoNo.getNoCuoi());
            }
            if (BaoCaoNo.getPhatSinh() > 0) {
                BaoCaoNoCu.get().setPhatSinh(BaoCaoNo.getPhatSinh());
            }
            if (BaoCaoNo.getThang() != null) {
                BaoCaoNoCu.get().setThang(BaoCaoNo.getThang());
            }
            BaoCaoNoRepository.save(BaoCaoNoCu.get());
            return RestResponse.<updateBaoCaoNoResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(mapper.map(BaoCaoNoCu, updateBaoCaoNoResponse.class))
                    .build();
        } else {
            return null;
        }
    }

    public void DeleteHoaDon(Long id) {
        BaoCaoNoRepository.deleteById(id);
    }
}
