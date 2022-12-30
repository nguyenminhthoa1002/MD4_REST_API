package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.*;
import ra.model.service.IColorService;
import ra.model.service.IProductDetailService;
import ra.model.service.IProductService;
import ra.model.service.ISizeService;
import ra.payload.request.ProductDetailRequest;
import ra.payload.respone.InforForProductDetailPage;
import ra.payload.respone.ProductDetailResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/productDetail")
public class ProductDetailController {
    @Autowired
    private IProductDetailService productDetailService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private ISizeService sizeService;

    //    -------------------------- ROLE : ADMIN & MODERATOR --------------------
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Set<ProductDetailResponse> getAllProductDetail() {
        List<ProductDetail> listProductDetails = productDetailService.findAll();
        Set<ProductDetailResponse> listProDeRes = new HashSet<>();
        for (ProductDetail pd : listProductDetails) {
            ProductDetailResponse pdr = new ProductDetailResponse();
            pdr.setProductDetailId(pd.getProductDetailId());
            pdr.setColorName(pd.getColor().getColorName());
            pdr.setColorHex(pd.getColor().getColorHex());
            pdr.setQuantity(pd.getQuantity());
            pdr.setSizeName(pd.getSize().getSizeName());
            pdr.setProductName(pd.getProduct().getProductName());
            listProDeRes.add(pdr);
        }
        return listProDeRes;
    }

    @GetMapping("getAllProDetailByProId")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Set<ProductDetailResponse> getALLProDetailByProId(@RequestParam("proId") int proId) {
        Set<ProductDetail> listProDetail = productDetailService.findByProduct_ProductId(proId);
        Set<ProductDetailResponse> listRes = new HashSet<>();
        for (ProductDetail pd : listProDetail) {
            ProductDetailResponse pdRes = new ProductDetailResponse();
            pdRes.setProductDetailId(pd.getProductDetailId());
            pdRes.setColorName(pd.getColor().getColorName());
            pdRes.setColorHex(pd.getColor().getColorHex());
            pdRes.setSizeName(pd.getSize().getSizeName());
            pdRes.setQuantity(pd.getQuantity());
            pdRes.setProductName(pd.getProduct().getProductName());
            listRes.add(pdRes);
        }
        return listRes;
    }

