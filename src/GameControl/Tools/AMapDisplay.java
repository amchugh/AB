package GameControl.Tools;

import GameControl.ACellManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AMapDisplay extends JFrame {

    public Canvas canvas;
    private ACellManager cellManager;

    public AMapDisplay(Dimension window_size, ACellManager cellManager) {
        this.setTitle("");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        canvas = new Canvas();

        canvas.setPreferredSize(window_size);
        canvas.setFocusable(false);
        canvas.createBufferStrategy(2);

        this.add(canvas);
        this.pack();
        this.setLocation(50, 50); // Position on screen


        this.cellManager = cellManager;

        int cellViewportX = 400;
        int cellViewportY = 10;
        int cellButtonSizeX = 18;
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
        add(b);
        setLayout(null);
    }
}
