import java.util.*;
import java.io.*;

public class Jumbles
{
    public static void main ( String args[] ) throws Exception
    {
        BufferedReader infile = new BufferedReader ( new FileReader (args[0]));
        BufferedReader infile2 = new BufferedReader ( new FileReader (args[1]));
        TreeSet<String> dict = new TreeSet<String>();
        TreeSet<String> jum = new TreeSet<String>();
        TreeMap<String, TreeSet<String>> dictMap = new TreeMap<String, TreeSet<String>>();
        TreeMap<String, String> jumMap = new TreeMap<String, String>();

        while (infile.ready())
            dict.add(infile.readLine());
        
        while (infile2.ready())
            jum.add(infile2.readLine());
        
        for (String e: dict)
        {
            String can = toCanonical(e);
            TreeSet<String> newValue = new TreeSet<String>();
            newValue.add(e);
            if (dictMap.containsKey(can))
            {
                newValue.addAll(dictMap.get(can));
                dictMap.put(can, newValue);
            }
            else
                dictMap.put(can, newValue);
        }

        for (String e: jum)
            jumMap.put(toCanonical(e), e);

        TreeMap<String, TreeSet<String>> solution = new TreeMap<String, TreeSet<String>>();
        for (String e: jum)
        {
            TreeSet<String> newValue = new TreeSet<String>();
            solution.put(e, newValue);
        }
        for (String e: jumMap.keySet())
        {
            TreeSet<String> newValue = new TreeSet<String>();
            for (String s: dictMap.keySet())
            {
                if (e.equals(s))
                    solution.put(jumMap.get(e), dictMap.get(s));
            }
        }
        printMap(solution);
    }

    static TreeSet<String> loadSet( BufferedReader infile ) throws Exception
    {
        TreeSet<String> loadset = new TreeSet<String>();
        while (infile.ready())
            loadset.add(infile.readLine());
        return loadset;
    }

    static String toCanonical( String s )
    {
        char[] letters = s.toCharArray();
        Arrays.sort( letters );
        return new String( letters );
    }

        static void printSet( String caption, TreeSet<String> set )
    {
        System.out.print(caption);
        for (String e: set)
            System.out.print(e + " ");
        System.out.println();
    }

     static void printMap(TreeMap<String, TreeSet<String>> map)
    {
        for(String key: map.keySet())
        {
            System.out.print(key + " ");
            TreeSet<String> elements = new TreeSet<String> (map.get(key));
            for(String e: elements)
            System.out.print(e + " ");
            System.out.println();
        }
        System.out.println();
    }
}