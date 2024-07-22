import java.io.*;
import java.util.*;

public class GraphLL
{
	private final int NO_EDGE = -1; // all real edges are positive
	private  Edge[] G;              // every G[i] is the head of a linked list, i.e ref to an Edge 

	private int numEdges;
	public GraphLL( String graphFileName ) throws Exception  // since readFild doesn't handle them either
	{
		loadGraphFile( graphFileName );
	}

	///////////////////////////////////// LOAD GRAPH FILE //////////////////////////////////////////

	private void loadGraphFile( String graphFileName ) throws Exception
	{
		Scanner graphFile = new Scanner( new File( graphFileName ) );
		int numNodes = graphFile.nextInt();   
		G = new Edge[numNodes];
		numEdges=0;

		// NOW LOOP THRU THE FILE READING EACH TRIPLET row col weight FROM THE LINE
		// DO an insertAtFront using g[SRC] as the head 

		while ( graphFile.hasNextInt() )
		{	
            addEdge(graphFile.nextInt(), graphFile.nextInt(), graphFile.nextInt());
		}
	} // END readGraphFile

	// GO TO G[src] AND DO INSERTATFRONT ON THAT 'head' POINTER I.E. G[src]
	private void addEdge( int src, int dest, int weight )
	{
        Edge head = G[src];
		G[src] = new Edge(dest, weight, head);
		++numEdges;
	}
	
	private boolean hasEdge(int src, int dest)
	{
		return false; // CHANGE/REMOVE/MOVE AS NEEDED
		// GOTO G[src] AND WALK THAT LINKED LIST LOOKING FOR A NODE (EDGE) WITH DEST
	}

	private int inDegree(int dest) // # of roads(edges) entering this city (node)
	{	// THE HARDER ONE
		int inDeg = 0;
        for (Edge head : G)
        {
            while (head.next!=null)
            {
                if (head.dest == dest)
                    inDeg++;
                head = head.next;
            }
            if (head.dest == dest)
                inDeg++;
        }
		// WALK ALL THE LISTS COUNTING THE NODE HAVING THIS DEST
		return inDeg;
	}

	private int outDegree(int src) // # of roads(edges) leaving this city (src node #)
	{	// THE EASIER ONE
		int outDeg=0;
        Edge cur = G[src];
        while(cur!=null)
        {
            outDeg++;
            cur = cur.next;
        }
		// JUST RETURN THE LENGTH OF THIS LIST AT G[src]
		return outDeg;	
	}

	private int degree(int node) // indegree + outdegree of this node #
	{
		return inDegree(node) + outDegree(node);
	}

	// PUBLIC METHODS. THIS CODE COPIED FROM THE GRAPH2D LAB AND USED AS IS. SINCE IT IS NOT
	// DEPENDENT ON UNDERLYING DATA STRUCTURE AND RELIES ONLY ON CALLING THE 3 DEGREE FUNCTIONS
	
	public int maxOutDegree()
	{
		int maxOutDegree = 0;
        for(int i=0; i<G.length; i++)
        {
            if (outDegree(i) > maxOutDegree)
                maxOutDegree = outDegree(i);           
        }
		return maxOutDegree;
	}

	public int maxInDegree()
	{
		int maxInDegree = 0;
        for(int i=0; i<G.length; i++)
        {
            if(inDegree(i) > maxInDegree)
                maxInDegree = inDegree(i); 
        }
		return maxInDegree;
	}

	public int minOutDegree()
	{
		int minOutDegree = maxOutDegree();
        for(int i=0; i<G.length; i++)
        {
            if (outDegree(i) < minOutDegree)
                minOutDegree = outDegree(i);
        }
		return minOutDegree;
	}

	public int minInDegree()
	{
		int minInDegree = maxInDegree();
        for(int i=0; i<G.length; i++)
        {
            if (inDegree(i) < minInDegree)
                minInDegree = inDegree(i);
        }
		return minInDegree;
	}	
	
	public int maxDegree()
	{
		int maxDegree = 0;
        for(int i=0; i<G.length; i++)
        {
            if ((inDegree(i) + outDegree(i)) > maxDegree)
                maxDegree = inDegree(i) + outDegree(i);
        }
        return maxDegree;
	}

	public int minDegree()
	{
		int minDegree = maxInDegree();
        for(int i=0; i<G.length; i++)
        {
            if ((inDegree(i) + outDegree(i)) < minDegree)
                minDegree = inDegree(i) + outDegree(i);
        }
        return minDegree;
	}
	
	public void removeEdge(int src, int dest)
	{	
        try
        {
            if (src >= numEdges || G[src] == null)
                throw new Exception();
            Edge cur = G[src];
            if(cur.dest == dest)
            {
                G[src] = cur.next;
                return;
            }
            while(cur.next!=null)
            {
                if (cur.next.dest == dest)
                {
                    cur.next = cur.next.next;
                    return;
                }
                cur = cur.next;
            }

            if (cur.next.dest == dest)
                cur.next =null;
            return;
        }

        catch (Exception e)
        {
            System.out.format(e + ": Non Existent Edge Exception: removeEdge(%d,%d)", src, dest);
            System.exit(0);
        }
	} // E N D  R E M O V E D G E  
	
	// TOSTRING
	public String toString()
	{
		String the2String = "";	
        for (int i = 0; i<G.length; i++)
        {
            the2String = the2String + i + ":";
            Edge head = G[i];
            while(head != null && head.next !=null)
            {
                the2String = the2String + " -> [" + head.dest + "," + head.weight + "]";
                head = head.next;
            }
            if (head != null)
                the2String = the2String + " -> [" + head.dest + "," + head.weight + "]";
            the2String = the2String + "\n";
        }
		return the2String;
	} // END TOSTRING
} // E N D    G R A P H L L    C L A S S

class Edge 
{
    int dest, weight;
	Edge next;
	Edge( int dest, int weight, Edge next )
	{
		this.dest = dest;
		this.weight = weight;
		this.next = next;
	}
}

