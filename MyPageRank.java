package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.PageRank;

/**
 * In this class you will implement one of many different versions
 * of the PageRank algorithm. This algorithm will only work on
 * directed graphs. Keep in mind that there are many different ways
 * to handle sinks.
 *
 * Make sure you review the help slides and handout for details on
 * the PageRank algorithm.
 *
 */
public class MyPageRank<V> implements PageRank<V> {
	private Graph<V> _g;
	private List<CS16Vertex<V>> _vertices;
	private Map<CS16Vertex<V>, Double> _vertsToRanks;
	private static final double _dampingFactor = 0.85;
	private static final int _maxIterations = 100;
	private static final double _error = 0.01;

	/**
	 * TODO: Feel free to add in anything else necessary to store the information
	 * needed to calculate the rank. Maybe make something to keep track of sinks,
	 * your ranks, and your outgoing edges?
	 */

	/**
	 * The main method that does the calculations! You'll want to call the methods
	 * that initialize your variables here. You'll also want to decide on a
	 * type of loop - for loop, do while, or while loop - for your calculations.
	 *
	 * @return A Map of every Vertex to its corresponding rank
	 *
	 */
	@Override
	public Map<CS16Vertex<V>, Double> calcPageRank(Graph<V> g) {
		// TODO: initialize private instance variables
		return null;
	}

	/**
	 * Method used to account for sink pages (those with no outgoing
	 * edges). There are multiple ways you can implement this, check
	 * the lecture and help slides!
	 */
	private void handleSinks() {
		// TODO: Fill this in
	}

	// Feel free to add helper methods below.


}
