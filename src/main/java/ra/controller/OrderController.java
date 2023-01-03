//package ra.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import ra.model.entity.OrderDetail;
//import ra.model.entity.Orders;
//import ra.model.entity.Product;
//import ra.model.entity.Users;
//import ra.model.service.*;
//import ra.payload.request.OrderDetailRequest;
//import ra.security.CustomUserDetails;
//
//import java.util.Set;
//
//@CrossOrigin(origins = "http://localhost:8080")
//@RestController
//@RequestMapping("api/v1/order")
//public class OrderController {
//    @Autowired
//    private IOrderService orderService;
//    @Autowired
//    private IOrderDetailService orderDetailService;
//    @Autowired
//    private IUserService userService;
//    @Autowired
//    private IProductDetailService productDetailService;
//    @Autowired
//    private IProductService productService;
//
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
//    public Orders addToCart(@RequestBody OrderDetailRequest orderDetailRequest){
//        CustomUserDetails usersChangePass = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Users users = userService.findUsersByUserName(usersChangePass.getUsername());
//        Product product = (Product) productService.findById(orderDetailRequest.getProductId());
//        Orders orders = orderService.findOrdersByUsers_UserId(users.getUserId());
//
//        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setProductDetail(productDetailService.findByColor_ColorHexAndSize_SizeName(orderDetailRequest.getColorHex(), orderDetailRequest.getSizeName()));
//        orderDetail.setOrders(orders);
//        orderDetail.setQuantity(orderDetailRequest.getQuantity());
//        orderDetail.setPrice(product.getProductExportPrice());
//        orderDetail.setTotalMoneyOrderDetail(orderDetail.getPrice()*orderDetail.getQuantity());
//
//        orderDetailService.saveOrUpdate(orderDetail);
//        orders.getListOrderDetail().add(orderDetail);
//        return orders;
//
//    }
//}
