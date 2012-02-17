package enemy;

import player.Player;
import player.Spell;

public class Enemy {
	private final String name;
	
	private int strength;
	private int stamina;
	private int agility;
	private int intellect;
	private int level;
	
	private int currentHealth;
	private int maxHealth;
	
	private int currentMana;
	private int maxMana;
	private int xpReward;
	private int goldReward;
	
	public Enemy(String name, int strength, int intellect,
			int stamina, int agility, int xpReward, int goldReward){
		this.name = name;
		this.strength = strength;
		this.agility = agility;
		this.stamina = stamina;
		this.intellect = intellect;
		this.level = 1;
		
		this.xpReward = xpReward;
		this.goldReward = goldReward;
		
		this.maxHealth = this.currentHealth = stamina * 10;
		this.maxMana = this.currentMana = intellect * 15;
	}
	
	public void setMaxHealth(int maxHealth){ this.maxHealth = maxHealth; }
	public void setCurrentHealth(int currentHealth){ this.currentHealth = currentHealth; }
	public void setMaxMana(int maxMana){ this.maxMana = maxMana; }
	public void setCurrentMana(int currentMana){ this.currentMana = currentMana; }
	
	public int getStrength(){ return strength; }
	public int getStamina(){ return stamina; }
	public int getAgility(){ return agility; }
	public int getIntellect(){ return intellect; }
	
	public void attack(Player p){
		int variance = ((int) (Math.random() * 10)) - 5;
		int attackDamage = (strength * 2) + (agility * 2);
		attackDamage += variance;
		p.setCurrentHealth(p.getCurrentHealth() - attackDamage);
		System.out.println(name + " hit " + p.getName() + " for "
				+ attackDamage + " damage.");
	}
	
	public void useSpell(Player p, Spell s){
		int spellDamage = s.getDamage();
		this.currentMana -= s.getManaCost();
		spellDamage += (intellect * 2);
		p.setCurrentHealth(p.getCurrentHealth() - spellDamage);
	}
	
	@Override public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.name);
		sb.append("\t");
		sb.append("Health: ");
		sb.append(this.currentHealth);
		sb.append("/");
		sb.append(this.maxHealth);
		return sb.toString();
	}

	public int getCurrentHealth() { return currentHealth; }
	public String getName(){ return name; }
	public int getGoldReward() { return goldReward; }
	public int getXpAward() { return xpReward; }
}
