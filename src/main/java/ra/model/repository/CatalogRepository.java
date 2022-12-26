package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.Catalog;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
    List<Catalog> searchCatalogByCatalogNameContaining(String searchName);

//    @Query(value = "select child.catalogId, child.catalogName, parent.catalogName from catalog child join catalog parent on child.catalogParentId = parent.catalogId where child.catalogParentId=:catalogId", nativeQuery = true)
    @Query(value = "from Catalog c where c.catalogParentId=:catalogId")
    List<Catalog> findCatalogChild(@Param("catalogId") int catalogId);
}
