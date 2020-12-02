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

        GeoPoint point10 = new GeoPoint(-2100002, 1300000);
        point9 = point10;
        assert point9.equals(point10);
        System.out.println("Point 9 and Point 10 are equal.");

        assert point10.toString().equals("Point - latitude: -2100002, " +
                "longitude: 1300000");
        System.out.println("The string representation of point10 is: Point - " +
                "latitude: -2100002, longitude: 1300000");

        GeoPoint point11 = new GeoPoint(45000000, 45000000);
        GeoPoint point12 = new GeoPoint(45000000, -45000000);
        double heading11to12 = point11.headingTo(point12);
        assert point11.headingTo(point12) >= 269.0 && point11.headingTo(point12) <= 271.0;
        System.out.println("The heading from point 11 to point 12 is: " + heading11to12);

        GeoPoint point13 = new GeoPoint(45000000, 45000000);
        GeoPoint point14 = new GeoPoint(0, 0);
        double heading13to14 = point13.headingTo(point14);
        assert point13.headingTo(point14) >= 224.0 && point13.headingTo(point14) <= 226.0;
        System.out.println("The heading from point 13 to point 14 is: " + heading13to14);

        GeoPoint point15 = new GeoPoint(-1, -2);
        GeoPoint point16 = new GeoPoint(3, -1);
        double heading15to16 = point15.headingTo(point16);
        assert point15.headingTo(point16) >= 14.02 && point15.headingTo(point16) <= 14.04;
        System.out.println("The heading from point 15 to point 16 is: " + heading15to16);

        double heading16to15 = point16.headingTo(point15);
        assert point16.headingTo(point15) >= 194.03 && point16.headingTo(point15) <= 194.04;
        System.out.println("The heading from point 16 to point 15 is: " + heading16to15);
    }
}
