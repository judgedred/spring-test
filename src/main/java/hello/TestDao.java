package hello;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {

    private final SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public TestDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Hall createHall(Hall hall) {

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(hall);
            session.flush(); // Обязательно?
            session.getTransaction().commit();
            return hall;
        } catch(Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            if(session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /*public Seat createSeat(Seat seat) {

    }*/
}
