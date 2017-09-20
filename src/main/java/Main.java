import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {

    static String[] route(String r) {
        return r.split("-");
    }

    static String handleNoRoute(Optional<Integer> it) {
        return it.map((i) -> i.toString()).orElse("NO SUCH ROUTE");
    }

    public static void main(String [] args) {
        String graphStr = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";

        Map<String,Node> nodes = GraphBuilder.buildGraph(graphStr);

        Map<Integer,String> outputs = new HashMap<>();

        int i = 1;
        outputs.put(i++, handleNoRoute(GraphTraversal.findPathDistance(route("A-B-C"), nodes)));
        outputs.put(i++, handleNoRoute(GraphTraversal.findPathDistance(route("A-D"), nodes)));
        outputs.put(i++, handleNoRoute(GraphTraversal.findPathDistance(route("A-D-C"), nodes)));
        outputs.put(i++, handleNoRoute(GraphTraversal.findPathDistance(route("A-E-B-C-D"), nodes)));
        outputs.put(i++, handleNoRoute(GraphTraversal.findPathDistance(route("A-E-D"), nodes)));

        outputs.put(i++, String.valueOf(GraphTraversal.countNumTrips(nodes.get("C"), "C", 3, GraphTraversal.ExactOrLessThan.LESS_THAN, false)));
        outputs.put(i++, String.valueOf(GraphTraversal.countNumTrips(nodes.get("A"), "C", 4, GraphTraversal.ExactOrLessThan.EXACT, false)));

        outputs.put(i++, String.valueOf(GraphTraversal.findShortestPath(nodes.get("A"), "C").get()));
        outputs.put(i++, String.valueOf(GraphTraversal.findShortestPath(nodes.get("B"), "B").get()));

        outputs.put(i++, String.valueOf(GraphTraversal.countNumTrips(nodes.get("C"), "C", 29, GraphTraversal.ExactOrLessThan.LESS_THAN, true)));

        for (Integer j : outputs.keySet()) {
            System.out.println(String.format("Output #%d: %s", j, outputs.get(j)));
        }
    }
}
