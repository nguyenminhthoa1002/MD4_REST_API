package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.ProductDetail;
import ra.model.service.IProductDetailService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/productDetail")
public class ProductDetailController {
    @Autowired
    private IProductDetailService productDetailService;

//    @GetMapping
//    public List<ProductDetail> getAllProductDetail(){
//        return productDetailService.findAll();
//    }
//
//    @GetMapping("/{productDetailId}")
//    public ProductDetail getProductDetailById(@PathVariable("productDetailId") int productDetailId){
//        return (ProductDetail) productDetailService.findById(productDetailId);
//    }

    @PostMapping
    public ProductDetail createProductDetail(@RequestBody ProductDetail productDetail){
        return (ProductDetail) productDetailService.saveOrUpdate(productDetail);
    }

//    @PutMapping("/{productDetailId}")
//    public ProductDetail updateProductDetail(@PathVariable("productDetailId") int productDetailId, @RequestBody ProductDetail productDetail){
//        ProductDetail productDetailUpdate = (ProductDetail) productDetailService.findById(productDetailId);
//        productDetailUpdate.setProduct(productDetail.getProduct());
//        productDetailUpdate.setColor(productDetail.getColor());
//        productDetailUpdate.setSize(productDetail.getSize());
//        return (ProductDetail) productDetailService.saveOrUpdate(productDetailUpdate);
//    }

    @DeleteMapping("/{productDetailId}")
    public void deleteProductDetail(@PathVariable("productDetailId") int productDetailId){
        productDetailService.delete(productDetailId);
    }

    @GetMapping("search")
    public List<ProductDetail> search(@RequestParam("searchName") int searchName){
        return productDetailService.searchProductDetail(searchName);
    }
}
