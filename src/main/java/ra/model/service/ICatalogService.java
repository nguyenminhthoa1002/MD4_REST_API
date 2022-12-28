package ra.model.service;

import ra.model.entity.Catalog;
import ra.payload.respone.CatalogResponse;

import java.util.List;

public interface ICatalogService<T,V> extends IShopService<T,V>{
    List<T> searchCatalog(String searchName);
    List<T> findChildById(int catId);
    List<T> getCatalogForCreateProduct();
}
