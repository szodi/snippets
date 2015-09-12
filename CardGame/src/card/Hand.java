package card;

public class Hand implements Comparable<Hand>
{
	int cards;

	HandType handType;

	public Hand( int cards, HandType handType )
	{
		this.cards = cards;
		this.handType = handType;
	}

	public String toString( )
	{
		return handType + " : " + Long.toBinaryString( cards );
	}

	public int compareTo( Hand hand )
	{
		if ( hand.getHandType( ) == handType )
		{
			if ( hand.getCards( ) > cards )
			{
				return -1;
			}
			else if ( hand.getCards( ) < cards )
			{
				return 1;
			}
			return 0;
		}
		else
		{
			return handType.compareTo( hand.getHandType( ) );
		}
	}

	public HandType getHandType( )
	{
		return handType;
	}

	public int getCards( )
	{
		return cards;
	}
}
