import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Locale;

public class Graphics extends JFrame {
    /**
     * all the GUI elements
     */
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel menuCard;
    private JButton bQuit;
    private JButton bPlay;
    private JPanel gameCard;
    private JLabel lWelcome;
    private Map pMap;
    private JPanel pInformation;
    private JPanel pButtons;
    private JLabel lMechText;
    private JLabel lSabText;
    private JLabel lActionText;
    private JLabel lMechPoints;
    private JLabel lSabPoints;
    private JLabel lActionPoints;
    private JLabel lCustomText;
    private JButton bMovePipe;
    private JButton bMove;
    private JButton bMakeSlippery;
    private JButton bMakeSticky;
    private JButton bBreak;
    private JButton bRepair;
    private JButton bPlace;
    private JButton bPickup;
    private JButton bChangePumpDirection;
    private JButton bEndGame;
    private JButton bEndTurn;

    //logic
    private int moveIC = 0; // move interaction counter
    private int movePipeIC = 0; // move pipe interaction counter
    private int changePumpDirectionIC = 0; // change pump direction interaction counter
    private ArrayList<DisplayField> clicked = new ArrayList<>();

    private void modelUpdated(String operationName) {
        lCustomText.setText("<html>Last operation was: " + operationName + ".</html>");
        lMechPoints.setText(Game.getInstance().getMechanicPoints() + "");
        lSabPoints.setText(Game.getInstance().getSaboteurPoints() + "");
        int actionNum = Game.getInstance().getActionNumber();
        if(actionNum > 0) {
            lActionPoints.setForeground(new Color(0,0,0));
            lActionPoints.setText(actionNum + "");
        } else {
            lActionPoints.setForeground(new Color(255,0,0));
            lActionPoints.setText("None!");
        }
        if(pMap != null) pMap.repaint();
    }

