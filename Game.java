package com.company;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Game
{
	public static ArrayList<Account> accountList = new ArrayList<>();
	public static HashMap<CellEnum, ArrayList<String>> dictionary = new HashMap<>();
	public static Account currentAccount;
	private static Game object = null;

	private Game()
	{
	}

	public static void getInstance()
	{
		if (object == null)
			object = new Game();
	}


	public static void run() throws InvalidCommandException
	{

		JSONParse();
		Grid.initializeMap();

		Scanner scanner = new Scanner(System.in);
		firstPage text = new firstPage();

		System.out.println();
		System.out.println();

		String direction;
		int amIDead = 0;

		while (true)
		{
			System.out.println();
			Grid.printMap();

			direction = scanner.next();

			// taking the next move from System.in
			switch (direction)
			{
				case "E":
					Grid.goEast();
					break;
				case "W":
					Grid.goWest();
					break;
				case "N":
					Grid.goNorth();
					break;
				case "S":
					Grid.goSouth();
					break;
				default:
					try
					{
						throw new InvalidCommandException();
					}
					catch (InvalidCommandException exception)
					{
						System.out.println("Wrong command! Type another command, please.");
						continue;
					}
			}

			// displaying a story in case the cell is not visited
			if (!Grid.getCurrentCell().isVisited)
			{
				Random random = new Random();
				int storyIndex = random.nextInt(dictionary.get(Grid.getCurrentCell().cellType).size());
				ArrayList<String> array = dictionary.get(Grid.getCurrentCell().cellType);
				System.out.println(array.get(storyIndex));
				Grid.getMap().get(Grid.getCurrentCell().x).get(Grid.getCurrentCell().y).isVisited = true;
			}

			// if you bump into a shop you can either buy a potion or go to another cell
			if (Grid.getCurrentCell().cellType.equals(CellEnum.SHOP))
			{
				Shop shop;
				if (Grid.getMap().get(Grid.getCurrentCell().x).get(Grid.getCurrentCell().y).object == null)
				{
					shop = new Shop();
					Grid.getMap().get(Grid.getCurrentCell().x).get(Grid.getCurrentCell().y).object = shop;
				}
				else
				{
					shop = (Shop) Grid.getMap().get(Grid.getCurrentCell().x).get(Grid.getCurrentCell().y).object;
				}
				System.out.println("Do you want to buy a potion?");
				String decision = scanner.next();
				if (decision.equals("Y"))
				{
					System.out.println("Choose the potion you want to buy.");
					for (int i = 0; i < shop.potionList.size(); i++)
					{
						System.out.println(i + " : " + shop.potionList.get(i).toString());
					}
					int index = scanner.nextInt();
					if (shop.potionList.get(index) != null)
					{
						if (Grid.getCurrentCharacter().canBeBought(shop.potionList.get(index)))
						{
							Grid.getCurrentCharacter().buyPotion(shop.getPotion(index));
							System.out.println("You have bought a potion! Your current inventory contains:");
							System.out.println("Currency: " + Grid.getCurrentCharacter().inventory.currency);
							for (int j = 0; j < Grid.getCurrentCharacter().inventory.potionsList.size(); j++)
							{
								System.out.println(Grid.getCurrentCharacter().inventory.potionsList.get(j).toString());
							}
							System.out.println();
						}
						else
							System.out.println("You don't have enough money or your inventory is full!");
					}
					else
						System.out.println("There is no stock left in the shop!");
				}
			}

			// if you bump into an enemy you can fight him or run away
			if (Grid.getCurrentCell().cellType.equals(CellEnum.ENEMY))
			{
				int index;
				String decision;
				// 0 for the character and 1 for the enemy
				int turn = 0;
				Enemy enemy;
				if (Grid.getMap().get(Grid.getCurrentCell().x).get(Grid.getCurrentCell().y).object == null)
				{
					enemy = new Enemy();
					Grid.getMap().get(Grid.getCurrentCell().x).get(Grid.getCurrentCell().y).object = enemy;
				}
				else
				{
					enemy = (Enemy) Grid.getMap().get(Grid.getCurrentCell().x).get(Grid.getCurrentCell().y).object;
				}

				if (enemy.currentLife < 0)
				{
					System.out.println("Here lies a dead enemy.");
					continue;
				}

				System.out.println("Do you want to fight the enemy?");
				System.out.println("Press Y if you do or N if you don't feel like dying.");
				decision = scanner.next();
				if (decision.equals("Y"))
				{
					while (Grid.getCurrentCharacter().currentLife > 0 || enemy.currentLife > 0)
					{
						if (turn == 0)
						{
							System.out.println("What attack do you want to choose?");
							System.out.println("Press 1 if you want to punch the enemy, 2 if you want to use a spell or 3 if you want to drink a potion.");
							index = scanner.nextInt();
							if (index == 1)
							{
								int damage = Grid.getCurrentCharacter().dealDamage();
								if (enemy.receiveDamage(damage) == 0)
								{
									System.out.println("The enemy blocked your attack");
									System.out.println();
									System.out.println("The enemy has the following vital signs:");
									System.out.println("HP: " + enemy.currentLife);
									System.out.println("Mana: " + enemy.currentMana);
									System.out.println();
								}
								else
								{
									if (enemy.currentLife < 0)
									{
										Grid.getCurrentCharacter().getXp(30);
										System.out.println("You killed the enemy! Good job!");
										System.out.println();
										System.out.println("Your statuses are:");
										System.out.println("HP: " + Grid.getCurrentCharacter().currentLife);
										System.out.println("Mana: " + Grid.getCurrentCharacter().currentMana);
										System.out.println("Level: " + Grid.getCurrentCharacter().currentLevel);
										System.out.println("XP: " + Grid.getCurrentCharacter().currentXp);
										System.out.println();
										break;

									}
									else
									{
										System.out.println();
										System.out.println("The enemy has the following vital signs:");
										System.out.println("HP: " + enemy.currentLife);
										System.out.println("Mana: " + enemy.currentMana);
										System.out.println();
									}
								}

							}
							else if (index == 2)
							{
								System.out.println();
								System.out.println("Your spell list is the following. Choose a spell to attack the enemy with.");
								for (int i = 0; i < Grid.getCurrentCharacter().spellList.size(); i++)
								{
									System.out.println(i + ". " + Grid.getCurrentCharacter().spellList.get(i).getClass().getSimpleName());
								}
								System.out.println();
								System.out.println("Be careful! The enemy has the following protections");
								System.out.println("->Earth: " + enemy.earthProtection);
								System.out.println("---->Fire: " + enemy.fireProtection);
								System.out.println("->Ice: " + enemy.iceProtection);
								System.out.println();
								index = scanner.nextInt();
								if ((Grid.getCurrentCharacter().hasManaForSpell(Grid.getCurrentCharacter().spellList.get(index))))
								{
									if (enemy.accept(Grid.getCurrentCharacter().spellList.get(index)))
									{
										Grid.getCurrentCharacter().currentMana -= Grid.getCurrentCharacter().spellList.get(index).manaCost;
										if (enemy.currentLife < 0)
										{
											Random random = new Random();
											Grid.getCurrentCharacter().inventory.currency += random.nextInt(20) + 20;
											System.out.println("You killed the enemy. Good job!");
											System.out.println();
											System.out.println("Your statuses are:");
											System.out.println("HP: " + Grid.getCurrentCharacter().currentLife);
											System.out.println("Mana: " + Grid.getCurrentCharacter().currentMana);
											System.out.println("Level: " + Grid.getCurrentCharacter().currentLevel);
											System.out.println("XP: " + Grid.getCurrentCharacter().currentXp);
											System.out.println();
											break;
										}
										else
										{
											System.out.println("The enemy has the following vital signs:");
											System.out.println("HP: " + enemy.currentLife);
											System.out.println("Mana: " + enemy.currentMana);
											System.out.println();
										}
									}
								}
								else
								{
									System.out.println("You do not have enough mana to use the spell.");
								}

							}
							else
							{
								if (Grid.getCurrentCharacter().inventory.potionsList.size() != 0)
								{
									System.out.println("Choose the potion you want to drink.");
									for (int i = 0; i < Grid.getCurrentCharacter().inventory.potionsList.size(); i++)
									{
										System.out.println(i + ". " + Grid.getCurrentCharacter().inventory.potionsList.get(i).getClass().getSimpleName());
									}
									index = scanner.nextInt();
									Potion potion = Grid.getCurrentCharacter().inventory.potionsList.get(index);
									Grid.getCurrentCharacter().inventory.removePotion(potion);
									if (potion.getClass().getSimpleName().contains("HealthPotion"))
									{
										Grid.getCurrentCharacter().lifeRegen(potion.getRegenValue());
										System.out.println("You used a health potion!");
									}
									else
									{
										Grid.getCurrentCharacter().manaRegen(potion.getRegenValue());
										System.out.println("You used a mana potion!");
									}
									System.out.println("Your statuses are:");
									System.out.println("HP: " + Grid.getCurrentCharacter().currentLife);
									System.out.println("Mana: " + Grid.getCurrentCharacter().currentMana);
									System.out.println();
								}
								else
								{
									System.out.println("You do not have potions in your inventory!");
								}
							}

							turn = 1;
						}
						if (turn == 1)
						{
							Grid.getCurrentCharacter().receiveDamage(enemy.dealDamage());
							System.out.println("Your statuses are:");
							if (Grid.getCurrentCharacter().currentLife < 0)
							{
								System.out.println("HP: 0");
								System.out.println("You died!");
								System.out.println();
								amIDead = 1;
								break;

							}
							else
							{
								System.out.println("HP: " + Grid.getCurrentCharacter().currentLife);
								System.out.println("Mana: " + Grid.getCurrentCharacter().currentMana);
								System.out.println();
							}
							turn = 0;
						}
					}
				}
				else
				{
					continue;
				}
			}

			// if you reach the finish cell, quit the game and save the progress
			if (Grid.getCurrentCell().cellType.equals(CellEnum.FINISH) || amIDead == 1)
			{
				writeProgess("src\\com\\company\\test.json");
				lastPage lastPage = new lastPage();
				break;
			}

		}
	}

	public static void JSONParse()
	{
		JSONParser parser = new JSONParser();

		try (FileReader fileReader = new FileReader("src\\com\\company\\stories.json"))
		{

			Object obj = parser.parse(fileReader);
			JSONArray array = (JSONArray) obj;
			array.forEach(objectInJSON -> parseStories((JSONObject) objectInJSON));
		}

		catch (IOException | ParseException exception)
		{
			exception.printStackTrace();
		}


		try (FileReader fileReader = new FileReader("src\\com\\company\\test.json"))
		{

			Object obj = parser.parse(fileReader);
			JSONArray array = (JSONArray) obj;
			array.forEach(objectInJSON -> parseAccounts((JSONObject) objectInJSON));
		}

		catch (IOException | ParseException exception)
		{
			exception.printStackTrace();
		}
	}

	public static void parseStories(JSONObject story)
	{
		String type = (String) story.get("type");
		String value = (String) story.get("value");

		if (type.equals("EMPTY"))
		{
			ArrayList<String> items = dictionary.get(CellEnum.EMPTY);
			if (items == null)
			{
				items = new ArrayList<>();
				items.add(value);
				dictionary.put(CellEnum.EMPTY, items);
			}
			else
			{
				if (!items.contains(value))
					items.add(value);
			}
		}
		if (type.equals("SHOP"))
		{
			ArrayList<String> items = dictionary.get(CellEnum.SHOP);
			if (items == null)
			{
				items = new ArrayList<>();
				items.add(value);
				dictionary.put(CellEnum.SHOP, items);
			}
			else
			{
				if (!items.contains(value))
					items.add(value);
			}
		}
		if (type.equals("ENEMY"))
		{
			ArrayList<String> items = dictionary.get(CellEnum.ENEMY);
			if (items == null)
			{
				items = new ArrayList<>();
				items.add(value);
				dictionary.put(CellEnum.ENEMY, items);
			}
			else
			{
				if (!items.contains(value))
					items.add(value);
			}
		}
		if (type.equals("FINISH"))
		{
			ArrayList<String> items = dictionary.get(CellEnum.FINISH);
			if (items == null)
			{
				items = new ArrayList<>();
				items.add(value);
				dictionary.put(CellEnum.FINISH, items);
			}
			else
			{
				if (!items.contains(value))
					items.add(value);
			}
		}
	}

	public static void parseAccounts(JSONObject account)
	{

		JSONObject credentials = (JSONObject) account.get("credentials");
		String email = (String) credentials.get("email");
		String password = (String) credentials.get("password");
		Credentials accountCredentials = new Credentials();
		accountCredentials.setEmail(email);
		accountCredentials.setPassword(password);

		String name = (String) account.get("name");
		String country = (String) account.get("country");


		JSONArray favoriteGames = (JSONArray) account.get("favorite_games");
		ArrayList<String> preferedGames = (ArrayList<String>) favoriteGames;

		Account.Information accountInformation = new Account.Information.InformationBuilder(name, country)
				.setPlayerCredentials(accountCredentials).setPreferedGames(preferedGames).build();

		String mapsCompleted = (String) account.get("maps_completed");
		Integer gamesCompleted = Integer.parseInt(mapsCompleted);

		JSONArray characters = (JSONArray) account.get("characters");
		ArrayList<JSONObject> tobeAddedInAccount = (ArrayList<JSONObject>) characters;
		ArrayList<Character> accountCharacters = new ArrayList<>();
		for (JSONObject currentCharacter : tobeAddedInAccount)
		{
			String characterName = (String) currentCharacter.get("name");
			String profession = (String) currentCharacter.get("profession");
			String level = (String) currentCharacter.get("level");
			Integer charLevel = Integer.parseInt(level);
			Long experience = (Long) currentCharacter.get("experience");
			int charExperience = experience.intValue();

			accountCharacters.add(CharacterFactory.getNewCharacter(profession, characterName, charExperience, charLevel));
		}

		accountList.add(new Account(accountInformation, accountCharacters, gamesCompleted));
	}

	public static void writeProgess(String path)
	{
		JSONArray array = new JSONArray();
		for (int i = 0; i < accountList.size(); i++)
		{
			Account currentAccount = accountList.get(i);
			JSONObject account = new JSONObject();

			JSONObject credentials = new JSONObject();
			credentials.put("email", currentAccount.information.getPlayerCredentials().getEmail());
			credentials.put("password", currentAccount.information.getPlayerCredentials().getPassword());
			account.put("credentials", credentials);

			account.put("name", currentAccount.information.getName());
			account.put("country", currentAccount.information.getCountry());

			JSONArray favorite = new JSONArray();
			for (int j = 0; j < currentAccount.information.getPreferedGames().size(); j++)
			{
				favorite.add(currentAccount.information.getPreferedGames().get(j));
			}
			account.put("favorite_games", favorite);

			account.put("maps_completed", currentAccount.noGamesPlayed.toString());

			JSONArray characters = new JSONArray();
			for (int j = 0; j < currentAccount.characters.size(); j++)
			{
				JSONObject currentCharacter = new JSONObject();
				currentCharacter.put("name", currentAccount.characters.get(j).name);
				currentCharacter.put("profession", currentAccount.characters.get(j).getClass().getSimpleName());
				currentCharacter.put("level", currentAccount.characters.get(j).currentLevel.toString());
				currentCharacter.put("experience", currentAccount.characters.get(j).currentXp);

				characters.add(currentCharacter);
			}
			account.put("characters", characters);
			array.add(account);

		}

		try (FileWriter file = new FileWriter(path))
		{

			file.write(array.toJSONString());

		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}

}