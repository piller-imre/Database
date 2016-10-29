package database.queryObject.update;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;
import database.queryObject.Where;
import database.queryObject.wherebuilder.WhereBuilder;

/**
 * This class can prepare an Update QueryObject.
 * */
public class UpdateBuilder {
	private Update update;
	private InMemoryDatabase db;
	private Set set;
	private Move move;
	private List<Integer> moveValues;/*ezen pointra mozog(Point(10,10))*/
	
	private WhereBuilder whereBuilder;
	private Where where;
	
	public UpdateBuilder(InMemoryDatabase db) {
		this.whereBuilder = new WhereBuilder();
		this.db = db;
		this.moveValues = new ArrayList<Integer>();
	}
	
	public void createUpdate(String mapName){
		this.update = new Update(mapName, this.db);
		this.update.db = this.db;
	}
	
	
	public void setType(String type){
		if(type.equalsIgnoreCase("set")){
			this.set = new Set();
		}else if(type.equalsIgnoreCase("move")){
			this.move = new Move();
		}
	}
	
	public void addSetAttribute(String attribute){
		this.set.setAttribute(attribute);
	}
	
	public void addSetAttributeValue(Object value){
		this.set.setValue(value);
	}
	
	
	public void setMoveValue(int value){
		this.moveValues.add(value);
	}
	
	public IQueryObject build(){
		this.where = this.whereBuilder.build();
		
		if(this.move != null){
			this.update.setMove(this.move);
			
			this.move.setNewLocationPoint(new Point(this.moveValues.get(0), this.moveValues.get(1)));
			
			return this.update;
		}else if(this.set != null){
			this.update.setSet(this.set);
			
			return this.update;
		}else{
			return null;
		}
	}
	
	public void addOperandPiece(String piece){
		this.whereBuilder.addOperandPiece(piece);
	}
	
	public void addRoundBracket(){
		this.whereBuilder.addRoundBracket();
	}
	
	public void removeRoundBracket(){
		this.whereBuilder.removeRoundBracket();
	}
	
}



/*
 				UpdateBuilder builder = new UpdateBuilder(Window.this.db);
				builder.createUpdate(Window.this.openedMapNameValue.getText());
				builder.setMove();
				builder.setMoveValue(300);
				builder.setMoveValue(160);
				builder.setWhere(wh);
				builder.build().execute();
				
				
				
				
				
				
				
				
				
				UpdateBuilder builder = new UpdateBuilder(Window.this.db);
				builder.createUpdate(Window.this.openedMapNameValue.getText());
				builder.setSet();
				builder.addSetAttribute("x");
				builder.addSetAttributeValue(400);
				builder.addSetAttribute("y");
				builder.addSetAttributeValue(200);
				builder.setWhere(wh);
				builder.build().execute();
 */
