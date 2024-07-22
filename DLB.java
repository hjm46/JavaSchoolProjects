package cs1501_p2;
import java.util.ArrayList;

public class DLB implements Dict
{    
    private DLBNode start;
    private static char term = '^'; //terminating character = ^
    private int count;
    public DLBNode search;
    public String searchBy;

    public DLB()
    {
        start = null;
        search = start;
        searchBy = "";
        count = 0;
    }

    //TESTED TO BEST OF ABILITY
    public void add(String key)
    {
        if(key == null)
            return;
        int i = 0;
        key = key + term;
        DLBNode cur = null;

        if(start == null)
        {
            DLBNode head = new DLBNode(key.charAt(i));
            start = head;
            search = head;
            addDown(start, key, i+1);
        }

        else
        {
            if(contains(key) == true)
                return;
            cur = start;

            while(i<key.length())
            {
                DLBNode letter = LLfindChar(cur, key.charAt(i));
                if (letter == null)
                {
                    add_next(cur, key.charAt(i));
                    i+=1;
                    cur = cur.getRight();
                    addDown(cur, key, i);
                    break;
                }
                
                else if(letter.getDown() == null)
                {
                    letter.setDown(new DLBNode(key.charAt(i)));
                    i+=1;
                    cur = letter.getDown();
                }

                else
                {
                    cur = letter.getDown();
                    i+=1;
                }
            }
        }
        count+=1;
    }

    //HELPER FOR LINKED LIST
    private void add_next(DLBNode head, char let)
    {
        DLBNode temp = head.getRight();
        head.setRight(new DLBNode(let));
        head = head.getRight();
        head.setRight(temp);
    }

    private DLBNode LLfindChar(DLBNode cur, char let)
    {
        while(cur!=null && cur.getLet()!=let)
            cur = cur.getRight();
        return cur;
    }

    private void addDown(DLBNode cur, String key, int i)
    {
        while(i<key.length())
        {
            cur.setDown(new DLBNode(key.charAt(i)));
            cur = cur.getDown();
            i+=1;
        }
    }

    public boolean contains(String key)
    {
        if(key == null || start == null)
            return false;
        
        DLBNode cur = start;
        int i = 0;
        key = key + term;
        return contains_rec(cur, key, i);
    }

    private boolean contains_rec(DLBNode cur, String key, int i)
    {
        while(i<key.length())
        {
            DLBNode letter = LLfindChar(cur, key.charAt(i));
            if(letter == null)
                return false;

            if(letter.getDown() == null && i<key.length()-1)
                return false;

            cur = letter.getDown();
            i+=1;
        }
        return true;
    }

    public boolean containsPrefix(String pre)
    {
        if(pre == null || start == null)
            return false;
        
        DLBNode cur = start;
        int i = 0;
        
        while(i<pre.length())
        {
            DLBNode letter = LLfindChar(cur, pre.charAt(i));
            if(letter == null)
                return false;

            if(letter.getDown() == null && i<pre.length()-1)
                return false;

            cur = letter.getDown();
            i+=1;
        }
        return true;
    }

    public int searchByChar(char next)
    {
        search = LLfindChar(search, next);
        if(search != null)
            searchBy = searchBy + search.getLet();

        if(search == null)
            return -1;
        if(search.getDown() == null)
            return -1;

        search = search.getDown();
        DLBNode letter = LLfindChar(search, term);

        if(letter == null)
            return 0;
        if(letter.getRight() == null)
            return 1;
        else
            return 2;

    }

    public void resetByChar()
    {
        search = start;
        searchBy = "";
    }

    public ArrayList<String> suggest()
    {
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<String> suggested = new ArrayList<String>();
        DLBNode temp = start;
        start = search;

        words = traverse();

        int i = 0;
        start = temp;
        if(contains(searchBy))
        {
            suggested.add(searchBy);
        }
        while(i<5 && suggested.size()<=5 && i<words.size())
        {
            String newWord = searchBy + words.get(i);
            suggested.add(newWord);
            i+=1;
        }

        return suggested;
    }

    public ArrayList<String> traverse()
    {
        ArrayList<String> all = new ArrayList<String>();
        String word = "";
        //DLBNode right = start;
        print(start, all, word);
        
        mergesort(all);
        return all;
    }

    private void print(DLBNode cur, ArrayList<String> all, String word)
    {
        if(cur == null)
        {
            word = "";
            return;
        }

        if(cur.getLet() == term)
        {
            if(word!="")
                all.add(word);
        }

        print(cur.getRight(), all, word);

        if(cur.getDown() != null)
        {
            word = word + cur.getLet();
            print(cur.getDown(), all, word);
        }
    }

    private void mergesort(ArrayList<String> array)
    {
        int len = array.size();
        if(len <= 1)    
            return;

        int mid = len/2;
        ArrayList<String> left = new ArrayList<String>();
        ArrayList<String> right = new ArrayList<String>();

        int i = 0;
        for(; i<mid; i+=1)
            left.add(array.get(i));

        for(; i<len; i+=1)
            right.add(array.get(i));

        mergesort(left);
        mergesort(right);
        merge(left, right, array);
    }

    private void merge(ArrayList<String> left, ArrayList<String> right, ArrayList<String> array)
    {
        array.clear();
        int leftSize = left.size();
        int rightSize = right.size();
        int R = 0;
        int L = 0;

        while(L<leftSize && R<rightSize)
        {
            if(left.get(L).compareTo(right.get(R)) < 0)
            {
                array.add(left.get(L));
                L+=1;
            }

            else
            {
                array.add(right.get(R));
                R+=1;
            }
        }

        while(L<leftSize)
        {
            array.add(left.get(L));
            L+=1;
        }

        while(R<rightSize)
        {
            array.add(right.get(R));
            R+=1;
        }
    }

    public int count()
    {
        return count;
    }
}
