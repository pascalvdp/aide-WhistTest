package com.mycompany.WhistTest;
//hoogte kaart = 182
//breedte kaart = 260 

//hoogte 32mm breedte 22.5mm
//dit scherm is hdpi, gsm is xhdpi

import android.app.*;
import android.os.*;       //"checkCardsPlayed" 
import android.view.*;
import android.widget.*;     //"supporting multiple screens android" in google dit ingeven=uitleg over verschillende schermen
import android.graphics.*;
import android.view.View.*;
import android.graphics.drawable.*;
import java.util.*;
import android.database.*;
import android.content.*;
import android.preference.*;
import java.text.*;
import android.view.WindowManager.*;
import android.widget.CompoundButton.*;
import android.animation.*;
import android.provider.*;
import android.net.*;
import java.io.*;


public class MainActivity extends Activity
{  
    ImageView[][] iv=new ImageView[5][14]; 
	//ImageView[][] ivShadow=new ImageView[5][14];
	OnClickListener[][] imageClickListener=new OnClickListener[5][14];
	OnClickListener[][] cardImageClickListener=new OnClickListener[5][14];
	int[] posXPlayedCard=new int[5];
	int[] posYPlayedCard=new int[5];
	int screenWidth;
	int screenHeight;
	boolean otherCardIsSelected=false;
	int otherCardPlayer=0;
	int otherCard=-1;
	
	Button mButtonZoekFout,mbuttonDoorgaan;
	Button mButtonDealer,mButtonSaveAsExternalStorage;
	Button mButtonTrumpCategory;
	Button mButtonLoad1,mButtonLoad2,mButtonLoad3,mButtonBack2;
	Button mButtonSave1,mButtonSave2,mButtonSave3;
	Button mButtonToast,mButtonResetPoints,mButtonZoekenNaar,mButtonChangeSearching;
	Button mButtonPlayCard,mButtonSettings,mButtonSave;
	Button mButtonPlayTheCards,mButtonStopPlayTheCards;
	Button mButtonRestart,mButtonChooseAgain;
	Button mButtonChoiceOk;
	Button mButtonChoiceAll;
	Button mButtonChoiceSelf;
	Button mButtonChoiceSelfCategory;
	Button mButtonChoiceAgain;
	
	CheckBox[] mCheckBoxZichtbaarSp = new CheckBox[5];
	boolean[] visibleCardsPlayer = new boolean[5];
	
	CheckBox[] mCheckBoxLock = new CheckBox[5];
	boolean[] lockPlayer = new boolean[5];
	
	TextView mTextView1,mTextView2,mTextView3,mTextView4;
	TextView mTextViewDealer,mTextViewTrumpCategory;
	TextView mTextViewTrump, mTextViewOpenM;
	
	LinearLayout lL, lLChoice, lLRootMain, lLStartMain;
	
	CardsDatabase mDb;
	
	Game game;
	
	SharedPreferences settings;
	String lastSavedName;
	boolean mainMenu,selected,play;
	
	ProgressDialog progressDialog;
	
	private static final int DIALOG_SEARCH = 1;
	
	ListView mListView1;
	
	Button mButtonChangePlayer1, mButtonChangePlayer2, mButtonChangePlayer3, mButtonChangePlayer4, mButtonBack;
	int clickedPlayer=0;//dit gebruiken we enkel voor mButtonChangePlayer1 tot 4
	
	boolean playTheCards=false;
	
	int keuze=1;//enkel gebruikt bij mButtonChangeSearching
	boolean annul;//enkel gebruikt bij mButtonZoekenNaar
	
	Intent mServiceIntent;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		// remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        setContentView(R.layout.main);
		
		setViews();
		
		settings = PreferenceManager.getDefaultSharedPreferences(this);
		loadPreferences();

		if (lastSavedName.compareTo("save4")==0){
			//mDb.close();
			mDb=new CardsDatabase(this, true);
			mDb.open();
		}else{
			mDb=new CardsDatabase(this, false);
			mDb.open();
		}	

		game=new Game(this);
		
		/*settings = PreferenceManager.getDefaultSharedPreferences(this);
		loadPreferences();
		
		if (lastSavedName.compareTo("save4")==0){
			//mDb.close();
			mDb=new CardsDatabase(this, true);
			mDb.open();
		}	*/
		
		screenWidth=getWindowManager().getDefaultDisplay().getWidth();//=1280     gsm=1440
		screenHeight=getWindowManager().getDefaultDisplay().getHeight();//=736         810
		
	/*	Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		screenWidth = size.x;//=1280
		screenHeight = size.y;//=736*/
	
		posXPlayedCard[1]=screenWidth/2-140;//=500
		posYPlayedCard[1]=screenHeight/2-113;//=255
		posXPlayedCard[2]=screenWidth/2-300;//=340
		posYPlayedCard[2]=screenHeight/2-148;//=220
		posXPlayedCard[3]=screenWidth/2-140;//=500
		posYPlayedCard[3]=screenHeight/2-208;//=160  //208
		posXPlayedCard[4]=screenWidth/2+20;//=660
		posYPlayedCard[4]=screenHeight/2-173;//=195
		
		this.getWindow().setBackgroundDrawableResource(R.drawable.green_back_3);//
		
		lLRootMain=findViewById(R.id.rootmain);
		lLStartMain=findViewById(R.id.startmain);
		lL=findViewById(R.id.layout);
		lLChoice=findViewById(R.id.layoutchoice);
		
		mButtonZoekFout=findViewById(R.id.buttonZoekFout);
		mbuttonDoorgaan=findViewById(R.id.buttonDoorgaan);
		mButtonDealer=findViewById(R.id.buttondealer);
		Button mButtonStartGame=findViewById(R.id.buttonstartgame);
		mButtonTrumpCategory=findViewById(R.id.buttontrumpcategory);
		Button mButtonScrambleCards=findViewById(R.id.buttonscramblecards);
		mButtonSave1=findViewById(R.id.buttonsave1);
		mButtonSave2=findViewById(R.id.buttonsave2);
		mButtonSave3=findViewById(R.id.buttonsave3);
		mButtonChangePlayer1=findViewById(R.id.buttonchangeplayer1);
		mButtonChangePlayer2=findViewById(R.id.buttonchangeplayer2);
		mButtonChangePlayer3=findViewById(R.id.buttonchangeplayer3);
		mButtonChangePlayer4=findViewById(R.id.buttonchangeplayer4);
		mTextView1=findViewById(R.id.textView1);
		mTextView2=findViewById(R.id.textView2);
		mTextView3=findViewById(R.id.textView3);
		mTextView4=findViewById(R.id.textView4);
		mTextViewTrump=findViewById(R.id.textViewTrump);
		mTextViewOpenM=findViewById(R.id.textViewOpenM);
		mCheckBoxLock[1] = findViewById(R.id.vastsp1);
		mCheckBoxLock[2] = findViewById(R.id.vastsp2);
		mCheckBoxLock[3] = findViewById(R.id.vastsp3);
		mCheckBoxLock[4] = findViewById(R.id.vastsp4);
		mCheckBoxLock[1].setChecked(lockPlayer[1]);
		mCheckBoxLock[2].setChecked(lockPlayer[2]);
		mCheckBoxLock[3].setChecked(lockPlayer[3]);
		mCheckBoxLock[4].setChecked(lockPlayer[4]);
		
		//linearlayout lLChoice
		mButtonBack=findViewById(R.id.buttonBack);
		mButtonChoiceSelf=findViewById(R.id.buttonchoiceself);
		mButtonChoiceSelfCategory=findViewById(R.id.buttonchoiceselfcategory);
		mTextViewDealer=findViewById(R.id.textViewDealer);
		mTextViewTrumpCategory=findViewById(R.id.textViewTrumpCategory);
		mButtonChoiceAgain=findViewById(R.id.buttonchoiceagain);
		mButtonChoiceOk=findViewById(R.id.buttonchoiceok);
		mButtonChoiceAll=findViewById(R.id.buttonchoiceall);
		
		//zonder layout
		mButtonSaveAsExternalStorage=findViewById(R.id.buttonSaveAsExternalStorage);
		mButtonToast=findViewById(R.id.buttonToast);
		mButtonResetPoints=findViewById(R.id.buttonResetPoints);
		mButtonLoad1=findViewById(R.id.buttonload1);
		mButtonLoad2=findViewById(R.id.buttonload2);
		mButtonLoad3=findViewById(R.id.buttonload3);
		mButtonBack2=findViewById(R.id.buttonBack2);
		mButtonZoekenNaar=findViewById(R.id.buttonZoekenNaar);
		mButtonChangeSearching=findViewById(R.id.buttonChangeSearching);
		mButtonPlayCard=findViewById(R.id.buttonPlayCard);
		mButtonPlayTheCards=findViewById(R.id.buttonPlayTheCards);
		mButtonStopPlayTheCards=findViewById(R.id.buttonStopPlayTheCards);
		mButtonSettings=findViewById(R.id.buttonSettings);
		mButtonRestart=findViewById(R.id.buttonRestart);
		mButtonChooseAgain=findViewById(R.id.buttonChooseAgain);
		mButtonSave=findViewById(R.id.buttonSave);
		
		mServiceIntent = new Intent(this, MyService.class);
		
