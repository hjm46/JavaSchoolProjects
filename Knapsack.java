/*
	PrintSubSets.java
*/
import java.io.*;
import java .util.*;

public class Knapsack
{
	public static void main( String[] args ) throws Exception
	{	
        Scanner infile = new Scanner( new FileReader( args[0] ) );
        int[] set = new int[16];
        for (int i=0; i<set.length; i++)
        {
            set[i] = infile.nextInt();
        }

        int target = infile.nextInt();
        infile.close();

        int[] temp = new int[16];
        int count = 0;
        int total = 0;

		for ( int i=0 ; i<Math.pow(2,set.length); ++i )
		{	
            count = 0;
            total = 0;
            String bitmap = toBitString( i, set.length );
			for ( int bindx=0 ; bindx<bitmap.length() ; bindx++ )
            {
                if ( bitmap.charAt(bindx)=='1' )
				{
                    temp[count] = set[bindx];
                    total = total + set[bindx];
                    count++;
                }

            }

            if (total == target)
            {
                for (int j=0; j<count; j++)
                    System.out.print( temp[j] + " ");
                System.out.println();
            }
            
		}
	} // END MAIN

	// i.e number 31 converted to a width of 5 bits = "11111"
	//     nuumber 7 converted to a width of 5 bits = "00111"
	static String toBitString( int number, int width )
	{
		String bitstring = "";
		while (number > 0)
		{	if (number % 2 == 0)
				bitstring = "0" + bitstring;
			else
				bitstring = "1" + bitstring;
			number /= 2 ;
		}
		while ( bitstring.length() < width )
				bitstring = "0" + bitstring;
		return bitstring;
	}
} // END CLASS