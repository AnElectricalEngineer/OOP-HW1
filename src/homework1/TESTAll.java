package homework1;

public class TESTAll
{
    public static void main(String[] args)
    {
        testGeoPoint();
        testGeoSegment();
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

    private static void testGeoSegment()
    {
        GeoPoint point1 = new GeoPoint(4500000, 25000);
        GeoPoint point2 = new GeoPoint(-25000, 25000);
        GeoPoint point3 = new GeoPoint(4500000, 4500000);
        GeoPoint point4 = new GeoPoint(-4500000, -4500000);

        GeoSegment seg1 = new GeoSegment("Seg1", point1, point2);
        GeoSegment seg2 = new GeoSegment("Seg2", point3, point4);

        //  Uncomment to test - program should fail
        //GeoSegment seg3 = new GeoSegment(null, point1, point2);
        //GeoSegment seg4 = new GeoSegment("seg4", null, point2);
        //GeoSegment seg5 = new GeoSegment("seg5", point1, null);

        assert seg1.getP1().equals(point1) && seg1.getP2().equals(point2);
        assert seg2.getP1().equals(point3) && seg2.getP2().equals(point4);
        assert !seg1.getP2().equals(seg2.getP1());
        System.out.println("getP1() and getP2() works");

        assert seg1.getName().equals("Seg1");
        assert seg2.getName().equals("Seg2");
        assert !seg2.getName().equals("ajgikgijklajgkal;");
        System.out.println("getName() works");

        GeoSegment seg6 = seg1.reverse();
        assert seg6.getName().equals(seg1.getName()) && seg6.getP1().equals(seg1.getP2())
                && seg6.getP2().equals(seg1.getP1());
        System.out.println("reverse() works");

        GeoPoint point5 = new GeoPoint(0, 45000000);
        GeoPoint point6 = new GeoPoint(45000000, 0);
        GeoSegment seg7 = new GeoSegment("Seg7", point5, point6);
        assert seg7.getLength() >= 6532.7 && seg7.getLength() <= 6532.8;
        System.out.println("getLength() works");

        GeoPoint point7 = new GeoPoint(-1, -2);
        GeoPoint point8 = new GeoPoint(3, -1);
        GeoSegment seg8 = new GeoSegment("Seg8", point7, point8);
        assert seg8.getHeading() >= 14.02 && seg8.getHeading() <= 14.04;
        System.out.println("getHeading() works");

        GeoSegment seg1Copy = new GeoSegment("Seg1", point1, point2);
        assert seg1Copy.equals(seg1);
        assert !seg1.equals(seg2);
        System.out.println("equals() works");

        assert seg7.hashCode() == 6532;
        System.out.println("hashCode() works");

        assert seg7.toString().equals("Name: Seg7, Start Point: Point - " +
                "latitude: 0, longitude: 45000000, End Point: Point - latitude: " +
                "45000000, longitude: 0");
        System.out.println("toString() works");
    }

    private static void testGeoFeature()
    {
        //TODO implement tests including test for checkRep()!
    }
}
