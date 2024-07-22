import java.io.*;
import java.util.*;

public class MyHashSet implements HS_Interface
{	private int numBuckets; // changes over life of the hashset due to resizing the array
	private Node[] bucketArray;
	private int size; // total # keys stored in set right now

	// THIS IS A TYPICAL AVERAGE BUCKET SIZE. IF YOU GET A LOT BIGGER THEN YOU ARE MOVING AWAY FROM (1)
	private final int MAX_ACCEPTABLE_AVE_BUCKET_SIZE = 20;  // **DO NOT CHANGE THIS NUMBER**

	public MyHashSet( int numBuckets )
	{	size=0;
		this.numBuckets = numBuckets;
		bucketArray = new Node[numBuckets]; // array of linked lists
		System.out.format("IN CONSTRUCTOR: INITIAL TABLE LENGTH=%d RESIZE WILL OCCUR EVERY TIME AVE BUCKET LENGTH EXCEEDS %d\n", numBuckets, MAX_ACCEPTABLE_AVE_BUCKET_SIZE );
	}

	public boolean add( String key )
	{
		// your code here to add the key to the table and ++ your size variable

		//checking for duplicates
		if (contains(key))	return false;

		//hashing
		int hash = 3797;
            for (int i=0 ; i< key.length() ; ++i )
                hash = Math.abs( hash * 17  + (short)key.charAt(i) );
		hash = hash % numBuckets;

		//insert in order
		Node cur = bucketArray[hash];
		if ((cur==null) || (key.compareTo(cur.data) < 0))
            bucketArray[hash] = new Node(key,cur);
		else
		{
			while (cur.next!=null)
			{
				if(key.compareTo(cur.next.data) < 0)
				{
					cur.next = new Node(key, cur.next);
					break;
				}
				cur = cur.next;
			}
			if (cur.next == null && key.compareTo(cur.data) > 0)
				cur.next = new Node(key, null);
		}

 		++size; // you just added a key to one of the lists
		if ( size > MAX_ACCEPTABLE_AVE_BUCKET_SIZE * this.numBuckets)
			upSize_ReHash_AllKeys(); // DOUBLE THE ARRAY .length THEN REHASH ALL KEYS

		return true;
	}
	public boolean contains( String key )
	{	
		if (bucketArray == null) return false;
		int hash = 3797;
            for (int i=0 ; i< key.length() ; ++i )
                hash = Math.abs( hash * 17  + (short)key.charAt(i) );
		hash = hash % bucketArray.length;
		Node cur = bucketArray[hash];

		if (cur == null) return false;

		while(cur.next != null) 
		{
			if(cur.data.equals(key))
				return true; 
			cur = cur.next;
		}
		if (cur.data.equals(key))   return true;

		return false;
	}

	private void upSize_ReHash_AllKeys()
	{	System.out.format("KEYS HASHED=%10d UPSIZING TABLE FROM %8d to %8d REHASHING ALL KEYS\n",
						   size, bucketArray.length, bucketArray.length*2  );
		Node[] biggerArray = new Node[ bucketArray.length * 2 ];
		this.numBuckets = biggerArray.length;
		for(Node list: bucketArray)
		{
			Node key = list;
			while(key!= null)
			{
				boolean hashed = false;
				int hash = 3797;
				for (int i=0 ; i< key.data.length() ; ++i )
					hash = Math.abs( hash * 17  + (short)key.data.charAt(i) );
				hash = hash % this.numBuckets;
				Node cur = biggerArray[hash];
				if ((cur==null) || (key.data.compareTo(cur.data) < 0))
					biggerArray[hash] = new Node(key.data, cur);
				else
				{
					while (hashed == false && cur.next!=null)
					{
						if(key.data.compareTo(cur.next.data) < 0)
						{
							cur.next = new Node(key.data, cur.next);
							hashed = true;
						}
						cur = cur.next;
					}
					if (hashed == false)
						cur.next = new Node(key.data, null);
				}
				key = key.next;
			}
		}

		bucketArray = biggerArray;
	} // END OF UPSIZE & REHASH

	public boolean remove( String key )
	{
		if (!contains(key)) return false;
		int hash = 3797;
            for (int i=0 ; i< key.length() ; ++i )
                hash = Math.abs( hash * 17  + (short)key.charAt(i) );
		hash = hash % bucketArray.length;
		Node cur = bucketArray[hash];
		if (cur == null) return false;
        if(cur.data.equals(key))
		{
			if(cur.next == null)
			{
				bucketArray[hash] = null;
				return true;
			}
			bucketArray[hash] = cur.next;
			return true;
		}

        while(cur.next.next!=null)
        {
            if (cur.next.data.equals(key))
            {
                cur.next = cur.next.next;
                return true;
            }
            cur = cur.next;
        }

        if (cur.next.data.equals(key))
		{
			cur.next = null;
			return true;
		}
		return false;

	}

	public boolean isEmpty()
	{
		return bucketArray == null ? true: false;
	}

	public void clear()
	{
		for (int i=0; i<bucketArray.length; i++)
			bucketArray[i] = null;
	}

	public int size()
	{
		return size;
	}
	
} //END MyHashSet CLASS

class Node
{	String data;
	Node next;
	public Node ( String data, Node next )
	{ 	
		this.data = data;
		this.next = next;
	}
}


