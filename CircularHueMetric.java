package cs1501_p5;

public class CircularHueMetric implements DistanceMetric_Inter
{
    public CircularHueMetric()
    {

    }
    
    public double colorDistance(Pixel p1, Pixel p2)
    {
        if(p1.getHue() == p2.getHue())
            return 0;
        double hueS = Math.abs(p1.getHue()-p2.getHue());
        int close1 = -1;
        if(p1.getHue()<180)
            close1 = p1.getHue();
        else
            close1 = 360 - p1.getHue();

        int close2 = -1;
        if(p2.getHue()<180)
            close2 = p2.getHue();
        else
            close2 = 360 - p2.getHue();

        double hueA = close1 + close2;
        
        return (hueA<hueS)? hueA:hueS;
    }
}