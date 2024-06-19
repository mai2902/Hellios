package app.controller;

import app.common.RestResponse;
import app.dto.request.createPhieuThueRequest;
import app.dto.request.updateNguoiThueRequest;
import app.dto.request.updatePhieuThueRequest;
import app.dto.response.*;
import app.service.nguoiThueService;
import app.service.phieuThueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/phieuThue")
public class phieuThueController {
    @Autowired
    private phieuThueService PhieuThueService;

    @PostMapping
    public ResponseEntity<RestResponse<createPhieuThueResponse>> CreatePhieuThue(@RequestBody createPhieuThueRequest PhieuThue) {
        return ResponseEntity.ok().body(PhieuThueService.CreatePhieuThue(PhieuThue));
    }
    @GetMapping
    public ResponseEntity<RestResponse<List<getListPhieuThueResponse>>> GetListPhieuThue(){
        return ResponseEntity.ok().body(PhieuThueService.GetListPhieuThue());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<RestResponse<getOnePhieuThueResponse>> GetOnePhieuThue(@PathVariable Long id) {
        return ResponseEntity.ok().body(PhieuThueService.GetOnePhieuThue(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<updatePhieuThueResponse>> UpdatePhieuThue (@RequestBody updatePhieuThueRequest PhieuThue, @PathVariable Long id){
        return ResponseEntity.ok().body(PhieuThueService.UpdatePhieuThue(PhieuThue,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeletePhieuThue (@PathVariable Long id){
        PhieuThueService.DeletePhieuThue(id);
        return ResponseEntity.noContent().build();
    }
}