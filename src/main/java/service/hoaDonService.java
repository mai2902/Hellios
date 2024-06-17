package service;

import common.RestResponse;
import dto.request.createHoaDonRequest;
import dto.request.updateHoaDonRequest;
import dto.response.*;
import dto.response.getListHoaDonResponse;
import entity.hoaDon;
import entity.loaiPhong;
import entity.phong;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repository.hoaDonRepository;
import repository.loaiPhongRepository;
import repository.phongRepository;


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
            return null;
        }
    }

    public RestResponse<createHoaDonResponse> CreateHoaDon(createHoaDonRequest HoaDon) {
        Optional<phong> Phong = PhongRepository.findById(HoaDon.getPhong_id());
        if (Phong.isEmpty()) {
            return null;
        }
        hoaDon hoaDonMoi = mapper.map(HoaDon, hoaDon.class);
        hoaDonMoi.setPhong_id(Phong.get());
        HoaDonRepository.save(hoaDonMoi);
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
                    return null;
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
            return null;
        }
    }

    public void DeleteHoaDon(Long id) {
        HoaDonRepository.deleteById(id);
    }
}
