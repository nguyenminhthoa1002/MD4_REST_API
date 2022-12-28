package ra.model.service;

import ra.model.entity.Color;

import java.util.List;

public interface IColorService<T,V> extends IShopService<T,V>{
    List<T> searchColor(String searchName);
    List<T> getColorForUser();
}
