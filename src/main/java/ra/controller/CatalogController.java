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
import ra.payload.respone.CatalogResponse;
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

    @GetMapping("showListCatalogChild/{catalogId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Catalog> findCatChild(@PathVariable("catalogId") int catalogId) {
        return catalogService.findChildById(catalogId);
    }

    @PutMapping("/{catalogId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Catalog updateCatalog(@PathVariable("catalogId") int catalogId, @RequestBody CatalogUpdateRequest catalog) {
        Catalog catUpdate = (Catalog) catalogService.findById(catalogId);
        List<Catalog> listChild = catalogService.findChildById(catalogId);
        catUpdate.setCatalogName(catalog.getCatalogName());
        catUpdate.setCatalogDescription(catalog.getCatalogDescription());
        if (catalog.getCatalogParentId() == 0) {
            catUpdate.setCatalogParentId(0);
            catUpdate.setCatalogParentName("root");
        } else {
            Catalog catPaUpdate = (Catalog) catalogService.findById(catalog.getCatalogParentId());
            catUpdate.setCatalogParentId(catPaUpdate.getCatalogId());
            catUpdate.setCatalogParentName(catPaUpdate.getCatalogName());
        }
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
                    catChild.setCatalogParentName(catalog.getCatalogName());
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

    @GetMapping("/getCatalogForCreateProduct")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<CatalogResponse> getCatalogForCreateProduct() {
        List<Catalog> listCat = catalogService.getCatalogForCreateProduct();
        List<CatalogResponse> listCatRes = new ArrayList<>();
        for (Catalog cat : listCat) {
            CatalogResponse catRes = new CatalogResponse();
            catRes.setCatalogId(cat.getCatalogId());
            catRes.setCatalogName(cat.getCatalogName());
            listCatRes.add(catRes);
        }
        return listCatRes;
    }

    //    -------------------------- ROLE : USER --------------------
    @GetMapping("getCatalogForUser")
    @PreAuthorize("hasRole('USER')")
    public List<CatalogResponse> getCatalogForUser() {
        List<CatalogResponse> list = new ArrayList<>();
        for (Catalog cat : getAllCatalog()) {
            if (cat.isCatalogStatus()) {
                CatalogResponse catalogResponse = new CatalogResponse();
                catalogResponse.setCatalogId(cat.getCatalogId());
                catalogResponse.setCatalogName(cat.getCatalogName());
                list.add(catalogResponse);
            }
        }
        return list;
    }
}
