import java.io.*;

public class UserFileManager {

    private static final String FILE_PATH = "korisnici.txt";

    // Registracija novog korisnika - dodaje red u fajl
    public static boolean register(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        // Proveri da li korisnicko ime vec postoji
        if (userExists(username)) {
            return false;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + ";" + password);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Prijava - proverava da li postoji kombinacija username/password
    public static boolean login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            // Fajl jos ne postoji - nema korisnika
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Proverava da li korisnicko ime vec postoji
    public static boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
