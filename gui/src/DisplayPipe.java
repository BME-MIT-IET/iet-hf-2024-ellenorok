import java.awt.*;

/**
 * Egy kirajzolt csövet (Pipe) jelenít meg két csomópont között. Amennyiben a cső egyik vége nincs sehova kötve,
 * egy létrehozáskor definiált helyre (Rectangle) fogja kötni azt a végét.
 */
public class DisplayPipe extends DisplayField {
    /**
     * A reprezentált cső.
     */
    private Pipe p;
    /**
     *   A kirajzolt sokszög (egy változó orientációjú, displayWidth széles téglalap a két DisplayNode vagy egy
     *   DisplayNode és egy Rectangle között).
     */
    private Polygon polygon;
    /**
     *  A cső egyik végét hova kell kötni.
     */
    private DisplayNode n1;
    /**
     * A cső másik végét hova kell kötni.
     */
    private DisplayNode n2;
    /**
     * Ha n1 és n2 közül pontosan az egyik null, akkor a csőnek az oda kötendő végét ennek a téglalapnak a közepére
     * fogja kötni. Amennyiben ilyen esetben ez is null, a véget a (0,0) pontba köti.
     */
    private Rectangle unboundEndConnection;
    /**
     * A két összekötött pont közötti távolság.
     */
    private float length;

    /**
     *  A cső belsejének színe, ha van benne víz.
     */
    final Color waterColor = new Color(8, 78, 255, 255);
    /**
     * A cső belsejének színe, ha nincs benne víz.
     */
    final Color noWaterColor = new Color(255, 255, 255, 255);
    /**
     * A cső körvonalának színe.
     */
    final Color outlineColor = new Color(0,0,0);
    /**
     * A kirajzolt cső szélessége.
     */
    final float displayWidth = 10;

    /**
     * Kiszámolja a kirajzolt sokszög helyét. A cső két vége a következő helyeken lesz, a következő kritériumok alapján:
     * n1 == null és n2 == null: A polygon null marad, nem történik rajzolás
     * n1 != null és n2 != null: A két összekötendő pont: n1.getContainer() közepe és n2.getContainer() közepe
     * n1 és n2 közül pontosan az egyik null és unBoundEndConnection != null: A két összekötendő pont:
     *      az unBoundEndConnection téglalap közepe és a nem null ni.getContainer() közepe
     * n1 és n2 közül pontosan az egyik null és unBoundEndConnection == null: A két összekötendő pont:  az (0,0) pont
     *      és a nem null ni.getContainer() közepe
     * A két pont összekötése ezt követően a következőképp történik: legyen p és q a két összekötendő pont.
     *      Ekkor egy p - q irányú, | p - q | hosszú vonal mindkét oldalán displayWidth / 2 eltolással (p - q)-ra
     *      merőleges irányokba a téglalap 1-1 oldala length hosszú. A téglalap maradék két oldala displayWidth
     *      hosszú és a két vonal két eleje és két vége között megy.
     */
    private void calculatePolygon () {
        Vec2 lineTo = null;
        Vec2 lineFrom = null;

        if(n2 != null && n1 == null) n1 = n2; // Make sure the connected end is on n1
        if(n1 != null) {
            lineTo = new Vec2((float)n1.getContainer().getCenterX(), (float)n1.getContainer().getCenterY()); // node 1 center

            if(n2 != null) { // both ends connected
                lineFrom = new Vec2((float)n2.getContainer().getCenterX(), (float)n2.getContainer().getCenterY()); // node 2 center
            } else if(unboundEndConnection != null){ // n2 not connected, connect to unbound end if needed
                lineFrom = new Vec2((float)unboundEndConnection.getCenterX(), (float)unboundEndConnection.getCenterY());
            } else { // no unbound end position set => connect to (0,0)
                lineFrom = new Vec2(0,0);
            }
        } else {
            polygon = null;
            return; // no ends connected
        }

        // lineTo -> lineFrom
        Vec2 directionVector = Vec2.normalize(Vec2.subtract(lineFrom, lineTo));
        Vec2 normal = new Vec2(directionVector.y * -1, directionVector.x); // perpendicular to directionVector (still unit vector)
        normal = Vec2.multiply(normal, displayWidth / 2);

        Vec2 topLeft = Vec2.add(lineTo, normal);
        Vec2 bottomLeft = Vec2.subtract(lineTo, normal);
        Vec2 topRight = Vec2.add(lineFrom, normal);
        Vec2 bottomRight = Vec2.subtract(lineFrom, normal);

        polygon = new Polygon();
        polygon.addPoint(topLeft.ix(), topLeft.iy());
        polygon.addPoint(bottomLeft.ix(), bottomLeft.iy());
        polygon.addPoint(bottomRight.ix(), bottomRight.iy());
        polygon.addPoint(topRight.ix(), topRight.iy());

        length = Vec2.length(Vec2.subtract(lineTo, lineFrom));
    }
    public DisplayPipe(Pipe _p, DisplayNode _n1, DisplayNode _n2, Rectangle connectUnboundEnd) {
        p = _p;
        n1 = _n1;
        n2 = _n2;
        unboundEndConnection = connectUnboundEnd;
    }

