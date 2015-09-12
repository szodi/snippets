package rule.holdem;

import java.util.List;

import entity.Player;
import gui.RoundGUI;

public class Round
{
	List<Player> players;
	Player actualPlayer;

	double smallBlind;
	double bigBlind;
	double ante;
	double minimumBet;

	double pot;

	RoundGUI roundGUI;

	public Round( List<Player> players, double smallBlind, double bigBlind, double ante )
	{
		this.players = players;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
		this.ante = ante;
		minimumBet = bigBlind;
	}

	public void initRound( GamePhase gamePhase )
	{
		resetPlayersPending( );
		if ( gamePhase == GamePhase.PREFLOP )
		{
			for ( Player player : players )
			{
				pot += player.doBet( ante );
			}
			minimumBet += ante;
			actualPlayer = players.get( 0 ); // small blind
			pot += actualPlayer.doBet( smallBlind );
			actualPlayer = getNextPendingPlayer( actualPlayer ); // big blind
			pot += actualPlayer.doBet( bigBlind );
			actualPlayer = getNextPendingPlayer( actualPlayer );
			resetPlayersPending( );
		}
		else
		{
			for ( Player player : players )
			{
				player.setChipsInRound( 0 );
			}
			actualPlayer = players.get( 0 );
			minimumBet = 0.0;
		}
	}

	public boolean isEndOfRound( )
	{
		for ( Player player : players )
		{
			if ( player.isPending( ) )
			{
				return false;
			}
		}
		return true;
	}

	public void doPlayerMove( PlayerMove playerMove, double bet )
	{
		logPlayerMove( actualPlayer, playerMove, bet, minimumBet );
		Player nextPlayer = getNextPendingPlayer( actualPlayer );
		if ( playerMove == PlayerMove.FOLD )
		{
			players.remove( actualPlayer );
		}
		else if ( playerMove == PlayerMove.BET )
		{
			double playerBet = actualPlayer.doBet( bet );
			pot += playerBet;
			if ( playerBet > minimumBet )
			{
				minimumBet = actualPlayer.getChipsInRound( );
				resetPlayersPending( );
				nextPlayer = getNextPendingPlayer( actualPlayer );
			}
			actualPlayer.setPending( false );
		}
		actualPlayer = nextPlayer;
		roundGUI.repaint( );

	}

	public void resetPlayersPending( )
	{
		for ( Player player : players )
		{
			player.setPending( player.getChips( ) > 0.0 );
		}
	}

	public Player getNextPendingPlayer( Player reference )
	{
		int referencePlayerIndex = players.indexOf( reference );
		if ( referencePlayerIndex == -1 )
		{
			return null;
		}
		int i = referencePlayerIndex;
		Player nextPlayer = null;
		while ( reference != nextPlayer )
		{
			nextPlayer = players.get( ( i + 1 ) % players.size( ) );
			if ( nextPlayer.isPending( ) )
			{
				break;
			}
			i++;
		}
		return nextPlayer;
	}

	public static void logPlayerMove( Player player, PlayerMove playerMove, double bet, double minimumBet )
	{
		if ( playerMove == PlayerMove.FOLD )
		{
			System.out.println( player.getName( ) + " folds" );
			return;
		}
		bet = Math.min( player.getChips( ), bet );
		if ( bet == 0.0 )
		{
			System.out.println( player.getName( ) + " checks" );
			return;
		}
		if ( bet + player.getChipsInRound( ) == minimumBet )
		{
			System.out.print( player.getName( ) + " calls" );
		}
		if ( bet + player.getChipsInRound( ) > minimumBet )
		{
			System.out.print( player.getName( ) + " bets " + bet );
		}
		if ( player.getChips( ) - bet == 0.0 )
		{
			System.out.println( " and is all-in" );
		}
		else
		{
			System.out.println( );
		}
	}

	public synchronized void dump( )
	{
		System.out.println( "===========================================================================" );
		for ( Player player : players )
		{
			System.out.println( player );
		}
		System.out.println( "ACTUAL : " + actualPlayer );
		System.out.println( "MINIMUM : " + minimumBet );
		System.out.println( isEndOfRound( ) );
		System.out.println( "===========================================================================" );
	}

	public Player getActualPlayer( )
	{
		return actualPlayer;
	}

	public List<Player> getPlayers( )
	{
		return players;
	}

	public double getMinimumBet( )
	{
		return minimumBet;
	}

	public double getPot( )
	{
		return pot;
	}

	public void setRoundGUI( RoundGUI roundGUI )
	{
		this.roundGUI = roundGUI;
	}
}
