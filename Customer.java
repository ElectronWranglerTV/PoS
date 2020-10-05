
package project;

import java.util.ArrayList;

public class Customer {
    private int ID;
    private String FName;
    private String LName;
    private String Street;
    private String City;
    private int Zip;
    private String State;
    private String Phone;
    private String ListString;
    private ArrayList<Integer> Order;
    
    //Constructors
    public Customer(String FName, String LName, String Street, String City, String State, int Zip, String Phone){
        this.FName = FName;
        this.LName = LName;
        this.Street = Street;
        this.City = City;
        this.State = State;
        this.Zip = Zip;
        this.Phone = Phone;
        this.Order = new ArrayList<>();
    }
    public Customer(int ID, String FName, String LName, String Street, String City, String State, int Zip, String Phone){
        this.ID = ID;
        this.FName = FName;
        this.LName = LName;
        this.Street = Street;
        this.City = City;
        this.State = State;
        this.Zip = Zip;
        this.Phone = Phone;
        this.Order = new ArrayList<>();
    }
    //Adds an order to the customer's account
    public void AddOrder(int OrderID){
        this.Order.add(OrderID);
    }
    //Deletes an order from the customer's account
    public void DeleteOrder(int OrderID){
        for(int i : this.Order){
            if(this.Order.get(i) == OrderID){
                this.Order.remove(i);
                return;
            }
        }
    }
    //Getters and setters
    public String City(){
        return this.City;
    }
    public String FName(){
        return this.FName;
    }
    public int ID(){
        return this.ID;
    }
    public String ListString(){
        return this.ListString;
    }
    public String LName(){
        return this.LName;
    }
    public String Phone(){
        return this.Phone;
    }
    public String State(){
        return this.State;
    }
    public String Street(){
        return this.Street;
    }
    public int Zip(){
        return this.Zip;
    }
    public ArrayList<Integer> Orders(){
        return new ArrayList<>(this.Order);
    }
    public void City(String City){
        this.City = City;
    }
    public void FName(String Name){
        this.FName = Name;
    }
    public void ID(int ID){
        this.ID = ID;
    }
    public void ListString(String ListString){
        this.ListString = ListString;
    }
    public void LName(String Name){
        this.LName = Name;
    }
    public void Phone(String Phone){
        this.Phone = Phone;
    }
    public void State(String State){
        this.State = State;
    }
    public void Street(String Street){
        this.Street = Street;
    }
    public void Zip(int Zip){
        this.Zip = Zip;
    }
}
