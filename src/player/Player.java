package player;

import java.util.ArrayList;
import java.util.List;

import enemy.Enemy;

public class Player{
	private String name;
	
	private int strength;
	private int agility;
	private int stamina;
	private int intellect;
	
	private int currentMana;
	private int currentHealth;
	
	private int maxHealth;
	private int maxMana;
	
	private int level;
	private int currentXP;
	private int xpToLevel;
	
	private int gold;
	
	private PlayerClass playerClass;
	private final String className;
	
	private List<Spell> spellList;
	
	private Inventory inventory;
	
	public static final int HEALTH_PER_STAMINA = 10;
	public static final int MANA_PER_INT = 15;
	
	public void setCurrentHealth(int currentHealth){ this.currentHealth = currentHealth; }
	public int getCurrentHealth(){ return currentHealth; }
	public int getCurrentMana(){ return currentMana; }
	public List<Spell> getSpells(){ return spellList; }
	public String getName(){ return name; }
	public Inventory getInventory(){ return inventory; }
	
	private static enum PlayerClass{
		MAGE("Mage", 		12, 8, 14, 16, 7, 1, 5, 2,
				new ConcreteSpell("Fireball", 100, 10, false)),
		WARRIOR("Warrior", 	18, 8, 14, 0, 1, 6, 4, 4, 
				(new ConcreteSpell("Smash", 100, 2, false))),
		ROGUE("Rogue", 		10, 16, 14, 10, 2, 2, 4, 7, 
				new ConcreteSpell("Assault", 110, 4, false)),
		MEDIC("Medic", 		8, 8, 16, 18, 7, 1, 6, 1,
				new ConcreteSpell("Heal", 80, 15, true));
		
		private String className;
		
		private int strength; 
		private int agility;
		private int stamina;
		private int intellect;
		
		private int bonusStrength;
		private int bonusAgility;
		private int bonusStamina;
		private int bonusIntellect;
		
		private List<Spell> classSpells;
		
		private PlayerClass(String name, int strength, int agility, int stamina,
				int intellect, int bonusInt, int bonusStr, int bonusStam,
				int bonusAgi, Spell startingSpell){
			this.strength = strength;
			this.agility = agility;
			this.stamina = stamina;
			this.intellect = intellect;
			this.bonusIntellect = bonusInt;
			this.bonusStrength = bonusStr;
			this.bonusAgility = bonusAgi;
			this.bonusStamina = bonusStam;
			this.classSpells = new ArrayList<Spell>();
			classSpells.add(startingSpell);
		}
		
		public int getStrength(){ return strength; }
		public int getStamina(){ return stamina; }
		public int getAgility(){ return agility; }
		public int getIntellect(){ return intellect; }
		public String getClassName(){ return className; }
		
		public List<Spell> getClassSpells(){ return classSpells; }

		public int getBonusStam() { return bonusStamina; }
		public int getBonusStr() { return bonusStrength; }
		public int getBonusInt() { return bonusIntellect; }
		public int getBonusAgi() { return bonusAgility; }
	}
	
	private Player(PlayerClass playerClass, String name){
		this.playerClass = playerClass;
		this.className = this.playerClass.getClassName();
		this.strength = this.playerClass.getStrength();
		this.stamina = this.playerClass.getStamina();
		this.intellect = this.playerClass.getIntellect();
		this.agility = this.playerClass.getAgility();
		this.spellList = this.playerClass.getClassSpells();
		this.name = name;
		this.level = 1;
		
		inventory = new Inventory(10);
		
		this.currentHealth = this.maxHealth = this.stamina * HEALTH_PER_STAMINA;
		this.currentMana = this.maxMana = this.intellect * MANA_PER_INT;
	}
	
	public static Player createNewWarrior(String name){
		return new Player(PlayerClass.WARRIOR, name);
	}
	public static Player createNewMage(String name){
		return new Player(PlayerClass.MAGE, name);
	}
	public static Player createNewRogue(String name){
		return new Player(PlayerClass.ROGUE, name);
	}
	public static Player createNewMedic(String name){
		return new Player(PlayerClass.MEDIC, name);
	}
	

