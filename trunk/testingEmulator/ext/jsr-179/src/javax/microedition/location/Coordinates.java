package javax.microedition.location;
/**
 * The Coordinates class represents coordinates as latitude-longitude-altitude values. The latitude and longitude values are expressed in degrees using floating point values. The degrees are in decimal values (rather than minutes/seconds). The coordinates are given using the WGS84 datum.
 * This class also provides convenience methods for converting between a string coordinate representation and the double representation used in this class.
 */
public class Coordinates{
    /**
     * Identifier for string coordinate representation Degrees, Minutes, decimal fractions of a minute
     * See Also:Constant Field Values
     */
    public static final int DD_MM=2;

    /**
     * Identifier for string coordinate representation Degrees, Minutes, Seconds and decimal fractions of a second
     * See Also:Constant Field Values
     */
    public static final int DD_MM_SS=1;
    
    private double _latitude=0;
    private double _longitude=0;
    private float _altitude=0;
    
    
    //Needed attributes from the NOKIA implementation
    static final double ERROR = (0.0D / 0.0D);
    static final int ITERATIONS = 7;
    static final double SQRT3 = 1.732050807569D;
    static double PIdiv2;
    static double PIdiv4;
    static double PIdiv6;
    static double PIdiv12;
    static double PImul2;
    static double PImul4;
    static boolean isInitialised = false;
    static final double EARTH_RADIUS = 6378137D;
    static final double FLATTENING = 298.25722356300003D;
    static double lastLat1 = (0.0D / 0.0D);
    static double lastLat2 = (0.0D / 0.0D);
    static double lastLon1 = (0.0D / 0.0D);
    static double lastLon2 = (0.0D / 0.0D);
    static float calculatedDistance = (0.0F / 0.0F);
    static float calculatedAzimuth = (0.0F / 0.0F);
    
    
    

    /**
     * Constructs a new Coordinates object with the values specified. The latitude and longitude parameters are expressed in degrees using floating point values. The degrees are in decimal values (rather than minutes/seconds).
     * The coordinate values always apply to the WGS84 datum.
     * The Float.NaN value can be used for altitude to indicate that altitude is not known.
     * latitude - the latitude of the location. Valid range: [-90.0, 90.0]. Positive values indicate northern latitude and negative values southern latitude.longitude - the longitude of the location. Valid range: [-180.0, 180.0). Positive values indicate eastern longitude and negative values western longitude.altitude - the altitude of the location in meters, defined as height above the WGS84 ellipsoid. Float.NaN can be used to indicate that altitude is not known.
     * java.lang.IllegalArgumentException - if an input parameter is out of the valid range
     */
    public Coordinates(double latitude, double longitude, float altitude){
    	this.setAltitude(altitude);
    	this.setLatitude(latitude);
    	this.setLongitude(longitude);
    }
    

    /**
     * Calculates the azimuth between the two points according to the ellipsoid model of WGS84. The azimuth is relative to true north. The Coordinates object on which this method is called is considered the origin for the calculation and the Coordinates object passed as a parameter is the destination which the azimuth is calculated to. When the origin is the North pole and the destination is not the North pole, this method returns 180.0. When the origin is the South pole and the destination is not the South pole, this method returns 0.0. If the origin is equal to the destination, this method returns 0.0. The implementation shall calculate the result as exactly as it can. However, it is required that the result is within 1 degree of the correct result.
     *
     * The implementation of this method is based on the NOKIA official release of the JSR179
     */
    public float azimuthTo(javax.microedition.location.Coordinates to){
        if(to == null)
            throw new NullPointerException();
        if(lastLat1 == getLatitude() && lastLon1 == getLongitude() && lastLat2 == to.getLatitude() && lastLon2 == to.getLongitude())
        {
            return calculatedAzimuth;
        } else
        {
            lastLat1 = getLatitude();
            lastLat1 = getLongitude();
            lastLat2 = to.getLatitude();
            lastLon2 = to.getLongitude();
            calculateDistanceAndAzimuth(getLatitude(), getLongitude(), to.getLatitude(), to.getLongitude());
            return calculatedAzimuth;
        }
    }

