package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Catalog;
import ra.model.entity.Color;
import ra.model.entity.Size;
import ra.model.service.ICatalogService;
import ra.model.service.IColorService;
import ra.model.service.IProductService;
import ra.model.entity.Product;
import ra.model.service.ISizeService;
import ra.payload.request.ProductRequest;
import ra.payload.respone.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private ICatalogService catalogService;

    //    -------------------------- ROLE : ADMIN & MODERATOR --------------------
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<ProductResponse> getAllProduct() {
        List<Product> listProduct = productService.findAll();
        List<ProductResponse> listProductResponse = new ArrayList<>();
        Set<String> listColorName = new HashSet<>();
        Set<String> listSizeName = new HashSet<>();
        for (Product pro : listProduct) {
            for (Color color : pro.getListColor()) {
                listColorName.add(color.getColorName());
            }
            for (Size size : pro.getListSize()) {
                listSizeName.add(size.getSizeName());
            }
            ProductResponse productResponse = new ProductResponse(pro.getProductId(), pro.getProductName(), pro.getProductDescription(),
                    pro.getProductImportPrice(), pro.getProductExportPrice(), pro.getProductImg(), pro.getCatalog().getCatalogName(),
                    pro.getProductCreateDate(), pro.isProductStatus(), listColorName, listSizeName);
            listProductResponse.add(productResponse);
        }
        return listProductResponse;
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ProductResponse getProductById(@PathVariable("productId") int productId) {
        Product pro = (Product) productService.findById(productId);
        Set<String> listColorName = new HashSet<>();
        Set<String> listSizeName = new HashSet<>();
        for (Color color : pro.getListColor()) {
            listColorName.add(color.getColorName());
        }
        for (Size size : pro.getListSize()) {
            listSizeName.add(size.getSizeName());
        }
        ProductResponse productResponse = new ProductResponse(pro.getProductId(), pro.getProductName(), pro.getProductDescription(),
                pro.getProductImportPrice(), pro.getProductExportPrice(), pro.getProductImg(), pro.getCatalog().getCatalogName(),
                pro.getProductCreateDate(), pro.isProductStatus(), listColorName, listSizeName);
        return productResponse;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ProductResponse createProduct(@RequestBody ProductRequest product) {
        Product proNew = new Product();
        proNew.setProductName(product.getProductName());
        proNew.setProductDescription(product.getProductDescription());
        proNew.setProductImportPrice(product.getProductImportPrice());
        proNew.setProductExportPrice((float) (product.getProductImportPrice() * 1.2));
        proNew.setProductImg(product.getProductImg());
        Catalog catalog = (Catalog) catalogService.findById(product.getCatalogId());
        proNew.setCatalog(catalog);
        LocalDateTime time = LocalDateTime.now();
        proNew.setProductCreateDate(time);
        proNew.setProductStatus(true);
        List<Color> listColorAll = colorService.findAll();
        Set<Color> listColor = new HashSet<>();
        for (Color color : listColorAll) {
            for (String strColor : product.getColorStrArr()) {
                if (color.getColorId() == Integer.parseInt(strColor)) {
                    listColor.add((Color) colorService.findById(Integer.parseInt(strColor)));
                }
            }
        }
        proNew.setListColor(listColor);
        List<Size> listSizeAll = sizeService.findAll();
        Set<Size> listSize = new HashSet<>();
        for (Size size : listSizeAll) {
            for (String strSize : product.getSizeStrArr()) {
                if (size.getSizeId() == Integer.parseInt(strSize)) {
                    listSize.add((Size) sizeService.findById(Integer.parseInt(strSize)));
                }
            }
        }
        proNew.setListSize(listSize);
        productService.saveOrUpdate(proNew);

        Set<String> listColorName = new HashSet<>();
        for (Color color : listColor) {
            listColorName.add(color.getColorName());
        }
        Set<String> listSizeName = new HashSet<>();
        for (Size size : listSize) {
            listSizeName.add(size.getSizeName());
        }

        ProductResponse productResponse = new ProductResponse(proNew.getProductId(), proNew.getProductName(), proNew.getProductDescription(),
                proNew.getProductImportPrice(), proNew.getProductExportPrice(), proNew.getProductImg(), proNew.getCatalog().getCatalogName(),
                proNew.getProductCreateDate(), proNew.isProductStatus(), listColorName, listSizeName);
        return productResponse;

    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ProductResponse updateProduct(@PathVariable("productId") int productId, @RequestBody ProductRequest product) {
        Product productUpdate = (Product) productService.findById(productId);
        Catalog catalog = (Catalog) catalogService.findById(product.getCatalogId());

        productUpdate.setProductName(product.getProductName());
        productUpdate.setProductDescription(product.getProductDescription());
        productUpdate.setProductImportPrice(product.getProductImportPrice());
        productUpdate.setProductExportPrice((float) (product.getProductImportPrice() * 1.2));
        productUpdate.setProductImg(productUpdate.getProductImg());
        LocalDateTime time = LocalDateTime.now();
        productUpdate.setProductCreateDate(time);
        productUpdate.setProductStatus(product.isProductStatus());
        productUpdate.setCatalog(catalog);
        List<Color> listColorAll = colorService.findAll();
        Set<Color> listColor = new HashSet<>();
        if (product.getColorStrArr().length == 0) {
            listColor = productUpdate.getListColor();
        } else {
            for (Color color : listColorAll) {
                for (String strColor : product.getColorStrArr()) {
                    if (color.getColorId() == Integer.parseInt(strColor)) {
                        listColor.add((Color) colorService.findById(Integer.parseInt(strColor)));
                    }
                }
            }
        }
        productUpdate.setListColor(listColor);
        List<Size> listSizeAll = sizeService.findAll();
        Set<Size> listSize = new HashSet<>();
        if (product.getSizeStrArr().length == 0) {
            listSize = productUpdate.getListSize();
        } else {
            for (Size size : listSizeAll) {
                for (String strSize : product.getSizeStrArr()) {
                    if (size.getSizeId() == Integer.parseInt(strSize)) {
                        listSize.add((Size) sizeService.findById(Integer.parseInt(strSize)));
                    }
                }
            }
        }
        productUpdate.setListSize(listSize);
        productService.saveOrUpdate(productUpdate);

        Set<String> listColorName = new HashSet<>();
        for (Color color : listColor) {
            listColorName.add(color.getColorName());
        }
        Set<String> listSizeName = new HashSet<>();
        for (Size size : listSize) {
            listSizeName.add(size.getSizeName());
        }

        ProductResponse productResponse = new ProductResponse(productUpdate.getProductId(), productUpdate.getProductName(), productUpdate.getProductDescription(),
                productUpdate.getProductImportPrice(), productUpdate.getProductExportPrice(), productUpdate.getProductImg(), productUpdate.getCatalog().getCatalogName(),
                productUpdate.getProductCreateDate(), productUpdate.isProductStatus(), listColorName, listSizeName);
        return productResponse;
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void deleteProduct(@PathVariable("productId") int productId) {
        productService.delete(productId);
    }

    @GetMapping("search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<ProductResponse> searchProduct(@RequestParam("searchName") String searchName) {
        List<Product> listPro = productService.searchProduct(searchName);
        List<ProductResponse> listProResponse = new ArrayList<>();
        for (Product pro : listPro) {
            ProductResponse pr = new ProductResponse();
            pr.setProductId(pro.getProductId());
            pr.setProductName(pro.getProductName());
            pr.setProductDescription(pro.getProductDescription());
            pr.setProductImportPrice(pro.getProductImportPrice());
            pr.setProductExportPrice(pro.getProductExportPrice());
            pr.setProductImg(pro.getProductImg());
            pr.setCatalogName(pro.getCatalog().getCatalogName());
            pr.setProductCreateDate(pro.getProductCreateDate());
            pr.setProductStatus(pro.isProductStatus());
            Set<String> listColorName = new HashSet<>();
            for (Color color : pro.getListColor()) {
                listColorName.add(color.getColorName());
            }
            pr.setListColorName(listColorName);
            Set<String> listSizeName = new HashSet<>();
            for (Size size : pro.getListSize()) {
                listSizeName.add(size.getSizeName());
            }
            pr.setListSizeName(listSizeName);
            listProResponse.add(pr);
        }
        return listProResponse;
    }

    @GetMapping("/getAllProductByCatalogId")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<ProductResponse> getAllProductByCatalogId(@RequestParam("catId") int catId) {
        List<Product> listPro = productService.getAllProductByCatalogId(catId);
        List<ProductResponse> listProResponse = new ArrayList<>();
        for (Product pro : listPro) {
            ProductResponse pr = new ProductResponse();
            pr.setProductId(pro.getProductId());
            pr.setProductName(pro.getProductName());
            pr.setProductDescription(pro.getProductDescription());
            pr.setProductImportPrice(pro.getProductImportPrice());
            pr.setProductExportPrice(pro.getProductExportPrice());
            pr.setProductImg(pro.getProductImg());
            pr.setCatalogName(pro.getCatalog().getCatalogName());
            pr.setProductCreateDate(pro.getProductCreateDate());
            pr.setProductStatus(pro.isProductStatus());
            Set<String> listColorName = new HashSet<>();
            for (Color color : pro.getListColor()) {
                listColorName.add(color.getColorName());
            }
            pr.setListColorName(listColorName);
            Set<String> listSizeName = new HashSet<>();
            for (Size size : pro.getListSize()) {
                listSizeName.add(size.getSizeName());
            }
            pr.setListSizeName(listSizeName);
            listProResponse.add(pr);
        }
        return listProResponse;
    }

    @PostMapping("initCreateOrUpdate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public InforForCreateOrUpdateProduct initCreateOrUpdate() {
        List<Catalog> listCat = catalogService.getCatalogForCreateProduct();
        List<CatalogResponse> listCatalogRes = new ArrayList<>();
        for (Catalog cat : listCat) {
            CatalogResponse catalogResponse = new CatalogResponse();
            catalogResponse.setCatalogId(cat.getCatalogId());
            catalogResponse.setCatalogName(cat.getCatalogName());
            listCatalogRes.add(catalogResponse);
        }

        List<Color> listColor = colorService.getColorForUser();
        List<ColorResponse> listColorRes = new ArrayList<>();
        for (Color color : listColor) {
            ColorResponse colorResponse = new ColorResponse();
            colorResponse.setColorId(color.getColorId());
            colorResponse.setColorHex(color.getColorHex());
            colorResponse.setColorName(color.getColorName());
            listColorRes.add(colorResponse);
        }

        List<Size> listSize = sizeService.getSizeForUser();
        List<SizeResponse> listSizeRes = new ArrayList<>();
        for (Size size : listSize) {
            SizeResponse sizeResponse = new SizeResponse();
            sizeResponse.setSizeId(size.getSizeId());
            sizeResponse.setSizeName(size.getSizeName());
            listSizeRes.add(sizeResponse);
        }

        InforForCreateOrUpdateProduct infor = new InforForCreateOrUpdateProduct();
        infor.setListCatalog(listCatalogRes);
        infor.setListColor(listColorRes);
        infor.setListSize(listSizeRes);
        return infor;
    }

    //    -------------------------- ROLE : USER --------------------
    @GetMapping("/displayProduct")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public List<ProductResponseForUser> displayProduct() {
        List<Product> listProduct = productService.displayProduct();
        List<ProductResponseForUser> productResponseForUserList = new ArrayList<>();
        for (Product pro : listProduct) {
            ProductResponseForUser proRes = new ProductResponseForUser();
            proRes.setProductId(pro.getProductId());
            proRes.setProductName(pro.getProductName());
            proRes.setProductExportPrice(pro.getProductExportPrice());
            proRes.setProductImg(pro.getProductImg());
            productResponseForUserList.add(proRes);
        }
        return productResponseForUserList;
    }


}
