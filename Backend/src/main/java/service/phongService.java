package service;

import common.RestResponse;
import dto.request.createPhongRequest;
import dto.request.updatePhongRequest;
import dto.response.*;
import entity.phong;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repository.phongRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class phongService {
    @Autowired
    private final phongRepository PhongRepository;
    private final ModelMapper mapper;

    public RestResponse<List<getListPhongResponse>> GetListPhong() {
        List<phong> dsPhong = PhongRepository.findAll();

        return RestResponse.<List<getListPhongResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(dsPhong.stream()
                        .map(user -> mapper.map(user, getListPhongResponse.class))
                        .collect(Collectors.toList()))
                .build();
    }

    public RestResponse<getOnePhongResponse> GetOnePhong(Long id) {
        Optional<phong> Phong = PhongRepository.findById(id);
        if (Phong.isPresent()) {
            getOnePhongResponse res = mapper.map(Phong, getOnePhongResponse.class);
            return RestResponse.<getOnePhongResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(res)
                    .build();
        }
        else {
            return null;
        }
    }

    public RestResponse<createPhongResponse> CreatePhong(createPhongRequest Phong) {
        Optional<phong> PhongDaTonTai = PhongRepository.findById(Phong.getId());
        if(PhongDaTonTai.isPresent())
            return null;
        phong res = PhongRepository.save(mapper.map(Phong, phong.class));
        return RestResponse.<createPhongResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(mapper.map(res, createPhongResponse.class))
                .build();
    }

    public RestResponse<updatePhongResponse> UpdatePhong(updatePhongRequest Phong, Long id) {
        Optional<phong> phongCu = PhongRepository.findById(id);
        if (phongCu.isPresent()) {
            if(Phong.getSoPhong()!=0) {
                phongCu.get().setSophong(Phong.getSoPhong());
            }
            if(Phong.getLoaiPhong().equals("A")||Phong.getLoaiPhong().equals("B")||Phong.getLoaiPhong().equals("C")||Phong.getLoaiPhong().equals("D")) {
                phongCu.get().setLoaiPhong(Phong.getLoaiPhong());
            }
            if(Phong.getTinhTrang().equals("Da thue")||Phong.getLoaiPhong().equals("Trong")) {
                phongCu.get().setTinhTrang(Phong.getTinhTrang());
            }
            PhongRepository.save(phongCu.get());
            return RestResponse.<updatePhongResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(mapper.map(phongCu, updatePhongResponse.class))
                    .build();
        }
        else {
            return null;
        }

    }
    public void DeletePhong(Long id) {
        PhongRepository.deleteById(id);
    }
}
