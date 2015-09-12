package rule.holdem;

import java.util.ArrayList;
import java.util.List;

import entity.Player;
import event.PlayerMoveListener;
import gui.PlayerGUI;
import gui.RoundGUI;

public class Game implements Runnable, PlayerMoveListener
{
	Round round;

	List<Player> players;
	List<PlayerGUI> playerGUIs = new ArrayList<PlayerGUI>( );

	double pot = 0.0;

	RoundGUI roundGUI = new RoundGUI( this );

	public Game( List<Player> players )
	{
		this.players = players;
		int offset = 0;
		for ( Player player : players )
		{
			PlayerGUI p = new PlayerGUI( this, player );
			p.addPlayerMoveListener( this );
			p.setLocation( offset * 320, 0 );
			offset++;
			playerGUIs.add( p );
		}
	}

	public void start( )
	{
		new Thread( this ).start( );
	}

	@Override
	public void run( )
	{
		for ( GamePhase gamePhase : GamePhase.values( ) )
		{
			round = new Round( players, 100, 200, 10 );
			round.initRound( gamePhase );
			round.setRoundGUI( roundGUI );
			roundGUI.setVisible( true );
			System.out.println( gamePhase );
			for ( PlayerGUI playerGUI : playerGUIs )
			{
				playerGUI.setVisible( true );
			}
			while ( !round.isEndOfRound( ) )
			{
				try
				{
					synchronized ( this )
					{
						wait( );
					}
					repaint( );
				}
				catch ( Exception e )
				{
					e.printStackTrace( );
				}
			}
			pot += round.getPot( );
			System.out.println( "POT : " + pot );
		}
		System.exit( 0 );
	}

	public void playerMovePerformed( Player player, PlayerMove playerMove, double value )
	{
		round.doPlayerMove( playerMove, value );
		synchronized ( this )
		{
			notify( );
		}
	}

	public Round getRound( )
	{
		return round;
	}

	public void repaint( )
	{
		for ( PlayerGUI playerGUI : playerGUIs )
		{
			playerGUI.repaint( );
		}
	}

	public static void main( String[ ] args )
	{
		Player player1 = new Player( "Player1", 15000 );
		Player player2 = new Player( "Player2", 15000 );
		Player player3 = new Player( "Player3", 5000 );
		Player player4 = new Player( "Player4", 15000 );
		List<Player> players = new ArrayList<Player>( );
		players.add( player1 );
		players.add( player2 );
		players.add( player3 );
		players.add( player4 );
		Game game = new Game( players );
		game.start( );
	}

	public double getPot( )
	{
		return pot;
	}
}
