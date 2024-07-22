package cs1501_p2;
import java.util.*;

public class UserHistory implements Dict
{
    private TreeMap<String, Integer> history;
    public String search;

    public UserHistory()
    {
        history = new TreeMap<String, Integer>();
        search = "";
    }

    public void add(String key)
    {   
        if (key == null)
            return;
        if(contains(key))
        {
            int i = history.get(key);
            history.remove(key);
            history.put(key, i+1);
        }
        else
            history.put(key, 1);
    }

    public boolean contains(String key)
    {
        return history.containsKey(key);
    }

    public boolean containsPrefix(String pre)
    {
        String key = history.ceilingKey(pre);
        if(key!=null && key.contains(pre))
            return true;
        return false;
    }

    public int searchByChar(char next)
    {
        search = search + next;
        String high = history.higherKey(search);
        boolean con = contains(search);
        if(high !=null && high.contains(search) && con == true)
            return 2;
        if(containsPrefix(search) == false)
            return -1;
        if((high == null || high.contains(search) == false) && con == true)
            return 1;
        
        return 0;
    }

    public void resetByChar()
    {
        search = "";
    }

    public ArrayList<String> suggest()
    {
        TreeMap<String, Integer> words = new TreeMap<String, Integer>();
        words.putAll(history.tailMap(search, true));
    
        ArrayList<String> suggest = new ArrayList<String>();
        TreeMap<Integer, TreeSet<String>> val2word = new TreeMap<Integer, TreeSet<String>>();

        for(String key : words.keySet())
        {
            int val = words.get(key);
            TreeSet<String> newValue = new TreeSet<String>();
            if (!val2word.containsKey(val))
            {
                newValue.add(key);
                val2word.put(val , newValue );
            }
            else
            {
                TreeSet<String> addValue = val2word.get(val);
                addValue.add(key);
                val2word.replace(val, addValue);
            }
        }
        
        int i = 1;
        while(val2word!= null && val2word.size()!= 0&& i<=5)
        {
            TreeSet<String> values = val2word.get(val2word.lastKey());
            while(values != null && values.size()!=0 && i<=5)
            {
                String first = values.first();
                if(first.contains(search))
                    suggest.add(first);
                values.remove(values.first());
                i+=1;
            }

            val2word.remove(val2word.lastKey());
        }

        return suggest;
    }

    public ArrayList<String> traverse()
    {
        ArrayList<String> words = new ArrayList<String>();
        words.addAll(history.keySet());
        return words;
    }

    public int count()
    {
        return history.size();
    }

    public int getValue(String key)
    {
        return history.get(key);
    }
}