package GameControl.Tools;

import GameControl.ACellManager;
import GameControl.AMapInstance;

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

    private ACellManager cellManager;
    private AMapInstance map;
    private APanel panel;

    public AMapDisplay(Dimension window_size, ACellManager cellManager, AMapInstance map) {
        this.setTitle("");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.map = map;

        panel = new APanel();
        
        JLayeredPane lp = getLayeredPane();
        
        int canvasSizeX = 200;
        int canvasSizeY = 400;

        panel.setBounds(0, 0, canvasSizeX, canvasSizeY);
        panel.setFocusable(false);
    
        this.setFocusable(true);
        this.setLayout(null);

        lp.add(panel, 0);
        
        this.setPreferredSize(window_size);
        this.setLocation(50, 50); // Position on screen

        this.cellManager = cellManager;

        int cellViewportX = 400;
        int cellViewportY = 10;
        int cellButtonSizeX = 26;
        int cellButtonSizeY = 26;
        int cellButtonBufferX = 5;
        int cellButtonBufferY = 5;

        int cellId = 0;
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
                    System.out.println("Got a click on cell: " + c);
                }
            });
            lp.add(b, 1);
            cellId += 1;

            if (cellViewportX > 725) {
                cellViewportX = 400;
                cellViewportY += cellButtonSizeY + cellButtonBufferY;
            } else {
                cellViewportX += cellButtonSizeX + cellButtonBufferX;
            }
        }
        this.pack();
    }
}
