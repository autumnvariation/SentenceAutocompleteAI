import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Node {
    String node;
    HashMap<Node, Integer> edges = new HashMap<>();
    public String getNode() {
        return node;
    }

    public Node(String node) {
        this.node = node;
    }
    public void addEdgeNode(Node node){
        if (!edges.containsKey(node)){
            edges.put(node, 0);
        }
    }
    public void incrementEdgeNode(Node nextNode) {
        edges.put(nextNode, edges.get(nextNode) + 1);
    }
    public String getClosestNode(){
        ArrayList<Node> nodes = new ArrayList<>(edges.keySet());
        Node closestNode = null;
        Integer closestNodeInt = 0;
        for (Node node : nodes) {
            if (edges.get(node) > closestNodeInt){
                closestNode = node;
                closestNodeInt = edges.get(node);
            }
        }
        return closestNode.getNode();
    }
    public String getRandomNextWord(){
        ArrayList<String> nodes = new ArrayList<>();
        for (Node node : edges.keySet()) {
            for (int i = 0; i < edges.get(node); i++) {
                nodes.add(node.getNode());
            }
        }

        int randomIndex = new Random().nextInt(0,nodes.size());
        return nodes.get(randomIndex);
    }
}
