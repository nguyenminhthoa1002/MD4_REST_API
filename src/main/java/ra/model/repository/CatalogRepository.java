package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.Catalog;
import ra.payload.respone.CatalogResponse;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
    List<Catalog> searchCatalogByCatalogNameContaining(String searchName);

    @Query(value = "from Catalog c where c.catalogParentId=:catalogId")
    List<Catalog> findCatalogChild(@Param("catalogId") int catalogId);

    @Query(value = "WITH recursive TEMPDATA(catalogId,catalogName,catalogDescription,catalogParentId,catalogParentName,catalogCreateDate,catalogStatus)\n" +
            "                       AS (SELECT c.catalogId,\n" +
            "                                  c.catalogName,\n" +
            "                                  c.catalogDescription,\n" +
            "                                  c.catalogParentId,\n" +
            "                                  c.catalogParentName,\n" +
            "                                  c.catalogCreateDate,\n" +
            "                                  c.catalogStatus\n" +
            "                           FROM catalog c\n" +
            "                           WHERE catalogId = :catId\n" +
            "                           union all\n" +
            "                           select child.catalogId,child.catalogName,child.catalogDescription,child.catalogParentId,child.catalogParentName,child.catalogCreateDate,child.catalogStatus\n" +
            "                           from TEMPDATA p\n" +
            "                                    inner join catalog child on p.catalogId = child.catalogParentId)\n" +
            "    SELECT *\n" +
            "    FROM TEMPDATA where catalogId not in (:catId)",nativeQuery = true)
    List<Catalog> findChildById(@Param("catId")int catId);

    @Query(value = "select  child.catalogId,child.catalogName,child.catalogDescription,child.catalogParentId,child.catalogCreateDate,child.catalogStatus,child.catalogParentName\n" +
            "from catalog child\n" +
            "where child.catalogStatus=1 and child.catalogId not in (select p.catalogId\n" +
            "                                                        from catalog p inner join catalog c on p.catalogId=c.catalogParentId\n" +
            "                                                        group by p.catalogId )",nativeQuery = true)
    List<Catalog> getCatalogForCreateProduct();

}
