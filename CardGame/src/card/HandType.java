package card;

public enum HandType
{
	FOUR_OF_A_KIND( 8 ), STRAIGHT_FLUSH( 9 ), STRAIGHT( 5 ), FLUSH( 6 ), HIGH_CARD( 1 ), PAIR( 2 ), TWO_PAIRS( 3 ), ROYAL_FLUSH( 10 ), THREE_OF_A_KIND( 4 ), FULL_HOUSE( 7 );

	int value;

	private HandType( int value )
	{
		this.value = value;
	}
}
