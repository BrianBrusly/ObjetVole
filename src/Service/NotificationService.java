package Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
import java.util.Properties;

public class NotificationService {

    private static final String CONFIG_TABLE = "config";

    private static String getConfigParam(String paramName) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Items", "root", "votreMotDePasse");
             PreparedStatement pstmt = conn.prepareStatement("SELECT param_value FROM " + CONFIG_TABLE + " WHERE param_name = ?")) {
            pstmt.setString(1, paramName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("param_value");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du paramètre '" + paramName + "' : " + e.getMessage());
        }
        return null;
    }

    public boolean envoyerEmail(String destinataire, String sujet, String messageTexte) {
        String email = getConfigParam("EMAIL_NOTIFICATION");
        String password = getConfigParam("PASSWORD_NOTIFICATION");

        if (email == null || password == null) {
            System.err.println("Erreur : Informations de connexion SMTP introuvables.");
            return false;
        }

        if (!validerEmail(destinataire)) {
            System.err.println("Erreur : Adresse email invalide.");
            return false;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(messageTexte);

            Transport.send(message);
            System.out.println("Email envoyé avec succès à " + destinataire);
            return true;
        } catch (MessagingException e) {
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
            return false;
        }
    }

    private boolean validerEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    }
}
