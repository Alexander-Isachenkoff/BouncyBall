package bouncy.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Star extends MovingGameObject {

    @Override
    public void affectPlayer(Player player) {
        if (intersects(player)) {
            getLevelData().remove(this);
        }
    }

}
