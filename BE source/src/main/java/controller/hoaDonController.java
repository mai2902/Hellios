package controller;

import common.RestResponse;
import dto.request.createHoaDonRequest;
import dto.request.updateHoaDonRequest;
import dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.hoaDonService;

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

