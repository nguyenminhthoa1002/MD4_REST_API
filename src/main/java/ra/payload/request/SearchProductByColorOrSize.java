package ra.payload.request;


public class SearchProductByColorOrSize {
    private int[] search = new int[50];

    public int[] getSearch() {
        return search;
    }

    public void setSearch(int[] search) {
        this.search = search;
    }
}
