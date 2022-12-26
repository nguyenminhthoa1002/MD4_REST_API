package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Color;
import ra.model.service.IColorService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/color")
public class ColorController {

    @Autowired
    private IColorService colorService;

//    @GetMapping
//    public List<Color> getAllColor(){
//        return colorService.findAll();
//    }

//    @GetMapping("/{colorId}")
//    public Color getColorById(@PathVariable("colorId") int colorId){
//        return (Color) colorService.findById(colorId);
//    }

    @PostMapping
    public Color createColor(@RequestBody Color color){
        return (Color) colorService.saveOrUpdate(color);
    }

//    @PutMapping("/{colorId}")
//    public Color updateColor(@PathVariable("colorId") int colorId, @RequestBody Color color){
//        Color colorUpdate = (Color) colorService.findById(colorId);
//        colorUpdate.setColorHex(color.getColorHex());
//        colorUpdate.setColorName(color.getColorName());
//        colorUpdate.setColorStatus(color.isColorStatus());
//        return (Color) colorService.saveOrUpdate(colorUpdate);
//    }

    @DeleteMapping("/{colorId}")
    public void deleteColor(@PathVariable("colorId") int colorId){
        colorService.delete(colorId);
    }

    @GetMapping("search")
    public List<Color> searchColor(@RequestParam("searchName") String searchName){
        return colorService.searchColor(searchName);
    }
}
