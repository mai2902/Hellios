package app.controller;

import app.common.RestResponse;
import app.dto.request.createHoaDonRequest;
import app.dto.request.updateHoaDonRequest;
import app.dto.response.createHoaDonResponse;
import app.dto.response.getListHoaDonResponse;
import app.dto.response.getOneHoaDonResponse;
import app.dto.response.updateHoaDonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.service.hoaDonService;

import java.util.List;

@RestController
@RequestMapping(value = "api/hoaDon")
public class hoaDonController {

    @Autowired
    private hoaDonService HoaDonService;

    @PostMapping
    public ResponseEntity<RestResponse<createHoaDonResponse>> CreateHoaDon(@RequestBody createHoaDonRequest HoaDon) {
        return ResponseEntity.ok().body(HoaDonService.CreateHoaDon(HoaDon));
    }
    @GetMapping
    public ResponseEntity<RestResponse<List<getListHoaDonResponse>>> GetListHoaDon(){
        return ResponseEntity.ok().body(HoaDonService.GetListHoaDon());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<RestResponse<getOneHoaDonResponse>> getOneHoaDon(@PathVariable Long id) {
        return ResponseEntity.ok().body(HoaDonService.GetOneHoaDon(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<updateHoaDonResponse>> updateHoaDon (@RequestBody updateHoaDonRequest HoaDon, @PathVariable Long id){
        return ResponseEntity.ok().body(HoaDonService.UpdateHoaDon(HoaDon,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoaDon (@PathVariable Long id){
        HoaDonService.DeleteHoaDon(id);
        return ResponseEntity.noContent().build();
    }
}

