package elements;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private static int ID = 0;
	private int id;
	private boolean isActive;
	private int currentPosition;
	private int chocolateMoney;
	private int roundEarnings;
	private Steal roundSteal;
	private boolean isStealActive;
	private List<Trap> activatedTraps;
	private List<Company> companiesInvested;
	
	public Player() {
		this.id = Player.ID++;
		this.isActive = true;
		this.currentPosition = 0;
		this.chocolateMoney = 1000;
		this.roundEarnings = 0;
		this.isStealActive = false;
		this.activatedTraps = new ArrayList<Trap>();
		this.companiesInvested = new ArrayList<Company>();
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getChocolateMoney() {
		return chocolateMoney;
	}

	public void setChocolateMoney(int chocolateMoney) {
		if(chocolateMoney < 0) {
			this.chocolateMoney = 0;
			return;
		}
		this.chocolateMoney = chocolateMoney;
	}
	
	public void addTrap(Trap trap) {

		this.activatedTraps.add(trap);
	}

	public List<Trap> getActivatedTraps() {
		return new ArrayList<Trap>(this.activatedTraps);
	}
	
	public void clearTraps() {
		this.activatedTraps.clear();
	}
	
	public void addCompany(Company companies) {

		this.companiesInvested.add(companies);
	}

	public List<Company> getCompaniesInvested() {
		return new ArrayList<Company>(this.companiesInvested);
	}
	
	public void clearCompaniesInvested() {
		this.companiesInvested.clear();
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return String.format("Играч с id: %d се намира на позиция: %d и разполага с %d пари",this.id,this.currentPosition,this.chocolateMoney);

	}

	public int getRoundEarnings() {
		return roundEarnings;
	}

	public void setRoundEarnings(int roundEarnings) {
		this.roundEarnings = roundEarnings;
	}

	public void removeHazartenBosTrap() {
		Trap trap = this.activatedTraps.stream().findAny().get();
		this.activatedTraps.remove(trap);
	}

	public Steal getRoundSteal() {
		return roundSteal;
	}

	public void setRoundSteal(Steal roundSteal) {
		this.roundSteal = roundSteal;
	}

	public boolean isStealActive() {
		return isStealActive;
	}

	public void setStealActive(boolean isStealActive) {
		this.isStealActive = isStealActive;
	}

	public int getId() {
		return id;
	}

	
}
