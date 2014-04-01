package com;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

import com.shared.Terrain;

import entities.gameboard.GameBoard;
import entities.gameboard.Resource;

public class RunGame {

	public static void main(String[] args) {

		String h;
		String w;
		String pl;

		h = JOptionPane.showInputDialog("Enter height");
		w = JOptionPane.showInputDialog("Enter width");
		pl = JOptionPane.showInputDialog("Enter player amount");

		int x = Integer.parseInt(w);
		int y = Integer.parseInt(h);
		int p = Integer.parseInt(pl);

		GameBoard game = new GameBoard(x, y, p);

		JFrame frame = new JFrame("display");
		frame.setVisible(true);
		frame.setSize(x, y);
		JPanel test = new draw(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(test);

	}

}

// 1391894924588

class draw extends JPanel {

	GameBoard game;

	public draw(GameBoard g) {
		game = g;

	}

	public void paintComponent(Graphics g) {
		long start = System.currentTimeMillis();
		for (int r = 0; r < game.getRows(); r++) {
			for (int c = 0; c < game.getCols(); c++) {
				if (game.getTileAt(r, c).getResource() == Resource.FOOD) {
					g.setColor(new Color(186, 57, 57));
				} else if (game.getTileAt(r, c).getResource() == Resource.GOLD) {
					g.setColor(new Color(240, 188, 16));
				} else if (game.getTileAt(r, c).getResource() == Resource.STONE) {
					g.setColor(new Color(129, 133, 146));
				} else if (game.getTileAt(r, c).getResource() == Resource.WOOD) {
					g.setColor(new Color(55, 91, 67));
				} else // Resource is none or does not change color.
				{
					if (game.getTileAt(r, c).getTerrainType() == Terrain.SAND)
						g.setColor(new Color(255, 241, 212));
					else if (game.getTileAt(r, c).getTerrainType() == Terrain.WATER)
						g.setColor(new Color(51, 126, 255));
					else if (game.getTileAt(r, c).getTerrainType() == Terrain.FOREST)
						g.setColor(new Color(139, 131, 120));
					else if (game.getTileAt(r, c).getTerrainType() == Terrain.MOUNTAIN)
						g.setColor(new Color(238, 238, 224));
					else if (game.getTileAt(r, c).getTerrainType() == Terrain.SNOW)
						g.setColor(new Color(255, 255, 255));
					else
						g.setColor(new Color(118, 208, 108));
				}
				g.fillRect(r * 1, c * 1, 1, 1);

			}
		}
		long end = System.currentTimeMillis();
		System.out.println("JPanel Setup Time: " + (end - start));
	}
}
