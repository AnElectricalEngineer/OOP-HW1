package homework1;

public class TESTAll
{
    public static void main(String[] args)
    {
        testGeoPoint();
    }

    private static void testGeoPoint()
    {
        GeoPoint point1 = new GeoPoint(1000000, 1000000);

        assert point1.getLatitude() == 1000000 && point1.getLongitude() == 1000000;
        System.out.println("The latitude is: " + point1.getLatitude());
        System.out.println("The longitude is: " + point1.getLongitude());

        assert point1.equals(point1);
        System.out.println("Point one is equal to itself? " + point1.equals(point1));

        GeoPoint point2 = new GeoPoint(1000000, 1000000);
        assert point1.equals(point2);
        System.out.println("Point one and Point two are equal? " + point1.equals(point2));

        GeoPoint point3 = new GeoPoint(1000000, 2000000);
        assert !point1.equals(point3);
        System.out.println("Point one and Point three are equal? " + point1.equals(point3));

        // Uncomment to test - program should fail here
        //GeoPoint point4 = new GeoPoint(-91000000, 1000000);

        GeoPoint point5 = new GeoPoint(0, 45000000);
        GeoPoint point6 = new GeoPoint(45000000, 0);
        assert point6.distanceTo(point5) >= 6532.7 && point6.distanceTo(point5) <= 6532.8;
        System.out.println("The distance between point 5 and point 6 is: " + point6.distanceTo(point5));

        GeoPoint point7 = new GeoPoint(45000000, 0);
        assert point7.distanceTo(point6) >= 0.0 && point7.distanceTo(point6) <= 0.1;
        System.out.println("The distance between point 6 and point 7 is: " + point7.distanceTo(point6));

        GeoPoint point8 = new GeoPoint(-2100000, 1500000);
        GeoPoint point9 = new GeoPoint(-2300000, -1000000);
        assert point9.distanceTo(point8) >= 235.2 && point9.distanceTo(point8) <= 235.3;
        System.out.println("The distance between point 8 and point 9 is: " + point9.distanceTo(point8));

        assert point8.hashCode() == -600000;
        System.out.println("The hash code is: " + point8.hashCode());

        assert point1.hashCode() == point2.hashCode();
        System.out.println("Point 1 and Point 2 are equal, so their hashcode " +
                "is equal");


        //  TODO add tests for getHeading and toString.

    }
}
