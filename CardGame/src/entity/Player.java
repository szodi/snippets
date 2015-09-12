package entity;

public class Player
{
	String name;

	double chips;
	double chipsInRound;
	double chipsInGame;

	boolean pending = true;

	public Player(String name, double chips)
	{
		this.name = name;
		this.chips = chips;
	}

	public double doBet(double chips)
	{
		double maxChips = Math.min(this.chips, chips);
		this.chips -= maxChips;
		chipsInRound += maxChips;
		chipsInGame += maxChips;
		return maxChips;
	}

	public double getChips()
	{
		return chips;
	}

	public void setChips(double chips)
	{
		this.chips = chips;
	}

	public double getChipsInRound()
	{
		return chipsInRound;
	}

	public void setChipsInRound(double chipsInRound)
	{
		this.chipsInRound = chipsInRound;
	}

	public double getChipsInGame()
	{
		return chipsInGame;
	}

	public void setChipsInGame(double chipsInGame)
	{
		this.chipsInGame = chipsInGame;
	}

	public boolean isPending()
	{
		return pending;
	}

	public void setPending(boolean pending)
	{
		this.pending = pending;
	}

	public String toString()
	{
		return getClass().getName() + "[NAME: " + name + "; CHIPS:" + chips + "; PENDING:" + pending + "; CHIPS_IN_ROUND:" + chipsInRound + "; CHIPS_IN_GAME:" + chipsInGame + "]";
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
