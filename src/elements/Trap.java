package elements;

import java.util.Random;

public class Trap extends Element {
	
	private boolean isActive;
	private TrapType type;
	
	public void specialEffect(Player player) {
		if(this.type.equals(TrapType.TAX_AUDIT)) {
			player.setRoundEarnings( (int)(player.getRoundEarnings() * 0.9) );
		}
		
		if(this.type.equals(TrapType.CAT_DIVORCE)) {
			int dice = new Random().nextInt(10) + 1;
			if (dice == 8 || dice == 2 ) {
				player.setRoundEarnings(0);
			}
		}
	}

	
	public Trap() {
		this.isActive = false;
	}





	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public TrapType getType() {
		return type;
	}


	public void setType(TrapType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "| T|";
	}
}
