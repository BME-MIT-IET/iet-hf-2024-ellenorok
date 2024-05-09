import java.awt.*;

/**
 * Egy kirajzolt pumpát (Pump) reprezentál.
 */
public class DisplayPump extends DisplayNode {
    /**
     * A reprezentált pumpa referenciája.
     */
    private Pump p;
    /**
     *  A pumpa bemeneti csövének megfelelő DisplayPipe másik végén lévő DisplayNode.
     */
    private DisplayNode inLinkTowards = null;
    /**
     *  A pumpa kimeneti csövének megfelelő DisplayPipe másik végén lévő DisplayNode.
     */
    private DisplayNode outLinkTowards = null;
    /**
     * Egy dirTriangleColor színű háromszög, mely az outLinkTowards (ha nem null) felé mutat a felrajzolt
     * pumpa szélétől (azaz a kimeneti pumpálási irányt szemlélteti).
     */
    private Polygon outDirTriangle;
    /**
     *  Egy dirTriangleColor színű háromszög, mely mindig az inLinkTowards (ha nem null) felől mutat a felrajzolt
     *  pumpa szélére, annak közepének irányába (azaz a bemeneti pumpálási irányt szemlélteti).
     */
    private Polygon inDirTriangle;

    /**
     *  A pumpa kitöltési színe, amennyiben a reprezentált pumpa tartályában van víz.
     */
    private final Color waterColor = new Color(8, 78, 255, 255);
    /**
     * A pumpa kitöltési színe, amennyiben a reprezentált pumpa tartályában nincs víz.
     */
    private final Color noWaterColor = new Color(255,255,255);
    /**
     * A pumpa keretének színe.
     */
    private final Color outlineColor = new Color(0,0,0);
    /**
     *  A pumpálási irányt szemléltető háromszögek színe.
     */
    private final Color dirTriangleColor = new Color(0,0,0);

    /**
     * A pumpálási irányt szemléltető háromszög alapjának hossza.
     */
    private final float directionTriangleBase = (float) ((container.getWidth() + container.getHeight()) * 0.2f);
    /**
     * A pumpálási irányt szemléltető háromszög magassága.
     */
    private final float directionTriangleHeight = (float) ((container.getWidth() + container.getHeight()) * 0.3f);

    /**
     * A paraméterként kapott dir irányba előállít egy pontot, ami egység távolságra van mind x, mind y komponensek
     * szerint a kirajzolt pumpa középpontjától.
     * @param dir az irány
     * @return Vec2 a pont koordinátái
     */
    private Vec2 getTriangleClosestPosition(Vec2 dir) {
        final Vec2 center = new Vec2((float)container.getCenterX(), (float)container.getCenterY());
        final Vec2 unitX = new Vec2(1.0f, 0.0f);
        final Vec2 unitY = new Vec2(0.0f, 1.0f);
        final float compX = Vec2.dot(dir, unitX) * (container.width / 2.0f); // center -> base X component
        final float compY = Vec2.dot(dir, unitY) * (container.height / 2.0f); // center -> base Y component
        Vec2 triangleClosestPosition = center;
        triangleClosestPosition = Vec2.add(triangleClosestPosition, Vec2.multiply(unitX, compX));
        triangleClosestPosition = Vec2.add(triangleClosestPosition, Vec2.multiply(unitY, compY));
        return triangleClosestPosition;
    }

    /**
     * Amennyiben az outLinkTowards != null, kiszámolja az outDirTriangle-t. Majd,
     * amennyiben az inLinkTowards != null, kiszámolja az inDirTriangle-t.
     */
    private void setTriangles() {
        if (outLinkTowards != null) {
            Vec2 outDir = getVectorTo(outLinkTowards);
            Vec2 triangleBaseCenter = getTriangleClosestPosition(outDir);

            //noinspection SuspiciousNameCombination
            Vec2 normal = new Vec2(-1.0f * outDir.y, outDir.x);
            Vec2 p1 = Vec2.add(triangleBaseCenter, Vec2.multiply(normal, directionTriangleBase / 2)); // base 1
            Vec2 p2 = Vec2.add(triangleBaseCenter, Vec2.multiply(normal, -1.0f * directionTriangleBase / 2)); // base 2
            Vec2 p3 = Vec2.add(triangleBaseCenter, Vec2.multiply(outDir, directionTriangleHeight));

            outDirTriangle = new Polygon();
            outDirTriangle.addPoint(p1.ix(), p1.iy());
            outDirTriangle.addPoint(p2.ix(), p2.iy());
            outDirTriangle.addPoint(p3.ix(), p3.iy());
        }

        if (inLinkTowards != null) {
            Vec2 inDir = getVectorTo(inLinkTowards);
            Vec2 triangleBaseCenter = Vec2.add(getTriangleClosestPosition(inDir), Vec2.multiply(inDir, directionTriangleHeight));

            //noinspection SuspiciousNameCombination
            Vec2 normal = new Vec2(-1.0f * inDir.y, inDir.x);
            Vec2 p1 = Vec2.add(triangleBaseCenter, Vec2.multiply(normal, directionTriangleBase / 2)); // base 1
            Vec2 p2 = Vec2.add(triangleBaseCenter, Vec2.multiply(normal, -1.0f * directionTriangleBase / 2)); // base 2
            Vec2 p3 = Vec2.add(triangleBaseCenter, Vec2.multiply(inDir, -1.0f * directionTriangleHeight));

            inDirTriangle = new Polygon();
            inDirTriangle.addPoint(p1.ix(), p1.iy());
            inDirTriangle.addPoint(p2.ix(), p2.iy());
            inDirTriangle.addPoint(p3.ix(), p3.iy());
        }
    }

