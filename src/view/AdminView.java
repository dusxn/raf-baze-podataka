package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame {

    // --- Tab 1: Pregled sesija ---
    private JTable tableSesije;
    private DefaultTableModel modelSesije;
    private JButton btnRefresh;

    // --- Tab 2: Izmena sesije ---
    private JComboBox<String> cbSesijaId;
    private JTextField tfDatum, tfPocetak, tfZavrsetak, tfBrojDataset;
    private JLabel lblInfoSesija;
    private JButton btnSave;

    // --- Tab 3: Brisanje laboratorije ---
    private JComboBox<String> cbLaboratorija;
    private JButton btnDelete;

    public AdminView(String username) {
        setTitle("Admin panel - " + username);
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Pregled sesija", createPregledPanel());
        tabs.addTab("Izmena sesije", createIzmenaPanel());
        tabs.addTab("Brisanje laboratorije", createBrisanjePanel());

        add(tabs);
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

        btnRefresh = new JButton("Osvezi");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnRefresh);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
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

        JLabel lbl = new JLabel("Izmena podataka o sesiji");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lbl, gbc);

        // Izbor sesije
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Izaberite sesiju (ID):"), gbc);
        cbSesijaId = new JComboBox<>();
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

        btnSave = new JButton("Sacuvaj izmene");
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        panel.add(btnSave, gbc);

        return panel;
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

        btnDelete = new JButton("Obrisi laboratoriju");
        btnDelete.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnDelete, gbc);

        return panel;
    }

    // =================================================================
    // GETTERI ZA CONTROLLER
    // =================================================================

    // --- Tab 1 ---
    public DefaultTableModel getTableModel() { return modelSesije; }

    // --- Tab 2 ---
    public String getSelectedSesijaId() {
        return (String) cbSesijaId.getSelectedItem();
    }

    public String getDatum()       { return tfDatum.getText().trim(); }
    public String getPocetak()     { return tfPocetak.getText().trim(); }
    public String getZavrsetak()   { return tfZavrsetak.getText().trim(); }
    public String getBrojDataset() { return tfBrojDataset.getText().trim(); }

    public void setDatum(String val)       { tfDatum.setText(val); }
    public void setPocetak(String val)     { tfPocetak.setText(val); }
    public void setZavrsetak(String val)   { tfZavrsetak.setText(val); }
    public void setBrojDataset(String val) { tfBrojDataset.setText(val); }
    public void setInfoSesija(String text) { lblInfoSesija.setText(text); }

    public void clearSesijaIds() { cbSesijaId.removeAllItems(); }
    public void addSesijaId(String id) { cbSesijaId.addItem(id); }

    // --- Tab 3 ---
    public String getSelectedLaboratorija() {
        return (String) cbLaboratorija.getSelectedItem();
    }

    public void clearLaboratorije() { cbLaboratorija.removeAllItems(); }
    public void addLaboratorija(String lab) { cbLaboratorija.addItem(lab); }

    // =================================================================
    // LISTENERI KOJE CONTROLLER POSTAVLJA
    // =================================================================

    public void addRefreshListener(ActionListener listener) {
        btnRefresh.addActionListener(listener);
    }

    public void addSesijaSelectListener(ActionListener listener) {
        cbSesijaId.addActionListener(listener);
    }

    public void addSaveListener(ActionListener listener) {
        btnSave.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    // =================================================================
    // PRIKAZ PORUKA
    // =================================================================

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Greska", JOptionPane.ERROR_MESSAGE);
    }

    public void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Upozorenje", JOptionPane.WARNING_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Uspeh", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean confirmDelete(String labName) {
        int result = JOptionPane.showConfirmDialog(this,
                "Da li ste sigurni da zelite da obrisete laboratoriju '" + labName + "'?",
                "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
