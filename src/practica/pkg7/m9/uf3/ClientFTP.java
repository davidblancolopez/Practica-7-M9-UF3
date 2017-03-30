package practica.pkg7.m9.uf3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class ClientFTP {

    private String server;
    private int port;
    private String user;
    private String pass;
    private FTPClient ftp;
    private boolean login;

    public ClientFTP(String server, int port, String user, String pass) throws IOException {
        this.server = server;
        this.port = port;
        this.user = user;
        this.pass = pass;

        ftp = new FTPClient();

        conectar();

    }

    private void conectar() throws IOException {
        //realitzar conexió amb connect(), ip i port
        ftp.connect(server, port);

        //realitzar login amb user i password.
        this.login = ftp.login(user, pass);

        //Comprobació de connexió amb valor de retorn de login
        if (login) {
            System.out.println("Connexió realitzada correctament!");
        } else {
            System.out.println("No s'ha pogut conectar... revisa la configuració!");
        }

        //Comprobació de valor de connexió amb els metode getReplyCode() i isPositiveCompletion(reply)
        if (ftp.isConnected()) {
            System.out.println(ftp.getReplyCode());
        }

    }

    //cambiar a directori rebut per parametre amb changeWorkingDirectory()
    public void setDirectorio(String directorio) throws IOException {
        ftp.changeWorkingDirectory(directorio);
    }

    //obtenir el llistat de fitxers i directoris amb listFiles() i printWorkingDirectory()
    public List<String> listar() throws IOException {
        List<String> lista = new ArrayList<>(); // creamos una array List
        if (this.login) { // si es true
            FTPFile[] lista2 = ftp.listFiles();

            for (FTPFile i : lista2) {
                lista.add(i.getName());
            }

        } else {
            System.out.println("No logeat...");
        }
        return lista;
    }

    //activar enviamente en mode binari amb setFileType()
    public void activarEnvio() throws IOException {
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
    }

    //pujar fitxer al servidor. BufferedInputStream, FileInputStream, enterLocalPassiveMode() i storeFile()
    public void enviarFichero(String ruta) throws FileNotFoundException, IOException {
        ftp.enterLocalPassiveMode();
        
        FileInputStream fis = new FileInputStream(ruta);
        BufferedInputStream bis = new BufferedInputStream(fis);
        
        ftp.storeFile(ruta, bis);
    }

    //tancar la sessió
    public void cerrarSesion() throws IOException {
        ftp.logout();
    }

    //desconectar del servidor
    public void desconectarServidor() throws IOException {
        ftp.disconnect();
    }

    public void descargarFichero(String fichero) throws IOException {
        try {
            this.setDirectorio("/priv");

            for (FTPFile file : ftp.listFiles()) {
                if (file.getName().equals(fichero)) {
                    ftp.enterLocalPassiveMode();

                    File f = new File(file.getName());
                    FileOutputStream fos = new FileOutputStream(f);
                    OutputStream bos = new BufferedOutputStream(fos);

                    ftp.retrieveFile(fichero, bos);
                    bos.close();
                    System.out.println("Descarga fichero: " + fichero);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