    /**
     * Converts a double representation of a coordinate with decimal degrees into a string representation.
     * There are string syntaxes supported are the same as for the convert(String) method. The implementation shall provide as many significant digits for the decimal fractions as are allowed by the string syntax definition.
     *
     *	The implementation of this method is based on the NOKIA official release of the JSR179
     */
    public static java.lang.String convert(double coordinate, int outputType){
        if(coordinate >= 180D || coordinate < -180D)
            throw new IllegalArgumentException("Bad coordinate value (out of range)");
        if(Double.isNaN(coordinate))
            throw new IllegalArgumentException("Bad coordinate value (NaN)");
        if(outputType == 2)
        {
            byte byte0 = ((byte)(coordinate >= 0.0D ? 1 : -1));
            coordinate = Math.abs(coordinate);
            int j = (int)coordinate;
            double d1 = (coordinate - (double)j) * 60D;
            double d3 = (double)(int)Math.floor(10000D * d1 + 0.5D) / 10000D;
            String s = (byte0 >= 0 ? "" : "-") + j + ":" + (d1 >= 10D ? "" : "0") + d3;
            return s;
        }
        if(outputType == 1)
        {
            byte byte1 = ((byte)(coordinate >= 0.0D ? 1 : -1));
            coordinate = Math.abs(coordinate);
            int k = (int)coordinate;
            int l = (int)((coordinate - (double)k) * 60D);
            double d2 = ((coordinate - (double)k) * 60D - (double)l) * 60D;
            double d4 = (double)(int)Math.floor(100D * d2 + 0.5D) / 100D;
            String s1 = (byte1 >= 0 ? "" : "-") + k + ":" + (l >= 10 ? "" : "0") + l + ":" + (d4 >= 10D ? "" : "0") + d4;
            return s1;
        } else
        {
            throw new IllegalArgumentException("Bad outputType");
        }

    }

