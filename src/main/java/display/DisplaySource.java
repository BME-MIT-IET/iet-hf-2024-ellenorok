package display;

import model.Field;
import model.Source;

import java.awt.*;

/**
 * Egy kirajzolt forrást (Source) reprezentál.
 */
public class DisplaySource extends DisplayNode {
    /**
     * A reprezentált forrás.
     */
    private Source s;
    /**
     * A forrás megjelenítési színe
     */
    private final Color color = new Color(8, 78, 255, 255);

    public DisplaySource(Rectangle _container, Source _s) {
        super(_container);
        s = _s;
    }

    /**
     * Kirajzolja a paraméterként kapott grafikus objektumra a forrást, a container bennfoglaló négyzetbe,
     * color kitöltési és keret színnel.
     * @param g a grafikus objektum
     */
    @Override
    public void draw(java.awt.Graphics g){
        g.setColor(color);
        g.fillRect(container.x, container.y, container.width, container.height);

        super.draw(g);
    }

    /**
     * Visszaadja a reprezentált forrás objektumot (s).
     * @return a reprezentált forrás objektum
     */
    @Override
    public Field getGameReference() {
        return s;
    }

    /**
     * A következő sztringet adja vissza: “Source”.
     * @return a tooltip-ben megjelenítendő szöveg
     */
    @Override
    public String getTooltipText() {
        return "Source";
    }
}
