package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class firstPage implements ActionListener
{
	String getMail, getPass;
	JFrame frame = new JFrame();
	JTextField mail = new JTextField();
	JTextField password = new JTextField();
	JButton submit = new JButton("submit");
	JLabel n1 = new JLabel("Mail:");
	JLabel n2 = new JLabel("Password:");
	JLabel descriptor = new JLabel("Enter your mail and password below.");
	JLabel title = new JLabel("WORLD OF MARCELINO");
	JComboBox<Character> characters = new JComboBox<>();
	JButton characterAccept = new JButton("accept");

	JTextField newCharacter = new JTextField();

	JLabel newCharacterDescriptor = new JLabel("Do you want to create a new character?");
	JTextField newCharacterName = new JTextField();
	JButton newCharacterButton = new JButton("New Character or Not");
	JComboBox<String> newCharacterCombo = new JComboBox<String>();


	public firstPage()
	{
		frame.setSize(800, 400);
		frame.setLayout(null);

		title.setSize(200, 20);
		descriptor.setSize(300, 20);
		mail.setSize(200, 20);
		password.setSize(200, 20);
		submit.setSize(100, 20);
		n1.setSize(50, 20);
		n2.setSize(70, 20);
		characters.setSize(250, 20);
		characterAccept.setSize(100, 20);
		newCharacterCombo.setSize(150, 20);
		newCharacterDescriptor.setSize(300, 20);
		newCharacterButton.setSize(250, 20);

		title.setLocation(350, 10);
		descriptor.setLocation(30, 40);
		mail.setLocation(100, 70);
		password.setLocation(100, 100);
		submit.setLocation(100, 130);
		n1.setLocation(30, 70);
		n2.setLocation(30, 100);
		characters.setLocation(30, 160);
		characterAccept.setLocation(300, 160);

		newCharacterCombo.setLocation(30, 200);
		newCharacterDescriptor.setLocation(30, 170);
		newCharacterButton.setLocation(190, 200);

		newCharacterCombo.setVisible(false);
		newCharacterDescriptor.setVisible(false);
		newCharacterButton.setVisible(false);

		characters.setVisible(false);
		characterAccept.setVisible(false);

		newCharacterCombo.addItem("Yes");
		newCharacterCombo.addItem("No");

		frame.add(title);
		frame.add(descriptor);
		frame.add(mail);
		frame.add(password);
		frame.add(submit);
		frame.add(n1);
		frame.add(n2);
		frame.add(characters);
		frame.add(newCharacterCombo);
		frame.add(characterAccept);
		frame.add(newCharacterDescriptor);
		frame.add(newCharacterButton);

		frame.setResizable(false);

		newCharacterButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int index = newCharacterCombo.getSelectedIndex();
				newCharacterCombo.setVisible(false);
				newCharacterDescriptor.setVisible(false);
				newCharacterButton.setVisible(false);

				if (index == 0)
				{
					JLabel newLabel = new JLabel("Please enter the name and the profession of your new character");
					newLabel.setSize(450, 20);
					newLabel.setLocation(30, 170);
					newLabel.setVisible(true);

					JTextField charName = new JTextField();
					charName.setSize(200, 20);
					charName.setLocation(30, 200);
					charName.setVisible(true);

					JTextField charProfession = new JTextField();
					charProfession.setSize(200, 20);
					charProfession.setLocation(30, 230);
					charProfession.setVisible(true);

					JButton create = new JButton("Create the new character");
					create.setSize(200, 20);
					create.setLocation(30, 270);
					create.setVisible(true);

					create.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							Game.currentAccount.characters.add(CharacterFactory.getNewCharacter(charProfession.getText(),
									charName.getText(), 0, 0));
							for (int j = 0; j < Game.currentAccount.characters.size(); j++)
							{
								characters.addItem(Game.currentAccount.characters.get(j));
							}
							characters.setVisible(true);
							characterAccept.setVisible(true);
							newLabel.setVisible(false);
							charName.setVisible(false);
							charProfession.setVisible(false);
							create.setVisible(false);

						}
					});

					frame.add(newLabel);
					frame.add(charName);
					frame.add(charProfession);
					frame.add(create);
				}
				else
				{
					for (int j = 0; j < Game.currentAccount.characters.size(); j++)
					{
						characters.addItem(Game.currentAccount.characters.get(j));
					}
					characters.setVisible(true);
					characterAccept.setVisible(true);
				}
			}
		});

		characterAccept.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int index = characters.getSelectedIndex();
				Grid.setCurrentCharacter(Game.currentAccount.getCharacters().get(index));
				frame.setVisible(false);
				frame.dispose();
			}
		});


		submit.addActionListener(this);
		frame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == submit)
		{

			getMail = mail.getText();
			getPass = password.getText();


			for (int i = 0; i < Game.accountList.size(); i++)
			{
				Account current = Game.accountList.get(i);
				if (current.information.getPlayerCredentials().getEmail().equals(getMail))
				{
					if (current.information.getPlayerCredentials().getPassword().equals(getPass))
					{
						Game.currentAccount = current;
						break;
					}
				}
			}

			newCharacterCombo.setVisible(true);
			newCharacterDescriptor.setVisible(true);
			newCharacterButton.setVisible(true);
		}
	}

}
