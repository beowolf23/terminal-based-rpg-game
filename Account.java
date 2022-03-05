package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Account
{
	public Information information;
	public ArrayList<Character> characters;
	public Integer noGamesPlayed;

	public Account()
	{

	}

	public Account(Information information, ArrayList<Character> characters, int noGamesPlayed)
	{
		this.information = information;
		this.characters = characters;
		this.noGamesPlayed = noGamesPlayed;
	}

	public ArrayList<Character> getCharacters()
	{
		return characters;
	}

	@Override
	public String toString()
	{
		return "Account{" +
				"information=" + information +
				", characters=" + characters +
				", noGamesPlayed=" + noGamesPlayed +
				'}';
	}

	static class Information
	{
		private String name;
		private String country;
		private Credentials playerCredentials;
		private ArrayList<String> preferedGames;

		private Information(InformationBuilder builder)
		{
			this.country = builder.country;
			this.name = builder.name;
			this.playerCredentials = builder.playerCredentials;
			this.preferedGames = builder.preferedGames;
		}

		public String getName()
		{
			return name;
		}

		public String getCountry()
		{
			return country;
		}

		public ArrayList<String> getPreferedGames()
		{
			return preferedGames;
		}

		public Credentials getPlayerCredentials()
		{
			return playerCredentials;
		}

		public static class InformationBuilder
		{

			private final String name;
			private final String country;
			private Credentials playerCredentials;
			private ArrayList<String> preferedGames;

			public InformationBuilder(String name, String country)
			{
				this.name = name;
				this.country = country;
			}

			public InformationBuilder setPlayerCredentials(Credentials playerCredentials)
			{
				this.playerCredentials = playerCredentials;
				return this;
			}

			public InformationBuilder setPreferedGames(ArrayList<String> preferedGames)
			{
				this.preferedGames = preferedGames;
				Collections.sort(this.preferedGames);
				return this;
			}

			public Information build()
			{
				return new Information(this);
			}

		}

	}

}
