/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.api;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.esprit.entities.Offre;

/**
 *
 * @author Gaaloul
 */
public class SendSMS {
    
    public static final String ACCOUNT_SID = System.getenv("AC2dfad6d935e79825c66b24df8a2d9e1a");
    public static final String AUTH_TOKEN = System.getenv("78203640648fdbaecbb6bea52981482f\n"
            + "");

    public static void sendSMS(Offre o) {
        Twilio.init("AC2dfad6d935e79825c66b24df8a2d9e1a", "78203640648fdbaecbb6bea52981482f");
        Message message = Message.creator(new PhoneNumber("+21625704316"),
                new PhoneNumber("+15076773378"), "Nouvel Offre! ce livre est soldé avec :" + o.getPourcentage_solde() + "est le nouvel prix =" + o.getPrix_soldé()).create();

        System.out.println(message.getSid());
    }
}
