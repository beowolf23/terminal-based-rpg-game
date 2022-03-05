package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class lastPage
{
	JFrame frame = new JFrame();


	public lastPage()
	{
		frame.setSize(400, 350);
		frame.setLayout(null);

		JLabel gameOver = new JLabel("GAME OVER");
		gameOver.setVisible(true);
		gameOver.setSize(200, 20);
		gameOver.setLocation(175, 35);
		JLabel descriptor = new JLabel("Your stats were:");
		descriptor.setSize(200, 20);
		descriptor.setLocation(160, 65);
		gameOver.setVisible(true);
		descriptor.setVisible(true);

		JLabel currentLevel = new JLabel("Level: " + Grid.getCurrentCharacter().currentLevel.toString());
		currentLevel.setVisible(true);
		currentLevel.setSize(200, 20);
		currentLevel.setLocation(160, 95);

		JLabel currentXp = new JLabel("Experience: " + Grid.getCurrentCharacter().currentXp.toString());
		currentXp.setVisible(true);
		currentXp.setSize(200, 20);
		currentXp.setLocation(160, 115);
		JLabel currency = new JLabel("Currency: " + Grid.getCurrentCharacter().inventory.currency.toString());
		currency.setVisible(true);
		currency.setSize(200, 20);
		currency.setLocation(160, 135);

		frame.add(gameOver);
		frame.add(descriptor);
		frame.add(currency);
		frame.add(currentXp);
		frame.add(currentLevel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
