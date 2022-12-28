package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Color;
import ra.model.entity.Size;
import ra.model.service.ISizeService;
import ra.payload.respone.ColorResponse;
import ra.payload.respone.SizeResponse;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/size")
public class SizeController {

    @Autowired
    private ISizeService sizeService;

    //    -------------------------- ROLE : ADMIN & MODERATOR --------------------

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Size> getAllSize(){
        return sizeService.findAll();
    }

    @GetMapping("/{sizeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Size getById(@PathVariable("sizeId") int sizeId){
        return (Size) sizeService.findById(sizeId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Size createSize(@RequestBody Size size){
        return (Size) sizeService.saveOrUpdate(size);
    }

    @PutMapping("/{sizeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Size updateSize(@PathVariable("sizeId") int sizeId, @RequestBody Size size){
        Size sizeUpdate = (Size) sizeService.findById(sizeId);
        sizeUpdate.setSizeName(size.getSizeName());
        sizeUpdate.setSizeStatus(size.isSizeStatus());
        return (Size) sizeService.saveOrUpdate(sizeUpdate);
    }

    @DeleteMapping("/{sizeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void deleteSize(@PathVariable("sizeId") int sizeId){
        sizeService.delete(sizeId);
    }

    @GetMapping("search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Size> searchSize(@RequestParam("searchName") String searchName){
        return sizeService.searchSize(searchName);
    }

    //    -------------------------- ROLE : USER --------------------
    @GetMapping("/getSizeForUser")
    @PreAuthorize("hasRole('USER')")
    public List<SizeResponse> getSizeForUser() {
        List<SizeResponse> list = new ArrayList<>();
        for (Size size:getAllSize()) {
            if (size.isSizeStatus()){
                SizeResponse sizeResponse = new SizeResponse();
                sizeResponse.setSizeId(size.getSizeId());
                sizeResponse.setSizeName(size.getSizeName());
                list.add(sizeResponse);
            }
        }
        return list;
    }
}
