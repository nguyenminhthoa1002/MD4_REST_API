package ra.model.service;

import ra.model.entity.Orders;

public interface IOrderService extends IShopService<Orders,Integer> {
    Orders findOrdersByUsers_UserId(int userId);
}
