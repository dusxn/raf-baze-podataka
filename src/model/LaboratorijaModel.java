package model;

import java.util.List;

public class LaboratorijaModel {

    /**
     * Vraca listu svih laboratorija kao parove {id, naziv}.
     * Sortirano po id_laboratorija.
     *
     * Koristiti Statement.
     */
    public List<String[]> getSveLaboratorije() {
        // TODO: implementirati SQL upit
        return List.of();
    }

    /**
     * Proverava da li u laboratoriji radi ijedan istrazivac.
     * Provera se vrsi preko tabela: tim_izvodjaca JOIN izvodjenje
     * gde je izvodjenje.id_laboratorija = labId.
     *
     * Vraca broj istrazivaca u toj laboratoriji.
     *
     * Koristiti PreparedStatement.
     */
    public int brojIstrazivacaULab(int labId) {
        // TODO: implementirati SQL upit
        return -1;
    }

    /**
     * Brise laboratoriju i sve zavisne podatke u transakciji.
     * Redosled brisanja:
     *   1. sesije (preko izvodjenja u ovoj laboratoriji)
     *   2. tim_izvodjaca (preko izvodjenja)
     *   3. izvodjenja
     *   4. eksperiment_alat (za alate u ovoj laboratoriji)
     *   5. hardverski_alat i softverski_alat (podtipovi)
     *   6. alati
     *   7. lab_resurs
     *   8. laboratorija
     *
     * Vraca true ako je brisanje uspesno.
     * Ako dodje do greske, rollback.
     *
     * Koristiti PreparedStatement i transakciju (setAutoCommit(false)).
     */
    public boolean deleteLaboratorija(int labId) {
        // TODO: implementirati SQL upit sa transakcijom
        return false;
    }
}
