package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>>
{
	private static ArrayList<ArrayList<Cell>> map;
	private static Cell currentCell;
	private static Character currentCharacter;
	private static int length;
	private static int width;

	private Grid()
	{

	}

	public static void initializeMap()
	{
		Grid.setLength(10);
		Grid.setWidth(10);
		ArrayList<ArrayList<Cell>> map = Grid.generateMap();
		Grid.setMap(map);
		Grid.setCurrentCell(map.get(0).get(0));

	}

	// print the cuurent map
	public static void printMap()
	{
		for (int i = 0; i < Grid.getWidth(); i++)
		{
			for (int j = 0; j < Grid.getLength(); j++)
			{
				if (!Grid.getMap().get(i).get(j).isVisited)
				{
					System.out.print("? ");
					continue;
				}
				if (Grid.getMap().get(i).get(j).equals(currentCell))
				{
					System.out.print("P ");
					continue;
				}
				if (Grid.getMap().get(i).get(j).cellType.equals(CellEnum.EMPTY))
				{
					System.out.print("N ");
					continue;
				}
				if (Grid.getMap().get(i).get(j).cellType.equals(CellEnum.ENEMY))
				{
					System.out.print("E ");
					continue;
				}
				if (Grid.getMap().get(i).get(j).cellType.equals(CellEnum.SHOP))
				{
					System.out.print("S ");
					continue;
				}
				if (Grid.getMap().get(i).get(j).cellType.equals(CellEnum.FINISH))
				{
					System.out.print("F ");
				}
			}
			System.out.println();
		}
	}

	public static ArrayList<ArrayList<Cell>> getMap()
	{
		return map;
	}

	public static void setMap(ArrayList<ArrayList<Cell>> map)
	{
		Grid.map = map;
	}

	public static int getLength()
	{
		return length;
	}

	public static void setLength(int length)
	{
		Grid.length = length;
	}

	public static int getWidth()
	{
		return width;
	}

	public static void setWidth(int width)
	{
		Grid.width = width;
	}

	public static Cell getCurrentCell()
	{
		return currentCell;
	}

	public static void setCurrentCell(Cell currentCell)
	{
		Grid.currentCell = currentCell;
	}

	public static Character getCurrentCharacter()
	{
		return currentCharacter;
	}

	public static void setCurrentCharacter(Character currentCharacter)
	{
		Grid.currentCharacter = currentCharacter;
	}

	// random map generating method
	public static ArrayList<ArrayList<Cell>> generateMap()
	{
		map = new ArrayList<ArrayList<Cell>>();
		for (int i = 0; i < width; i++)
		{
			ArrayList<Cell> mapLine = new ArrayList<>();
			for (int j = 0; j < length; j++)
			{
				mapLine.add(new Cell(i, j));
			}
			map.add(mapLine);
		}

		int counter = 0;
		Random randomObj = new Random();
		while (counter < Grid.getWidth() * Grid.getLength() / 10 + 1)
		{
			int randomLine = randomObj.nextInt(width);
			int randomCol = randomObj.nextInt(length);
			if (map.get(randomLine).get(randomCol).cellType.equals(CellEnum.EMPTY))
			{
				map.get(randomLine).get(randomCol).cellType = CellEnum.SHOP;
				counter += 1;
			}
		}

		counter = 0;
		while (counter < Grid.getLength() * Grid.getWidth() / 5 + 1)
		{
			int randomLine = randomObj.nextInt(width);
			int randomCol = randomObj.nextInt(length);
			if (map.get(randomLine).get(randomCol).cellType.equals(CellEnum.EMPTY))
			{
				map.get(randomLine).get(randomCol).cellType = CellEnum.ENEMY;
				counter += 1;
			}
		}
		while (true)
		{
			int randomLine = randomObj.nextInt(width);
			int randomCol = randomObj.nextInt(length);
			if (map.get(randomLine).get(randomCol).cellType.equals(CellEnum.EMPTY))
			{
				map.get(randomLine).get(randomCol).cellType = CellEnum.FINISH;
				break;
			}
		}

		return map;
	}

	public static void goEast()
	{
		if ((currentCell.y + 1) < length)
		{
			map.get(currentCell.x).get(currentCell.y).isVisited = true;
			currentCell = map.get(currentCell.x).get(currentCell.y + 1);
			currentCell.cellType = map.get(currentCell.x).get(currentCell.y).cellType;
			if (!currentCell.isVisited)
			{
				Random randomObj = new Random();
				int chance = randomObj.nextInt(5);
				if (chance == 0)
				{
					currentCharacter.inventory.currency += 40;
				}
			}
		}
		else
		{
			System.out.println("Can't go to EAST");
		}
	}

	public static void goWest()
	{
		if ((currentCell.y - 1) >= 0)
		{
			map.get(currentCell.x).get(currentCell.y).isVisited = true;
			currentCell = map.get(currentCell.x).get(currentCell.y - 1);
			currentCell.cellType = map.get(currentCell.x).get(currentCell.y).cellType;
			if (!currentCell.isVisited)
			{
				Random randomObj = new Random();
				int chance = randomObj.nextInt(5);
				if (chance == 0)
				{
					currentCharacter.inventory.currency += 40;
				}
			}
		}
		else
		{
			System.out.println("Can't go to WEST");
		}
	}

	public static void goNorth()
	{
		if ((currentCell.x - 1) >= 0)
		{
			map.get(currentCell.x).get(currentCell.y).isVisited = true;
			currentCell = map.get(currentCell.x - 1).get(currentCell.y);
			currentCell.cellType = map.get(currentCell.x).get(currentCell.y).cellType;
			if (!currentCell.isVisited)
			{
				Random randomObj = new Random();
				int chance = randomObj.nextInt(5);
				if (chance == 0)
				{
					currentCharacter.inventory.currency += 40;
				}
			}
		}
		else
		{
			System.out.println("Can't go to NORTH");
		}
	}

	public static void goSouth()
	{
		if ((currentCell.x + 1) < width)
		{
			map.get(currentCell.x).get(currentCell.y).isVisited = true;
			currentCell = map.get(currentCell.x + 1).get(currentCell.y);
			currentCell.cellType = map.get(currentCell.x).get(currentCell.y).cellType;
			if (!currentCell.isVisited)
			{
				Random randomObj = new Random();
				int chance = randomObj.nextInt(5);
				if (chance == 0)
				{
					currentCharacter.inventory.currency += 40;
				}
			}
		}
		else
		{
			System.out.println("Can't go to SOUTH");
		}
	}

}
