package hello;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            throw new RuntimeException(e);
        } finally {
            if(session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Hall getHallById(int id) {
        try {
//            session = sessionFactory.getCurrentSession();
            session = sessionFactory.openSession();
            Hall hall = (Hall)session.get(Hall.class, id);
            return hall;
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(session != null && session.isOpen()) {
                session.close();
            }
        }
        // Узнать про getCurrentSession и нужно ли ее закрывать
        // пока getCurrentSession кидает exception
    }

    public List<Hall> getHalls() {
        try {
            session = sessionFactory.openSession();
            return (List<Hall>) session.createCriteria(Hall.class).list();
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Seat> getSeatsByHallId(int id){
            try {
                session = sessionFactory.openSession();
                Hall hall = (Hall) session.get(Hall.class, id);
                return hall.getSeats();
            } catch(Exception e) {
                throw new RuntimeException(e);
            } finally {
                if(session != null && session.isOpen()) {
                    session.close();
                }

            }
        }

    /*public Seat createSeat(Seat seat) {

    }*/
}
