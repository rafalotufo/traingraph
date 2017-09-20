import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

public class GraphTraversalTest {

    @Test
    public void testEmptyGraph() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("");

        Assert.assertEquals(Optional.empty(), GraphTraversal.findShortestPath(null, "B"));
    }

    @Test
    public void testFindShortestPath1() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB1");

        Assert.assertEquals(1, GraphTraversal.findShortestPath(nodes.get("A"), "B").get());
    }

    @Test
    public void testFindShortestPathLoop() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB1, BA1");

        Assert.assertEquals(1, GraphTraversal.findShortestPath(nodes.get("A"), "B").get());
    }

    @Test
    public void testFindShortestPath2() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB1, BC2");

        Assert.assertEquals(3, GraphTraversal.findShortestPath(nodes.get("A"), "C").get());
    }

    @Test
    public void testFindShortestPath3() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB2, BC3, AC1");

        Assert.assertEquals(1, GraphTraversal.findShortestPath(nodes.get("A"), "C").get());
    }

    @Test
    public void testFindShortestPath() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

        Assert.assertEquals(9, GraphTraversal.findShortestPath(nodes.get("A"), "C").get());
        Assert.assertEquals(9, GraphTraversal.findShortestPath(nodes.get("B"), "B").get());
    }

    @Test
    public void testFindPathDistance1() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB1");

        Assert.assertEquals(1, GraphTraversal.findPathDistance(new String[]{"A", "B"}, nodes).get().intValue());
    }

    @Test
    public void testFindPathDistance2() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB1, AC4, BC2");

        Assert.assertEquals(3, GraphTraversal.findPathDistance(new String[]{"A", "B", "C"}, nodes).get().intValue());
    }

    @Test
    public void testFindPathDistance3() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

        Assert.assertEquals(9, GraphTraversal.findPathDistance(new String[]{"A", "B", "C"}, nodes).get().intValue());
        Assert.assertEquals(5, GraphTraversal.findPathDistance(new String[]{"A", "D"}, nodes).get().intValue());
        Assert.assertEquals(13, GraphTraversal.findPathDistance(new String[]{"A", "D", "C"}, nodes).get().intValue());
        Assert.assertEquals(22, GraphTraversal.findPathDistance(new String[]{"A", "E", "B", "C", "D"}, nodes).get().intValue());
        Assert.assertEquals(Optional.empty(), GraphTraversal.findPathDistance(new String[]{"A", "E", "D"}, nodes));
    }


    @Test
    public void testCountNumTrips0() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB1, CD1");

        Assert.assertEquals(0, GraphTraversal.countNumTrips(nodes.get("A"), "C", 1, GraphTraversal.ExactOrLessThan.LESS_THAN, false));
    }

    @Test
    public void testCountNumTrips() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB1");

        Assert.assertEquals(1, GraphTraversal.countNumTrips(nodes.get("A"), "B", 1, GraphTraversal.ExactOrLessThan.LESS_THAN, false));
        Assert.assertEquals(1, GraphTraversal.countNumTrips(nodes.get("A"), "B", 2, GraphTraversal.ExactOrLessThan.LESS_THAN, false));
    }

    @Test
    public void testCountNumTrips2() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB1, BC1, CA1");

        Assert.assertEquals(1, GraphTraversal.countNumTrips(nodes.get("A"), "B", 1, GraphTraversal.ExactOrLessThan.LESS_THAN, false));
        Assert.assertEquals(1, GraphTraversal.countNumTrips(nodes.get("A"), "B", 2, GraphTraversal.ExactOrLessThan.LESS_THAN, false));
        Assert.assertEquals(1, GraphTraversal.countNumTrips(nodes.get("A"), "B", 3, GraphTraversal.ExactOrLessThan.LESS_THAN, false));
        Assert.assertEquals(2, GraphTraversal.countNumTrips(nodes.get("A"), "B", 4, GraphTraversal.ExactOrLessThan.LESS_THAN, false));
    }

    @Test
    public void testCountNumTrips3() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

        Assert.assertEquals(2, GraphTraversal.countNumTrips(nodes.get("C"), "C", 3, GraphTraversal.ExactOrLessThan.LESS_THAN, false));
        Assert.assertEquals(3, GraphTraversal.countNumTrips(nodes.get("A"), "C", 4, GraphTraversal.ExactOrLessThan.EXACT, false));
    }

    @Test
    public void testCountNumTrips4() {
        Map<String,Node> nodes = GraphBuilder.buildGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

        Assert.assertEquals(7, GraphTraversal.countNumTrips(nodes.get("C"), "C", 29, GraphTraversal.ExactOrLessThan.LESS_THAN, true));
    }
}
