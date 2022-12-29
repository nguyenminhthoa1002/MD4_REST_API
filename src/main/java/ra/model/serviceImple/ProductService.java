package ra.model.serviceImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.model.entity.Color;
import ra.model.repository.ProductRepository;
import ra.model.service.IProductService;
import ra.model.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Service
@Transactional(rollbackFor = SQLException.class)
public class ProductService implements IProductService<Product,Integer> {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> searchProduct(String searchName) {
        return productRepository.searchProductByProductNameContaining(searchName);
    }

    @Override
    public List<Product> getAllProductByCatalogId(int catId) {
        return productRepository.getAllProductByCatalogId(catId);
    }

    @Override
    public List<Product> displayProduct() {
        return productRepository.displayProduct();
    }

    @Override
    public List<Product> searchProductByProductExportPriceBetween(float min, float max) {
        return productRepository.searchProductByProductExportPriceBetween(min, max);
    }

    @Override
    public List<Product> findByListColor(Set<Color> listColor) {
        return productRepository.findByListColor(listColor);
    }


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Integer id) {
        Product proDelete = findById(id);
        proDelete.setProductStatus(false);
        productRepository.save(proDelete);
    }
}
