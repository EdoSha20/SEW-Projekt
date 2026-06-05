import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Hauptfenster der Haushaltsbuch-Anwendung.
 * Ermöglicht das Hinzufügen, Anzeigen und Löschen von Transaktionen.
 * @author Person1
 * @version 1.0
 */
public class HaushaltsbuchGUI extends JFrame {

    /** Das Haushaltsbuch mit der Logik. */
    private Haushaltsbuch buch;

    /** Tabellenmodell für die Transaktionsliste. */
    private DefaultTableModel tabellenModell;

    /** Eingabefeld für die Beschreibung. */
    private JTextField beschreibungField;

    /** Eingabefeld für den Betrag. */
    private JTextField betragField;

    /** Dropdown für die Kategorie. */
    private JComboBox<String> kategorieBox;

    /** Auswahl ob Einnahme oder Ausgabe. */
    private JRadioButton einnahmeButton;

    /** Label für den aktuellen Saldo. */
    private JLabel saldoLabel;

    /** Panel mit dem Balkendiagramm. */
    private DiagrammPanel diagramm;

    /**
     * Erstellt und initialisiert das Hauptfenster.
     */
    public HaushaltsbuchGUI() {
        buch = new Haushaltsbuch();
        initGUI();
    }

    /**
     * Initialisiert alle GUI-Komponenten.
     */
    private void initGUI() {
        setTitle("Haushaltsbuch");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(erstelleEingabePanel(), BorderLayout.NORTH);
        add(erstelleTabelle(), BorderLayout.CENTER);
        add(erstelleSeitenPanel(), BorderLayout.EAST);
        add(erstelleSaldoLeiste(), BorderLayout.SOUTH);
    }

    /**
     * Erstellt das Eingabeformular oben.
     * @return JPanel mit Eingabefeldern
     */
    private JPanel erstelleEingabePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Neue Transaktion"));

        beschreibungField = new JTextField(12);
        betragField = new JTextField(8);
        kategorieBox = new JComboBox<>(new String[]{
            "Lebensmittel", "Miete", "Transport", "Freizeit", "Gehalt", "Sonstiges"
        });

        einnahmeButton = new JRadioButton("Einnahme", true);
        JRadioButton ausgabeButton = new JRadioButton("Ausgabe");
        ButtonGroup gruppe = new ButtonGroup();
        gruppe.add(einnahmeButton);
        gruppe.add(ausgabeButton);

        JButton hinzufuegenBtn = new JButton("Hinzufügen");
        hinzufuegenBtn.addActionListener(e -> transakionHinzufuegen());

        panel.add(new JLabel("Beschreibung:"));
        panel.add(beschreibungField);
        panel.add(new JLabel("Betrag (€):"));
        panel.add(betragField);
        panel.add(new JLabel("Kategorie:"));
        panel.add(kategorieBox);
        panel.add(einnahmeButton);
        panel.add(ausgabeButton);
        panel.add(hinzufuegenBtn);

        return panel;
    }

    /**
     * Erstellt die Tabelle zur Anzeige der Transaktionen.
     * @return JScrollPane mit der Tabelle
     */
    private JScrollPane erstelleTabelle() {
        String[] spalten = {"Typ", "Beschreibung", "Kategorie", "Betrag (€)"};
        tabellenModell = new DefaultTableModel(spalten, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable tabelle = new JTable(tabellenModell);
        tabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelle.setRowHeight(24);

        JButton loeschenBtn = new JButton("Ausgewählte löschen");
        loeschenBtn.addActionListener(e -> {
            int zeile = tabelle.getSelectedRow();
            if (zeile >= 0) {
                buch.entfernen(zeile);
                tabellenModell.removeRow(zeile);
                aktualisieren();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tabelle), BorderLayout.CENTER);
        panel.add(loeschenBtn, BorderLayout.SOUTH);
        return new JScrollPane(tabelle);
    }

    /**
     * Erstellt das rechte Panel mit Diagramm und Saldo.
     * @return JPanel mit Diagramm
     */
    private JPanel erstelleSeitenPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        diagramm = new DiagrammPanel();
        panel.add(diagramm, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Erstellt die untere Statusleiste mit dem Saldo.
     * @return JPanel mit Saldo-Anzeige
     */
    private JPanel erstelleSaldoLeiste() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saldoLabel = new JLabel("Saldo: 0,00 €");
        saldoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(saldoLabel);
        return panel;
    }

    /**
     * Liest die Eingabefelder aus und fügt eine neue Transaktion hinzu.
     * Zeigt eine Fehlermeldung bei ungültigen Eingaben.
     */
    private void transakionHinzufuegen() {
        String beschreibung = beschreibungField.getText().trim();
        String betragText = betragField.getText().trim();
        String kategorie = (String) kategorieBox.getSelectedItem();
        boolean istEinnahme = einnahmeButton.isSelected();

        if (beschreibung.isEmpty() || betragText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte Beschreibung und Betrag eingeben.");
            return;
        }

        try {
            double betrag = Double.parseDouble(betragText.replace(",", "."));
            if (betrag <= 0) throw new NumberFormatException();

            Transaktion t = new Transaktion(beschreibung, betrag, kategorie, istEinnahme);
            buch.hinzufuegen(t);

            String typ = istEinnahme ? "Einnahme" : "Ausgabe";
            tabellenModell.addRow(new Object[]{typ, beschreibung, kategorie, String.format("%.2f", betrag)});

            beschreibungField.setText("");
            betragField.setText("");
            aktualisieren();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Bitte einen gültigen Betrag eingeben.");
        }
    }

    /**
     * Aktualisiert Saldo und Diagramm nach jeder Änderung.
     */
    private void aktualisieren() {
        double saldo = buch.getSaldo();
        saldoLabel.setText(String.format("Saldo: %.2f €", saldo));
        saldoLabel.setForeground(saldo >= 0 ? new Color(0, 128, 0) : Color.RED);
        diagramm.aktualisieren(buch.getGesamtEinnahmen(), buch.getGesamtAusgaben());
    }
}
