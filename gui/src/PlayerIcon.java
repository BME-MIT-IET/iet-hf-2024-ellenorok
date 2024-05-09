import java.awt.*;

public class PlayerIcon {
    Player p;
    Rectangle container;
    final Color p1Color = new Color(255,0,0);
    final Color p2Color = new Color(196, 185, 71);

    public PlayerIcon(Rectangle _container, Player _p) {
        container = _container;
        p = _p;
    }
    public void draw(java.awt.Graphics g) {
        if(Game.getInstance().getMechanics().contains(p)) {
            Polygon polygon = new Polygon();
            polygon.addPoint(container.x, container.y + container.height);
            polygon.addPoint(container.x + container.width, container.y + container.height);
            polygon.addPoint(container.x + container.width / 2, container.y);

            g.setColor(Game.getInstance().getMechanics().indexOf(p) == 0 ? p1Color : p2Color);

            if(p == Game.getInstance().getActivePlayer()) {
                g.fillPolygon(polygon);
            } else {
                g.drawPolygon(polygon);
            }
        } else {
            g.setColor(Game.getInstance().getSaboteurs().indexOf(p) == 0 ? p1Color : p2Color);

            if(p == Game.getInstance().getActivePlayer()) {
                g.fillOval(container.x, container.y, container.width, container.height);
            } else {
                g.drawOval(container.x, container.y, container.width, container.height);
            }
        }
   }
}
