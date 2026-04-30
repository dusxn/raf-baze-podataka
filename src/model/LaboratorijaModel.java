package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaboratorijaModel {

    // Vraca listu svih laboratorija kao parove {id, naziv}

    public List<String[]> getSveLaboratorije() {
        String query =
                "SELECT id_laboratorija, naziv_laboratorije " +
                        "FROM laboratorija " +
                        "ORDER BY id_laboratorija ASC";

        List<String[]> result = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                result.add(new String[]{
                        String.valueOf(rs.getInt("id_laboratorija")),
                        rs.getString("naziv_laboratorije")
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

     //Proverava da li u laboratoriji radi ijedan istrazivac

    public int brojIstrazivacaULab(int labId) {
        String query =
                "SELECT COUNT(DISTINCT ti.id_istrazivac) AS broj " +
                        "FROM tim_izvodjaca ti " +
                        "JOIN izvodjenje i ON ti.id_izvodjenje = i.id_izvodjenje " +
                        "WHERE i.id_laboratorija = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, labId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("broj");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    //Brise laboratoriju i sve zavisne podatke u transakciji
    //Vraca true ako je brisanje uspesno
    //Ako dodje do greske, rollback

    public boolean deleteLaboratorija(int labId) {
        String deleteSesije =
                "DELETE FROM sesija WHERE id_izvodjenja IN " +
                        "(SELECT id_izvodjenje FROM izvodjenje WHERE id_laboratorija = ?)";

        String deleteTimIzvodjaca =
                "DELETE FROM tim_izvodjaca WHERE id_izvodjenje IN " +
                        "(SELECT id_izvodjenje FROM izvodjenje WHERE id_laboratorija = ?)";

        String deleteIzvodjenja =
                "DELETE FROM izvodjenje WHERE id_laboratorija = ?";

        String deleteEksperimentAlat =
                "DELETE FROM eksperiment_alat WHERE id_alat IN " +
                        "(SELECT id_alat FROM alat WHERE id_laboratorija = ?)";

        String deleteHardverskiAlat =
                "DELETE FROM hardverski_alat WHERE id_alat IN " +
                        "(SELECT id_alat FROM alat WHERE id_laboratorija = ?)";

        String deleteSoftVerskiAlat =
                "DELETE FROM softverski_alat WHERE id_alat IN " +
                        "(SELECT id_alat FROM alat WHERE id_laboratorija = ?)";

        String deleteAlati =
                "DELETE FROM alat WHERE id_laboratorija = ?";

        String deleteLabResurs =
                "DELETE FROM lab_resurs WHERE id_laboratorija = ?";

        String deleteLaboratorija =
                "DELETE FROM laboratorija WHERE id_laboratorija = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(deleteSesije)) {
                    ps.setInt(1, labId); ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(deleteTimIzvodjaca)) {
                    ps.setInt(1, labId); ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(deleteIzvodjenja)) {
                    ps.setInt(1, labId); ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(deleteEksperimentAlat)) {
                    ps.setInt(1, labId); ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(deleteHardverskiAlat)) {
                    ps.setInt(1, labId); ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(deleteSoftVerskiAlat)) {
                    ps.setInt(1, labId); ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(deleteAlati)) {
                    ps.setInt(1, labId); ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(deleteLabResurs)) {
                    ps.setInt(1, labId); ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(deleteLaboratorija)) {
                    ps.setInt(1, labId); ps.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}