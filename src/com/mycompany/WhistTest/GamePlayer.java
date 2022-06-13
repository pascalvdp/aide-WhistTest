package com.mycompany.WhistTest;
import android.content.*;
import java.util.*;
import android.widget.*;
import android.os.*;

public class GamePlayer
{
	private final Context mCtx;//enkel nodig voor toast
	private CardSet deckOfCards = new CardSet();
	//Card lastCard = new Card(0, R.drawable.ic_launcher, "", "");
	public ArrayList<Card>[] playerDeck = new ArrayList[5];//0 tot 4, zie verder naar constructor
	public ArrayList<Card>[] waitingDeck = new ArrayList[2];
	public ArrayList<Card> gameDeck = new ArrayList<Card>();
	public ArrayList<Card> last4CardDeck = new ArrayList<Card>();
	public int startPlayer=1;
	public int oldStartPlayer=0;
	public int player=1;//1
	public int trumpCategory=0;
	public int dealer=4;//4
	public boolean onToast=true;
	public ArrayList<Card> checkDeck = new ArrayList<Card>();
	
//	ArrayList<String> myArrayList=new ArrayList<String>();// misschien kunnen we dit gebruiken??????
//	String[] myArray = myArrayList.toArray(new String[myArrayList.size()]);
	////////

	Random generator;
	int rndm;

	public GamePlayer(Context context){
		mCtx=context;
		generator=new Random();
		for(int a=0;a<=4;a++){//0 mag misschien 1 worden
			playerDeck[a]=new ArrayList<Card>();
		}
		waitingDeck[0]=new ArrayList<Card>();
		waitingDeck[1]=new ArrayList<Card>();
		//scrambleDeck(deckOfCards.getDeck());
		generatePlayersDeck();
		//sortingCards();
	}

	public int getDealer(){
		return dealer;
	}

	public void setDealer(){
		dealer+=1;
		if (dealer==5) dealer=1;
		setStartPlayer();
		setPlayer(startPlayer);
	}

	public void setDealer(int dealer){
		this.dealer = dealer;
		setStartPlayer();
		setPlayer(startPlayer);
	}

	private void setStartPlayer(){
		startPlayer = dealer + 1;
		if (startPlayer==5) startPlayer = 1;
	}

	public void setStartPlayer(int startPlayer){
		this.startPlayer = startPlayer;
	}

	public void setOldStartPlayer(){
		this.oldStartPlayer = startPlayer;
	}
	
	public int getStartPlayer(){
		return startPlayer;
	}

	public void setPlayer(){
		player+=1;
		if (player==5) player=1;
	}

	public void setPlayer(int player){
		this.player = player;
	}

	public int getPlayer(){
		return player;
	}

	public void setTrumpCategory(){
		trumpCategory+=1;
		if (trumpCategory==5) trumpCategory=1;
	}

	public void setTrumpCategory(int trumpCategory){
		this.trumpCategory=trumpCategory;
	}

	public int getTrumpCategory(){
		return trumpCategory;
	}

	public void setDeck(ArrayList<Card> deck, ArrayList<Card> deckSaved){
		deck.clear();
		deck.addAll(deckSaved);
	}
	
	public void setCheckDeck(){
		checkDeck.clear();
		for(int a=1;a<=4;a++){
			for(int b=0;b<playerDeck[a].size();b++){
				playerDeck[a].get(b).player=a;
			}	
			checkDeck.addAll(playerDeck[a]);
		}
		for(int b=0;b<gameDeck.size();b++){
			gameDeck.get(b).player=0;
		}	
		for(int b=0;b<last4CardDeck.size();b++){
			last4CardDeck.get(b).player=0;
		}	
		checkDeck.addAll(gameDeck);
		checkDeck.addAll(last4CardDeck);
	}

	public void swapDecks(ArrayList<Card> deck1, ArrayList<Card> deck2){
		ArrayList<Card> deckTemp=new ArrayList<Card>();
		deckTemp.addAll(deck1);
		setDeck(deck1,deck2);
		setDeck(deck2,deckTemp);
	}	

