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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.InvalidEdgeException;
import support.graph.InvalidVertexException;
import support.graph.NoSuchEdgeException;
import support.graph.NoSuchVertexException;

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
