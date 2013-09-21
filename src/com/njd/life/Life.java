package com.njd.life;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Life extends JPanel {

	private Timer	timer	= new Timer();
	private Grid	grid	= new Grid();
	private boolean	play	= false;

	public Life() {
		setPreferredSize(new Dimension(800, 600));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int col = ((e.getX() + 2) / grid.getTileWidth());
				int row = ((e.getY() + 2) / grid.getTileHeight());
				grid.add(col, row);
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					grid.togglePause();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					play = false;
					grid.step();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					play = true;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					play = false;
				} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE
						|| e.getKeyCode() == KeyEvent.VK_DELETE) {
					play = false;
					grid.pause();
					grid.clear();
				} else if (e.getKeyCode() == KeyEvent.VK_R) {
					play = false;
					grid.pause();
					grid.clear();
					Random r = new Random(System.currentTimeMillis());
					int total = (int) (0.75 * r.nextInt((getWidth() / grid
							.getTileWidth())
							* (getHeight() / grid.getTileHeight())));
					for (int i = 0; i < total; ++i) {
						grid.add(r.nextInt(getWidth() / grid.getTileWidth()),
								r.nextInt(getHeight() / grid.getTileHeight()));
					}
				}
			}
		});

		timer.schedule(new TimerTask() {
			public void run() {
				if (play && !grid.isPaused()) {
					grid.step();
				}
				Life.this.repaint();
			}
		}, 0, 20);
	}

	@Override
	public void paintComponent(Graphics init) {
		// System.out.println("Repainting");
		Graphics2D g = (Graphics2D) init.create();
		g.setColor(Color.white);
		g.clearRect(0, 0, getWidth(), getHeight());
		grid.render(g, getWidth(), getHeight());
		g.setColor(Color.black);
		g.drawString(
				String.format(
						"Click to create life, [DEL] or [BACKSP] to clear, up to step, right to play, [R] for random fill. status=%s",
						(grid.isPaused() && !play) ? "Paused" : "Running"), 10,
				getHeight() - 20);
		g.dispose();
	}

	public static void main(String... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final Life life = new Life();
				JFrame frame = new JFrame("Life");
				frame.setLayout(new BorderLayout());
				frame.add(life);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						life.requestFocus();
					}
				});
			}
		});
	}
}
