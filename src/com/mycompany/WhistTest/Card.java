package com.mycompany.WhistTest;

public class Card
{
	public int value, cardReference, category, sortingValue;
	float X, Y, startX, startY; 
	boolean selected=false;
	int player=-1;//enkel gebruikt in checkCardsPlayed in mainactivity

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getSortingValue() {
		return sortingValue;
	}
	public int getCardReference() {
		return cardReference;
	}
	/*public void setCardReference(int cardReference) {
		this.cardReference = cardReference;
	}*/
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public float getX() {
		return X;
	}
	public void setX(float X) {
		this.X = X;
	}
	public float getY() {
		return Y;
	}
	public void setY(float Y) {
		this.Y = Y;
	}
	public float getStartX() {
		return startX;
	}
	public void setStartX(float X) {
		this.startX = X;
	}
	public float getStartY() {
		return startY;
	}
	public void setStartY(float Y) {
		this.startY = Y;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Card(int value, int cardReference, int category) {
		this.value = value;
		this.cardReference = cardReference;
		this.category = category;
		
		int i=1;int z=category;
		while(z>1) {i*=8;z-=1;}
		if(value>20){value-=20;}
		sortingValue=value*category*i;
	}
	
	public String getStringValue(){
		switch(value){
			case 10:
				return "t";
			case 11:
				return "b";
			case 12:
				return "v";	
			case 13:
				return "k";	
			case 14:
				return "a";	
		}
		return String.valueOf(value);
	}
	
	public void setValueToStartValue(){
		switch(cardReference){
			case R.drawable.hearts2:
				value=2;break;
			case R.drawable.hearts3:
				value=3;break;
			case R.drawable.hearts4:
				value=4;break;	
			case R.drawable.hearts5:
				value=5;break;
			case R.drawable.hearts6:
				value=6;break;
			case R.drawable.hearts7:
				value=7;break;
			case R.drawable.hearts8:
				value=8;break;
			case R.drawable.hearts9:
				value=9;break;
			case R.drawable.hearts10:
				value=10;break;
			case R.drawable.heartsj:
				value=11;break;
			case R.drawable.heartsq:
				value=12;break;
			case R.drawable.heartsk:
				value=13;break;	
			case R.drawable.heartsa:
				value=14;break;
			case R.drawable.diamonds2:
				value=2;break;
			case R.drawable.diamonds3:
				value=3;break;
			case R.drawable.diamonds4:
				value=4;break;	
			case R.drawable.diamonds5:
				value=5;break;
			case R.drawable.diamonds6:
				value=6;break;
			case R.drawable.diamonds7:
				value=7;break;
			case R.drawable.diamonds8:
				value=8;break;
			case R.drawable.diamonds9:
				value=9;break;
			case R.drawable.diamonds10:
				value=10;break;
			case R.drawable.diamondsj:
				value=11;break;
			case R.drawable.diamondsq:
				value=12;break;
			case R.drawable.diamondsk:
				value=13;break;	
			case R.drawable.diamondsa:
				value=14;break;
			case R.drawable.clubs2:
				value=2;break;
			case R.drawable.clubs3:
				value=3;break;
			case R.drawable.clubs4:
				value=4;break;	
			case R.drawable.clubs5:
				value=5;break;
			case R.drawable.clubs6:
				value=6;break;
			case R.drawable.clubs7:
				value=7;break;
			case R.drawable.clubs8:
				value=8;break;
			case R.drawable.clubs9:
				value=9;break;
			case R.drawable.clubs10:
				value=10;break;
			case R.drawable.clubsj:
				value=11;break;
			case R.drawable.clubsq:
				value=12;break;
			case R.drawable.clubsk:
				value=13;break;	
			case R.drawable.clubsa:
				value=14;break;
			case R.drawable.spades2:
				value=2;break;
			case R.drawable.spades3:
				value=3;break;
			case R.drawable.spades4:
				value=4;break;	
			case R.drawable.spades5:
				value=5;break;
			case R.drawable.spades6:
				value=6;break;
			case R.drawable.spades7:
				value=7;break;
			case R.drawable.spades8:
				value=8;break;
			case R.drawable.spades9:
				value=9;break;
			case R.drawable.spades10:
				value=10;break;
			case R.drawable.spadesj:
				value=11;break;
			case R.drawable.spadesq:
				value=12;break;
			case R.drawable.spadesk:
				value=13;break;	
			case R.drawable.spadesa:
				value=14;break;	
		}
	}
}
