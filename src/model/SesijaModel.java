package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SesijaModel {


    //Vraca sve sesije sa podacima o eksperimentu i laboratoriji

    public List<Object[]> getSveSesije() {
        String query =
                "SELECT s.id_sesija, s.datum_sesije, s.vreme_pocetka, s.vreme_zavrsetka, " +
                        "       s.broj_snim_dataset, e.naziv_eksperiment, l.naziv_laboratorije, i.status_izvodjenja " +
                        "FROM sesija s " +
                        "JOIN izvodjenje i ON s.id_izvodjenja = i.id_izvodjenje " +
                        "JOIN eksperiment e ON i.id_eksperiment = e.id_eksperiment " +
                        "JOIN laboratorija l ON i.id_laboratorija = l.id_laboratorija " +
                        "ORDER BY s.datum_sesije DESC, s.vreme_pocetka ASC";

        List<Object[]> result = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getInt("id_sesija");
                row[1] = rs.getString("datum_sesije");
                row[2] = rs.getString("vreme_pocetka");
                row[3] = rs.getString("vreme_zavrsetka");
                row[4] = rs.getInt("broj_snim_dataset");
                row[5] = rs.getString("naziv_eksperiment");
                row[6] = rs.getString("naziv_laboratorije");
                row[7] = rs.getString("status_izvodjenja");
                result.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    //Vraca listu svih ID-jeva sesija iz baze

    public List<Integer> getSesijaIds() {
        String query = "SELECT id_sesija FROM sesija ORDER BY id_sesija ASC";

        List<Integer> result = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                result.add(rs.getInt("id_sesija"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

     //Za datu sesiju vraca detalje: datum, pocetak, zavrsetak,
     //broj_dataset, naziv_eksperimenta, naziv_laboratorije, vraca niz ili null ako ne postoji

    public Object[] getSesijaDetalji(int idSesije) {
        String query =
                "SELECT s.datum_sesije, s.vreme_pocetka, s.vreme_zavrsetka, " +
                        "       s.broj_snim_dataset, e.naziv_eksperiment, l.naziv_laboratorije " +
                        "FROM sesija s " +
                        "JOIN izvodjenje i ON s.id_izvodjenja = i.id_izvodjenje " +
                        "JOIN eksperiment e ON i.id_eksperiment = e.id_eksperiment " +
                        "JOIN laboratorija l ON i.id_laboratorija = l.id_laboratorija " +
                        "WHERE s.id_sesija = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idSesije);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Object[]{
                            rs.getString("datum_sesije"),
                            rs.getString("vreme_pocetka"),
                            rs.getString("vreme_zavrsetka"),
                            rs.getInt("broj_snim_dataset"),
                            rs.getString("naziv_eksperiment"),
                            rs.getString("naziv_laboratorije")
                    };
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //Azurira sesiju sa datim ID-jem
    //Vraca true ako je uspesno azurirana.

    public boolean updateSesija(int idSesije, String datum, String pocetak,
                                String zavrsetak, int brojDataset) {
        String query =
                "UPDATE sesija " +
                        "SET datum_sesije = ?, vreme_pocetka = ?, vreme_zavrsetka = ?, broj_snim_dataset = ? " +
                        "WHERE id_sesija = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, datum);
            ps.setString(2, pocetak);
            ps.setString(3, zavrsetak);
            ps.setInt(4, brojDataset);
            ps.setInt(5, idSesije);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}