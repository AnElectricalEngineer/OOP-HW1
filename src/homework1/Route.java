package homework1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless
 * of their names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment
 * to the end of a Route. An added segment must be properly oriented; that
 * is, its p1 field must correspond to the end of the original Route, and
 * its p2 field corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily
 * the same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features,
 * using the <tt>getGeoFeatures()</tt> method which returns an Iterator of
 * GeoFeature objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {


    //	Abstraction Function:
    //	A Route is a path composed of a positive number of
    //	connected GeoFeatures. The Route begins at
    //	Route.getFirst().getFirst().getP1() (the first point of the first
    //	segment of the first feature in the route). The route ends at
    //	Route.getLast().getLast().getP2() (the second point of the last
    //	segment of the last feature in the route).

    //	Representation Invariant:
    //	For every Route r, 0 <= r.startHeading_ < 360
    //	&& 0 <= r.endHeading_ < 360 && for all GeoFeatures gf_i in a Route r,
    //	gf_i.endPoint == gf_i+1.startPoint && gf_i.name != gf_i+1.name.

    private final List<GeoFeature> features_;   // List containing GeoSegments

    /**
     * Constructs a new Route.
     * @requires gs != null
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
    public Route(GeoSegment gs) {
        this.features_ = new LinkedList<>();
        GeoFeature newFeature = new GeoFeature(gs);
        this.features_.add(newFeature);
        checkRep();
    }


    /**
     * Constructs a new Route r from an existing Route prevRoute.
     * @requires prevRoute != null, gs != null
     * @effects Constructs a new Route r such that
     *          r.startHeading = prevRoute.startHeading &&
     *          r.endHeading = gs.endHeading &&
     *          r.start = prevRoute.start &&
     *          r.end = gs.end
     **/
    private Route(Route prevRoute, GeoSegment gs)
    {
        features_ = new LinkedList<>();

        /*  Copy all features except last from prevRoute.features_ to
          new list
          */
        for(int i = 0; i < prevRoute.features_.size() - 1; i++)
        {
            features_.add(prevRoute.features_.get(i));
        }

        /*  Differentiate between two cases. If the name of the segment gs is
          the same as that of the last feature in the route, add gs to that
          feature. Otherwise, create a new feature with gs as the first
          segment, and append to the end of the route.
          */
        if(gs.getName().equals(prevRoute.features_.get(prevRoute.
                features_.size() - 1).getName()))
        {
            GeoFeature lastFeature =
                    prevRoute.features_.get(prevRoute.features_.size() - 1).
                            addSegment(gs);
            features_.add(lastFeature);
        }
        else
        {
            //  Insert last feature from prevRoute into new route
            features_.add(prevRoute.features_.get(prevRoute.features_.size() - 1));

            //  Create new feature and insert into new route
            GeoFeature newFeature = new GeoFeature(gs);
            features_.add(newFeature);
        }
        checkRep();
    }


    /**
     * Returns location of the start of the route.
     * @return location of the start of the route.
     **/
    public GeoPoint getStart() {
        checkRep();
        return features_.get(0).getStart();
    }


    /**
     * Returns location of the end of the route.
     * @return location of the end of the route.
     **/
    public GeoPoint getEnd() {
        checkRep();
        return features_.get(features_.size() - 1).getEnd();
    }


    /**
     * Returns direction of travel at the start of the route, in degrees.
     * @return direction (in compass heading) of travel at the start of the
     *         route, in degrees. If the first segment of the first feature is
     *         of length 0, the heading is defined as 0 degrees.
     **/
    public double getStartHeading() {
        checkRep();
        return features_.get(0).getStartHeading();
    }


    /**
     * Returns direction of travel at the end of the route, in degrees.
     * @return direction (in compass heading) of travel at the end of the
     *         route, in degrees. If the last segment of the last feature is
     *         of length 0, the heading is defined as 0 degrees.
     **/
    public double getEndHeading() {
        checkRep();
        return features_.get(features_.size() - 1).getEndHeading();
    }


    /**
     * Returns total length of the route.
     * @return total length of the route, in kilometers.  NOTE: this is NOT
     *         as-the-crow-flies, but rather the total distance required to
     *         traverse the route. These values are not necessarily equal.
     **/
    public double getLength() {
        checkRep();
        double length = 0;
        for(GeoFeature feature : features_)
        {
            length += feature.getLength();
        }
        checkRep();
        return length;
    }


    /**
     * Creates a new route that is equal to this route with gs appended to
     * its end.
     * @requires gs != null && gs.p1 == this.end
     * @return a new Route r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *         r.length = this.length + gs.length
     **/
    public Route addSegment(GeoSegment gs) {
        checkRep();
        Route route = new Route(this, gs);
        checkRep();
        return route;
    }


    /**
     * Returns an Iterator of GeoFeature objects. The concatenation
     * of the GeoFeatures, in order, is equivalent to this route. No two
     * consecutive GeoFeature objects have the same name.
     * @return an Iterator of GeoFeatures such that
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
    public Iterator<GeoFeature> getGeoFeatures() {
        checkRep();

        //  Create a COPY of the list of the GeoFeatures comprising the Route
        List <GeoFeature> featuresCopy = new LinkedList<>();
        featuresCopy.addAll(features_);
        checkRep();
        return featuresCopy.iterator();
    }


    /**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
    public Iterator<GeoSegment> getGeoSegments() {
        checkRep();
        List <GeoSegment> allSegments = new LinkedList<>();
        for (GeoFeature feature : features_)
        {
            Iterator<GeoSegment> segments = feature.getGeoSegments();
            while (segments.hasNext())
            {
                allSegments.add(segments.next());
            }
        }
        checkRep();
        return allSegments.iterator();
    }


    /**
     * Compares the specified Object with this Route for equality.
     * @return true iff (o instanceof Route) &&
     *         (o.geoFeatures and this.geoFeatures contain
     *          the same elements in the same order).
     **/
    public boolean equals(Object o) {
        checkRep();
        if((o == null) || !(o instanceof Route))
        {
            return false;
        }

        Route route = (Route) o;

        /*  If number of features in each route is different, routes
          cannot be equal.*/
        if(route.features_.size() != features_.size())
        {
            return false;
        }

        //  Check whether each i'th feature in each route is equal.
        for(int i = 0; i<route.features_.size(); i++)
        {
            if(!route.features_.get(i).equals(features_.get(i)))
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
        // for improved performance.

        return (int)getLength();
    }


    /**
     * Returns a string representation of this.
     * @return a string representation of this.
     **/
    public String toString() {
        checkRep();
        return "Route: Start point: " + getStart() + ", End point: " + getEnd();
    }

    private void checkRep()
    {
        //	Representation Invariant:
        //	For every Route r, 0 <= r.startHeading_ < 360
        //	&& 0 <= r.endHeading_ < 360 && for all GeoFeatures gf_i in a Route r,
        //	gf_i.endPoint == gf_i+1.startPoint.
        assert features_.get(0).getStartHeading() >= 0 &&
                features_.get(0).getStartHeading() < 360;
        assert features_.get(features_.size() - 1).getEndHeading() >= 0 &&
                features_.get(features_.size() - 1).getEndHeading() >= 0;

        /*  If there is more than one feature in features_, check that the
        endpoints match and that no two consecutive features have the same name.
        */
        if(features_.size() > 1)
        {
            for( int i = 0; i < features_.size() - 1; i++)
            {
                assert features_.get(i).getEnd().
                        equals(features_.get(i + 1).getStart());
                assert !features_.get(i).getName().equals(features_.get(i + 1)
                        .getName());
            }
        }

    }
}