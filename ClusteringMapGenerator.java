package cs1501_p5;
import java.util.*;

public class ClusteringMapGenerator implements ColorMapGenerator_Inter
{
    DistanceMetric_Inter m;
    public ClusteringMapGenerator(DistanceMetric_Inter metric)
    {
       m = metric;
    }

    public Pixel[] generateColorPalette(Pixel[][] pixelArr, int numColors) throws IllegalArgumentException
    {
        if(numColors<1) throw new IllegalArgumentException();
    
        ArrayList<Pixel> arr = new ArrayList<Pixel>();
        arr.add(pixelArr[0][0]);
        Pixel maxP = new Pixel(0,0,0);
        double maxD = 0;
        while(arr.size() < numColors)
        {
            for(int j=0; j<pixelArr.length; j++)
            {
                for(int k=0; k<pixelArr[j].length; k++)
                {
                    double dist = m.colorDistance(arr.get(0), pixelArr[j][k]);
                    if(arr.contains(pixelArr[j][k])==false)
                    {
                        if(dist>maxD)
                        {
                            maxD = dist;
                            maxP = pixelArr[j][k];
                        }
                        else if(dist==maxD)
                        {
                            Pixel p2 = pixelArr[j][k];
                            int sum1 = ((maxP.getRed()<<16) & 0xff0000) | ((maxP.getGreen()<<8) & 0xff00) | (maxP.getBlue() & 0xff);
                            int sum2 = ((p2.getRed()<<16) & 0xff0000) | ((p2.getGreen()<<8) & 0xff00) | (p2.getBlue() & 0xff);
                            if(sum2>sum1)
                            {
                                maxD = dist;
                                maxP = p2;
                            }
                        }
                    }
                }
            }
            if(arr.contains(maxP) == false && maxD !=0)
            {
                arr.add(maxP);
                maxP = new Pixel(0,0,0);
                maxD = 0;
            }
        }
        
        Pixel[] arrFin = new Pixel[numColors];
        for(int i=0; i<arr.size(); i++)
            arrFin[i] = arr.get(i);

        return arrFin;
    }

    public Map<Pixel, Pixel> generateColorMap(Pixel[][] pixelArr, Pixel[] initialColorPalette)
    {
        Map<Pixel, LinkedList<Pixel>> map = new HashMap<Pixel, LinkedList<Pixel>>();
        boolean cont = true;
        while(cont)
        {
            map.clear();
            for(Pixel e: initialColorPalette)
            {
                LinkedList<Pixel> tree = new LinkedList<Pixel>();
                map.put(e, tree);
            }
            
            for(int i=0; i<pixelArr.length; i++)
            {
                for(int j=0; j<pixelArr[i].length; j++)
                {
                    double min = Integer.MAX_VALUE;
                    Pixel cent = new Pixel(0,0,0);
                    Pixel p = pixelArr[i][j];
                    for(int k=0; k<initialColorPalette.length; k++)
                    {
                        Pixel c = initialColorPalette[k];
                        double dist = m.colorDistance(p, c);
                        if(dist<min)
                        {
                            min = dist;
                            cent = c;
                        }

                        else if(dist==min)
                        {
                            int sum1 = ((cent.getRed()<<16) & 0xff0000) | ((cent.getGreen()<<8) & 0xff00) | (cent.getBlue() & 0xff);
                            int sum2 = ((c.getRed()<<16) & 0xff0000) | ((c.getGreen()<<8) & 0xff00) | (c.getBlue() & 0xff);
                            if(sum2>sum1)
                                cent = c;
                        }
                    }
                
                    if(map.get(cent).contains(p) == false)
                    {
                        map.get(cent).add(p);
                    }
                }
            }
            
            Pixel[] temp = new Pixel[initialColorPalette.length];
            for(int m=0; m<initialColorPalette.length; m++)
            {
                Pixel e = initialColorPalette[m];
                LinkedList<Pixel> pix = map.get(e);
                double red = 0;
                double green = 0;
                double blue = 0;
                for(Pixel q: pix)
                {
                    red = q.getRed() + red;
                    green = q.getGreen() + green;
                    blue = q.getBlue() + blue;
                }

                temp[m] = new Pixel((int)(red/pix.size()), (int)(green/pix.size()), (int)(blue/pix.size()));
            }

            if(Arrays.equals(initialColorPalette, temp))
                cont = false;
            else
                initialColorPalette = temp;
        }

        Map<Pixel, Pixel> finMap = new HashMap<Pixel, Pixel>();
        for(Pixel e: initialColorPalette)
        {
            LinkedList<Pixel> pix = map.get(e);
            if(pix!=null)
            {
                for(Pixel q: pix)
                {
                    finMap.put(q, e);
                }
            }
        }

        return finMap;
    }
}