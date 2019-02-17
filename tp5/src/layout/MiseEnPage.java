package layout;

import javax.swing.*;
import java.awt.*;

public class MiseEnPage extends JFrame {

    JButton bBorder, bFlow, bGrid, bCompose, bQuit;

    public MiseEnPage() {
        init();
    }

    public static void main(String[] args) {
        MiseEnPage frame = new MiseEnPage();

        frame.setTitle("Le titre");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.flow();
    }

    public void init() {
        gestionBoutons();
    }

    public void gestionBoutons() {
        bBorder = new JButton("Border");
        bBorder.addActionListener(
                e -> {
                    this.border();
                }
        );

        bFlow = new JButton("Flow");
        bFlow.addActionListener(
                e -> {
                    this.flow();
                }
        );
        bGrid = new JButton("Grid");
        bGrid.addActionListener(
                e -> {
                    this.grid();
                }
        );

        bCompose = new JButton("Compose");
        bCompose.addActionListener(
                e -> {
                    this.compose();
                }
        );

        bQuit = new JButton("Quit");
        bQuit.addActionListener(
                e -> {
                    System.exit(0);
                }
        );

        if(this.getLayout().getClass().getSimpleName().equals("BorderLayout")) {
            this.add(bBorder, BorderLayout.WEST);
            this.add(bFlow, BorderLayout.NORTH);
            this.add(bGrid, BorderLayout.EAST);
            this.add(bCompose, BorderLayout.CENTER);
            this.add(bQuit, BorderLayout.SOUTH);
        }
        else {
            this.add(bBorder);
            this.add(bFlow);
            this.add(bGrid);
            this.add(bCompose);
            this.add(bQuit);
        }
    }

    public void changeLayout(LayoutManager lm) {
        this.setTitle(lm.getClass().getSimpleName());
        this.getContentPane().removeAll();
        this.setLayout(lm);
        gestionBoutons();
        validate();
        repaint();
    }

    public void flow() {
        changeLayout(new FlowLayout());
    }

    public void grid() {
        changeLayout(new GridLayout());
    }

    public void border() {
        changeLayout(new BorderLayout());
    }

    public void compose() {
        this.setTitle("Compose");
        this.getContentPane().removeAll();

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout());

        bBorder = new JButton("Border");
        bBorder.addActionListener(
                e -> {
                    this.border();
                }
        );

        bFlow = new JButton("Flow");
        bFlow.addActionListener(
                e -> {
                    this.flow();
                }
        );
        bGrid = new JButton("Grid");
        bGrid.addActionListener(
                e -> {
                    this.grid();
                }
        );

        p1.add(bBorder);
        p1.add(bFlow);
        p1.add(bGrid);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout());

        bCompose = new JButton("Compose");
        bCompose.addActionListener(
                e -> {
                    this.compose();
                }
        );

        bQuit = new JButton("Quit");
        bQuit.addActionListener(
                e -> {
                    System.exit(0);
                }
        );

        p2.add(bCompose);
        p2.add(bQuit);

        this.add(p1);
        this.add(p2);

        GridLayout gl = new GridLayout(2, 1);
        gl.setVgap(50);
        gl.setHgap(100);
        this.setLayout(gl);

        this.validate();
        this.repaint();

    }


}
