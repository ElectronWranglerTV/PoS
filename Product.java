
package project;

public class Product {
    private int ID;
    private String Name;
    private float Price;
    //Constructors
    public Product(){
        
    }
    public Product(Product Product){
        
    }
    public Product(int ProductID){
        this.ID = ProductID;
    }
    public Product(String Name, float Price){
        this.Name = Name;
        this.Price = Price;
    }
    public Product(int ID, String Name, float Price){
        this.ID = ID;
        this.Name = Name;
        this.Price = Price;
    }
    public String Name(){
        return this.Name;
    }
    public float Price(){
        return this.Price;
    }
    public void ID(int ID){
        this.ID = ID;
    }
    public int ID(){
        return this.ID;
    }
    @Override
    public String toString(){
        return this.Name + " " + this.Price;
    }
    //Calculates the total for the indicated quantity of this product
    public float Total(int Quantity){
        if(Quantity < 0){
            return 0;
        }
        return this.Price * Quantity;
    }
}
