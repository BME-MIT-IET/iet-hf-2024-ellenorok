package iet.hf.ellenorok;

import java.awt.*;
import java.awt.Graphics;

/**
 * A csomópont szerű objektumok (pl. Cistern, Source, Pump) megjelenítéséért felelős osztály.
 */
public abstract class DisplayNode extends DisplayField {
    /**
     *  A felrajzolt csomópont bennfoglaló téglalapja.
     */
    protected Rectangle container;

    /**
     * Új csomópont a paraméterként kapott bennfoglaló téglalappal
     * @param _container a bennfoglaló téglalap
     */
    public DisplayNode(Rectangle _container) {
        container = _container;
    }

    /**
     * Feltölti a playerIcons tömb elemeit a getGameReference() által visszaadott Field getPlayers() függvényével
     * lekért játékosok listájából. A játékosok az objektumon sorrendben a konténerben (container) a következő
     * helyekre kerülnek, és mindkét dimenzióban fele akkorák mint a kirajzolt csomópont:
     * 1. bal felül
     * 2. jobb felül
     * 3. bal alul
     * 4. jobb alul
     */
    @Override
    protected void setPlayerIcons() {
        playerIcons.clear();

        // First player => top left
        Rectangle playerContainer = new Rectangle(
                container.x,
                container.y,
                container.width / 2,
                container.height / 2
        );
        for (int i = 0; i < getGameReference().getPlayers().size(); i++) {
            switch (i){
                case 0: //First player top left
                    playerContainer.x = container.x;
                    playerContainer.y = container.y;
                    break;
                case 1: //Second player top right
                    playerContainer.x = container.x + container.width / 2;
                    playerContainer.y = container.y;
                    break;
                case 2: //Third player bottom left
                    playerContainer.x = container.x;
                    playerContainer.y = container.y + container.height / 2;
                    break;
                case 3: //Fourth player bottom right
                    playerContainer.x = container.x + container.width / 2;
                    playerContainer.y = container.y + container.height / 2;
                    break;
                // There are only 4 cases, as there can be only 4 players;
            }

            //We put the player icon in the container specified
            PlayerIcon p = new PlayerIcon(new Rectangle(playerContainer), getGameReference().getPlayers().get(i)); // pass by value, not by reference
            playerIcons.add(p);
        }
    }

    /**
     * true, ha az (x,y) pont benne van a container téglalapban, false, ha nincs.
     * @param x a pont x koordinátája
     * @param y a pont y koordinátája
     * @return
     */
    @Override
    public boolean on(int x, int y) {
        if(container == null) return false;
        return container.contains(x,y);
    }

    /**
     * Beállítja a linkelési irányt a linkTowards csomópont felé, amennyiben az edge teljesít valamilyen feltételt.
     * A linkelési irány és a kritérium leszármazottanként eltérő, az alapértelmezett implementációban a függvény nem
     * csinál semmit.
     * @param edge
     * @param linkTowards
     */
    public void link(DisplayPipe edge, DisplayNode linkTowards) {}

    /**
     * Alapértelmezett értékre állítja a linkelési irányt.
     * A linkelési irány leszármazottanként eltérő, az alapértelmezett implementációban a függvény nem
     * csinál semmit.
     */
    public void unlink() {}

    /**
     * Implicit getter a felrajzolt csomópont bennfoglaló téglalapjához.
     * @return a felrajzolt csomópont bennfoglaló téglalapja
     */
    public final Rectangle getContainer() {
        return container;
    }

    /**
     * Kirajzolja a csomópontot a grafikus objektumra
     * @param g a grafikus objektum
     */
    @Override
    public void draw(Graphics g) {
        setPlayerIcons();
        for (PlayerIcon pi :
                playerIcons) {
            pi.draw(g);
        }
    }
}