    public Graphics() {
        super("Game");
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Game.getInstance().play();

        //making all initial GUI setups
        pMap = new Map(
                Game.getInstance().getCisterns(),
                Game.getInstance().getSources(),
                Game.getInstance().getPumps(),
                Game.getInstance().getPipes()
        );
        pMap.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Graphics.this.mousePressed(e);
            }
        });
        setup();

        //making the window visible and setting the cardLayout
        this.setContentPane(cardPanel);
        this.setVisible(true);
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        modelUpdated("Tip: Drag&Drop with <br> right click to move fields around!");

        //setting action commands for buttons
        bPlay.addActionListener(e -> cardLayout.next(cardPanel));
        bQuit.addActionListener(e -> {
            messagePopup("Tie!");
            cardLayout.previous(cardPanel);
        });
        bEndGame.addActionListener(e -> endGame());
        bBreak.addActionListener(e -> breakField());
        bRepair.addActionListener(e -> repairField());
        bMakeSlippery.addActionListener(e -> makeSlippery());
        bMakeSticky.addActionListener(e -> makeSticky());
        bPlace.addActionListener(e -> place());
        bPickup.addActionListener(e -> pickup());
        bChangePumpDirection.addActionListener(e -> changePumpDirection());
        bEndTurn.addActionListener(e -> endTurn());
        bMovePipe.addActionListener(e -> movePipe());
        bMove.addActionListener(e -> move());
    }
    public void move() {
        System.out.println(clicked);

        //This call is valid only if there was an appointed object
        int clicked_count = clicked.size();
        if(clicked_count >= 1 && clicked.get(clicked_count-1) != null) {

            //getGameReference returns the logic behind the interpreter
            //the graphics are modeled based on the logic behind it
            Game.getInstance().getActivePlayer().moveToField(
                    clicked.get(clicked_count-1).getGameReference());

            //If the procedure was successful then we clear the list
            clicked.clear();

            //Display, that the move button was pressed most recently
            modelUpdated("move");
        }
    }

    public void movePipe() {
        System.out.println(clicked);

        //This call is valid only if there was an appointed object
        int clicked_count = clicked.size();

        //If the second to last was a pipe that indicates that this
        // is a pipe with one end levitating. Then we need only 2 clicks.
        if(clicked_count >= 2 && clicked.get(clicked_count-2).getPipe() != null){
            //The first one must be a pipe
            Pipe pipe = clicked.get(clicked_count - 2).getPipe();

            //The newEnd can be null pointer as well. In that case the pipe will hang.
            Field to = null;
            if (clicked.get(clicked_count - 1) != null)
                to = clicked.get(clicked_count - 1).getGameReference();

            //This case if only jogos if pipe has fewer neighbours than 2
            if(pipe.getNeighbours().size() < 2)
                Game.getInstance().movePipe(pipe, null, to);

            // If the procedure was successful then we clear the list
            clicked.clear();

            // call every time the model changes
            modelUpdated("Move Pipe");
        }

        //in case of a pipe that is connected from both ends
        else if(clicked_count >= 3) {
            //The first one must be a pipe
            if (clicked.get(clicked_count - 3) == null) return;
            Pipe pipe = clicked.get(clicked_count - 3).getPipe();

            //old end cannot be null
            if (clicked.get(clicked_count - 2) == null) return;
            Field from = clicked.get(clicked_count - 2).getGameReference();

            //The newEnd can be null pointer as well. In that case the pipe will hang.
            Field to = null;
            if (clicked.get(clicked_count - 1) != null)
                to = clicked.get(clicked_count - 1).getGameReference();

            //Invoking the function that with the logic responsibility
            if (pipe != null && from != null)
                Game.getInstance().movePipe(pipe, from, to);

            // If the procedure was successful then we clear the list
            clicked.clear();

            // call every time the model changes
            modelUpdated("Move Pipe");
        }
    }

    public void endGame() {
        if(Game.getInstance().getSaboteurPoints() >= 100)
            messagePopup("A sabotourok nyertek!");

        else if(Game.getInstance().getMechanicPoints() >= 100)
            messagePopup("A szerelők nyertek!");

        else
            messagePopup("Döntetlen lett!");

        cardLayout.previous(cardPanel);

        Game.getInstance().end();

        modelUpdated("<feature not yet implemented>"); // call every time the model changes
    }

    public void makeSlippery() {
        Game.getInstance().makeSlippery();
        modelUpdated("make slippery"); // call every time the model changes
    }

    public void makeSticky() {
        Game.getInstance().makeSticky();
        modelUpdated("make sticky"); // call every time the model changes
    }

    public void breakField() {
        Game.getInstance().breakField();
        modelUpdated("break field"); // call every time the model changes
    }

    public void repairField() {
        Game.getInstance().repairField();
        modelUpdated("repair field"); // call every time the model changes
    }

    public void place() {
        int actionPoints = Game.getInstance().getActionNumber();

        //Calls the function of the logic behind it
        Game.getInstance().getActivePlayer().place();

        // call even if place unsuccessful
        if(actionPoints > Game.getInstance().getActionNumber()) {
            pMap.pumpPlaced(Game.getInstance().getPipes(), Game.getInstance().getPumps());

            // call every time the model changes
            modelUpdated("place");
        }
    }

    public void pickup() {
        Game.getInstance().pickup();
        modelUpdated("pickup"); // call every time the model changes
    }

    public void changePumpDirection() {
        System.out.println(clicked);

        //This call is valid only if there was an appointed object
        int clicked_count = clicked.size();

        //If there were two objects assigned then is good
        if(clicked_count >= 2) {
            //We read the last 2 object that were clicked
            boolean not_null = clicked.get(clicked_count-1) != null && clicked.get(clicked_count-2) != null;

            //If there was a missclick than ignore
            if(!not_null) return;

            //If getPipe returns null the input/output is null;
            //First click in second click out
            Game.getInstance().getActivePlayer().changePumpDirection(
                    clicked.get(clicked_count-2).getPipe(),
                    clicked.get(clicked_count-1).getPipe()
            );
            //If the procedure was successful then we clear the list
            clicked.clear();

            // call every time the model changes
            modelUpdated("Change Pump Direction");
        }
    }

    public void endTurn() {
        Game.getInstance().endTurn();

        if(Game.getInstance().getMechanicPoints() >= 100 || Game.getInstance().getSaboteurPoints() >= 100)
            endGame();

        pMap.repaint(); // call every time the model changes
        modelUpdated("end turn"); // notify user about the operation
    }

    /**
     * creates a popup window to display the message given as parameter
     * @param message
     */
    public void messagePopup(String message) {
        JDialog popup = new JDialog(this, "Game Message");
        popup.setLocationRelativeTo(null);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setSize(200, 100);
        JLabel labelMessage = new JLabel(message);
        popup.add(labelMessage);
        popup.setVisible(true);
    }

    private void mousePressed(MouseEvent e) {
        if(e.getButton() != MouseEvent.BUTTON1) return; // only listening for left click

        DisplayPipe p = pMap.getPipeFromClick(e.getX(), e.getY());
        if(p != null) {
            System.out.println("Clicked on pipe: " + p.getGameReference()); //TODO
            clicked.add(p);
        }


        DisplayNode n = pMap.getNodeFromClick(e.getX(), e.getY());
        if(n != null) {
            System.out.println("Clicked on node: " + n.getGameReference()); //TODO
            clicked.add(n);
        }

        if(p == null && n == null) {
            System.out.println("Clicked outside of any drawn nodes / pipes");
            clicked.add(null);
        }
    }

    /**
     * setting up the overall look and structure of the GUI
     * this code was originally generated by the Swing Designer, but it is just copied here,
     * so it runs on any platform, not only in Intellij
     */
    private void setup() {
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout(0, 0));
        menuCard = new JPanel();
        menuCard.setLayout(new GridBagLayout());
        cardPanel.add(menuCard, "menu");
        bQuit = new JButton();
        bQuit.setText("Quit");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 50;
        menuCard.add(bQuit, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuCard.add(spacer1, gbc);
        bPlay = new JButton();
        bPlay.setText("Play");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 50;
        menuCard.add(bPlay, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuCard.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuCard.add(spacer3, gbc);
        lWelcome = new JLabel();
        lWelcome.setBackground(new Color(-16777216));
        lWelcome.setEnabled(true);
        Font lWelcomeFont = this.getFont("Arial", Font.PLAIN, 48, lWelcome.getFont());
        if (lWelcomeFont != null) lWelcome.setFont(lWelcomeFont);
        lWelcome.setForeground(new Color(-16777216));
        lWelcome.setHorizontalAlignment(0);
        lWelcome.setHorizontalTextPosition(0);
        lWelcome.setText("Welcome");
        lWelcome.setVerticalAlignment(0);
        lWelcome.setVerticalTextPosition(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 2;
        gbc.ipadx = 200;
        gbc.ipady = 50;
        menuCard.add(lWelcome, gbc);
        gameCard = new JPanel();
        gameCard.setLayout(new GridBagLayout());
        cardPanel.add(gameCard, "game");

        //properties of pMap
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 10.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gameCard.add(pMap, gbc);

        pInformation = new JPanel();
        pInformation.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gameCard.add(pInformation, gbc);
        lMechText = new JLabel();
        lMechText.setHorizontalAlignment(2);
        lMechText.setText("Mechanic Points: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.ipadx = 50;
        gbc.ipady = 30;
        gbc.insets = new Insets(30, 0, 0, 0);
        pInformation.add(lMechText, gbc);
        lSabText = new JLabel();
        lSabText.setHorizontalAlignment(2);
        lSabText.setText("Saboteur Points: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 50;
        gbc.ipady = 30;
        pInformation.add(lSabText, gbc);
        lActionText = new JLabel();
        lActionText.setHorizontalAlignment(2);
        lActionText.setText("Action Points Left In Round: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 50;
        gbc.ipady = 30;
        pInformation.add(lActionText, gbc);
        lMechPoints = new JLabel();
        lMechPoints.setText("3");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 50.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 50;
        gbc.ipady = 30;
        gbc.insets = new Insets(10, 0, 0, 0);
        pInformation.add(lMechPoints, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pInformation.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        pInformation.add(spacer5, gbc);
        lSabPoints = new JLabel();
        lSabPoints.setText("2");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 50;
        gbc.ipady = 30;
        pInformation.add(lSabPoints, gbc);
        lActionPoints = new JLabel();
        lActionPoints.setText("1");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 50;
        gbc.ipady = 30;
        pInformation.add(lActionPoints, gbc);

        lCustomText = new JLabel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 50;
        gbc.ipady = 30;
        pInformation.add(lCustomText, gbc);

        pButtons = new JPanel();
        pButtons.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 3.0;
        gbc.fill = GridBagConstraints.BOTH;
        gameCard.add(pButtons, gbc);
        bMovePipe = new JButton();
        bMovePipe.setHorizontalTextPosition(0);
        bMovePipe.setText("<html>Move<br>Pipe</html>");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2.0;
        gbc.fill = GridBagConstraints.BOTH;
        pButtons.add(bMovePipe, gbc);
        bBreak = new JButton();
        bBreak.setText("Break");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        pButtons.add(bBreak, gbc);
        bChangePumpDirection = new JButton();
        bChangePumpDirection.setHorizontalTextPosition(0);
        bChangePumpDirection.setText("<html>Change<br>Pump<br>Direction</html>");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 3.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        pButtons.add(bChangePumpDirection, gbc);
        bMakeSlippery = new JButton();
        bMakeSlippery.setHorizontalTextPosition(0);
        bMakeSlippery.setText("<html>Make<br>Slippery</html>");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 2.0;
        gbc.fill = GridBagConstraints.BOTH;
        pButtons.add(bMakeSlippery, gbc);
        bMakeSticky = new JButton();
        bMakeSticky.setHorizontalTextPosition(0);
        bMakeSticky.setText("<html>Make<br>Sticky</html>");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 2.0;
        gbc.fill = GridBagConstraints.BOTH;
        pButtons.add(bMakeSticky, gbc);
        bMove = new JButton();
        bMove.setText("Move");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 50;
        pButtons.add(bMove, gbc);
        bRepair = new JButton();
        bRepair.setText("Repair");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 50;
        pButtons.add(bRepair, gbc);
        bPlace = new JButton();
        bPlace.setText("Place");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        pButtons.add(bPlace, gbc);
        bPickup = new JButton();
        bPickup.setText("Pickup");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        pButtons.add(bPickup, gbc);
        bEndGame = new JButton();
        bEndGame.setHorizontalTextPosition(0);
        bEndGame.setText("<html>End<br>Game</html>");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        pButtons.add(bEndGame, gbc);
        bEndTurn = new JButton();
        bEndTurn.setHorizontalTextPosition(0);
        bEndTurn.setText("<html>End<br>Turn</html>");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        pButtons.add(bEndTurn, gbc);
    }

    /**
     * generated code copied here
     * creates the font that is used in the setup method to set the font of the "Welcome" word
     * @param fontName
     * @param style
     * @param size
     * @param currentFont
     * @return
     */
    private Font getFont(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * generated code copied here, currently not used
     * @return
     */
    public JComponent getRootComponent() {
        return cardPanel;
    }
}
