package com.mycompany.WhistTest;
import android.database.sqlite.*;
import java.util.*;
import android.provider.*;
import android.database.*;
import android.content.*;
import android.widget.*;
import android.util.*;
import android.os.*;
//database is vooral om snel te zoeken
//sharedpreferences is om simpele variabelen op te slaan

public class CardsDatabase
{
    private static final String TAG = "Database";

    //The columns we'll include in the tables
	public static final String KEY_SAVED_NAME = "SavedName";

	public static final String KEY_INGAME = "InGame";
	public static final String KEY_VALUE = "Value";
	public static final String KEY_CARDREFERENCE = "CardReference";
	public static final String KEY_CATEGORY = "Category";

	public static final String KEY_DEALER = "Dealer";
	public static final String KEY_STARTPLAYER = "StartPlayer";
	public static final String KEY_OLDSTARTPLAYER = "OldStartPlayer";
	public static final String KEY_PLAYER = "Player";
	public static final String KEY_TRUMPCATEGORY = "TrumpCategory";

	public static final String KEY_POINTS = "Points";
	public static final String KEY_CHOICE = "Choice";													
	public static final String KEY_OLDCHOICE = "OldChoice";		
	public static final String KEY_TRUMPCAT = "TrumpCategory";		

	public static final String KEY_FOLLOWED ="Followed";
	public static final String KEY_OTHERPLAYER ="OtherPlayer";



	public static final String KEY_OBJPLAYER = "ObjPlayer";
	public static final String KEY_OBJCATEGORY = "ObjCategory";
	public static final String KEY_OBJAVERAGE = "ObjAverage";
	public static final String KEY_OBJREMAININGCARDS = "ObjRemainingCards";
	public static final String KEY_OBJCHECKEDCARDS = "ObjCheckedCards";
	public static final String KEY_OBJUNDERPLAYER = "ObjUnderPlayer";

	public static final String KEY_PLAYCATEGORY = "PlayCategory";

	public static final String KEY_OKAY = "Okay";

	public static final String KEY_MAXCHOICE = "MaxChoice";
	public static final String KEY_OLDMAXCHOICE = "OldMaxChoice";
	public static final String KEY_TROELPLAYER = "TroelPlayer";
	public static final String KEY_TROELMEEPLAYER = "TroelMeePlayer";
	public static final String KEY_ALLEEN = "Alleen";

	public static final String KEY_CARD = "Card";

    private static final String FTS_VIRTUAL_TABLE_CARDS = "FTSCards";
	private static final String FTS_VIRTUAL_TABLE2 = "FTS2";
	private static final String FTS_VIRTUAL_TABLE3 = "FTS3";
	private static final String FTS_VIRTUAL_TABLE_PLAYERS = "FTSPlayers";
	private static final String FTS_VIRTUAL_TABLE_OBJECTS = "FTSObjects";
	private static final String FTS_VIRTUAL_TABLE_FOLLOWED = "FTSFollowed";
	private static final String FTS_VIRTUAL_TABLE_PLAYPARAM = "FTSPlayCategory";


    private static final int DATABASE_VERSION = 1;

    private FoodOpenHelper mDbHelper;
	private SQLiteDatabase mDb;
    private static final HashMap<String,String> mColumnMap = buildColumnMap();
	private static final HashMap<String,String> mColumnMap2 = buildColumnMap2();
	private static final HashMap<String,String> mColumnMap3 = buildColumnMap3();
	private static final HashMap<String,String> mColumnMapPlayers = buildColumnMapPlayers();
	private static final HashMap<String,String> mColumnMapObjects = buildColumnMapObjects();
	private static final HashMap<String,String> mColumnMapFollowed = buildColumnMapFollowed();
	private static final HashMap<String,String> mColumnMapPlayParam = buildColumnMapPlayParam();

    private static HashMap<String,String> buildColumnMap()
	{
        HashMap<String,String> map = new HashMap<String,String>();
		map.put(KEY_INGAME, KEY_INGAME);
        map.put(KEY_SAVED_NAME, KEY_SAVED_NAME);
		map.put(KEY_VALUE, KEY_VALUE);
		map.put(KEY_CARDREFERENCE, KEY_CARDREFERENCE);//
		map.put(KEY_CATEGORY, KEY_CATEGORY);//
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
        return map;
    }

	private static HashMap<String,String> buildColumnMap2()
	{
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(KEY_SAVED_NAME, KEY_SAVED_NAME);
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
		map.put(KEY_DEALER, KEY_DEALER);
		map.put(KEY_STARTPLAYER, KEY_STARTPLAYER);
		map.put(KEY_PLAYER, KEY_PLAYER);
		map.put(KEY_TRUMPCATEGORY, KEY_TRUMPCATEGORY);
        return map;
    }

