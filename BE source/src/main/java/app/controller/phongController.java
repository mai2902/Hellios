package app.controller;

import app.common.RestResponse;
import app.dto.request.createPhongRequest;
import app.dto.request.updatePhongRequest;
import app.dto.response.createPhongResponse;
import app.dto.response.getListPhongResponse;
import app.dto.response.getOnePhongResponse;
import app.dto.response.updatePhongResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.service.phongService;

import java.util.List;

@RestController
@RequestMapping(value = "api/phong")
public class phongController {

    @Autowired
    private phongService PhongService;

    @PostMapping
    public ResponseEntity<RestResponse<createPhongResponse>> CreatePhong(@RequestBody createPhongRequest Phong) {
        return ResponseEntity.ok().body(PhongService.CreatePhong(Phong));
    }
    @GetMapping
    public ResponseEntity<RestResponse<List<getListPhongResponse>>> GetListPhong(){
        return ResponseEntity.ok().body(PhongService.GetListPhong());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<RestResponse<getOnePhongResponse>> getOnePhong(@PathVariable Long id) {
        return ResponseEntity.ok().body(PhongService.GetOnePhong(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<updatePhongResponse>> updatePhong (@RequestBody updatePhongRequest Phong, @PathVariable Long id){
        return ResponseEntity.ok().body(PhongService.UpdatePhong(Phong,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhong (@PathVariable Long id){
        PhongService.DeletePhong(id);
        return ResponseEntity.noContent().build();
    }
}
