package ra.model.service;

import ra.model.entity.Size;

import java.util.List;

public interface ISizeService<T,V> extends IShopService<T,V>{
    List<T> searchSize(String searchName);
    List<T> getSizeForUser();
}