	private static HashMap<String,String> buildColumnMap3()
	{
        HashMap<String,String> map = new HashMap<String,String>();
		map.put(KEY_SAVED_NAME, KEY_SAVED_NAME);
		map.put(KEY_DEALER, KEY_DEALER);
		map.put(KEY_STARTPLAYER, KEY_STARTPLAYER);
		map.put(KEY_OLDSTARTPLAYER, KEY_OLDSTARTPLAYER);
		map.put(KEY_PLAYER, KEY_PLAYER);
		map.put(KEY_TRUMPCATEGORY, KEY_TRUMPCATEGORY);
		map.put(KEY_MAXCHOICE, KEY_MAXCHOICE);
		map.put(KEY_OLDMAXCHOICE, KEY_OLDMAXCHOICE);
		map.put(KEY_TROELPLAYER, KEY_TROELPLAYER);
		map.put(KEY_TROELMEEPLAYER, KEY_TROELMEEPLAYER);
		map.put(KEY_ALLEEN, KEY_ALLEEN);
		map.put(KEY_CARD, KEY_CARD);
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
        return map;
    }

	private static HashMap<String,String> buildColumnMapPlayParam()
	{
        HashMap<String,String> map = new HashMap<String,String>();
		map.put(KEY_SAVED_NAME, KEY_SAVED_NAME);
		map.put(KEY_PLAYCATEGORY, KEY_PLAYCATEGORY);
		map.put(KEY_OKAY, KEY_OKAY);
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
        return map;
    }

	private static HashMap<String,String> buildColumnMapFollowed()
	{
        HashMap<String,String> map = new HashMap<String,String>();
		map.put(KEY_SAVED_NAME, KEY_SAVED_NAME);
		map.put(KEY_PLAYER, KEY_PLAYER);
		map.put(KEY_OTHERPLAYER, KEY_OTHERPLAYER);
		map.put(KEY_CATEGORY, KEY_CATEGORY);
		map.put(KEY_FOLLOWED, KEY_FOLLOWED);
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
        return map;
    }

	private static HashMap<String,String> buildColumnMapPlayers()
	{
        HashMap<String,String> map = new HashMap<String,String>();
		map.put(KEY_SAVED_NAME, KEY_SAVED_NAME);
		map.put(KEY_PLAYER, KEY_PLAYER);
		map.put(KEY_CHOICE, KEY_CHOICE);
		map.put(KEY_OLDCHOICE, KEY_OLDCHOICE);
		map.put(KEY_TRUMPCAT, KEY_TRUMPCAT);
		map.put(KEY_POINTS, KEY_POINTS);
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
        return map;
    }

