package ra.model.serviceImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.model.entity.OrderDetail;
import ra.model.entity.ProductDetail;
import ra.model.repository.OrderDetailRepository;
import ra.model.service.IOrderDetailService;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(rollbackFor = SQLException.class)
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail findById(Integer id) {
        return orderDetailRepository.findById(id).get();
    }

    @Override
    public OrderDetail saveOrUpdate(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void delete(Integer id) {
        orderDetailRepository.deleteById(id);
    }
}
