package player;

public class InventorySlot {
	private Useable item;
	private int itemCount;
	
	public boolean isEmpty() {
		if(item == null){
			return true;
		}
		return false;
	}

}
