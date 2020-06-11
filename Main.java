package thamdinh;

import java.awt.EventQueue;

import javax.swing.UIManager;


public class Main {
	public static void main(String [] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    mp3Player window = new mp3Player();

                    window.frame.setVisible(true);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
