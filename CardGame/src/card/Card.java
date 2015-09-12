package card;

import java.util.StringTokenizer;

public class Card implements Comparable<Card>
{
	CardSuit suit;
	CardRank rank;

	protected Card( CardSuit suit, CardRank rank )
	{
		this.suit = suit;
		this.rank = rank;
	}

	public static Card parse( String string )
	{
		String strSuit = String.valueOf( string.charAt( string.length( ) - 1 ) );
		String strRank = string.substring( 0, string.length( ) - 1 );
		if ( "j".equalsIgnoreCase( strRank ) )
		{
			strRank = "11";
		}
		else if ( "q".equalsIgnoreCase( strRank ) )
		{
			strRank = "12";
		}
		else if ( "k".equalsIgnoreCase( strRank ) )
		{
			strRank = "13";
		}
		else if ( "a".equalsIgnoreCase( strRank ) )
		{
			strRank = "14";
		}
		CardRank cardRank = CardRank.getCardRank( Integer.parseInt( strRank ) );
		CardSuit cardSuit = CardSuit.getCardSuit( strSuit );
		return new Card( cardSuit, cardRank );
	}

	public static Card[ ] parseCards( String string )
	{
		StringTokenizer st = new StringTokenizer( string );
		Card[ ] cards = new Card[ st.countTokens( ) ];
		int i = 0;
		while ( st.hasMoreTokens( ) )
		{
			cards[ i++ ] = parse( st.nextToken( ) );
		}
		return cards;
	}

	public boolean equals( Object object )
	{
		if ( object instanceof Card )
		{
			Card card = ( Card ) object;
			return card.getRank( ) == rank && card.getSuit( ) == suit;
		}
		return false;
	}

	public CardSuit getSuit( )
	{
		return suit;
	}

	public void setSuit( CardSuit suit )
	{
		this.suit = suit;
	}

	public CardRank getRank( )
	{
		return rank;
	}

	public void setRank( CardRank rank )
	{
		this.rank = rank;
	}

	public String toString( )
	{
		return "" + rank + suit;
	}

	public int compareTo( Card o )
	{
		return 0;
	}
}
