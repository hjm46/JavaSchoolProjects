import java.io.*;
import java.util.*;

public class CDLL_List<T>
{
	private CDLL_Node<T> head;  // pointer to the front (first) element of the list
	private int count=0;

	public CDLL_List()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE

	public CDLL_List( String fileName, String insertionMode ) throws Exception
	{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{	@SuppressWarnings("unchecked")
				T data = (T) infile.readLine(); // CAST CUASES WARNING (WHICH WE CONVENIENTLY SUPPRESS)
				if ( insertionMode.equals("atFront") )
					insertAtFront( data );
				else if ( insertionMode.equals( "atTail" ) )
					insertAtTail( data );
				else
					die( "FATAL ERROR: Unrecognized insertion mode <" + insertionMode + ">. Aborting program" );
			}
			infile.close();
	}

	private void die( String errMsg )
	{
		System.out.println( errMsg );
		System.exit(0);
	}

	// ########################## Y O U   W R I T E / F I L L   I N   T H E S E   M E T H O D S ########################

	// OF COURSE MORE EFFICIENT TO KEEP INTERNAL COUNTER BUT YOU COMPUTE IT DYNAMICALLY WITH A TRAVERSAL LOOP
	@SuppressWarnings("unchecked")
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


	// TACK A NEW NODE ONTO THE FRONT OF THE LIST
	
	public void insertAtFront(T data)
	{
		insertAtTail(data);
        head = head.prev;
	}

	// TACK ON NEW NODE AT END OF LIST
	@SuppressWarnings("unchecked")
	public void insertAtTail(T data)
	{
		if (head == null)
        {
            head = new CDLL_Node (data, null, null);
            head.next = head;
            head.prev = head;
            return;
        }

        CDLL_Node<T> curTail = head.prev;
        CDLL_Node<T> newTail = new CDLL_Node(data, null, null);
        curTail.next = newTail;
        newTail.prev = curTail;
        newTail.next = head;
        head.prev = newTail;
    }

	// RETURN TRUE/FALSE THIS LIST CONTAINS A NODE WITH DATA EQUALS KEY
	public boolean contains( T key )
	{
		return search(key) != null;
	}

	// RETURN REF TO THE FIRST NODE (SEARCH CLOCKWISE FOLLOWING next) THAT CONTAINS THIS KEY. DO -NOT- RETURN REF TO KEY ISIDE NODE
	// RETURN NULL IF NOT FOUND
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
	@SuppressWarnings("unchecked")
	public String toString()
	{
        if (head==null) return "";
        CDLL_Node<T> cur = head;
        String toString = "";
        do
        {
            toString = toString + cur.data + "<=>";
            cur = cur.next;
        } while (cur!=head.prev);
        toString = toString + cur.data;
		return toString;
	}

} // END CDLL_LIST CLASS

// PRIVATE TO CODE OUTSIDE FILE. BUT PUBLIC TO CODE INSIDE
class CDLL_Node<T>
{
  T data; // DONT DEFINE MEMBERS AS PUBLIC OR PRIVATE
  CDLL_Node<T> prev, next; //
  CDLL_Node() 		{ this( null, null, null ); }
  CDLL_Node(T data) { this( data, null, null);  }
  CDLL_Node(T data, CDLL_Node<T> prev, CDLL_Node<T> next)
  {	this.data=data; this.prev=prev; this.next=next;
  }
  public String toString() // TOSTRING MUST BE PUBLIC
  {	return ""+data;
  }
} //END NODE CLASS
