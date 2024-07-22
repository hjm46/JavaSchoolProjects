package cs1501_p4;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class NetAnalysis implements NetAnalysis_Inter
{
    //graph must be an adgencency list
    Node[] netList;
    final int copSpeed = 230000000;
    final int optSpeed = 200000000;

    public NetAnalysis(String file)
    {
        if (file == null) return;

        try
        {
            Scanner infile = new Scanner ( new File (file));

            netList = new Node[infile.nextInt()];
            
            while(infile.hasNextLine())
            {
                int index = infile.nextInt();
                int vert = infile.nextInt();
                String type = infile.next();
                boolean cop = false;
                if(type.equals("copper"))
                    cop = true;

                int band = infile.nextInt();
                int len = infile.nextInt();
                add(index, vert, cop, band, len);
                add(vert, index, cop, band, len);
            }
            infile.close();
        }

        catch (Exception e)
        {
            return;
        }
    }

    private void add(int index, int vert, boolean cop, int band, int len)
    {
        Node head = netList[index];
        if(head == null || len < head.length)
        {
            netList[index] = new Node(vert, cop, band, len, head, index);
            return;
        }

        while(head.next!=null && len>head.next.length)
            head = head.next;
        head.next = new Node(vert, cop, band, len, head.next, index);
    }

    public ArrayList<Integer> lowestLatencyPath(int u, int w)
    {
        ArrayList<Integer> path = new ArrayList<Integer>();
        if (netList == null || u>=netList.length || u<0 || netList[u] == null) return null;
        DijkstraNode[] dGraph = new DijkstraNode[netList.length];
        for(int i = 0;i<netList.length; i+=1)
        {
            dGraph[i] = new DijkstraNode();
        }
        
        dGraph[u].wasSeen = true;
        dGraph[u].sd = 0;
        int count = 0;
        int start = 0;
        Node[] q = new Node[netList.length*2];
        int cur = u;
        count = enumNeighbors(q, count, cur, dGraph);

        while(start<q.length && q[start]!=null)
        {
            int i = q[start].vertex;
            double lat = q[start].length;
            if(netList[q[start].index].isCopper == false)
                lat = lat/optSpeed;
            else
                lat = lat/copSpeed;

            if(dGraph[i].sd > lat + dGraph[q[start].index].sd)
            {
                dGraph[i].sd = (int) lat + dGraph[q[start].index].sd;
                dGraph[i].prev = q[start].index;
            }

            cur = q[start].vertex;
            count = enumNeighbors(q, count, cur, dGraph);
            dGraph[i].wasSeen = true;
            start+=1;
        }

        if(w>=dGraph.length || w<0 || dGraph[w] == null) return null;
        DijkstraNode dest = dGraph[w];
        Node head = new Node(w);
        while(dest.prev != u && dest.prev!=-1)
        {
             Node i = new Node(dest.prev);
             i.next = head;
             head = i;
             dest = dGraph[dest.prev];
        }

        if(dest.prev!=u)  
            return null;

        if(head!=null)
        {
            Node source = new Node(u);
            source.next = head;
            head = source;
        }

        while(head!=null)
        {
            path.add(head.vertex);
            head = head.next;
        }

        return path;
    }

    private int enumNeighbors(Node[] q, int count, int cur, DijkstraNode[] dGraph)
    {
        Node i = netList[cur];
        while(i!=null && count<q.length)
        {
            if(dGraph[i.vertex].wasSeen == false)
            {
                q[count] = i;
                count+=1;
            }
            i = i.next;
        }
        return count;
    }

    public int bandwidthAlongPath(ArrayList<Integer> p) throws IllegalArgumentException
    {
        int band = 0;
        if(p==null)
            throw new IllegalArgumentException();
        for(int i= 0; i<p.size()-1; i++)
        {
            if (netList[p.get(i)] == null)
                throw new IllegalArgumentException();
            Node head = netList[p.get(i)];
            while(head!=null)
            {
                if(head.vertex == p.get(i+1))
                {
                    band = band + head.bandwidth;
                    break;
                }
                head = head.next;
            }
            if(head == null)
                throw new IllegalArgumentException();
        }

        return band;
    }

    public boolean copperOnlyConnected()
    {
        int[] arr = new int[netList.length];
        int arrCount = 0;
        DijkstraNode[] dGraph = new DijkstraNode[netList.length];
        for(int i = 0;i<netList.length; i+=1)
        {
            dGraph[i] = new DijkstraNode();
        }
        
        int j=0;
        while(netList[j].isCopper == false)
            j++;
        
        dGraph[j].wasSeen = true;
        dGraph[j].sd = 0;
        int count = 0;
        int start = 0;
        Node[] q = new Node[netList.length*2];
        int cur = j;
        count = enumNeighbors(q, count, cur, dGraph);
        int i = 0;

        while(start<q.length && q[start]!=null)
        {
            if(netList[q[start].index].isCopper == false)
            {
                start=+1;
                continue;
            }
            i = q[start].vertex;
            double lat = q[start].length;
            if(netList[q[start].index].isCopper == false)
                lat = lat/optSpeed;
            else
                lat = lat/copSpeed;

            if(dGraph[i].sd > lat + dGraph[q[start].index].sd)
            {
                dGraph[i].sd = (int) lat + dGraph[q[start].index].sd;
                dGraph[i].prev = q[start].index;
            }

            cur = q[start].vertex;
            count = enumNeighbors(q, count, cur, dGraph);
            dGraph[i].wasSeen = true;
            start+=1;
        }

        if(i>=dGraph.length || i<0 || dGraph[i] == null) return false;
        DijkstraNode dest = dGraph[i];
        Node head = new Node(i);
        while(dest.prev != j && dest.prev!=-1)
        {
             Node k = new Node(dest.prev);
             k.next = head;
             head = k;
             dest = dGraph[dest.prev];
        }

        if(dest.prev!=j)  
            return false;

        if(head!=null)
        {
            Node source = new Node(j);
            source.next = head;
            head = source;
        }

        while(head!=null)
        {
            arr[arrCount] = head.vertex;
            head = head.next;
        }

        if(arr == null || arr[0] == 0 && arr[1] == 0)
            return false;
        else
            return true;
    }

    public boolean connectedTwoVertFail()
    {
        for(int i=0; i<netList.length; i++)
        {
            for(int j=0; j<netList.length; j++)
            {
                boolean fail = twoVertFail_rec(i, j);
                if(fail == false)
                    return false;
            }
        }             
        return true;
    }

    private boolean twoVertFail_rec(int x, int y)
    {
        int[] path = new int[netList.length];
        int arrCount = 0;
        DijkstraNode[] dGraph = new DijkstraNode[netList.length];
        for(int i = 0;i<netList.length; i+=1)
        {
            dGraph[i] = new DijkstraNode();
        }

        int j=0;
        while(netList[j].index == x || netList[j].index == y)
            j++;
        
        dGraph[j].wasSeen = true;
        dGraph[j].sd = 0;
        int count = 0;
        int start = 0;
        Node[] q = new Node[netList.length*2];
        int cur = j;
        count = enumNeighbors(q, count, cur, dGraph);
        int i=0;

        while(start<q.length && q[start]!=null)
        {
            if(q[start].vertex == x || q[start].vertex == y)
            {
                start+=1;
                continue;
            }
            i = q[start].vertex;
            double lat = q[start].length;
            if(netList[q[start].index].isCopper == false)
                lat = lat/optSpeed;
            else
                lat = lat/copSpeed;

            if(dGraph[i].sd > lat + dGraph[q[start].index].sd)
            {
                dGraph[i].sd = (int) lat + dGraph[q[start].index].sd;
                dGraph[i].prev = q[start].index;
            }

            cur = q[start].vertex;
            count = enumNeighbors(q, count, cur, dGraph);
            dGraph[i].wasSeen = true;
            start+=1;
        }

        if(i>=dGraph.length || i<0 || dGraph[i] == null) return false;
        DijkstraNode dest = dGraph[i];
        Node head = new Node(i);
        while(dest.prev != j && dest.prev!=-1)
        {
             Node k = new Node(dest.prev);
             k.next = head;
             head = k;
             dest = dGraph[dest.prev];
        }

        if(dest.prev!=j)  
            return false;

        if(head!=null)
        {
            Node source = new Node(j);
            source.next = head;
            head = source;
        }

        while(head!=null)
        {
            path[arrCount] = head.vertex;
            head = head.next;
        }

        if(path == null || path[0] == 0 && path[1] == 0)
            return false;
        else
            return true;
    }

    public ArrayList<STE> lowestAvgLatST()
    {
        ArrayList<STE> mst = new ArrayList<STE>();
        int[] path = new int[netList.length];
        boolean[] visited = new boolean[netList.length];
        visited[0] = true;
        while(mst.size()<netList.length-1)
        {
            Node next = enumNeighborsLAL(visited);
            if(next.vertex == -1)
                return null;
            visited[next.vertex] = true;
            mst.add(new STE(next.index, next.vertex));            
        }

        return mst;
    }

    private Node enumNeighborsLAL(boolean[] visited)
    {
        double minLat = 2147483647;
        Node min = new Node();
        for(int j=0; j<visited.length; j++)
        {
            if(visited[j] == true)
            {
                Node i = netList[j];
                while(i!=null)
                {
                    if(visited[i.vertex] == false )
                    {
                        double lat = i.length;
                        if(netList[i.index].isCopper == false)
                            lat = lat/optSpeed;
                        else
                            lat = lat/copSpeed;

                        if(lat<minLat)
                        {
                            minLat = lat;
                            min = i;
                        }
                    }
                    i = i.next;
                }
            }
        }
        return min;
    }
}

class Node
{
    int vertex;
    boolean isCopper; //1 = yes, 0 = no;
    int bandwidth;
    int length;
    Node next;
    int index;

    Node()
    {
        this(-1, false, -1, -1, null, -1);
    }

    Node(int vert)
    {
        this(vert, false, -1, -1, null, -1);
    }

    Node(int vert, boolean cop, int band, int len, int i)
    {
        this(vert, cop, band, len, null, i);
    }

    Node(int vert, boolean cop, int band, int len, Node n, int i)
    {
        vertex = vert;
        isCopper = cop;
        bandwidth = band;
        length = len;
        next = n;
        index = i;
    } 
}

class DijkstraNode
{
    final int max_int = 2147483647;
    boolean wasSeen; //1= yes, 0 = no
    int sd; //shortest distance
    int prev;

    DijkstraNode()
    {
        wasSeen = false;
        sd = max_int;
        prev = -1;
    }
}