package card;

public enum CardSuit
{
	SPADE( 1 ), HEART( 2 ), DIAMOND( 4 ), CLUB( 8 );

	long value;

	CardSuit( long value )
	{
		this.value = value;
	}

	public int compare( CardSuit cardSuit )
	{
		if ( cardSuit.getValue( ) < value )
		{
			return 1;
		}
		if ( cardSuit.getValue( ) > value )
		{
			return -1;
		}
		return 0;
	}

	public long getValue( )
	{
		return value;
	}

	public long getIndex( )
	{
		if ( this == SPADE )
		{
			return 0;
		}
		else if ( this == HEART )
		{
			return 1;
		}
		else if ( this == DIAMOND )
		{
			return 2;
		}
		else if ( this == CLUB )
		{
			return 3;
		}
		return -1;
	}

	public static CardSuit getCardSuit( String valueString )
	{
		if ( "s".equalsIgnoreCase( valueString ) )
		{
			return SPADE;
		}
		else if ( "h".equalsIgnoreCase( valueString ) )
		{
			return HEART;
		}
		else if ( "d".equalsIgnoreCase( valueString ) )
		{
			return DIAMOND;
		}
		else if ( "c".equalsIgnoreCase( valueString ) )
		{
			return CLUB;
		}
		return null;
	}

	public String toString( )
	{
		if ( value == 1 )
		{
			return "s";
		}
		else if ( value == 2 )
		{
			return "h";
		}
		else if ( value == 4 )
		{
			return "d";
		}
		else if ( value == 8 )
		{
			return "c";
		}
		return "";
	}
}
