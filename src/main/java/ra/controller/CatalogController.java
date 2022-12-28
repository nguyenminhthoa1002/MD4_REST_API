package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Catalog;
import ra.model.entity.Product;
import ra.model.service.ICatalogService;
import ra.payload.request.CatalogRequest;
import ra.payload.request.CatalogUpdateRequest;
import ra.payload.respone.MessageResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/catalog")
public class CatalogController {
    @Autowired
    private ICatalogService catalogService;

    //    -------------------------- ROLE : ADMIN & MODERATOR --------------------
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Catalog> getAllCatalog() {
        return catalogService.findAll();
    }

    @GetMapping("/{catalogId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Catalog getCatalogById(@PathVariable("catalogId") int catalogId) {
        return (Catalog) catalogService.findById(catalogId);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Catalog createCatalog(@RequestBody CatalogRequest catalog) {
        Catalog catPaCreate = (Catalog) catalogService.findById(catalog.getCatalogParentId());
        Catalog catNew = new Catalog();
        catNew.setCatalogName(catalog.getCatalogName());
        catNew.setCatalogDescription(catalog.getCatalogDescription());
        catNew.setCatalogParentId(catPaCreate.getCatalogId());
        catNew.setCatalogParentName(catPaCreate.getCatalogName());
        LocalDateTime time = LocalDateTime.now();
        catNew.setCatalogCreateDate(time);
        catNew.setCatalogStatus(true);
        Set<Product> list = new HashSet<>();
        catNew.setListProduct(list);
        return (Catalog) catalogService.saveOrUpdate(catNew);
    }

    @PutMapping("/{catalogId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Catalog updateCatalog(@PathVariable("catalogId") int catalogId, @RequestBody CatalogUpdateRequest catalog) {
        Catalog catUpdate = (Catalog) catalogService.findById(catalogId);
        List<Catalog> listChild = catalogService.findChildById(catalogId);
        Catalog catPaUpdate = (Catalog) catalogService.findById(catalog.getCatalogParentId());
        catUpdate.setCatalogName(catalog.getCatalogName());
        catUpdate.setCatalogDescription(catalog.getCatalogDescription());
        catUpdate.setCatalogParentId(catPaUpdate.getCatalogId());
        catUpdate.setCatalogParentName(catPaUpdate.getCatalogName());
        LocalDateTime time = LocalDateTime.now();
        catUpdate.setCatalogCreateDate(time);
        catUpdate.setCatalogStatus(catalog.isCatalogStatus());
        List<Catalog> listChildUpdate = new ArrayList<>();
        if (listChild != null) {
            if (catalog.isCatalogStatus()) {
                for (Catalog cat : getAllCatalog()) {
                    for (String str : catalog.getStrArr()) {
                        if (cat.getCatalogId() == Integer.parseInt(str)) {
                            listChildUpdate.add(getCatalogById(Integer.parseInt(str)));
                        }
                    }
                }
                for (Catalog catChild : listChildUpdate) {
                    catChild.setCatalogStatus(true);
                    catalogService.saveOrUpdate(catChild);
                }
            } else {
                for (Catalog catChild : listChild) {
                    catChild.setCatalogStatus(false);
                    catalogService.saveOrUpdate(catChild);
                }
            }
        }
        return (Catalog) catalogService.saveOrUpdate(catUpdate);
    }

    @DeleteMapping("/{catalogId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void deleteCatalog(@PathVariable("catalogId") int catalogId) {
        catalogService.delete(catalogId);
        List<Catalog> listChild = catalogService.findChildById(catalogId);
        if (listChild != null) {
            for (Catalog catChild : listChild) {
                catChild.setCatalogStatus(false);
                catalogService.saveOrUpdate(catChild);
            }
        }
    }

    @GetMapping("search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Catalog> searchCatalog(@RequestParam("searchName") String searchName) {
        return catalogService.searchCatalog(searchName);
    }

    //    -------------------------- ROLE : USER --------------------

}
