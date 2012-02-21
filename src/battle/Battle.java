package battle;

import java.util.Scanner;

import enemy.Enemy;
import player.Player;
import player.Spell;
import player.Useable;


/*
 * Battle system for the RPG. Used everytime a random battle occurs while
 * exploring the world.
 */
public class Battle {
	public static final Scanner PLAYER_INPUT = new Scanner(System.in);
	Player playerCharacter;
	Enemy enemy;
	
	public Battle(Player playerCharacter, Enemy enemy){
		this.playerCharacter = playerCharacter;
		this.enemy = enemy;
	}
	
	/*
	 * Builds and displays the menu choices the player may make. Also handles
	 * the players choice by making the correct method calls based on input.
	 */
	public void displayMenu(){
		StringBuilder sb = new StringBuilder();
		sb.append("1. Attack\t");
		sb.append("2. Cast spell\n");
		sb.append("3. Use item\t");
		sb.append("4. Run\n");
		System.out.print(sb.toString());
		int choice = Integer.valueOf(PLAYER_INPUT.nextLine());
		switch(choice){
			case 1:
				playerCharacter.attack(enemy);
				break;
			case 2:
				spellCast();
				break;
			case 3:
				useItem();
				break;
			case 4:
				run();
				break;
			default:
		}
	}
	
	//Player attempts to run, if successful the battle is terminated
	private void run() {
		if(playerCharacter.run()){
			System.out.println("You have escaped!");
			System.exit(0);
		}
		return;
	}
	
	//Display player items in inventory and allows item usage.
	private void useItem() {
		String [] inventory = playerCharacter.viewInventory();
		int returnNum = Integer.valueOf(inventory[1]) + 1;
		inventory[0] = inventory[0] + returnNum + ". Return\n";
		System.out.print(inventory[0]);
		int choice = Integer.valueOf(PLAYER_INPUT.nextLine());
		if(choice != returnNum){
			int start = inventory[0].indexOf(String.valueOf(choice));
			int end = inventory[0].indexOf(String.valueOf(choice + 1));
			String itemChoice = inventory[0].substring((start + 3), end - (start + 2));
			itemChoice = itemChoice.trim();
			Useable item = playerCharacter.getInventory().get(itemChoice);
			playerCharacter.useItem(item, enemy);
		}
		else{
			displayMenu();
		}
	}
	
	/*
	 * Allows the player to view spells they have and cast one if they would
	 * like
	 */
	private void spellCast(){
		System.out.println(playerCharacter.getCurrentMana());
		System.out.print(playerCharacter.viewSpells());
		int returnNum = playerCharacter.getSpells().size() + 1;
		System.out.print(returnNum);
		System.out.println(". Return");
		int choice = Integer.valueOf(PLAYER_INPUT.nextLine());
		if(choice != returnNum){
			Spell sp = playerCharacter.getSpells().get(choice - 1);
			if(playerCharacter.getCurrentMana() >= sp.getManaCost()){
				playerCharacter.castSpell(enemy, sp);
			}
		}
		else{
			displayMenu();
		}
	}
	

	/*
	 * Starts the main battle loop and makes calls to allow the player to 
	 * attack. Continues until player wins/loses.
	 */
	public void startBattle(){
		while((playerCharacter.getCurrentHealth() > 0) &&
				(enemy.getCurrentHealth() > 0)){
			System.out.println(playerCharacter);
			System.out.println(enemy);
			displayMenu();
			
			enemy.attack(playerCharacter);
		}
		if(playerCharacter.getCurrentHealth() <= 0){
			System.out.println("You have lost!");
		}
		else{
			System.out.println("You have won!");
			this.victory();
		}
	}
	
	/*
	 * Display screen if the player wins the battle. Displays any gold/exp 
	 * rewards
	 */
	public void victory(){
		int gold = enemy.getGoldReward();
		int xpAward = enemy.getXpAward();
		playerCharacter.expGained(xpAward);
		System.out.print(playerCharacter.getName() + " has earned ");
		System.out.println(xpAward + " experience.");
		playerCharacter.goldGained(gold);
		System.out.print(playerCharacter.getName() + " has earned ");
		System.out.println(gold + " gold.");
	}
	
	public static void main(String[] args){
		Player pl = Player.createNewWarrior("Sunde");
		Enemy e1 = new Enemy("vulture", 15, 10, 8, 12, 100, 100);
		Battle b = new Battle(pl, e1);
		b.startBattle();
	}
}
