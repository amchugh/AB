package Editor;

import GameControl.ACellManager;
import GameControl.AMapInstance;
import GameControl.AMapWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AMapDisplay extends JFrame {

    private class APanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D gg = (Graphics2D) g;

            // Draw a border on the canvas
            gg.setColor(Color.BLACK);
            gg.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

            // Draw the map
            map.draw(g);
        }
    }

    class CellIcon implements Icon {
        private int cellId;
        private Image i;

        public CellIcon() {
        }

        public int getIconWidth() {
            return cellManager.getCellWidth();
        }

        public int getIconHeight() {
            return cellManager.getCellHeight();
        }

        public void setCellId(int cellId) {
            this.cellId = cellId;
            i = cellManager.getCellImage(cellId);
        }

        public int getCellId() {
            return cellId;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (i != null) {
                g.drawImage(i, x, y, getIconWidth(), getIconHeight(), null);
            }
        }
    }

    private ACellManager cellManager;
    private AMapInstance map;
    private APanel panel;
    private CellIcon currentSelectedCell;
    JPanel currentSelectedPanel;

    public AMapDisplay(Dimension window_size, ACellManager cellManager, AMapInstance map, AMapWriter mapWriter) {
        this.setTitle("");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(window_size);
        this.setLocation(50, 50);

        pack();
        Dimension viewable_size = getContentPane().getSize();

        this.map = map;
        this.cellManager = cellManager;

        int mapOffsetX = 10;
        int mapOffsetY = 10;
        int mapSizeX = (viewable_size.width - (4 * mapOffsetX)) / 2;
        int mapSizeY = (viewable_size.height - (2 * mapOffsetY));

        currentSelectedCell = new CellIcon();
        currentSelectedCell.setCellId(0);
        panel = new APanel();

        panel.setBounds(mapOffsetX, mapOffsetY, mapSizeX, mapSizeY);
        panel.setFocusable(false);
    
        this.setFocusable(true);
        this.setLayout(null);

        JLayeredPane lp = getLayeredPane();
        lp.add(panel);

        System.out.println("mapOffset: ( " + mapOffsetX + ", " + mapOffsetY + " )");
        System.out.println("mapSize: ( " + mapSizeX + ", " + mapSizeY + " )");

        int cellViewportX = mapSizeX + (2 * mapOffsetX);
        int cellViewportY = mapOffsetY;
        int cellViewportSizeX = mapSizeX;
        int cellViewportSizeY = mapSizeY - 200;
        int cellButtonSizeX = 22;
        int cellButtonSizeY = 22;
        int cellButtonBufferX = 3;
        int cellButtonBufferY = 3;

        System.out.println("mapOffset: ( " + mapOffsetX + ", " + mapOffsetY + " )");
        System.out.println("mapSize: ( " + mapSizeX + ", " + mapSizeY + " )");

        System.out.println("cellViewPort: ( " + cellViewportX + ", " + cellViewportY + " )");
        System.out.println("cellViewportSize: ( " + cellViewportSizeX + ", " + cellViewportSizeY + " )");

        int cellId = 0;
        JPanel cellPanel = new JPanel();
        cellPanel.setBounds(cellViewportX, cellViewportY, cellViewportSizeX, cellViewportSizeY);
        cellPanel.setSize(new Dimension(cellViewportSizeX, cellViewportSizeY));
        cellPanel.setBorder(BorderFactory.createTitledBorder(
                "Cells"));

        final AMapDisplay containingWindow = this;
        while (cellManager.isCellIdValid(cellId)) {
            JButton b = new JButton();
            b.setIcon(new ImageIcon(cellManager.getCellImage(cellId)));
            b.setBounds(cellViewportX, cellViewportY,
                    cellButtonSizeX,
                    cellButtonSizeY);
            final int c = cellId;
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    currentSelectedCell.setCellId(c);
                    currentSelectedPanel.repaint();
                    System.out.println("Got a click on cell: " + c);
                    containingWindow.requestFocus();
                }
            });
            cellPanel.add(b);
            cellId += 1;

            if (cellViewportX > viewable_size.width - (cellButtonSizeX + cellButtonBufferX)) {
                cellViewportX = mapSizeX + mapOffsetX;
                cellViewportY += cellButtonSizeY + cellButtonBufferY;
            } else {
                cellViewportX += cellButtonSizeX + cellButtonBufferX;
            }
        }

        int currentCellX = mapSizeX + (2 * mapOffsetX);
        int currentCellY = mapOffsetY + cellViewportSizeY + mapOffsetY;
        int currentCellSizeX = cellViewportSizeX;
        int currentCellSizeY = currentSelectedCell.getIconHeight() * 5;

        System.out.println("currentCell: ( " + currentCellX + ", " + currentCellY + " )");
        System.out.println("currentCellSize: ( " + currentCellSizeX + ", " + currentCellSizeY + " )");

        currentSelectedPanel = new JPanel();
        currentSelectedPanel.setBounds(currentCellX, currentCellY, currentCellSizeX, currentCellSizeY);
        currentSelectedPanel.setSize(new Dimension(currentCellSizeX, currentCellSizeY));
        currentSelectedPanel.setBorder(BorderFactory.createTitledBorder(
                "Current Cell"));
        JLabel label = new JLabel(currentSelectedCell);
        currentSelectedPanel.add(label);

        JPanel operationsPanel = new JPanel();
        operationsPanel.setBounds(currentCellX, currentCellY + currentCellSizeY + mapOffsetY,
                currentCellSizeX,
                currentCellSizeY);
        operationsPanel.setSize(new Dimension(currentCellSizeX, currentCellSizeY));
        operationsPanel.setBorder(BorderFactory.createTitledBorder(
                "Operations"));
        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mapWriter.writeMap(map);
                System.out.println("Wrote map");
                containingWindow.requestFocus();
            }
        });
        operationsPanel.add(save);

        lp.add(cellPanel);
        lp.add(currentSelectedPanel);
        lp.add(operationsPanel);
        this.pack();
    }

    public int getCurrentCellId() {
        return currentSelectedCell.getCellId();
    }
}