    /**
     * Visszaadja a polygon középpontját.
     * @return
     */
    public Point getCenter() {
        int computedX = 0;
        int computedY = 0;
        for (int i = 0; i < polygon.npoints; i++) {
            computedX += polygon.xpoints[i];
            computedY += polygon.ypoints[i];
        }
        return new Point(computedX / polygon.npoints, computedY / polygon.npoints);
    }

    /**
     * Kiszámolja a kirajzolandó polygon helyét (calculatePolygon), majd ezután ha az nem null, kirajzolja azt a
     * megfelelő kitöltési (p.getOldWaterState() függvényében waterColor vagy noWaterColor) és körvonal színnel.
     * Ezután kiszámolja, a megjelenítendő játékos ikonok helyét (setPlayerIcons() hívás), majd kirajzolja őket a
     * polygon közepére.
     * @param g a grafikus objektum
     */
    @Override
    public void draw(java.awt.Graphics g){
        calculatePolygon();
        if(polygon == null) return;

        setPlayerIcons();
        if(p.getOldWaterState()) {
            g.setColor(waterColor);
        } else {
            g.setColor(noWaterColor);
        }
        g.fillPolygon(polygon);

        g.setColor(outlineColor);
        g.drawPolygon(polygon);

        for (PlayerIcon pi :
                playerIcons) {
            pi.draw(g);
        }
    }

    /**
     * Visszaadja, hogy az (x,y) pont rajta van-e a polygon-on.
     * @param x a pont x koordinátája
     * @param y a pont y koordinátája
     * @return
     */
    @Override
    public boolean on(int x, int y) {
        if(polygon == null) return false;
        return polygon.contains(x,y);
    }

    /**
     *  Visszaadja p-t.
     * @return
     */
    @Override
    public Field getGameReference() {
        return p;
    }

    /**
     * Visszaadja a következő sorokat tartalmazó String-et:
     * “Pipe”
     * “Sticky: <yes/no>”: p.isSticky() függvényében
     * “Slippery: <yes/no>”: p.isSlippery() függvényében
     * “Broken: <yes/no>”: p.isBroken() függvényében
     * @return
     */
    @Override
    public String getTooltipText() {
        String str = "<html>Pipe";
        str += "<br>Sticky: " + (p.isSticky() ? "yes" : "no");
        str += "<br>Slippery: " + (p.isSlippery() ? "yes" : "no");
        str += "<br>Broken: " + (p.isBroken() ? "yes" : "no");
        str += "</html>";
        return str;
    }

    /**
     * setPlayerIcons(): A playerIcons listának ha van első eleme, abból egy új PlayerIcon objektumot készít,
     * bennfoglaló téglalapjának közepe pedig a polygon közepén lesz.
     */
    @Override
    protected void setPlayerIcons() {
        playerIcons.clear();

        if(p.getPlayers().size() == 0) return;
        Point c = getCenter();
        int dim = (int)displayWidth * 3;
        Rectangle r = new Rectangle(c.x - dim / 2, c.y - dim / 2, dim, dim);
        playerIcons.add(new PlayerIcon(r, p.getPlayers().get(0)));
    }

    /**
     * Implicit getter a reprezentált cső referenciájához
     * @return a reprezentált cső referenciája
     */
    @Override
    public Pipe getPipe() {
        return p;
    }
}
