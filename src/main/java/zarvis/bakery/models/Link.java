package zarvis.bakery.models;

public class Link {

    private String source;
    private float dist;
    private String guid;
    private String target;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Link{" +
                "source='" + source + '\'' +
                ", dist=" + dist +
                ", guid='" + guid + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
