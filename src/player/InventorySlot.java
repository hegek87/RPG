package player;

public class InventorySlot {
	private Useable item;
	private int itemCount;
	private boolean empty;
	
	public InventorySlot(){
		this.item = null;
		this.itemCount = 0;
		this.empty = true;
	}
	
	public boolean isEmpty() { return empty; }

	public void add(int i) {
		this.itemCount++;
	}

	public void add(Useable item) {
		this.item = item;
		this.itemCount = 1;
	}
	
	public void add(Useable item, int itemCount){
		this.item = item;
		this.itemCount = itemCount;
	}

}
