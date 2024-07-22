package cs1501_p5;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;

public class ColorQuantizer implements ColorQuantizer_Inter
{
    ColorMapGenerator_Inter colMap;
    Pixel[][] arr;
    public ColorQuantizer(Pixel[][] pixelArray, ColorMapGenerator_Inter gen)
    {
        colMap = gen;
        arr = pixelArray;
    }

    public ColorQuantizer(String bmpFilename, ColorMapGenerator_Inter gen)
    {
        colMap = gen;
        try
        {
            BufferedImage image = ImageIO.read(new File(bmpFilename));
            arr = new Pixel[image.getWidth()][image.getHeight()];
            for(int i=0; i<image.getWidth(); i++)
            {
                for(int j=0; j<image.getHeight(); j++)
                {
                    int rgb = image.getRGB(i, j);
                    int red = (rgb >> 16) & 0x0000ff;
                    int green = (rgb >> 8) & 0x0000ff;
                    int blue = rgb & 0x0000ff;
                    arr[i][j] = new Pixel(red, green, blue);
                }
            }
        }
        
        catch(Exception e)
        {
            
        }
    }

    public Pixel[][] quantizeTo2DArray(int numColors) throws IllegalArgumentException
    {
        if(numColors<1) throw new IllegalArgumentException();

        Pixel[][] compArr = new Pixel[arr.length][arr[0].length];
        Pixel[] palette = colMap.generateColorPalette(arr, numColors);
        Map<Pixel, Pixel> map = colMap.generateColorMap(arr, palette);
        for(int i=0; i<arr.length; i++)
        {
            for(int j=0; j<arr[i].length; j++)
            {
                Pixel p = arr[i][j];
                compArr[i][j] = map.get(p);
            }
        }
        return compArr;
    }

    public void quantizeToBMP(String fileName, int numColors) throws IllegalArgumentException
    {
        if(numColors<1) throw new IllegalArgumentException();

        Pixel[][] compArr = quantizeTo2DArray(numColors);

        try
        {
            BufferedImage image = ImageIO.read(new File(fileName));
            for(int i=0; i<compArr.length; i++)
            {
                for(int j=0; j<compArr[i].length; j++)
                {
                    Pixel p = compArr[i][j];
                    int rgb = ((p.getRed()<<16) & 0xff0000) | ((p.getGreen()<<8) & 0xff00) | (p.getBlue() & 0xff);
                    image.setRGB(i, j, rgb);
                }
            }
        }

        catch(Exception e)
        {

        }
    }
}