package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
public class Receiver {

    private final String prop;

//    @Autowired
    public Receiver(@Value("${some.prop}") String prop) {
        this.prop = prop;
    }

    /*Guide example*/
    /*@JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        System.out.println("Received <" + email + ">");
    }*/

    /*Using JMS provider directly*/
    /*@JmsListener(destination = "RequestResponseQueue", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        System.out.println("Received <" + email + ">");
    }*/

    /*Using JNDI Destination name*/
//    @JmsListener(destination = "JMS_RequestResponseQueue", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        System.out.println("Received <" + email + ">");
    }

    /*Overloading listener*/
//    @JmsListener(destination = "RequestResponseQueue", containerFactory = "myFactory")
    public void receiveMessage(TextMessage email) {
        System.out.println("Received <" + email + ">");
    }
}
