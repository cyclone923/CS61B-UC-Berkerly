/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import dict.*;
import pj3.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g)
  {
	  WUGraph mstr = new WUGraph();
	  Object vertices[] = g.getVertices();
	  for(int i = 0;i < vertices.length;i++)
	  {
		  mstr.addVertex(vertices[i]);
	  }
	  Entry[] edges = g.getEdges();
	  for(int i = 0;i < edges.length;i++)
	  {
		  for(int j = 0;j < edges.length - i - 1;j++)
		  {
			  if(((Integer)edges[j].value()).intValue() > ((Integer)edges[j + 1].value()).intValue())
			  {
				  Entry temp = edges[j];
				  edges[j] = edges[j + 1];
				  edges[j + 1] = temp;
			  }
		  }
	  }
	  
	  DisjointSets indvtx = new DisjointSets(vertices.length);
	  int vertex1 = 0;
	  int vertex2 = 0;
	  int root1 = 0;
	  int root2 = 0;
	  for(int i = 0;i < edges.length;i++)
	  {
		  vertex1 = ((DFSVertex)((VertexPair)edges[i].key()).getObj1()).getnumber();
		  vertex2 = ((DFSVertex)((VertexPair)edges[i].key()).getObj2()).getnumber();
		  root1 = indvtx.find(vertex1);
		  root2 = indvtx.find(vertex2);
		  if(root1 != root2)
		  {
			  indvtx.union(root1,root2);
			  mstr.addEdge((DFSVertex)((VertexPair)edges[i].key()).getObj1(), (DFSVertex)((VertexPair)edges[i].key()).getObj2(), ((Integer)edges[i].value()).intValue());
		  }
	  }

	  return mstr;
  }

}


