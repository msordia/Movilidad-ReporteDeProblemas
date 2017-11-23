package itesm.mx.movilidad_reportedeproblemas.Services;

import android.os.StrictMode;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by armando on 17/11/17.
 * Esta clase manda correos.
 */

public class EmailSender {

    String correo;
    String contraseña;
    Session session;


    public void sendEmail()
    {
        correo="reportes.movilidad.tec@gmail.com";
        contraseña="reportes";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");

        try{
            session=Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo,contraseña);
                }
            });

            if(session!=null)
            {
                javax.mail.Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correo));
                message.setSubject("Se genero un nuevo reporte");
                message.setRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse("reportes.movilidad.tec@gmail.com"));
                message.setContent("Tu reporte se ha generado con éxito","text/html; charset=utf-8");
                Transport.send(message);

            }


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }





}
