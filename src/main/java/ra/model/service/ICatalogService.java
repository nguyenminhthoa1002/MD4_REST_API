package ra.model.service;

import ra.model.entity.Catalog;
import ra.payload.respone.CatalogResponse;

import java.util.List;
import java.util.Set;

public interface ICatalogService<T,V> extends IShopService<T,V>{
    List<T> searchCatalog(String searchName);
    List<T> findChildById(int catId);
    List<T> getCatalogForCreateProduct();
    Set<Catalog> findByCatalogIdIn(int[] listCatalog);
    List<Catalog> getCatalogForCreatCatalog();
}
