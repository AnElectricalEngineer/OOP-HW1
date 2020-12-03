package homework1;

import java.lang.Math;

/**
 * A GeoPoint is a point on the earth. GeoPoints are immutable.
 * <p>
 * North latitudes and east longitudes are represented by positive numbers.
 * South latitudes and west longitudes are represented by negative numbers.
 * <p>
 * The code may assume that the represented points are nearby the Technion.
 * <p>
 * <b>Implementation direction</b>:<br>
 * The Ziv square is at approximately 32 deg. 46 min. 59 sec. N
 * latitude and 35 deg. 0 min. 52 sec. E longitude. There are 60 minutes
 * per degree, and 60 seconds per minute. So, in decimal, these correspond
 * to 32.783098 North latitude and 35.014528 East longitude. The
 * constructor takes integers in millionths of degrees. To create a new
 * GeoPoint located in the the Ziv square, use:
 * <tt>GeoPoint zivCrossroad = new GeoPoint(32783098,35014528);</tt>
 * <p>
 * Near the Technion, there are approximately 110.901 kilometers per degree
 * of latitude and 93.681 kilometers per degree of longitude. An
 * implementation may use these values when determining distances and
 * headings.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   latitude :  real        // latitude measured in degrees
 *   longitude : real        // longitude measured in degrees
 * </pre>
 **/
public class GeoPoint
{

    /**
     * Minimum value the latitude field can have in this class.
     **/
    public static final int MIN_LATITUDE = -90 * 1000000;

    /**
     * Maximum value the latitude field can have in this class.
     **/
    public static final int MAX_LATITUDE = 90 * 1000000;

    /**
     * Minimum value the longitude field can have in this class.
     **/
    public static final int MIN_LONGITUDE = -180 * 1000000;

    /**
     * Maximum value the longitude field can have in this class.
     **/
    public static final int MAX_LONGITUDE = 180 * 1000000;

    /**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
    public static final double KM_PER_DEGREE_LATITUDE = 110.901;

    /**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
    public static final double KM_PER_DEGREE_LONGITUDE = 93.681;

    /**
     * Conversion factor for converting millionths of a degree into 1 degree.
     * Divide x [millionths of a degree] by CONVERT_FACTOR to get
     * x [degrees].
     */
    public static final double CONVERT_FACTOR = 1000000.0;

    // Implementation hint:
    // Doubles and floating point math can cause some problems. The exact
    // value of a double can not be guaranteed except within some epsilon.
    // Because of this, using doubles for the equals() and hashCode()
    // methods can have erroneous results. Do not use floats or doubles for
    // any computations in hashCode(), equals(), or where any other time
    // exact values are required. (Exact values are not required for length
    // and distance computations). Because of this, you should consider
    // using ints for your internal representation of GeoPoint.

    private final int latitude_;    // N/S latitude in millionths of degrees
    private final int longitude_;    // E/W longitude in millionths of degrees

    //	Abstraction Function:
    //	A GeoPoint is a point on the face of the Earth represented by (p
    //	.latitude_, p.longitude_) where p.latitude_ is the latitude of the
    //	point in millionths of degrees, and p.longitude_ is the longitude in
    //	millionths of degrees.

    //	Representation Invariant:
    //	For every GeoPoint p, MIN_LATITUDE <= p.latitude_ <=
    //	MAX_LATITUDE and MIN_LONGITUDE <= p.longitude_ <= MAX_LONGITUDE.


    /**
     * Constructs GeoPoint from a latitude and longitude.
     *
     * @requires the point given by (latitude, longitude) in millionths
     * of a degree is valid such that:
     * (MIN_LATITUDE <= latitude <= MAX_LATITUDE) and
     * (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE)
     * @effects constructs a GeoPoint from a latitude and longitude
     * given in millionths of degrees.
     **/
    public GeoPoint(int latitude, int longitude)
    {
        this.latitude_ = latitude;
        this.longitude_ = longitude;
        checkRep();
    }


    /**
     * Returns the latitude of this.
     *
     * @return the latitude of this in millionths of degrees.
     */
    public int getLatitude()
    {
        checkRep();
        return latitude_;
    }


    /**
     * Returns the longitude of this.
     *
     * @return the latitude of this in millionths of degrees.
     */
    public int getLongitude()
    {
        checkRep();
        return longitude_;
    }


