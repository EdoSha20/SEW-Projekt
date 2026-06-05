import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle Transaktionen und berechnet Salden.
 * @author Person2
 * @version 1.0
 */
public class Haushaltsbuch {

    /** Liste aller gespeicherten Transaktionen. */
    private List<Transaktion> transaktionen;

    /**
     * Erstellt ein neues, leeres Haushaltsbuch.
     */
    public Haushaltsbuch() {
        transaktionen = new ArrayList<>();
    }

    /**
     * Fügt eine neue Transaktion hinzu.
     * @param t Die hinzuzufügende Transaktion
     */
    public void hinzufuegen(Transaktion t) {
        transaktionen.add(t);
    }

    /**
     * Entfernt eine Transaktion anhand des Index.
     * @param index Index der zu entfernenden Transaktion
     */
    public void entfernen(int index) {
        if (index >= 0 && index < transaktionen.size()) {
            transaktionen.remove(index);
        }
    }

    /**
     * Gibt alle Transaktionen zurück.
     * @return Liste aller Transaktionen
     */
    public List<Transaktion> getTransaktionen() {
        return transaktionen;
    }

    /**
     * Berechnet die Gesamtsumme der Einnahmen.
     * @return Summe aller Einnahmen in Euro
     */
    public double getGesamtEinnahmen() {
        return transaktionen.stream()
                .filter(Transaktion::istEinnahme)
                .mapToDouble(Transaktion::getBetrag)
                .sum();
    }

    /**
     * Berechnet die Gesamtsumme der Ausgaben.
     * @return Summe aller Ausgaben in Euro
     */
    public double getGesamtAusgaben() {
        return transaktionen.stream()
                .filter(t -> !t.istEinnahme())
                .mapToDouble(Transaktion::getBetrag)
                .sum();
    }

    /**
     * Berechnet den aktuellen Kontostand (Einnahmen minus Ausgaben).
     * @return Saldo in Euro
     */
    public double getSaldo() {
        return getGesamtEinnahmen() - getGesamtAusgaben();
    }
}
