package player;

public class Potion implements Useable {
	private boolean isHeal;
	private int amount;
	private String name;
	
	public Potion(String name, boolean isHeal, int amount){
		this.name = name;
		this.isHeal = isHeal;
		this.amount = amount;
	}
	
	@Override private String getName(){ return name; }
	@Override public boolean isHeal() { return isHeal; }
	@Override public int getAmount() { return amount; }
	
	@Override public String toString(){ return name; }
	
	public boolean nameEquals(String itemName){
		if(itemName.equals(name)){
			return true;
		}
		return false;
	}
}
