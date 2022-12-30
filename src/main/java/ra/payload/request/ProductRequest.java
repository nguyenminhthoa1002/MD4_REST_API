package ra.payload.request;

import ra.model.entity.Image;

public class ProductRequest {
    private String productName;
    private String productDescription;
    private float productImportPrice;
    private String productImg;
    private int catalogId;
    private boolean productStatus;
    private int[] colorStrArr = new int[50];
    private int[] sizeStrArr = new int[50];
    private String[] listSubImage = new String[50];


    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public float getProductImportPrice() {
        return productImportPrice;
    }

    public void setProductImportPrice(float productImportPrice) {
        this.productImportPrice = productImportPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }


    public int[] getColorStrArr() {
        return colorStrArr;
    }

    public void setColorStrArr(int[] colorStrArr) {
        this.colorStrArr = colorStrArr;
    }

    public int[] getSizeStrArr() {
        return sizeStrArr;
    }

    public void setSizeStrArr(int[] sizeStrArr) {
        this.sizeStrArr = sizeStrArr;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String[] getListSubImage() {
        return listSubImage;
    }

    public void setListSubImage(String[] listSubImage) {
        this.listSubImage = listSubImage;
    }
}
