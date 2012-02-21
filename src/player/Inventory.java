package player;

public class Inventory {
	InventorySlot [] spaces;
	private int size;
	
	public Inventory(int size) {
		spaces = new InventorySlot[size];
		this.size = size;
		for(int i = 0; i < spaces.length; ++i){
			spaces[i] = new InventorySlot();
		}
	}

	public int size() {	return spaces.length; }
	public InventorySlot get(int i) { return spaces[i];	}
	
	public void add(Useable item){
		if(contains(item)){
			get(item).add(1);
		}
		else{
			int i = 0;
			while(!spaces[i].isEmpty()){
				i++;
			}
			spaces[i].add(item);
		}
	}

	private boolean contains(Useable item) {
		for(InventorySlot iv : spaces){
			if(iv.equals(item)){
				return true;
			}
		}
		return false;
	}

	private InventorySlot get(Useable item) {	
		for(InventorySlot iv : spaces){
			if(iv.equals(item)){
				return iv;
			}
		}
		return null;
	}
	
	public Useable get(String item){
		for(InventorySlot iv : spaces){
			Useable ivItem = iv.getItem();
			if(ivItem.nameEquals(item) && ivItem != null){
				return (Useable) ivItem;
			}
		}
		return null;
	}
	
	@Override public String toString(){
		int j = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("Inventory\n");
		for(int i = 0; i < size; ++i){
			if(spaces[i].isEmpty()){
				continue;
			}
			sb.append(++j);
			sb.append(". ");
			sb.append(spaces[i]);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args){
		Inventory iv = new Inventory(10);
		iv.add(new Potion("Test", false, 10));
		System.out.println(iv);
	}

}
