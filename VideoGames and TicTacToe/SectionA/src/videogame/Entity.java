package videogame;

public abstract class Entity {
	
	protected String name;
	protected int lifePoints = 0;

	public Entity(String name, int lifePoints) {
		assert(lifePoints >= 0);
		this.name = name;
		this.lifePoints = lifePoints;
	}

	public final boolean isAlive() {
		/* TODO: Implement as part of Section A Question 2 */
		return lifePoints > 0;
	}
	
	public final int applySpell(SpellCaster spellCaster) {
		return propagateDamage(spellCaster.getStrength());
	}
	
	protected abstract int propagateDamage(int damageAmount);

	public abstract int minimumStrikeToDestroy();

	@Override
	public String toString(){
		return name + "(" + lifePoints + ")";
	}
	
}
