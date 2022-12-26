package ra.model.serviceImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.model.entity.ProductDetail;
import ra.model.repository.ProductDetailRepository;
import ra.model.service.IProductDetailService;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(rollbackFor = SQLException.class)
public class ProductDetailService implements IProductDetailService<ProductDetail,Integer> {
    @Autowired
    private ProductDetailRepository productDetailRepository;



    @Override
    public List<ProductDetail> findAll() {
        return productDetailRepository.findAll();
    }

    @Override
    public ProductDetail findById(Integer id) {
        return productDetailRepository.findById(id).get();
    }

    @Override
    public ProductDetail saveOrUpdate(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }

    @Override
    public void delete(Integer id) {
        productDetailRepository.deleteById(id);
    }

    @Override
    public List<ProductDetail> searchProductDetail(int searchName) {
        return productDetailRepository.searchProduct(searchName);
    }
}
