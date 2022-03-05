package com.company;

public class Cell
{
	public int x;
	public int y;
	public boolean isVisited;
	public CellEnum cellType;
	CellElement object;

	public Cell(int xPos, int yPos)
	{
		this.x = xPos;
		this.y = yPos;
		isVisited = false;
		cellType = CellEnum.EMPTY;
	}
}
