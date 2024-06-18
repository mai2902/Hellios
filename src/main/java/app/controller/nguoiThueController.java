package app.controller;

import app.common.RestResponse;
import app.dto.request.createNguoiThueRequest;
import app.dto.request.updateNguoiThueRequest;
import app.dto.response.createNguoiThueResponse;
import app.dto.response.getListNguoiThueResponse;
import app.dto.response.getOneNguoiThueResponse;
import app.dto.response.updateNguoiThueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.service.nguoiThueService;
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
