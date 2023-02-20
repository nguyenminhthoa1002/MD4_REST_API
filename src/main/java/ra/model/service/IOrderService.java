package ra.model.service;

import ra.model.entity.Orders;

import java.util.List;

public interface IOrderService extends IShopService<Orders,Integer> {
    List<Orders> findOrdersByUsers_UserId(int userId);
}
