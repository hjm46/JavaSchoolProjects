import java.util.*;
import java.io.*;

public class DrugInteractions
{
	public static void main( String[] args ) throws Exception
	{
		BufferedReader foodDrug2CategoryFile = new BufferedReader( new FileReader( "foodDrug2Category.txt" ) );
		BufferedReader patient2FoodDrugFile = new BufferedReader( new FileReader( "patient2FoodDrug.txt") );
		BufferedReader dontMixFile = new BufferedReader( new FileReader( "dontMix.txt" ) );
		
        TreeMap<String, TreeSet<String>> foodDrug2 = loadMap(foodDrug2CategoryFile);
		TreeMap<String, TreeSet<String>> patient2FoodDrug = loadMap(patient2FoodDrugFile);
        TreeMap<String, TreeSet<String>> dontMix = dontMixLoad(dontMixFile);
        
        printMap(foodDrug2);
        printMap(patient2FoodDrug);
        printSet(dontMix(combo( inverse(foodDrug2), patient2FoodDrug), dontMix));
		
	} // END MAIN

    static TreeMap<String,TreeSet<String>> loadMap(BufferedReader infile) throws Exception
    {
        TreeMap<String,TreeSet<String>> map = new TreeMap<String,TreeSet<String>>();
        while(infile.ready())
        {
            ArrayList<String> elements = new ArrayList<String>( Arrays.asList( infile.readLine().split(",") ) );
		    String key = elements.remove(0);
            TreeSet<String> newValue = new TreeSet<String>();
			for ( String e :  elements)
            {
				if (!map.containsKey(key))
				{
                    newValue.add(e);
					map.put( key, newValue );
                }

                TreeSet<String> members = new TreeSet<String>(map.get(key));
                members.add(e);
                map.put(key, members);

            }
        }
        return map;
    }

    static TreeMap<String,TreeSet<String>> dontMixLoad(BufferedReader infile) throws Exception
    {
        TreeMap<String,TreeSet<String>> map = new TreeMap<String,TreeSet<String>>();
        int key = 0;
        while(infile.ready())
        {
                String skey = key + "";
                ArrayList<String> elements = new ArrayList<String>( Arrays.asList( infile.readLine().split(",") ) );
                TreeSet<String> newValue = new TreeSet<String>();
			    for (int i=0; i<elements.size(); i++)
                    newValue.add(elements.get(i));
                map.put(skey, newValue);
                key++;
        }
        return map;
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

    static void printSet( TreeSet<String> set )
	{
        for (String e: set)
            System.out.println(e);
	}

    static TreeMap<String,TreeSet<String>> inverse(TreeMap<String,TreeSet<String>> map)
    {
        TreeMap<String,TreeSet<String>> inverse = new TreeMap<String,TreeSet<String>>();
        for(String key : map.keySet())
        {
            TreeSet<String> elements = new TreeSet<String>(map.get(key));
            TreeSet<String> newValue = new TreeSet<String>();
			for ( String e :  elements)
            {
				if (!inverse.containsKey(key))
				{
                    newValue.add(key);
					inverse.put( e, newValue );
                }
            }
        }
        return inverse;
    }

    static TreeMap<String,TreeSet<String>> combo(TreeMap<String,TreeSet<String>> map, TreeMap<String,TreeSet<String>> map2)
    {
        TreeMap<String,TreeSet<String>> combo = new TreeMap<String,TreeSet<String>>();
        for(String key : map2.keySet())
        {
            TreeSet<String> elements = new TreeSet<String>(map2.get(key));
            TreeSet<String> newValue = new TreeSet<String>();
			for ( String e :  elements)
            {
				if (map.containsKey(e))
				{
                    String value = "";
                    for (String s: map.get(e))
                        value = value + s;
                    newValue.add(value);
                    combo.put(key, newValue);
                }
            }
        }
        return combo;
    }

    static TreeSet<String> dontMix (TreeMap<String,TreeSet<String>> map, TreeMap<String,TreeSet<String>> map2)
    {
        TreeSet<String> dontMix = new TreeSet<String>();
        for (String key: map.keySet())
        {
            for(String e: map2.keySet())
            {
                TreeSet<String> elements = new TreeSet<String>(map2.get(e));
                if (map.get(key).containsAll(elements))
                    dontMix.add(key);
            }
        }
        return dontMix;
    }

} // END CLASS