package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import rule.holdem.Game;

public class RoundGUI extends Frame
{
	private static final long serialVersionUID = -4604532540628264671L;

	Game game;

	Font fName = new Font( "Arial", Font.BOLD, 20 );

	public RoundGUI( Game game )
	{
		this.game = game;
		setLayout( null );
		setBounds( 300, 400, 300, 200 );
		addWindowListener( new WindowAdapter( )
		{
			public void windowClosing( WindowEvent e )
			{
				System.exit( 0 );
			}
		} );
	}

	public void paint( Graphics g )
	{
		g.setColor( Color.black );
		g.setFont( fName );
		String strPotInGame = "Pot in game : " + game.getPot( );
		String strPotInRound = "Pot in round : " + game.getRound( ).getPot( );
		String strMinimumBet = "Minimum bet : " + game.getRound( ).getMinimumBet( );
		g.drawString( strPotInGame, 20, 50 );
		g.drawString( strPotInRound, 20, 80 );
		g.drawString( strMinimumBet, 20, 110 );
	}
}
