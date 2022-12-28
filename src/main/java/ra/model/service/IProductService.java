package ra.model.service;

import org.springframework.data.repository.query.Param;
import ra.model.entity.Product;

import java.util.List;

public interface IProductService<T,V> extends IShopService<T,V> {
    List<T> searchProduct(String searchName);
    List<T> getAllProductByCatalogId(int catId);
    List<T> displayProduct();
}
