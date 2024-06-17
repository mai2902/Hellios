package controller;
import common.RestResponse;
import dto.request.createBaoCaoNoRequest;
import dto.request.updateBaoCaoNoRequest;
import dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.baoCaoNoService;

import java.util.List;

@RestController
@RequestMapping(value = "api/baoCaoNo")
public class baoCaoNoController {

    @Autowired
    private baoCaoNoService BaoCaoNoService;

    @PostMapping
    public ResponseEntity<RestResponse<createBaoCaoNoResponse>> CreateBaoCaoNo(@RequestBody createBaoCaoNoRequest BaoCaoNo) {
        return ResponseEntity.ok().body(BaoCaoNoService.CreateBaoCaoNo(BaoCaoNo));
    }
    @GetMapping
    public ResponseEntity<RestResponse<List<getListBaoCaoNoResponse>>> GetListBaoCaoNo(){
        return ResponseEntity.ok().body(BaoCaoNoService.GetListBaoCaoNo());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<RestResponse<getOneBaoCaoNoResponse>> GetOneBaoCaoNo(@PathVariable Long id) {
        return ResponseEntity.ok().body(BaoCaoNoService.GetOneBaoCaoNo(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<updateBaoCaoNoResponse>> UpdateBaoCaoNo (@RequestBody updateBaoCaoNoRequest BaoCaoNo, @PathVariable Long id){
        return ResponseEntity.ok().body(BaoCaoNoService.UpdateBaoCaoNo(BaoCaoNo,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteBaoCaoNo (@PathVariable Long id){
        BaoCaoNoService.DeleteHoaDon(id);
        return ResponseEntity.noContent().build();
    }
}