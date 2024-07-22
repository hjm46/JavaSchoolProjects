package cs1501_p5;
import java.util.*;
public class SquaredEuclideanMetric implements DistanceMetric_Inter
{
    public SquaredEuclideanMetric()
    {

    }

    public double colorDistance(Pixel p1, Pixel p2)
    {
        double redD = Math.pow(p2.getRed() - p1.getRed(), 2);
        double greenD = Math.pow(p2.getGreen() - p1.getGreen(), 2);
        double blueD = Math.pow(p2.getBlue() - p1.getBlue(), 2);
        return redD + greenD + blueD;
    }
}