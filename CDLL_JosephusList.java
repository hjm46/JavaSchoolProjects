import java.io.*;
import java.util.*;

public class CDLL_JosephusList<T>
{
	private CDLL_Node<T> head;  // pointer to the front (first) element of the list
	private int count=0;
	// private Scanner kbd = new Scanner(System.in); // FOR DEBUGGING. See executeRitual() method 
	public CDLL_JosephusList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE
	
	public CDLL_JosephusList( String infileName ) throws Exception
	{
		BufferedReader infile = new BufferedReader( new FileReader( infileName ) );	
		while ( infile.ready() )
		{	@SuppressWarnings("unchecked") 
			T data = (T) infile.readLine(); // CAST CUASES WARNING (WHICH WE CONVENIENTLY SUPPRESS)
			insertAtTail( data ); 
		}
		infile.close();
	}
	

	
	// ########################## Y O U   W R I T E / F I L L   I N   T H E S E   M E T H O D S ########################
	
	// TACK ON NEW NODE AT END OF LIST
	public void insertAtTail(T data)
	{
        if (head == null)
        {
            head = new CDLL_Node<T>(data, null, null);
            head.next = head;
            head.prev = head;
            return;
        }

        CDLL_Node<T> curTail = head.prev;
        CDLL_Node<T> newTail = new CDLL_Node<T>(data, null, null);
        curTail.next = newTail;
        newTail.prev = curTail;
        newTail.next = head;
        head.prev = newTail;
	}

	
	public int size()
	{	
		if (head==null) return 0;
        CDLL_Node<T> cur = head;
        int count = 0;
        do
        {
            cur = cur.next;
            count++;
        } while (cur!=head);
		return count;
	}
	
	// RETURN REF TO THE FIRST NODE CONTAINING  KEY. ELSE RETURN NULL
	public CDLL_Node<T> search( T key )
	{	
		if (head == null) return null;
		CDLL_Node<T> cur = head;
        do
        {
            if(cur.data.equals(key)) return cur;
            cur = cur.next;
        } while (cur!=head);
		return null;
	}
	
	// RETURNS CONATENATION OF CLOCKWISE TRAVERSAL

	public String toString()
	{
		if (head==null) return "";
        CDLL_Node<T> cur = head;
        String toString = "";
        if (size()!=1)
        {
            do
            {
                toString = toString + cur.data + "<=>";
                cur = cur.next;
            } while (cur!=head.prev);
        }
        toString = toString + cur.data;
		return toString;		
	}
	
	void removeNode( CDLL_Node<T> deadNode )
	{
        CDLL_Node<T> cur = search(deadNode.data);
        CDLL_Node<T> curNext = cur.next;
        cur = cur.prev;
        cur.next = curNext;
        curNext.prev = cur;
	}
	
	public void executeRitual( T first2Bdeleted, int skipCount )
	{
		if (size() <= 1 ) return;
		CDLL_Node<T> cur = search( first2Bdeleted );
		if ( cur==null ) return;
		
		// OK THERE ARE AT LEAST 2 NODES AND CUR IS SITING ON first2Bdeleted
		do
		{
			CDLL_Node<T> deadNode = cur;
			
			System.out.println( "stopping on " + cur.data + " to delete " + cur.data);
			
            String direction = "";
            int skip = 0;
            if (skipCount >= 0) 
            {
                cur = cur.next;
                skip = skipCount-1;
                direction = "CLOCKWISE";
            }
            else 
            {
                cur=cur.prev;
                skip = Math.abs(skipCount+1);
                direction = "COUNTER_CLOCKWISE";
            }
            if (head==deadNode) head=cur;
			removeNode(deadNode);

			System.out.println("deleted. list now: "+ this.toString() );
			if (this.size() == 1) return;
	
			System.out.println("resuming at " + cur.data +", skipping " +cur.data +" + " + skip + " nodes " + direction + " after");
			
            if (skipCount>=0)
            {
                for(int i=0; i<skipCount; i++)
                    cur = cur.next;
            }
            else 
            {
                for(int i=0; i>skipCount; i--)
                    cur = cur.prev;
            }

		}
		while (size() > 1 );

	}
	
} // END CDLL_LIST CLASS

class CDLL_Node<T>
{
  T data;
  CDLL_Node<T> prev, next;

  public CDLL_Node()
  {
    this( null, null, null );
  }

  public CDLL_Node(T data)
  {
    this( data, null, null);
  }

  public CDLL_Node(T data, CDLL_Node<T> prev, CDLL_Node<T> next)
  {
    this.data = data;
	this.prev = prev;
    this.next = next;
  }
 
  public String toString()
  {
	  return "" + data;
  } 
	 
}