import java.io.*;
import java.util.*;
// DO NOT IMPORT ANYTHING ELSE
// NO PACKAGE STATMENTS 
// NO OVERRIDE STATMENTS 

public class ComplexSwamp
{
	static int[][] swamp;  // NOW YOU DON'T HAVE PASS THE REF IN/OUT METHODS

 	public static void main(String[] args) throws Exception
	{
		int[] dropInPt = new int[2]; // row and col will be on the 2nd line of input file;
		swamp = loadSwamp( args[0], dropInPt);
		int row=dropInPt[0], col = dropInPt[1];
		String path = ""; // with each step grows to => "[2,3][3,4][3,5][4,6]" et
		dfs( row, col, path);
		System.out.print(path);
	} // END MAIN

 	// JUST FOR YOUR DEBUGGING - DELETE THIS METHOD AND ITS CALL BEFORE HANDIN
	// ----------------------------------------------------------------
	private static void printSwamp(String label, int[][] swamp )
	{
		System.out.println( label );
		System.out.print("   ");
		for(int c = 0; c < swamp.length; c++)
			System.out.print( c + " " ) ;
		System.out.print( "\n   ");
		for(int c = 0; c < swamp.length; c++)
			System.out.print("- ");
		System.out.print( "\n");

		for(int r = 0; r < swamp.length; r++)
		{	System.out.print( r + "| ");
			for(int c = 0; c < swamp[r].length; c++)
				System.out.print( swamp[r][c] + " ");
			System.out.println("|");
		}
		System.out.print( "   ");
		for(int c = 0; c < swamp.length; c++)
			System.out.print("- ");
		System.out.print( "\n");
	}
	// --YOU-- WRITE THIS METHOD 
	// (you may copy from YOURSELF from YOUR lab7 not someone else's)
   	// ----------------------------------------------------------------
	private static int[][] loadSwamp( String infileName, int[] dropInPt) throws Exception
	{
		int[][] theSwamp;
		Scanner infile = new Scanner(new FileReader (infileName)); // open infile with Scanner
		int dimension = infile.nextInt();
		theSwamp = new int[dimension][dimension];
        dropInPt[0] = infile.nextInt();
        dropInPt[1] = infile.nextInt();
        for (int i=0; i<theSwamp.length; i++)
        {
            for (int j=0; j<theSwamp.length; j++)
            {
                theSwamp[i][j] = infile.nextInt();
            }
        }

        return theSwamp;
	}
 
	static boolean onEdge( int[][] swamp, int r, int c ) // RET TRUE IF ON EDGE OF SWAMP
	{
		return r==0 || r==swamp.length-1 || c ==0 || c==swamp.length-1;
	}

	static void dfs( int row, int col, String path ) // dfs = DEPTH FIRST SEARCH
	{
		if (onEdge (swamp, row, col))
		{
			//swamp[row][col] = -1;
			System.out.println(path + "[" + row + "," + col + "]");
			while (path.length() > 1)
			{
				swamp[Integer.parseInt(Character.toString(path.charAt((path.length()-4))))][Integer.parseInt(Character.toString(path.charAt((path.length()-2))))] = 1;
				path = path.substring(0, path.length() - 5);
			}
			printSwamp("swamp", swamp);

		}

		else if (swamp[row-1][col] == 1) 	//N
		{
			path = path + "[" + row + "," + col + "]";
			swamp[row][col] = -1;
			dfs(row-1, col, path);
			
		}

		else if (swamp[row-1][col+1] == 1) 	//NE
		{
			path = path + "[" + row + "," + col + "]";
			swamp[row][col] = -1;
			dfs(row-1, col+1, path);
			
		}

		else if (swamp[row][col+1] == 1) 	//E
		{
			path = path + "[" + row + "," + col + "]";
			swamp[row][col] = -1;
			dfs(row, col+1, path);
			
		}

		else if (swamp[row+1][col+1] == 1) 	//SE
		{
			path = path + "[" + row + "," + col + "]";
			swamp[row][col] = -1;
			dfs(row+1, col+1, path);
			
		}

		else if (swamp[row+1][col] == 1) 	//S
		{
			path = path + "[" + row + "," + col + "]";
			swamp[row][col] = -1;
			dfs(row+1, col, path);
			
		}

		else if (swamp[row+1][col-1] == 1) 	//SW
		{
			path = path + "[" + row + "," + col + "]";
			swamp[row][col] = -1;
			dfs(row+1, col-1, path);
			
		}

		else if (swamp[row][col-1] == 1) 	//W
		{
			path = path + "[" + row + "," + col + "]";
			swamp[row][col] = -1;
			dfs(row, col-1, path);
			
		}

		else if (swamp[row-1][col-1] == 1) 	//NW
		{
			path = path + "[" + row + "," + col + "]";
			swamp[row][col] = -1;
			dfs(row-1, col-1, path);
			
		}
		
		swamp[row][col] = -1;
		if(path.length() > 7)
		{
			row = Integer.parseInt(Character.toString(path.charAt((path.length()-4))));
			col = Integer.parseInt(Character.toString(path.charAt((path.length()-2))));
			path = path.substring(0, path.length() - 5);
			dfs(row,col,path);
			//swamp[row][col] = 1;
			//return;
		}	
		
		else if (path.length() > 1)
		{
			//System.out.println(path);
			row = Integer.parseInt(Character.toString(path.charAt((1))));
			col = Integer.parseInt(Character.toString(path.charAt((3))));
			path = "";
			dfs(row,col,path);
			//swamp[row][col] = 1;
		}
		//swamp[row][col] = 1;
		//return;

	}	
}
