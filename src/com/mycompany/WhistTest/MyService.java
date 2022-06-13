package com.mycompany.WhistTest;
import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;

public class MyService extends IntentService //niet in gebruik
{
	CardsDatabase mDb;
	Game game;
	boolean play;
	String lastSavedName;
	int value, cardReference, category;
	
	public MyService() {
        super("MyService");
    }

	@Override
	public void onCreate()//komt voor onstartcommand
	{
	//	game=new Game(this);
	//	mDb.getParameters("save1",game);
	//	game.setPlayer();
	//	mDb.setParameters("save1",game);
		Toast.makeText(this,"oncreate service",Toast.LENGTH_LONG).show();
		super.onCreate();
	}
	
	@Override
	protected void onHandleIntent(Intent intent)
	{
		
	}
	
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Let it continue running until it is stopped.
		game=new Game(this);
		mDb=new CardsDatabase(this, false);
		mDb.open();
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		play = intent.getBooleanExtra("play",false);
		if (play){
			lastSavedName = intent.getStringExtra("lastSavedName");
			for(int a=0;a<=1;a++){
				for(int b=0;b<intent.getIntExtra(String.valueOf(a) + "waitingDecksize",0);b++){
					value=intent.getIntExtra("waitingDeck" + String.valueOf(a) + String.valueOf(b) + "value",100);
					cardReference=intent.getIntExtra("waitingDeck" + String.valueOf(a) + String.valueOf(b) + "cardReference",100);
					category=intent.getIntExtra("waitingDeck" + String.valueOf(a) + String.valueOf(b) + "category",100);
					Card card = new Card(value,cardReference,category);
					game.waitingDeck[a].add(card);
				}
			}
			for(int b=0;b<intent.getIntExtra("gameDecksize",0);b++){
				value=intent.getIntExtra("gameDeck" + String.valueOf(b) + "value",100);
				cardReference=intent.getIntExtra("gameDeck" + String.valueOf(b) + "cardReference",100);
				category=intent.getIntExtra("gameDeck" + String.valueOf(b) + "category",100);
				Card card = new Card(value,cardReference,category);
				game.gameDeck.add(card);
			}
			for(int b=0;b<intent.getIntExtra("last4CardDecksize",0);b++){
				value=intent.getIntExtra("last4CardDeck" + String.valueOf(b) + "value",100);
				cardReference=intent.getIntExtra("last4CardDeck" + String.valueOf(b) + "cardReference",100);
				category=intent.getIntExtra("last4CardDeck" + String.valueOf(b) + "category",100);
				Card card = new Card(value,cardReference,category);
				game.last4CardDeck.add(card);
			}
			game.maxChoice=intent.getIntExtra("maxChoice",-1);
			game.oldMaxChoice=intent.getIntExtra("oldMaxChoice",-1);
			game.troelPlayer=intent.getIntExtra("troelPlayer",-1);
			game.troelMeePlayer=intent.getIntExtra("troelMeePlayer",-1);
			game.alleen=intent.getBooleanExtra("alleen",false);
			game.dealer=intent.getIntExtra("dealer",-1);
			for(int a=1;a<=4;a++){
				game.playerTrumpCategory[a]=intent.getIntExtra("playerTrumpCategory" + String.valueOf(a),-1);
				game.playerChoice[a]=intent.getIntExtra("playerChoice" + String.valueOf(a),-1);
				game.oldPlayerChoice[a]=intent.getIntExtra("oldPlayerChoice" + String.valueOf(a),-1);
			}
			game.startPlayer=intent.getIntExtra("startPlayer",-1);
			game.oldStartPlayer=intent.getIntExtra("oldStartPlayer",-1);
			game.player=intent.getIntExtra("player",-1);
			game.card=intent.getIntExtra("card",-1);
			for(int a=1;a<=4;a++){
				game.playerDeck[a].clear();//bij new game dan is er al een arraylist(allemaal harten bij 1)
				for(int b=0;b<intent.getIntExtra(String.valueOf(a) + "size",0);b++){
					value=intent.getIntExtra(String.valueOf(a) + String.valueOf(b) + "value",100);
					cardReference=intent.getIntExtra(String.valueOf(a) + String.valueOf(b) + "cardReference",100);
					category=intent.getIntExtra(String.valueOf(a) + String.valueOf(b) + "category",100);
					Card card = new Card(value,cardReference,category);
					game.playerDeck[a].add(card);
				}
			}
			
			mDb.setCards(lastSavedName,"waitingDeck0→play" ,game.waitingDeck[0].iterator()); 
			mDb.setCards(lastSavedName,"waitingDeck1→play" ,game.waitingDeck[1].iterator());
			mDb.setCards(lastSavedName,"gameDeck→play" ,game.gameDeck.iterator());
			mDb.setCards(lastSavedName,"last4CardDeck→play" ,game.last4CardDeck.iterator());
			mDb.setPlay(lastSavedName,game);
			for(int a=1;a<=4;a++){
				mDb.setCards(lastSavedName,String.valueOf(a) + "→play" ,game.playerDeck[a].iterator());
			}	
			//Game ga= new Game(this);//test
			//ga.setDeck(ga.waitingDeck[0],mDb.getCards(lastSavedName, "waitingDeck0→play"));
			Toast.makeText(this, "saving game......", Toast.LENGTH_LONG).show();
			stopService(intent);
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		//hier mDb close of niet,  nog testen
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	}
}
