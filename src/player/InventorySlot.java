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
		this.empty = false;
	}
	
	public void add(Useable item, int itemCount){
		this.item = item;
		this.itemCount = itemCount;
	}
	
	@Override public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(item);
		sb.append("\t");
		sb.append(itemCount);
		return sb.toString();
	}
	
	public Useable getItem(){ return item; }
	
	public static void main(String[] args){
		InventorySlot is = new InventorySlot();
		is.add(new Potion("Testing IvSlot", false, 10));
		System.out.println(is);
	}

}
