import elements.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;


public class Board {

	private List<Element> elements;

	
	public Board( List<Company> companies) {
		this.elements = new ArrayList<Element>();
		this.elements.add(new Start());
		
		for (int trapCount = 1; trapCount <= 7; trapCount++) {
			this.elements.add(new Trap());
		}
		for (int chanceCount = 1; chanceCount <= 3; chanceCount++) {
			this.elements.add(new Chance());
		}
		for (int investCount = 1; investCount <= 3; investCount++) {
			this.elements.add(new Invest());
		}
		for (int partyHardCount = 1; partyHardCount <= 3; partyHardCount++) {
			this.elements.add(new PartyHard());
		}
		for (int stealCount = 1; stealCount <= 3; stealCount++) {
			this.elements.add(new Steal());
		}
		
		this.shuffleFields();

	}

	public void shuffleFields() {
		for (int i = 0; i < 1000; i++) {
			int swap1 = new Random().nextInt(this.elements.size() - 1) + 1;
			int swap2 = new Random().nextInt(this.elements.size() - 1) + 1;
			Element swapElement = this.elements.get(swap1);
			this.elements.set(swap1, this.elements.get(swap2));
			this.elements.set(swap2, swapElement);
		}
	}

	public List<Element> getElements() {
		return elements;
	}
}
