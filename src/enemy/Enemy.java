package enemy;

import player.Player;
import player.Spell;

/** 
 * Enemy
 * @author Kenny
 * 
 * Generic Enemy class that the player character will fight throughout the game
 */
public class Enemy {
	private final String name;
	
	//Enemy stats
	private int strength;
	private int stamina;
	private int agility;
	private int intellect;
	private int level;
	
	private int currentHealth;
	private int maxHealth;
	
	private int currentMana;
	private int maxMana;
	
	//Player rewards upon victory
	private int xpReward;
	private int goldReward;
	
	/*
	 * Constructor. Initializes the enemy based on the input. The input of the
	 * contructor is the stats of the enemy as well as a name, and the rewards.
	 */
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
	
	//setters - used to change the health and mana of the enemy
	public void setMaxHealth(int maxHealth){ this.maxHealth = maxHealth; }
	public void setCurrentHealth(int currentHealth){ this.currentHealth = currentHealth; }
	public void setMaxMana(int maxMana){ this.maxMana = maxMana; }
	public void setCurrentMana(int currentMana){ this.currentMana = currentMana; }
	
	//getters - returns the enemy stats
	public int getStrength(){ return strength; }
	public int getStamina(){ return stamina; }
	public int getAgility(){ return agility; }
	public int getIntellect(){ return intellect; }
	
	
	/*
	 * Basic melee attack of the enemy. Generates a random number to introduce
	 * some randomness in the damage done.
	 */
	public void attack(Player p){
		int variance = ((int) (Math.random() * 10)) - 5;
		int attackDamage = (strength * 2) + (agility * 2);
		attackDamage += variance;
		p.setCurrentHealth(p.getCurrentHealth() - attackDamage);
		System.out.println(name + " hit " + p.getName() + " for "
				+ attackDamage + " damage.");
	}
	
	/*
	 * Spell casting abilities are implements via this method
	 */
	public void useSpell(Player p, Spell s){
		int spellDamage = s.getDamage();
		this.currentMana -= s.getManaCost();
		spellDamage += (intellect * 2);
		p.setCurrentHealth(p.getCurrentHealth() - spellDamage);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * String representation of an enemy
	 */
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
	
	//getters
	public int getCurrentHealth() { return currentHealth; }
	public String getName(){ return name; }
	public int getGoldReward() { return goldReward; }
	public int getXpAward() { return xpReward; }
}
