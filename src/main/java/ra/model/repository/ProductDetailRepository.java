package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ra.model.entity.ProductDetail;

import java.util.List;
import java.util.Set;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer>, PagingAndSortingRepository<ProductDetail, Integer> {
    Set<ProductDetail> findByProduct_ProductId(int proId);

    Set<ProductDetail> findByColor_ColorNameOrSize_SizeName(String colorName, String sizeName);

}
