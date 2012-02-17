package battle;

import java.util.Scanner;

import enemy.Enemy;
import player.Player;
import player.Spell;
import player.Useable;

public class Battle {
	public static final Scanner PLAYER_INPUT = new Scanner(System.in);
	Player playerCharacter;
	Enemy enemy;
	
	public Battle(Player playerCharacter, Enemy enemy){
		this.playerCharacter = playerCharacter;
		this.enemy = enemy;
	}
	
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
	
	private void run() {
		if(playerCharacter.run()){
			System.out.println("You have escaped!");
			System.exit(0);
		}
		return;
	}

	private void useItem() {
		String [] inventory = playerCharacter.viewInventory();
		System.out.print(inventory[0]);
		int returnNum = Integer.valueOf(inventory[1]) + 1;
		System.out.print(returnNum);
		System.out.println(". Return");
		int choice = Integer.valueOf(PLAYER_INPUT.nextLine());
		if(choice != returnNum){
			int start = inventory[0].indexOf(choice);
			int end = inventory[0].indexOf(choice + 1);
			String itemChoice = inventory[0].substring(start, end - start);
			Useable item = playerCharacter.getInventory().get(itemChoice);
			playerCharacter.useItem(item, enemy);
		}
		else{
			displayMenu();
		}
	}

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
