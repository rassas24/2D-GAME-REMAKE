package Main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel) {

        jframe = new JFrame();
        //ImageIcon icon = new ImageIcon("path/to/your/logo.png");
        //jframe.setIconImage(icon.getImage());

        String logoImagePath = "C:/Users/faisa/IdeaProjects/platformer/src/res/logo.png";

        // Load the image and set it as the window icon
        ImageIcon icon = new ImageIcon(logoImagePath);
        jframe.setIconImage(icon.getImage());


        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);

        jframe.setResizable(false);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                // TODO Auto-generated method stub

            }
        });

    }

}
