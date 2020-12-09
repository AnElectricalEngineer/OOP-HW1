package homework1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A GeoFeature represents a route from one location to another along a
 * single geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have
 * the same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the
 * course of a winding river, or travel along a road through intersections
 * but remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding
 * a segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the
 * distance traveled by following the path from start to end - is not
 * necessarily the same as the distance along a straight line between
 * its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {

    // Implementation hint:
    // When asked to return an Iterator, consider using the iterator() method
    // in the List interface. Two nice classes that implement the List
    // interface are ArrayList and LinkedList. If comparing two Lists for
    // equality is needed, consider using the equals() method of List. More
    // info can be found at:
    //   http://docs.oracle.com/javase/8/docs/api/java/util/List.html


    //	Abstraction Function:
    //	A GeoFeature is a geographical feature composed of a positive number of
    //	connected GeoSegments with the same name. The feature begins at
    //	GeoFeature.getFirst().getP1() (the first point of the first segment in
    //	the feature). The feature ends at GeoFeature.getLast().getP2() (the
    //  second point of the last segment in the feature).

    //	Representation Invariant:
    //	For every GeoFeature f, f.name_ != null && 0 <= f.startHeading_ < 360
    //	&& 0 <= f.endHeading_ < 360 && for all GeoSegments s_i,
    //	s_i.endPoint == s_i+1.startPoint && for all GeoSegments s
    //	in a GeoFeature f,	it must hold that s.name_ == f.name_.

    private final String name_;
    private final List<GeoSegment> segments_;   // List containing GeoSegments

    /**
     * Constructs a new GeoFeature.
     * @requires gs != null
     * @effects Constructs a new GeoFeature, r, such that
     *	        r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
    public GeoFeature(GeoSegment gs) {
        this.name_ = gs.getName();
        segments_ = new LinkedList<>();
        segments_.add(gs);
        checkRep();
    }


    /**
     * Constructs a new GeoFeature r from an existing GeoFeature gf.
     * @requires gf != null
     * @effects Constructs a new GeoFeature r such that
     *	        r.name = gf.name &&
     *          r.startHeading = gf.startHeading &&
     *          r.endHeading = gf.endHeading &&
     *          r.start = gf.start &&
     *          r.end = gf.end
     **/
    private GeoFeature(GeoFeature gf)
    {
        this.name_ = gf.getName();
        segments_ = new LinkedList<>();
        Iterator<GeoSegment> segments = gf.getGeoSegments();

        //  Copy segments from gf.segments_ to new list
        while(segments.hasNext())
        {
            segments_.add(segments.next());
        }
        checkRep();
    }


    /**
     * Returns name of geographic feature.
     * @return name of geographic feature
     */
    public String getName() {
        checkRep();
        return name_;
    }


    /**
     * Returns location of the start of the geographic feature.
     * @return location of the start of the geographic feature.
     */
    public GeoPoint getStart() {
        checkRep();
        return segments_.get(0).getP1();
    }


    /**
     * Returns location of the end of the geographic feature.
     * @return location of the end of the geographic feature.
     */
    public GeoPoint getEnd() {
        checkRep();
        return segments_.get(segments_.size() - 1).getP2();
    }


    /**
     * Returns direction of travel at the start of the geographic feature.
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in degrees. If the first segment is of
     *         length 0, the heading is defined as 0 degrees.
     */
    public double getStartHeading() {
        checkRep();
        return segments_.get(0).getHeading();
    }


    /**
     * Returns direction of travel at the end of the geographic feature.
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in degrees. If the last segment is of
     *      *         length 0, the heading is defined as 0 degrees.
     */
    public double getEndHeading() {
        checkRep();
        return segments_.get(segments_.size() - 1).getHeading();
    }


    /**
     * Returns total length of the geographic feature, in kilometers.
     * @return total length of the geographic feature, in kilometers.
     *         NOTE: this is NOT as-the-crow-flies, but rather the total
     *         distance required to traverse the geographic feature. These
     *         values are not necessarily equal.
     */
    public double getLength() {
        checkRep();
        double length = 0;
        for(GeoSegment segment : segments_)
        {
            length += segment.getLength();
        }
        checkRep();
        return length;
    }


    /**
     * Creates a new GeoFeature that is equal to this GeoFeature with gs
     * appended to its end.
     * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
     * @return a new GeoFeature r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *    	   r.length = this.length + gs.length
     **/
    public GeoFeature addSegment(GeoSegment gs) {
        checkRep();

        //  Create a new GeoFeature from existing GeoFeature
        GeoFeature feature = new GeoFeature(this);

        //  Add the new GeoSegment
        feature.segments_.add(gs);
        checkRep();
        return feature;
    }


    /**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this GeoFeature. All the
     * GeoSegments have the same name.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2d  == a[i+1].p1))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
    public Iterator<GeoSegment> getGeoSegments() {
        checkRep();

        //  Create a COPY of the list of segments comprising the GeoFeature
        List <GeoSegment> segmentsCopy = new LinkedList<>();
        segmentsCopy.addAll(segments_);
        checkRep();
        return segmentsCopy.iterator();
    }


    /**
     * Compares the argument with this GeoFeature for equality.
     * @return o != null && (o instanceof GeoFeature) &&
     *         (o.geoSegments and this.geoSegments contain
     *          the same elements in the same order).
     **/
    public boolean equals(Object o) {
        checkRep();
        if ((o == null) || !(o instanceof GeoFeature))
        {
            return false;
        }
        GeoFeature feature = (GeoFeature) o;

        /*  If number of segments in each feature is different, features
          cannot be equal.*/
        if(feature.segments_.size() != segments_.size())
        {
            return false;
        }

        //  Check whether each i'th segment in each feature is equal.
        for(int i = 0; i<feature.segments_.size(); i++)
        {
            if(!feature.segments_.get(i).equals(segments_.get(i)))
            {
                checkRep();
                return false;
            }
        }
        checkRep();
        return true;
    }


    /**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
    public int hashCode() {
        // This implementation will work, but you may want to modify it
        // improved performance.
        checkRep();
        return (int)getLength();
    }


    /**
     * Returns a string representation of this.
     * @return a string representation of this.
     **/
    public String toString() {
        checkRep();
        return "(\"" + name_ + "\", " + getStart() + ", " + getEnd() + ")";
    }

    /**
     * Checks that the representation invariant is maintained.
     */
    private void checkRep()
    {
        assert name_ != null;
        assert segments_.get(0).getHeading() >= 0 &&
                segments_.get(0).getHeading() < 360;
        assert segments_.get(segments_.size() - 1).getHeading() >= 0 &&
                segments_.get(segments_.size() - 1).getHeading() < 360;

        //  If size of list is 1, no need to check that linking points match.
        if(segments_.size() == 1)
        {
            assert segments_.get(0).getName().equals(name_);
        }

        /*  In the general case, check that each segment's name matches the
          feature name, and that the endpoints of each connected segment match*/
        else
        {
            for( int i = 0; i < segments_.size() - 1; i++)
            {
                assert segments_.get(i).getName().equals(name_);
                assert segments_.get(i).getP2().
                        equals(segments_.get(i + 1).getP1());
            }
            assert segments_.get(segments_.size() - 1).getName().equals(name_);
        }
    }
}