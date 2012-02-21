package player;

/*
 * Interface for all useable items in game.
 */
public interface Useable {

	boolean isHeal();

	int getAmount();

	boolean nameEquals(String itemName);
	
	String getName();
}
