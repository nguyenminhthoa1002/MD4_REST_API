package ra.model.service;

import ra.model.entity.OrderDetail;

import java.util.List;

public interface IOrderDetailService extends IShopService<OrderDetail,Integer> {
    OrderDetail findByProductDetail_ProductDetailIdAndOrders_OrderId(int productDetailId, int orderId);
    List<OrderDetail> findByOrders_OrderId(int orderId);
}