    /**
     * Computes the distance between GeoPoints.
     *
     * @return the distance from this to gp, using the flat-surface, near
     * the Technion approximation in km.
     * @requires gp != null
     **/
    public double distanceTo(GeoPoint gp)
    {
        checkRep();

        //	The location (x and y coordinates) of this and gp on the face of
        //	the Earth in units of km instead of degrees. Millionths of
        //	degrees are converted to degrees
        double y1 =
                ((double) latitude_ / CONVERT_FACTOR) *
                        KM_PER_DEGREE_LATITUDE;
        double x1 =
                ((double) longitude_ / CONVERT_FACTOR) *
                        KM_PER_DEGREE_LONGITUDE;
        double y2 =
                ((double) gp.latitude_ / CONVERT_FACTOR) *
                        KM_PER_DEGREE_LATITUDE;
        double x2 =
                ((double) gp.longitude_ / CONVERT_FACTOR) *
                        KM_PER_DEGREE_LONGITUDE;

        double deltaY = y2 - y1;
        double deltaX = x2 - x1;

        checkRep();

        return Math.sqrt(Math.pow(deltaY, 2) + Math.pow(deltaX, 2));
    }


    /**
     * Computes the compass heading between GeoPoints.
     *
     * @return the compass heading h from this to gp, in degrees, using the
     * flat-surface, near the Technion approximation, such that
     * 0 <= h < 360. In compass headings, north = 0, east = 90,
     * south = 180, and west = 270.
     * @requires gp != null && !this.equals(gp)
     **/
    public double headingTo(GeoPoint gp)
    {
        //	Implementation hints:
        // 1. You may find the method Math.atan2() useful when
        // implementing this method. More info can be found at:
        // http://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
        //
        // 2. Keep in mind that in our coordinate system, north is 0
        // degrees and degrees increase in the clockwise direction. By
        // mathematical convention, "east" is 0 degrees, and degrees
        // increase in the counterclockwise direction.

        checkRep();
        //Convert measurements from millionths of degrees to degrees
        final double latitude1 = (double) latitude_ / CONVERT_FACTOR;
        final double longitude1 = (double) longitude_ / CONVERT_FACTOR;
        final double latitude2 = (double) gp.latitude_ / CONVERT_FACTOR;
        final double longitude2 = (double) gp.longitude_ / CONVERT_FACTOR;

        // 	Calculate the heading h in radians
        final double y = longitude2 - longitude1;
        final double x = latitude2 - latitude1;
        final double theta = Math.atan2(y, x);

        //  Convert the heading from radians to degrees and normalize to range
        //  0 <= h <= 360.
        final double h = atan2ToDegrees(theta);
        checkRep();
        return h;
    }


    /**
     * Compares the specified Object with this GeoPoint for equality.
     *
     * @return gp != null && (gp instanceof GeoPoint) &&
     * gp.latitude = this.latitude && gp.longitude = this.longitude
     **/
    public boolean equals(Object gp)
    {
        checkRep();
        if ((gp != null) && !(gp instanceof GeoPoint))
        {
            return false;
        }
        GeoPoint point = (GeoPoint) gp;
        checkRep();
        return point.latitude_ == latitude_ && point.longitude_ == longitude_;
    }


    /**
     * Returns a hash code value for this GeoPoint.
     *
     * @return a hash code value for this GeoPoint.
     **/
    public int hashCode()
    {
        checkRep();
        return latitude_ + longitude_;
    }


    /**
     * Returns a string representation of this GeoPoint.
     *
     * @return a string representation of this GeoPoint.
     **/
    public String toString()
    {
        checkRep();
        return "Point - latitude: " + latitude_ + ", longitude: " + longitude_;
    }

    /**
     * Checks that the representation invariant is maintained.
     *
     * @effects Checks that the representation invariant is maintained.
     * Aborts the program if rep invariant is broken.
     */
    private void checkRep()
    {
        assert latitude_ >= MIN_LATITUDE && latitude_ <= MAX_LATITUDE :
                "Invalid latitude value";
        assert longitude_ >= MIN_LONGITUDE && longitude_ <= MAX_LONGITUDE :
                "Invalid longitude value";
    }

    /**
     * Converts the output theta of an atan2 calculation which is in the range
     * -pi <= theta <= pi [radians] to a normalized value: 0 <= fi <= 360
     * [degrees].
     *
     * @requires theta != null and -pi <= theta <= pi
     * @modifies none
     * @effects Converts the output theta of an atan2 calculation which is in
     * the range -pi <= theta <= pi [radians] to a normalized value: 0 <= fi <=
     * 360 [degrees].
     */
    private double atan2ToDegrees(double theta)
    {
        assert theta >= -Math.PI && theta <= Math.PI; //TODO maybe add
        // epsilon because comparing doubles
        final double degreesInPi = 180.0;
        final double degreesIn2Pi = 360.0;
        return (theta * (degreesInPi / Math.PI) + degreesIn2Pi) % degreesIn2Pi;
    }
}

