package ra.model.service;

import java.util.List;

public interface ISizeService<T,V> extends IShopService<T,V>{
    List<T> searchSize(String searchName);
}
