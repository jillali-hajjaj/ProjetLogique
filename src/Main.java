import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                try {
                    new fenetre();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}