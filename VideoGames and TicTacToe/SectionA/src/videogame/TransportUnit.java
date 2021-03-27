package videogame;

import java.util.ArrayList;
import java.util.List;

class TransportUnit extends Entity {

  private final List<Entity> contains = new ArrayList<>();

  public TransportUnit(String name, int lifePoints) {
    super(name, lifePoints);
  }

  public void add(Entity entity) {
    contains.add(entity);
  }

  @Override
  protected int propagateDamage(int damageAmount) {
    int damage = Math.min(lifePoints, damageAmount);
    lifePoints -= damage;

    for (Entity contain : contains) {
      damage += contain.propagateDamage(damageAmount / 2);
    }

    return damage;
  }

  @Override
  public int minimumStrikeToDestroy() {

    // must use 2 * points as spell damage is halved when going to container elements
    return Math.max(lifePoints,contains.stream().map(child -> child.minimumStrikeToDestroy() * 2).reduce(
        Integer::sum).orElse(0));
  }

  @Override
  public String toString() {
    String result = super.toString() + " transporting: [" + contains.get(0);
    for (int index = 1; index < contains.size(); index++) {
      result += "," + contains.get(index).toString();
    }
    return result + "]";
  }
}

// TODO: complete this class as part of Section A Question 4