    /**
     * Converts a String representation of a coordinate into the double representation as used in this API.
     * There are two string syntaxes supported:
     * 1. Degrees, minutes, seconds and decimal fractions of seconds. This is expressed as a string complying with the following BNF definition where the degrees are within the range [-179, 179] and the minutes and seconds are within the range [0, 59], or the degrees is -180 and the minutes, seconds and decimal fractions are 0: coordinate = degrees ":" minutes ":" seconds "." decimalfrac | degrees ":" minutes ":" seconds | degrees ":" minutes degrees = degreedigits | "-" degreedigits degreedigits = digit | nonzerodigit digit | "1" digit digit minutes = minsecfirstdigit digit seconds = minsecfirstdigit digit decimalfrac = 1*3digit digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" nonzerodigit = "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" minsecfirstdigit = "0" | "1" | "2" | "3" | "4" | "5"
     * 2. Degrees, minutes and decimal fractions of minutes. This is expressed as a string complying with the following BNF definition where the degrees are within the range [-179, 179] and the minutes are within the range [0, 59], or the degrees is -180 and the minutes and decimal fractions are 0: coordinate = degrees ":" minutes "." decimalfrac | degrees ":" minutes degrees = degreedigits | "-" degreedigits degreedigits = digit | nonzerodigit digit | "1" digit digit minutes = minsecfirstdigit digit decimalfrac = 1*5digit digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" nonzerodigit = "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" minsecfirstdigit = "0" | "1" | "2" | "3" | "4" | "5"
     * For example, for the double value of the coordinate 61.51d, the corresponding syntax 1 string is "61:30:36" and the corresponding syntax 2 string is "61:30.6".
     *
     * The implementation of this method is based on the NOKIA official release of the JSR179
     */
    public static double convert(java.lang.String coordinate){
    	try
        {
            if(coordinate == null)
                throw new NullPointerException("Bad coordinate string (null)");
            coordinate = coordinate.trim();
            if(coordinate.length() < 4)
                throw new IllegalArgumentException("Bad coordinate string (len < 4)");
            char c = coordinate.charAt(coordinate.length() - 1);
            if(c < '0' || c > '9')
                throw new IllegalArgumentException("Bad coordinate string (must end with digit)");
            int i = coordinate.indexOf(':');
            if(i < 1)
                throw new IllegalArgumentException("Bad coordinate string (no ':')");
            if(i > 1 && coordinate.charAt(0) == '0' || i > 2 && coordinate.charAt(0) == '-' && coordinate.charAt(1) == '0')
                throw new IllegalArgumentException("Bad coordinate string (must not start with 0)");
            int j = Integer.parseInt(coordinate.substring(0, i));
            if(j >= 180 || j < -180)
                throw new IllegalArgumentException("Bad coordinate string (degrees out of range)");
            int k = 1;
            if(coordinate.startsWith("-"))
            {
                k = -1;
                j = -j;
            }
            byte byte0 = 1;
            //Object obj = null;
            int l = coordinate.indexOf(':', i + 1);
            if(l < 1)
            {
                byte0 = 2;
                l = coordinate.length();
            }
            if(byte0 == 2)
            {
                String s1 = coordinate.substring(i + 1, coordinate.length());
                char c1 = s1.charAt(0);
                if(c1 < '0' || c1 > '9')
                    throw new IllegalArgumentException("Bad coordinate string (mins must start with two digits)");
                char c3 = s1.charAt(1);
                if(c3 < '0' || c3 > '9')
                    throw new IllegalArgumentException("Bad coordinate string (mins must start with two digits)");
                if(s1.length() > 8)
                    throw new IllegalArgumentException("Bad coordinate string (mins can't have more than five decimals)");
                double d = Double.parseDouble(s1);
                if(d >= 60D || d < 0.0D)
                    throw new IllegalArgumentException("Bad coordinate string");
                double d1 = (double)k * ((double)j + d / 60D);
                if(d1 >= 180D || d1 < -180D)
                    throw new IllegalArgumentException("Bad coordinate string");
                else
                    return d1;
            }
            if(byte0 == 1)
            {
                if(l - (i + 1) < 2)
                    throw new IllegalArgumentException("Bad coordinate string (mins must have two digits)");
                String s2 = coordinate.substring(i + 1, l);
                char c2 = s2.charAt(0);
                if(c2 < '0' || c2 > '9')
                    throw new IllegalArgumentException("Bad coordinate string (mins must start with two digits)");
                char c4 = s2.charAt(1);
                if(c4 < '0' || c4 > '9')
                    throw new IllegalArgumentException("Bad coordinate string (mins must start with two digits)");
                int i1 = Integer.parseInt(s2);
                if(i1 > 59 || i1 < 0)
                    throw new IllegalArgumentException("Bad coordinate string (mins out of range)");
                //Object obj1 = null;
                double d2 = 0.0D;
                if(l != coordinate.length())
                {
                    String s3 = coordinate.substring(l + 1, coordinate.length());
                    char c5 = s3.charAt(0);
                    if(c5 < '0' || c5 > '9')
                        throw new IllegalArgumentException("Bad coordinate string (secs must start with two digits)");
                    char c6 = s3.charAt(1);
                    if(c6 < '0' || c6 > '9')
                        throw new IllegalArgumentException("Bad coordinate string (secs must start with two digits)");
                    if(s3.length() > 6)
                        throw new IllegalArgumentException("Bad coordinate string (secs can't have more than three decimals)");
                    d2 = Double.parseDouble(s3);
                    if(d2 >= 60D || d2 < 0.0D)
                        throw new IllegalArgumentException("Bad coordinate string (secs out of range)");
                }
                double d3 = (double)k * ((double)j + (double)i1 / 60D + d2 / 3600D);
                if(d3 >= 180D || d3 < -180D)
                    throw new IllegalArgumentException("Bad coordinate string (result out of range)");
                else
                    return d3;
            } else
            {
                throw new IllegalArgumentException("Unknown format");
            }
        }
        catch(NumberFormatException numberformatexception)
        {
            throw new IllegalArgumentException("Bad decimal number format");
        }
    }

