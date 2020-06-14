package elements;

public class Steal extends Element {
	private boolean isActive;
	private StealType type;
	


	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public StealType getType() {
		return type;
	}

	public void setType(StealType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "|St|";
	}
}
