package itesm.mx.movilidad_reportedeproblemas.Services;

import android.os.StrictMode;
import android.util.Log;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//////////////////////////////////////////////////////////
//Clase: EmailSender
// Descripción: Esta clase manda correos al generarse un nuevo reporte o cambiar el estatus
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

public class EmailSender {

    private static final String EMAIL = "reportes.movilidad.tec@gmail.com";
    private static final String PASSWORD = "reportes";


    public void sendEmail(String messageString, String... to)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");

        try{
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL, PASSWORD);
                }
            });

            if(session != null)
            {
                javax.mail.Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(EMAIL));
                message.setSubject("Se genero un nuevo reporte");
                for (String recipient : to) {
                    message.addRecipient(javax.mail.Message.RecipientType.TO,InternetAddress.parse(recipient)[0]);
                }
                message.addRecipient(javax.mail.Message.RecipientType.TO,InternetAddress.parse(EMAIL)[0]);
                message.setContent(messageString,"text/html; charset=utf-8");
                Transport.send(message);
                Log.d("EmailSender", "Mail sent to: " + message.getAllRecipients().toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }





}
