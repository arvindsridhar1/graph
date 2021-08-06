package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import net.datastructures.Entry;
import support.graph.CS16AdaptableHeapPriorityQueue;
import support.graph.CS16Edge;
import support.graph.CS16GraphVisualizer;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.MinSpanForest;

/**
 * In this class you will implement a slightly modified version
 * of the Prim-Jarnik algorithm for generating Minimum Spanning trees.
 * The original version of this algorithm will only generate the 
 * minimum spanning tree of the connected vertices in a graph, given
 * a starting vertex. Like Kruskal's, this algorithm can be modified to 
 * produce a minimum spanning forest with very little effort.
 *
 * See the handout for details on Prim-Jarnik's algorithm.
 * Like Kruskal's algorithm this algorithm makes extensive use of 
 * the decorator pattern, so make sure you know it.
 */
public class MyPrimJarnik<V> implements MinSpanForest<V> {

    /** 
     * This method implements Prim-Jarnik's algorithm and extends 
     * it slightly to account for disconnected graphs. You must return 
     * the collection of edges of the Minimum Spanning Forest (MSF) for 
     * the given graph, g.
     * 
     * This algorithm must run in O((|E| + |V|)log(|V|)) time
     * @param g Your graph
     * @param v Only used if you implement the optional animation.
     * @return returns a data structure that contains the edges of your MSF that implements java.util.Collection
     */

    private MyDecorator<CS16Vertex<V>, Integer> _vertexCost;
    private MyDecorator<CS16Vertex<V>, CS16Vertex<V>> _previousVertex;
    private MyDecorator<CS16Vertex<V>, Entry<Integer, CS16Vertex<V>>> _vertexEntry;
    private MyDecorator<CS16Vertex<V>, Boolean> _inPriorityQueue;


    @Override
    public Collection<CS16Edge<V>> genMinSpanForest(Graph<V> g, CS16GraphVisualizer<V> visualizer) {

        _vertexCost = new MyDecorator<CS16Vertex<V>, Integer>();
        _previousVertex = new MyDecorator<CS16Vertex<V>, CS16Vertex<V>>();
        _vertexEntry = new MyDecorator<CS16Vertex<V>, Entry<Integer, CS16Vertex<V>>>();
        _inPriorityQueue = new MyDecorator<CS16Vertex<V>, Boolean>();

        Iterator<CS16Vertex<V>> vertices = g.vertices();
        while(vertices.hasNext()){
            CS16Vertex<V> nextVertex = vertices.next();
            _vertexCost.setDecoration(nextVertex, Integer.MAX_VALUE);
            _inPriorityQueue.setDecoration(nextVertex, true);
            _previousVertex.setDecoration(nextVertex, null);
        }

        Collection<CS16Edge<V>> MST = new ArrayList<CS16Edge<V>>();

        Iterator<CS16Vertex<V>> verticesCopy = g.vertices();
        //edge case where graph is empty
        if(!verticesCopy.hasNext()){
            return MST;
        }
        CS16Vertex<V> firstVertex = verticesCopy.next();
        _vertexCost.setDecoration(firstVertex, 0);

        CS16AdaptableHeapPriorityQueue<Integer, CS16Vertex<V>> PQ = new CS16AdaptableHeapPriorityQueue<Integer, CS16Vertex<V>>();

        Iterator<CS16Vertex<V>> verticesCopy2 = g.vertices();
        while(verticesCopy2.hasNext()){
            CS16Vertex<V> nextVertex = verticesCopy2.next();
            _vertexEntry.setDecoration(nextVertex, PQ.insert(_vertexCost.getDecoration(nextVertex), nextVertex));
        }

        while(!PQ.isEmpty()){
            CS16Vertex<V> vertex = PQ.removeMin().getValue();
            _inPriorityQueue.setDecoration(vertex, false);
            if(_previousVertex.getDecoration(vertex) != null){
                MST.add(g.connectingEdge(_previousVertex.getDecoration(vertex), vertex));
            }
            Iterator<CS16Edge<V>> vertexEdges = g.outgoingEdges(vertex);
            while(vertexEdges.hasNext()){
                CS16Edge<V> nextEdge = vertexEdges.next();
                CS16Vertex<V> vertexTwo = g.opposite(vertex, nextEdge);
                if(_vertexCost.getDecoration(vertexTwo) > nextEdge.element() && _inPriorityQueue.getDecoration(vertexTwo) == true){
                    _vertexCost.setDecoration(vertexTwo, nextEdge.element());
                    _previousVertex.setDecoration(vertexTwo, vertex);

                    Entry<Integer, CS16Vertex<V>> vertexEntry = _vertexEntry.getDecoration(vertexTwo);

                    PQ.replaceKey(vertexEntry, _vertexCost.getDecoration(vertexTwo));
                }
            }
        }
        return MST;
      }
    }
