package graph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.MinSpanForest;

/**
 * This class tests the functionality of your MSF algorithms on an AdjacencyMatrixGraph
 * with a 'String' type parameter for the vertices. Edge elements are Integers.
 * The general framework of a test case is: - Name the test method descriptively,
 * mentioning what is  being tested (it is ok to have slightly verbose method names here)
 * Set-up the program state (ex: instantiate a heap and insert K,V pairs into it) - Use
 * assertions to validate that the progam is in the state you expect it to be
 * See header comments over tests for what each test does
 * 
 * Before each test is run, you can assume that the '_graph' variable is reset to
 * a new instance of the a Graph<String> that you can simply use 'as is', as
 * well as the '_msf' variable.
 *
 * Of course, please do not modify anything below the 'DO NOT MODIFY ANYTHING BELOW THIS LINE'
 * line, or the above assumptions may be broken.
 */
@RunWith(Parameterized.class)
public class MsfTest {

    private String _msfClassName;
    private MinSpanForest<String> _msf;
    private Graph<String> _graph;
    
    @Test
    public void simpleTest() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 1);
        CS16Edge<String> ca = _graph.insertEdge(A, C, 10);
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

        assertThat(MSF.size(), is(2));
        assertThat(MSF.contains(ab), is(true));
        assertThat(MSF.contains(bc), is(true));
        assertThat(MSF.contains(ca), is(false));
    }

    @Test
    public void emptyGraphTest() {
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        assertThat(MSF.size(), is(0));
    }


    @Test
    public void oneVertexTest() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        assertThat(MSF.size(), is(0));
    }

    @Test
    public void oneEdgeTest() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);

        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        assertThat(MSF.size(), is(1));
    }

    @Test
    public void twoSeparateTrees() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        CS16Edge<String> ab = _graph.insertEdge(A, B, 21);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 22);
        CS16Edge<String> ca = _graph.insertEdge(A, C, 23);

        CS16Vertex<String> X = _graph.insertVertex("X");
        CS16Vertex<String> Y = _graph.insertVertex("Y");
        CS16Vertex<String> Z = _graph.insertVertex("Z");

        CS16Edge<String> xy = _graph.insertEdge(X, Y, 10);
        CS16Edge<String> yz = _graph.insertEdge(Y, Z, 11);
        CS16Edge<String> xz = _graph.insertEdge(X, Z, 12);

        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

        assertThat(MSF.size(), is(4));
        assertThat(MSF.contains(ab), is(true));
        assertThat(MSF.contains(bc), is(true));
        assertThat(MSF.contains(ca), is(false));

        assertThat(MSF.contains(xy), is(true));
        assertThat(MSF.contains(yz), is(true));
        assertThat(MSF.contains(xz), is(false));
    }

    @Test
    public void multipleValidPathsTest() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        CS16Edge<String> ab = _graph.insertEdge(A, B, 2);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _graph.insertEdge(A, C, 2);

        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);

        assertThat(MSF.size(), is(2));
        assertThat((MSF.contains(ab) && MSF.contains(ac)) || (MSF.contains(ab) && MSF.contains(bc)) ||
                (MSF.contains(ac) && MSF.contains(ab)), is(true));
    }
    

    /*
     * This is the method that, using junit magic, provides the list of MSF algorithms
     * that should be created and be tested via the methods above.
     * By default, all of the above tests will be run on MyPrimJarnik algorithm implementations.
     * If you're interested in testing the methods on just one of the
     * algorithms, comment out the one you don't want in the method below!
     */
    @Parameters(name = "with msf algo: {0}")
    public static Collection<String> msts() {
        List<String> algoNames = new ArrayList<>();
        algoNames.add("graph.MyPrimJarnik");
        return algoNames;
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
    public void setup() {
        Class<?> msfClass = null;
        try {
            msfClass = Class.forName(_msfClassName);
            Constructor<?> constructor = msfClass.getConstructors()[0];
            _msf = (MinSpanForest<String>) constructor.newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException
                | IllegalArgumentException e) {
            System.err.println("Exception while instantiating msf class " + _msfClassName + " from test.");
            e.printStackTrace();
        }
        _graph = new AdjacencyMatrixGraph<>(false);
    }

    public MsfTest(String msfClassName) {
        _msfClassName = msfClassName;
    }
}
