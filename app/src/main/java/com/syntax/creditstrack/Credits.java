package com.syntax.creditstrack;
public class Credits {
	 
    //private variables
    int _id;
    String _name;
    int _transaction_value;
    String _transaction_status;
    String _transaction_note;

    int _person_balance;
 
    // Empty constructor
    public Credits(){
 
    }
    // constructor
    public Credits(int id, String name, int transaction_value, String transaction_status, String transaction_note){
        this._id = id;
        this._name = name;
        this._transaction_value = transaction_value;
        this._transaction_status = transaction_status;
        this._transaction_note = transaction_note;
    }
 
    // constructor
    public Credits(String name, int transaction_value, String transaction_status, String transaction_note){
        this._name = name;
        this._transaction_value = transaction_value;
        this._transaction_status = transaction_status;
        this._transaction_note = transaction_note;
    }

    //constructor
    public Credits(String name, int person_balance){
        this._name = name;
        this._person_balance = person_balance;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
 
    // setting id
    public void setID(int id){
        this._id = id;
    }
 
    // getting name
    public String getName(){
        return this._name;
    }
 
    // setting name
    public void setName(String name){
        this._name = name;
    }
 
    // getting transaction value
    public int getTransactionValue(){
        return this._transaction_value;
    }
 
    // setting transaction value
    public void setTransactionValue(int transaction_value){
        this._transaction_value = transaction_value;
    }
    
    // getting transaction status
    public String getTransactionStatus(){
        return this._transaction_status;
    }
 
    // setting transaction status
    public void setTransactionStatus(String transaction_status){
        this._transaction_status = transaction_status;
    }

    // getting transaction note
    public String getTransactionNote(){
        return this._transaction_note;
    }

    // setting transaction note
    public void setTransactionNote(String transaction_note){
        this._transaction_note = transaction_note;
    }

    // getting person balance
    public int getPersonBalance(){
        return this._person_balance;
    }

    // setting person balance
    public void setPersonBalance(int person_balance){
        this._person_balance = person_balance;
    }

}