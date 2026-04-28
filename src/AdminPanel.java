import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminPanel extends JFrame {

    private String currentUser;

    // Komponente za Tab 1 - Pregled sesija
    private JTable tableSesije;
    private DefaultTableModel modelSesije;

    // Komponente za Tab 2 - Promena sesije
    private JComboBox<String> cbSesijaId;
    private JTextField tfDatum, tfPocetak, tfZavrsetak, tfBrojDataset;
    private JLabel lblInfoSesija;

    // Komponente za Tab 3 - Brisanje laboratorije
    private JComboBox<String> cbLaboratorija;
    private JLabel lblInfoLab;

    public AdminPanel(String username) {
        this.currentUser = username;
        setTitle("Admin panel - " + username);
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Pregled sesija", createPregledPanel());
        tabs.addTab("Izmena sesije", createIzmenaPanel());
        tabs.addTab("Brisanje laboratorije", createBrisanjePanel());

        add(tabs);

        // Ucitaj podatke pri otvaranju
        loadSesije();
        loadSesijaIds();
        loadLaboratorije();
    }

    // =================================================================
    // TAB 1: Pregled zakazanih sesija i eksperimenata
    // =================================================================
    private JPanel createPregledPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lbl = new JLabel("Zakazane sesije i eksperimenti");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(lbl, BorderLayout.NORTH);

        String[] columns = {"ID sesije", "Datum", "Pocetak", "Zavrsetak",
                "Br. dataset", "Eksperiment", "Laboratorija", "Status"};
        modelSesije = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSesije = new JTable(modelSesije);
        tableSesije.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scroll = new JScrollPane(tableSesije);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Osvezi");
        btnRefresh.addActionListener(e -> loadSesije());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnRefresh);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadSesije() {
        modelSesije.setRowCount(0);
        String sql = "SELECT s.id_sesija, s.datum_sesije, s.vreme_pocetka, s.vreme_zavrsetka, " +
                "s.broj_snim_dataset, e.naziv_eksperiment, l.naziv_laboratorije, i.status_izvodjenja " +
                "FROM sesija s " +
                "JOIN izvodjenje i ON s.id_izvodjenja = i.id_izvodjenje " +
                "JOIN eksperiment e ON i.id_eksperiment = e.id_eksperiment " +
                "JOIN laboratorija l ON i.id_laboratorija = l.id_laboratorija " +
                "ORDER BY s.datum_sesije DESC, s.vreme_pocetka";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                modelSesije.addRow(new Object[]{
                        rs.getInt("id_sesija"),
                        rs.getString("datum_sesije"),
                        rs.getString("vreme_pocetka"),
                        rs.getString("vreme_zavrsetka"),
                        rs.getInt("broj_snim_dataset"),
                        rs.getString("naziv_eksperiment"),
                        rs.getString("naziv_laboratorije"),
                        rs.getString("status_izvodjenja")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Greska pri ucitavanju sesija: " + e.getMessage(),
                    "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =================================================================
    // TAB 2: Promena podataka o zakazanoj sesiji
    // =================================================================
    private JPanel createIzmenaPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Naslov
        JLabel lbl = new JLabel("Izmena podataka o sesiji");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lbl, gbc);

        // Izbor sesije
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Izaberite sesiju (ID):"), gbc);
        cbSesijaId = new JComboBox<>();
        cbSesijaId.addActionListener(e -> loadSesijaDetails());
        gbc.gridx = 1;
        panel.add(cbSesijaId, gbc);

        // Info labela
        lblInfoSesija = new JLabel(" ");
        lblInfoSesija.setFont(new Font("SansSerif", Font.ITALIC, 12));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(lblInfoSesija, gbc);

        // Polja za izmenu
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Datum (YYYY-MM-DD):"), gbc);
        tfDatum = new JTextField(15);
        gbc.gridx = 1;
        panel.add(tfDatum, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Vreme pocetka (HH:MM:SS):"), gbc);
        tfPocetak = new JTextField(15);
        gbc.gridx = 1;
        panel.add(tfPocetak, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Vreme zavrsetka (HH:MM:SS):"), gbc);
        tfZavrsetak = new JTextField(15);
        gbc.gridx = 1;
        panel.add(tfZavrsetak, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Broj snimljenih datasetova:"), gbc);
        tfBrojDataset = new JTextField(15);
        gbc.gridx = 1;
        panel.add(tfBrojDataset, gbc);

        // Dugme za cuvanje
        JButton btnSave = new JButton("Sacuvaj izmene");
        btnSave.addActionListener(e -> updateSesija());
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        panel.add(btnSave, gbc);

        return panel;
    }

    private void loadSesijaIds() {
        cbSesijaId.removeAllItems();
        String sql = "SELECT id_sesija FROM sesija ORDER BY id_sesija";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cbSesijaId.addItem(String.valueOf(rs.getInt("id_sesija")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Greska pri ucitavanju ID-jeva sesija: " + e.getMessage(),
                    "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSesijaDetails() {
        if (cbSesijaId.getSelectedItem() == null) return;
        int id = Integer.parseInt((String) cbSesijaId.getSelectedItem());

        String sql = "SELECT s.datum_sesije, s.vreme_pocetka, s.vreme_zavrsetka, " +
                "s.broj_snim_dataset, e.naziv_eksperiment, l.naziv_laboratorije " +
                "FROM sesija s " +
                "JOIN izvodjenje i ON s.id_izvodjenja = i.id_izvodjenje " +
                "JOIN eksperiment e ON i.id_eksperiment = e.id_eksperiment " +
                "JOIN laboratorija l ON i.id_laboratorija = l.id_laboratorija " +
                "WHERE s.id_sesija = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tfDatum.setText(rs.getString("datum_sesije"));
                tfPocetak.setText(rs.getString("vreme_pocetka"));
                tfZavrsetak.setText(rs.getString("vreme_zavrsetka"));
                tfBrojDataset.setText(String.valueOf(rs.getInt("broj_snim_dataset")));
                lblInfoSesija.setText("Eksperiment: " + rs.getString("naziv_eksperiment") +
                        " | Laboratorija: " + rs.getString("naziv_laboratorije"));
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Greska pri ucitavanju detalja sesije: " + e.getMessage(),
                    "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSesija() {
        if (cbSesijaId.getSelectedItem() == null) return;
        int id = Integer.parseInt((String) cbSesijaId.getSelectedItem());

        String datum = tfDatum.getText().trim();
        String pocetak = tfPocetak.getText().trim();
        String zavrsetak = tfZavrsetak.getText().trim();
        String brojStr = tfBrojDataset.getText().trim();

        if (datum.isEmpty() || pocetak.isEmpty() || zavrsetak.isEmpty() || brojStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Sva polja moraju biti popunjena.",
                    "Greska", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE sesija SET datum_sesije = ?, vreme_pocetka = ?, " +
                "vreme_zavrsetka = ?, broj_snim_dataset = ? WHERE id_sesija = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, datum);
            ps.setString(2, pocetak);
            ps.setString(3, zavrsetak);
            ps.setInt(4, Integer.parseInt(brojStr));
            ps.setInt(5, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this,
                        "Sesija uspesno azurirana.",
                        "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                loadSesije(); // osvezi tabelu na prvom tabu
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Greska pri azuriranju: " + e.getMessage(),
                    "Greska", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Broj datasetova mora biti ceo broj.",
                    "Greska", JOptionPane.WARNING_MESSAGE);
        }
    }

    // =================================================================
    // TAB 3: Brisanje laboratorije
    // =================================================================
    private JPanel createBrisanjePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbl = new JLabel("Brisanje laboratorije");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lbl, gbc);

        JLabel lblNote = new JLabel("Dozvoljeno samo ako u laboratoriji ne radi nijedan istrazivac.");
        lblNote.setFont(new Font("SansSerif", Font.ITALIC, 12));
        gbc.gridy = 1;
        panel.add(lblNote, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Izaberite laboratoriju:"), gbc);
        cbLaboratorija = new JComboBox<>();
        gbc.gridx = 1;
        panel.add(cbLaboratorija, gbc);

        lblInfoLab = new JLabel(" ");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(lblInfoLab, gbc);

        JButton btnDelete = new JButton("Obrisi laboratoriju");
        btnDelete.setForeground(Color.RED);
        btnDelete.addActionListener(e -> deleteLaboratorija());
        gbc.gridy = 4;
        panel.add(btnDelete, gbc);

        return panel;
    }

    private void loadLaboratorije() {
        cbLaboratorija.removeAllItems();
        String sql = "SELECT id_laboratorija, naziv_laboratorije FROM laboratorija ORDER BY id_laboratorija";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cbLaboratorija.addItem(rs.getInt("id_laboratorija") + " - " +
                        rs.getString("naziv_laboratorije"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Greska pri ucitavanju laboratorija: " + e.getMessage(),
                    "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteLaboratorija() {
        if (cbLaboratorija.getSelectedItem() == null) return;

        String selected = (String) cbLaboratorija.getSelectedItem();
        int labId = Integer.parseInt(selected.split(" - ")[0].trim());
        String labName = selected.substring(selected.indexOf(" - ") + 3);

        // Provera: da li u laboratoriji radi ijedan istrazivac
        // (preko tim_izvodjaca -> izvodjenje -> laboratorija)
        String checkSql = "SELECT COUNT(*) AS cnt FROM tim_izvodjaca ti " +
                "JOIN izvodjenje i ON ti.id_izvodjenje = i.id_izvodjenje " +
                "WHERE i.id_laboratorija = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            // 1. Proveri da li ima istrazivaca
            PreparedStatement psCheck = conn.prepareStatement(checkSql);
            psCheck.setInt(1, labId);
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            int count = rs.getInt("cnt");
            rs.close();
            psCheck.close();

            if (count > 0) {
                JOptionPane.showMessageDialog(this,
                        "Laboratorija '" + labName + "' ne moze biti obrisana jer " +
                                "u njoj radi " + count + " istrazivac(a).",
                        "Brisanje nije dozvoljeno", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Potvrda od korisnika
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Da li ste sigurni da zelite da obrisete laboratoriju '" + labName + "'?",
                    "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            // 3. Brisanje sa svim zavisnostima (u transakciji)
            conn.setAutoCommit(false);
            try {
                // Obrisi sesije vezane za izvodjenja u ovoj laboratoriji
                PreparedStatement ps1 = conn.prepareStatement(
                        "DELETE FROM sesija WHERE id_izvodjenja IN " +
                                "(SELECT id_izvodjenje FROM izvodjenje WHERE id_laboratorija = ?)");
                ps1.setInt(1, labId);
                ps1.executeUpdate();
                ps1.close();

                // Obrisi izvodjenja u ovoj laboratoriji
                PreparedStatement ps2 = conn.prepareStatement(
                        "DELETE FROM izvodjenje WHERE id_laboratorija = ?");
                ps2.setInt(1, labId);
                ps2.executeUpdate();
                ps2.close();

                // Obrisi podtipove alata (hardverski/softverski) za alate u ovoj laboratoriji
                PreparedStatement ps3a = conn.prepareStatement(
                        "DELETE FROM hardverski_alat WHERE id_alat IN " +
                                "(SELECT id_alat FROM alat WHERE id_laboratorija = ?)");
                ps3a.setInt(1, labId);
                ps3a.executeUpdate();
                ps3a.close();

                PreparedStatement ps3b = conn.prepareStatement(
                        "DELETE FROM softverski_alat WHERE id_alat IN " +
                                "(SELECT id_alat FROM alat WHERE id_laboratorija = ?)");
                ps3b.setInt(1, labId);
                ps3b.executeUpdate();
                ps3b.close();

                // Obrisi eksperiment_alat veze za alate u ovoj laboratoriji
                PreparedStatement ps3c = conn.prepareStatement(
                        "DELETE FROM eksperiment_alat WHERE id_alat IN " +
                                "(SELECT id_alat FROM alat WHERE id_laboratorija = ?)");
                ps3c.setInt(1, labId);
                ps3c.executeUpdate();
                ps3c.close();

                // Obrisi alate u ovoj laboratoriji
                PreparedStatement ps4 = conn.prepareStatement(
                        "DELETE FROM alat WHERE id_laboratorija = ?");
                ps4.setInt(1, labId);
                ps4.executeUpdate();
                ps4.close();

                // Obrisi lab_resurs veze
                PreparedStatement ps5 = conn.prepareStatement(
                        "DELETE FROM lab_resurs WHERE id_laboratorija = ?");
                ps5.setInt(1, labId);
                ps5.executeUpdate();
                ps5.close();

                // Konacno, obrisi laboratoriju
                PreparedStatement ps6 = conn.prepareStatement(
                        "DELETE FROM laboratorija WHERE id_laboratorija = ?");
                ps6.setInt(1, labId);
                ps6.executeUpdate();
                ps6.close();

                conn.commit();

                JOptionPane.showMessageDialog(this,
                        "Laboratorija '" + labName + "' uspesno obrisana.",
                        "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                loadLaboratorije();
                loadSesije();
                loadSesijaIds();

            } catch (SQLException ex) {
                conn.rollback();
                JOptionPane.showMessageDialog(this,
                        "Greska pri brisanju: " + ex.getMessage(),
                        "Greska", JOptionPane.ERROR_MESSAGE);
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Greska pri konekciji: " + e.getMessage(),
                    "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
}