	private static HashMap<String,String> buildColumnMapObjects()
	{
        HashMap<String,String> map = new HashMap<String,String>();
		map.put(KEY_SAVED_NAME, KEY_SAVED_NAME);
		map.put(KEY_OBJPLAYER, KEY_OBJPLAYER);
		map.put(KEY_OBJCATEGORY, KEY_OBJCATEGORY);
		map.put(KEY_OBJAVERAGE, KEY_OBJAVERAGE);
		map.put(KEY_OBJREMAININGCARDS, KEY_OBJREMAININGCARDS);
		map.put(KEY_OBJCHECKEDCARDS, KEY_OBJCHECKEDCARDS);
		map.put(KEY_OBJUNDERPLAYER, KEY_OBJUNDERPLAYER);
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
        return map;
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns, String sortOrder)
	{
        //contentprovider moet geen kolomnamen kennen
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE_CARDS);
        builder.setProjectionMap(mColumnMap);
        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),             
									  columns, selection, selectionArgs, null, null, sortOrder); //DESC  ASC
		if (cursor == null)
		{                                                     
            return null;
        }
		else if (!cursor.moveToFirst())
		{
            cursor.close();
            return null;
        }
        return cursor;
    }

	private Cursor query2(String selection, String[] selectionArgs, String[] columns, String sortOrder)
	{
        //contentprovider moet geen kolomnamen kennen
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE2);
        builder.setProjectionMap(mColumnMap2);
        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),             
									  columns, selection, selectionArgs, null, null, sortOrder);
		if (cursor == null)
		{                                                     
            return null;
        }
		else if (!cursor.moveToFirst())
		{
            cursor.close();
            return null;
        }
        return cursor;
    }

	private Cursor query3(String selection, String[] selectionArgs, String[] columns, String sortOrder)
	{
        //contentprovider moet geen kolomnamen kennen
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE3);
        builder.setProjectionMap(mColumnMap3);
        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),             
									  columns, selection, selectionArgs, null, null, sortOrder);
		if (cursor == null)
		{                                                     
            return null;
        }
		else if (!cursor.moveToFirst())
		{
            cursor.close();
            return null;
        }
        return cursor;
    }

	private Cursor queryPlayers(String selection, String[] selectionArgs, String[] columns, String sortOrder)
	{
        //contentprovider moet geen kolomnamen kennen
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE_PLAYERS);
        builder.setProjectionMap(mColumnMapPlayers);
        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),             
									  columns, selection, selectionArgs, null, null, sortOrder);
		if (cursor == null)
		{                                                     
            return null;
        }
		else if (!cursor.moveToFirst())
		{
            cursor.close();
            return null;
        }
        return cursor;
    }

	private Cursor queryObjects(String selection, String[] selectionArgs, String[] columns, String sortOrder)
	{
        //contentprovider moet geen kolomnamen kennen
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE_OBJECTS);
        builder.setProjectionMap(mColumnMapObjects);
        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),             
									  columns, selection, selectionArgs, null, null, sortOrder);
		if (cursor == null)
		{                                                     
            return null;
        }
		else if (!cursor.moveToFirst())
		{
            cursor.close();
            return null;
        }
        return cursor;
    }

	private Cursor queryPlayParam(String selection, String[] selectionArgs, String[] columns, String sortOrder)
	{
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE_PLAYPARAM);
        builder.setProjectionMap(mColumnMapPlayParam);
        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),             
									  columns, selection, selectionArgs, null, null, sortOrder);
		if (cursor == null)
		{                                                     
            return null;
        }
		else if (!cursor.moveToFirst())
		{
            cursor.close();
            return null;
        }
        return cursor;
    }

	private Cursor queryFollowed(String selection, String[] selectionArgs, String[] columns, String sortOrder)
	{
        //contentprovider moet geen kolomnamen kennen
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE_FOLLOWED);
        builder.setProjectionMap(mColumnMapFollowed);
        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),             
									  columns, selection, selectionArgs, null, null, sortOrder);
		if (cursor == null)
		{                                                     
            return null;
        }
		else if (!cursor.moveToFirst())
		{
            cursor.close();
            return null;
        }
        return cursor;
    }

	public ArrayList<Card> getCards(String savedName, String name)
	{
		ArrayList<Card> deck = new ArrayList<Card>();
		Cursor curs = getSavedName(savedName, name);
		if (curs != null)
		{
			do{
				String in=curs.getString(curs.getColumnIndex(CardsDatabase.KEY_INGAME));
				boolean inGame=Boolean.valueOf(in);
				int value=curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_VALUE));
				int cardreference=curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_CARDREFERENCE));
				int category=curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_CATEGORY));
				if (inGame)
				{
					Card card = new Card(value, cardreference, category);
					deck.add(card);
				}
			}while (curs.moveToNext());
		}	
		return deck;
	}

	public Cursor getSavedName(String savedName, String name)
	{ //private
		String selection=KEY_SAVED_NAME + " = ?"; //MATCH 
		savedName = savedName + "→" + name;// _ = spatie , daarom →
		String[] selectionArgs = new String[] {savedName};
		if (name == "parameters") return query2(selection, selectionArgs, null, null);
		if (name == "choiceparameters" || name == "playparameters") return query3(selection, selectionArgs, null, null);
		if (name == "players") return queryPlayers(selection, selectionArgs, null, null);
		if (name == "objects") return queryObjects(selection, selectionArgs, null, null);
		if (name == "followed") return queryFollowed(selection, selectionArgs, null, null);
		if (name == "playparam") return queryPlayParam(selection, selectionArgs, null, null);//tijd=3ms
//		if (name=="playparam"){ // dit werkt ook, tijd=1ms
//			selection=KEY_SAVED_NAME + " = ?" + " AND " + KEY_OKAY + " = ?";
//			selectionArgs = new String[] {savedName,"false"};  //"false"
//			return queryPlayParam(selection, selectionArgs, null, null);
//		} 
		return query(selection, selectionArgs, null, null);
	}

	public void getPoints(String savedName, Game game)
	{
		Cursor curs = getSavedName(savedName, "players");
		if (curs != null)
		{
			int player,a=1;
			do{
				game.playerPoints[a] = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_POINTS));
				a++;
			}while(curs.moveToNext());
		}	
	}

	public void getParameters(String savedName, Game game)
	{
		Cursor curs = getSavedName(savedName, "parameters");
		if (curs != null)
		{
			game.setDealer(curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_DEALER)));
			game.setStartPlayer(curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_STARTPLAYER)));
			game.setPlayer(curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_PLAYER)));
			game.setTrumpCategory(curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_TRUMPCATEGORY)));
		}
	}

	public void getChoices(String savedName, Game game)
	{
		int a;
		Cursor curs = getSavedName(savedName, "choiceparameters");
		if (curs != null)
		{
			game.maxChoice = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_MAXCHOICE));
			game.oldMaxChoice = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_OLDMAXCHOICE));
			game.troelPlayer = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_TROELPLAYER));
			game.troelMeePlayer = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_TROELMEEPLAYER));
			String alleen=curs.getString(curs.getColumnIndex(CardsDatabase.KEY_ALLEEN));
			game.alleen = Boolean.valueOf(alleen);
			a = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_DEALER));
			game.setDealer(a);//nu wordt startPlayer en player vastgelegd
			game.player = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_PLAYER));
			game.setTrumpCategory(curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_TRUMPCATEGORY)));
		}

		curs = getSavedName(savedName, "players");
		if (curs != null)
		{
			a = 1;
			do{
				game.playerChoice[a] = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_CHOICE));
				game.oldPlayerChoice[a] = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_OLDCHOICE));
				game.playerTrumpCategory[a] = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_TRUMPCAT));
				a++;
			}while(curs.moveToNext());
		}
	}

	public void getPlay(String savedName, Game game)
	{
		int a;
		Cursor curs = getSavedName(savedName, "playparameters");
		if (curs != null)
		{
			game.maxChoice = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_MAXCHOICE));
			game.oldMaxChoice = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_OLDMAXCHOICE));
			game.troelPlayer = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_TROELPLAYER));
			game.troelMeePlayer = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_TROELMEEPLAYER));
			String alleen=curs.getString(curs.getColumnIndex(CardsDatabase.KEY_ALLEEN));
			game.alleen = Boolean.valueOf(alleen);
			game.dealer = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_DEALER));
			game.startPlayer = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_STARTPLAYER));
			game.oldStartPlayer = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_OLDSTARTPLAYER));
			game.player = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_PLAYER));
			game.trumpCategory = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_TRUMPCATEGORY));
			game.card = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_CARD));
		}

		curs = getSavedName(savedName, "objects");
		if (curs != null)
		{
			do{
				int objP=curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_OBJPLAYER));
				int objC=curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_OBJCATEGORY));
				float objA=curs.getFloat(curs.getColumnIndex(CardsDatabase.KEY_OBJAVERAGE));
				int objR=curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_OBJREMAININGCARDS));
				int objCH=curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_OBJCHECKEDCARDS));
				int objU=curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_OBJUNDERPLAYER));
				if (objP != 0)
				{
					ObjPlayer objPl=new ObjPlayer(objP, objC, objA, objR, objCH, objU);
					game.getCategory.add(objPl);
				}
			}while(curs.moveToNext());
		}

		curs = getSavedName(savedName, "players");
		if (curs != null)
		{
			a = 1;
			do{
				game.playerChoice[a] = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_CHOICE));
				game.oldPlayerChoice[a] = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_OLDCHOICE));
				game.playerTrumpCategory[a] = curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_TRUMPCAT));
				a++;
			}while(curs.moveToNext());
		}

		curs = getSavedName(savedName, "followed");
		if (curs != null)
		{
			String[][][]followed=new String[5][5][5];
			for (a = 1;a <= 4;a++)
			{
				for (int b=1;b <= 4;b++)
				{
					for (int c=1;c <= 4;c++)
					{
						if (a != b)
						{
							followed[a][b][c] = curs.getString(curs.getColumnIndex(CardsDatabase.KEY_FOLLOWED));
							game.followed[a][b][c] = Boolean.valueOf(followed[a][b][c]);
							curs.moveToNext();
						}	
					}		
				}			
			}	
		}

		curs = getSavedName(savedName, "playparam");
		if (curs != null)
		{
			String[][]okay=new String[5][5];
			for (a = 1;a <= 4;a++)
			{
				for (int b=1;b <= 4;b++)
				{
					int inPlay=curs.getInt(curs.getColumnIndex(CardsDatabase.KEY_PLAYCATEGORY));
					if (inPlay != 0)
					{game.playCategory[a].add(inPlay);}	
					okay[a][b] = curs.getString(curs.getColumnIndex(CardsDatabase.KEY_OKAY));
					game.okay[a][b] = Boolean.valueOf(okay[a][b]);
					curs.moveToNext();	
				}
			}	
		}


	}

	//een UNIQUE key gebruiken we als alle rows verschillend moeten zijn
	//rowid is automatisch een unieke identificatie
	private static final String FTS_TABLE_CARDS_CREATE =
	"CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE_CARDS +
	" USING fts3 (" +
	KEY_INGAME + ", " +
	KEY_SAVED_NAME + ", " +
	KEY_VALUE + " INT " + ", " +
	KEY_CARDREFERENCE + " INT " + ", " +
	KEY_CATEGORY + " INT " + "); "; //DATETIME TEXT INTEGER INT REAL

	private static final String FTS_TABLE_CREATE2 =
	"CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE2 +
	" USING fts3 (" +
	KEY_SAVED_NAME + ", " +
	KEY_DEALER + " INT " + ", " +
	KEY_STARTPLAYER + " INT " + ", " +
	KEY_PLAYER + " INT " + ", " +
	KEY_TRUMPCATEGORY + " INT " + "); ";

	private static final String FTS_TABLE_PLAYERS_CREATE =
	"CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE_PLAYERS +
	" USING fts3 (" +
	KEY_SAVED_NAME + ", " +
	KEY_PLAYER + " INT " + ", " +
	KEY_CHOICE + " INT " + ", " +
	KEY_OLDCHOICE + " INT " + ", " +
	KEY_TRUMPCAT + " INT " + ", " +
	KEY_POINTS + " INT " + "); "; 

	private static final String FTS_TABLE_OBJECTS_CREATE =
	"CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE_OBJECTS +
	" USING fts3 (" +
	KEY_SAVED_NAME + ", " +
	KEY_OBJPLAYER + " INT " + ", " +
	KEY_OBJCATEGORY + " INT " + ", " +
	KEY_OBJAVERAGE + " FLOAT " + ", " +
	KEY_OBJREMAININGCARDS + " INT " + ", " +
	KEY_OBJCHECKEDCARDS + " INT " + ", " +
	KEY_OBJUNDERPLAYER + " INT " + "); "; 

	private static final String FTS_TABLE_PLAYPARAM_CREATE =  
	"CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE_PLAYPARAM +
	" USING fts3 (" +
	KEY_SAVED_NAME + ", " +
	KEY_OKAY + ", " +
	KEY_PLAYCATEGORY + " INT " + "); ";

	private static final String FTS_TABLE_FOLLOWED_CREATE =
	"CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE_FOLLOWED +
	" USING fts3 (" +
	KEY_SAVED_NAME + ", " +
	KEY_PLAYER + " INT " + ", " +
	KEY_OTHERPLAYER + " INT " + ", " +
	KEY_CATEGORY + " INT " + ", " +
	KEY_FOLLOWED + "); ";

	private static final String FTS_TABLE_CREATE3 =
	"CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE3 +
	" USING fts3 (" +
	KEY_SAVED_NAME + ", " +
	KEY_DEALER + " INT " + ", " +
	KEY_STARTPLAYER + " INT " + ", " +
	KEY_OLDSTARTPLAYER + " INT " + ", " +
	KEY_PLAYER + " INT " + ", " +
	KEY_TRUMPCATEGORY + " INT " + ", " +
	KEY_MAXCHOICE + " INT " + ", " +
	KEY_OLDMAXCHOICE + " INT " + ", " +
	KEY_TROELPLAYER + " INT " + ", " +
	KEY_TROELMEEPLAYER + " INT " + ", " +
	KEY_ALLEEN + ", " +
	KEY_CARD + " INT " + "); ";

	private final Context mCtx;
	private boolean isExternalStorage;

	//This creates/opens the database.
    private static class FoodOpenHelper extends SQLiteOpenHelper
	{

		FoodOpenHelper(Context context, final String DATABASE_NAME)
		{
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
		{
			Log.w(TAG, FTS_TABLE_CARDS_CREATE);
			db.execSQL(FTS_TABLE_CARDS_CREATE);
			Log.w(TAG, FTS_TABLE_CREATE2);
			db.execSQL(FTS_TABLE_CREATE2);
			Log.w(TAG, FTS_TABLE_CREATE3);
			db.execSQL(FTS_TABLE_CREATE3);
			Log.w(TAG, FTS_TABLE_PLAYERS_CREATE);
			db.execSQL(FTS_TABLE_PLAYERS_CREATE);
			Log.w(TAG, FTS_TABLE_OBJECTS_CREATE);
			db.execSQL(FTS_TABLE_OBJECTS_CREATE);
			Log.w(TAG, FTS_TABLE_FOLLOWED_CREATE);
			db.execSQL(FTS_TABLE_FOLLOWED_CREATE);
			Log.w(TAG, FTS_TABLE_PLAYPARAM_CREATE);
			db.execSQL(FTS_TABLE_PLAYPARAM_CREATE);
        }

		@Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				  + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE_CARDS);
			db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE2);
			db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE3);
			db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_PLAYERS_CREATE);
			db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_OBJECTS_CREATE);
			db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_FOLLOWED_CREATE);
			db.execSQL("DROP TABLE IF EXISTS " + FTS_TABLE_PLAYPARAM_CREATE);
            onCreate(db);
        }
    }		

	public CardsDatabase(Context ctx, boolean bool)
	{
		this.isExternalStorage = bool;
		this.mCtx = ctx;
	}

    public CardsDatabase open() throws SQLException
	{
		String DATABASE_NAME = "WhistTest.db"; //.db moet niet
		if (isExternalStorage)
		{ DATABASE_NAME = "/sdcard/AppProjects/WhistTest/WhistTest.db";}
		mDbHelper = new FoodOpenHelper(mCtx, DATABASE_NAME);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close()
	{
		if (mDbHelper != null)
		{
			mDbHelper.close();
		}
	}

	public void setCards(String savedName, String name, Iterator it)
	{
		Cursor curs=getSavedName(savedName, name);
		long id=0;
		ContentValues initialValues = new ContentValues();
		savedName = savedName + "→" + name;
		while (it.hasNext())
		{
			Card card = (Card) it.next();
			initialValues.put(KEY_INGAME, "true");
			initialValues.put(KEY_SAVED_NAME, savedName);
			initialValues.put(KEY_VALUE, card.getValue());
			initialValues.put(KEY_CARDREFERENCE, card.getCardReference());
			initialValues.put(KEY_CATEGORY, card.getCategory());
			if (curs == null || curs.isAfterLast())
			{
				mDb.insert(FTS_VIRTUAL_TABLE_CARDS, null, initialValues);
			}
			else
			{
				id = curs.getLong(curs.getColumnIndex(BaseColumns._ID));
				mDb.update(FTS_VIRTUAL_TABLE_CARDS, initialValues, "rowid = " + id, null);
				curs.moveToNext();
			}	
		}
		while (curs != null && !curs.isAfterLast())
		{
			initialValues.put(KEY_INGAME, "false");
			initialValues.put(KEY_SAVED_NAME, savedName);
			initialValues.put(KEY_VALUE, 100);
			initialValues.put(KEY_CARDREFERENCE, 100);
			initialValues.put(KEY_CATEGORY, 100);
			id = curs.getLong(curs.getColumnIndex(BaseColumns._ID));
			mDb.update(FTS_VIRTUAL_TABLE_CARDS, initialValues, "rowid = " + id, null);
			curs.moveToNext();
		}	
    }

	public void deleteCards(String savedName, String name)
	{ //private  niet in gebruik
		Cursor curs = getSavedName(savedName, name);
		if (curs != null)
		{
			do{
				long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
				mDb.delete(FTS_VIRTUAL_TABLE_CARDS, "rowid = " + id, null);
			}while(curs.moveToNext());
		}
	}

	public void setPoints(String savedName, Game game)
	{
		Cursor curs = getSavedName(savedName, "players");
		savedName = savedName + "→" + "players";
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SAVED_NAME, savedName);
		if (curs == null)
		{
			for (int a=1;a <= 4;a++)
			{
				initialValues.put(KEY_PLAYER, a);
				initialValues.put(KEY_POINTS, game.playerPoints[a]);
				mDb.insert(FTS_VIRTUAL_TABLE_PLAYERS, null, initialValues);
			}	
		}
		else
		{
			int player,a=1;
			do{
				initialValues.put(KEY_POINTS, game.playerPoints[a]);
				long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
				mDb.update(FTS_VIRTUAL_TABLE_PLAYERS, initialValues, "rowid = " + id, null);
				a++;

			}while(curs.moveToNext());
		}	
	}

	public void setParameters(String savedName, Game game)
	{
		Cursor curs=getSavedName(savedName, "parameters");//dit kan maar 1 rij zijn
		savedName = savedName + "→" + "parameters";
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_DEALER, game.getDealer());
		initialValues.put(KEY_STARTPLAYER, game.getStartPlayer());
		initialValues.put(KEY_PLAYER, game.getPlayer());
		initialValues.put(KEY_TRUMPCATEGORY, game.getTrumpCategory());
		if (curs == null)
		{
			initialValues.put(KEY_SAVED_NAME, savedName);
			mDb.insert(FTS_VIRTUAL_TABLE2, null, initialValues);
		}
		else
		{
			long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
			mDb.update(FTS_VIRTUAL_TABLE2, initialValues, "rowid = " + id, null);
		}		
	}

	public void setChoices(String savedName, Game game)
	{
		String savedN;
		Cursor curs=getSavedName(savedName, "choiceparameters");
		savedN = savedName + "→" + "choiceparameters";
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_MAXCHOICE, game.maxChoice);
		initialValues.put(KEY_OLDMAXCHOICE, game.oldMaxChoice);
		initialValues.put(KEY_TROELPLAYER, game.troelPlayer);
		initialValues.put(KEY_TROELMEEPLAYER, game.troelMeePlayer);
		initialValues.put(KEY_ALLEEN, String.valueOf(game.alleen));
		initialValues.put(KEY_DEALER, game.dealer);
		initialValues.put(KEY_STARTPLAYER, game.startPlayer);//wordt voorlopig niet gebruikt
		initialValues.put(KEY_PLAYER, game.player);
		initialValues.put(KEY_TRUMPCATEGORY, game.getTrumpCategory());
		if (curs == null)
		{
			initialValues.put(KEY_SAVED_NAME, savedN);
			mDb.insert(FTS_VIRTUAL_TABLE3, null, initialValues);
		}
		else
		{
			long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
			mDb.update(FTS_VIRTUAL_TABLE3, initialValues, "rowid = " + id, null);
		}		

	 	curs = getSavedName(savedName, "players");
		savedN = savedName + "→" + "players";
		initialValues = new ContentValues();
		initialValues.put(KEY_SAVED_NAME, savedN);
		if (curs == null)
		{
			for (int a=1;a <= 4;a++)
			{
				initialValues.put(KEY_PLAYER, a);
				initialValues.put(KEY_CHOICE, game.playerChoice[a]);
				initialValues.put(KEY_OLDCHOICE, game.oldPlayerChoice[a]);
				initialValues.put(KEY_TRUMPCAT, game.playerTrumpCategory[a]);
				mDb.insert(FTS_VIRTUAL_TABLE_PLAYERS, null, initialValues);
			}	
		}
		else
		{
			int a=1;
			do{
				initialValues.put(KEY_CHOICE, game.playerChoice[a]);
				initialValues.put(KEY_OLDCHOICE, game.oldPlayerChoice[a]);
				initialValues.put(KEY_TRUMPCAT, game.playerTrumpCategory[a]);
				long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
				mDb.update(FTS_VIRTUAL_TABLE_PLAYERS, initialValues, "rowid = " + id, null);
				a++;
			}while(curs.moveToNext());
		}	
	}

	public void setPlay(String savedName, Game game)
	{
		String savedN;
		Cursor curs=getSavedName(savedName, "playparameters");
		savedN = savedName + "→" + "playparameters";
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_MAXCHOICE, game.maxChoice);//voorlopig dit ook opgeslagen
		initialValues.put(KEY_OLDMAXCHOICE, game.oldMaxChoice);
		initialValues.put(KEY_TROELPLAYER, game.troelPlayer);
		initialValues.put(KEY_TROELMEEPLAYER, game.troelMeePlayer);
		initialValues.put(KEY_ALLEEN, String.valueOf(game.alleen));
		initialValues.put(KEY_DEALER, game.dealer);
		initialValues.put(KEY_STARTPLAYER, game.startPlayer);
		initialValues.put(KEY_OLDSTARTPLAYER, game.oldStartPlayer);
		initialValues.put(KEY_PLAYER, game.player);
		initialValues.put(KEY_TRUMPCATEGORY, game.getTrumpCategory());
		initialValues.put(KEY_CARD, game.card);

		if (curs == null)
		{
			initialValues.put(KEY_SAVED_NAME, savedN);
			mDb.insert(FTS_VIRTUAL_TABLE3, null, initialValues);
		}
		else
		{
			long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
			mDb.update(FTS_VIRTUAL_TABLE3, initialValues, "rowid = " + id, null);
		}

		curs = getSavedName(savedName, "objects");
		savedN = savedName + "→" + "objects";
		initialValues = new ContentValues();
		initialValues.put(KEY_SAVED_NAME, savedN);
		int z=-1;
		if (curs == null)
		{
			for (int b=0;b < game.getCategory.size();b++)
			{
				z++;initialValues.put(KEY_OBJPLAYER, game.getCategory.get(b).player);
				initialValues.put(KEY_OBJCATEGORY, game.getCategory.get(b).category);
				initialValues.put(KEY_OBJAVERAGE, game.getCategory.get(b).average);
				initialValues.put(KEY_OBJREMAININGCARDS, game.getCategory.get(b).remainingCards);
				initialValues.put(KEY_OBJCHECKEDCARDS, game.getCategory.get(b).checkedCards);
				initialValues.put(KEY_OBJUNDERPLAYER, game.getCategory.get(b).underPlayer);
				mDb.insert(FTS_VIRTUAL_TABLE_OBJECTS, null, initialValues);
			}
			while (z < 15)
			{
				z++;initialValues.put(KEY_OBJPLAYER, 0);
				initialValues.put(KEY_OBJCATEGORY, 0);
				initialValues.put(KEY_OBJAVERAGE, 0);
				initialValues.put(KEY_OBJREMAININGCARDS, -10);
				initialValues.put(KEY_OBJCHECKEDCARDS, -10);
				initialValues.put(KEY_OBJUNDERPLAYER, -1);
				mDb.insert(FTS_VIRTUAL_TABLE_OBJECTS, null, initialValues);
			}		
		}
		else
		{
			for (int b=0;b < game.getCategory.size();b++)
			{
				z++;initialValues.put(KEY_OBJPLAYER, game.getCategory.get(b).player);
				initialValues.put(KEY_OBJCATEGORY, game.getCategory.get(b).category);
				initialValues.put(KEY_OBJAVERAGE, game.getCategory.get(b).average);
				initialValues.put(KEY_OBJREMAININGCARDS, game.getCategory.get(b).remainingCards);
				initialValues.put(KEY_OBJCHECKEDCARDS, game.getCategory.get(b).checkedCards);
				initialValues.put(KEY_OBJUNDERPLAYER, game.getCategory.get(b).underPlayer);
				long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
				mDb.update(FTS_VIRTUAL_TABLE_OBJECTS, initialValues, "rowid = " + id, null);
				curs.moveToNext();
			}	
			while (z < 15)
			{
				z++;initialValues.put(KEY_OBJPLAYER, 0);
				initialValues.put(KEY_OBJCATEGORY, 0);
				initialValues.put(KEY_OBJAVERAGE, 0);
				initialValues.put(KEY_OBJREMAININGCARDS, -10);
				initialValues.put(KEY_OBJCHECKEDCARDS, -10);
				initialValues.put(KEY_OBJUNDERPLAYER, -1);
				long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
				mDb.update(FTS_VIRTUAL_TABLE_OBJECTS, initialValues, "rowid = " + id, null);
				curs.moveToNext();
			}
		}	

		curs = getSavedName(savedName, "players");
		savedN = savedName + "→" + "players";
		initialValues = new ContentValues();
		initialValues.put(KEY_SAVED_NAME, savedN);
		if (curs == null)
		{
			for (int a=1;a <= 4;a++)
			{
				initialValues.put(KEY_CHOICE, game.playerChoice[a]);
				initialValues.put(KEY_OLDCHOICE, game.oldPlayerChoice[a]);
				initialValues.put(KEY_TRUMPCAT, game.playerTrumpCategory[game.player]);
				initialValues.put(KEY_PLAYER, a);
				mDb.insert(FTS_VIRTUAL_TABLE_PLAYERS, null, initialValues);
			}	
		}
		else
		{
			int a=1;
			do{
				initialValues.put(KEY_CHOICE, game.playerChoice[a]);
				initialValues.put(KEY_OLDCHOICE, game.oldPlayerChoice[a]);
				initialValues.put(KEY_TRUMPCAT, game.playerTrumpCategory[game.player]);
				long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
				mDb.update(FTS_VIRTUAL_TABLE_PLAYERS, initialValues, "rowid = " + id, null);
				a++;
			}while(curs.moveToNext());
		}	

		curs = getSavedName(savedName, "followed");
		savedN = savedName + "→" + "followed";
		initialValues = new ContentValues();
		initialValues.put(KEY_SAVED_NAME, savedN);
		if (curs == null)
		{
			for (int a=1;a <= 4;a++)
			{
				for (int b=1;b <= 4;b++)
				{
					for (int c=1;c <= 4;c++)
					{
						if (a != b)
						{
							initialValues.put(KEY_PLAYER, a);
							initialValues.put(KEY_OTHERPLAYER, b);
							initialValues.put(KEY_CATEGORY, c);
							initialValues.put(KEY_FOLLOWED, String.valueOf(game.followed[a][b][c]));
							mDb.insert(FTS_VIRTUAL_TABLE_FOLLOWED, null, initialValues);
						}
					}	
				}					
			}	
		}
		else
		{
			for (int a=1;a <= 4;a++)
			{
				for (int b=1;b <= 4;b++)
				{
					for (int c=1;c <= 4;c++)
					{
						if (a != b)
						{
							initialValues.put(KEY_PLAYER, a);//niet gebruikt in getplay,magweg???
							initialValues.put(KEY_OTHERPLAYER, b);//niet gebruikt in getplay,magweg???
							initialValues.put(KEY_CATEGORY, c);//niet gebruikt in getplay,magweg???
							initialValues.put(KEY_FOLLOWED, String.valueOf(game.followed[a][b][c]));
							long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
							mDb.update(FTS_VIRTUAL_TABLE_FOLLOWED, initialValues, "rowid = " + id, null);
							curs.moveToNext();
						}
					}	
				}					
			}		
		}	

		curs = getSavedName(savedName, "playparam");
		savedN = savedName + "→" + "playparam";
		initialValues = new ContentValues();
		initialValues.put(KEY_SAVED_NAME, savedN);
		//Date dt=new Date();
		//System.out.println(dt);
		//1 value saven=<1ms, 16=+/-180ms
		if (curs == null)
		{
			for (int a=1;a <= 4;a++)
			{
				z = 0;
				for (int b=0;b < game.playCategory[a].size();b++)
				{
					z++;initialValues.put(KEY_PLAYCATEGORY, game.playCategory[a].get(b));
					initialValues.put(KEY_OKAY, String.valueOf(game.okay[a][z]));
					mDb.insert(FTS_VIRTUAL_TABLE_PLAYPARAM, null, initialValues);
				}
				while (z < 4)
				{
					z++;initialValues.put(KEY_PLAYCATEGORY, 0);
					initialValues.put(KEY_OKAY, String.valueOf(game.okay[a][z]));
					mDb.insert(FTS_VIRTUAL_TABLE_PLAYPARAM, null, initialValues);
				}
			}	
		}
		else
		{
			for (int a=1;a <= 4;a++)
			{
				z = 0;
				for (int b=0;b < game.playCategory[a].size();b++)
				{
					z++;initialValues.put(KEY_PLAYCATEGORY, game.playCategory[a].get(b));
					initialValues.put(KEY_OKAY, String.valueOf(game.okay[a][z]));
					long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
					mDb.update(FTS_VIRTUAL_TABLE_PLAYPARAM, initialValues, "rowid = " + id, null);
					curs.moveToNext();
				}
				while (z < 4)
				{
					z++;initialValues.put(KEY_PLAYCATEGORY, 0);
					initialValues.put(KEY_OKAY, String.valueOf(game.okay[a][z]));
					long id=curs.getLong(curs.getColumnIndex(BaseColumns._ID));
					mDb.update(FTS_VIRTUAL_TABLE_PLAYPARAM, initialValues, "rowid = " + id, null);
					curs.moveToNext();
				}
			}	
		}	
	}

	public void toast(String msg)
	{//opgepast toast kan problemen geven in database
		//dit moet weg
        //Toast.makeText(this.mCtx, msg, Toast.LENGTH_SHORT).show();
    }
}
