package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.Color;
import ra.model.entity.Product;
import ra.model.entity.Size;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> searchProductByProductNameContaining(String searchName);
    List<Product> searchProductByProductExportPriceBetween(float min, float max);

    @Query(value = "select * from product where catalogId=:catId",nativeQuery = true)
    List<Product> getAllProductByCatalogId(@Param("catId") int catId);

    @Query(value = "select * from product where productStatus = 1",nativeQuery = true)
    List<Product> displayProduct();


//    @Query(value = "select * from productDetail where productId=:searchName", nativeQuery = true)
//    List<ProductDetail> getAllProductDetailByProductId(@Param("searchName") int searchName);

//    @Query(value = "select pd.productDetailId, p.productName, c.colorName, s.sizeName, pd.productDetailStatus\n" +
//            "from productdetail pd join color c on c.colorId = pd.colorId join size s on pd.sizeId = s.sizeId join product p on p.productId = pd.productId\n" +
//            "where pd.productId = :searchName", nativeQuery = true)
//    List<ProductDetailDTO> getAllProductDetailByProductId(@Param("searchName") int searchName);

//    List<Product> findByListColorOrListSize(Set<Color> listColor, Set<Size> listSizes);

    List<Product> findByListColor(Set<Color> listColor);
}
