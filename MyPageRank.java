package graph;

import java.util.*;

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
	private List<CS16Vertex<V>> _graphSinks;
	private List<Integer> _outgoingEdges;
	private List<Double> _previousPageRank;
	private List<Double> _currentPageRank;
	private double _numRounds = 0;
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
		_g = g;
		_vertices = new ArrayList<>();
		_vertsToRanks = new HashMap<>();
		_previousPageRank = new ArrayList<>();
		_currentPageRank = new ArrayList<>();
		_graphSinks = new ArrayList<>();
		_outgoingEdges = new ArrayList<>();

		Iterator<CS16Vertex<V>> graphVertices = g.vertices();
		while(graphVertices.hasNext()){
			CS16Vertex<V> next = graphVertices.next();
			_vertices.add(next);

			int vertexOutgoingEdges = g.numOutgoingEdges(next);
			_outgoingEdges.add(vertexOutgoingEdges);
			if(vertexOutgoingEdges == 0){
				_graphSinks.add(next);
			}

			double dividedRank = 1.0 / g.getNumVertices();
			_currentPageRank.add(dividedRank);
		}

		int numVertices = _vertices.size();

		do{
			this.currIntoPrev(numVertices);
			this.handleSinks(numVertices);
			this.rankUpdater(numVertices);
			_numRounds ++;
		} while(!checkForStoppage(numVertices));

		for(int i = 0; i < numVertices; i++){
			_vertsToRanks.put(_vertices.get(i), _currentPageRank.get(i));
		}

		return _vertsToRanks;
	}

	/**
	 * Method used to account for sink pages (those with no outgoing
	 * edges). There are multiple ways you can implement this, check
	 * the lecture and help slides!
	 */

	private void currIntoPrev(int numVertices){
		for(int i= 0; i < numVertices; i++){
			_previousPageRank.add(i, _currentPageRank.get(i));
		}
	}


	private void handleSinks(int numVertices) {
		double sinkSum = 0;
		for (int i = 0; i < numVertices; i++){
			double previousRank = _previousPageRank.get(i);
			if(_outgoingEdges.get(i) == 0){
				sinkSum += previousRank / numVertices;
			}
		}
		for(int i = 0; i < numVertices; i++){
			_currentPageRank.set(i, sinkSum);
		}
	}

	private boolean checkForStoppage(int numVertices){
		for (int i = 0; i < numVertices; i++){
			if(_numRounds > _maxIterations || Math.abs(_currentPageRank.get(i) - _previousPageRank.get(i)) > _error){
				return false;
			}
		}
		_numRounds ++;
		return true;
	}

	private void rankUpdater(int numVertices){
		for(int i = 0; i < numVertices; i++) {
			CS16Vertex<V> vertex = _vertices.get(i);
			Iterator<CS16Edge<V>> incomingEdges = _g.incomingEdges(vertex);

			while(incomingEdges.hasNext()){
				CS16Edge<V> edge = incomingEdges.next();
				CS16Vertex<V> oppositeVertex = _g.opposite(vertex, edge);
				this.rankUpdaterHelper(vertex, oppositeVertex);
			}

			double dampingDiluted = (1-_dampingFactor) / (numVertices);
			double currentPageRank = _currentPageRank.get(_vertices.indexOf(vertex));
			double dampingAccounted = (_dampingFactor * currentPageRank);
			double updatedRank = dampingDiluted + dampingAccounted;
			_currentPageRank.set(i, updatedRank);
		}
	}

	private void rankUpdaterHelper(CS16Vertex<V> vertex, CS16Vertex<V> oppositeVertex){
		double currentRank = _currentPageRank.get(_vertices.indexOf(vertex));
		double oppositeIndex = _previousPageRank.get(_vertices.indexOf(oppositeVertex));
		double previousOutgoingEdges = _outgoingEdges.get(_vertices.indexOf(oppositeVertex));
		double previousRankDiluted = oppositeIndex/previousOutgoingEdges;
		double rankMath = currentRank + previousRankDiluted;
		_currentPageRank.set(_vertices.indexOf(vertex), rankMath);
	}

}
