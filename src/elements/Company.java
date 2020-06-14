package elements;

import java.util.Random;

public class Company {

	private String name;
	private int minInvest;
	private int invest;
	private double coefficient;
	private int riskMinus;
	private int riskPlus;
	
	public Company(String name, int minInvest, double coefficient, int riskMinus, int riskPlus) {
		this.name = name;
		this.minInvest = minInvest;
		this.invest = 0;
		this.coefficient = coefficient;
		this.riskMinus = riskMinus;
		this.riskPlus = riskPlus;
	}
	
	public void payInvestments(Player player) {
		
		int chance = new Random().nextInt(this.riskPlus - this.riskMinus + 1) - this.riskMinus;
		if(chance <= 0) {
			System.out.println(player + " губи инвестициите си в " + this);
		}
		else {
			System.out.println(player + " печели инвестициите си в " + this);
			player.setChocolateMoney((int) (player.getChocolateMoney() + this.invest * this.coefficient));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinInvest() {
		return minInvest;
	}

	public void setMinInvest(int minInvest) {
		this.minInvest = minInvest;
	}

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double koeficient) {
		this.coefficient = koeficient;
	}

	public int getRiskMinus() {
		return riskMinus;
	}

	public void setRiskMinus(int riskMinus) {
		this.riskMinus = riskMinus;
	}

	public int getRiskPlus() {
		return riskPlus;
	}

	public void setRiskPlus(int riskPlus) {
		this.riskPlus = riskPlus;
	}

	public int getInvest() {
		return invest;
	}

	public void setInvest(int invest) {
		this.invest = invest;
	}

	@Override
	public String toString() {
		return String.format("Име: %s,минимална инвестиция: %d,коефицент на възвращаемост: %.2f,риск-минимум: %d,риск-максимум: %d"
				,this.name,this.minInvest,this.coefficient,this.riskMinus,this.riskPlus);
	}


	
	
}
