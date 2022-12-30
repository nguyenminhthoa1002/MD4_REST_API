package ra.payload.request;

public class SearchByPriceColorSize {
    private int[] listsize;
    private int[] listColor;
    private float min;
    private float max;

    public int[] getListsize() {
        return listsize;
    }

    public void setListsize(int[] listsize) {
        this.listsize = listsize;
    }

    public int[] getListColor() {
        return listColor;
    }

    public void setListColor(int[] listColor) {
        this.listColor = listColor;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }
}
