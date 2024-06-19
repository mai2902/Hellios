package app.service;

import app.common.RestResponse;
import app.dto.request.createLoaiPhongRequest;
import app.dto.request.updateLoaiPhongRequest;
import app.dto.response.createLoaiPhongResponse;
import app.dto.response.getListLoaiPhongResponse;
import app.dto.response.getOneLoaiPhongResponse;
import app.dto.response.updateLoaiPhongResponse;
import app.entity.loaiPhong;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import app.repository.loaiPhongRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class loaiPhongService {
    @Autowired
    private final loaiPhongRepository LoaiPhongRepository;
    private final ModelMapper mapper;

    public RestResponse<getOneLoaiPhongResponse> GetOneLoaiPhong(Long id) {
        Optional<loaiPhong> LoaiPhong = LoaiPhongRepository.findById(id);
        if (LoaiPhong.isPresent()) {
            getOneLoaiPhongResponse res = mapper.map(LoaiPhong, getOneLoaiPhongResponse.class);
            return RestResponse.<getOneLoaiPhongResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(res)
                    .build();
        } else {
            return RestResponse.<getOneLoaiPhongResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    public RestResponse<List<getListLoaiPhongResponse>> GetListLoaiPhong() {
        List<loaiPhong> dsLoaiPhong = LoaiPhongRepository.findAll();

        return RestResponse.<List<getListLoaiPhongResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(dsLoaiPhong.stream()
                        .map(user -> mapper.map(dsLoaiPhong, getListLoaiPhongResponse.class))
                        .collect(Collectors.toList()))
                .build();
    }


    public RestResponse<createLoaiPhongResponse> CreateLoaiPhong(createLoaiPhongRequest LoaiPhong) {
        loaiPhong Loai = LoaiPhongRepository.save(mapper.map(LoaiPhong, loaiPhong.class));
        return RestResponse.<createLoaiPhongResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(mapper.map(Loai, createLoaiPhongResponse.class))
                .build();
    }


    public RestResponse<updateLoaiPhongResponse> UpdateLoaiPhong(updateLoaiPhongRequest LoaiPhong, Long id) {
        Optional<loaiPhong> loaiPhongCu = LoaiPhongRepository.findById(id);
        if (loaiPhongCu.isPresent()) {
            if(LoaiPhong.getLoai()!=null) {
                loaiPhongCu.get().setLoai(LoaiPhong.getLoai());
            }
            if(LoaiPhong.getDonGia()>0) {
                loaiPhongCu.get().setDonGia(LoaiPhong.getDonGia());
            }
            LoaiPhongRepository.save(loaiPhongCu.get());
            return RestResponse.<updateLoaiPhongResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(mapper.map(loaiPhongCu, updateLoaiPhongResponse.class))
                    .build();
        }
        else {
            return RestResponse.<updateLoaiPhongResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    public void DeleteLoaiPhong(Long id) {
        LoaiPhongRepository.deleteById(id);
    }
}