    /**
     * Calculates the geodetic distance between the two points according to the ellipsoid model of WGS84. Altitude is neglected from calculations.
     * The implementation shall calculate this as exactly as it can. However, it is required that the result is within 0.36% of the correct result.
     */
    public float distance(javax.microedition.location.Coordinates to){
        if(to == null)
            throw new NullPointerException();
        if(lastLat1 == getLatitude() && lastLon1 == getLongitude() && lastLat2 == to.getLatitude() && lastLon2 == to.getLongitude())
        {
            return calculatedDistance;
        } else
        {
            lastLat1 = getLatitude();
            lastLat1 = getLongitude();
            lastLat2 = to.getLatitude();
            lastLon2 = to.getLongitude();
            calculateDistanceAndAzimuth(getLatitude(), getLongitude(), to.getLatitude(), to.getLongitude());
            return calculatedDistance;
        }
    }

    /**
     * Returns the altitude component of this coordinate. Altitude is defined to mean height above the WGS84 reference ellipsoid. 0.0 means a location at the ellipsoid surface, negative values mean the location is below the ellipsoid surface, Float.NaN that the altitude is not available.
     */
    public float getAltitude(){
    	return this._altitude;
    }

    /**
     * Returns the latitude component of this coordinate. Positive values indicate northern latitude and negative values southern latitude.
     * The latitude is given in WGS84 datum.
     */
    public double getLatitude(){
    	return this._latitude;
    }

    /**
     * Returns the longitude component of this coordinate. Positive values indicate eastern longitude and negative values western longitude.
     * The longitude is given in WGS84 datum.
     */
    public double getLongitude(){
    	return this._longitude;
    }

    /**
     * Sets the geodetic altitude for this point.
     */
    public void setAltitude(float altitude){
    	this._altitude=altitude;
    }

    /**
     * Sets the geodetic latitude for this point. Latitude is given as a double expressing the latitude in degrees in the WGS84 datum.
     */
    public void setLatitude(double latitude){
    	if(Double.isNaN(latitude))
    	{
            throw new IllegalArgumentException("Latitude " + latitude + " is not legal");
    	}
        if(latitude >= -90D && latitude <= 90D)
        {
        	this._latitude=latitude;
        }
        else
        {
            throw new IllegalArgumentException("Latitude out of range");
        }
    }

    /**
     * Sets the geodetic longitude for this point. Longitude is given as a double expressing the longitude in degrees in the WGS84 datum.
     */
    public void setLongitude(double longitude){
    	
    	if(Double.isNaN(longitude))
    	{
            throw new IllegalArgumentException("Longitude " + longitude + " is not legal");
    	}
        if(longitude >= -180D && longitude < 180D)
        {
        	this._longitude=longitude;
        }
        else
        {
            throw new IllegalArgumentException("Longitude out of range");
        }
    }
    
    
    
    //Needed methods from the NOKIA implementation
    private static void init()
    {
        isInitialised = true;
        PIdiv2 = 1.5707963267948966D;
        PIdiv4 = 0.78539816339744828D;
        PIdiv6 = 0.52359877559829882D;
        PIdiv12 = 0.26179938779914941D;
        PImul2 = 6.2831853071795862D;
        PImul4 = 12.566370614359172D;
    }

