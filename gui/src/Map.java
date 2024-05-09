import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Map extends JPanel {
    /**
     * A panelen megjelenő pálya szélessége.
     */
    private final int sizeX = 1500;
    /**
     * A panel megjelenő pálya magassága.
     */
    private final int sizeY = 700;
    /**
     *  A panelen megjelenő csomópontok szélessége.
     */
    private final int nodeWidth = 50;
    /**
     * A panelen megjelenő csomópontok magassága.
     */
    private final int nodeHeight = 50;
    /**
     * Amennyiben a felhasználó éppen Drag&Drop operációt végez, a mozgatott csomópont referenciája, egyébként null.
     */
    private DisplayNode draggingNode = null;
    /**
     *  A kirajzolt csomópontok listája.
     */
    protected List<DisplayNode> nodes = new ArrayList<>();
    /**
     * A kirajzolt élek (csövek) listája.
     */
    protected List<DisplayPipe> edges = new ArrayList<>();

    /**
     * A kapott pipes lista alapján létrehozza a hozzájuk tartozó DisplayNode elemeket és eltárolja őket az edges
     * listában. A létrehozás minden pipes-belli p csőre a következők szerint:
     * p.getNeighbors() == 0: A csövet nem veszi figyelembe
     * p.getNeighbors() == 1: A cső egyik szomszédja azon
     *      nodes-beli n, melyre n.getGameReference() == p.GetNeighbors.get(0).
     *      A csőnek át kell adni egy olyan négyszöget, ahova a másik véget kösse,
     *      ezt n1 közepétől kicsit lejjebbre számolja ki.
     * p.getNeighbors() == 1: A cső egyik szomszédja azon
     *      nodes-beli n1, melyre n1.getGameReference() == p.GetNeighbors.get(0),
     *      másik szomszédja  azon nodes-beli n2, melyre n2.getGameReference() == p.GetNeighbors.get(1).
     * @param pipes az új lista
     */
    private void drawEdges(List<Pipe> pipes) {
        edges.clear(); // remove all edges
        for (DisplayNode n:
             nodes) {
            n.unlink(); // unlink all nodes
        }

        // Edges
        for (Pipe p : pipes) {
            if(p.getNeighbours().size() == 1) { // Only 1 end of pipe connected
                Field f1 = p.getNeighbours().get(0);
                DisplayNode n1 = nodes.stream().filter(n -> n.getGameReference() == f1).findFirst().orElse(null);

                Rectangle r = new Rectangle((int)n1.container.getCenterX(), (int)n1.container.getCenterY(), 0,0);
                r.y += nodeHeight * 2; // push it away a bit
                DisplayPipe edge = new DisplayPipe(p, n1, null, r); // connect edge to temporary container

                edges.add(edge);
                DisplayNode tempNode = new DisplayPump(new Rectangle(r.x, r.y, r.width,r.height), null);
                n1.link(edge, tempNode); // link other end of pipe
            } else if(p.getNeighbours().size() == 2) { // Both ends of pipe connected
                Field f1 = p.getNeighbours().get(0);
                Field f2 = p.getNeighbours().get(1);

                DisplayNode n1 = nodes.stream().filter(n -> n.getGameReference() == f1).findFirst().orElse(null);
                DisplayNode n2 = nodes.stream().filter(n -> n.getGameReference() == f2).findFirst().orElse(null);

                DisplayPipe edge = new DisplayPipe(p, n1, n2, null);
                edges.add(edge);

                if(n1 != null && n2 != null) { // linking (setting pump directions)
                    n1.link(edge, n2);
                    n2.link(edge, n1);
                }
            } else { // pipe not connected anywhere
                edges.add(new DisplayPipe(p, null, null, null));
            }
        }
    }
    public Map(List<Cistern> cisterns, List<Source> sources, List<Pump> pumps, List<Pipe> pipes) {
        super();
        ToolTipManager.sharedInstance().registerComponent(this); // register the component on the tooltip manager
        ToolTipManager.sharedInstance().setInitialDelay(0); // tooltips appear immediately
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Map.this.mousePressed(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Map.this.mouseReleased(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Map.this.mouseDragged(e);
            }
        });

        // Nodes
        Rectangle cursor = new Rectangle(0,0,nodeWidth,nodeHeight);
        final int nodeDistanceX = sizeX / Collections.max(Arrays.asList(cisterns.size(), pumps.size(), pipes.size())); // dynamic column amount
        final int nodeDistanceY = sizeY / 3; // Cistern, Pump, Source => 3 rows

        for (Source s: sources) {
            DisplaySource n = new DisplaySource(new Rectangle(cursor), s); // pass by value, not by reference
            nodes.add(n);
            cursor.x += nodeDistanceX; // sources next to each other horizontally
        }

        cursor.y += nodeDistanceY; // next row
        cursor.x = 0; // very left of next row
        for (Pump p: pumps) {
            DisplayPump n = new DisplayPump(new Rectangle(cursor), p); // pass by value, not by reference
            nodes.add(n);
            cursor.x += nodeDistanceX; // pumps next to each other horizontally
        }

        cursor.y += nodeDistanceY; // next row
        cursor.x = 0; // very left of next row
        for (Cistern c: cisterns) {
            DisplayCistern n = new DisplayCistern(new Rectangle(cursor), c); // pass by value, not by reference
            nodes.add(n);
            cursor.x += nodeDistanceX; // cisterns next to each other horizontally
        }

        drawEdges(pipes);
    }

    /**
     * Visszaadja azt a nodes-beli elemet, ami az (x,y) pontra van felrajzolva.
     * Ha több is ide van felrajzolva, ezek közül az egyiket, ha egy sincs, null-t
     * @param x A klikk x koordinátája
     * @param y A klikk y koordinátája
     * @return Az egyik DisplayNode, vagy null
     */
    public DisplayNode getNodeFromClick(int x, int y) {
        for (DisplayNode n :
                nodes) {
            if(n.on(x, y)) return n;
        }
        return null;
    }

    /**
     * Visszaadja azt az edges-beli elemet, ami az (x,y) pontra van felrajzolva.
     * Ha több is ide van felrajzolva, ezek közül az egyiket, ha egy sincs, null-t.
     * @param x A klikk x koordinátája
     * @param y A klikk y koordinátája
     * @return Az egyik DisplayPipe, vagy null
     */
    public DisplayPipe getPipeFromClick(int x, int y) {
        for (DisplayPipe p :
                edges) {
            if(p.on(x, y)) return p;
        }
        return null;
    }

    /**
     * A kapott két listából kitalálja, hogy melyik az új lerakott pumpa, melyik az eltűnt cső és melyik a két új cső.
     *  Ezután újrarajzolja a csöveket (drawEdges(pipes)).
     * @param pipes az új csövek listája
     * @param pumps az új pumpák listája
     */
    public void pumpPlaced(List<Pipe> pipes, List<Pump> pumps) {
        Point newPumpPos = null;
        // - 1 pipe
        for (DisplayPipe edge : edges) {
            if(!pipes.stream().anyMatch(p -> p == edge.getGameReference())) {
                newPumpPos = edge.getCenter(); // the new pump is drawn to the center of the removed pipe
                newPumpPos.x = newPumpPos.x - nodeWidth / 2;
                newPumpPos.y = newPumpPos.y - nodeHeight / 2;
                edges.remove(edge); // the old pipe is no longer in the game
                break;
            }
        }
        if(newPumpPos == null) return; // no pipe missing => no pump placed, returning
        // + 1 pump
        for (Pump p: pumps) {
            if(!nodes.stream().anyMatch(displayNode -> displayNode.getGameReference() == p)) {
                Rectangle container = new Rectangle(newPumpPos.x, newPumpPos.y, nodeWidth, nodeHeight);
                DisplayPump n = new DisplayPump(container, p);
                nodes.add(n); // add new pipe to our nodes
                break;
            }
        }
        // + 2 pipes, edges need to be redrawn
        drawEdges(pipes);
    }

    /**
     * Egér lenyomásakor hívódik meg, ha a jobb klikk lett lenyomva, és van a klikk (x,y) koordinátáján egy DisplayNode,
     * beállítja azt a draggingNode-ra. (Ennek kiderítéséhez: getNodeFromClick(e.getX(), e.getY())).
     * @param e az egér által kiváltott MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        if(e.getButton() != MouseEvent.BUTTON3) return; // only listening for right click
        if(draggingNode != null) return; // already set dragging node

        draggingNode = getNodeFromClick(e.getX(), e.getY());
    }

    /**
     * Egérgomb felengedésekor hívódik meg, ha a jobb klikk lettt felengedve, a draggingNode null lesz.
     * @param e az egér által kiváltott MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() != MouseEvent.BUTTON3) return; // only listening for right click
        if(draggingNode == null) return; // not dragging any nodes
        draggingNode = null;
    }

    /**
     * Egérgomb lenyomott állapota melletti egérmozgásra hívódik meg, amennyiben a draggingNode nem null,
     * annak konténerének (getContainer()) x és y koordinátáját a kurzor x és y koordinátájára állítja.
     * @param e az egér által kiváltott MouseEvent
     */
    public void mouseDragged(MouseEvent e) {
        if(draggingNode == null) return;

        if(!this.contains(e.getX(), e.getY())) {
            draggingNode = null; // stop the user from dragging node outside the screen
            return;
        }

        // move target node
        draggingNode.getContainer().x = e.getX() - nodeWidth / 2;
        draggingNode.getContainer().y = e.getY() - nodeHeight / 2;

        repaint();
    }

    /**
     * Felülírja a panel azonos szignatúrájú függvényét, az event-ből az egér koordinátái alapján kideríti
     * (getPipeFromClick(..) ill. getNodeFromClick(..)), melyik felrajzolt objektum felett van a kurzor,
     * és annak megjeleníti a tooltip-jét (getTooltipText()).
     * @param event the {@code MouseEvent} that initiated the
     *              {@code ToolTip} display
     * @return
     */
    @Override
    public String getToolTipText(MouseEvent event) {
        DisplayNode n = getNodeFromClick(event.getX(), event.getY());
        if(n != null) return n.getTooltipText();
        DisplayPipe p = getPipeFromClick(event.getX(), event.getY());
        if(p != null) return p.getTooltipText();
        return super.getToolTipText(event);
    }

    /**
     * Felrajzolja először az éleket (edges) aztán a  csúcsokat (nodes), azok draw(g) metódusának meghívásával.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(java.awt.Graphics g) {
        super.paint(g);

        //Redraw the canvas
        drawEdges(Game.getInstance().getPipes());

        //Draw the pipes first, so nodes are on top of edges
        for (DisplayPipe p : edges) p.draw(g);

        // first edges, then nodes => nodes are on top of edges
        for (DisplayField n : nodes) n.draw(g);
    }
}
