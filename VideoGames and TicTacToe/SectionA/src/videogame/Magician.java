package videogame;


class Magician extends Entity implements SpellCaster{


  public Magician(String name, int lifePoints) {
    super(name, lifePoints);
  }

  @Override
  public int propagateDamage(int damageAmount){
    int damage = Math.min(lifePoints, damageAmount);
    lifePoints -= damage;
    return damage;
  }

  @Override
  public int minimumStrikeToDestroy(){
    return lifePoints;
  }


  @Override
  public int getStrength() {
    return lifePoints * 2;
  }
}
// TODO: complete this class as part of Section A Question 3
