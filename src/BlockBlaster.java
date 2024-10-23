import javax.swing.JFrame;

public class BlockBlaster extends JFrame {

    public BlockBlaster() {
        initUI();
    }

    private void initUI() {
        add(new GameBoard());  // Adding the game board where the game will run
        setTitle("Block Blaster");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);  // Setting the window size
        setLocationRelativeTo(null);
        setResizable(false);
    }

    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            BlockBlaster game = new BlockBlaster();
            game.setVisible(true);
        });
    }
}
