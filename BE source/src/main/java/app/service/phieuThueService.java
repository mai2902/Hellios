package app.service;

import app.common.RestResponse;
import app.dto.request.createNguoiThueRequest;
import app.dto.request.createPhieuThueRequest;
import app.dto.request.updateNguoiThueRequest;
import app.dto.request.updatePhieuThueRequest;
import app.dto.response.*;
import app.entity.nguoiThue;
import app.entity.phieuThue;
import app.entity.phong;
import app.repository.nguoiThueRepository;
import app.repository.phieuThueRepository;
import app.repository.phongRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class phieuThueService {
    @Autowired
    private final phieuThueRepository PhieuThueRepository;
    private final phongRepository PhongRepository;
    private final nguoiThueRepository NguoiThueRepository;
    private final ModelMapper mapper;

    public RestResponse<List<getListPhieuThueResponse>> GetListPhieuThue() {
        List<phieuThue> dsPhieuThue = PhieuThueRepository.findAll();
        return RestResponse.<List<getListPhieuThueResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(dsPhieuThue.stream()
                        .map(book -> mapper.map(book, getListPhieuThueResponse.class))
                        .collect(Collectors.toList()))
                .build();
    }

    public RestResponse<getOnePhieuThueResponse> GetOnePhieuThue(Long id) {
        Optional<phieuThue> PhieuThue = PhieuThueRepository.findById(id);
        if (PhieuThue.isPresent()) {
            getOnePhieuThueResponse pt = mapper.map(PhieuThue, getOnePhieuThueResponse.class);
            return RestResponse.<getOnePhieuThueResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(pt)
                    .build();
        }
        else {
            return RestResponse.<getOnePhieuThueResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    public RestResponse<createPhieuThueResponse> CreatePhieuThue(createPhieuThueRequest PhieuThue) {

        List<phong> phongs = PhongRepository.findAll();
        Optional<phong> ph = Optional.empty();
        for (phong p : phongs) {
            if(p.getSoPhong() == PhieuThue.getPhong_id()){
                ph = Optional.ofNullable(p);
            }
        }
        if (ph.isEmpty()) {
            return RestResponse.<createPhieuThueResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        phieuThue pt = PhieuThueRepository.save(mapper.map(PhieuThue, phieuThue.class));
        return RestResponse.<createPhieuThueResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(mapper.map(pt, createPhieuThueResponse.class))
                .build();
    }

    public RestResponse<updatePhieuThueResponse> UpdatePhieuThue(updatePhieuThueRequest PhieuThue, Long id) {
        Optional<phieuThue> phieuThueCu = PhieuThueRepository.findById(id);
        if (phieuThueCu.isPresent()) {
            if (PhieuThue.getNguoiThue_id() != 0) {
                Optional<nguoiThue> NguoiThue = NguoiThueRepository.findById(PhieuThue.getNguoiThue_id());
                if (NguoiThue.isEmpty()) {
                    return RestResponse.<updatePhieuThueResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .build();
                }
                else {
                    phieuThueCu.get().setNguoiThue_id(NguoiThue.get().getId());
                }
            }
            if(PhieuThue.getNgayBatDau() != null){
                phieuThueCu.get().setNgayBatDau(PhieuThue.getNgayBatDau());
            }
            if(PhieuThue.getNgayKetThuc() != null){
                phieuThueCu.get().setNgayKetThuc(PhieuThue.getNgayKetThuc());
            }
            if (PhieuThue.getPhong_id() != 0) {
                List<phong> phongs = PhongRepository.findAll();
                Optional<phong> ph = Optional.empty();
                for (phong p : phongs) {
                    if(p.getSoPhong() == PhieuThue.getPhong_id()){
                        ph = Optional.ofNullable(p);
                    }
                }
                if (ph.isEmpty()) {
                    return RestResponse.<updatePhieuThueResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .build();
                }
                else {
                    phieuThueCu.get().setPhong(ph.get());
                }
            }
            PhieuThueRepository.save(phieuThueCu.get());
            return RestResponse.<updatePhieuThueResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(mapper.map(phieuThueCu,updatePhieuThueResponse.class))
                    .build();
        }
        else {
            return RestResponse.<updatePhieuThueResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    public void DeletePhieuThue(Long id) {
        PhieuThueRepository.deleteById(id);
    }
}