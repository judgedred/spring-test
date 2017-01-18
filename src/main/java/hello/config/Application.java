package hello.config;

import com.ibm.mq.jms.MQQueue;
import hello.ComfortHall;
import hello.Hall;
import hello.Seat;
import hello.TestDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.NamingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
@EnableJms
@ComponentScan({"hello"})
@EntityScan({"hello"})
public class Application {

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    /*Using JMS Provider directly*/
    /*@Bean
    public ConnectionFactory connectionFactory() throws JMSException {
        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
        factory.setHostName("localhost");
        factory.setPort(1414);
        factory.setQueueManager("MQJMS.QManager");
        factory.setTransportType(0);
//        factory.setChannel("NON_SSL_CHANNEL");
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory();
        singleConnectionFactory.setTargetConnectionFactory(factory);
        return singleConnectionFactory;
    }*/

    /*Using JMS through JNDI*/
    @Bean
    public ConnectionFactory connectionFactory() throws JMSException, NamingException {
        JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
        factory.setJndiTemplate(jndiTemplate());
        factory.setJndiName("MQ_JMS_MANAGER");
        factory.afterPropertiesSet();
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory();
        singleConnectionFactory.setTargetConnectionFactory((ConnectionFactory)factory.getObject());
        return singleConnectionFactory;
    }

    /*Defining Destination*/
    @Bean
    public MQQueue mqQueue() throws JMSException {
        MQQueue mqQueue = new MQQueue("RequestResponseQueue");
        mqQueue.setBaseQueueManagerName("MQJMS.QManager");
        mqQueue.setBaseQueueName("RequestResponseQueue");
        return mqQueue;
    }

    @Bean
    public JndiTemplate jndiTemplate() {
        JndiTemplate jndiTemplate = new JndiTemplate();
        /*Better use profile properties*/
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "com.sun.jndi.fscontext.RefFSContextFactory");
        properties.setProperty("java.naming.provider.url", "file:/C:/JNDI-Directory");
        jndiTemplate.setEnvironment(properties);
        return jndiTemplate;
    }

    @Bean
    public JndiDestinationResolver jndiDestinationResolver() {
        JndiDestinationResolver resolver = new JndiDestinationResolver();
        resolver.setJndiTemplate(jndiTemplate());
        resolver.setCache(true);
        return resolver;
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }


    public static void main(String[] args) {
        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        /*Jms test*/
        /*
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        MQQueue mqQueue = context.getBean(MQQueue.class);
        // Send a message with a POJO - the template reuse the message converter
        System.out.println("Sending an email message.");
        *//*Guide example*//*
//        jmsTemplate.convertAndSend("mailbox", new Email("info@example.com", "Hello"));
        *//*Using defined Destination*//*
//        jmsTemplate.convertAndSend(mqQueue, new Email("info@example.com", "Hello"));
        *//*Using Provider Queue name directly*//*
//        jmsTemplate.convertAndSend("RequestResponseQueue", new Email("info@example.com", "Hello"));
        *//*Using Queue name defined in JNDI*//*
        jmsTemplate.convertAndSend("JMS_RequestResponseQueue", new Email("info@example.com", "Hello"));
        */
        /*Jms test*/

        /*Hibernate test*/
        TestDao testDao = context.getBean(TestDao.class);
        Hall hall = new Hall(1, "Большой зал");
        Seat[] seats = {new Seat(1), new Seat(2), new Seat(3), new Seat(4), new Seat(5)};
//        Seat[] seats = {new Seat(1)};
        hall.setSeats(Arrays.asList(seats));
//        hall.setHallLabels(Arrays.asList("Label1", "Label2"));
        testDao.createHall(hall);
        Hall hallPersistent = testDao.getHallById(1);
        System.out.println(hallPersistent);
        Hall comfortHall = new ComfortHall(2, "Зал повышенной комфортности", "Есть", false);
        testDao.createHall(comfortHall);
        /*Seat seat = testDao.getSeatsByHallId(1).get(1);
        System.out.println(seat);*/
        Hall hall2 = new Hall(1, "Test sequence");
        testDao.createHall(hall);
        List<Hall> halls = testDao.getHalls();
        System.out.println(halls);
        /*Hibernate test*/

    }
}
