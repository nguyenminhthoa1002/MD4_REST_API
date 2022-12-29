package ra.model.service;

import org.springframework.data.repository.query.Param;
import ra.model.entity.Color;
import ra.model.entity.Product;

import java.util.List;
import java.util.Set;

public interface IProductService<T,V> extends IShopService<T,V> {
    List<T> searchProduct(String searchName);
    List<T> getAllProductByCatalogId(int catId);
    List<T> displayProduct();
    List<T> searchProductByProductExportPriceBetween(float min, float max);
    List<Product> findByListColor(Set<Color> listColor);
}
