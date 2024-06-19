package app.service;

import app.common.RestResponse;
import app.dto.request.createHoaDonRequest;
import app.dto.request.updateHoaDonRequest;
import app.dto.response.createHoaDonResponse;
import app.dto.response.getOneHoaDonResponse;
import app.dto.response.updateHoaDonResponse;
import app.dto.response.getListHoaDonResponse;
import app.entity.hoaDon;
import app.entity.loaiPhong;
import app.entity.phong;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import app.repository.hoaDonRepository;
import app.repository.loaiPhongRepository;
import app.repository.phongRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class hoaDonService {
    @Autowired
    private final hoaDonRepository HoaDonRepository;
    private final phongRepository PhongRepository;
    private final loaiPhongRepository LoaiPhongRepository;
    private final ModelMapper mapper;

    public RestResponse<List<getListHoaDonResponse>> GetListHoaDon() {
        List<hoaDon> dsHoaDon = HoaDonRepository.findAll();

        return RestResponse.<List<getListHoaDonResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(dsHoaDon.stream()
                        .map(user -> mapper.map(user, getListHoaDonResponse.class))
                        .collect(Collectors.toList()))
                .build();
    }

    public RestResponse<getOneHoaDonResponse> GetOneHoaDon(Long id) {
        Optional<hoaDon> HoaDon = HoaDonRepository.findById(id);
        if (HoaDon.isPresent()) {
            getOneHoaDonResponse res = mapper.map(HoaDon, getOneHoaDonResponse.class);
            return RestResponse.<getOneHoaDonResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(res)
                    .build();
        } else {
            return RestResponse.<getOneHoaDonResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    public RestResponse<createHoaDonResponse> CreateHoaDon(createHoaDonRequest HoaDon) {
        Optional<phong> Phong = PhongRepository.findById(HoaDon.getPhong_id());
        if (Phong.isEmpty()) {
            return RestResponse.<createHoaDonResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
        hoaDon hoaDonMoi = HoaDonRepository.save(mapper.map(HoaDon, hoaDon.class));
        return RestResponse.<createHoaDonResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(mapper.map(hoaDonMoi, createHoaDonResponse.class))
                .build();
    }

    public RestResponse<updateHoaDonResponse> UpdateHoaDon(updateHoaDonRequest HoaDon, Long id) {
        Optional<hoaDon> hoaDonCu = HoaDonRepository.findById(id);
        if (hoaDonCu.isPresent()) {
            if (HoaDon.getPhong_id() != 0) {
                Optional<phong> Phong_id = PhongRepository.findById(HoaDon.getPhong_id());
                if (Phong_id.isEmpty()) {
                    return RestResponse.<updateHoaDonResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .build();
                } else {
                    hoaDonCu.get().setPhong_id(Phong_id.get());
                }
            }
            if (HoaDon.getTienDien() != 0) {
                hoaDonCu.get().setTienDien(HoaDon.getTienDien());
            }
            if (HoaDon.getTienNuoc() != 0) {
                hoaDonCu.get().setTienNuoc(HoaDon.getTienNuoc());
            }
            HoaDonRepository.save(hoaDonCu.get());
            return RestResponse.<updateHoaDonResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(mapper.map(hoaDonCu, updateHoaDonResponse.class))
                    .build();
        }
        else {
            return RestResponse.<updateHoaDonResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    public void DeleteHoaDon(Long id) {
        HoaDonRepository.deleteById(id);
    }
}