	public void goldGained(int goldEarned) {
		this.gold += goldEarned;
	}
	
	public void expGained(int earnedXP){
		while(earnedXP > 0){
			if((earnedXP + currentXP) >= xpToLevel){
				earnedXP -= xpToLevel;
				levelUp();
				continue;
			}
			else{
				currentXP += earnedXP;
				earnedXP = 0;
			}
		}
	}
	
	private void levelUp(){
		level++;
		currentXP = 0;
		this.stamina += playerClass.getBonusStam();
		this.strength += playerClass.getBonusStr();
		this.intellect += playerClass.getBonusInt();
		this.agility += playerClass.getBonusAgi();
		updateStats();
		int xpNext;
		xpNext = (this.xpToLevel * 5) + this.level;
		this.xpToLevel = xpNext;
	}
	
	private void updateStats(){
		double percent = (double) currentHealth / maxHealth;
		this.maxHealth = stamina * HEALTH_PER_STAMINA;
		this.currentHealth = (int) (maxHealth * percent);
		
		percent = (double) currentMana / maxMana;
		this.maxMana = this.intellect * MANA_PER_INT;
		this.currentMana = (int) (maxHealth * percent);
	}
	
	public void attack(Enemy e){
		int variance = ((int) (Math.random() * 10)) - 5;
		int attackDamage = (strength * 2) + (agility * 2);
		attackDamage += variance;
		e.setCurrentHealth(e.getCurrentHealth() - attackDamage);
		System.out.println(name + " hit " + e.getName() + 
				" for " + attackDamage + " damage.");
	}
	
	public void castSpell(Enemy e, Spell s){
		this.currentMana -= s.getManaCost();
		int spellDamage = s.getDamage();
		spellDamage += (this.intellect * 2);
		e.setCurrentHealth(spellDamage);
	}
	
	public void useItem(Useable it, Enemy e){
		int amount = it.getAmount();
		if(it.isHeal()){
			this.currentHealth += amount;
			this.currentHealth = (this.currentHealth > this.maxHealth) ? 
					this.maxHealth : this.currentHealth;
			return;
		}
		e.setCurrentHealth(e.getCurrentHealth() - amount);
	}
	
	public boolean run(){
		double chance = Math.random();
		if(chance > .8){
			return true;
		}
		else{
			return false;
		}
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
	
	private String displayXP(){
		StringBuilder sb = new StringBuilder();
		sb.append("Level: ");
		sb.append(level);
		sb.append("\nxp: ");
		sb.append(currentXP);
		sb.append("/");
		sb.append(xpToLevel);
		return sb.toString();
	}
	
	public String viewSpells(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < spellList.size(); ++i){
			sb.append(i + 1);
			sb.append(". ");
			sb.append(spellList.get(i));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String[] viewInventory(){
		StringBuilder sb = new StringBuilder();
		int j = 0;
		for(int i = 0; i < inventory.size(); ++i){
			if(inventory.get(i).isEmpty()){
				continue;
			}
			sb.append(j + 1);
			sb.append(". ");
			sb.append(inventory.get(i).toString());
			sb.append("\n");
			++j;
		}
		String[] info = new String[2];
		info[0] = sb.toString();
		info[1] = String.valueOf(j);
		return info;
	}
	
	public static void main(String[] args){
		Player p = createNewWarrior("Sunde");
		System.out.println(p.displayXP());
		p.levelUp();
		System.out.println(p.displayXP());
		p.levelUp();
		System.out.println(p.displayXP());
		p.levelUp();
		System.out.println(p.displayXP());
		p.levelUp();
		System.out.println(p.displayXP());
		p.levelUp();
		System.out.println(p.displayXP());
		p.levelUp();
		System.out.println(p.displayXP());
		p.levelUp();
		System.out.println(p.displayXP());
		p.levelUp();
		System.out.println(p.displayXP());
		p.levelUp();
		
	}
}