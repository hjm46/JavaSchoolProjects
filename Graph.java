/* This class was borrowed and modified as needed with permission from it's original author
   Mark Stelhik ( http:///www.cs.cmu.edu/~mjs ).  You can find Mark's original presentation of
   this material in the links to his S-01 15111,  and F-01 15113 courses on his home page.
*/

import java.io.*;
import java.util.*;

public class Graph 
{
	private final int NO_EDGE = -1;
	private Edge G[];

	private int numEdges;
	public Graph( String graphFileName ) throws Exception
	{
		loadGraphFile( graphFileName );
		
	}

	private void loadGraphFile( String graphFileName ) throws Exception
	{
		Scanner graphFile = new Scanner( new File( graphFileName ) );

		int dimension = graphFile.nextInt();
		G = new Edge[dimension];
		numEdges=0;

	    while ( graphFile.hasNextInt() )
            addEdge(row, col, graphFile.nextInt());

	} // END readGraphFile

	private void addEdge( int r, int c, int w )
	{
        head = G[r];
		G[r] = new Edge(c, w, head);
		++numEdges;
	}
	
    private boolean hasEdge(int fromNode, int toNode)
    {
        if (G[fromNode][toNode] > 0)   return true;
        else    return false;
    }

	private int inDegree(int node)
	{
        int inDegree = 0;
        for(int r = 0; r<G.length; r++)
        {
            if (hasEdge(r, node))
                inDegree++;
        }
		return inDegree;
	}

	private int outDegree(int node)
	{
		int outDegree = 0;
        for(int c = 0; c<G[node].length; c++)
        {
            if (hasEdge(node, c))
                outDegree++;
        }
		return outDegree;	
	}

	private int degree(int node)
	{
		int degree = inDegree(node) + outDegree(node);
        return degree;
	}

	// PUBLIC METHODS 
	
	public int maxOutDegree()
	{
        int maxOutDegree = 0;
        for(int r = 0; r<G.length; r++)
        {
            for(int c = 0; c<G[r].length; c++)
            {
                if (outDegree(c) > maxOutDegree)
                    maxOutDegree = outDegree(c);
                    
            }
        }
		return maxOutDegree;
	}

	public int maxInDegree()
	{
		int maxInDegree = 0;
        for(int r = 0; r<G.length; r++)
        {
            for(int c = 0; c<G[r].length; c++)
            {
                if (inDegree(r) > maxInDegree)
                    maxInDegree = inDegree(r);
                    
            }
        }
		return maxInDegree;
	}

	public int minOutDegree()
	{
		int minOutDegree = maxOutDegree();
        for(int r = 0; r<G.length; r++)
        {
            for(int c = 0; c<G[r].length; c++)
            {
                if (outDegree(c) < minOutDegree)
                    minOutDegree = outDegree(c);
                    
            }
        }
		return minOutDegree;
	}

	public int minInDegree()
	{
		int minInDegree = maxInDegree();
        for(int r = 0; r<G.length; r++)
        {
            for(int c = 0; c<G[r].length; c++)
            {
                if (inDegree(c) < minInDegree)
                    minInDegree = inDegree(c);   
            }
        }
		return minInDegree;
	}	
	
	public int maxDegree()
	{
		int maxDegree = 0;
        for(int r = 0; r<G.length; r++)
        {
            for(int c = 0; c<G[r].length; c++)
            {
                if ((inDegree(c) + outDegree(c)) > maxDegree)
                    maxDegree = inDegree(c) + outDegree(c);
            }
        }
        return maxDegree;
	}

	public int minDegree()
	{
		int minDegree = maxInDegree();
        for(int r = 0; r<G.length; r++)
        {
            for(int c = 0; c<G[r].length; c++)
            {
                if ((inDegree(c) + outDegree(c)) < minDegree)
                    minDegree = inDegree(c) + outDegree(c);
            }
        }
        return minDegree;
	}
	
	public void removeEdge(int fromNode, int toNode)
	{
        try
        {
            if (fromNode >= G.length || toNode >= G[fromNode].length || !hasEdge(fromNode, toNode))
                throw new Exception();
            G[fromNode][toNode] = NO_EDGE;
        }

        catch (Exception e)
        {
            System.out.format(e + ": Non Existent Edge Exception: removeEdge(%d,%d)", fromNode, toNode);
            System.exit(0);
        }
	}
	
	// TOSTRING
	public String toString()
	{	String the2String = "";
		for (int r=0 ; r < G.length ;++r )
		{
			for ( int c=0 ; c < G[r].length ; ++c )
				the2String += String.format("%3s",G[r][c] + " ");
			the2String += "\n";
		}
		return the2String;
	} // END TOSTRING

}

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
