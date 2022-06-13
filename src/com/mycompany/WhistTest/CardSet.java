package com.mycompany.WhistTest;
import java.util.*;

public class CardSet
{
	Card hearts2 = new Card(2, R.drawable.hearts2, 1);
	Card hearts3 = new Card(3, R.drawable.hearts3, 1);
	Card hearts4 = new Card(4, R.drawable.hearts4, 1);
	Card hearts5 = new Card(5, R.drawable.hearts5, 1);
	Card hearts6 = new Card(6, R.drawable.hearts6, 1);
	Card hearts7 = new Card(7, R.drawable.hearts7, 1);
	Card hearts8 = new Card(8, R.drawable.hearts8, 1);
	Card hearts9 = new Card(9, R.drawable.hearts9, 1);
	Card hearts10 = new Card(10, R.drawable.hearts10, 1);
	Card heartsj = new Card(11, R.drawable.heartsj, 1);
	Card heartsq = new Card(12, R.drawable.heartsq, 1);
	Card heartsk = new Card(13, R.drawable.heartsk, 1);
	Card heartsa = new Card(14, R.drawable.heartsa, 1);
	
	Card diamonds2 = new Card(2, R.drawable.diamonds2, 3);
	Card diamonds3 = new Card(3, R.drawable.diamonds3, 3);
	Card diamonds4 = new Card(4, R.drawable.diamonds4, 3);
	Card diamonds5 = new Card(5, R.drawable.diamonds5, 3);
	Card diamonds6 = new Card(6, R.drawable.diamonds6, 3);
	Card diamonds7 = new Card(7, R.drawable.diamonds7, 3);
	Card diamonds8 = new Card(8, R.drawable.diamonds8, 3);
	Card diamonds9 = new Card(9, R.drawable.diamonds9, 3);
	Card diamonds10 = new Card(10, R.drawable.diamonds10, 3);
	Card diamondsj = new Card(11, R.drawable.diamondsj, 3);
	Card diamondsq = new Card(12, R.drawable.diamondsq, 3);
	Card diamondsk = new Card(13, R.drawable.diamondsk, 3);
	Card diamondsa = new Card(14, R.drawable.diamondsa, 3);
	
	Card clubs2 = new Card(2, R.drawable.clubs2, 2);
	Card clubs3 = new Card(3, R.drawable.clubs3, 2);
	Card clubs4 = new Card(4, R.drawable.clubs4, 2);
	Card clubs5 = new Card(5, R.drawable.clubs5, 2);
	Card clubs6 = new Card(6, R.drawable.clubs6, 2);
	Card clubs7 = new Card(7, R.drawable.clubs7, 2);
	Card clubs8 = new Card(8, R.drawable.clubs8, 2);
	Card clubs9 = new Card(9, R.drawable.clubs9, 2);
	Card clubs10 = new Card(10, R.drawable.clubs10, 2);
	Card clubsj = new Card(11, R.drawable.clubsj, 2);
	Card clubsq = new Card(12, R.drawable.clubsq, 2);
	Card clubsk = new Card(13, R.drawable.clubsk, 2);
	Card clubsa = new Card(14, R.drawable.clubsa, 2);

	Card spades2 = new Card(2, R.drawable.spades2, 4);
	Card spades3 = new Card(3, R.drawable.spades3, 4);
	Card spades4 = new Card(4, R.drawable.spades4, 4);
	Card spades5 = new Card(5, R.drawable.spades5, 4);
	Card spades6 = new Card(6, R.drawable.spades6, 4);
	Card spades7 = new Card(7, R.drawable.spades7, 4);
	Card spades8 = new Card(8, R.drawable.spades8, 4);
	Card spades9 = new Card(9, R.drawable.spades9, 4);
	Card spades10 = new Card(10, R.drawable.spades10, 4);
	Card spadesj = new Card(11, R.drawable.spadesj, 4);
	Card spadesq = new Card(12, R.drawable.spadesq, 4);
	Card spadesk = new Card(13, R.drawable.spadesk, 4);
	Card spadesa = new Card(14, R.drawable.spadesa, 4);

	ArrayList<Card> deck = new ArrayList<Card>();
	public ArrayList<Card> getDeck() {
		return deck;
	}

	public CardSet(){
		//Add all the cards to the deck
		deck.add(hearts2);
		deck.add(hearts3);
		deck.add(hearts4);
		deck.add(hearts5);
		deck.add(hearts6);
		deck.add(hearts7);
		deck.add(hearts8);
		deck.add(hearts9);
		deck.add(hearts10);
		deck.add(heartsj);
		deck.add(heartsq);
		deck.add(heartsk);
		deck.add(heartsa);
		
		deck.add(diamonds2);
		deck.add(diamonds3);
		deck.add(diamonds4);
		deck.add(diamonds5);
		deck.add(diamonds6);
		deck.add(diamonds7);
		deck.add(diamonds8);
		deck.add(diamonds9);
		deck.add(diamonds10);
		deck.add(diamondsj);
		deck.add(diamondsq);
		deck.add(diamondsk);
		deck.add(diamondsa);
		
		deck.add(clubs2);
		deck.add(clubs3);
		deck.add(clubs4);
		deck.add(clubs5);
		deck.add(clubs6);
		deck.add(clubs7);
		deck.add(clubs8);
		deck.add(clubs9);
		deck.add(clubs10);
		deck.add(clubsj);
		deck.add(clubsq);
		deck.add(clubsk);
		deck.add(clubsa);

		deck.add(spades2);
		deck.add(spades3);
		deck.add(spades4);
		deck.add(spades5);
		deck.add(spades6);
		deck.add(spades7);
		deck.add(spades8);
		deck.add(spades9);
		deck.add(spades10);
		deck.add(spadesj);
		deck.add(spadesq);
		deck.add(spadesk);
		deck.add(spadesa);
	}
}
