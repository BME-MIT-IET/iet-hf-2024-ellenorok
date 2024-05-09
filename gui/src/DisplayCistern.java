import java.awt.*;

/**
 * Egy kirajzolt ciszternát (Cistern) reprezentál.
 */
public class DisplayCistern extends DisplayNode {
    /**
     * A reprezentált ciszterna referenciája.
     */
    Cistern c;

    /**
     * A kirajzolt ciszterna megjelenítési és körvonal színe.
     */
    final Color color = new Color(160,82,45);
    public DisplayCistern(Rectangle _container, Cistern _c) {
        super(_container);
        c = _c;
    }

    /**
     * Kirajzolja a bennfoglaló téglalapját color kitöltő és körvonal színnel.
     * @param g a grafikus objektum
     */
    @Override
    public void draw(java.awt.Graphics g){
        g.setColor(color);
        g.fillRect(container.x, container.y, container.width, container.height);

        super.draw(g);
    }

    /**
     * Visszaadja a reprezentált ciszterna referenciáját (c).
     * @return a reprezentált ciszterna referenciája
     */
    @Override
    public Field getGameReference() {
        return c;
    }

    /**
     * Visszaadja a következő sorokat tartalmazó String-et:
     *  “Cistern”
     * “Has pump: <yes/no>”: c.hasPump() függvényében
     * @return
     */
    @Override
    public String getTooltipText() {
        String str = "<html>Cistern";
        str += "<br>Has pump: " + (c.hasPump() ? "yes" : "no");
        str += "</html>";
        return str;
    }
}
