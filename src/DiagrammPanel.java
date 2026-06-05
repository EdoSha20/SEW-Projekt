import javax.swing.*;
import java.awt.*;

/**
 * Zeichnet ein Balkendiagramm für Einnahmen und Ausgaben.
 * @author Person1
 * @version 1.0
 */
public class DiagrammPanel extends JPanel {

    /** Betrag der Einnahmen. */
    private double einnahmen;

    /** Betrag der Ausgaben. */
    private double ausgaben;

    /** Maximale Balkenhöhe in Pixeln. */
    private static final int MAX_HOEHE = 200;

    /**
     * Erstellt ein neues DiagrammPanel.
     */
    public DiagrammPanel() {
        this.einnahmen = 0;
        this.ausgaben = 0;
        setPreferredSize(new Dimension(300, 280));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Übersicht"));
    }

    /**
     * Aktualisiert die angezeigten Werte und zeichnet neu.
     * @param einnahmen Gesamteinnahmen in Euro
     * @param ausgaben  Gesamtausgaben in Euro
     */
    public void aktualisieren(double einnahmen, double ausgaben) {
        this.einnahmen = einnahmen;
        this.ausgaben = ausgaben;
        repaint();
    }

    /**
     * Zeichnet das Balkendiagramm.
     * @param g Graphics-Objekt für die Darstellung
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double max = Math.max(einnahmen, Math.max(ausgaben, 1));
        int balkenBreite = 80;
        int startY = 230;

        // Einnahmen-Balken (grün)
        int hoeheEinnahmen = (int) (einnahmen / max * MAX_HOEHE);
        g2.setColor(new Color(76, 175, 80));
        g2.fillRect(40, startY - hoeheEinnahmen, balkenBreite, hoeheEinnahmen);
        g2.setColor(Color.BLACK);
        g2.drawRect(40, startY - hoeheEinnahmen, balkenBreite, hoeheEinnahmen);
        g2.drawString(String.format("%.0f€", einnahmen), 45, startY - hoeheEinnahmen - 5);
        g2.drawString("Einnahmen", 35, startY + 18);

        // Ausgaben-Balken (rot)
        int hoeheAusgaben = (int) (ausgaben / max * MAX_HOEHE);
        g2.setColor(new Color(244, 67, 54));
        g2.fillRect(170, startY - hoeheAusgaben, balkenBreite, hoeheAusgaben);
        g2.setColor(Color.BLACK);
        g2.drawRect(170, startY - hoeheAusgaben, balkenBreite, hoeheAusgaben);
        g2.drawString(String.format("%.0f€", ausgaben), 175, startY - hoeheAusgaben - 5);
        g2.drawString("Ausgaben", 173, startY + 18);

        // Basislinie
        g2.drawLine(20, startY, 280, startY);
    }
}
