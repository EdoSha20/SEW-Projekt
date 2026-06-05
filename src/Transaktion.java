/**
 * Repräsentiert eine einzelne Transaktion (Einnahme oder Ausgabe).
 * @author Person2
 * @version 1.0
 */
public class Transaktion {

    /** Beschreibung der Transaktion. */
    private String beschreibung;

    /** Betrag der Transaktion in Euro. */
    private double betrag;

    /** Kategorie der Transaktion (z.B. Lebensmittel, Miete). */
    private String kategorie;

    /** Art der Transaktion: true = Einnahme, false = Ausgabe. */
    private boolean istEinnahme;

    /**
     * Erstellt eine neue Transaktion.
     * @param beschreibung Kurze Beschreibung
     * @param betrag       Betrag in Euro (positiv)
     * @param kategorie    Kategorie der Transaktion
     * @param istEinnahme  true für Einnahme, false für Ausgabe
     */
    public Transaktion(String beschreibung, double betrag, String kategorie, boolean istEinnahme) {
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.kategorie = kategorie;
        this.istEinnahme = istEinnahme;
    }

    /**
     * Gibt die Beschreibung zurück.
     * @return Beschreibung der Transaktion
     */
    public String getBeschreibung() { return beschreibung; }

    /**
     * Gibt den Betrag zurück.
     * @return Betrag in Euro
     */
    public double getBetrag() { return betrag; }

    /**
     * Gibt die Kategorie zurück.
     * @return Kategorie als String
     */
    public String getKategorie() { return kategorie; }

    /**
     * Prüft ob es eine Einnahme ist.
     * @return true wenn Einnahme, false wenn Ausgabe
     */
    public boolean istEinnahme() { return istEinnahme; }

    /**
     * Gibt eine lesbare Darstellung der Transaktion zurück.
     * @return Formatierter String mit allen Infos
     */
    @Override
    public String toString() {
        String typ = istEinnahme ? "Einnahme" : "Ausgabe";
        return String.format("[%s] %s | %s | %.2f €", typ, beschreibung, kategorie, betrag);
    }
}
