package homework1;

public class TESTGeoPoint
{
    public static void main(String[] args)
    {
        GeoPoint point1 = new GeoPoint(1000000, 1000000);
        System.out.println("The latitude is: " + point1.getLatitude());
        System.out.println("The longitude is: " + point1.getLongitude());
        System.out.println("Point one is equal to itself? " + point1.equals(point1));

        GeoPoint point2 = new GeoPoint(1000000, 1000000);
        System.out.println("Point one and Point two are equal? " + point1.equals(point2));

        GeoPoint point3 = new GeoPoint(1000000, 2000000);
        System.out.println("Point one and Point three are equal? " + point1.equals(point3));

        GeoPoint point4 = new GeoPoint(-91000000, 1000000);
    }
}
