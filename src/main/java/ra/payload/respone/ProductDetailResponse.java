package ra.payload.respone;


public class ProductDetailResponse {
    private int productDetailId;
    private int quantity;
    private String productName;
    private String colorName;
    private String colorHex;
    private String sizeName;

    public ProductDetailResponse() {
    }

    public ProductDetailResponse(int productDetailId, int quantity, String productName, String colorName, String colorHex, String sizeName) {
        this.productDetailId = productDetailId;
        this.quantity = quantity;
        this.productName = productName;
        this.colorName = colorName;
        this.colorHex = colorHex;
        this.sizeName = sizeName;
    }

    public int getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
