
package project;

public class Item {
    private int OrderID;
    private Product Product;
    private int Quantity;
    //Constructors
    public Item(int ProductID, int Quantity){
        this.Product.ID(ProductID);
        this.Quantity = Quantity;
    }
    public Item(Product Product, int Quantity){
        this.Product = Product;
        this.Quantity = Quantity;
    }
    //Increases the quantity by 1
    public void Add(){
        this.Quantity++;
    }
    public Product Product(){
        return this.Product;
    }
    public int Quantity(){
        return this.Quantity;
    }
    public void Remove(){
        this.Quantity--;
        if(this.Quantity < 0){
            this.Quantity = 0;
        }
    }
    //Returns the total
    public float Total(){
        return this.Product.Total(Quantity);
    }
}
