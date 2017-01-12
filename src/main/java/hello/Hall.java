package hello;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Hall {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private int number;

    @Column
    private String name;

    @OneToMany
    private Set<Seat> seats;

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

}
