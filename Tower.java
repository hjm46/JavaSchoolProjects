import java.io.*;
import java.util.*;

public class Tower<T>
{
	private Disk<T> base;  // pointer to first disk at BASE of the tower (i.e. the old head pointer)
	private Disk<T> top;   // pointer to last disk at TOP of the tower   (i.e. the old tail pointer)

	public Tower()
	{	base = null; // compiler does this anyway. just for emphasis
	}

	public boolean empty()
	{
		return (base==null);
	}

	// i.e. the old insertAtTail or now insertAtTop we call it a PUSH
	public void push(T label)
	{
        if (base == null)
        {
            base = new Disk<T>(label);
            top = base;
            return;
        }
        top.next = new Disk<T>(label);
        top = top.next;
        return; 
	}

	// i.e. the old removeAtTail or now removeAtTop we call it a POP
	public Disk<T> pop() throws Exception
	{
        if (base == null)   
        {
            System.out.println("FATAL ERROR");
            return null;
        }
        
        Disk<T> temp = top;
        Disk<T> cur = base;
        if (base.next == null) 
        {
            base = null;
            return top;
        }
        while(cur.next.next!=null)
            cur = cur.next;
        cur.next = null;
        top = cur;
		return temp;
	}

	// prints the tower base to top, left to right,  respectively //
	public String toString()
	{	if (base == null ) 	return "EMPTY\t";
		String toString = "";
		for ( Disk<T> curr = base; curr != null ; curr=curr.next )
			toString += curr.label + " ";

		return toString;
	}
} // END TOWER CLASS

/*###############################################################################*/

class Disk<T>
{
	T label;
	Disk<T> next;

	Disk(T data)
	{	this( data, null );
	}

	Disk(T label, Disk<T> next)
	{	this.label = label;
		this.next = next;
	}

} // END DISK CLASS