		mButtonZoekFout.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					String fileStr= "/sdcard/AppProjects/WhistTest/WhistTest.db";
					File file=new File(fileStr);
					if (file.exists()){
						savePreferences("lastSavedName","save4");
						savePreferences("save4"+"_mainMenu",false);
						recreate();
					}else{
						toast("file niet gevonden");
					}
				}
		});
		mbuttonDoorgaan.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(lastSavedName.compareTo("save4")==0){
						mDb.close();
						mDb=new CardsDatabase(MainActivity.this, false);
						mDb.open();
						savePreferences("lastSavedName","save1");
						savePreferences("save1"+"_mainMenu",false);
						savePreferences("save2"+"_mainMenu",false);//dit is enkel nodig als men in save4 is geweest
						savePreferences("save3"+"_mainMenu",false);//dit is enkel nodig als men in save4 is geweest
						recreate();
					}else{
						//mainMenu=false;
						savePreferences(lastSavedName+"_mainMenu",false);
						recreate();
					}
				}
		});
	
		mButtonChangePlayer1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					swapClickedDecks(1);
				}
		});
		mButtonChangePlayer2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					swapClickedDecks(2);
				}
		});
		mButtonChangePlayer3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					swapClickedDecks(3);
				}
		});
		mButtonChangePlayer4.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					swapClickedDecks(4);
				}
		});
		mButtonSaveAsExternalStorage.setOnClickListener(new OnClickListener(){
		
				@Override
				public void onClick(View p1)
				{
					savePreferences("save4"+"_mainMenu",mainMenu);
					savePreferences("save4"+"_selected",selected);
					savePreferences("save4"+"_play",play);
					for(int a=1;a<=4;a++){
						if(a==1)savePreferences("save4"+"_visibleCardsP1",visibleCardsPlayer[a]);
						if(a==2)savePreferences("save4"+"_visibleCardsP2",visibleCardsPlayer[a]);
						if(a==3)savePreferences("save4"+"_visibleCardsP3",visibleCardsPlayer[a]);
						if(a==4)savePreferences("save4"+"_visibleCardsP4",visibleCardsPlayer[a]);
						savePreferences("save4"+"_lockP"+String.valueOf(a),lockPlayer[a]);
					}
					if (lastSavedName.compareTo("save4")==0){
						saveAll("save4");
					}else{
						saveAllExternal("save4");
					}
				}
		});
		mButtonToast.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					if(game.onToast){
						game.onToast=false;
						mButtonToast.setText(" TOAST OFF ");
					}else{
						game.onToast=true;
						mButtonToast.setText(" TOAST ON ");
					}
				}
		});
		mButtonResetPoints.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					for(int a=1;a<=4;a++){
						game.playerPoints[a]=0;
						if(!selected && !play){setTextNameAndPointsPlayers();}
						if(play){setTextPlayPlayers();}
					}	
					mDb.setPoints(lastSavedName, game);
				}
		});
		mButtonLoad1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					if(lastSavedName.compareTo("save4")==0){
						recreate();
					}else{
						savePreferences("lastSavedName","save1");
						recreate();
						//loadAll("save1");
					}	
				}
		});
		mButtonLoad2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					savePreferences("lastSavedName","save2");
					recreate();
				}
		});
		mButtonLoad3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					savePreferences("lastSavedName","save3");
					recreate();
				}
		});
		mButtonBack2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//hier aanpassingen dan ook in onBackPressed
					mainMenu=true;
					savePreferences(lastSavedName+"_mainMenu",true);
					recreate();
				}
		});	
		mButtonZoekenNaar.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					zoekenNaar();
				}
		});
		mButtonChangeSearching.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					boolean ok=false;
					while(!ok){
						keuze++;
						ok=true;
						switch(keuze){
							case game.SOLOSLIM:
								mButtonChangeSearching.setText("SOLOSLIM");
								break;
							case game.SOLO:
								mButtonChangeSearching.setText("SOLO");
								break;
							case game.OPENMISERE:
								mButtonChangeSearching.setText("OPENMISERE");
								break;		
							case game.TROELMEE:
								mButtonChangeSearching.setText("TROELMEE (enkel bij eerste keuze)");
								break;	
							case game.TROEL:
								mButtonChangeSearching.setText("TROEL");
								break;		
							case game.DANSEN12INTROEF:
								mButtonChangeSearching.setText("DANSEN12INTROEF");
								break;		
							case game.DANSEN12:
								mButtonChangeSearching.setText("DANSEN12");
								break;	
							case game.DANSEN11INTROEF:
								mButtonChangeSearching.setText("DANSEN11INTROEF");
								break;		
							case game.DANSEN11:
								mButtonChangeSearching.setText("DANSEN11");
								break;		
							case game.DANSEN10INTROEF:
								mButtonChangeSearching.setText("DANSEN10INTROEF");
								break;		
							case game.DANSEN10:
								mButtonChangeSearching.setText("DANSEN10");
								break;	
							case game.MISERE:
								mButtonChangeSearching.setText("MISERE");
								break;
							case game.DANSEN9INTROEF:
								mButtonChangeSearching.setText("DANSEN9INTROEF");
								break;		
							case game.DANSEN9:
								mButtonChangeSearching.setText("DANSEN9");
								break;	
							case game.MEEGAAN:
								mButtonChangeSearching.setText("MEEGAAN");
								break;
							case game.VRAAG:
								mButtonChangeSearching.setText("VRAAG");
								break;	
							case game.ALLEEN:
								mButtonChangeSearching.setText("ALLEEN (enkel bij laatste speler)");
								break;
							default:
								ok=false;
								if(keuze>game.SOLOSLIM){keuze=0;}
								break;		
						}
					}
				}
		});
		mButtonChangeSearching.setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1)
				{
					keuze=0;
					return false;
				}
		});
		mButtonDealer.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					game.setDealer();
					mButtonDealer.setText("DEALER = " + String.valueOf(game.getDealer()));
				}
		});
		mButtonStartGame.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					setImageClickable(false,0,0);
					startGame();
				}
		});
		mButtonScrambleCards.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					boolean isChecked=false;
					for(int a=1;a<=4;a++){
						if(lockPlayer[a]){isChecked=true;break;}
					}	
					if(isChecked){//indien er minstens 1 vast is
						//hier gaan we waitingDeck[0] eventjes in gebruik nemen
						for(int a=1;a<=4;a++){
							if(!lockPlayer[a]){game.waitingDeck[0].addAll(game.playerDeck[a]);}
						}
						game.liftDeck(game.waitingDeck[0]);
						for(int a=1;a<=4;a++){if(!lockPlayer[a]){game.playerDeck[a].clear();}}
						int z=0,i=0,c=0;
						while(i<game.waitingDeck[0].size()){
							c++;if(c==3){z=5;}else{z=4;}
							for(int a=1;a<=4;a++){
								if(lockPlayer[a]){continue;}
								for(int b=1;b<=z;b++){
									if(i<game.waitingDeck[0].size()){game.playerDeck[a].add(game.waitingDeck[0].get(i));}
									i++;
								}
							}	
						}
						game.waitingDeck[0].clear();
						for(int a=1;a<=4;a++){
							if(!lockPlayer[a]){game.sortingDeck(game.playerDeck[a],true);}
						}		
					}else{
						game.buttonScrambleDeck();
					}
					setCards(0);
					drawCards(0,true);
				}
		});
		mButtonTrumpCategory.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					game.setTrumpCategory();
					mButtonTrumpCategory.setText("TRUMPCATEGORY = " + String.valueOf(game.getTrumpCategory()));
					mTextViewTrump.setText("trump = " + String.valueOf(game.getTrumpCategory()));
				}
		});
		mButtonSave1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					if(lastSavedName.compareTo("save4")==0){
						saveAll("save4");
					}else{
						saveAll("save1");
					}
				}
		}); 
		mButtonSave2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					mainMenu=false;//is enkel nodig bij de eerste keer saven
					savePreferences("save2"+"_mainMenu",false);
					saveAll("save2");
					mButtonLoad2.setVisibility(View.VISIBLE);
				}
		});
		mButtonSave3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					mainMenu=false;//is enkel nodig bij de eerste keer saven
					savePreferences("save3"+"_mainMenu",false);
					saveAll("save3");
					mButtonLoad3.setVisibility(View.VISIBLE);
				}
		});
		//linearlayout lLChoice
		mButtonBack.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					setImageClickable(true,0,0);
					selected=false;
					savePreferences(lastSavedName+"_selected",false);
					lLChoice.setVisibility(View.INVISIBLE);
					lL.setVisibility(View.VISIBLE);
					setTextNameAndPointsPlayers();
					mButtonChoiceOk.setVisibility(View.VISIBLE);
					mButtonChoiceSelf.setVisibility(View.VISIBLE);//extra
					mButtonChoiceAgain.setVisibility(View.VISIBLE);//extra
					mButtonZoekenNaar.setVisibility(View.VISIBLE);
					mButtonChangeSearching.setVisibility(View.VISIBLE);
					game.setPlayer(game.getStartPlayer());
					mButtonChoiceAll.setText("          CHOICE ALL          ");
					game.choiceSelf=-1;
					mButtonChoiceSelf.setText("CHOICE SELF");
					mButtonChoiceSelfCategory.setText(" / ");
				}
		});
		mButtonChoiceSelf.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					int a=game.getPlayer()-1;
					if (a==0){a=4;}
					game.setPlayer(a);
					game.setMaxChoice();
					game.startChoiceSelf();
					if(game.choice==game.DANSEN9 || game.choice==game.DANSEN10 || 
					   game.choice==game.DANSEN11 || game.choice==game.DANSEN12 || 
					   game.choice==game.SOLO){
						if(game.playerTrumpCategory[game.player]==0){game.playerTrumpCategory[game.player]=game.trumpCategory;}
						mButtonChoiceSelfCategory.setText(" " +String.valueOf(game.playerTrumpCategory[game.player]) + " ");
					}else{
						mButtonChoiceSelfCategory.setText(" / ");
					}	 
					mDb.setChoices(lastSavedName,game);
					setTextChoicePlayer();
					game.setPlayer();
					switch(game.choice){
						case game.SOLOSLIM:
							mButtonChoiceSelf.setText("SOLOSLIM");
							break;
						case game.SOLO:
							mButtonChoiceSelf.setText("SOLO");
							break;
						case game.OPENMISERE:
							mButtonChoiceSelf.setText("OPENMISERE");
							break;		
						case game.TROELMEE:
							mButtonChoiceSelf.setText("TROELMEE");
							break;	
						case game.TROEL:
							mButtonChoiceSelf.setText("TROEL");
							break;				
						case game.DANSEN12INTROEF:
							mButtonChoiceSelf.setText("DANSEN12INTROEF");
							break;		
						case game.DANSEN12:
							mButtonChoiceSelf.setText("DANSEN12");
							break;	
						case game.DANSEN11INTROEF:
							mButtonChoiceSelf.setText("DANSEN11INTROEF");
							break;		
						case game.DANSEN11:
							mButtonChoiceSelf.setText("DANSEN11");
							break;		
						case game.DANSEN10INTROEF:
							mButtonChoiceSelf.setText("DANSEN10INTROEF");
							break;		
						case game.DANSEN10:
							mButtonChoiceSelf.setText("DANSEN10");
							break;	
						case game.MISERE:
							mButtonChoiceSelf.setText("MISERE");
							break;
						case game.DANSEN9INTROEF:
							mButtonChoiceSelf.setText("DANSEN9INTROEF");
							break;		
						case game.DANSEN9:
							mButtonChoiceSelf.setText("DANSEN9");
							break;	
						case game.MEEGAAN:
							mButtonChoiceSelf.setText("MEEGAAN");
							break;
						case game.VRAAG:
							mButtonChoiceSelf.setText("VRAAG");
							break;	
						case game.ALLEEN:
							mButtonChoiceSelf.setText("ALLEEN");
							//alleen=false;
							break;
						case game.PAS:
							mButtonChoiceSelf.setText("PAS");
							break;	
						case -1:
							mButtonChoiceSelf.setText("CHOICE SELF");
							break;		
					}
				}
		});
		mButtonChoiceSelf.setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1)//hier wordt ook de setOnClickListener bevestigd
				{
					game.choiceSelf=-1;
					mButtonChoiceSelfCategory.setText(" / ");
					return false;
				}
		});
		mButtonChoiceSelfCategory.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(game.choice==game.DANSEN9 || game.choice==game.DANSEN10 || 
					  game.choice==game.DANSEN11 || game.choice==game.DANSEN12 || 
					  game.choice==game.SOLO){
						int a=game.getPlayer()-1;
						if (a==0){a=4;}
						game.setPlayer(a);
						game.setMaxChoice();
						if(game.choiceSelf!=-1){game.choiceSelf-=1;}
						game.startChoiceSelf();
						game.setPlayerTrumpCategory();
						mButtonChoiceSelfCategory.setText(" " +String.valueOf(game.playerTrumpCategory[game.player]) + " ");
						mDb.setChoices(lastSavedName,game);
						setTextChoicePlayer();
						game.setPlayer();
					}
					
				}
		});
		mButtonChoiceAgain.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					int a=game.getPlayer()-1;
					if (a==0){a=4;}
					game.setPlayer(a);
					game.setMaxChoice();
					game.startChoice();
					mDb.setChoices(lastSavedName,game);
					setTextChoicePlayer();
					game.setPlayer();
					game.choiceSelf=-1;
					mButtonChoiceSelf.setText("CHOICE SELF");
					mButtonChoiceSelfCategory.setText(" / ");
				}
		});
		mButtonChoiceOk.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
						game.setOldMaxChoice();
						game.startChoice();
						mDb.setChoices(lastSavedName,game);
						setTextChoicePlayer();
						game.setPlayer();
						if (game.getStartPlayer()==game.getPlayer()){
							mButtonChoiceOk.setVisibility(View.INVISIBLE);
							mButtonChoiceAll.setText("          PLAY          ");
						}
						game.choiceSelf=-1;
						mButtonChoiceSelf.setText("CHOICE SELF");
						mButtonChoiceSelfCategory.setText(" / ");
				}
		});
		mButtonChoiceAll.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					game.choiceSelf=-1;
					mButtonChoiceSelf.setText("CHOICE SELF");
					mButtonChoiceSelfCategory.setText(" / ");
					if (game.getStartPlayer()==game.getPlayer()){
						game.setOldMaxChoice();
						game.setHighestChoice();
						mTextViewTrump.setText("trump = " + String.valueOf(game.getTrumpCategory()));
						setTextPlayPlayers();
						if(game.maxChoice==game.PAS){//indien iedereen past
							toast("iedereen past");
							if(game.spelerdatvraagt!=0){//extra
								toast("long press=speler dat vraagt gaat toch alleen");
								mButtonChoiceSelf.setVisibility(View.INVISIBLE);//extra
								mButtonChoiceAgain.setVisibility(View.INVISIBLE);//extra
							}
							return;
						}
						lLChoice.setVisibility(View.INVISIBLE);
						mButtonPlayCard.setVisibility(View.VISIBLE);
						mButtonStopPlayTheCards.setVisibility(View.VISIBLE);
						mButtonPlayTheCards.setVisibility(View.VISIBLE);
						mButtonRestart.setVisibility(View.VISIBLE);
						mButtonChooseAgain.setVisibility(View.VISIBLE);
						mButtonSave.setVisibility(View.VISIBLE);
						setValueToTrumpValue();
						play=true;
						savePreferences(lastSavedName+"_play",true);
						setCardsVisibility();
						setImageClickable(true,game.player,0);
						game.setFriendsEnemies();
						game.restartGame();
						game.startGame();
						setCardPos(game.card+1);
						saveInPlay();
						//zien bij openmisere de openmiserespelers er niet meer aan kunnen,einde spel dus
						if(game.gameEndOpenmisere()){mTextViewOpenM.setText("openmisere ok");}
					}
					while(game.getStartPlayer()!=game.getPlayer()){
						game.setOldMaxChoice();
						game.startChoice();
						mDb.setChoices(lastSavedName,game);
						setTextChoicePlayer();
						game.setPlayer();
					}
					mButtonChoiceOk.setVisibility(View.INVISIBLE);
					mButtonChoiceAll.setText("          PLAY          ");
				}
		});
		mButtonChoiceAll.setOnLongClickListener(new OnLongClickListener(){//extra

				@Override
				public boolean onLongClick(View p1)
				{
					if(game.spelerdatvraagt==0){return false;}
					int a=game.spelerdatvraagt;
					game.setPlayer(a);
					game.choice=game.ALLEEN;
					game.playerChoice[game.player]=game.choice;
					game.maxChoice=game.choice;
					mDb.setChoices(lastSavedName,game);
					setTextChoicePlayer();
					game.setPlayer(game.startPlayer);
					return false;
				}
		});
		mButtonPlayCard.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					mButtonPlayCard.setClickable(false);
					mButtonPlayTheCards.setClickable(false);
					setImageClickable(false,0,0);
					moveCard();
				}
		});
		mButtonPlayTheCards.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					mButtonToast.setText(" TOAST OFF ");
					game.onToast=false;
					mButtonPlayCard.setClickable(false);
					mButtonPlayTheCards.setClickable(false);
					playTheCards=true;
					setImageClickable(false,0,0);
					moveCard();
				}
		});
		mButtonStopPlayTheCards.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//mButtonToast.setText(" TOAST ON ");
					//game.onToast=true;
					mButtonPlayCard.setClickable(true);
					mButtonPlayTheCards.setClickable(true);
					playTheCards=false;
					setImageClickable(true,0,0);
				}
		});
		mButtonSave.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					mButtonSave.setClickable(false);
					saveInPlay();
				}
		});	
		mButtonSettings.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//game.safely(game.player,2);//test
					stopService(mServiceIntent);//test
					showPopupWindow();
				}
			});
		mButtonRestart.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					savePreferences(lastSavedName+"_selected",false);
					savePreferences(lastSavedName+"_play",false);
					recreate();
				}
			});
		mButtonChooseAgain.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					game.startGame();
					setCardPos(game.card+1);
				}
		});
        //	showDialog(DIALOG_LOADSAVE);
		
		if (lastSavedName.compareTo("0")==0){
			saveAll("save1");
			mButtonLoad2.setVisibility(View.INVISIBLE);
			mButtonLoad3.setVisibility(View.INVISIBLE);
		}else{
			loadAll(lastSavedName);
			if (mDb.getCards("save2", "1").isEmpty()){mButtonLoad2.setVisibility(View.INVISIBLE);}
			if (mDb.getCards("save3", "1").isEmpty()){mButtonLoad3.setVisibility(View.INVISIBLE);}	
		}
		
		for(int c=1;c<=4;c++){
			for(int d=1;d<=13;d++){
				final int a=c;
				final int b=d;
				imageClickListener[a][b] = new OnClickListener(){
					
						@Override
						public void onClick(View view)
						{
							if (game.playerDeck[a].get(b-1).isSelected()){
								game.playerDeck[a].get(b-1).setSelected(false);
								setParam(false,0,-1);
								view.setX(game.playerDeck[a].get(b-1).getStartX());
								view.setY(game.playerDeck[a].get(b-1).getStartY());
							}
							else{
								if(otherCardIsSelected){
									Card temp = game.playerDeck[a].get(b-1);
									game.playerDeck[a].set(b-1,game.playerDeck[otherCardPlayer].get(otherCard-1));
									game.playerDeck[otherCardPlayer].set(otherCard-1,temp);
									game.sortingCards();
									setCards(a);
									drawCards(a,true);
									setCards(otherCardPlayer);
									drawCards(otherCardPlayer,true);
									setParam(false,0,-1);
								}else{
									int viewWidth = iv[a][b].getDrawable().getIntrinsicWidth();//test
									//toast("breedte="+String.valueOf(iv[a][b].getWidth())+" en "+String.valueOf(ivShadow[a][b].getWidth()));//test
									//toast("hoogte="+String.valueOf(iv[a][b].getHeight())+" en "+String.valueOf(ivShadow[a][b].getHeight()));//test
									//toast("viewbreedte="+String.valueOf(viewWidth));//test
									game.playerDeck[a].get(b-1).setSelected(true);
									setParam(true,a,b);
									switch(a){
										case 1:
											view.setY(view.getY()-28);
											break;
										case 2:
											view.setX(view.getX()+28);
											break;	
										case 3:
											view.setY(view.getY()+28);
											break;
										case 4:
											view.setX(view.getX()-28);
											break;	
									}		
								}		
							}
							game.playerDeck[a].get(b-1).setX(view.getX());
							game.playerDeck[a].get(b-1).setY(view.getY());
						}		
				};
			}	
		}
		for(int c=1;c<=4;c++){
			for(int d=1;d<=13;d++){
				final int a=c;
				final int b=d;
				cardImageClickListener[a][b] = new OnClickListener(){

					@Override
					public void onClick(View view)
					{
						if (!game.playerDeck[a].get(b-1).isSelected()){
							setCardPos(b);
							game.card=b-1;
							//toast("value = " + String.valueOf(game.playerDeck[a].get(b-1).value));
							
							//test////////
							float average = 0;
							int totalValue=0;
							totalValue=game.getTotalValue(game.player, game.playerDeck[a].get(b-1).category);
								if(totalValue!=0){//als totalValue!=0 dan is getNumberOfCards ook!=0 (delen door 0)
									average=(float)totalValue/game.getNumberOfCards(game.player,game.playerDeck[a].get(b-1).category);
								}
							toast("average = " + String.valueOf(average));
							//////////////
						}
					}		
				};
			}	
		}
		setImageClickable(true,0,0);
		 
		/*iv[a][b].setOnLongClickListener(new OnLongClickListener(){

		 @Override
		 public boolean onLongClick(View view)
		 {
		 game.playerDeck[a].remove(b-1);
		 drawCards(a);
		 return true;
		 }
		 });*/
		
		for(int b=1;b<=4;b++){
			final int a=b;
			mCheckBoxLock[a].setOnCheckedChangeListener(new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton button, boolean isChecked)
					{
						if(isChecked){//mCheckBoxLock[a].
							//toast(String.valueOf(lastSavedName)+ " true "+ String.valueOf(a));//test
							savePreferences(lastSavedName+"_lockP"+String.valueOf(a),true);
							lockPlayer[a]=true;
						}else{
							//toast(String.valueOf(lastSavedName)+ " false"+ String.valueOf(a));//test
							savePreferences(lastSavedName+"_lockP"+ String.valueOf(a),false);
							lockPlayer[a]=false;
						}
					}
				});
		}	
		//kaarten onzichtbaar maken, staat voorlopig hier,aanpassen?????
		/*for(int a=1;a<=4;a++){
		 	for(int b=1;b<=13;b++){
		 		iv[a][b].setVisibility(View.INVISIBLE);
		 	}
		}	*/
    }//onCreate
	
	/*private float xVerhouding(ImageView iv,int imageWidth){
		float xVerh;
		xVerh=(float)(screenWidth/2-iv.getWidth()/2)/(1280/2-imageWidth/2);//182
		return xVerh;
	}

	private float yVerhouding(ImageView iv){
		float yVerh;
		yVerh=(float)(screenHeight/2-iv.getHeight()/2)/(736/2-260/2);
		return yVerh;
	}*/
	
	private void zoekenNaar(){
		//de geselecteerde kaart terug in startpositie zetten
		for(int a=1;a<=4;a++){
			for(int b=1;b<=13;b++){
				if (game.playerDeck[a].get(b-1).isSelected()){
					game.playerDeck[a].get(b-1).setSelected(false);
					setParam(false,0,-1);
					//iv[a][b].setX(game.playerDeck[a].get(b-1).getStartX());
					//iv[a][b].setY(game.playerDeck[a].get(b-1).getStartY());
					float x=game.playerDeck[a].get(b-1).getStartX();
					float y=game.playerDeck[a].get(b-1).getStartY();
					setCardRealPos(a,b,x,y,-1);
				}		
			}	
		}
		annul=false;
		progressDialog=new ProgressDialog(this);
		progressDialog.setTitle("Eventjes wachten aub");
		progressDialog.setMessage("Zoeken naar... "+mButtonChangeSearching.getText());
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
		progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Annuleren",
			new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					annul=true;
				}
		});
		progressDialog.show();
        
		new Thread(new Runnable() {
				int z=0;
				@Override
				public void run()
				{
					while(!annul & !game.startZoekenNaar(keuze)){
						z+=1;
					}
					runOnUiThread(new Runnable() {
							@Override
							public void run()
							{
								if(annul){
									toast("Zoeken naar... geannuleerd");
									toast(String.valueOf(z)+" keer gezocht");
									recreate();
								}else{
									toast(String.valueOf(z)+" keer gezocht");
									showDialog(DIALOG_SEARCH);
								}	
								progressDialog.dismiss();
							}
					});
				}
		}).start();	
	}
	
	public void setCardsVisibility(){//al de openmiserespelers hun kaarten zichtbaar zetten
		if(game.maxChoice==game.OPENMISERE){
			for(int a=1;a<=4;a++){
				if(game.playerChoice[a]==game.OPENMISERE){
					savePreferences(lastSavedName+"_visibleCardsP"+String.valueOf(a),true);
					visibleCardsPlayer[a]=true;
					setCards(a);
					drawCards(a,true);
				}
			}	
		}
	}
	
	private void setValueToTrumpValue(){
		for(int a=1;a<=4;a++){
			for(int b=0;b<game.playerDeck[a].size();b++){
				if (game.playerDeck[a].get(b).category==game.getTrumpCategory()){
					game.playerDeck[a].get(b).setValue(game.playerDeck[a].get(b).value + 20);
				}
			}
		}	
	}
	
	private void saveInPlay(){
		progressDialog = ProgressDialog.show(this, "Eventjes wachten a.u.b.", "Opslaan gegevens...", true);
		new Thread(new Runnable() {
				@Override
				public void run()
				{
					if (lastSavedName.compareTo("save4")==0){
						mDb.close();
						mDb=new CardsDatabase(MainActivity.this, true);
						mDb.open();
					}	
					if (play){
						mDb.setCards(lastSavedName,"waitingDeck0→play" ,game.waitingDeck[0].iterator()); 
						mDb.setCards(lastSavedName,"waitingDeck1→play" ,game.waitingDeck[1].iterator());
						mDb.setCards(lastSavedName,"gameDeck→play" ,game.gameDeck.iterator());
						mDb.setCards(lastSavedName,"last4CardDeck→play" ,game.last4CardDeck.iterator());
						mDb.setPlay(lastSavedName,game);
						for(int a=1;a<=4;a++){
							mDb.setCards(lastSavedName,String.valueOf(a) + "→play" ,game.playerDeck[a].iterator());
						}	
					}		

					runOnUiThread(new Runnable() {
							@Override
							public void run()
							{
								toast("Game saved!");
								mButtonSave.setClickable(true);
								if (lastSavedName.compareTo("save4")==0){
									//recreate();
								}	
								progressDialog.dismiss();
							}
						});
				}
			}).start();	
	}
	
	private void saveByExit(){ //niet in gebruik
		//variabelen die hier nog niet zijn bijgezet:
		//game.followed[][] en...
		
		
		if (play){
			mServiceIntent.putExtra("play",play);
			mServiceIntent.putExtra("lastSavedName",lastSavedName);
			for(int a=0;a<=1;a++){
				mServiceIntent.putExtra(String.valueOf(a) + "waitingDecksize",game.waitingDeck[a].size());
				for(int b=0;b<game.waitingDeck[a].size();b++){
					mServiceIntent.putExtra("waitingDeck" + String.valueOf(a) + String.valueOf(b) + "value",game.waitingDeck[a].get(b).value);
					mServiceIntent.putExtra("waitingDeck" + String.valueOf(a) + String.valueOf(b) + "cardReference",game.waitingDeck[a].get(b).cardReference);
					mServiceIntent.putExtra("waitingDeck" + String.valueOf(a) + String.valueOf(b) + "category",game.waitingDeck[a].get(b).category);
				}
			}
			mServiceIntent.putExtra("gameDecksize",game.gameDeck.size());
			for(int b=0;b<game.gameDeck.size();b++){
				mServiceIntent.putExtra("gameDeck" + String.valueOf(b) + "value",game.gameDeck.get(b).value);
				mServiceIntent.putExtra("gameDeck" + String.valueOf(b) + "cardReference",game.gameDeck.get(b).cardReference);
				mServiceIntent.putExtra("gameDeck" + String.valueOf(b) + "category",game.gameDeck.get(b).category);
			}
			mServiceIntent.putExtra("last4CardDecksize",game.last4CardDeck.size());
			for(int b=0;b<game.last4CardDeck.size();b++){
				mServiceIntent.putExtra("last4CardDeck" + String.valueOf(b) + "value",game.last4CardDeck.get(b).value);
				mServiceIntent.putExtra("last4CardDeck" + String.valueOf(b) + "cardReference",game.last4CardDeck.get(b).cardReference);
				mServiceIntent.putExtra("last4CardDeck" + String.valueOf(b) + "category",game.last4CardDeck.get(b).category);
			}
			mServiceIntent.putExtra("maxChoice",game.maxChoice);
			mServiceIntent.putExtra("oldMaxChoice",game.oldMaxChoice);
			mServiceIntent.putExtra("troelPlayer",game.troelPlayer);
			mServiceIntent.putExtra("troelMeePlayer",game.troelMeePlayer);
			mServiceIntent.putExtra("alleen",game.alleen);//dit is boolean
			mServiceIntent.putExtra("dealer",game.dealer);
			for(int a=1;a<=4;a++){
				mServiceIntent.putExtra("playerTrumpCategory" + String.valueOf(a),game.playerTrumpCategory[a]);
				mServiceIntent.putExtra("playerChoice" + String.valueOf(a),game.playerChoice[a]);
				mServiceIntent.putExtra("oldPlayerChoice" + String.valueOf(a),game.oldPlayerChoice[a]);
			}
			mServiceIntent.putExtra("startPlayer",game.startPlayer);
			mServiceIntent.putExtra("oldStartPlayer",game.oldStartPlayer);
			mServiceIntent.putExtra("player",game.player);
			mServiceIntent.putExtra("card",game.card);
			for(int a=1;a<=4;a++){
				mServiceIntent.putExtra(String.valueOf(a) + "size",game.playerDeck[a].size());
				for(int b=0;b<game.playerDeck[a].size();b++){
					mServiceIntent.putExtra(String.valueOf(a) + String.valueOf(b) + "value",game.playerDeck[a].get(b).value);
					mServiceIntent.putExtra(String.valueOf(a) + String.valueOf(b) + "cardReference",game.playerDeck[a].get(b).cardReference);
					mServiceIntent.putExtra(String.valueOf(a) + String.valueOf(b) + "category",game.playerDeck[a].get(b).category);
				}
			}
			startService(mServiceIntent);
		}	
	}
	
	private void anSetPlayCard(int player, int card, float Xpos, float Ypos){
		//float xx=Xpos*xVerhouding(iv[player][card],182);
		//float yy=Ypos*yVerhouding(iv[player][card]);
		//float xxShadow=Xpos*xVerhouding(ivShadow[player][card],213);
		//float yyShadow=Ypos*yVerhouding(ivShadow[player][card]);
		AnimatorSet set=new AnimatorSet();
		ObjectAnimator animX=ObjectAnimator.ofFloat(iv[player][card],View.X,Xpos);//View.TRANSLATION_X
		ObjectAnimator animY=ObjectAnimator.ofFloat(iv[player][card],View.Y,Ypos);
		ObjectAnimator rotate=ObjectAnimator.ofFloat(iv[player][card],View.ROTATION,0);
		//ObjectAnimator animShadowX=ObjectAnimator.ofFloat(ivShadow[player][card],View.X,xxShadow);//View.TRANSLATION_X
		//ObjectAnimator animShadowY=ObjectAnimator.ofFloat(ivShadow[player][card],View.Y,yyShadow);
		//ObjectAnimator rotateShadow=ObjectAnimator.ofFloat(ivShadow[player][card],View.ROTATION,0);
		//kaarten draaien naar 0 graden, als ze al op 0 staan dan draaien ze niet
		//ObjectAnimator alpha=ObjectAnimator.ofFloat(iv,View.ALPHA,0f,1);
		set.play(animY).with(animX).with(rotate);//.with(animShadowY).with(animShadowX).with(rotateShadow);
		set.setDuration(2000);//500
		if(playTheCards){set.setDuration(0);}//100
		set.start();
		set.addListener(new AnimatorSet.AnimatorListener(){

				@Override
				public void onAnimationStart(Animator p1)
				{
					//
				}

				@Override
				public void onAnimationEnd(Animator animator)
				{  
					game.gameDeck.add(game.playerDeck[game.player].get(game.card));
					drawPlayedCards(game.player);
					game.playerDeck[game.player].remove(game.card);
					game.setRangeCategory2();
					game.setFollowAfterPlayingCard();//openmisere
					game.setOkayAfterPlayingCard();//openmisere
					setCards(game.player);
					drawCards(game.player,true);
					if(game.gameDeck.size()==4){
						checkCardsPlayed();
						if(game.playerDeck[game.player].isEmpty()){playTheCards=false;}
						game.setOldStartPlayer();
						//hier bepalen wie ronde gewonnen heeft
						game.setStartPlayer(game.getWinner());
						game.setPlayer(game.startPlayer);
						///////////////////
						game.last4CardDeck.clear();
						game.last4CardDeck.addAll(game.gameDeck);
						if(game.playerChoice[game.player]==game.PAS || 
						   game.playerChoice[game.player]==game.MISERE || game.playerChoice[game.player]==game.OPENMISERE){
							game.waitingDeck[0].addAll(game.gameDeck);
						}else{
							game.waitingDeck[1].addAll(game.gameDeck);
						}
						game.checkGameEnd();//enkel bij misere en openmisere
						//setRangeCategory, misere
						if(game.maxChoice==game.MISERE){game.setRangeCategory(0,false);}
						//setRangeCategory, openmisere
						if(game.maxChoice==game.OPENMISERE){game.setRangeCategory(0,true);}
						//zien bij openmisere de openmiserespelers er niet meer aan kunnen,einde spel dus
						if(game.gameEndOpenmisere()){
							mTextViewOpenM.setText("openmisere ok");
							game.gameEnd=true;
						}
						//kaarten verschuiven naar de winnaar ..........
						for(int b=5;b<=8;b++){
							switch(game.player){
								case 1:
									anSetPutAwayCards(0,b,1280/2-100,736+20);break;
								case 2:
									anSetPutAwayCards(0,b,-250,736/2-143);break;
								case 3:
									anSetPutAwayCards(0,b,1280/2-100,-300);break;
								case 4:
									anSetPutAwayCards(0,b,1280+20,736/2-143);break;	
							}	
						}	
						//indien einde spel = tellen punten
						if(game.gameEnd){
							game.setPoints();
							mDb.setPoints(lastSavedName, game);
						}
						setTextPlayPlayers();
						game.gameDeck.clear();
					}else{
						game.setPlayer();
						if(!playTheCards){setImageClickable(true,game.player,0);}
						game.startGame();
						setCardPos(game.card+1);
					}
					if(playTheCards){
						moveCard();
					}
					
					for(int a=0;a<game.waitingDeck[0].size();a++){//dit voorlopig hier gezet
						//game.waitingDeck[0].get(a).setValueToStartValue();
						//game.waitingDeck[1].get(a).setValueToStartValue();
						//dit pas toepassen als het spel volledig is uitgespeeld want in gamedeck is dezelfde
						//kaart die dan ook al wordt aangepast
					}	
				}

				@Override
				public void onAnimationCancel(Animator p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAnimationRepeat(Animator p1)
				{
					// TODO: Implement this method
				}
		});
	}
	
	private void anSetPutAwayCards(final int player,final int card, float Xpos, float Ypos){
		//float xx=Xpos*xVerhouding(iv[player][card],182);
		//float yy=Ypos*yVerhouding(iv[player][card]);
		//float xxShadow=Xpos*xVerhouding(ivShadow[player][card],213);
		//float yyShadow=Ypos*yVerhouding(ivShadow[player][card]);
		AnimatorSet set=new AnimatorSet();
		ObjectAnimator animX=ObjectAnimator.ofFloat(iv[player][card],View.X,Xpos);
		ObjectAnimator animY=ObjectAnimator.ofFloat(iv[player][card],View.Y,Ypos);
		//ObjectAnimator animShadowX=ObjectAnimator.ofFloat(ivShadow[player][card],View.X,xxShadow);
		//ObjectAnimator animShadowY=ObjectAnimator.ofFloat(ivShadow[player][card],View.Y,yyShadow);
		set.play(animY).with(animX);//.with(animShadowY).with(animShadowX);
		set.setDuration(2000);//500
		set.setStartDelay(100);
		if(playTheCards){
			set.setDuration(0);
			set.setStartDelay(0);
		}
		set.start();
		set.addListener(new AnimatorSet.AnimatorListener(){

				@Override
				public void onAnimationStart(Animator p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAnimationEnd(Animator p1)
				{
					if(iv[player][card]==iv[0][5]){//dit is om er voor te zorgen dat het maar 1 keer gebeurd
						if(game.gameEnd){	
							mButtonSaveAsExternalStorage.setVisibility(View.INVISIBLE);
							mButtonPlayTheCards.setVisibility(View.INVISIBLE);
							mButtonStopPlayTheCards.setVisibility(View.INVISIBLE);
							mButtonPlayCard.setVisibility(View.INVISIBLE);
							mButtonSave.setVisibility(View.INVISIBLE);
							mButtonChooseAgain.setVisibility(View.INVISIBLE);
							if(!game.playerDeck[game.player].isEmpty()){//alle niet afgelegde kaarten toevoegen aan waitingdeck[0]
								for(int a=1;a<=4;a++){
									game.waitingDeck[0].addAll(game.playerDeck[a]);
								}	
							}	
						}else{
							if(!playTheCards){setImageClickable(true,game.player,0);}
							game.startGame();
							setCardPos(game.card+1);
							if(playTheCards){moveCard();}
						}
					}
				}

				@Override
				public void onAnimationCancel(Animator p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAnimationRepeat(Animator p1)
				{
					// TODO: Implement this method
				}
		});	
	}
	
	private void moveCard(){
		for(int b=1;b<=game.playerDeck[game.player].size();b++){
			if (game.playerDeck[game.player].get(b-1).isSelected()){
				anSetPlayCard(game.player,b,posXPlayedCard[game.player],posYPlayedCard[game.player]);
			}
		}
	}
	
	private void checkCardsPlayed(){//gebeurd pas als de 4 kaarten zijn afgelegd
		//alle categorien controleren bij de andere spelers om te zien of jij van deze categorie er enkel
		//nog hebt(ook bij troef dus) of bij een openmiserespeler de andere 2 er geen meer van hebben bijvb.
		if(game.maxChoice==game.OPENMISERE){//ok
			for(int categor=1;categor<=4;categor++){
				for(int c=1;c<=4;c++){
					int total=game.getNumberOfPlayedCards(categor)+game.getNumberOfPlayedCardsGameDeck(categor);
					for(int b=1;b<=4;b++){
						if(c==b || game.playerChoice[b]==game.OPENMISERE){total+=game.getNumberOfCards(b,categor);}
					}
					if(total==13){
						for(int b=1;b<=4;b++){
							if(c!=b && game.playerChoice[b]!=game.OPENMISERE){game.setFollow(c, b, categor);}
						}	
					}
				}
			}	
		}else{//ok
			for(int categor=1;categor<=4;categor++){
				for(int c=1;c<=4;c++){
					int total=0;
					for(int b=1;b<=4;b++){
						if(c!=b){total+=game.getNumberOfCards(b,categor);}
					}
					if(total==0){
						for(int b=1;b<=4;b++){
							if(c!=b){game.setFollow(c, b, categor);}
						}	
					}
				}
			}	
		}	
		game.setCheckDeck();//iets lager kan checkdeck nog aangepast worden
		if(game.maxChoice==game.OPENMISERE){
			//checkdeck aanpassen door openmisere
			for(int a=0;a<game.checkDeck.size();a++){
				for(int z=1;z<=4;z++){//player
					if(game.playerChoice[z]==game.OPENMISERE && game.checkDeck.get(a).player==z){
						game.checkDeck.get(a).player=0;
					}	
				}	
			}
			//voorwaarden om speler te controleren:
			//als men niet kan volgen
			//achter de allerhoogste kaart
			//achter de 1ste openmiserespeler, behalve als er nog een openmiserespeler komt die kan volgen
			//   						       behalve als de passer lager geraakt(indien passer lager moest)
			int categoryStart=game.gameDeck.get(0).category;
			int b=game.startPlayer;b--;if(b==0){b=4;}
			//eerst zoeken of er een allerhoogste kaart is
			int firstValue=0;
			int lowestValueInDesc=0;//wordt pas later gebruikt
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				if(game.gameDeck.get(a).category==categoryStart && 
				   game.hasHighestValue(game.gameDeck.get(a).value,game.gameDeck.get(a).category)){
					firstValue=game.gameDeck.get(a).value;
					lowestValueInDesc=game.getLowestValueInDescendingOrder(0,0,game.gameDeck.get(a).category);
					break;
				}	
			}
			//indien allerhoogste niet gevonden dan gaan zoeken bij de openmiserespelers of oldopenmiserespelers
			if(lowestValueInDesc==0){
				for(b=1;b<=4;b++){
					if((game.playerChoice[b]==game.OPENMISERE || game.oldPlayerChoice[b]==game.OPENMISERE) && 
				   	game.hasHighestValue(game.getValueHighestValue(game.playerDeck[b],categoryStart),categoryStart)){
					 	lowestValueInDesc=game.getLowestValueInDescendingOrder(b,b,categoryStart);
					 	break;
					}	
				}	
				//firstValue nog vastleggen
				if(lowestValueInDesc!=0){
					b=game.startPlayer;b--;if(b==0){b=4;}
					for(int a=0;a<game.gameDeck.size();a++){
						b++;if(b==5){b=1;}
						if(game.gameDeck.get(a).category==categoryStart && game.gameDeck.get(a).value>=lowestValueInDesc){
							firstValue=game.gameDeck.get(a).value;break;
						}
					}
				}
			}
			//nu zoeken wie niet kan volgen of tot de allerhoogste kaart en dan vastleggen
			ArrayList<Integer> checkPlayers = new ArrayList<Integer>();
			b=game.startPlayer;b--;if(b==0){b=4;}	
			boolean found=false;
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				//openmiserespelers moeten niet hier gecontroleerd worden(ook oldopenmiserespelers)
				if(game.playerChoice[b]!=game.OPENMISERE && game.oldPlayerChoice[b]!=game.OPENMISERE && (found || game.gameDeck.get(a).category!=categoryStart)){
					checkPlayers.add(b);
				}
				if(game.gameDeck.get(a).category==categoryStart){
					if(game.gameDeck.get(a).value==firstValue){found=true;}   
				}
			}
			//nu zoeken achter de 1ste openmiserespeler
			//behalve als er nog een openmiserespeler komt die kan volgen
			//behalve als de passer lager geraakt
			b=game.startPlayer;b--;if(b==0){b=4;}
			boolean checkValue=false;
			int value=0;
			found=false;
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				if(game.gameDeck.get(a).category==categoryStart){
					if(game.gameDeck.get(a).value>value){value=game.gameDeck.get(a).value;}
				}
				if(found){
					if(game.playerChoice[b]==game.PAS){
						boolean ok=true;
						if(checkValue){
							ok=false;
							if(game.gameDeck.get(a).category==categoryStart){
								if(game.gameDeck.get(a).value==value){
									checkValue=false;ok=true;
								}
							}	
						}
						//zien of de (old)openmiserespelers achter je niet kunnen volgen
						int z=b;
						z++;if(z==5){z=1;}
						while(z!=game.startPlayer){
							if((game.playerChoice[z]==game.OPENMISERE || game.oldPlayerChoice[z]==game.OPENMISERE) && game.followed[b][z][categoryStart]){
								ok=false;
							}
							z++;if(z==5){z=1;}
						}	
						if(ok && game.playerChoice[b]!=game.OPENMISERE && game.oldPlayerChoice[b]!=game.OPENMISERE){
							checkPlayers.add(b);
						}
					}
				}
				if(game.playerChoice[b]==game.OPENMISERE){
					found=true;
					if(game.gameDeck.get(a).category==categoryStart){
						if(game.gameDeck.get(a).value==value){//value openmisere is de hoogste op dit moment
							checkValue=true;
						}
					}	
				}
			}	
			//nu zoeken achter de 2de,3de... hoogste kaart voor de allerhoogste kaart
			//bvb k2a8,de 2 is ook de laatste kaart
			if(lowestValueInDesc!=0){
				b=game.startPlayer;b--;if(b==0){b=4;}	
				found=false;
				for(int a=0;a<game.gameDeck.size();a++){
					b++;if(b==5){b=1;}
					if(game.gameDeck.get(a).value==firstValue){break;}
					if(game.playerChoice[b]!=game.OPENMISERE &&  game.oldPlayerChoice[b]!=game.OPENMISERE  && found && game.gameDeck.get(a).category==categoryStart){
						checkPlayers.add(b);
					}
					if(game.gameDeck.get(a).category==categoryStart){
						if(game.gameDeck.get(a).value>=lowestValueInDesc){found=true;}   
					}
				}
			}	
			//indien dezelfde in checkplayers dan deze wissen zodat er max. maar 1 in staat
			for(int a=0;a<checkPlayers.size()-1;a++){
				for(int c=a+1;c<checkPlayers.size();c++){
					if((int)checkPlayers.get(a)==(int)checkPlayers.get(c)){
						checkPlayers.remove(c);c--;
					}
				}	
			}
			//toast gebruiken
			String str="controle op speler(s)...";
			for(int a=0;a<checkPlayers.size();a++){
				str=str + " " + String.valueOf(checkPlayers.get(a));
			}	
			if(!checkPlayers.isEmpty()){toast(str);}
			//controle allerlaatste kaart
			b=game.startPlayer;b--;if(b==0){b=4;}
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				for(int e=0;e<checkPlayers.size();e++){
					if(b==checkPlayers.get(e)){
						//controle missingcards
						for(int deck=0;deck<=4;deck++){
							for(int c=0;c<=4;c++){
								if(c!=b){
									int missingCards=game.getMissingCardsInAscendingOrder(deck,c,game.gameDeck.get(a));
									if(missingCards==0){
										game.setFollow(c, b, game.gameDeck.get(a).category);
										if(c==0){
											toast("followed[all]["+String.valueOf(b)+"]["+String.valueOf(game.gameDeck.get(a).category)+"]=false");
										}else{
											toast("followed["+String.valueOf(c)+"]["+String.valueOf(b)+"]["+String.valueOf(game.gameDeck.get(a).category)+"]=false");
										}
										if(c==0){break;}//indien c=0 dan geen controle players
									}
								}
							}
						}
					}
				}
			}	
			//nu gaan we pas al de spelers die niet gevolgd hebben(enkel de passers) op niet gevolgd zetten
			//reden:hogerop wordt er controle gedaan op de spelers                          
			categoryStart=game.gameDeck.get(0).category;
			b=game.startPlayer;b--;if(b==0){b=4;}
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				if(game.gameDeck.get(a).category!=categoryStart && game.playerChoice[b]!=game.OPENMISERE){
					game.setFollow(0,b,categoryStart);
				}
			}
		}	
		
		//voorwaarden om speler te controleren:
		//als men niet kan volgen
		//achter de allerhoogste kaart
		//achter de 1ste miserespeler, behalve als er nog een miserespeler komt die kan volgen
		//   						   behalve als de passer lager geraakt(indien passer lager moest)
		if(game.maxChoice==game.MISERE){
			int categoryStart=game.gameDeck.get(0).category;
			int b=game.startPlayer;b--;if(b==0){b=4;}
			//eerst zoeken of er een allerhoogste kaart is
			int highestValue=0;
			int lowestValueInDesc=0;//wordt pas later gebruikt
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				if(game.gameDeck.get(a).category==categoryStart && 
				   game.hasHighestValue(game.gameDeck.get(a).value,game.gameDeck.get(a).category)){
					highestValue=game.gameDeck.get(a).value;
					lowestValueInDesc=game.getLowestValueInDescendingOrder(0,0,game.gameDeck.get(a).category);
					break;
				}	
			}
			//nu zoeken wie niet kan volgen of tot de allerhoogste kaart en dan vastleggen na de allerhoogste kaart
			ArrayList<Integer> checkPlayers = new ArrayList<Integer>();
			b=game.startPlayer;b--;if(b==0){b=4;}	
			boolean found=false;
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				if(found || game.gameDeck.get(a).category!=categoryStart){
					checkPlayers.add(b);
				}
				if(game.gameDeck.get(a).category==categoryStart){
					if(game.gameDeck.get(a).value==highestValue){found=true;}   
				}
			}
			//nu zoeken achter de 1ste miserespeler
			//behalve als er nog een miserespeler komt die kan volgen
			//behalve als de passer lager geraakt
			b=game.startPlayer;b--;if(b==0){b=4;}
			boolean checkValue=false;
			int value=0;
			found=false;
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				if(game.gameDeck.get(a).category==categoryStart){
					if(game.gameDeck.get(a).value>value){value=game.gameDeck.get(a).value;}
				}
				if(found){
					if(game.playerChoice[b]==game.PAS){
						boolean ok=true;
						if(checkValue){
							ok=false;
							if(game.gameDeck.get(a).category==categoryStart){
								if(game.gameDeck.get(a).value==value){
									checkValue=false;ok=true;
								}
							}	
						}
						//zien of de miserespelers achter je niet kunnen volgen
						int z=b;
						z++;if(z==5){z=1;}
						while(z!=game.startPlayer){
							if(game.playerChoice[z]==game.MISERE && game.followed[b][z][categoryStart]){
								ok=false;
							}
							z++;if(z==5){z=1;}
						}	
						if(ok){
							checkPlayers.add(b);
						}
					}
				}
				if(game.playerChoice[b]==game.MISERE){
					found=true;
					if(game.gameDeck.get(a).category==categoryStart){
						if(game.gameDeck.get(a).value==value){//value misere is de hoogste op dit moment
							checkValue=true;
						}
					}	
				}
			}	
			//nu zoeken achter de 2de,3de... hoogste kaart voor de allerhoogste kaart
			//bvb k2a8,de 2 is ook de laatste kaart
			if(lowestValueInDesc!=0){
				b=game.startPlayer;b--;if(b==0){b=4;}	
				found=false;
				for(int a=0;a<game.gameDeck.size();a++){
					b++;if(b==5){b=1;}
					if(game.gameDeck.get(a).value==highestValue){break;}
					if(found && game.gameDeck.get(a).category==categoryStart){
						checkPlayers.add(b);
					}
					if(game.gameDeck.get(a).category==categoryStart){
						if(game.gameDeck.get(a).value>=lowestValueInDesc){found=true;}   
					}
				}
			}	
			//indien dezelfde in checkplayers dan deze wissen zodat er max. maar 1 in staat
			for(int a=0;a<checkPlayers.size()-1;a++){
				for(int c=a+1;c<checkPlayers.size();c++){
					if((int)checkPlayers.get(a)==(int)checkPlayers.get(c)){
						checkPlayers.remove(c);c--;
					}
				}	
			}
			//toast gebruiken
			String str="controle op speler(s)...";
			for(int a=0;a<checkPlayers.size();a++){
				str=str + " " + String.valueOf(checkPlayers.get(a));
			}	
			if(!checkPlayers.isEmpty()){toast(str);}
			//controle allerlaatste kaart
			b=game.startPlayer;b--;if(b==0){b=4;}
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				for(int e=0;e<checkPlayers.size();e++){
					if(b==checkPlayers.get(e)){
						//controle missingcards
						for(int deck=0;deck<=4;deck++){
							for(int c=0;c<=4;c++){
								if(c!=b){
									int missingCards=game.getMissingCardsInAscendingOrder(deck,c,game.gameDeck.get(a));
									if(missingCards==0){
										game.setFollow(c, b, game.gameDeck.get(a).category);
										if(c==0){
											toast("followed[all]["+String.valueOf(b)+"]["+String.valueOf(game.gameDeck.get(a).category)+"]=false");
											//indien miserespeler dan in playcategory category verwijderen als die er in zit
											if(game.playerChoice[b]==game.MISERE){
												for(int d=0;d<game.playCategory[b].size();d++){
													if(game.playCategory[b].get(d)==game.gameDeck.get(a).category){game.playCategory[b].remove(d);}
												}
											}
										}else{
											toast("followed["+String.valueOf(c)+"]["+String.valueOf(b)+"]["+String.valueOf(game.gameDeck.get(a).category)+"]=false");
										}
										if(c==0){break;}//indien c=0 dan geen controle players
									}
								}
							}
						}
					}
				}
			}	
			//nu gaan we pas al de spelers die niet gevolgd hebben op niet gevolgd zetten
			//reden:hogerop wordt er controle gedaan op de spelers                        
			categoryStart=game.gameDeck.get(0).category;
			b=game.startPlayer;b--;if(b==0){b=4;}
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				if(game.gameDeck.get(a).category!=categoryStart){
					game.setFollow(0,b,categoryStart);
				}
			}
		}	
		           
		if(game.maxChoice!=game.OPENMISERE && game.maxChoice!=game.MISERE){//al de andere keuzes
			//zien naar alle kaarten die niet gevolgd hebben en geen troef zijn
			//zien of er net nog 1 kaart lager is achter de allerhoogste kaart of achter de eerstgekochte kaart
			//ook zoeken achter de 2de,3de... hoogste kaart voor de allerhoogste kaart
			//en eens zien naar de kaarten van de speler zelf...
			int categoryStart=game.gameDeck.get(0).category;
			int b=game.startPlayer;b--;if(b==0){b=4;}
			//eerst zoeken of er een allerhoogste kaart is
			int highestValue=0;
			int lowestValueInDesc=0;//wordt pas later gebruikt
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				if(game.gameDeck.get(a).category==categoryStart && 
				   game.hasHighestValue(game.gameDeck.get(a).value,game.gameDeck.get(a).category)){
					highestValue=game.gameDeck.get(a).value;
					lowestValueInDesc=game.getLowestValueInDescendingOrder(0,0,game.gameDeck.get(a).category);
					break;
				}	
			}
			//nu zoeken wie niet kan volgen tot de allerhoogste kaart(of tot de eerste troef) en dan vastleggen na deze kaart
			ArrayList<Integer> checkPlayers = new ArrayList<Integer>();
			b=game.startPlayer;b--;if(b==0){b=4;}	
			boolean found=false;
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				if(!found && game.gameDeck.get(a).category!=categoryStart && game.gameDeck.get(a).category!=game.trumpCategory ||
				   (found && (game.gameDeck.get(a).category!=game.trumpCategory || categoryStart==game.trumpCategory))){
					checkPlayers.add(b);
				}
				if(game.gameDeck.get(a).category==categoryStart){
					if(game.gameDeck.get(a).value==highestValue){found=true;}   
				}else{
					if(game.gameDeck.get(a).category==game.trumpCategory){found=true;}
				}
			}
			//nu zoeken achter de 2de,3de... hoogste kaart voor de allerhoogste kaart
			//bvb kva8,de v is ook de laatste kaart
			if(lowestValueInDesc!=0){
				b=game.startPlayer;b--;if(b==0){b=4;}	
				found=false;
				int highestValueAtTheMoment=0;
				for(int a=0;a<game.gameDeck.size();a++){
					b++;if(b==5){b=1;}
					if(game.gameDeck.get(a).value==highestValue){break;}
					if(game.gameDeck.get(a).category==categoryStart && game.gameDeck.get(a).value>highestValueAtTheMoment){
						highestValueAtTheMoment=game.gameDeck.get(a).value;
						if(found){continue;}
					}
					if(found && game.gameDeck.get(a).category==categoryStart){
						checkPlayers.add(b);
					}
					if(game.gameDeck.get(a).category==categoryStart){
						if(game.gameDeck.get(a).value>=lowestValueInDesc){found=true;}   
					}
				}
			}	
			//indien dezelfde in checkplayers dan deze wissen zodat er max. maar 1 in staat
			for(int a=0;a<checkPlayers.size()-1;a++){
				for(int c=a+1;c<checkPlayers.size();c++){
					if((int)checkPlayers.get(a)==(int)checkPlayers.get(c)){
						checkPlayers.remove(c);c--;
					}
				}	
			}
			//toast gebruiken
			String str="controle op speler(s)...";
			for(int a=0;a<checkPlayers.size();a++){
				str=str + " " + String.valueOf(checkPlayers.get(a));
			}	
			if(!checkPlayers.isEmpty()){toast(str);}
			//controle allerlaatste kaart
			b=game.startPlayer;b--;if(b==0){b=4;}
			for(int a=0;a<game.gameDeck.size();a++){
				b++;if(b==5){b=1;}
				for(int e=0;e<checkPlayers.size();e++){
					if(b==checkPlayers.get(e)){
						//controle missingcards
						for(int deck=0;deck<=4;deck++){
							for(int c=0;c<=4;c++){
								if(c!=b){
									int missingCards=game.getMissingCardsInDescendingOrder(deck,c,game.gameDeck.get(a));
									if(missingCards==0){
										game.setFollow(c, b, game.gameDeck.get(a).category);
										if(c==0){
											toast("followed[all]["+String.valueOf(b)+"]["+String.valueOf(game.gameDeck.get(a).category)+"]=false");
										}else{
											toast("followed["+String.valueOf(c)+"]["+String.valueOf(b)+"]["+String.valueOf(game.gameDeck.get(a).category)+"]=false");
										}
										if(c==0){break;}//indien c=0 dan geen controle players
									}
									//indien een andere speler maximum nog 1 kaart er van kan hebben dan 50% kans op niet gevolgd zetten
									//indien er ook minimum 1 van de andere 2 spelers nog gevolgd heeft
									if(missingCards==1 && c!=0){//bij 50% moet men c=0 niet toepassen
										int followers=0;
										for(int z=1;z<=4;z++){
											if(z!=c && z!=b && game.followed[c][z][game.gameDeck.get(a).category] ){
												followers++;
											}
										}
										if(game.random(2)==1 && followers!=0){//indien min. 1 van beide gevolgd heeft en 50%
											//if(followers!=0){//test
											game.setFollow(c, b, game.gameDeck.get(a).category);
											if(c==0){//test
												toast("followed[all]["+String.valueOf(b)+"]["+String.valueOf(game.gameDeck.get(a).category)+"]=false???");
											}else{
												toast("followed["+String.valueOf(c)+"]["+String.valueOf(b)+"]["+String.valueOf(game.gameDeck.get(a).category)+"]=false???");
											}
											if(c==0){break;}//indien c=0 dan geen controle players
										}
									}
								}
							}
						}
					}
				}
			}	
		}		
	}
	
	private void setCardPosStart(){
		//de geselecteerde kaart terug in startpositie zetten
		for(int b=1;b<=game.playerDeck[game.player].size();b++){
			if (game.playerDeck[game.player].get(b-1).isSelected()){
				game.playerDeck[game.player].get(b-1).setSelected(false);
				//iv[game.player][b].setX(game.playerDeck[game.player].get(b-1).getStartX());
				//iv[game.player][b].setY(game.playerDeck[game.player].get(b-1).getStartY());
				float x=game.playerDeck[game.player].get(b-1).getStartX();
				float y=game.playerDeck[game.player].get(b-1).getStartY();
				setCardRealPos(game.player,b,x,y,-1);
			}		
		}
	}
	
	public void setCardPos(int card){
		setCardPosStart();
		if (card>=1 && card<=game.playerDeck[game.player].size()){
			game.playerDeck[game.player].get(card-1).setSelected(true);
			switch(game.player){
				case 1:
					//iv[game.player][card].setY(iv[game.player][card].getY()-28);
					float x=iv[game.player][card].getX();
					float y=iv[game.player][card].getY()-28;
					setCardRealPos(game.player,card,x,y,-1);
					break;
				case 2:
					//iv[game.player][card].setX(iv[game.player][card].getX()+28);
					x=iv[game.player][card].getX()+28;
					y=iv[game.player][card].getY();
					setCardRealPos(game.player,card,x,y,-1);
					break;	
				case 3:
					//iv[game.player][card].setY(iv[game.player][card].getY()+28);
					x=iv[game.player][card].getX();
					y=iv[game.player][card].getY()+28;
					setCardRealPos(game.player,card,x,y,-1);
					break;
				case 4:
					//iv[game.player][card].setX(iv[game.player][card].getX()-28);
					x=iv[game.player][card].getX()-28;
					y=iv[game.player][card].getY();
					setCardRealPos(game.player,card,x,y,-1);
					break;	
			}
		}else{
			toast("Wrong card");
		}
		if(!playTheCards){
			mButtonPlayCard.setClickable(true);
			mButtonPlayTheCards.setClickable(true);
			mButtonStopPlayTheCards.setClickable(true);
		}
	}
	
	public void setImageClickable(boolean clickable,int player,int card){
		if(player == 0){
			if(clickable){
				for(int a=1;a<=4;a++){
					for(int b=1;b<=13;b++){
						if(!play) iv[a][b].setOnClickListener(imageClickListener[a][b]);
					}	
				}	
			}else{
				for(int a=1;a<=4;a++){
					for(int b=1;b<=13;b++){
						iv[a][b].setOnClickListener(null);
					}	
				}	
			}
		}else{
			if(card == 0){
				if(clickable){
					for(int b=1;b<=13;b++){
						if(!play) iv[player][b].setOnClickListener(imageClickListener[player][b]);
						if(play) iv[player][b].setOnClickListener(cardImageClickListener[player][b]);
					}	
				}else{
					for(int b=1;b<=13;b++){
						iv[player][b].setOnClickListener(null);
					}	
				}
			}else{
				if(clickable){
					if(!play) iv[player][card].setOnClickListener(imageClickListener[player][card]);
				}else{
					iv[player][card].setOnClickListener(null);
				}
			}	
		}
	}
	
	public void startGame(){
		//de geselecteerde kaart terug in startpositie zetten
		for(int a=1;a<=4;a++){
			for(int b=1;b<=13;b++){
				if (game.playerDeck[a].get(b-1).isSelected()){
					game.playerDeck[a].get(b-1).setSelected(false);
					setParam(false,0,-1);
					//iv[a][b].setX(game.playerDeck[a].get(b-1).getStartX());
					//iv[a][b].setY(game.playerDeck[a].get(b-1).getStartY());
					float x=game.playerDeck[a].get(b-1).getStartX();
					float y=game.playerDeck[a].get(b-1).getStartY();
					setCardRealPos(a,b,x,y,-1);
				}		
			}	
		}
		selected=true;
		savePreferences(lastSavedName+"_selected",true);
		lL.setVisibility(View.INVISIBLE);
		lLChoice.setVisibility(View.VISIBLE);
		mButtonZoekenNaar.setVisibility(View.INVISIBLE);
		mButtonChangeSearching.setVisibility(View.INVISIBLE);
		mTextViewDealer.setText("DEALER = " + String.valueOf(game.getDealer()));
		mTextViewTrumpCategory.setText("TRUMPCATEGORY = " + String.valueOf(game.getTrumpCategory()));
		game.restartChoice();
		game.checkTroel();
		game.startChoice();
		mDb.setChoices(lastSavedName,game);
		setTextNamePlayers();
		setTextChoicePlayer();
		game.setPlayer();
	}
	
	public void swapClickedDecks(int player){
		if (clickedPlayer==0){
			switch(player){
				case 1:
					mButtonChangePlayer1.setText("√1 ");
					break;
				case 2:
					mButtonChangePlayer2.setText("√2 ");
					break;
				case 3:
					mButtonChangePlayer3.setText("√3 ");
					break;
				case 4:
					mButtonChangePlayer4.setText("√4 ");
					break;
			}
			clickedPlayer=player;
		}else{
			game.swapDecks(game.playerDeck[player],game.playerDeck[clickedPlayer]);
			setCards(player);
			drawCards(player,true);
			setCards(clickedPlayer);
			drawCards(clickedPlayer,true);
			mButtonChangePlayer1.setText(" 1 ");
			mButtonChangePlayer2.setText(" 2 ");
			mButtonChangePlayer3.setText(" 3 ");
			mButtonChangePlayer4.setText(" 4 ");
			clickedPlayer=0;
		}	
	}
	
	public void setTextButtons(String savedName){
		switch(savedName){
			case "save1":
				mButtonLoad1.setText("√ LOAD1 ");
				mButtonLoad2.setText("   LOAD2 ");
				mButtonLoad3.setText("   LOAD3 ");
				break;
			case "save2":
				mButtonLoad1.setText("   LOAD1 ");
				mButtonLoad2.setText("√ LOAD2 ");
				mButtonLoad3.setText("   LOAD3 ");
				break;
			case "save3":
				mButtonLoad1.setText("   LOAD1 ");
				mButtonLoad2.setText("   LOAD2 ");
				mButtonLoad3.setText("√ LOAD3 ");
				break;
			case "save4":
				mButtonLoad1.setText("√ LOAD4 ");
				mButtonLoad2.setText("   ---- ");
				mButtonLoad3.setText("   ---- ");
				mButtonLoad2.setVisibility(View.INVISIBLE);
				mButtonLoad3.setVisibility(View.INVISIBLE);
				mButtonSave1.setText(" SAVE4 ");
				break;
		}
	}
	
	private void loadPreferences(){
		lastSavedName=settings.getString("lastSavedName","0");
		if (lastSavedName.compareTo("0")!=0){
			mainMenu=settings.getBoolean(lastSavedName+"_mainMenu",true);
			selected=settings.getBoolean(lastSavedName+"_selected",false);
			play=settings.getBoolean(lastSavedName+"_play",false);
			visibleCardsPlayer[1]=settings.getBoolean(lastSavedName+"_visibleCardsP1",true);
			visibleCardsPlayer[2]=settings.getBoolean(lastSavedName+"_visibleCardsP2",true);
			visibleCardsPlayer[3]=settings.getBoolean(lastSavedName+"_visibleCardsP3",true);
			visibleCardsPlayer[4]=settings.getBoolean(lastSavedName+"_visibleCardsP4",true);
			lockPlayer[1]=settings.getBoolean(lastSavedName+"_lockP1",false);
			lockPlayer[2]=settings.getBoolean(lastSavedName+"_lockP2",false);
			lockPlayer[3]=settings.getBoolean(lastSavedName+"_lockP3",false);
			lockPlayer[4]=settings.getBoolean(lastSavedName+"_lockP4",false);
		}	
	}

	private void savePreferences(String key,String value){
		SharedPreferences.Editor editor=settings.edit();
		editor.putString(key,value);
		editor.commit();
	}
	
	private void savePreferences(String key,boolean value){
		SharedPreferences.Editor editor=settings.edit();
		editor.putBoolean(key,value);
		editor.commit();
	}
	
	public void loadAll(final String savedName){
		progressDialog = ProgressDialog.show(this, "Eventjes wachten a.u.b.", "Laden gegevens...", true);
		new Thread(new Runnable() {
				@Override
				public void run()
				{//geen toast gebruiken onder deze run
					savePreferences("lastSavedName",savedName);
					loadPreferences();
					mDb.getPoints(savedName, game);
					if(!play){
						for(int a=1;a<=4;a++){
							game.setDeck(game.playerDeck[a],mDb.getCards(savedName, String.valueOf(a)));
						}	
						game.setDeck(game.waitingDeck[0],mDb.getCards(savedName, "waitingDeck0"));
						game.setDeck(game.waitingDeck[1],mDb.getCards(savedName, "waitingDeck1"));
						game.setDeck(game.gameDeck,mDb.getCards(savedName, "gameDeck"));
						game.setDeck(game.last4CardDeck,mDb.getCards(savedName, "last4CardDeck"));
						mDb.getParameters(savedName, game);	
					}
					if(selected && !play){
						game.restartChoice();//dit is nodig om playerchoice op -1 te zetten
						mDb.getChoices(lastSavedName,game);
					}
					if(play){
						mDb.getPlay(lastSavedName,game);
						game.setDeck(game.waitingDeck[0],mDb.getCards(savedName, "waitingDeck0→play"));
						game.setDeck(game.waitingDeck[1],mDb.getCards(savedName, "waitingDeck1→play"));
						game.setDeck(game.gameDeck,mDb.getCards(savedName, "gameDeck→play"));
						game.setDeck(game.last4CardDeck,mDb.getCards(savedName, "last4CardDeck→play"));
					}
					runOnUiThread(new Runnable() {
							@Override
							public void run()
							{
								if(play){
									for(int a=1;a<=4;a++){//dit hier moeten zetten anders problemen bij opstarten
										game.setDeck(game.playerDeck[a],mDb.getCards(savedName, String.valueOf(a) + "→play"));
									}
								}
								setCards(0);
								if(mainMenu){
									drawCards(0,false);
								}else{
									drawCards(0,true);
								}
								mButtonDealer.setText("DEALER = " + String.valueOf(game.getDealer()));
								mButtonTrumpCategory.setText("TRUMPCATEGORY = " + String.valueOf(game.getTrumpCategory()));
								setTextButtons(savedName);
								setTextPosPlayers();
								mTextViewTrump.setText("trump = " + String.valueOf(game.getTrumpCategory()));
								setImageClickable(true,0,0);
								if(lastSavedName.compareTo("save4")==0){
									mButtonSave2.setVisibility(View.INVISIBLE);
									mButtonSave3.setVisibility(View.INVISIBLE);
								}
								if(!mainMenu){
									lLStartMain.setVisibility(View.INVISIBLE);
									mButtonToast.setVisibility(View.VISIBLE);
									mButtonResetPoints.setVisibility(View.VISIBLE);
									mButtonLoad1.setVisibility(View.VISIBLE);
									mButtonLoad2.setVisibility(View.VISIBLE);
									mButtonLoad3.setVisibility(View.VISIBLE);
									mButtonBack2.setVisibility(View.VISIBLE);
									if (mDb.getCards("save2", "1").isEmpty()){mButtonLoad2.setVisibility(View.INVISIBLE);}
									if (mDb.getCards("save3", "1").isEmpty()){mButtonLoad3.setVisibility(View.INVISIBLE);}
									mTextViewTrump.setVisibility(View.VISIBLE);
									mTextViewOpenM.setVisibility(View.VISIBLE);
									mButtonSettings.setVisibility(View.VISIBLE);
									mTextView1.setVisibility(View.VISIBLE);
									mTextView2.setVisibility(View.VISIBLE);
									mTextView3.setVisibility(View.VISIBLE);
									mTextView4.setVisibility(View.VISIBLE);
									if(lastSavedName.compareTo("save4")!=0){//niet save4
										mButtonSaveAsExternalStorage.setVisibility(View.VISIBLE);
									}
								}
								if(!mainMenu && !selected && !play){
									setTextNameAndPointsPlayers();
									mButtonZoekenNaar.setVisibility(View.VISIBLE);
									mButtonChangeSearching.setVisibility(View.VISIBLE);
									lL.setVisibility(View.VISIBLE);
								}
								if(!mainMenu && selected && !play){
									lL.setVisibility(View.INVISIBLE);
									lLChoice.setVisibility(View.VISIBLE);
									mButtonZoekenNaar.setVisibility(View.INVISIBLE);
									mButtonChangeSearching.setVisibility(View.INVISIBLE);
									int a=game.player;
									game.player=game.dealer;
									setTextNamePlayers();
									do{
										game.setPlayer();
										setTextChoicePlayer();
									}while(game.player!=a);
									mTextViewDealer.setText("DEALER = " + String.valueOf(game.getDealer()));
									mTextViewTrumpCategory.setText("TRUMPCATEGORY = " + String.valueOf(game.getTrumpCategory()));
									game.setPlayer();
									if (game.getStartPlayer()==game.getPlayer()){
										mButtonChoiceOk.setVisibility(View.INVISIBLE);
										mButtonChoiceAll.setText("          PLAY          ");
									}
									setImageClickable(false,0,0);
								}
								if(!mainMenu && play){
									setTextPlayPlayers();
									lL.setVisibility(View.INVISIBLE);
									lLChoice.setVisibility(View.INVISIBLE);
									mButtonPlayCard.setVisibility(View.VISIBLE);
									mButtonPlayTheCards.setVisibility(View.VISIBLE);
									mButtonStopPlayTheCards.setVisibility(View.VISIBLE);
									mButtonRestart.setVisibility(View.VISIBLE);
									mButtonChooseAgain.setVisibility(View.VISIBLE);
									mButtonSave.setVisibility(View.VISIBLE);
									mButtonZoekenNaar.setVisibility(View.INVISIBLE);
									mButtonChangeSearching.setVisibility(View.INVISIBLE);
									setImageClickable(false,0,0);	
									setImageClickable(true,game.player,0);
									game.setFriendsEnemies();
									game.startGameSetVars();
									setCardPos(game.card+1);
									drawPlayedCards(0);
								}
								toast("Gegevens geladen");
								progressDialog.dismiss();
							}
						});
				}
		}).start();	
	}
	
	public void saveAll(final String savedName){
		progressDialog = ProgressDialog.show(this, "Eventjes wachten a.u.b.", "Opslaan gegevens...", true);
		new Thread(new Runnable() {
				@Override
				public void run()
				{
					if (savedName.compareTo("save4")==0){
						mDb.close();
						mDb=new CardsDatabase(MainActivity.this, true);
						mDb.open();
					}	
					savePreferences("lastSavedName",savedName);
					loadPreferences();
					//in een ListIterator kan je wel teruggaan (back)
					for(int a=1;a<=4;a++){
						mDb.setCards(savedName,String.valueOf(a) ,game.playerDeck[a].iterator());
					}
					mDb.setCards(savedName,"waitingDeck0" ,game.waitingDeck[0].iterator()); 
					mDb.setCards(savedName,"waitingDeck1" ,game.waitingDeck[1].iterator());
					mDb.setCards(savedName,"gameDeck" ,game.gameDeck.iterator());
					mDb.setCards(savedName,"last4CardDeck" ,game.last4CardDeck.iterator());
					mDb.setParameters(savedName, game);
					if (savedName.compareTo("save4")==0){
						if(selected && !play){
							int a=game.player-1;
							if(a==0){a=4;}
							game.player=game.dealer;
							do{
								game.setPlayer();
								mDb.setChoices(savedName,game);
							}while(game.player!=a);
						}
						if(play){
							for(int a=1;a<=4;a++){
								mDb.setChoices(lastSavedName,game);
								game.setPlayer();
							}	
							mDb.setCards(lastSavedName,"waitingDeck0→play" ,game.waitingDeck[0].iterator()); 
							mDb.setCards(lastSavedName,"waitingDeck1→play" ,game.waitingDeck[1].iterator());
							mDb.setCards(lastSavedName,"gameDeck→play" ,game.gameDeck.iterator());
							mDb.setCards(lastSavedName,"last4CardDeck→play" ,game.last4CardDeck.iterator());
							mDb.setPlay(lastSavedName,game);
							for(int a=1;a<=4;a++){
								mDb.setCards(lastSavedName,String.valueOf(a) + "→play" ,game.playerDeck[a].iterator());
							}	
						}
					}
					runOnUiThread(new Runnable() {
							@Override
							public void run()
							{
								setCards(0);
								//drawCards(0,false);
								mButtonDealer.setText("DEALER = " + String.valueOf(game.getDealer()));
								mButtonTrumpCategory.setText("TRUMPCATEGORY = " + String.valueOf(game.getTrumpCategory()));
								setTextButtons(savedName);
								toast(String.valueOf("player="+game.player));//test
								toast("Gegevens opgeslagen");
								if (savedName.compareTo("save4")==0){
									recreate();
								}	
								progressDialog.dismiss();
							}
					});
				}
		}).start();	
	}
	
	public void saveAllExternal(final String savedName){//hier wordt gedeeltelijk copy gemaakt van bvb.save1
		final Game game1=new Game(this);
		progressDialog = ProgressDialog.show(this, "Eventjes wachten a.u.b.", "Opslaan gegevens external...", true);
		new Thread(new Runnable() {
				@Override
				public void run()
				{
					for(int a=1;a<=4;a++){
						game1.setDeck(game1.playerDeck[a],mDb.getCards(lastSavedName, String.valueOf(a)));
					}	
					game1.setDeck(game1.waitingDeck[0],mDb.getCards(lastSavedName, "waitingDeck0"));
					game1.setDeck(game1.waitingDeck[1],mDb.getCards(lastSavedName, "waitingDeck1"));
					game1.setDeck(game1.gameDeck,mDb.getCards(lastSavedName, "gameDeck"));
					game1.setDeck(game1.last4CardDeck,mDb.getCards(lastSavedName, "last4CardDeck"));
					if (play){
						mDb.getParameters(lastSavedName, game1);
					}	
					mDb.close();
					mDb=new CardsDatabase(MainActivity.this, true);
					mDb.open();
					for(int a=1;a<=4;a++){
						mDb.setCards(savedName,String.valueOf(a) ,game1.playerDeck[a].iterator());
					}
					mDb.setCards(savedName,"waitingDeck0" ,game1.waitingDeck[0].iterator()); 
					mDb.setCards(savedName,"waitingDeck1" ,game1.waitingDeck[1].iterator());
					mDb.setCards(savedName,"gameDeck" ,game1.gameDeck.iterator());
					mDb.setCards(savedName,"last4CardDeck" ,game1.last4CardDeck.iterator());
					if(play){
						mDb.setParameters(savedName, game1);
					}else{
						mDb.setParameters(savedName, game);
					}
					if(selected && !play){
						int a=game.player-1;
						if(a==0){a=4;}
						game.player=game.dealer;
						do{
							game.setPlayer();
							mDb.setChoices(savedName,game);
						}while(game.player!=a);
					}
					if(play){
						for(int a=1;a<=4;a++){
							mDb.setChoices(savedName,game);
							game.setPlayer();
						}	
						mDb.setCards(savedName,"waitingDeck0→play" ,game.waitingDeck[0].iterator()); 
						mDb.setCards(savedName,"waitingDeck1→play" ,game.waitingDeck[1].iterator());
						mDb.setCards(savedName,"gameDeck→play" ,game.gameDeck.iterator());
						mDb.setCards(savedName,"last4CardDeck→play" ,game.last4CardDeck.iterator());
						mDb.setPlay(savedName,game);
					}
					runOnUiThread(new Runnable() {
							@Override
							public void run()
							{
								if(play){
									for(int a=1;a<=4;a++){//dit hier moeten zetten anders problemen bij opstarten
										mDb.setCards(savedName,String.valueOf(a) + "→play" ,game.playerDeck[a].iterator());
									}	
								}
								savePreferences("lastSavedName",savedName);
								toast("Gegevens opgeslagen external");
								if (savedName.compareTo("save4")==0){
								 	recreate();
								}
								progressDialog.dismiss();
							}
					});
				}
			}).start();	
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		lLStartMain.setY(screenHeight/2-(lLStartMain.getHeight()/2)-18);
		lL.setY(screenHeight/2-(lL.getHeight()/2)-18);
		lLChoice.setY(screenHeight/2-(lLChoice.getHeight()/2)-18);
		mButtonPlayCard.setX(985);
		mButtonPlayCard.setY(430);
		mButtonPlayTheCards.setX(940);
		mButtonPlayTheCards.setY(210);
		mButtonStopPlayTheCards.setX(940);
		mButtonStopPlayTheCards.setY(280);
		mButtonSave.setX(840);
		mButtonSave.setY(350);
		mButtonSettings.setX(180);
		mButtonSettings.setY(180);
		mButtonRestart.setX(340);
		mButtonRestart.setY(180);
		mButtonSaveAsExternalStorage.setX(1040);
		mButtonSaveAsExternalStorage.setY(-10);
		mButtonToast.setX(1010);
		mButtonToast.setY(100);
		mButtonResetPoints.setX(85);
		mButtonResetPoints.setY(600);
		mButtonLoad1.setX(215);
		mButtonLoad1.setY(240);
		mButtonLoad2.setX(215);
		mButtonLoad2.setY(300);
		mButtonLoad3.setX(215);
		mButtonLoad3.setY(360);
		mButtonBack2.setX(215);
		mButtonBack2.setY(420);
		mButtonZoekenNaar.setX(935);
		mButtonZoekenNaar.setY(215);
		mButtonChangeSearching.setX(935);
		mButtonChangeSearching.setY(270);
		mButtonChooseAgain.setX(770);
		mButtonChooseAgain.setY(430);
		mTextViewTrump.setX(18);
		mTextViewTrump.setY(700);
		mTextViewOpenM.setX(18);
		mTextViewOpenM.setY(-5);
		setTextPosPlayers();
	}
	
	public void showListView1(){
		 String[] values = new String[] { 
		 	"numberOfPlayedTrumpCards = " + game.getNumberOfPlayedCards(game.trumpCategory),
			"numberOfHatchedTrump(aantal uitgekomen troef) = " + game.getNumberOfHatchedTrump(),
		 	"playerdeck1 = " + game.playerDeck[1].size(),
			"playerdeck2 = " + game.playerDeck[2].size(),
			"playerdeck3 = " + game.playerDeck[3].size(),
			"playerdeck4 = " + game.playerDeck[4].size(),
			"gamedeck = " + game.gameDeck.size(),
			"last4CardDeck = " + game.last4CardDeck.size(),
			"waitingDeck0 = " + game.waitingDeck[0].size(),
			"waitingDeck1 = " + game.waitingDeck[1].size(),
			"lastSavedName = " + lastSavedName,
			lastSavedName+"_mainMenu = " + String.valueOf(mainMenu),
			lastSavedName+"_selected = " + String.valueOf(selected),
			lastSavedName+"_play = " + String.valueOf(play),
			"card = " + String.valueOf(game.card),
			"dealer = " + String.valueOf(game.dealer),
		 	"player = " + String.valueOf(game.player),
			"startplayer = " + String.valueOf(game.startPlayer),
			"oldstartplayer = " + String.valueOf(game.oldStartPlayer),
			"trumpCategory = " + String.valueOf(game.trumpCategory),
			"playerChoice1 = " + String.valueOf(game.playerChoice[1]),
			"playerChoice2 = " + String.valueOf(game.playerChoice[2]),
			"playerChoice3 = " + String.valueOf(game.playerChoice[3]),
			"playerChoice4 = " + String.valueOf(game.playerChoice[4]),
			"oldPlayerChoice1 = " + String.valueOf(game.oldPlayerChoice[1]),
			"oldPlayerChoice2 = " + String.valueOf(game.oldPlayerChoice[2]),
			"oldPlayerChoice3 = " + String.valueOf(game.oldPlayerChoice[3]),
			"oldPlayerChoice4 = " + String.valueOf(game.oldPlayerChoice[4]),
			"playerTrumpCategory1 = " + String.valueOf(game.playerTrumpCategory[1]),
			"playerTrumpCategory2 = " + String.valueOf(game.playerTrumpCategory[2]),
			"playerTrumpCategory3 = " + String.valueOf(game.playerTrumpCategory[3]),
			"playerTrumpCategory4 = " + String.valueOf(game.playerTrumpCategory[4]),
			"maxChoice = " + String.valueOf(game.maxChoice),
		 	"oldMaxChoice = " + String.valueOf(game.oldMaxChoice),
			"troelPlayer = " + String.valueOf(game.troelPlayer),
			"troelMeePlayer = " + String.valueOf(game.troelMeePlayer),
			"alleen = " + String.valueOf(game.alleen),
			"visibleCardsPlayer[1] = " + String.valueOf(visibleCardsPlayer[1]),
			"visibleCardsPlayer[2] = " + String.valueOf(visibleCardsPlayer[2]),
			"visibleCardsPlayer[3] = " + String.valueOf(visibleCardsPlayer[3]),
			"visibleCardsPlayer[4] = " + String.valueOf(visibleCardsPlayer[4]),
			"lockPlayer[1] = " + String.valueOf(lockPlayer[1]),
			"lockPlayer[2] = " + String.valueOf(lockPlayer[2]),
			"lockPlayer[3] = " + String.valueOf(lockPlayer[3]),
			"lockPlayer[4] = " + String.valueOf(lockPlayer[4]),
		  };

		 final ArrayList<String> list = new ArrayList<String>();
		 for (int i = 0; i < values.length; ++i) {list.add(values[i]);}
		 
		for(int a=1;a<=4;a++){
			for(int c=0;c<game.friend[a].length;c++){
				list.add("friend["+String.valueOf(a)+"]["+String.valueOf(c)+"] = "+String.valueOf(game.friend[a][c]));
			}	
			for(int c=0;c<game.enemy[a].length;c++){
				list.add("enemy["+String.valueOf(a)+"]["+String.valueOf(c)+"] = "+String.valueOf(game.enemy[a][c]));
			}	
		}	
		
		final ArrayList<String> list2 = new ArrayList<String>();
		//game.setCheckDeck();//test
	/*	for(int b=0;b<game.checkDeck.size();b++){//kan altijd terug gezet worden
			list2.add("kl="+String.valueOf(game.checkDeck.get(b).category)+" val="+
					  String.valueOf(game.checkDeck.get(b).value)+" pl="+
					  String.valueOf(game.checkDeck.get(b).player)+" sortv="+
					  String.valueOf(game.checkDeck.get(b).sortingValue));
		}*/
		if(play){
			for(int a=1;a<=4;a++){
				String str = "";
				for(int c=0;c<game.playCategory[a].size();c++){
					str+=String.valueOf(game.playCategory[a].get(c)) + " ";
				}	
				list2.add("playCategory["+String.valueOf(a)+"] = "+ str);
			}	
		/*	for(int a=1;a<=4;a++){
				String str = "";
				for(int c=0;c<game.getCategory[a].size();c++){
					str+=String.valueOf(game.getCategory[a].get(c)) + " ";
				}	
				list2.add("getCategory["+String.valueOf(a)+"] = "+ str);
			}	*/
			for(int a=1;a<=4;a++){
				String str = "";
				ArrayList<ObjPlayer> arList = game.getCategory(a);
				for(int c=0;c<arList.size();c++){
					str+=String.valueOf(arList.get(c).category) + " ";
				}	
				list2.add("getCategory["+String.valueOf(a)+"] = "+ str);
			}	
			//dit is een test
			String str = "";
			for(int c=0;c<game.getCategory.size();c++){
				str+=String.valueOf(game.getCategory.get(c).average) + " ";
			}	
			list2.add("average="+ str);
			str = "";
			for(int c=0;c<game.getCategory.size();c++){
				str+=String.valueOf(game.getCategory.get(c).remainingCards) + " ";
			}	
			list2.add("remainingCards="+ str);
			str = "";
			for(int c=0;c<game.getCategory.size();c++){
				str+=String.valueOf(game.getCategory.get(c).checkedCards) + " ";
			}	
			list2.add("checkedCards="+ str);
			str = "";
			for(int c=0;c<game.getCategory.size();c++){
				str+=String.valueOf(game.getCategory.get(c).underPlayer) + " ";
			}	
			list2.add(str+"=underPlayer");
			str = "";
			for(int c=0;c<game.getCategory.size();c++){
				str+=String.valueOf(game.getCategory.get(c).player) + " ";
			}	
			list2.add(str+"=player");
			str = "";
			for(int c=0;c<game.getCategory.size();c++){
				str+=String.valueOf(game.getCategory.get(c).category) + " ";
			}	
			list2.add(str+"=category");
			////////////////
			list2.add("turn(beurt) = "+String.valueOf(game.turn));
			list2.add("round = "+String.valueOf(game.round));
			for(int a=1;a<=4;a++){
				for(int b=1;b<=4;b++){
					list2.add("okay["+String.valueOf(a)+"]["+String.valueOf(b)+"] = "+
							  String.valueOf(game.okay[a][b]));
				}	
			}		
			for(int a=1;a<=4;a++){
				for(int b=1;b<=4;b++){
					for(int c=1;c<=4;c++){
						if(a!=b){
							list2.add("followed["+String.valueOf(a)+"]["+String.valueOf(b)+"]["+String.valueOf(c)+"] = "+
									  String.valueOf(game.followed[a][b][c]));
						}
					}	
				}
			}	
		}
		list.addAll(0,list2);
		
		final ArrayAdapter adapter = new ArrayAdapter(this,
		 android.R.layout.simple_list_item_1, list);
		 mListView1.setAdapter(adapter);
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id)
	{
		//LayoutInflater layoutInflater = LayoutInflater.from(this);
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
			case DIALOG_SEARCH:
				builder
					.setTitle("Zoeken naar... "+mButtonChangeSearching.getText()+"... gevonden!!!!!")
					.setCancelable(false)
				/*	.setOnCancelListener(new DialogInterface.OnCancelListener(){

											 @Override
											 public void onCancel(DialogInterface p1)
											 {
												 toast("Voedsellijst niet verwijderd");
											 }
					})	*/
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							progressDialog = ProgressDialog.show(MainActivity.this, "Eventjes wachten a.u.b.", "Opslaan gegevens...", true);
							new Thread(new Runnable() {
									
									@Override
									public void run()
									{
										for(int a=1;a<=4;a++){
											mDb.setCards(lastSavedName ,String.valueOf(a) ,game.playerDeck[a].iterator());
										}
										runOnUiThread(new Runnable() {
												@Override
												public void run()
												{
													progressDialog.dismiss();
													recreate();
												}
										});		
									}
							}).start();			
						}
					})
					.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int id) {
							dialog.cancel();
							recreate();
						}
					});
				AlertDialog dialog = builder.create();
				dialog.show();
				break;
		}			
		return super.onCreateDialog(id);
	}

	@Override
	protected void onStart()
	{
		toast("onstart");
		setCards(0);
		if(play){
			setCardPos(game.card+1);
			//zien bij openmisere de openmiserespelers er niet meer aan kunnen,einde spel dus
			if(game.gameEndOpenmisere()){mTextViewOpenM.setText("openmisere ok");}
		}
		super.onStart();
	}
	
	public void setTextPosPlayers(){//dit ook aanpassen in het uiteindelijke spel
		mTextView1.setX(316*screenWidth/1280);//316
		mTextView1.setY(480*screenHeight/736);//480
		mTextView2.setX(-182*screenWidth/1280);
		mTextView2.setY(240*screenHeight/736);
		mTextView3.setX(710*screenWidth/1280);
		mTextView3.setY(155*screenHeight/736);
		mTextView4.setX(1074*screenWidth/1280);
		mTextView4.setY(180*screenHeight/736);
	}
	
	public void setTextNamePlayers(){
		mTextView1.setText("Speler1");
		mTextView2.setText("Speler2"); 
		mTextView3.setText("Speler3");  
		mTextView4.setText("Speler4"); 
	}
	
	public void setTextNameAndPointsPlayers(){
		mTextView1.setText("Speler1"+"("+String.valueOf(game.playerPoints[1])+")");
		mTextView2.setText("Speler2"+"("+String.valueOf(game.playerPoints[2])+")");
		mTextView3.setText("Speler3"+"("+String.valueOf(game.playerPoints[3])+")");
		mTextView4.setText("Speler4"+"("+String.valueOf(game.playerPoints[4])+")");
	}
	
	public void	setTextChoicePlayer(){
		switch(game.player){
			case 1:
				mTextView1.setText(game.getChoice());break;
			case 2:
				mTextView2.setText(game.getChoice());break;
			case 3:
				mTextView3.setText(game.getChoice());break;	
			case 4:
				mTextView4.setText(game.getChoice());break;	
		}
	}
	
	public void	setTextPlayPlayers(){
		for(int a=1;a<=4;a++){
			game.setPlayer();
			switch(game.player){
				case 1:
					mTextView1.setText(game.getPlay());break;
				case 2:
					mTextView2.setText(game.getPlay());break;
				case 3:
					mTextView3.setText(game.getPlay());break;	
				case 4:
					mTextView4.setText(game.getPlay());break;	
			}
		}
	}
	
	public void setParam(boolean selected, int player, int card){
		otherCardIsSelected=selected;
		otherCardPlayer=player;
		otherCard=card;
	}
	
	public void drawPlayedCards(int player){//0=alle spelers
		int b=game.startPlayer;
		if(player==0){
			for(int a=0;a<game.gameDeck.size();a++){
				iv[0][a+1].setImageResource(game.gameDeck.get(a).getCardReference());
				//iv[0][a+1].setX(posXPlayedCard[b]);
				//iv[0][a+1].setY(posYPlayedCard[b]);
				float x=posXPlayedCard[b];
				float y=posYPlayedCard[b];
				setCardRealPos(0,a+1,x,y,-1);
				b++;if(b==5) b=1;
			}
		}else{
			for(int a=0;a<game.gameDeck.size();a++){
				if(b==player){
					iv[0][a+1].setImageResource(game.gameDeck.get(a).getCardReference());
					//iv[0][a+1].setX(posXPlayedCard[b]);
					//iv[0][a+1].setY(posYPlayedCard[b]);
					float x=posXPlayedCard[b];
					float y=posYPlayedCard[b];
					setCardRealPos(0,a+1,x,y,-1);
				}
				b++;if(b==5) b=1;
			}
			if(game.gameDeck.size()==4){
				for(int a=0;a<game.gameDeck.size();a++){
					iv[0][a+5].setImageResource(game.gameDeck.get(a).getCardReference());
					//iv[0][a+5].setX(posXPlayedCard[b]);
					//iv[0][a+5].setY(posYPlayedCard[b]);
					float x=posXPlayedCard[b];
					float y=posYPlayedCard[b];
					setCardRealPos(0,a+5,x,y,-1);
					b++;if(b==5) b=1;
				}
				for(int a=1;a<=4;a++){
					//iv[0][a].setX(-200*xVerhouding(iv[0][a]));
					float x=-200;
					float y=iv[0][a].getY();
					setCardRealPos(0,a,x,y,-1);
				}	
			}	
		}
	}
	
	public void setCards(int player){
		//breedte=182 en hoogte=260 => getWidth en getHeight is hier nog steeds 0
		//screenWidth=1280, screenHeight=736
		float deltaX=0;
		float deltaY=0;
		float[] startX = new float[5];
		float[] startY = new float[5];
		int numberOfCards;
		for(int a=1;a<=4;a++){
			if (player==0){
				numberOfCards=game.playerDeck[a].size();
			}else{
				numberOfCards=game.playerDeck[player].size();
				a=player;
			}
			switch(screenWidth){
				case 1280:
					if(screenHeight==736){
						deltaX=55;
						deltaY=40;
						startX[1]=1280/2 - 182/2 - (deltaX/2 * (numberOfCards-1));//screenWidth/2 - 200/2 - (deltaX/2 * (numberOfCards-1));
						startY[1]=520;//
						startX[2]=-90;//-65
						startY[2]=736/2 - 290/2 - (deltaY/2 * (numberOfCards-1));//260
						startX[3]=1280/2 - 215/2 + (deltaX/2 * (numberOfCards-1));//182
						startY[3]=-140;//-110
						startX[4]=1160;//1144
						startY[4]=736/2 - 260/2 + (deltaY/2 * (numberOfCards-1)); 
					}
					break;
				case 1440:		
					if(screenHeight==810){
						deltaX=55;
						deltaY=40;
						startX[1]=549 - (deltaX/2 * (numberOfCards-1));
						startY[1]=520;//
						startX[2]=-90;//
						startY[2]=223 - (deltaY/2 * (numberOfCards-1));
						startX[3]=533 + (deltaX/2 * (numberOfCards-1));
						startY[3]=-140;//
						startX[4]=1160;//
						startY[4]=238 + (deltaY/2 * (numberOfCards-1)); 
					}
					break;
				default:
					deltaX=55;
					deltaY=40;
					startX[1]=549 - (deltaX/2 * (numberOfCards-1));
					startY[1]=820;//
					startX[2]=-190;//
					startY[2]=223 - (deltaY/2 * (numberOfCards-1));
					startX[3]=533 + (deltaX/2 * (numberOfCards-1));
					startY[3]=-140;//
					startX[4]=1760;//
					startY[4]=238 + (deltaY/2 * (numberOfCards-1)); 
			}
			for(int b=1;b<=13;b++){
				if (b<=numberOfCards){
					//iv[a][b].setX(startX[a]);
					//iv[a][b].setY(startY[a]);
					game.playerDeck[a].get(b-1).setStartX(startX[a]);
					game.playerDeck[a].get(b-1).setStartY(startY[a]);
					game.playerDeck[a].get(b-1).setX(startX[a]);
					game.playerDeck[a].get(b-1).setY(startY[a]);
					game.playerDeck[a].get(b-1).setSelected(false);
					if(visibleCardsPlayer[a]){
						iv[a][b].setImageResource(game.playerDeck[a].get(b-1).getCardReference());
					}else{
						iv[a][b].setImageResource(R.drawable.card_back);
					}
					//if (game.playerDeck[a].get(b-1).isSelected()){toast("selected after drawcards??????");}//test
					float x=startX[a];
					float y=startY[a];
					switch(a){
						case 1:
							setCardRealPos(a,b,x,y,-1);
							startX[a]+=deltaX;
							break;
						case 2:
							setCardRealPos(a,b,x,y,90);
							//iv[a][b].setRotation(90);
							startY[a]+=deltaY;
							break;	
						case 3:
							setCardRealPos(a,b,x,y,-1);
							startX[a]-=deltaX;
							break;
						case 4:
							setCardRealPos(a,b,x,y,90);
							//iv[a][b].setRotation(90);
							startY[a]-=deltaY;
							break;	
					}		
				}
			}
			if (player!=0){break;}
		}		
	}
	            //in uiteindelijke spel omzetten naar ansetplaycard
	public void setCardRealPos(int player, int card, float x, float y, int rotation){
		//hier beweegt schaduw ook mee, player=0=heeft niets te maken met alle spelers
		//float cardWidth = iv[player][card].getDrawable().getIntrinsicWidth();
		//float cardHeight = iv[player][card].getDrawable().getIntrinsicHeight();
		//float shadowWidth = ivShadow[player][card].getDrawable().getIntrinsicWidth();
		//float shadowHeight = ivShadow[player][card].getDrawable().getIntrinsicHeight();
		//float x1=(shadowWidth-cardWidth)/2;
		//float y1=(shadowHeight-cardHeight)/2;
		//x1=15.5 en y1=15.5
		//shadowWidth en cardWidth      shadowHeight en cardHeight
		        //213 en 182=31                //291 en 260=31
		 //op gsm 360 en 308=52                  492 en 440=52
		//float xx,yy;                                          
		//xx=(x-15)*xVerhouding(iv[player][card],213);//(x-15)
		//yy=(y-y1)*yVerhouding(iv[player][card]);//y-10
		//ivShadow[player][card].setX(xx);
		//ivShadow[player][card].setY(yy);
		//if(rotation!=-1){ivShadow[player][card].setRotation(rotation);}
		//xx=x*xVerhouding(iv[player][card],182);
		//yy=y*yVerhouding(iv[player][card]);
		iv[player][card].setX(x);
		iv[player][card].setY(y);
		if(rotation!=-1){iv[player][card].setRotation(rotation);}
	}	
	
	public void drawCards(int player, boolean visible){
		if (player==0){
			for(int a=1;a<=4;a++){
				int numberOfCards=game.playerDeck[a].size();
				for(int b=1;b<=13;b++){
					if (b>numberOfCards){
						//ivShadow[a][b].setVisibility(ivShadow[a][b].INVISIBLE);
						iv[a][b].setVisibility(iv[a][b].INVISIBLE);
					}else{
						if (visible){
							//ivShadow[a][b].setVisibility(ivShadow[a][b].VISIBLE);
							iv[a][b].setVisibility(iv[a][b].VISIBLE);	
						}else{
							//ivShadow[a][b].setVisibility(ivShadow[a][b].INVISIBLE);
							iv[a][b].setVisibility(iv[a][b].INVISIBLE);
						}
					}	
				}				
			}
		}else{
			int numberOfCards=game.playerDeck[player].size();
			for(int b=1;b<=13;b++){
				if (b>numberOfCards){
					//ivShadow[player][b].setVisibility(ivShadow[player][b].INVISIBLE);
					iv[player][b].setVisibility(iv[player][b].INVISIBLE);
				}else{
					if (visible){
						//ivShadow[player][b].setVisibility(ivShadow[player][b].VISIBLE);
						iv[player][b].setVisibility(iv[player][b].VISIBLE);	
					}else{
						//ivShadow[player][b].setVisibility(ivShadow[player][b].INVISIBLE);
						iv[player][b].setVisibility(iv[player][b].INVISIBLE);
					}
				}	
			}		
		}
	}
	
	public void showPopupWindow(){
		LayoutInflater layoutInflater = (LayoutInflater)
			getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
		View popupView = layoutInflater.inflate(R.layout.params, null); 
		final PopupWindow popupWindow = new PopupWindow(
			popupView, 
			(int)(screenWidth/2.7f),   //LayoutParams.WRAP_CONTENT  //(int)(screenWidth/3.4f)
			screenHeight - 100 //100
		); 
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable());//dient als cancelable
		popupWindow.setOutsideTouchable(true);

		mListView1 = (ListView)popupView.findViewById(R.id.listview1);
		showListView1();

		mCheckBoxZichtbaarSp[1] = (CheckBox)popupView.findViewById(R.id.zichtbaarsp1);
		mCheckBoxZichtbaarSp[2] = (CheckBox)popupView.findViewById(R.id.zichtbaarsp2);
		mCheckBoxZichtbaarSp[3] = (CheckBox)popupView.findViewById(R.id.zichtbaarsp3);
		mCheckBoxZichtbaarSp[4] = (CheckBox)popupView.findViewById(R.id.zichtbaarsp4);

		mCheckBoxZichtbaarSp[1].setChecked(visibleCardsPlayer[1]);
		mCheckBoxZichtbaarSp[2].setChecked(visibleCardsPlayer[2]);
		mCheckBoxZichtbaarSp[3].setChecked(visibleCardsPlayer[3]);
		mCheckBoxZichtbaarSp[4].setChecked(visibleCardsPlayer[4]);

		final ImageView[] iv=new ImageView[4];
	 	iv[0]=(ImageView)popupView.findViewById(R.id.last4carddeck_card1);
		iv[1]=(ImageView)popupView.findViewById(R.id.last4carddeck_card2);
		iv[2]=(ImageView)popupView.findViewById(R.id.last4carddeck_card3);
		iv[3]=(ImageView)popupView.findViewById(R.id.last4carddeck_card4);
		if(game.last4CardDeck.isEmpty()){
			for(int a=0;a<=3;a++){
				iv[a].setVisibility(View.INVISIBLE);
			}	
		}
		for(int a=0;a<game.last4CardDeck.size();a++){
			iv[a].setVisibility(View.VISIBLE);
			iv[a].setImageResource(game.last4CardDeck.get(a).getCardReference());
		}
		
		final int[] posXCard=new int[5];
		int[] posYCard=new int[5];
		posXCard[1]=0;//opgepast als je get doet dan krijg je steeds 0
		posYCard[1]=50;//dit hebben we op een andere manier moeten oplossen
		posXCard[2]=-50;
		posYCard[2]=0;
		posXCard[3]=0;
		posYCard[3]=-50;
		posXCard[4]=50;
		posYCard[4]=0;
		
		int c=game.oldStartPlayer;
		for(int b=0;b<=3;b++){
			int v=0;
			if(b==0)v=200;
			if(b==1)v=0;
			if(b==2)v=-200;
			if(b==3)v=-400;
			iv[b].setX(v+posXCard[c]-220);
			iv[b].setY(posYCard[c]);	
			c++;if(c==5)c=1;
		}
		
		for(int b=1;b<=4;b++){
			final int a=b;
			mCheckBoxZichtbaarSp[a].setOnCheckedChangeListener(new OnCheckedChangeListener(){
	
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if(mCheckBoxZichtbaarSp[a].isChecked()){
						if(a==1)savePreferences(lastSavedName+"_visibleCardsP1",true);
						if(a==2)savePreferences(lastSavedName+"_visibleCardsP2",true);
						if(a==3)savePreferences(lastSavedName+"_visibleCardsP3",true);
						if(a==4)savePreferences(lastSavedName+"_visibleCardsP4",true);
						visibleCardsPlayer[a]=true;
					}else{
						if(a==1)savePreferences(lastSavedName+"_visibleCardsP1",false);
						if(a==2)savePreferences(lastSavedName+"_visibleCardsP2",false);
						if(a==3)savePreferences(lastSavedName+"_visibleCardsP3",false);
						if(a==4)savePreferences(lastSavedName+"_visibleCardsP4",false);
						visibleCardsPlayer[a]=false;
					}
					setCards(a);
					drawCards(a,true);
				}
			});
		}	
		
		popupView.setOnTouchListener(new OnTouchListener(){
				int orgX, orgY;
				int offsetX, offsetY;

				@Override
				public boolean onTouch(View v, MotionEvent event)
				{
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							orgX = (int) event.getX();
							orgY = (int) event.getY();
							break;
						case MotionEvent.ACTION_MOVE:
							offsetX = (int)event.getRawX() - orgX;
							offsetY = (int)event.getRawY() - orgY;
							popupWindow.update(offsetX, offsetY, -1, -1, true);
							break;
					}
					return true;
				}
			});
		//popupWindow.showAsDropDown(mButtonSettings, 0, 0);
		popupWindow.showAtLocation(lLRootMain,Gravity.LEFT | Gravity.TOP,20,50); //Gravity.LEFT | Gravity.TOP
	}
	
	public void setViews(){
		//schadows
		//ivShadow[0][1]=findViewById(R.id.player0_card1_shadow);
		//ivShadow[0][2]=findViewById(R.id.player0_card2_shadow);
		//ivShadow[0][3]=findViewById(R.id.player0_card3_shadow);
		/*ivShadow[0][4]=findViewById(R.id.player0_card4_shadow);

		ivShadow[1][1]=findViewById(R.id.player1_card1_shadow);
		ivShadow[1][2]=findViewById(R.id.player1_card2_shadow);
		ivShadow[1][3]=findViewById(R.id.player1_card3_shadow);
		ivShadow[1][4]=findViewById(R.id.player1_card4_shadow);
		ivShadow[1][5]=findViewById(R.id.player1_card5_shadow);
		ivShadow[1][6]=findViewById(R.id.player1_card6_shadow);
		ivShadow[1][7]=findViewById(R.id.player1_card7_shadow);
		ivShadow[1][8]=findViewById(R.id.player1_card8_shadow);
		ivShadow[1][9]=findViewById(R.id.player1_card9_shadow);
		ivShadow[1][10]=findViewById(R.id.player1_card10_shadow);
		ivShadow[1][11]=findViewById(R.id.player1_card11_shadow);
		ivShadow[1][12]=findViewById(R.id.player1_card12_shadow);
		ivShadow[1][13]=findViewById(R.id.player1_card13_shadow);

		ivShadow[2][1]=findViewById(R.id.player2_card1_shadow);
		ivShadow[2][2]=findViewById(R.id.player2_card2_shadow);
		ivShadow[2][3]=findViewById(R.id.player2_card3_shadow);
		ivShadow[2][4]=findViewById(R.id.player2_card4_shadow);
		ivShadow[2][5]=findViewById(R.id.player2_card5_shadow);
		ivShadow[2][6]=findViewById(R.id.player2_card6_shadow);
		ivShadow[2][7]=findViewById(R.id.player2_card7_shadow);
		ivShadow[2][8]=findViewById(R.id.player2_card8_shadow);
		ivShadow[2][9]=findViewById(R.id.player2_card9_shadow);
		ivShadow[2][10]=findViewById(R.id.player2_card10_shadow);
		ivShadow[2][11]=findViewById(R.id.player2_card11_shadow);
		ivShadow[2][12]=findViewById(R.id.player2_card12_shadow);
		ivShadow[2][13]=findViewById(R.id.player2_card13_shadow);

		ivShadow[3][1]=findViewById(R.id.player3_card1_shadow);
		ivShadow[3][2]=findViewById(R.id.player3_card2_shadow);
		ivShadow[3][3]=findViewById(R.id.player3_card3_shadow);
		ivShadow[3][4]=findViewById(R.id.player3_card4_shadow);
		ivShadow[3][5]=findViewById(R.id.player3_card5_shadow);
		ivShadow[3][6]=findViewById(R.id.player3_card6_shadow);
		ivShadow[3][7]=findViewById(R.id.player3_card7_shadow);
		ivShadow[3][8]=findViewById(R.id.player3_card8_shadow);
		ivShadow[3][9]=findViewById(R.id.player3_card9_shadow);
		ivShadow[3][10]=findViewById(R.id.player3_card10_shadow);
		ivShadow[3][11]=findViewById(R.id.player3_card11_shadow);
		ivShadow[3][12]=findViewById(R.id.player3_card12_shadow);
		ivShadow[3][13]=findViewById(R.id.player3_card13_shadow);

		ivShadow[4][1]=findViewById(R.id.player4_card1_shadow);
		ivShadow[4][2]=findViewById(R.id.player4_card2_shadow);
		ivShadow[4][3]=findViewById(R.id.player4_card3_shadow);
		ivShadow[4][4]=findViewById(R.id.player4_card4_shadow);
		ivShadow[4][5]=findViewById(R.id.player4_card5_shadow);
		ivShadow[4][6]=findViewById(R.id.player4_card6_shadow);
		ivShadow[4][7]=findViewById(R.id.player4_card7_shadow);
		ivShadow[4][8]=findViewById(R.id.player4_card8_shadow);
		ivShadow[4][9]=findViewById(R.id.player4_card9_shadow);
		ivShadow[4][10]=findViewById(R.id.player4_card10_shadow);
		ivShadow[4][11]=findViewById(R.id.player4_card11_shadow);
		ivShadow[4][12]=findViewById(R.id.player4_card12_shadow);
		ivShadow[4][13]=findViewById(R.id.player4_card13_shadow);

		ivShadow[0][5]=findViewById(R.id.player0_card5_shadow);
		ivShadow[0][6]=findViewById(R.id.player0_card6_shadow);
		ivShadow[0][7]=findViewById(R.id.player0_card7_shadow);
		ivShadow[0][8]=findViewById(R.id.player0_card8_shadow);*/
		
		//cards
		iv[0][1]=findViewById(R.id.player0_card1);
		iv[0][2]=findViewById(R.id.player0_card2);
		iv[0][3]=findViewById(R.id.player0_card3);
		iv[0][4]=findViewById(R.id.player0_card4);
		
		iv[1][1]=findViewById(R.id.player1_card1);
		iv[1][2]=findViewById(R.id.player1_card2);
		iv[1][3]=findViewById(R.id.player1_card3);
		iv[1][4]=findViewById(R.id.player1_card4);
		iv[1][5]=findViewById(R.id.player1_card5);
		iv[1][6]=findViewById(R.id.player1_card6);
		iv[1][7]=findViewById(R.id.player1_card7);
		iv[1][8]=findViewById(R.id.player1_card8);
		iv[1][9]=findViewById(R.id.player1_card9);
		iv[1][10]=findViewById(R.id.player1_card10);
		iv[1][11]=findViewById(R.id.player1_card11);
		iv[1][12]=findViewById(R.id.player1_card12);
		iv[1][13]=findViewById(R.id.player1_card13);
		
		iv[2][1]=findViewById(R.id.player2_card1);
		iv[2][2]=findViewById(R.id.player2_card2);
		iv[2][3]=findViewById(R.id.player2_card3);
		iv[2][4]=findViewById(R.id.player2_card4);
		iv[2][5]=findViewById(R.id.player2_card5);
		iv[2][6]=findViewById(R.id.player2_card6);
		iv[2][7]=findViewById(R.id.player2_card7);
		iv[2][8]=findViewById(R.id.player2_card8);
		iv[2][9]=findViewById(R.id.player2_card9);
		iv[2][10]=findViewById(R.id.player2_card10);
		iv[2][11]=findViewById(R.id.player2_card11);
		iv[2][12]=findViewById(R.id.player2_card12);
		iv[2][13]=findViewById(R.id.player2_card13);
		
		iv[3][1]=findViewById(R.id.player3_card1);
		iv[3][2]=findViewById(R.id.player3_card2);
		iv[3][3]=findViewById(R.id.player3_card3);
		iv[3][4]=findViewById(R.id.player3_card4);
		iv[3][5]=findViewById(R.id.player3_card5);
		iv[3][6]=findViewById(R.id.player3_card6);
		iv[3][7]=findViewById(R.id.player3_card7);
		iv[3][8]=findViewById(R.id.player3_card8);
		iv[3][9]=findViewById(R.id.player3_card9);
		iv[3][10]=findViewById(R.id.player3_card10);
		iv[3][11]=findViewById(R.id.player3_card11);
		iv[3][12]=findViewById(R.id.player3_card12);
		iv[3][13]=findViewById(R.id.player3_card13);
		
		iv[4][1]=findViewById(R.id.player4_card1);
		iv[4][2]=findViewById(R.id.player4_card2);
		iv[4][3]=findViewById(R.id.player4_card3);
		iv[4][4]=findViewById(R.id.player4_card4);
		iv[4][5]=findViewById(R.id.player4_card5);
		iv[4][6]=findViewById(R.id.player4_card6);
		iv[4][7]=findViewById(R.id.player4_card7);
		iv[4][8]=findViewById(R.id.player4_card8);
		iv[4][9]=findViewById(R.id.player4_card9);
		iv[4][10]=findViewById(R.id.player4_card10);
		iv[4][11]=findViewById(R.id.player4_card11);
		iv[4][12]=findViewById(R.id.player4_card12);
		iv[4][13]=findViewById(R.id.player4_card13);
		
		iv[0][5]=findViewById(R.id.player0_card5);
		iv[0][6]=findViewById(R.id.player0_card6);
		iv[0][7]=findViewById(R.id.player0_card7);
		iv[0][8]=findViewById(R.id.player0_card8);
	}

	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void onPause()
	{
		//saveByExit();//om te testen
		//opgepast run werkt hier niet
		super.onPause();
		//toast("onpause");
	}

	@Override
	protected void onDestroy()
	{
		toast("onDestroy");
		super.onDestroy();
		if (mDb != null) {
			mDb.close();
		}
	}

	@Override
	public void onBackPressed()
	{
		if(mainMenu){
			super.onBackPressed();
		}else{
			mainMenu=true;
			savePreferences(lastSavedName+"_mainMenu",true);
			recreate();
		}
	}
	
	// Method to start the service
	public void startService(View view) {//niet in gebruik
		startService(new Intent(getBaseContext(), MyService.class));
	}

	// Method to stop the service
	public void stopService(View view) {//niet in gebruik
		stopService(new Intent(getBaseContext(), MyService.class));
	}
	
	public void toast(String pp){
		if(game.onToast){
			Toast.makeText(this,pp,Toast.LENGTH_SHORT).show();
		}	
	}
}
