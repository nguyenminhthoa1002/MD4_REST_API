package ra.model.service;

import ra.model.entity.ProductDetail;

import java.util.List;
import java.util.Set;

public interface IProductDetailService<T, V> extends IShopService<T, V> {
    Set<ProductDetail> findByProduct_ProductId(int proId);

    Set<ProductDetail> findByColor_ColorNameOrSize_SizeName(String colorName, String sizeName);
}
