package cs1501_p2;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;

public class AutoCompleter implements AutoComplete_Inter
{
    private DLB dict;
    private UserHistory UH;

    public AutoCompleter(String dictionary, String userHist)
    {
        dict = new DLB();
        UH = new UserHistory();

        //load dictionary
        try
        {
            BufferedReader words = new BufferedReader ( new FileReader (dictionary)); 
            while(words.ready())
                dict.add(words.readLine());
        }

        catch (Exception e)
        {
            try
            {
                String file = "/workspaces/project2-hjm46/app/" + dictionary.substring(0, dictionary.indexOf("."));
                BufferedReader words = new BufferedReader ( new FileReader (file)); 
                while(words.ready())
                    dict.add(words.readLine());
            }

            catch (Exception f)
            {
                return;
            }
        }

        //load UserHistory
        try
        {
            BufferedReader hist = new BufferedReader ( new FileReader (userHist));
            while(hist.ready())
                UH.add(hist.readLine());
        }

        catch (Exception g)
        {
            try
            {
                String file = "/workspaces/project2-hjm46/app/" + userHist.substring(0, userHist.indexOf("."));
                BufferedReader hist = new BufferedReader ( new FileReader (file));
                while(hist.ready())
                    UH.add(hist.readLine());
            }

            catch (Exception h)
            {
                
            }
        }
    }

    public AutoCompleter(String dictionary)
    {
        this(dictionary, null);
    }

    public ArrayList<String> nextChar(char next)
    {
        ArrayList<String> fromDict = new ArrayList<String>();
        ArrayList<String> used = new ArrayList<String>();
        if (UH!=null && UH.count() > 0)
        {
            int result = UH.searchByChar(next);
            if(result > 0)
                used.add(UH.search);

            used = UH.suggest();

            if(used.size() < 5)
            {
                result = dict.searchByChar(next);
                if(result > 0)
                    used.add(dict.searchBy);
                fromDict = dict.suggest();
            }
        
            int i = 0;
            while(used.size() < 5 && i<fromDict.size())
            {
                if(used.contains(fromDict.get(i)))
                    i+=1;
                else
                {
                    used.add(fromDict.get(i));
                    i+=1;
                }
            }

        }

        else
        {
            int result = dict.searchByChar(next);
            if(result > 0)
                    used.add(dict.searchBy);
            fromDict = dict.suggest();

            int i = 0;
            while(used.size() < 5 && i<fromDict.size())
            {
                if (used.contains(fromDict.get(i)))
                    i+=1;
                else
                {
                    used.add(fromDict.get(i));
                    i+=1;
                }
            }
        }

        return used;
    }

    public void finishWord(String cur)
    {
        UH.add(cur);
        UH.resetByChar();
        dict.resetByChar();
    }

    public void saveUserHistory(String fname)
    {
        try
        {
            BufferedWriter writeTo = new BufferedWriter(new FileWriter( new File (fname)));
            for (String s: UH.traverse())
            {
                int val = UH.getValue(s);
                for(; val > 0; val-=1)
                {
                    writeTo.append(s);
                    writeTo.newLine();
                }
            }
            writeTo.close();
        }
        
        catch (Exception e)
        {
            try
            {
                String file = "/workspaces/project2-hjm46/app/" + fname.substring(0, fname.indexOf("."));
                BufferedWriter writeTo = new BufferedWriter(new FileWriter( new File (fname)));
                for (String s: UH.traverse())
                {
                    int val = UH.getValue(s);
                    for(; val > 0; val-=1)
                    {
                        writeTo.append(s);
                        writeTo.newLine();
                    }
                }
                writeTo.close();
            }

            catch (Exception f)
            {

            }
        }
    }
}