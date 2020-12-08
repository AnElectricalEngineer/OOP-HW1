package homework1;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments
 * are immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street,
 * boundary, or other feature.
 * As an example usage, this map
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Hanita
 *               |
 *               z
 * </pre>
 * could be represented by the following GeoSegments:
 * ("Trumpeldor Avenue", a, i), ("Trumpeldor Avenue", z, i),
 * ("Hanita", i, j), and ("Hanita", j, k).
 * </p>
 *
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical
 * GeoPoint endpoints. Equality between GeoSegment objects requires
 * that the names be equal String objects and the end points be equal
 * GeoPoint objects.
 * </p>
 *
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   name : String       // name of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment  {

    private final String name_;
    private final GeoPoint startPoint_;
    private final GeoPoint endPoint_;

    //	Abstraction Function:
    //	A GeoSegment is a straight line segment on the face of the Earth
    //	represented by (g.name_, g.startPoint_, g.endPoint_) where g.name_ is a
    //	string which represents the name of the GeoSegment. The line segment
    //	begins at the GeoPoint g.startPoint_ and ends at the GeoPoint g
    //	.endPoint_.

    //	Representation Invariant:
    //	For every GeoSegment g, g.name_ != null && g.p1_ != null && g.p2_ !=
    //	null.


    /**
     * Constructs a new GeoSegment with the specified name and endpoints.
     * @requires name != null && p1 != null && p2 != null
     * @effects constructs a new GeoSegment with the specified name and endpoints.
     **/
    public GeoSegment(String name, GeoPoint p1, GeoPoint p2) {
        this.name_ = name;
        this.startPoint_ = p1;
        this.endPoint_ = p2;
        checkRep();
    }


    /**
     * Returns a new GeoSegment like this one, but with its endpoints reversed.
     * @return a new GeoSegment gs such that gs.name = this.name
     *         && gs.p1 = this.p2 && gs.p2 = this.p1
     **/
    public GeoSegment reverse() {
        checkRep();
        GeoSegment gs = new GeoSegment(name_, endPoint_, startPoint_);
        checkRep();
        return gs;
    }


    /**
     * Returns the name of this GeoSegment.
     * @return the name of this GeoSegment.
     */
    public String getName() {
        checkRep();
        return name_;
    }


    /**
     * Returns first endpoint of the segment.
     * @return first endpoint of the segment.
     */
    public GeoPoint getP1() {
        checkRep();
        return startPoint_;
    }


    /**
     * Returns second endpoint of the segment.
     * @return second endpoint of the segment.
     */
    public GeoPoint getP2() {
        checkRep();
        return endPoint_;
    }


    /**
     * Returns the length of the segment.
     * @return the length of the segment, using the flat-surface, near the
     *         Technion approximation.
     */
    public double getLength() {
        checkRep();
        return startPoint_.distanceTo(endPoint_);
    }


    /**
     * Returns the compass heading from p1 to p2.
     * @return the compass heading from p1 to p2, in degrees, using the
     *         flat-surface, near the Technion approximation. If the length
     *         of the segment is 0, the heading returned is 0 degrees.
     **/
    public double getHeading() {
        checkRep();
        if(this.startPoint_.equals(this.endPoint_))
        {
            return 0;
        }
        return startPoint_.headingTo(endPoint_);
    }


    /**
     * Compares the specified Object with this GeoSegment for equality.
     * @return gs != null && (gs instanceof GeoSegment)
     *         && gs.name = this.name && gs.p1 = this.p1 && gs.p2 = this.p2
     **/
    public boolean equals(Object gs) {
        checkRep();
        if ((gs == null) || !(gs instanceof GeoSegment))
        {
            checkRep();
            return false;
        }
        GeoSegment segment = (GeoSegment) gs;
        checkRep();
        return segment.startPoint_.equals(startPoint_) &&
                segment.endPoint_.equals(endPoint_) &&
                segment.name_.equals(name_);
    }


    /**
     * Returns a hash code value for this.
     * @return a hash code value for this.
     **/
    public int hashCode() {
        checkRep();
        return (int) startPoint_.distanceTo(endPoint_);
    }


    /**
     * Returns a string representation of this.
     * @return a string representation of this.
     **/
    public String toString() {
        checkRep();
        return "(\"" + name_ + "\", " + getP1() + ", " + getP2() + ")";
    }

    /**
     * Checks that the representation invariant is maintained.
     *
     * @effects Checks that the representation invariant is maintained.
     * Aborts the program if rep invariant is broken.
     */
    private void checkRep()
    {
        assert name_ != null && startPoint_ != null && endPoint_ != null;
    }

}