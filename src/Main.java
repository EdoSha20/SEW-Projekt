/**
 * Hauptklasse – startet die Anwendung.
 * @author Person1
 * @version 1.0
 */
public class Main {

    /**
     * Einstiegspunkt der Anwendung.
     * @param args Kommandozeilenargumente (werden nicht verwendet)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            HaushaltsbuchGUI gui = new HaushaltsbuchGUI();
            gui.setVisible(true);
        });
    }
}
