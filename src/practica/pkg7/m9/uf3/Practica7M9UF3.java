package practica.pkg7.m9.uf3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Practica7M9UF3 {

    public static void main(String[] args) {

        try {
            String server = "ftp.uv.es";
            String server2 = "ftp.m06uf3.esy.es";
            int port = 21;

            String user = "anonymous";
            String pass = "anonymous";

            String user2 = "u454499351.user";
            String pass2 = "user1234";

            String ruta = "prova_NOM.txt"; //Modificar NOM amb el vostre nom

            //PRIMERA PROVA
            ClientFTP client = new ClientFTP(server, port, user, pass);

            for (String arg : client.listar()) {
                System.out.println(arg);
            }

            client.cerrarSesion();
            client.desconectarServidor();

            //SEGONA PROVA
            /*			ClientFTP client2 = new ClientFTP(server2, port, user2, pass2);
            
            client2.setDirectorio("pub");
            
            for (String arg : client2.listar()) {
                System.out.println(arg);
            }
            
            client2.activarEnvio();
            
            System.out.println("Pujant fitxer " + ruta);
            client2.enviarFichero(ruta);
            
            for (String arg : client2.listar()) {
                System.out.println(arg);
            }
            
            client2.cerrarSesion();            
            client2.desconectarServidor();
             */
        } catch (IOException ex) {
            Logger.getLogger(M09uf3FTP.class.getName()).log(Level.SEVERE, null, ex);
        }

    
}