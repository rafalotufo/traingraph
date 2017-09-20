import java.util.HashMap;
import java.util.Map;

public class GraphBuilder {

    public static Map<String,Node> buildGraph(String graphSpec) {
        return buildGraph(graphSpec.split(",? "));
    }

    private static class ParsedEdge {
        String from;
        String to;
        int weight;

        public ParsedEdge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public static Map<String,Node> buildGraph(String [] edgeSpecs) {
        Map<String,Node> nodeMap = new HashMap<>();

        for (String edgeSpec : edgeSpecs) {
            if (edgeSpec.isEmpty()) {
                continue;
            }
            ParsedEdge parsedEdge = parseEdge(edgeSpec);
            Node fromNode = getNode(parsedEdge.from, nodeMap);
            Node toNode = getNode(parsedEdge.to, nodeMap);

            fromNode.addEdge(fromNode, toNode, parsedEdge.weight);
        }

        return nodeMap;
    }

    private static ParsedEdge parseEdge(String edgeSpec) {
        String from = edgeSpec.substring(0, 1);
        String to = edgeSpec.substring(1, 2);
        int weight = Integer.parseInt(edgeSpec.substring(2, 3));

        return new ParsedEdge(from, to, weight);
    }

    private static Node<String> getNode(String name, Map<String,Node> nodeMap) {
        Node node = nodeMap.get(name);
        if (node == null) {
            node = new Node(name);
            nodeMap.put(name, node);
        }
        return node;
    }
}
