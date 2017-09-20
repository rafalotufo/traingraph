import java.util.*;

public class GraphTraversal {

    /**
     * BFS traversal starting from `from`, stopping when
     * we find node with value `to`. We use a min heap
     * to enqueue the next nodes to be looked at as per
     * Dijkstra's algorithm.
     *
     * @param from
     * @param to
     * @return The length of the shortest route, in terms of weights of edges, from node "from" to node "to".
     *         Returns empty if no route exists.
     */
    public static Optional<Integer> findShortestPath(Node<String> from, String to) {
        if (from != null) {
            Map<String,Integer> dist = new HashMap<>();
            PriorityQueue<Node.Edge> heap = new PriorityQueue();

            for (Node.Edge edge : from.getEdges()) {
                heap.add(edge);
            }

            while (!heap.isEmpty()) {
                Node.Edge next = heap.poll();
                Node<String> node = next.getTarget();
                if (!dist.containsKey(next.getTarget().getValue())) {
                    Integer currDist = dist.getOrDefault(next.getSource().getValue(), 0);
                    dist.put(node.getValue(), currDist + next.getWeight());
                    for (Node.Edge edge : node.getEdges()) {
                        heap.add(edge);
                    }
                }

                if (node.getValue().equals(to)) return Optional.of(dist.get(node.getValue()));
            }
        }

        return Optional.empty();
    }

    /**
     * Traverses the route in the graph starting, returns the sum of the weights in
     * traversed edges.
     *
     * @param route - the route to traverse
     * @param nodes - a map of all nodes in the graph
     * @return sum of weights in the edges traversed in route or empty if route could not be found
     */
    public static Optional<Integer> findPathDistance(String[] route, Map<String,Node> nodes) {
        int dist = 0;
        Node<?> currNode = nodes.get(route[0]);

        for (int i = 1; i < route.length; i++) {
            boolean found = false;
            for (Node.Edge edge : currNode.getEdges()) {
                Node target = edge.getTarget();
                if (target.getValue().equals(route[i])) {
                    dist += edge.getWeight();
                    found = true;
                    currNode = target;
                }
            }
            if (!found) {
                return Optional.empty();
            }
        }

        return Optional.of(dist);
    }

    /**
     * Auxiliary class to help store the current
     * count or distance when in countNumTrips.
     */
    private static class Count {
        Node node;
        int count;

        Count(Node node, int count) {
            this.node = node;
            this.count = count;
        }
    }

    public enum ExactOrLessThan {
        EXACT, LESS_THAN
    }

    /**
     * Counts the number of trips starting at "start" and ending at "end" with
     * certain amount of stops or distance.
     *
     * @param start - Node to start traversal
     * @param end - Node value to end traversal
     * @param numStops - Number of stops or distance
     * @param type - LESS_THAN or EXACT numStops
     * @param useDist - if false, count number of stops, else count the distance per edge weight
     * @return
     */
    public static int countNumTrips(Node start, String end, int numStops, ExactOrLessThan type, boolean useDist) {
        List<Count> stack = new LinkedList<>();
        int numTrips = 0;

        stack.add(new Count(start, 0));
        while (!stack.isEmpty()) {
            Count next = stack.remove(stack.size() - 1);
            Node<?> node = next.node;
            int currDist = next.count;

            if (currDist < numStops) {
                for (Node.Edge edge : node.getEdges()) {
                    int incr = 1;
                    if (useDist) incr = edge.getWeight();
                    stack.add(new Count(edge.getTarget(), currDist + incr));
                }
            }

            if (currDist <= numStops) {
                if (next.node.getValue().equals(end) && currDist > 0) {
                    if (type == ExactOrLessThan.LESS_THAN) {
                        numTrips++;
                    } else if (type == ExactOrLessThan.EXACT && currDist == numStops) {
                        numTrips++;
                    }
                }
            }
        }

        return numTrips;
    }
}
