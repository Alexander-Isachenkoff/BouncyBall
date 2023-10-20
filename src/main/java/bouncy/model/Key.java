package bouncy.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

public class Key extends GameObject {

    @Getter
    @Setter
    @XmlAttribute
    private LockType lockType;

    @Override
    public void affectPlayer(Player player) {
        if (this.intersects(player)) {
            LevelData levelData = getLevelData();
            levelData.remove(this);
            levelData.getObjects(Lock.class).stream()
                    .filter(lock -> lock.getLockType() == lockType)
                    .forEach(levelData::remove);
        }
    }

}
