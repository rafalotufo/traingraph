import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Node<T> {
    private T value;
    private List<Edge<T>> edges = new LinkedList<>();

    public T getValue() {
        return value;
    }

    public Node(T value) {
        this.value = value;
    }

    public class Edge<T> implements Comparable {
        private int weight;
        private Node<T> target;
        private Node<T> source;

        @Override
        public int compareTo(Object o) {
            Edge e1 = this;
            Edge e2 = (Edge) o;

            return new Integer(e1.getWeight()).compareTo(e1.getWeight());
        }

        Edge(Node<T> source, Node<T> target, int weight) {
            this.source = source;
            this.weight = weight;
            this.target = target;
        }

        public int getWeight() {
            return weight;
        }

        public Node<T> getSource() {
            return source;
        }

        public Node<T> getTarget() {
            return target;
        }

    }

    public void addEdge(Node<T> source, Node<T> target, int weight) {
        edges.add(new Edge(source, target, weight));
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }
}
