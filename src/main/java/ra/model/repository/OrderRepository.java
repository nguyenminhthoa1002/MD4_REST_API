package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    Orders findOrdersByUsers_UserId(int userId);
}
