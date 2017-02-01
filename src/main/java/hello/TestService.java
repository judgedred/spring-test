package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
//@Transactional(propagation = Propagation.REQUIRED)
public class TestService {

    @Autowired
    TestDao testDao;

    @Autowired
    TestService testService;

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional
    public Hall createHall(Hall hall) {
        return testDao.createHall(hall);
    }

    @Transactional
    public Hall getHallById(int id) {
        return testDao.getHallById(id);
    }

    @Transactional
    public Seat createSeat(Seat seat) {
        return testDao.createSeat(seat);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Hall> getHalls() {
        try {
//            Thread.yield();
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return testDao.getHalls();
    }

    @Transactional
    public void singleTransaction () {
        Hall hall = new Hall(1, "Большой зал");
//        createHall(hall);
        testService.createHall(hall);
        /*try {
//            Thread.yield();
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch(InterruptedException e) {
            e.printStackTrace();
        }*/
        Seat seat = new Seat(5);
        createSeat(seat);
    }

    @Transactional
    public Hall attach(Hall hall) {
        hall = testDao.attach(hall);
        List<Seat> seats = hall.getSeats();
        Seat seat = seats.get(0);
        return hall;
    }
}

