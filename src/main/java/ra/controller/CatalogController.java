package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Catalog;
import ra.model.service.ICatalogService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/catalog")
public class CatalogController {
    @Autowired
    private ICatalogService catalogService;

    @GetMapping
    public List<Catalog> getAllCatalog() {
        return catalogService.findAll();
    }

    @GetMapping("/{catalogId}")
    public Catalog getCatalogById(@PathVariable("catalogId") int catalogId) {
        return (Catalog) catalogService.findById(catalogId);
    }

    @PostMapping("/{catalogParentId}")
    public Catalog createCatalog(@PathVariable("catalogParentId") int catalogParentId, @RequestBody Catalog catalog) {
        Catalog catPaCreate = (Catalog) catalogService.findById(catalogParentId);
        catalog.setCatalogParentId(catPaCreate.getCatalogId());
        catalog.setCatalogParentName(catPaCreate.getCatalogName());
        return (Catalog) catalogService.saveOrUpdate(catalog);
    }

    @PutMapping("/{catalogId}/{catalogParentId}")
    public Catalog updateCatalog(@PathVariable("catalogId") int catalogId, @PathVariable("catalogParentId") int catalogParentId, @RequestBody Catalog catalog) {
        Catalog catUpdate = (Catalog) catalogService.findById(catalogId);
        Catalog catPaUpdate = (Catalog) catalogService.findById(catalogParentId);
        List<Catalog> listChild = catalogService.findCatalogChild(catalogId);
        catUpdate.setCatalogName(catalog.getCatalogName());
        catUpdate.setCatalogDescription(catalog.getCatalogDescription());
        catUpdate.setCatalogParentId(catPaUpdate.getCatalogId());
        catUpdate.setCatalogParentName(catPaUpdate.getCatalogName());
        catUpdate.setCatalogCreateDate(catalog.getCatalogCreateDate());
        catUpdate.setCatalogStatus(catalog.isCatalogStatus());
        if (listChild!=null){
            for (Catalog catChild : listChild) {
               catChild.setCatalogStatus(catalog.isCatalogStatus());
            }
        }
        return (Catalog) catalogService.saveOrUpdate(catUpdate);
    }

    @DeleteMapping("/{catalogId}")
    public void deleteCatalog(@PathVariable("catalogId") int catalogId) {
        catalogService.delete(catalogId);
    }

    @GetMapping("search")
    public List<Catalog> searchCatalog(@RequestParam("searchName") String searchName) {
        return catalogService.searchCatalog(searchName);
    }

}
