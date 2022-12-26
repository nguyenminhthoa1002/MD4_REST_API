package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Size;
import ra.model.service.ISizeService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/size")
public class SizeController {

    @Autowired
    private ISizeService sizeService;
//
//    @GetMapping
//    public List<Size> getAllSize(){
//        return sizeService.findAll();
//    }
//
//    @GetMapping("/{sizeId}")
//    public Size getById(@PathVariable("sizeId") int sizeId){
//        return (Size) sizeService.findById(sizeId);
//    }
//
//    @PostMapping
//    public Size createSize(@RequestBody Size size){
//        return (Size) sizeService.saveOrUpdate(size);
//    }
//
//    @PutMapping("/{sizeId}")
//    public Size updateSize(@PathVariable("sizeId") int sizeId, @RequestBody Size size){
//        Size sizeUpdate = (Size) sizeService.findById(sizeId);
//        sizeUpdate.setSizeName(size.getSizeName());
//        sizeUpdate.setSizeStatus(size.isSizeStatus());
//        return (Size) sizeService.saveOrUpdate(sizeUpdate);
//    }

    @DeleteMapping("/{sizeId}")
    public void deleteSize(@PathVariable("sizeId") int sizeId){
        sizeService.delete(sizeId);
    }

    @GetMapping("search")
    public List<Size> searchSize(@RequestParam("searchName") String searchName){
        return sizeService.searchSize(searchName);
    }
}
