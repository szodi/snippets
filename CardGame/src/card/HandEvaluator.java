package card;

public class HandEvaluator
{
	private static Hand evaluate5( Card[ ] cards )
	{
		long extendedRankBitField = 0;
		int rankBitField = 0;
		int kickers = 0;
		for ( int i = 0; i < cards.length; i++ )
		{
			long rankValue = cards[ i ].getRank( ).getValue( ) - 2;
			extendedRankBitField += ( ( extendedRankBitField >> ( rankValue * 4 ) & 0b1111 ) + 1 ) << ( rankValue * 4 );
			rankBitField += 1 << rankValue;
		}
		int result = 0;
		HandType handType = HandType.HIGH_CARD;
		for ( int i = 0; i < 13; i++ )
		{
			long mod = extendedRankBitField & 0b1111;
			if ( mod == 0b0001 )
			{
				kickers += mod << i;
			}
			else if ( mod == 0b0011 )
			{
				if ( handType == HandType.THREE_OF_A_KIND )
				{
					handType = HandType.FULL_HOUSE;
					result += 1 << i;
				}
				else if ( handType == HandType.PAIR )
				{
					handType = HandType.TWO_PAIRS;
					result += 1 << ( i + 13 );
				}
				else
				{
					handType = HandType.PAIR;
					result += 1 << ( i + 13 );
				}
			}
			else if ( mod == 0b0111 )
			{
				if ( handType == HandType.PAIR )
				{
					handType = HandType.FULL_HOUSE;
					result = result >> 13;
				}
				else
				{
					handType = HandType.THREE_OF_A_KIND;
				}
				result += 1 << ( i + 13 );
			}
			else if ( mod == 0b1111 )
			{
				handType = HandType.FOUR_OF_A_KIND;
				result = 1 << ( i + 13 );
			}
			extendedRankBitField = extendedRankBitField >> 4;
		}
		if ( handType == HandType.HIGH_CARD )
		{
			kickers = 0;
			boolean isFlush = cards[ 0 ].getSuit( ).getValue( ) == ( cards[ 1 ].getSuit( ).getValue( ) | cards[ 2 ].getSuit( ).getValue( ) | cards[ 3 ].getSuit( ).getValue( ) | cards[ 4 ].getSuit( ).getValue( ) );
			if ( isFlush && rankBitField == 0b1111100000000 )
			{
				handType = HandType.ROYAL_FLUSH;
			}
			else
			{
				long group = ( rankBitField / ( rankBitField & -rankBitField ) );
				if ( group == 0b11111 || group == 0b1000000001111 )
				{
					handType = isFlush ? HandType.STRAIGHT_FLUSH : HandType.STRAIGHT;
					rankBitField -= ( group == 0b1000000001111 ) ? 0b1000000000000 : 0;
				}
				else if ( isFlush )
				{
					handType = HandType.FLUSH;
				}
			}
			result = rankBitField << 13;
		}
		result += kickers;
		Hand hand = new Hand( result, handType );
		return hand;
	}

	public static Hand evaluate( Card[ ] cards )
	{
		if ( cards.length < 5 )
		{
			throw new IllegalArgumentException( "Cards array should include at least 5 elements !" );
		}
		if ( cards.length == 5 )
		{
			return evaluate5( cards );
		}
		Card[ ] combination = new Card[ 5 ];
		System.arraycopy( cards, 0, combination, 0, 5 );
		Hand bestHand = null;
		long mask = 0b11111 << ( cards.length - 5 );
		long fullMask = mask + ( long ) Math.pow( 2, ( cards.length - 5 ) ) - 1;
		while ( mask > 0b11111 )
		{
			long lowest = mask & -mask;
			if ( lowest > 1 )
			{
				mask -= lowest - ( lowest >> 1 );
			}
			else
			{
				long xor = mask ^ fullMask;
				long q = ( xor & -xor );
				long p = mask - ( mask % q );
				long k = ( p & -p );
				mask = p - k + ( k >> 1 ) + ( mask % q ) * ( ( k >> 1 ) / q );
			}
			int j = 0;
			for ( int i = 0; i < cards.length; i++ )
			{
				long bit = ( long ) Math.pow( 2, cards.length - i - 1 );
				if ( ( mask & bit ) / bit == 1 )
				{
					combination[ j++ ] = cards[ i ];
				}
			}
			Hand hand = evaluate5( combination );
			if ( bestHand == null || ( bestHand.compareTo( hand ) < 0 ) )
			{
				bestHand = hand;
			}
		}
		return bestHand;
	}

	public static void main( String[ ] args )
	{
		Card[ ] cards = Card.parseCards( "Qs 3h Ks 2c 4d 3d Kh Kd" );
		Hand hand = evaluate( cards );
		System.out.println( hand.getHandType( ) );
	}
}
