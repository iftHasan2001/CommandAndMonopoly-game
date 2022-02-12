package Positions;
public abstract class Rules
{
	public abstract int calculatePropertyRent();
	public int calculateStationRent(int numberOfStations, Property station)
	{
		return numberOfStations*station.getRent();
	}
	public int calculateUtilityRent(int numberOfDice, Property utility)
	{
		return numberOfDice*utility.getRent();
	}
	public int calculateTaxRent(Property tax)
	{
		return tax.getRent();
	}
}