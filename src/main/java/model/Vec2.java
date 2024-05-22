package model;

/**
 * Kétdimenziós vektorok reprezentációja és azok műveleteinek megvalósítása.
 */
public class Vec2 {
    /**
     * A vektor x koordinátája.
     */
    private float x;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    /**
     * A vektor y koordinátája.
     */
    private float y;

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Visszaadja az x koordinátát egész számra kerekítve.
     * @return x koordináta egész számra kerekítve
     */
    public int ix() {
        return Math.round(x);
    }

    /**
     * Visszaadja az y koordinátát egész számra kerekítve.
     * @return y koordináta egész számra kerekítve
     */
    public int iy() {
        return Math.round(y);
    }

    /**
     * Skaláris szorzás.
     * @param v1 bal oldal
     * @param v2 jobb oldal
     * @return (v1 * v2)
     */
    public static float dot(Vec2 v1, Vec2 v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    /**
     * Vektorok összeadása.
     * @param v1 bal oldal
     * @param v2 jobb oldal
     * @return (v1 + v2)
     */
    public static Vec2 add(Vec2 v1, Vec2 v2) {
        return new Vec2(v1.x + v2.x, v1.y + v2.y);
    }

    /**
     * Vektorok kivonása.
     * @param v1 bal oldal
     * @param v2 jobb oldal
     * @return (v1 - v2)
     */
    public static Vec2 subtract(Vec2 v1, Vec2 v2) {
        return new Vec2(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * Vektor hosszának meghatározása.
     * @param v a vektor, melynek hosszát keressük
     * @return sqrt(v.x * v.x + v.y * v.y)
     */
    public static float length(Vec2 v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y);
    }

    /**
     * Vektor szorzása skalárral.
     * @param v bal oldal (vektor)
     * @param s jobb oldal (skalár)
     * @return (v * s)
     */
    public static Vec2 multiply(Vec2 v, float s) {
        return new Vec2(v.x * s, v.y * s);
    }
    /**
     * Vektor osztása skalárral.
     * @param v bal oldal (vektor)
     * @param s jobb oldal (skalár)
     * @return (v / s)
     */
    public static Vec2 divide(Vec2 v, float s) {
        return new Vec2(v.x / s, v.y / s);
    }

    /**
     * Vektor normalizálása
     * @param v a kiindulási vektor
     * @return (v / length(v))
     */
    public static Vec2 normalize(Vec2 v) {
        return divide(v, length(v));
    }
}