    private static void calculateDistanceAndAzimuth(double d, double d1, double d2, double d3)
    {
        double d4 = toRadians(d);
        double d5 = toRadians(d1);
        double d6 = toRadians(d2);
        double d7 = toRadians(d3);
        double d8 = 0.0033528106647474805D;
        double d9 = 0.0D;
        double d10 = 0.0D;
        double d20 = 0.0D;
        double d22 = 0.0D;
        double d24 = 0.0D;
        double d25 = 0.0D;
        double d26 = 0.0D;
        double d28 = 0.0D;
        double d29 = 0.0D;
        double d30 = 0.0D;
        double d31 = 0.0D;
        double d32 = 0.0D;
        double d33 = 5.0000000000000003E-10D;
        int i = 1;
        byte byte0 = 100;
        if(d4 == d6 && (d5 == d7 || Math.abs(Math.abs(d5 - d7) - 6.2831853071795862D) < d33))
        {
            calculatedDistance = 0.0F;
            calculatedAzimuth = 0.0F;
            return;
        }
        if(d4 + d6 == 0.0D && Math.abs(d5 - d7) == 3.1415926535897931D)
            d4 += 1.0000000000000001E-05D;
        double d11 = 1.0D - d8;
        double d12 = d11 * Math.tan(d4);
        double d13 = d11 * Math.tan(d6);
        double d14 = 1.0D / Math.sqrt(1.0D + d12 * d12);
        double d15 = d14 * d12;
        double d16 = 1.0D / Math.sqrt(1.0D + d13 * d13);
        double d17 = d14 * d16;
        double d18 = d17 * d13;
        double d19 = d18 * d12;
        d9 = d7 - d5;
        for(d32 = d9 + 1.0D; i < byte0 && Math.abs(d32 - d9) > d33; d9 = ((1.0D - d31) * d9 * d8 + d7) - d5)
        {
            i++;
            double d21 = Math.sin(d9);
            double d23 = Math.cos(d9);
            d12 = d16 * d21;
            d13 = d18 - d15 * d16 * d23;
            d24 = Math.sqrt(d12 * d12 + d13 * d13);
            d25 = d17 * d23 + d19;
            d10 = atan2(d24, d25);
            double d27 = (d17 * d21) / d24;
            d28 = 1.0D - d27 * d27;
            d29 = 2D * d19;
            if(d28 > 0.0D)
                d29 = d25 - d29 / d28;
            d30 = -1D + 2D * d29 * d29;
            d31 = (((-3D * d28 + 4D) * d8 + 4D) * d28 * d8) / 16D;
            d32 = d9;
            d9 = ((d30 * d25 * d31 + d29) * d24 * d31 + d10) * d27;
        }

        double d34 = mod(atan2(d12, d13), 6.2831853071795862D);
        d9 = Math.sqrt((1.0D / (d11 * d11) - 1.0D) * d28 + 1.0D);
        d9++;
        d9 = (d9 - 2D) / d9;
        d31 = ((d9 * d9) / 4D + 1.0D) / (1.0D - d9);
        d32 = (d9 * d9 * 0.375D - 1.0D) * d9;
        d9 = d30 * d25;
        double d35 = ((((((d24 * d24 * 4D - 3D) * (1.0D - d30 - d30) * d29 * d32) / 6D - d9) * d32) / 4D + d29) * d24 * d32 + d10) * d31 * 6378137D * d11;
        if((double)Math.abs(i - byte0) < d33)
        {
            calculatedDistance = (0.0F / 0.0F);
            calculatedAzimuth = (0.0F / 0.0F);
            return;
        }
        d34 = (180D * d34) / 3.1415926535897931D;
        calculatedDistance = (float)d35;
        calculatedAzimuth = (float)d34;
        if(d == 90D)
            calculatedAzimuth = 180F;
        else
        if(d == -90D)
            calculatedAzimuth = 0.0F;
    }

    private static double modlon(double d)
    {
        return mod(d + 3.1415926535897931D, 6.2831853071795862D) - 3.1415926535897931D;
    }

    private static double modlat(double d)
    {
        return mod(d + 1.5707963267948966D, 6.2831853071795862D) - 1.5707963267948966D;
    }

