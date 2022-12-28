package ra.payload.request;

import java.time.LocalDateTime;

public class CatalogUpdateRequest {
    private String catalogName;
    private String catalogDescription;
    private int catalogParentId;
    private LocalDateTime catalogCreateDate;
    private boolean catalogStatus;
    private String[] strArr = new String[50];

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogDescription() {
        return catalogDescription;
    }

    public void setCatalogDescription(String catalogDescription) {
        this.catalogDescription = catalogDescription;
    }

    public int getCatalogParentId() {
        return catalogParentId;
    }

    public void setCatalogParentId(int catalogParentId) {
        this.catalogParentId = catalogParentId;
    }

    public LocalDateTime getCatalogCreateDate() {
        return catalogCreateDate;
    }

    public void setCatalogCreateDate(LocalDateTime catalogCreateDate) {
        this.catalogCreateDate = catalogCreateDate;
    }

    public boolean isCatalogStatus() {
        return catalogStatus;
    }

    public void setCatalogStatus(boolean catalogStatus) {
        this.catalogStatus = catalogStatus;
    }

    public String[] getStrArr() {
        return strArr;
    }

    public void setStrArr(String[] strArr) {
        this.strArr = strArr;
    }
}
