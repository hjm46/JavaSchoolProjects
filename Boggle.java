import java.io.*;
import java.util.*;

public class Boggle
{
	static String[][] board;
	static long startTime,endTime;
	static final long MILLISEC_PER_SEC = 1000;

    static TreeSet<String> dictionary = new TreeSet<String>();
    static TreeSet<String> hits = new TreeSet<String>();

	public static void main( String args[] ) throws Exception
	{
		startTime= System.currentTimeMillis();
		board = loadBoard( args[1] );

        dictionary = loadSet(args[0]);

		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board[row].length; col++)
				dfs( row, col, "" );

        printSet(hits);
        System.out.println(hits.size());
		endTime =  System.currentTimeMillis();
		System.out.println("GENERATION COMPLETED: runtime=" + (endTime-startTime)/MILLISEC_PER_SEC );

	} // END MAIN ----------------------------------------------------------------------------

	static void dfs( int r, int c, String word  )
	{
		word += board[r][c];

        if (word.length() >= 3)
        {
            if (dictionary.contains(word))
                hits.add(word);

            else if (!dictionary.higher(word).startsWith(word))
            {
                for(int i=0; i<board.length; i++)
                {
                    for(int j=0; j<board[i].length; j++)
                    {
                        if (board[i][j] == null)
                        {
                            board[i][j] = "";
                            String unMarked = board[r][c];
                            board[r][c] = null;
                            dfs(i, j, word);
                            board[r][c] = unMarked;
                        }
                        return;
                    }
                }
            }
        }

		// NORTH IS [r-1][c]
		if ( r-1 >= 0   &&   board[r-1][c] != null )
		{	String unMarked = board[r][c];
			board[r][c] = null;
			dfs( r-1, c, word);
			board[r][c] = unMarked;
		}

		// NE IS [r-1][c+1]
		if (( r-1 >= 0) && (c+1 < board[r].length)   &&   (board[r-1][c+1] != null))
		{	String unMarked = board[r][c];
			board[r][c] = null;
			dfs( r-1, c+1, word);
			board[r][c] = unMarked;
		}

		// E IS [r][c+1]
        if ((c+1 < board[r].length) && (board[r][c+1] != null))
        {
            String unMarked = board[r][c];
            board[r][c] = null;
            dfs( r, c+1, word);
            board[r][c] = unMarked;
        }

		// SE IS [r+1][c+1]
        if (( r+1 < board.length) && (c+1 < board[r].length)   &&   (board[r+1][c+1] != null))
        {
            String unMarked = board[r][c];
            board[r][c] = null;
            dfs( r+1, c+1, word);
            board[r][c] = unMarked;
        }

		// S IS [r+1][c]
        if (( r+1 < board.length)  &&   (board[r+1][c] != null))
        {
            String unMarked = board[r][c];
            board[r][c] = null;
            dfs( r+1, c, word);
            board[r][c] = unMarked;
        }

		// SW IS [r+1][c-1]
        if (( r+1 < board.length) && (c-1 >= 0)   &&   (board[r+1][c-1] != null))
        {
            String unMarked = board[r][c];
            board[r][c] = null;
            dfs( r+1, c-1, word);
            board[r][c] = unMarked;
        }

		// W IS [r][c-1]
        if ((c-1 >= 0)   &&   (board[r][c-1] != null))
        {
            String unMarked = board[r][c];
            board[r][c] = null;
            dfs( r, c-1, word);
            board[r][c] = unMarked;
        }

		// NW IS [r-1][c-1]
        if (( r-1 >= 0) && (c-1 >= 0)   &&   (board[r-1][c-1] != null))
        {
            String unMarked = board[r][c];
            board[r][c] = null;
            dfs( r-1, c-1, word);
            board[r][c] = unMarked;
        }

	} // END DFS ----------------------------------------------------------------------------

	//=======================================================================================
	static String[][] loadBoard( String fileName ) throws Exception
	{	Scanner infile = new Scanner( new File(fileName) );
		int rows = infile.nextInt();
		int cols = rows;
		String[][] board = new String[rows][cols];
		for (int r=0; r<rows; r++)
			for (int c=0; c<cols; c++)
				board[r][c] = infile.next();
		infile.close();
		return board;
	} //END LOADBOARD

    static TreeSet<String> loadSet( String infileName ) throws Exception
	{
        BufferedReader infile = new BufferedReader(new FileReader (infileName));
        TreeSet<String> loadset = new TreeSet<String>();
        while (infile.ready())
            loadset.add(infile.readLine());
		return loadset;
	}

    static void printSet( TreeSet<String> set )
	{
        for (String e: set)
            System.out.println(e);
	}

} // END BOGGLE CLASS