    @GetMapping("/{productDetailId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ProductDetailResponse getProductDetailById(@PathVariable("productDetailId") int productDetailId) {
        ProductDetail pd = (ProductDetail) productDetailService.findById(productDetailId);
        ProductDetailResponse pdRes = new ProductDetailResponse();
        pdRes.setProductDetailId(pd.getProductDetailId());
        pdRes.setColorName(pd.getColor().getColorName());
        pdRes.setColorHex(pd.getColor().getColorHex());
        pdRes.setSizeName(pd.getSize().getSizeName());
        pdRes.setQuantity(pd.getQuantity());
        pdRes.setProductName(pd.getProduct().getProductName());
        return pdRes;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ProductDetailResponse createProductDetail(@RequestBody ProductDetailRequest productDetail) {
        ProductDetail pd = new ProductDetail();
        pd.setColor((Color) colorService.findById(productDetail.getColorId()));
        pd.setSize((Size) sizeService.findById(productDetail.getSizeId()));
        pd.setQuantity(productDetail.getQuantity());
        Product pro = (Product) productService.findById(productDetail.getProductId());
        pd.setProduct(pro);
        pd.setProductDetailStatus(true);
        productDetailService.saveOrUpdate(pd);
        int totalQuan = pro.getTotalQuantity();
        int proDetailQuan = pd.getQuantity();
        totalQuan += proDetailQuan;
        pro.setTotalQuantity(totalQuan);
        productService.saveOrUpdate(pro);

        ProductDetailResponse pdRes = new ProductDetailResponse();
        pdRes.setProductDetailId(pd.getProductDetailId());
        pdRes.setQuantity(pd.getQuantity());
        pdRes.setProductName(pd.getProduct().getProductName());
        pdRes.setColorHex(pd.getColor().getColorHex());
        pdRes.setColorName(pd.getColor().getColorName());
        pdRes.setSizeName(pd.getSize().getSizeName());

        return pdRes;
    }

    @PutMapping("/{productDetailId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ProductDetailResponse updateProductDetail(@PathVariable("productDetailId") int productDetailId, @RequestBody ProductDetailRequest productDetail) {
        ProductDetail productDetailUpdate = (ProductDetail) productDetailService.findById(productDetailId);
        Product pro = (Product) productService.findById(productDetail.getProductId());
        productDetailUpdate.setProduct(pro);
        productDetailUpdate.setColor((Color) colorService.findById(productDetail.getColorId()));
        productDetailUpdate.setSize((Size) sizeService.findById(productDetail.getSizeId()));
        productDetailUpdate.setQuantity(productDetail.getQuantity());
        productDetailUpdate.setProductDetailStatus(productDetail.isProDetailStatus());
        productDetailService.saveOrUpdate(productDetailUpdate);


        int totalQuan = pro.getTotalQuantity();
        int proDetailQuan = productDetailUpdate.getQuantity();
        totalQuan += proDetailQuan;
        pro.setTotalQuantity(totalQuan);
        productService.saveOrUpdate(pro);

        ProductDetailResponse pdRes = new ProductDetailResponse();
        pdRes.setProductDetailId(productDetailUpdate.getProductDetailId());
        pdRes.setQuantity(productDetailUpdate.getQuantity());
        pdRes.setProductName(productDetailUpdate.getProduct().getProductName());
        pdRes.setColorHex(productDetailUpdate.getColor().getColorHex());
        pdRes.setColorName(productDetailUpdate.getColor().getColorName());
        pdRes.setSizeName(productDetailUpdate.getSize().getSizeName());

        return pdRes;
    }

    @DeleteMapping("/{productDetailId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void deleteProductDetail(@PathVariable("productDetailId") int productDetailId) {
        productDetailService.delete(productDetailId);
    }

    @GetMapping("searchProDetailByColorOrSize")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Set<ProductDetailResponse> search(@RequestParam("colorName") String colorName, @RequestParam("sizeName") String sizeName) {
        Set<ProductDetail> listSearch = productDetailService.findByColor_ColorNameOrSize_SizeName(colorName, sizeName);
        Set<ProductDetailResponse> listSearchRes = new HashSet<>();
        for (ProductDetail pd : listSearch) {
            ProductDetailResponse pdRes = new ProductDetailResponse();
            pdRes.setProductDetailId(pd.getProductDetailId());
            pdRes.setQuantity(pd.getQuantity());
            pdRes.setProductName(pd.getProduct().getProductName());
            pdRes.setColorHex(pd.getColor().getColorHex());
            pdRes.setColorName(pd.getColor().getColorName());
            pdRes.setSizeName(pd.getSize().getSizeName());
            listSearchRes.add(pdRes);
        }
        return listSearchRes;
    }

    //    -------------------------- ROLE : USER --------------------
    @GetMapping("getProductDetailForPageByProductId")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public InforForProductDetailPage getProductDetailForPageByProductId(@RequestParam("proId") int proId) {
        Product pro = (Product) productService.findById(proId);

        InforForProductDetailPage infor = new InforForProductDetailPage();
        infor.setProductName(pro.getProductName());
        infor.setProductDescription(pro.getProductDescription());
        infor.setProductExportPrice(pro.getProductExportPrice());
        infor.setProductImg(pro.getProductImg());
        Set<String> listColorHex = new HashSet<>();
        Set<String> listSizeName = new HashSet<>();
        Set<String> listSubImg = new HashSet<>();
        for (Image img : pro.getListSubImage()) {
            listSubImg.add(img.getImageLink());
        }
        infor.setListSubImage(listSubImg);
        for (Color color : pro.getListColor()) {
            listColorHex.add(color.getColorHex());
        }
        infor.setListColorHex(listColorHex);
        for (Size size : pro.getListSize()) {
            listSizeName.add(size.getSizeName());
        }
        infor.setListSizeName(listSizeName);

        return infor;
    }
}
