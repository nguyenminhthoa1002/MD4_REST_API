package ra.model.service;

import java.util.List;
import java.util.Set;

public interface IImageService<T,V> extends IShopService<T,V>{
    Set<T> searchImageByProductId(V productId);
}
