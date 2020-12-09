package homework1;

import java.util.Iterator;

public class TESTAll
{
    public static void main(String[] args)
    {
        testGeoPoint();
        testGeoSegment();
        testGeoFeature();
        testRoute();
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

        assert point1.distanceTo(point2) >= 0.0 && point1.distanceTo(point2) <= 0.1;
        System.out.println("The distance between point 1 and point 2 is: " + point1.distanceTo(point2));
        assert point2.distanceTo(point1) >= 0.0 && point2.distanceTo(point1) <= 0.1;
        System.out.println("The distance between point 2 and point 1 is: " + point2.distanceTo(point1));

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
        assert !point9.equals(null);


        assert point10.toString().equals("Point - latitude: -2100002, " +
                "longitude: 1300000");
        System.out.println("The string representation of point10 is: Point - " +
                "latitude: -2100002, longitude: 1300000");

        GeoPoint point11 = new GeoPoint(45000000, 45000000);
        GeoPoint point12 = new GeoPoint(45000000, -45000000);
        double heading11to12 = point11.headingTo(point12);
        assert heading11to12 >= 269.0 && heading11to12 <= 271.0;
        System.out.println("The heading from point 11 to point 12 is: " + heading11to12);

        GeoPoint point13 = new GeoPoint(45000000, 45000000);
        GeoPoint point14 = new GeoPoint(0, 0);
        double heading13to14 = point13.headingTo(point14);
        assert heading13to14 >= 224.0 && heading13to14 <= 226.0;
        System.out.println("The heading from point 13 to point 14 is: " + heading13to14);

        GeoPoint point15 = new GeoPoint(-1, -2);
        GeoPoint point16 = new GeoPoint(3, -1);
        double heading15to16 = point15.headingTo(point16);
        assert heading15to16 >= 14.02 && heading15to16 <= 14.04;
        System.out.println("The heading from point 15 to point 16 is: " + heading15to16);

        double heading16to15 = point16.headingTo(point15);
        assert heading16to15 >= 194.03 && heading16to15 <= 194.04;
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

        GeoPoint point9 = new GeoPoint(1, -2);
        GeoSegment seg9 = new GeoSegment("Seg9", point7, point9);
        assert seg9.getHeading() >= 0.0 && seg9.getHeading() <= 0.1;
        GeoSegment seg10 = new GeoSegment("Seg10", point9, point7);
        assert seg10.getHeading() >= 179.9 && seg9.getHeading() <= 181.1;

        GeoPoint testPoint1 = new GeoPoint(45, 45);
        GeoPoint testPoint2 = new GeoPoint(45, 45);
        GeoSegment testSeg1 = new GeoSegment("testSeg", testPoint1, testPoint2);
        double testSeg1Heading = testSeg1.getHeading();
        assert testSeg1Heading >= 0.0 && testSeg1Heading <= 0.1;
        System.out.println("getHeading() works");

        GeoSegment seg1Copy = new GeoSegment("Seg1", point1, point2);
        assert seg1Copy.equals(seg1);
        assert !seg1.equals(seg2);
        assert !seg1.equals(null);
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
        GeoPoint point11 = new GeoPoint(45000000, 45000000);
        GeoPoint point12 = new GeoPoint(45000000, -45000000);
        GeoSegment seg1 = new GeoSegment("Seg1", point11, point12);

        GeoPoint point13 = new GeoPoint(45000000, -45000000);
        GeoPoint point14 = new GeoPoint(0, 0);
        GeoSegment seg2 = new GeoSegment("Seg1", point13, point14);

        GeoFeature feature1 = new GeoFeature(seg1);
        String nameFeature1 = feature1.getName();
        assert nameFeature1.equals(seg1.getName());
        System.out.println("The name of Feature 1 is: " + nameFeature1);

        assert feature1.getStart().equals(point11);
        assert feature1.getEnd().equals(point12);
        System.out.println("Feature 1 begins at: " + feature1.getStart());
        System.out.println("Feature 1 ends at: " + feature1.getEnd());

        assert feature1.getStartHeading() == seg1.getHeading();
        assert feature1.getEndHeading() == seg1.getHeading();
        System.out.println("Feature 1's beginning heading is: " + feature1.getStartHeading());
        System.out.println("Feature 1's ending heading is: " + feature1.getEndHeading());

        GeoPoint testPoint1 = new GeoPoint(45, 45);
        GeoPoint testPoint2 = new GeoPoint(45, 45);
        GeoSegment testSeg1 = new GeoSegment("testSeg", testPoint1, testPoint2);
        GeoFeature testFeature1 = new GeoFeature(testSeg1);
        GeoPoint testPoint3 = new GeoPoint(45, 45);
        GeoSegment testSeg2 = new GeoSegment("testSeg", testPoint2, testPoint3);
        testFeature1 = testFeature1.addSegment(testSeg2);
        double testFeature1StartHeading = testFeature1.getStartHeading();
        double testFeature1EndHeading = testFeature1.getEndHeading();
        assert testFeature1StartHeading >= 0.0 && testFeature1StartHeading <= 0.1;
        assert testFeature1EndHeading >= 0.0 && testFeature1EndHeading <= 0.1;

        assert feature1.getLength() == seg1.getLength();
        System.out.println("Feature 1's length is: " + feature1.getLength());

        GeoFeature feature2 = feature1.addSegment(seg2);
        String nameFeature2 = feature2.getName();
        assert nameFeature2.equals(seg1.getName());
        assert nameFeature2.equals(seg2.getName());
        System.out.println("The name of Feature 2 is: " + nameFeature2);

        assert feature2.getStart().equals(point11);
        assert !feature2.getStart().equals(point12);
        assert feature2.getEnd().equals(point14);
        assert !feature2.getEnd().equals(point13);
        System.out.println("Feature 2 begins at: " + feature2.getStart());
        System.out.println("Feature 2 ends at: " + feature2.getEnd());

        assert feature2.getStartHeading() == seg1.getHeading();
        assert feature2.getEndHeading() == seg2.getHeading();
        System.out.println("Feature 2's beginning heading is: " + feature2.getStartHeading());
        System.out.println("Feature 2's ending heading is: " + feature2.getEndHeading());

        assert feature2.getLength() == seg1.getLength() + seg2.getLength();
        System.out.println("Feature 2's length is: " + feature2.getLength());

        Iterator<GeoSegment> it = feature2.getGeoSegments();
        while(it.hasNext())
        {
            System.out.println(it.next().toString());
        }

        assert feature2.toString().equals("GeoFeature - Name: Seg1");
        assert !feature2.toString().equals("GeoFeature - Name: Seg2");

        assert feature2.hashCode() == (int)(seg1.getLength() + seg2.getLength());

        GeoPoint point15 = new GeoPoint(45000000, 45000000);
        GeoPoint point16 = new GeoPoint(45000000, -45000000);
        GeoSegment seg3 = new GeoSegment("Seg1", point15, point16);

        GeoPoint point17 = new GeoPoint(45000000, -45000000);
        GeoPoint point18 = new GeoPoint(0, 0);
        GeoSegment seg4 = new GeoSegment("Seg1", point17, point18);

        GeoFeature feature3 = new GeoFeature(seg3);
        GeoFeature feature4 = feature3.addSegment(seg4);

        assert feature2.equals(feature4);
        assert feature1.equals(feature3);
        assert !feature1.equals(feature2);
    }

