package card;

public enum CardRank
{
	CARD_2( 2 ), CARD_3( 3 ), CARD_4( 4 ), CARD_5( 5 ), CARD_6( 6 ), CARD_7( 7 ), CARD_8( 8 ), CARD_9( 9 ), CARD_10( 10 ), JACK( 11 ), QUEEN( 12 ), KING( 13 ), ACE( 14 );

	int value;

	CardRank( int value )
	{
		this.value = value;
	}

	public int compare( CardRank cardRank )
	{
		if ( cardRank.getValue( ) < value )
		{
			return 1;
		}
		if ( cardRank.getValue( ) > value )
		{
			return -1;
		}
		return 0;
	}

	public int getValue( )
	{
		return value;
	}

	public static CardRank getCardRank( int value )
	{
		switch ( value )
		{
			case 2:
				return CARD_2;
			case 3:
				return CARD_3;
			case 4:
				return CARD_4;
			case 5:
				return CARD_5;
			case 6:
				return CARD_6;
			case 7:
				return CARD_7;
			case 8:
				return CARD_8;
			case 9:
				return CARD_9;
			case 10:
				return CARD_10;
			case 11:
				return JACK;
			case 12:
				return QUEEN;
			case 13:
				return KING;
			case 14:
				return ACE;
			default:
				return null;
		}
	}

	public String toString( )
	{
		switch ( value )
		{
			case 2:
				return "2";
			case 3:
				return "3";
			case 4:
				return "4";
			case 5:
				return "5";
			case 6:
				return "6";
			case 7:
				return "7";
			case 8:
				return "8";
			case 9:
				return "9";
			case 10:
				return "T";
			case 11:
				return "J";
			case 12:
				return "Q";
			case 13:
				return "K";
			case 14:
				return "A";
			default:
				return "";
		}
	}
}
