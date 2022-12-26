package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ra.model.service.IProductService;
import ra.model.entity.Product;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private IProductService productService;
//
//    @GetMapping
//    public List<Product> getAllProduct(){
//        return productService.findAll();
//    }
//
//    @GetMapping("/{productId}")
//    public Product getProductById(@PathVariable("productId") int productId){
//        return (Product) productService.findById(productId);
//    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return (Product) productService.saveOrUpdate(product);
    }

//    @PutMapping("/{productId}")
//    public Product updateProduct(@PathVariable("productId") int productId, @RequestBody Product product){
//        Product productUpdate = (Product) productService.findById(productId);
//        productUpdate.setProductName(product.getProductName());
//        productUpdate.setProductDescription(product.getProductDescription());
//        productUpdate.setProductImportPrice(product.getProductImportPrice());
//        productUpdate.setProductExportPrice(product.getProductExportPrice());
//        productUpdate.setProductCreateDate(product.getProductCreateDate());
//        productUpdate.setProductStatus(product.isProductStatus());
//        productUpdate.setCatalog(product.getCatalog());
//        return (Product) productService.saveOrUpdate(productUpdate);
//    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") int productId){
        productService.delete(productId);
    }

    @GetMapping("search")
    public List<Product> searchProduct(@RequestParam("searchName") String searchName){
        return productService.searchProduct(searchName);
    }

//    @GetMapping("showProductDetail")
//    public List<ProductDetailDTO> getAllProductDetailByProductId(@RequestParam("productId") int productId){
//        return productService.getAllProductDetailByProductId(productId);
//    }
}
