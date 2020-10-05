
package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private final String Host;
    private String ErrorMsg;
    private Connection Database;
    //Table names for database accesses
    private final String CustomerTable = "Customers";
    private final String OrderTable = "Orders";
    private final String ItemTable = "Items";
    private final String ProductTable = "Products";
    //
    public Database(String Host){
        this.Host = Host;
    }
    //Connects to the specified database. Return 0 if sucess, 1 if fail
    public boolean Connect(String Username, String Password){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.Database = DriverManager.getConnection("jdbc:mysql://" + this.Host, Username, Password);
        } catch (ClassNotFoundException e){
            this.ErrorMsg = e.getMessage();
            return true;
        } catch (SQLException e){
            this.ErrorMsg = e.getMessage();
            return true;
        }
        return false;
    }
    //Fetches a customer's info. Returns null if there are no customers or error
    public Customer Customer(int CustomerID){
        String SQL = "SELECT * FROM " + CustomerTable + " WHERE ID = ?";
        PreparedStatement PS = null;
        try{
            PS = this.Database.prepareStatement(SQL);
            PS.setInt(1, CustomerID);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                return new Customer(RS.getInt("ID"), RS.getString("FName"), RS.getString("LName"),
                                    RS.getString("Street"), RS.getString("City"), RS.getString("State"),
                                    RS.getInt("Zip"), RS.getString("Phone"));
            }
            return null;
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return null;
        }
    }
    //Fetches a list of customers. Returns null if there are no customers or error
    public ArrayList<Customer> Customers(){
        ArrayList<Customer> Result = new ArrayList<>();
        String SQL = "SELECT * FROM " + CustomerTable;
        PreparedStatement PS = null;
        try{
            PS = this.Database.prepareStatement(SQL);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                Result.add(new Customer(RS.getInt("ID"), RS.getString("FName"), RS.getString("LName"),
                                        RS.getString("Street"), RS.getString("City"), RS.getString("State"),
                                        RS.getInt("Zip"), RS.getString("Phone")));
            }
            PS.closeOnCompletion();
            return new ArrayList<>(Result);
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return null;
        }
    }
    //Returns the error message for failed operations
    public String Error(){
        return this.ErrorMsg;
    }
    //Fetches a list of items for a specific order. Returns null if there are no items or error
    public ArrayList<Item> Items(int OrderID){
        if(OrderID < 0){
            return null;
        }
        ArrayList<Item> Result = new ArrayList<>();
        String SQL = "SELECT * FROM " + ItemTable + " WHERE OrderID = ?";
        PreparedStatement PS = null;
        try{
            PS = this.Database.prepareStatement(SQL);
            PS.setInt(1, OrderID);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                Result.add(new Item(this.Product(RS.getInt("ProductID")), RS.getInt("Quantity")));
            }
            PS.closeOnCompletion();
            return Result;
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return null;
        }
    }
    //Fetches the specified order from the database
    public Order Order(int OrderID){
        Order Result = new Order();
        String SQL = "SELECT * FROM " + OrderTable + " WHERE ID = ?";
        PreparedStatement PS = null;
        try{
            PS = Database.prepareStatement(SQL);
            PS.setInt(1, OrderID);
            PS.execute();
            ResultSet RS = PS.getResultSet();
            if(RS.next()){
                Result.ID(RS.getInt(1));
            }
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return null;
        }

        SQL = "SELECT * FROM " + ItemTable + " WHERE OrderID = ?";
        try{
            PS = Database.prepareStatement(SQL);
            PS.setInt(1, OrderID);
            PS.execute();
            ResultSet RS = PS.getResultSet();
            if(RS.next()){
                Product Product = new Product(RS.getInt("ProductID"));
                Result.AddItem(Product, RS.getInt("Quantity"));
            }
        } catch(SQLException e){
            return null;
        }
        return Result;
    }
    //Fetches all orders from the database.
    public ArrayList<Order> Orders(){
        ArrayList<Order> Result = new ArrayList<>();
        String SQL = "SELECT * FROM " + OrderTable;
        PreparedStatement PS = null;
        try{
            PS = this.Database.prepareStatement(SQL);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                Result.add(new Order(RS.getInt("ID"), RS.getInt("CustomerID")));
            }
            PS.closeOnCompletion();
            return Result;
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return null;
        }
    }
    //Fetches a list of orders for a specific customer. Returns null if there are no orders or error
    public ArrayList<Order> Orders(int CustomerID){
        if(CustomerID < 0){
            return null;
        }
        ArrayList<Order> Result = new ArrayList<>();
        String SQL = "SELECT * FROM " + OrderTable + " WHERE ID = ?";
        PreparedStatement PS = null;
        try{
            PS = this.Database.prepareStatement(SQL);
            PS.setInt(1, CustomerID);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                Result.add(new Order(RS.getInt("CustomerID")));
            }
            PS.closeOnCompletion();
            return new ArrayList<>(Result);
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return null;
        }
    }
        //Fetches information for a product. Returns null if product does not exist or error
    public Product Product(int ID){
        if(ID < 0){
            return null;
        }
        String SQL = "SELECT * FROM " + ProductTable + " WHERE ID = ?";
        PreparedStatement PS = null;
        try{
            PS = this.Database.prepareStatement(SQL);
            PS.setInt(1, ID);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                return new Product(RS.getString("Name"), RS.getInt("Price"));
            }
            PS.closeOnCompletion();
            return null;
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return null;
        }
    }
    //Fetches information for a product. Returns null if product does not exist or error
    public Product Product(String Name){
        if(Name.equals(null)){
            return null;
        }
        String SQL = "SELECT * FROM " + ProductTable + " WHERE Name = ?";
        PreparedStatement PS = null;
        try{
            PS = this.Database.prepareStatement(SQL);
            PS.setString(1, Name);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                return new Product(RS.getString("Name"), RS.getInt("Price"));
            }
            PS.closeOnCompletion();
            return null;
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return null;
        }
    }
    //Adds a new customer to the database.
    //Returns customer ID if success, -1 if fail
    public int InsertCustomer(Customer Customer){
        String SQL = "INSERT INTO " + CustomerTable + "(FName, LName, Street, City, State, Zip, Phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement PS = null;
        try{
            PS = Database.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            PS.setString(1, Customer.FName());
            PS.setString(2, Customer.LName());
            PS.setString(3, Customer.City());
            PS.setString(4, Customer.Street());
            PS.setString(5, Customer.State());
            PS.setInt(6, Customer.Zip());
            PS.setString(7, Customer.Phone());
            PS.execute();
            ResultSet RS = PS.getGeneratedKeys();
            if(RS.next()){
                return RS.getInt(1);
            }
            return -1;
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return -1;
        }
    }
    //Adds a new order to the database
    public boolean InsertOrder(Order Order){
        //Create new entry in order table
        String SQL = "INSERT INTO " + OrderTable + "(CustomerID) VALUES (?)";
        PreparedStatement PS = null;
        try{
            PS = Database.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            PS.setInt(1, Order.Customer());
            PS.execute();
            ResultSet RS = PS.getGeneratedKeys();
            if(RS.next()){
                Order.ID(RS.getInt(1));
            }
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return false;
        }
        //Create new entries in item table
        for(int i = 0; i < Order.ItemCount(); i++){
            SQL = "INSERT INTO " + ItemTable + "(OrderID, ProductID, Quantity) VALUES (?, ?, ?)";
            PS = null;
            try{
                PS = Database.prepareStatement(SQL);
                PS.setInt(1, Order.ID());
                Item I = Order.NextItem();
                PS.setInt(2, I.Product().ID());
                PS.setInt(3, I.Quantity());
                PS.execute();
            } catch(SQLException e){
                this.ErrorMsg = e.getMessage();
                return false;
            }
        }
        return true;
    }
    //Adds a new product to the database
    public boolean InsertProduct(Product Product){
        String SQL = "INSERT INTO " + ProductTable + "(Name, Price) VALUES (?, ?)";
        PreparedStatement PS = null;
        try{
            PS = Database.prepareStatement(SQL);
            PS.setString(1, Product.Name());
            PS.setFloat(2, Product.Price());
            PS.execute();
            return true;
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return false;
        }
    }
    //Fetches information for a product. Returns null if product does not exist or error
    public ArrayList<Product> Products(){
        ArrayList<Product> Result = new ArrayList<>();
        String SQL = "SELECT * FROM " + ProductTable;
        PreparedStatement PS = null;
        try{
            PS = this.Database.prepareStatement(SQL);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                Result.add(new Product(RS.getInt("ID"), RS.getString("Name"), RS.getInt("Price")));
            }
            PS.closeOnCompletion();
            return new ArrayList<>(Result);
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return null;
        }
    }
    //Removes the specified customer
    public boolean RemoveCustomer(Customer Customer){
        String SQL = "DELETE FROM " + CustomerTable + " WHERE ID = ?";
        PreparedStatement PS = null;
        try{
            PS = this.Database.prepareStatement(SQL);
            PS.setInt(1, Customer.ID());
            PS.execute();
            PS.closeOnCompletion();
            return true;
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return false;
        }
    }
    //Removes the specified customer
    public boolean RemoveProduct(Product Product){
        String SQL = "DELETE FROM " + ProductTable + " WHERE ID = ?";
        PreparedStatement PS = null;
        try{
            PS = this.Database.prepareStatement(SQL);
            PS.setInt(1, Product.ID());
            PS.execute();
            PS.closeOnCompletion();
            return true;
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return false;
        }
    }
    //Tries to create a table. Returns false if table created, true if failed
    public boolean TableCreate(String Table, String Layout){
        String SQL = "CREATE TABLE IF NOT EXISTS " + Table + " (" + Layout + ")";
        try{
            Statement Query = Database.createStatement();
            Query.executeUpdate(SQL);
        } catch(SQLException e){
            this.ErrorMsg = e.getMessage();
            return true;
        }
        return false;
    }
}
