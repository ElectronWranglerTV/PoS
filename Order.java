
package project;

import java.util.ArrayList;

public class Order {
    private int ID;
    private int CustomerID;
    private ArrayList<Item> LineItem;
    private int AccessIndex = -1;
    //Constructors
    public Order(){
        this.LineItem = new ArrayList<>();
    }
    public Order(int CustomerID){
        this.CustomerID = CustomerID;
        this.LineItem = new ArrayList<>();
    }
    public Order(int OrderID, int CustomerID){
        this.ID = OrderID;
        this.CustomerID = CustomerID;
        this.LineItem = new ArrayList<>();
    }
    //Adds an item to an order
    public void AddItem(int ID, int Quantity){
        //Check if item already exists in order, and increment quantity if it is
        for(Item I : this.LineItem){
            if(I.Product().ID() == ID){
                //Item found, increment quantity
                I.Add();
                return;
            }
        }
        //Item not found, add it to order
        this.LineItem.add(new Item(ID, Quantity));
        this.AccessIndex = -1;
    }
    public void AddItem(Product Product, int Quantity){
        //Check if item already exists in order, and increment quantity if it is
        for(Item I : this.LineItem){
            if(I.Product().equals(Product)){
                //Item found, increment quantity
                I.Add();
                return;
            }
        }
        //Item not found, add it to order
        this.LineItem.add(new Item(Product, Quantity));
        this.AccessIndex = -1;
    }
    //Returns the customer's ID
    public int Customer(){
        return this.CustomerID;
    }
    //Completely deletes a line item from the order
    public void DeleteItem(Product Product){
        for(int i = 0; i < this.LineItem.size(); i++){
            if(this.LineItem.get(i).Product().equals(Product)){
                this.LineItem.remove(i);
                this.AccessIndex--;
                if(this.AccessIndex < 0){
                    this.AccessIndex = -1;
                }
                return;
            }
        }
    }
    //Returns the DB ID of this order
    public int ID(){
        return this.ID;
    }
    //Sets this order's ID. Returns false if success, true if fail
    public boolean ID(int ID){
        if(ID < 0){
            return false;
        }
        this.ID = ID;
        return true;
    }
    //Returns the number of items in this order
    public int ItemCount(){
        return this.LineItem.size();
    }
    //Returns the quantity for the currently selected item
    //Zero if end of order or order empty
    public int ItemQuantity(){
        if(this.AccessIndex > -1 && this.AccessIndex < this.LineItem.size()){
            return this.LineItem.get(AccessIndex).Quantity();
        }
        return 0;
    }
    //Returns the next item in the order
    public Item NextItem(){
        this.AccessIndex++;
        if(this.AccessIndex >= this.LineItem.size()){
            this.AccessIndex = 0;
        }
        return this.LineItem.get(this.AccessIndex);
    }
    //Decrements the quantity of the specified line item by 1
    public void RemoveItem(Product Product){
        for(int i = 0; i < this.LineItem.size(); i++){
            if(this.LineItem.get(i).Product().equals(Product)){
                this.LineItem.get(i).Remove();
                if(this.LineItem.get(i).Quantity() == 0){
                    this.LineItem.remove(i);
                }
                return;
            }
        }
    }
    public float Total(){
        float Total = 0.0f;
        //Add each line item to the total then return
        for(Item I : this.LineItem){
            Total += I.Total();
        }
        return Total;
    }
}
