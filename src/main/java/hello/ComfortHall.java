package hello;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ComfortHall extends Hall {

    @Column
    private String vipZone;

    @Column
    private boolean hasBar;

    public ComfortHall() {
    }

    public ComfortHall(int number, String name, String vipZone, boolean hasBar) {
        super(number, name);
        this.vipZone = vipZone;
        this.hasBar = hasBar;
    }

    public String getVipZone() {
        return vipZone;
    }

    public void setVipZone(String vipZone) {
        this.vipZone = vipZone;
    }

    public boolean isHasBar() {
        return hasBar;
    }

    public void setHasBar(boolean hasBar) {
        this.hasBar = hasBar;
    }
}
