package controller;

import common.RestResponse;
import dto.request.createNguoiThueRequest;
import dto.request.updateNguoiThueRequest;
import dto.response.createNguoiThueResponse;
import dto.response.getListNguoiThueResponse;
import dto.response.getOneNguoiThueResponse;
import dto.response.updateNguoiThueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.nguoiThueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/nguoiThue")
public class nguoiThueController {

    @Autowired
    private nguoiThueService NguoiThueService;

    @PostMapping
    public ResponseEntity<RestResponse<createNguoiThueResponse>> CreateNguoiThue(@RequestBody createNguoiThueRequest NguoiThue) {
        return ResponseEntity.ok().body(NguoiThueService.CreateNguoiThue(NguoiThue));
    }
    @GetMapping
    public ResponseEntity<RestResponse<List<getListNguoiThueResponse>>> GetListNguoiThue(){
        return ResponseEntity.ok().body(NguoiThueService.GetListNguoiThue());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<RestResponse<getOneNguoiThueResponse>> getOneNguoiThue(@PathVariable Long id) {
        return ResponseEntity.ok().body(NguoiThueService.GetOneNguoiThue(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<updateNguoiThueResponse>> updateNguoiThue (@RequestBody updateNguoiThueRequest NguoiThue, @PathVariable Long id){
        return ResponseEntity.ok().body(NguoiThueService.UpdateNguoiThue(NguoiThue,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor (@PathVariable Long id){
        NguoiThueService.DeleteNguoiThue(id);
        return ResponseEntity.noContent().build();
    }
}
