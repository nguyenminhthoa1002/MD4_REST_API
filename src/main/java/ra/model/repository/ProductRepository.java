package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> searchProductByProductNameContaining(String searchName);

//    @Query(value = "select * from productDetail where productId=:searchName", nativeQuery = true)
//    List<ProductDetail> getAllProductDetailByProductId(@Param("searchName") int searchName);

//    @Query(value = "select pd.productDetailId, p.productName, c.colorName, s.sizeName, pd.productDetailStatus\n" +
//            "from productdetail pd join color c on c.colorId = pd.colorId join size s on pd.sizeId = s.sizeId join product p on p.productId = pd.productId\n" +
//            "where pd.productId = :searchName", nativeQuery = true)
//    List<ProductDetailDTO> getAllProductDetailByProductId(@Param("searchName") int searchName);
}
