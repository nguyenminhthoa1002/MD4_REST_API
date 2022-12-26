package ra.model.service;

import java.util.List;

public interface IProductDetailService<T,V> extends IShopService<T,V>{
    List<T> searchProductDetail(int searchName);
}
