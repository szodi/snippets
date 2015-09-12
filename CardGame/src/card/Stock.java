package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stock
{
	private static Stock stock = new Stock( );

	List<Card> cards = new ArrayList<Card>( );

	private Stock( )
	{
		for ( CardSuit cardSuit : CardSuit.values( ) )
		{
			for ( CardRank cardRank : CardRank.values( ) )
			{
				Card card = new Card( cardSuit, cardRank );
				cards.add( card );
			}
		}
	}

	public static Stock getInstance( )
	{
		return stock;
	}

	public void shuffle( )
	{
		Collections.shuffle( cards );
	}

	public List<Card> getCards( )
	{
		return cards;
	}

	public static void main( String[ ] args )
	{
		Stock stock = getInstance( );
		stock.shuffle( );
	}
}
