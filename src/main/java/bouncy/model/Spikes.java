package bouncy.model;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "spikes")
@NoArgsConstructor
public class Spikes extends GameObject {

    @Override
    public void affectPlayer(Player player) {
        if (intersects(player)) {
            player.setDead(true);
        }
    }

}
