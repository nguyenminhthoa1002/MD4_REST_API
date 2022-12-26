package ra.model.service;

import java.util.List;

public interface IProductService<T,V> extends IShopService<T,V> {
    List<T> searchProduct(String searchName);
//    List<ProductDetailDTO> getAllProductDetailByProductId(int productId);
}
