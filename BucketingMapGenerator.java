package cs1501_p5;
import java.util.*;

public class BucketingMapGenerator implements ColorMapGenerator_Inter
{
    public Pixel[] generateColorPalette(Pixel[][] pixelArr, int numColors) throws IllegalArgumentException
    {
        if(numColors<1) throw new IllegalArgumentException();
        Pixel[] arr = new Pixel[numColors];
        int step = 256/numColors;
        if(numColors == 1)
        {
            arr[0] = new Pixel(step/2, 0, 0);
            return arr;
        }
        for(int i=0; i<numColors; i++)
        {
            int red = step*i + step/2;
            if(numColors%2==1 && i>=2)
                red = red + i-(i/2);

            int blue = (red*2)%256;
            if(i%2 == 1 && (red*2)/256<1)
                blue = blue + 1;
            if(i%2 == 0 && (red*2)/256>=1)
                blue = blue+1;

            int green = ((blue*2) % 256) + 1;
            if(red>=128)
                green = green -1;

            arr[i] = new Pixel(red, green, blue);
            if(numColors%2 == 0)
                arr[i] = new Pixel(red, 0, 0);
            else
                arr[i] = new Pixel(red, green, blue);
        }
        return arr;
    }

    public Map<Pixel, Pixel> generateColorMap(Pixel[][] pixelArr, Pixel[] initialColorPalette)
    {
        Map<Pixel, Pixel> colorMap = new HashMap<Pixel, Pixel>();
        for(int i=0; i<pixelArr.length; i++)
        {
            for(int j=0; j<pixelArr[i].length; j++)
            {
                Pixel p = pixelArr[i][j];
                int k=0;
                Pixel cur = initialColorPalette[k];
                int step = cur.getRed();
                if(p.getRed()<=cur.getRed())
                    colorMap.put(p, new Pixel(cur.getRed(), cur.getGreen(), cur.getBlue()));
                else
                {
                    for(k=1; k<initialColorPalette.length; k++)
                    {
                        if(p.getRed()<initialColorPalette[k].getRed()+step)
                        {
                            cur = initialColorPalette[k];
                            colorMap.put(p, new Pixel(cur.getRed(), cur.getGreen(), cur.getBlue()));
                            break;
                        }

                    }
                }
            }
        }

        return colorMap;
    }
}