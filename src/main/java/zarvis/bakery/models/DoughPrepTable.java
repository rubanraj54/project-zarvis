package zarvis.bakery.models;

public class DoughPrepTable {

    private String guid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        return "DoughPrepTable{" +
                "guid='" + guid + '\'' +
                '}';
    }
}