    /**
     * A container középpontjából a paraméterként kapott node középpontjába mutató egységvektorral tér vissza.
     * @param node ahova a vektor mutasson
     * @return Vec2 a számolt egységvektor
     */
    private Vec2 getVectorTo(DisplayNode node) {
        final Vec2 center = new Vec2((float)container.getCenterX(), (float)container.getCenterY());
        final Rectangle linkTowardsContainer = node.getContainer();
        final Vec2 nodePos = new Vec2((float)linkTowardsContainer.getCenterX(), (float)linkTowardsContainer.getCenterY());
        return Vec2.normalize(Vec2.subtract(nodePos, center));
    }

    public DisplayPump(Rectangle _container, Pump _p) {
        super(_container);
        p = _p;
    }

    /**
     * Amennyiben a reprezentált pumpája bemeneti / kimeneti csöve (p.getInput() / p.getOutput()) az
     * edge.getGameReference()-el egyenlő, beállítja az inLinkTowards / outLinkTowards értékét a
     * linkTowards paraméterként kapott objektumra.
     * @param edge az egyezést ezen vizsgálja
     * @param linkTowards errefelé fog mutatni az irány háromszög egyezés esetén
     */
    @Override
    public void link(DisplayPipe edge, DisplayNode linkTowards) {
        if(edge.getGameReference() == p.getInput()) {
            inLinkTowards = linkTowards;
        } else if(edge.getGameReference() == p.getOutput()) {
            outLinkTowards = linkTowards;
        }
    }

    /**
     * A kirajzolt pumpálási irányt szemléltető háromszögeket és azokhoz mindent kapcsolódó számítási eredményt
     * (inLinkTowards, outLinkTowards, inDirTriangle és outDirTriangle beállítása null-ra) töröl.
     */
    @Override
    public void unlink() {
        inLinkTowards = outLinkTowards = null;
        inDirTriangle = outDirTriangle = null;
    }

    /**
     * A megfelelő kitöltési (p.isHasStoredWater() alapján) és körvonal színnel kirajzolja a pumpát reprezentáló
     * téglalapot. Ezután kirajzolja a dirTriangleColor színnel az inDirTriangle és outDirTriangle háromszögeket.
     * @param g a grafikus objektum
     */
    @Override
    public void draw(java.awt.Graphics g){
        if(p.isHasStoredWater()) {
            g.setColor(waterColor);
        } else {
            g.setColor(noWaterColor);
        }
        g.fillRect(container.x, container.y, container.width, container.height);

        g.setColor(outlineColor);
        g.drawRect(container.x, container.y, container.width, container.height);
        setTriangles();

        g.setColor(dirTriangleColor);
        if(outDirTriangle != null) g.fillPolygon(outDirTriangle);
        if(inDirTriangle != null) g.fillPolygon(inDirTriangle);

        super.draw(g);
    }

    /**
     * Visszaadja a reprezentált pumpa referenciáját.
     * @return a reprezentált pumpa referenciája
     */
    @Override
    public Field getGameReference() {
        return p;
    }

    /**
     *  Visszaadja a következő sorokat tartalmazó String-et:
     *  “Pump”
     * “Broken: <yes/no>”: p.isBroken() függvényében
     * @return a tooltip-en megjelenítendő szöveg
     */
    @Override
    public String getTooltipText() {
        String str = "<html>Pump";
        str += "<br>Broken: " + (p.isBroken() ? "yes" : "no");
        str += "</html>";
        return str;
    }
}
