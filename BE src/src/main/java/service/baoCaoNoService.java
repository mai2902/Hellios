package service;
import common.RestResponse;
import dto.request.createBaoCaoNoRequest;
import dto.request.updateBaoCaoNoRequest;
import dto.response.*;
import entity.baoCaoNo;
import entity.phong;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.InjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repository.baoCaoNoRepository;
import repository.loaiPhongRepository;
import repository.phongRepository;

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
        Optional<baoCaoNo> BaoCaoNoCu = BaoCaoNoRepository.findById(BaoCaoNo.getPhong_id());
        if (BaoCaoNoCu.isEmpty()) {
            return null;
        }
        baoCaoNo BaoCaoNoMoi = mapper.map(BaoCaoNo, baoCaoNo.class);
        BaoCaoNoCu.get().setPhong_id(BaoCaoNoMoi.getPhong_id());
        BaoCaoNoRepository.save(BaoCaoNoMoi);
        return RestResponse.<createBaoCaoNoResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(mapper.map(BaoCaoNoMoi, createBaoCaoNoResponse.class))
                .build();
    }

    public RestResponse<updateBaoCaoNoResponse> UpdateBaoCaoNo(updateBaoCaoNoRequest BaoCaoNo, Long id) {
        Optional<baoCaoNo> BaoCaoNoCu = BaoCaoNoRepository.findById(id);
        if (BaoCaoNoCu.isPresent()) {
            if (BaoCaoNo.getPhong_id() != 0) {
                Optional<phong> Phong_id = PhongRepository.findById(BaoCaoNo.getPhong_id());
                if (Phong_id.isEmpty()) {
                    return null;
                } else {
                    BaoCaoNoCu.get().setPhong_id(Phong_id.get());
                }
            }
            if(BaoCaoNo.getNoDau()>0){
                BaoCaoNoCu.get().setNoDau(BaoCaoNo.getNoDau());
            }
            if(BaoCaoNo.getNoCuoi()>0){
                BaoCaoNoCu.get().setNoCuoi(BaoCaoNo.getNoCuoi());
            }
            if(BaoCaoNo.getPhatSinh()>0){
                BaoCaoNoCu.get().setPhatSinh(BaoCaoNo.getPhatSinh());
            }
            if(BaoCaoNo.getThang()!=null){
                BaoCaoNoCu.get().setThang(BaoCaoNo.getThang());
            }
            BaoCaoNoRepository.save(BaoCaoNoCu.get());
            return RestResponse.<updateBaoCaoNoResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(mapper.map(BaoCaoNoCu, updateBaoCaoNoResponse.class))
                    .build();
        }
        else {
            return null;
        }
    }

    public void DeleteHoaDon(Long id) {
            BaoCaoNoRepository.deleteById(id);
    }
}
