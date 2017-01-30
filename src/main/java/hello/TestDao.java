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
        session = sessionFactory.getCurrentSession();
        session.save(hall);
        session.flush(); // бессмысленен при GenerationType.IDENTITY, т.к. запрос сразу выполняется
//            throw new RuntimeException();
        return hall;
    }

    /*Manual transaction management*/
    /*public Hall createHall(Hall hall) {

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(hall);
            session.flush(); // Обязательно?
            try {
//            Thread.yield();
                Thread.sleep(TimeUnit.SECONDS.toMillis(10));
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            session.getTransaction().rollback();
            session.close();
            return hall;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    public Hall getHallById(int id) {
        session = sessionFactory.getCurrentSession();
        Hall hall = (Hall) session.get(Hall.class, id);
        return hall;
        // Узнать про getCurrentSession и нужно ли ее закрывать
        // пока getCurrentSession кидает exception
        // Разобрался. При подключении TransactionManager всё работает
    }

    public List<Hall> getHalls() {
        session = sessionFactory.getCurrentSession();
        return (List<Hall>) session.createCriteria(Hall.class).list();
    }

    public List<Seat> getSeatsByHallId(int id) {
        session = sessionFactory.getCurrentSession();
        Hall hall = (Hall) session.get(Hall.class, id);
        return hall.getSeats();
    }

    public Seat createSeat(Seat seat) {
        session = sessionFactory.getCurrentSession();
        session.save(seat);
        throw new RuntimeException();
//            return seat;
    }
}