    private static void testRoute()
    {
        GeoPoint point11 = new GeoPoint(45000000, 45000000);
        GeoPoint point12 = new GeoPoint(45000000, -45000000);
        GeoSegment seg1 = new GeoSegment("Seg1", point11, point12);
        Route route1 = new Route(seg1);
        assert route1.getLength() == seg1.getLength();
        assert route1.getStart().equals(point11);
        assert route1.getEnd().equals(point12);
        assert route1.getStartHeading() == seg1.getHeading();
        assert route1.getEndHeading() == seg1.getHeading();

        GeoPoint point13 = new GeoPoint(45000000, -45000000);
        GeoPoint point14 = new GeoPoint(0, 0);
        GeoSegment seg2 = new GeoSegment("Seg1", point13, point14);

        route1 = route1.addSegment(seg2);
        assert route1.getLength() == seg1.getLength() + seg2.getLength();
        assert route1.hashCode() == (int)(seg1.getLength() + seg2.getLength());
        assert route1.getStart().equals(point11);
        assert route1.getEnd().equals(point14);
        assert route1.getStartHeading() == seg1.getHeading();
        assert route1.getEndHeading() == seg2.getHeading();

        GeoPoint testPoint1 = new GeoPoint(45, 45);
        GeoPoint testPoint2 = new GeoPoint(45, 45);
        GeoSegment testSeg1 = new GeoSegment("testSeg", testPoint1, testPoint2);
        GeoPoint testPoint3 = new GeoPoint(45, 45);
        GeoSegment testSeg2 = new GeoSegment("testSeg", testPoint2, testPoint3);
        Route testRoute1 = new Route(testSeg1);
        testRoute1.addSegment(testSeg2);
        double testRoute1StartHeading = testRoute1.getStartHeading();
        double testRoute1EndHeading = testRoute1.getEndHeading();
        assert testRoute1StartHeading >= 0.0 && testRoute1StartHeading <= 0.1;
        assert testRoute1EndHeading >= 0.0 && testRoute1EndHeading <= 0.1;

        GeoPoint point15 = new GeoPoint(0, 0);
        GeoPoint point16 = new GeoPoint(35000000, 25000000);
        GeoSegment seg3 = new GeoSegment("Seg2", point15, point16);

        GeoPoint point17 = new GeoPoint(45000000, -45000000);
        GeoPoint point18 = new GeoPoint(0, 0);
        GeoSegment seg4 = new GeoSegment("Seg2", point17, point18);

        route1 = route1.addSegment(seg3);
        assert route1.getLength() == seg1.getLength()+seg2.getLength()+seg3.getLength();
        assert route1.getStart().equals(point11);
        assert route1.getEnd().equals(point16);
        assert route1.getStartHeading() == seg1.getHeading();
        assert route1.getEndHeading() == seg3.getHeading();

        Iterator <GeoSegment> segments = route1.getGeoSegments();
        while(segments.hasNext())
        {
            String temp = segments.next().toString();
            System.out.println(temp);
        }

        Iterator<GeoFeature> features = route1.getGeoFeatures();
        while(features.hasNext())
        {
            String temp = features.next().toString();
            System.out.println(temp);
        }

        Route route2 = new Route(seg4);
        assert !route1.equals(route2);

        GeoPoint point20 = new GeoPoint(45000000, 45000000);
        GeoPoint point21 = new GeoPoint(45000000, -45000000);
        GeoSegment seg20 = new GeoSegment("Seg1", point20, point21);
        Route route3 = new Route(seg20);
        GeoPoint point23 = new GeoPoint(45000000, -45000000);
        GeoPoint point24 = new GeoPoint(0, 0);
        GeoSegment seg21 = new GeoSegment("Seg1", point23, point24);
        route3 = route3.addSegment(seg21);
        GeoPoint point25 = new GeoPoint(0, 0);
        GeoPoint point26 = new GeoPoint(35000000, 25000000);
        GeoSegment seg23 = new GeoSegment("Seg2", point25, point26);
        route3 = route3.addSegment(seg23);

        assert route1.equals(route3);
    }
}
