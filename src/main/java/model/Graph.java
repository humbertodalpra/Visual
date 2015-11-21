package model;

import java.util.ArrayList;

public class Graph {

    private ArrayList<Node> nodes;
    private ArrayList<Link> links;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void addLinks(Node source, ArrayList<Node> targets) {
        int size = nodes.size();
        for (int i = 0; i < size; i++) {
            if (nodes.get(i).sameAs(source)) {
                for (Node target : targets) {
                    for (int j = 0; j < size; j++) {
                        if (nodes.get(j).sameAs(target)) {
                            links.add(new Link(i, j));
                        }
                    }
                }
            }
        }
    }
}