/*
 * Copyright 2016 notrevequadrosc@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package modelo.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/** 
 * Classe Email para envio de emails via SMTP do gmail ou SMTP alternativo
 * @author notrevequadrosc@gmail.com
 */
public class EmailUtil {
    
    public static class Email{
       
        private static final String SMTPSERVER = "smtp.gmail.com";
        private static final String SMTPSERVERPORT = "587"; //alterado de 465
        private static final String LOGIN = "EvoeventMail@gmail.com";
        private static final String PASSWORD = "*EvoeventMail2016*";
        
            /**
         * Método principal que realiza toda operação de envio do email, realizando
         * a conexão entre do host e a autenticação da conta de email (Gmail) 
         * utilizada para efetuar o envio, em alguns casos pode ser necessário 
         * desativar o antivírus para completar a operação.
         * @param to Endereço de email utilizado como destinatário do email
         * @param subject Texto do assunto do email
         * @param message Texto da mensagem do email
         * @return response Confirmação se o email foi enviado sem erros
         */
        public static String enviaEmail(String to, String subject, String message) {

            StringBuilder response = new StringBuilder();

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp"); 
            props.put("mail.smtp.starttls.enable","true"); 
            props.put("mail.smtp.host", SMTPSERVER); 
            props.put("mail.smtp.auth", "true"); 
            props.put("mail.smtp.user", LOGIN); 
            props.put("mail.debug", "true");
            props.put("mail.smtp.port", SMTPSERVERPORT); 
            props.put("mail.smtp.socketFactory.port", SMTPSERVERPORT); 
            props.put("mail.smtp.socketFactory.class", 
                      "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
            SimpleAuth auth;
            auth = new SimpleAuth (LOGIN,PASSWORD);
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            Message msg = new MimeMessage(session);

            try {

                msg.setRecipient(Message.RecipientType.TO, 
                        new InternetAddress(to));
                msg.setFrom(new InternetAddress(LOGIN));
                msg.setSubject(subject);
                msg.setContent(message,"text/plain");

            } 
            catch (Exception e){

                response.append("Erro: Completar Mensagem\n")
                .append(e.getMessage())
                .append(System.getProperty("line.separator"));

            }

            Transport tr;

            try {

                tr = session.getTransport("smtp");
                tr.connect(SMTPSERVER, LOGIN, PASSWORD);
                msg.saveChanges();
                tr.sendMessage(msg, msg.getAllRecipients());
                tr.close();

            } 
            catch (Exception e) {

                response.append("Erro: Envio Mensagem\n")
                .append(e.getMessage())
                .append(System.getProperty("line.separator"));

                if(e.getMessage().contains("Could not connect to SMTP host")){
                    response.append("\n(VERIFIQUE O ANTIVÍRUS)");
                }

            }

            return response.toString();

        }

        /** 
        * Classe estática interna que realiza a autenticação no smtp e envia 
        * para um objeto.
        * @author notrevequadrosc@gmail.com
        */
        static class SimpleAuth extends Authenticator {

            public String username = null;
            public String password = null;

            public SimpleAuth(String user, String pwd) {

                username = user;
                password = pwd;

            }

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication (username,password);
            }

        }
        
    }
 
}
