package model;

import java.util.List;

public class SesijaModel {

    /**
     * Vraca sve sesije sa podacima o eksperimentu i laboratoriji.
     * Svaki red je niz: {id_sesija, datum, pocetak, zavrsetak,
     *                    broj_dataset, naziv_eksperimenta, naziv_laboratorije, status}
     *
     * SQL upit treba da radi JOIN:
     *   sesija -> izvodjenje -> eksperiment
     *   sesija -> izvodjenje -> laboratorija
     * i sortira po datum_sesije DESC, vreme_pocetka ASC.
     *
     * Koristiti Statement (nema parametara).
     */
    public List<Object[]> getSveSesije() {
        // TODO: implementirati SQL upit
        return List.of();
    }

    /**
     * Vraca listu svih ID-jeva sesija iz baze, sortirano rastuci.
     * Koristiti Statement.
     */
    public List<Integer> getSesijaIds() {
        // TODO: implementirati SQL upit
        return List.of();
    }

    /**
     * Za datu sesiju vraca detalje: datum, pocetak, zavrsetak,
     * broj_dataset, naziv_eksperimenta, naziv_laboratorije.
     * Vraca niz ili null ako ne postoji.
     *
     * Koristiti PreparedStatement (parametar: id_sesija).
     */
    public Object[] getSesijaDetalji(int idSesije) {
        // TODO: implementirati SQL upit
        return null;
    }

    /**
     * Azurira sesiju sa datim ID-jem.
     * Vraca true ako je uspesno azurirana.
     *
     * Koristiti PreparedStatement.
     */
    public boolean updateSesija(int idSesije, String datum, String pocetak,
                                String zavrsetak, int brojDataset) {
        // TODO: implementirati SQL upit
        return false;
    }
}
