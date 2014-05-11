package com;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;


import com.shared.model.Terrain;
import com.shared.model.gameboard.GameBoard;
import com.shared.model.gameboard.Resource;

public class RunGame {

	// controller class!

	public static void main(String[] args) {


		GameBoard game = new GameBoard(256, 256); // To change resolution, just change two parameters here

		game.resourceDistNat();				        // must be able to divide 1000 evenly. 

		JFrame frame = new JFrame("display");
		frame.setVisible(true);
		frame.setSize(1000, 1000);
		JPanel test = new draw(game, (int)(frame.getWidth() / game.getCols()) );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setContentPane(test);

		if (args.length == 3) {

		} else {

			System.out.println("usage");
		}

	}

}

// 1391894924588

class draw extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1185015430197023481L;
	GameBoard game;
	int mult; 

	public draw(GameBoard g, int m) {
		game = g;
		mult = m;
	}

	public void paintComponent(Graphics g) {

		for (int r = 0; r < game.getRows(); r++) {
			for (int c = 0; c < game.getCols(); c++) {
				if (game.getTileAt(r, c).getResource() == Resource.FOOD)
				{
					g.setColor(new Color(186,57,57));
				} else if (game.getTileAt(r, c).getResource() == Resource.GOLD)
				{
					g.setColor(new Color(240, 188, 16));
				} else if (game.getTileAt(r, c).getResource() == Resource.STONE)
				{
					g.setColor(new Color(129, 133, 146));
				} else if (game.getTileAt(r, c).getResource() == Resource.WOOD)
				{
					g.setColor(new Color(55, 91, 67));
				} else // Resource is none or does not change color.
				{
					if (game.getTileAt(r, c).getTerrainType() == Terrain.SAND)
						g.setColor(new Color(255, 241, 212));
					else if (game.getTileAt(r, c).getTerrainType() == Terrain.WATER)
						g.setColor(new Color(51, 126, 255));
					else if (game.getTileAt(r, c).getTerrainType() == Terrain.FOREST)
						g.setColor(new Color(100, 131, 120));
					else if (game.getTileAt(r, c).getTerrainType() == Terrain.MOUNTAIN)
						g.setColor(new Color(140, 132, 118));
					else if (game.getTileAt(r, c).getTerrainType() == Terrain.SNOW)
						g.setColor(new Color(250, 250, 250));
					else
						g.setColor(new Color(118,208,108));
				}
				g.fillRect(r * mult, c * mult, mult, mult);

			}
		}

	}

}
