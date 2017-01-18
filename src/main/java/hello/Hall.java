package hello;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Inheritance(strategy=InheritanceType.JOINED)
public class Hall {

    @Id
    @TableGenerator(name = "hall", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hall")
//    @GeneratedValue(strategy = GenerationType.TABLE) // works with InheritanceType.TABLE_PER_CLASS
    private int id;

    @Column
    private int number;

    @Column
    private String name;

    /*@ElementCollection
    List<String> hallLabels = new ArrayList<>();*/

    @ElementCollection
    List<Label> hallLabels = new ArrayList<>();

    /*By specifying the "cascade" option you tell hibernate to save them to the database when saving their parent.*/
    @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> seats;

    /*@NaturalId
    private String ssn;*/

    public Hall() {
    }

    public Hall(int number, String name) {
        this.number = number;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Label> getHallLabels() {
        return hallLabels;
    }

    public void setHallLabels(List<Label> hallLabels) {
        this.hallLabels = hallLabels;
    }
}
