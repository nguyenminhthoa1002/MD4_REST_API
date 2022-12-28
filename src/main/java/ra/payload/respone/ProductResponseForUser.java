package ra.payload.respone;

public class ProductResponseForUser {
    private int productId;
    private String productName;
    private float productExportPrice;
    private String productImg;

    public ProductResponseForUser() {
    }

    public ProductResponseForUser(int productId, String productName, float productExportPrice, String productImg) {
        this.productId = productId;
        this.productName = productName;
        this.productExportPrice = productExportPrice;
        this.productImg = productImg;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductExportPrice() {
        return productExportPrice;
    }

    public void setProductExportPrice(float productExportPrice) {
        this.productExportPrice = productExportPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
}
