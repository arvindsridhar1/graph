package graph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static support.graph.Constants.MAX_VERTICES;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import heap.IntegerComparator;
import heap.MyHeap;
import net.datastructures.InvalidKeyException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import support.graph.*;

/**
 * This class tests the functionality of a Graph based on a 'String' type
 * parameter for the vertices. Edge elements are Integers. The general framework
 * of a test case is: - Name the test method descriptively, mentioning what is
 * being tested (it is ok to have slightly verbose method names here) - Set-up
 * the program state (ex: instantiate a heap and insert K,V pairs into it) - Use
 * assertions to validate that the program is in the state you expect it to be
 * See header comments over tests for what each test does
 * 
 * Before each test is run, you can assume that the '_graph' variable is reset to
 * a new instance of the a Graph<String> that you can simply use 'as is'
 * via the methods under the 'DO NOT MODIFY ANYTHING BELOW THIS LINE' line.
 * Of course, please do not modify anything below that, or the above 
 * assumptions may be broken.
 */
@RunWith(Parameterized.class)
public class GraphTest {
    

    // Undirected Graph instance variable
    private Graph<String> _graph;
    // Directed Graph instance variable
    private Graph<String> _dirGraph;
    private String _graphClassName;
	
    /**
     * A simple test for insertVertex, that adds 3 vertices and then checks to
     * make sure they were added by accessing them through the vertices
     * iterator.
     */
    @Test(timeout = 10000)
    public void testInsertVertex() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        // use the vertex iterator to get a list of the vertices in the actual
        // graph
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _graph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }

        // assert that the graph state is consistent with what you expect
        assertThat(actualVertices.size(), is(3));
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
    }

    // Same test as above, but with a directed graph
    @Test(timeout = 10000)
    public void testInsertVertexDirected() {
        // insert vertices
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        // use the vertex iterator to get a list of the vertices in the actual
        // graph
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _dirGraph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }

        // assert that the graph state is consistent with what you expect
        assertThat(actualVertices.size(), is(3));
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
    }


    /**
     * A simple test for insertEdges that adds 3 vertices, adds two edges to the
     * graph and then asserts that both edges were in fact added using the edge
     * iterator as well as checks to make sure their from and to vertices were
     * set correctly.
     */
    @Test(timeout = 10000)
    public void testInsertEdges() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);

        // use the edge iterator to get a list of the edges in the actual graph.
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _graph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(2));
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
    }


    // Same test as above, but with a directed graph
    @Test(timeout = 10000)
    public void testInsertEdgesDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);

        // use the edge iterator to get a list of the edges in the actual graph.
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _dirGraph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(2));
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
    }

    @Test(expected = InvalidVertexException.class)
    public void insertEdgeExceptionTest(){
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> nullB = _graph.insertEdge(null, B, 1);
    }

    @Test(expected = InvalidVertexException.class)
    public void removeVertexExceptionTest(){
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        _graph.removeVertex(null);
    }

    @Test(expected = InvalidEdgeException.class)
    public void removeEdgeExceptionTest(){
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        _graph.removeEdge(null);
    }

    @Test(expected = InvalidVertexException.class)
    public void connectingEdgeVertexExceptionTest(){
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        _graph.connectingEdge(A, null);
    }

    @Test(expected = NoSuchEdgeException.class)
    public void connectingEdgeEdgeExceptionTest(){
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        _graph.connectingEdge(B, C);
    }

    @Test(expected = InvalidVertexException.class)
    public void incomingEdgesExceptionTest(){
        _graph.incomingEdges(null);
    }

    @Test(expected = InvalidVertexException.class)
    public void outgoingEdgesExceptionTest(){
        _graph.outgoingEdges(null);
    }

    @Test(expected = InvalidVertexException.class)
    public void numOutgoingEdgesExceptionTest(){
        _graph.numOutgoingEdges(null);
    }


    @Test(expected = DirectionException.class)
    public void numOutgoingEdgesDirectionExceptionTest(){
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        _graph.numOutgoingEdges(A);
    }


    @Test(expected = InvalidVertexException.class)
    public void oppositeVertException(){
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        _graph.opposite(null, ab);
    }

    @Test(expected = InvalidEdgeException.class)
    public void oppositeEdgeException(){
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        _graph.opposite(A, null);
    }

    @Test(expected = NoSuchVertexException.class)
    public void oppositeNoVertexException(){
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        _graph.opposite(C, ab);
    }

    @Test(expected = InvalidEdgeException.class)
    public void endVerticesException(){
        _graph.endVertices(null);
    }

    @Test(expected = InvalidVertexException.class)
    public void areAdjacentException(){
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        _graph.areAdjacent(null, B);
    }

    @Test(timeout = 10000)
    public void testRemoveVertex() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 1);


        _graph.removeVertex(C);

        // use the vertex iterator to get a list of the vertices in the actual
        // graph
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _graph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }

        List<CS16Edge<String>> edges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> edgeIterator = _graph.edges();
        while(edgeIterator.hasNext()){
            edges.add(edgeIterator.next());
        }


        // assert that the graph state is consistent with what you expect
        assertThat(actualVertices.size(), is(2));
        assertThat(edges.size(), is(1));
        assertThat(actualVertices.contains(A), is(true));
        assertThat(edges.contains(ab), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(false));
        assertThat(edges.contains(bc), is(false));
    }

    @Test(timeout = 10000)
    public void testAreAdjacent() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 1);

        assertThat(_graph.areAdjacent(A, B), is(true));
        assertThat(_graph.areAdjacent(B, C), is(true));
        assertThat(_graph.areAdjacent(C, B), is(true));
        assertThat(_graph.areAdjacent(B, A), is(true));
        assertThat(_graph.areAdjacent(A, C), is(false));
        assertThat(_graph.areAdjacent(C, A), is(false));


    }

    @Test(timeout = 10000)
    public void testAreAdjacentDirected() {
        // insert vertices
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 1);

        assertThat(_dirGraph.areAdjacent(A, B), is(true));
        assertThat(_dirGraph.areAdjacent(B, C), is(true));
        assertThat(_dirGraph.areAdjacent(C, B), is(false));
        assertThat(_dirGraph.areAdjacent(B, A), is(false));
        assertThat(_dirGraph.areAdjacent(A, C), is(false));
        assertThat(_dirGraph.areAdjacent(C, A), is(false));
    }

    @Test(timeout = 10000)
    public void testConnectingEdge() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 1);

        assertThat(_graph.connectingEdge(A, B), is(ab));
        assertThat(_graph.connectingEdge(B, C), is(bc));
    }

    @Test(timeout = 10000)
    public void incomingEdges() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Vertex<String> E = _graph.insertVertex("E");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> cb = _graph.insertEdge(C, B, 1);
        CS16Edge<String> db = _graph.insertEdge(D, B, 1);
        CS16Edge<String> eb = _graph.insertEdge(E, B, 1);
        CS16Edge<String> ac = _graph.insertEdge(A, C, 1);
        CS16Edge<String> de = _graph.insertEdge(D, E, 1);

        List<CS16Edge<String>> edges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> edgeIterator = _graph.incomingEdges(B);
        while(edgeIterator.hasNext()){
            edges.add(edgeIterator.next());
        }

        assertThat(edges.contains(ab), is(true));
        assertThat(edges.contains(cb), is(true));
        assertThat(edges.contains(db), is(true));
        assertThat(edges.contains(eb), is(true));
        assertThat(edges.contains(ac), is(false));
        assertThat(edges.contains(de), is(false));
    }

    @Test(timeout = 10000)
    public void incomingEdgesDirected() {
        // insert vertices
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("D");
        CS16Vertex<String> E = _dirGraph.insertVertex("E");
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> cb = _dirGraph.insertEdge(C, B, 1);
        CS16Edge<String> bd = _dirGraph.insertEdge(B, D, 1);
        CS16Edge<String> be = _dirGraph.insertEdge(B, E, 1);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 1);
        CS16Edge<String> de = _dirGraph.insertEdge(D, E, 1);

        List<CS16Edge<String>> edges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> edgeIterator = _dirGraph.incomingEdges(B);
        while(edgeIterator.hasNext()){
            edges.add(edgeIterator.next());
        }

        assertThat(edges.contains(ab), is(true));
        assertThat(edges.contains(cb), is(true));
        assertThat(edges.contains(bd), is(false));
        assertThat(edges.contains(be), is(false));
        assertThat(edges.contains(ac), is(false));
        assertThat(edges.contains(de), is(false));
    }

    @Test(timeout = 10000)
    public void outgoingEdges() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Vertex<String> E = _graph.insertVertex("E");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> cb = _graph.insertEdge(C, B, 1);
        CS16Edge<String> bd = _graph.insertEdge(B, D, 1);
        CS16Edge<String> be = _graph.insertEdge(B, E, 1);
        CS16Edge<String> ac = _graph.insertEdge(A, C, 1);
        CS16Edge<String> de = _graph.insertEdge(D, E, 1);

        List<CS16Edge<String>> edges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> edgeIterator = _graph.outgoingEdges(B);
        while(edgeIterator.hasNext()){
            edges.add(edgeIterator.next());
        }

        assertThat(edges.contains(ab), is(true));
        assertThat(edges.contains(cb), is(true));
        assertThat(edges.contains(bd), is(true));
        assertThat(edges.contains(be), is(true));
        assertThat(edges.contains(ac), is(false));
        assertThat(edges.contains(de), is(false));
    }

    @Test(timeout = 10000)
    public void outgoingEdgesDirected() {
        // insert vertices
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("D");
        CS16Vertex<String> E = _dirGraph.insertVertex("E");
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> cb = _dirGraph.insertEdge(C, B, 1);
        CS16Edge<String> bd = _dirGraph.insertEdge(B, D, 1);
        CS16Edge<String> be = _dirGraph.insertEdge(B, E, 1);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 1);
        CS16Edge<String> de = _dirGraph.insertEdge(D, E, 1);

        List<CS16Edge<String>> edges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> edgeIterator = _dirGraph.outgoingEdges(B);
        while(edgeIterator.hasNext()){
            edges.add(edgeIterator.next());
        }

        assertThat(edges.contains(ab), is(false));
        assertThat(edges.contains(cb), is(false));
        assertThat(edges.contains(bd), is(true));
        assertThat(edges.contains(be), is(true));
        assertThat(edges.contains(ac), is(false));
        assertThat(edges.contains(de), is(false));
        assertThat(_dirGraph.numOutgoingEdges(B), is(2));
    }

    @Test(timeout = 10000)
    public void testOpposite() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 1);

        assertThat(_graph.opposite(A, ab), is(B));
        assertThat(_graph.opposite(B, ab), is(A));
        assertThat(_graph.opposite(B, bc), is(C));
        assertThat(_graph.opposite(C, bc), is(B));
    }

    @Test(timeout = 10000)
    public void testEndVertices() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);

        assertThat(_graph.endVertices(ab).contains(A), is(true));
        assertThat(_graph.endVertices(ab).contains(B), is(true));
        assertThat(_graph.endVertices(ab).contains(C), is(false));
    }

    @Test(timeout = 10000)
    public void testClear() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);

        _graph.clear();

        List<CS16Edge<String>> edges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> edgeIterator = _graph.edges();
        while(edgeIterator.hasNext()){
            edges.add(edgeIterator.next());
        }

        assertThat(_graph.getNumVertices() == 0, is(true));
        assertThat(edges.size() == 0, is(true));
    }

    @Test(timeout = 10000)
    public void numVerticesTest() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Vertex<String> E = _graph.insertVertex("E");

        assertThat(_graph.getNumVertices() == 5, is(true));
    }












    /*
     * List of graphs for testing!
     */
    @Parameters(name = "with graph: {0}")
    public static Collection<String> graphs() {
        List<String> names = new ArrayList<>();
        names.add("graph.AdjacencyMatrixGraph");
        return names;
    }
    
    /*
     * ####################################################
     * 
     * DO NOT MODIFY ANYTHING BELOW THIS LINE
     * 
     * ####################################################
     */
	
	
    @SuppressWarnings("unchecked")
    @Before
	public void makeGraph() {
        Class<?> graphClass = null;
        try {
            graphClass = Class.forName(_graphClassName);
            Constructor<?> constructor = graphClass.getConstructors()[0];
            _graph = (Graph<String>) constructor.newInstance(false);
	    _dirGraph = (Graph<String>) constructor.newInstance(true);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
            System.err.println("Exception while instantiating Graph class in GraphTest.");
            e.printStackTrace();
        }
	}
	
    public GraphTest(String graphClassName) {
	    this._graphClassName = graphClassName;
	}
}