    private static double mod(double d, double d1)
    {
        return d - d1 * Math.floor(d / d1);
    }

    private static double atan(double d)
    {
        if(!isInitialised)
            init();
        boolean flag = false;
        boolean flag1 = false;
        int i = 0;
        if(d < 0.0D)
        {
            d = -d;
            flag = true;
        }
        if(d > 1.0D)
        {
            d = 1.0D / d;
            flag1 = true;
        }
        double d2;
        for(; d > PIdiv12; d *= d2)
        {
            i++;
            d2 = d + 1.732050807569D;
            d2 = 1.0D / d2;
            d *= 1.732050807569D;
            d--;
        }

        double d1 = d * d;
        double d3 = d1 + 1.4087812D;
        d3 = 0.55913709D / d3;
        d3 += 0.60310578999999997D;
        d3 -= 0.051604539999999997D * d1;
        d3 *= d;
        for(; i > 0; i--)
            d3 += PIdiv6;

        if(flag1)
            d3 = PIdiv2 - d3;
        if(flag)
            d3 = -d3;
        return d3;
    }

    private static double atan2(double d, double d1)
    {
        if(Double.isNaN(d) || Double.isNaN(d1))
            return (0.0D / 0.0D);
        if(d == 0.0D && d1 > 0.0D)
            return 0.0D;
        if(d > 0.0D && d1 == (1.0D / 0.0D))
            return 0.0D;
        if(d == 0.0D && d1 > 0.0D)
            return 0.0D;
        if(d < 0.0D && d1 == (1.0D / 0.0D))
            return 0.0D;
        if(d == 0.0D && d1 < 0.0D)
            return 3.1415926535897931D;
        if(d > 0.0D && d1 == (-1.0D / 0.0D))
            return 3.1415926535897931D;
        if(d == 0.0D && d1 < 0.0D)
            return -3.1415926535897931D;
        if(d < 0.0D && d1 == (-1.0D / 0.0D))
            return -3.1415926535897931D;
        if(d > 0.0D && d1 == 0.0D)
            return PIdiv2;
        if(d == (1.0D / 0.0D) && d1 != (1.0D / 0.0D) && d1 != (-1.0D / 0.0D))
            return PIdiv2;
        if(d < 0.0D && d1 == 0.0D)
            return -PIdiv2;
        if(d == (-1.0D / 0.0D) && d1 != (1.0D / 0.0D) && d1 != (-1.0D / 0.0D))
            return -PIdiv2;
        if(d == (1.0D / 0.0D) && d1 == (1.0D / 0.0D))
            return PIdiv4;
        if(d == (1.0D / 0.0D) && d1 == (-1.0D / 0.0D))
            return 3D * PIdiv4;
        if(d == (-1.0D / 0.0D) && d1 == (1.0D / 0.0D))
            return -PIdiv4;
        if(d == (-1.0D / 0.0D) && d1 == (-1.0D / 0.0D))
            return -3D * PIdiv4;
        if(d1 > 0.0D)
            return atan(d / d1);
        if(d1 < 0.0D)
            return (d >= 0.0D ? 1.0D : -1D) * (3.1415926535897931D - atan(Math.abs(d / d1)));
        else
            return atan(d / d1);
    }

    private static double acos(double d)
    {
        return PIdiv2 - asin(d);
    }

    private static double asin(double d)
    {
        if(d < -1D || d > 1.0D)
            return (0.0D / 0.0D);
        if(d == -1D)
            return -PIdiv2;
        if(d == 1.0D)
            return PIdiv2;
        else
            return atan(d / Math.sqrt(1.0D - d * d));
    }

    private static double pow2(double d)
    {
        return d * d;
    }

    private static double toDegrees(double d)
    {
        return (d * 360D) / 6.2831853071795862D;
    }

    private static double toRadians(double d)
    {
        return (d * 6.2831853071795862D) / 360D;
    }

    

}
