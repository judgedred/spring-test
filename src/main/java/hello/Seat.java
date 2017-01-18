package hello;

import javax.persistence.*;

@Entity
public class Seat {

    @Id
    @TableGenerator(name = "seat", allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seat")
    private int id;

    @Column
    private int number;

    /*@ManyToOne
    private Hall hall;*/

    public Seat() {
    }

    public Seat(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /*public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }*/
}
