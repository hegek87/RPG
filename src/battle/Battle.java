package battle;

import java.util.Scanner;

import enemy.Enemy;
import player.Player;

public class Battle {
	public static final Scanner PLAYER_INPUT = new Scanner(System.in);
	Player playerCharacter;
	Enemy enemy;
	
	public Battle(Player playerCharacter, Enemy enemy){
		this.playerCharacter = playerCharacter;
		this.enemy = enemy;
	}
	
	public String displayMenu(){
		StringBuilder sb = new StringBuilder();
		sb.append("1. Attack\t");
		sb.append("2. Cast spell\n");
		sb.append("3. Use item\t");
		sb.append("4. Run\n");
		return sb.toString();
	}
	
	public void startBattle(){
		while((playerCharacter.getCurrentHealth() > 0) &&
				(enemy.getCurrentHealth() > 0)){
			System.out.println(playerCharacter);
			System.out.println(enemy);
			System.out.print(displayMenu());
			int input = Integer.valueOf(PLAYER_INPUT.nextLine());
			switch(input){
				case 1:
					playerCharacter.attack(enemy);
					break;
				case 2:
					System.out.println(playerCharacter.getCurrentMana());
					System.out.print(playerCharacter.viewSpells());
					int returnNum = playerCharacter.getSpells().size() + 1;
					System.out.print(returnNum);
					System.out.println(". Return");
					int choice = Integer.valueOf(PLAYER_INPUT.nextLine());
					if(choice != returnNum){
						playerCharacter.castSpell(enemy, 
								playerCharacter.getSpells().get(choice - 1));
					}
					break;
				case 3:
				case 4:
					if(playerCharacter.run()){ 
						System.out.println("You have escaped");
						return;
					}
					else{ break; }
				default:
			}
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
