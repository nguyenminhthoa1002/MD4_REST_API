package ra.payload.request;

public class SearchProductByColorOrSize {
    private String[] search = new String[50];

    public String[] getSearch() {
        return search;
    }

    public void setSearch(String[] search) {
        this.search = search;
    }
}
