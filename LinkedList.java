import java.io.*;
import java.util.*;

// NOTICE THE "<T extends Comparable<T>>"
// using <T extends Comparable<T>> in here means compiler wont let the code in main send in any T type
// that does not implement Comparable.  Now we do not have to cast the incoming key to a Comparable
// in our insertInOrder() method. Compiler now lets us call .compareTo off the dot on the incoming key
// without throwing an error.

public class LinkedList<T extends Comparable<T>> 
{
	private Node<T> head;  // pointer to the front (first) element of the list

	public LinkedList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FROM INCOMING FILE
	@SuppressWarnings("unchecked")
	public LinkedList( String fileName, boolean orderedFlag )
	{	head = null;
		try
		{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{
				if (orderedFlag)
					insertInOrder( (T)infile.readLine() );  // WILL INSERT EACH ELEM INTO IT'S SORTED POSITION
				else
					insertAtTail( (T)infile.readLine() );  // TACK EVERY NEWELEM ONTO END OF LIST. ORIGINAL ORDER PRESERVED
			}
			infile.close();
		}
		catch( Exception e )
		{
			System.out.println( "FATAL ERROR CAUGHT IN C'TOR: " + e );
			System.exit(0);
		}
	}

	//-------------------------------------------------------------

	// inserts new elem at front of list - pushing old elements back one place

	public void insertAtFront(T data)
	{
		head = new Node<T>(data,head);
	}

	// we use toString as our print

	public String toString()
	{
		String toString = "";

		for (Node<T> curr = head; curr != null; curr = curr.next )
		{
			toString += curr.data;		// WE ASSUME OUR T TYPE HAS toString() DEFINED
			if (curr.next != null)
				toString += " ";
		}

		return toString;
	}

	// ########################## Y O U   W R I T E    T H E S E    M E T H O D S ########################



	public int size()
	{
        if (head==null) return 0;
		int count = 1;
        Node<T> cur = head;
        while(cur.next != null)
        {
            cur = cur.next;
            count++;
        }
        return count;
	}

	public boolean empty()
	{
		return (head == null);
	}

	public boolean contains( T key )
	{
		return search(key)!=null;
	}

	public Node<T> search( T key )
	{
		if(head == null) return null;
        Node<T> cur = head;
        while(cur.next != null) 
        {
            if(cur.data.equals(key))
                return cur; 
            cur = cur.next;
        }
        if (cur.data.equals(key))   return cur;
        return null;
	}

	// TACK A NEW NODE (CABOOSE) ONTO THE END OF THE LIST
	public void insertAtTail(T data)
	{
		if(head==null)
        {
            insertAtFront(data);
            return;
        }

        Node<T> cur = head;
        while(cur.next != null)
            cur = cur.next;
        cur.next = new Node<T>(data,null);
        return;
        
	}

	public void insertInOrder(T  data)
	{//FIX THIS
        if ((head==null) || (data.compareTo(head.data) <= 0))
        {
            insertAtFront(data);
            return;
        }


        if ((head.next==null) && (data.compareTo(head.data) >= 0))
        {
            insertAtTail(data);
            return;
        }

		Node<T> cur = head;

        while (cur.next!=null)
        {
            if(data.compareTo(cur.next.data) <= 0)
            {
                cur.next = new Node<T>(data, cur.next);
                return;
            }
            cur = cur.next;
        }

        insertAtTail(data);
        return;

	}

	public boolean remove(T key)
	{
        if (contains(key) == false) return false;
        Node<T> cur = head;
        if(cur.data.equals(key))    return removeAtFront();
        while(cur.next!=null)
        {
            if (cur.next.data.equals(key))
            {
                cur.next = cur.next.next;
                return true;
            }
            cur = cur.next;
        }

        return removeAtTail();

	}

	public boolean removeAtTail()
	{
		if (head == null) return false;
        Node<T> cur = head;
        if (cur.next == null)   return removeAtFront();

        while (cur.next.next != null)
            cur = cur.next;
        cur.next = null;
        return true;
	}

	public boolean removeAtFront()
	{
        if (head == null) return false;
        Node<T> cur = head;
        if(cur.next == null)
        {
            head = null;
            return true;
        }
        head = cur.next;
		return true;
	}

	public LinkedList<T> union( LinkedList<T> other )
	{
		LinkedList<T> union = new LinkedList<T>();
        Node<T> cur = this.head;
        while (cur!=null)
        {
            union.insertInOrder(cur.data);
            cur = cur.next;
        }
        cur = other.head;
        while (cur!=null)
        {
            if (union.search(cur.data) != null)
                cur = cur.next;
            union.insertInOrder(cur.data);
            cur = cur.next;
        }

		return union;

	}
	public LinkedList<T> inter( LinkedList<T> other )
	{
		LinkedList<T> inter = new LinkedList<T>();
        Node<T> cur = this.head;
        while(cur!=null)
        {
            if (other.search(cur.data)!=null)
            {
                inter.insertInOrder(cur.data);
                cur = cur.next;
            }
            cur = cur.next;
        }

		return inter;
	}
	public LinkedList<T> diff( LinkedList<T> other )
	{
		LinkedList<T> diff = new LinkedList<T>();
        LinkedList<T> inter = this.inter(other);
        Node<T> cur = this.head;
        while(cur!=null)
        {
            diff.insertInOrder(cur.data);
            cur = cur.next;
        }
        cur = inter.head;
        while (cur!=null)
        {
            diff.remove(cur.data);
            cur = cur.next;
        }
		return diff;
	}
	public LinkedList<T> xor( LinkedList<T> other )
	{
        LinkedList<T> xor = new LinkedList<T>();
        LinkedList<T> union = this.union(other);
        LinkedList<T> inter = this.inter(other);
        Node<T> cur = union.head;
        while(cur!=null)
        {
            xor.insertInOrder(cur.data);
            cur = cur.next;
        }
        cur = inter.head;
        while (cur!=null)
        {
            xor.remove(cur.data);
            cur = cur.next;
        }
		return xor;
    }
} //END LINKEDLIST CLASS 

// A D D   N O D E   C L A S S  D O W N   H E R E 
// R E M O V E  A L L  P U B L I C  &  P R I V A T E (except toString)
// R E M O V E  S E T T E R S  &  G E T T E R S 
// M A K E  T O  S T R I N G  P U B L I C

class Node<T extends Comparable<T>> // tells compiler our incoming T type implements Comparable

{
  T data;
  Node<T> next;

  Node()
  {
    this( null, null );
  }

  Node(T data)
  {
    this( data, null );
  }

  Node(T data, Node<T> next)
  {
    this.data = data;
    this.next = next;
  }

  public String toString()
  {
	  return "" + data;
  } 
	 
} //EOF