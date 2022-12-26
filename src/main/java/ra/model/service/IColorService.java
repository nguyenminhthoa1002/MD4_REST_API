package ra.model.service;

import java.util.List;

public interface IColorService<T,V> extends IShopService<T,V>{
    List<T> searchColor(String searchName);
}
