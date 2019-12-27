package GameControl.Tools;

import GameControl.ACellManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AMapDisplay extends JFrame {

    private ACellManager cellManager;

    public AMapDisplay(Dimension window_size, ACellManager cellManager, AMapEditorController amep) {
        this.setTitle("");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        JLayeredPane lp = getLayeredPane();
        
        int canvasSizeX = 200;
        int canvasSizeY = 400;
    
        amep.setBounds(0,0,canvasSizeX,canvasSizeY);
        amep.setFocusable(false);
    
        this.setFocusable(true);
        this.setLayout(null);
        
        lp.add(amep, 0);
        
        this.setPreferredSize(window_size);
        this.setLocation(50, 50); // Position on screen

        this.cellManager = cellManager;

        int cellViewportX = 400;
        int cellViewportY = 10;
        int cellButtonSizeX = 60;
        int cellButtonSizeY = 18;

        JButton b = new JButton("Test");
        b.setBounds(cellViewportX + 10, cellViewportY + 10,
                cellButtonSizeX,
                cellButtonSizeY);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("Got a click");
            }
        });
        lp.add(b, 1);
    
        this.pack();
    }
    
}
