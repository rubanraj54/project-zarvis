package zarvis.bakery.models;

public class Node {

    private String name;
    private String company;
    private Location location;
    private String guid;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", location=" + location +
                ", guid='" + guid + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
