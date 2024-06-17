package controller;

import common.RestResponse;
import dto.request.createLoaiPhongRequest;
import dto.request.updateLoaiPhongRequest;
import dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.loaiPhongService;

import java.util.List;

@RestController
@RequestMapping(value = "api/loaiPhong")
public class loaiPhongController {

    @Autowired
    private loaiPhongService LoaiPhongService;

    @PostMapping
    public ResponseEntity<RestResponse<createLoaiPhongResponse>> CreateLoaiPhong(@RequestBody createLoaiPhongRequest LoaiPhong) {
        return ResponseEntity.ok().body(LoaiPhongService.CreateLoaiPhong(LoaiPhong));
    }
    @GetMapping
    public ResponseEntity<RestResponse<List<getListLoaiPhongResponse>>> GetListLoaiPhong(){
        return ResponseEntity.ok().body(LoaiPhongService.GetListLoaiPhong());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<RestResponse<getOneLoaiPhongResponse>> GetOneLoaiPhong(@PathVariable Long id) {
        return ResponseEntity.ok().body(LoaiPhongService.GetOneLoaiPhong(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<updateLoaiPhongResponse>> UpdateLoaiPhong (@RequestBody updateLoaiPhongRequest LoaiPhong, @PathVariable Long id){
        return ResponseEntity.ok().body(LoaiPhongService.UpdateLoaiPhong(LoaiPhong,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoaiPhong (@PathVariable Long id){
        LoaiPhongService.DeleteLoaiPhong(id);
        return ResponseEntity.noContent().build();
    }
}