	public void buttonScrambleDeck(){
			scrambleDeck(deckOfCards.getDeck());
			generatePlayersDeck();
			sortingCards();
	}

	public void generatePlayersDeck(){
		for(int a=1;a<=4;a++){playerDeck[a].clear();}
		int a=1;
		for (int i=0; i<=51; i++) {
			playerDeck[a].add(deckOfCards.getDeck().get(i));
			if (i==12){a++;}
			if (i==25){a++;}
			if (i==38){a++;}
		}
	}

	public void scrambleDeck(ArrayList<Card> l){
		//generator = new Random();
		for (int i=0; i<l.size(); i++){
			Card temp = l.get(i);
			rndm = generator.nextInt(l.size()-i) + i;
			l.set(i, l.get(rndm));
			l.set(rndm, temp);
		}
	}
	
	public int random(int i){//bvb i=2 dan keuze=1 of 2, i=0=error
		rndm = generator.nextInt(i)+1;
		return rndm;
	}
	
	public void liftDeck(ArrayList<Card> deck){
		ArrayList<Card> deck2 = new ArrayList<Card>();
		int rand=random(deck.size()-1);//max.51
		deck2.addAll(deck.subList(rand,deck.size()));// van 1 tot max.51
		deck.subList(rand,deck.size()).clear();
		deck2.addAll(deck);
		deck.clear();
		deck.addAll(deck2);
	}

	public void sortingCards(){
		for(int a=1;a<=4;a++){
			sortingDeck(playerDeck[a],true);
		}		
	}
	
	public void sortingDeck(ArrayList<Card> deck, boolean fromLowToHigh){
		for(int b=1;b<deck.size();b++){//laatste kaart moet niet meer gecontroleerd worden
			for(int c=b+1;c<deck.size()+1;c++){
				if(fromLowToHigh){
					if(deck.get(b-1).getSortingValue() > deck.get(c-1).getSortingValue()){
						swapCards(deck,b,c);b-=1;break;
					}
				}else{
					if(deck.get(b-1).getSortingValue() < deck.get(c-1).getSortingValue()){
						swapCards(deck,b,c);b-=1;break;
					}
				}	
			}	
		}	
	}

	public void swapCards(ArrayList<Card> l,int card1,int card2){
		Card temp = l.get(card1-1);
		l.set(card1-1, l.get(card2-1));
		l.set(card2-1, temp);
	}
	
	public ArrayList<Card> getPlayerDeckCategory(int player,int category){
		ArrayList<Card> list=new ArrayList<Card>();
		for(int a=0;a<playerDeck[player].size();a++){
			if(playerDeck[player].get(a).category==category){
				list.add(playerDeck[player].get(a));
			}
		}
		return list;
	}
	
	public ArrayList<Card> getPlayerDeckValue(int player,int value){
		ArrayList<Card> list=new ArrayList<Card>();
		for(int a=0;a<playerDeck[player].size();a++){
			if(playerDeck[player].get(a).value==value){
				list.add(playerDeck[player].get(a));
			}
		}	
		return list;
	}
	
	public int getTotalValue(int category){//enkel gebruikt bij misere en openmisere
		int totalValue=0;
		ArrayList<Card> deck=new ArrayList<Card>();
		deck=getPlayerDeckCategory(player, category);
		for(int a=0;a<deck.size();a++){totalValue+=deck.get(a).value;}
		return totalValue;
	}

	public int getTotalValue(int player, int category){//enkel gebruikt bij misere en openmisere
		int totalValue=0;
		ArrayList<Card> deck=new ArrayList<Card>();
		deck=getPlayerDeckCategory(player, category);
		for(int a=0;a<deck.size();a++){totalValue+=deck.get(a).value;}
		return totalValue;
	}
	
	public void toast(String msg){
		if(onToast){
        	Toast.makeText(mCtx, msg, Toast.LENGTH_SHORT).show();
		}
    } 
}
