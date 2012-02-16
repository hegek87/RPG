package player;

public class ConcreteSpell implements Spell {
	private int manaCost;
	private int spellDmg;
	private boolean heal;
	
	private String spellName;
	
	public ConcreteSpell(String spellName,int manaCost,
			int spellDmg, boolean heal){
		this.manaCost = manaCost;
		this.spellDmg = spellDmg;
		this.heal = heal;
		this.spellName = spellName;
	}

	@Override public int getManaCost() { return manaCost; }
	@Override public int getDamage() { return spellDmg; }
	@Override public boolean isHeal(){ return heal; }

	@Override public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(spellName);
		sb.append("\t");
		sb.append(manaCost);
		return sb.toString();
	}
}
