package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import java.util.Map;
import java.util.HashMap;

/**
 * This class tests the functionality of your PageRank algorithm on a
 * directed AdjacencyMatrixGraph. The general framework of a test case is:
 * - Name the test method descriptively,
 * - Mention what is being tested (it is ok to have slightly verbose method names here)
 *
 * Some tips to keep in mind when writing test cases:
 * - All pages' ranks should total to 1.
 * - It will be easier to start out by writing test cases on smaller graphs.
 *
 */
public class MyPageRankTest {

	// This is your margin of error for testing
	double _epsilon = 0.03;

	/**
     * A simple test with four pages. Each page only has one
	 * outgoing link to a different page, resulting in a square
	 * shape or cycle when visualized. The pages' total ranks is 1.
     */
	@Test
	public void testFourEqualRanks() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");

		/**
		  * Inserting an edge with a null element since a weighted edge is not
		  * meaningful for the PageRank algorithm.
		  */

		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,c,null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(c,d,null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(d,a,null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		// Check that the number of vertices returned by PageRank is 4
		assertEquals(output.size(), 4);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}

		// The weights of each vertex should be 0.25
		double expectedRankA = 0.25;
		double expectedRankB = 0.25;
		double expectedRankC = 0.25;
		double expectedRankD = 0.25;

		// The sum of weights should always be 1
		assertEquals(total, 1, _epsilon);

		// The Rank for each vertex should be 0.25 +/- epsilon
		assertEquals(expectedRankA, output.get(a), _epsilon);
		assertEquals(expectedRankB, output.get(b), _epsilon);
		assertEquals(expectedRankC, output.get(c), _epsilon);
		assertEquals(expectedRankD, output.get(d), _epsilon);

	}

	/**
     	 * A simple test with three pages. Note that vertex A's
	 * rank is not 0 even though it has no incoming edges,
	 * demonstrating the effects of the damping factor and handling sinks.
     	 */
	@Test
	public void simpleTestOne() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,c,null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		assertEquals(output.size(), 3);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}

		// These are precomputed values
		double expectedRankA = 0.186;
		double expectedRankB = 0.342;
		double expectedRankC = 0.471;

		assertEquals(total, 1, _epsilon);
		assertEquals(expectedRankA, output.get(a), _epsilon);
		assertEquals(expectedRankB, output.get(b), _epsilon);
		assertEquals(expectedRankC, output.get(c), _epsilon);

	}

	@Test
	public void emptyTest() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		MyPageRank<String> pr = new MyPageRank<String>();
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
		assertEquals(output.size(), 0);
	}

	@Test
	public void oneNodeTest() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		MyPageRank<String> pr = new MyPageRank<String>();
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
		assertEquals(output.size(), 1);
		assertEquals(1, output.get(a), _epsilon);
	}

	@Test
	public void twoNodeTest() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Edge<String> ab = adjMatrix.insertEdge(a,b,null);
		MyPageRank<String> pr = new MyPageRank<String>();
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
		assertEquals(output.size(), 2);
		assertEquals(0.66, output.get(b), _epsilon);
		assertEquals(0.33, output.get(a), _epsilon);
	}

	@Test
	public void twoTrees() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");

		CS16Edge<String> ab = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> ad = adjMatrix.insertEdge(a,d,null);

		CS16Edge<String> ec = adjMatrix.insertEdge(e,c,null);
		CS16Edge<String> cf = adjMatrix.insertEdge(c,f,null);

		MyPageRank<String> pr = new MyPageRank<String>();
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
		assertEquals(output.size(), 6);
		assertTrue(output.get(f) > output.get(c));
		assertTrue(output.get(c) > output.get(e));

		assertEquals(output.get(b), output.get(d), _epsilon);
		assertTrue(output.get(d) > output.get(a));

		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
		assertEquals(total, 1, _epsilon);
	}

	@Test
	public void lotsOfSinks() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");

		CS16Edge<String> ab = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> ac = adjMatrix.insertEdge(a,c,null);
		CS16Edge<String> ad = adjMatrix.insertEdge(a,d,null);
		CS16Edge<String> ec = adjMatrix.insertEdge(e,c,null);
		CS16Edge<String> fb = adjMatrix.insertEdge(f,b,null);
		CS16Edge<String> fc = adjMatrix.insertEdge(f,c,null);

		MyPageRank<String> pr = new MyPageRank<String>();
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
		assertEquals(output.size(), 6);
		assertTrue(output.get(c) > output.get(b));
		assertTrue(output.get(b) > output.get(d));
		assertTrue(output.get(d) > output.get(e));
		assertEquals(output.get(f), output.get(e), _epsilon);
		assertEquals(output.get(e), output.get(a), _epsilon);

		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
		assertEquals(total, 1, _epsilon);
	}




	/**
	  * TODO: Add your own tests here. Instead of checking for specific rank values,
	  * make test cases comparing the relative ranks of pages (e.g. using an assertThat statement)!
	  */



}
