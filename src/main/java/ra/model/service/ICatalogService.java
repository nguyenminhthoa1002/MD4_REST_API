package ra.model.service;

import java.util.List;

public interface ICatalogService<T,V> extends IShopService<T,V>{
    List<T> searchCatalog(String searchName);
    List<T> findChildById(int catId);
}
