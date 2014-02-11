import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

import entities.Terrain;

public class RunGame {

	// controller class!

	public static void main(String[] args) {

		GameBoard game = new GameBoard(200, 200, 2);

		JFrame frame = new JFrame("display");
		frame.setVisible(true);
		frame.setSize(800, 800);
		JPanel test = new draw(game);
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

	GameBoard game;

	public draw(GameBoard g) {
		game = g;

	}

	public void paintComponent(Graphics g) {

		for (int r = 0; r < game.getRows(); r++) {
			for (int c = 0; c < game.getCols(); c++) {

				if (game.getTileAt(r, c).getTerrainType() == Terrain.DIRT)
					g.setColor(Color.black);
				else if (game.getTileAt(r, c).getTerrainType() == Terrain.WATER)
					g.setColor(Color.blue);
				else if (game.getTileAt(r, c).getTerrainType() == Terrain.HILL)
					g.setColor(new Color(139, 131, 120));
				else if (game.getTileAt(r, c).getTerrainType() == Terrain.MOUNTAIN)
					g.setColor(new Color(238, 238, 224));
				else if (game.getTileAt(r, c).getTerrainType() == Terrain.SNOW)
					g.setColor(new Color(255, 255, 255));
				else
					g.setColor(Color.GREEN);

				g.fillRect(r * 4, c * 4, 4, 4);

			}
		}

	}

}
