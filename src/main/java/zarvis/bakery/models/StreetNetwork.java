package zarvis.bakery.models;

import java.util.List;

public class StreetNetwork {

    private boolean directed;
    private List<Node> nodes;
    private List<Link> links;

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "StreetNetwork{" +
                "directed=" + directed +
                ", nodes=" + nodes +
                ", links=" + links +
                '}';
    }
}
