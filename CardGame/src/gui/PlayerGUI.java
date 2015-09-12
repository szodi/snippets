package gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import rule.holdem.Game;
import rule.holdem.PlayerMove;
import entity.Player;
import event.PlayerMoveListener;

public class PlayerGUI extends Frame implements ActionListener
{
	private static final long serialVersionUID = -4604532540628264671L;

	Game game;
	Player player;

	Font fName = new Font("Arial", Font.BOLD, 20);

	TextField tfBet = new TextField();

	Button bFold = new Button("Fold");
	Button bCall = new Button("Call");
	Button bBet = new Button("Bet");
	Button bAllIn = new Button("All-in");

	PlayerMoveListener playerMoveListener;

	public PlayerGUI(Game game, Player player)
	{
		this.game = game;
		this.player = player;
		setLayout(null);
		setBounds(0, 0, 300, 200);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		tfBet.setBounds(20, 60, 150, 20);
		bFold.setBounds(20, 85, 40, 20);
		bCall.setBounds(70, 85, 40, 20);
		bBet.setBounds(120, 85, 40, 20);
		bAllIn.setBounds(170, 85, 40, 20);
		add(tfBet);
		add(bFold);
		add(bCall);
		add(bBet);
		add(bAllIn);
		bFold.addActionListener(this);
		bCall.addActionListener(this);
		bBet.addActionListener(this);
		bAllIn.addActionListener(this);
	}

	public void paint(Graphics g)
	{
		if (isFolded())
		{
			g.setColor(Color.lightGray);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (game.getRound().getActualPlayer() == player)
		{
			g.setColor(Color.green);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		g.setColor(Color.black);
		g.setFont(fName);
		String caption = player.getName() + " (" + player.getChips() + ")";
		g.drawString(caption, 20, 50);
		g.drawString("Pot: " + (game.getPot() + game.getRound().getPot()), 20, 140);
	}

	public boolean isFolded()
	{
		return !game.getRound().getPlayers().contains(player);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bFold)
		{
			playerMoveListener.playerMovePerformed(player, PlayerMove.FOLD, 0);
		}
		else if (e.getSource() == bCall)
		{
			playerMoveListener.playerMovePerformed(player, PlayerMove.BET, game.getRound().getMinimumBet() - player.getChipsInRound());
		}
		else if (e.getSource() == bBet)
		{
			double bet = Double.parseDouble(tfBet.getText());
			playerMoveListener.playerMovePerformed(player, PlayerMove.BET, bet);
		}
		else if (e.getSource() == bAllIn)
		{
			playerMoveListener.playerMovePerformed(player, PlayerMove.BET, player.getChips());
		}
		// game.getRound().dump();
		game.repaint();
	}

	public void addPlayerMoveListener(PlayerMoveListener playerMoveListener)
	{
		this.playerMoveListener = playerMoveListener;
	}
}
