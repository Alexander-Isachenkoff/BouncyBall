package bouncy.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlTransient;
import java.util.function.Consumer;

@Setter
@Getter
public class Liquid extends GameObject {

    private double stateTime;

    @XmlTransient
    private Consumer<Double> onStateTimeChanged = d -> {
    };

    public void setStateTime(double dtSeconds) {
        this.stateTime = dtSeconds;
        onStateTimeChanged.accept(this.stateTime);
    }

    @Override
    public void affectPlayer(Player player) {
        if (intersects(player)) {
            player.setDead(true);
        }
    }

}
