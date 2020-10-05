package project;

import javax.swing.JFrame;

public class Project {
    //Info for table creation
    private static final String[] Table = {"Products", "Orders", "Items", "Customers"};
    private static final String[] TableSQL = {"ID INTEGER UNSIGNED AUTO_INCREMENT not NULL, " +
                                              "Name VARCHAR(64), Price FLOAT, PRIMARY KEY(ID)",
                                              "ID INTEGER UNSIGNED AUTO_INCREMENT not NULL, " +
                                              "CustomerID INTEGER, PRIMARY KEY(ID)",
                                              "ID INTEGER UNSIGNED AUTO_INCREMENT not NULL, " +
                                              "OrderID INTEGER, ProductID INTEGER, Quantity INTEGER, PRIMARY KEY(ID)",
                                              "ID INTEGER UNSIGNED AUTO_INCREMENT not NULL, " +
                                              "FName VARCHAR(16), LName VARCHAR(16), City VARCHAR(64), Street VARCHAR(64), " +
                                              "State VARCHAR(2), Zip INTEGER, Phone VARCHAR(13), PRIMARY KEY(ID)"};
    private static final String[] Product = {"Carpet", "Tile", "Wood", "Unobtanium"};
    private static final float[] Price = {10.0f, 15.0f, 20.0f, 1000.0f};
    //Database info
    private static Database Database;
	private static final String DBURL = "";
    private static final String DBName = "";
    private static final String DBUser = "";
    private static final String DBPassword = "";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Connect to database
        Database = new Database(DBURL + DBName);
        if(Database.Connect(DBUser, DBPassword)){
            System.out.println("Could not connect to database. Quitting");
            System.out.println(Database.Error());
            System.exit(1);
        }
        //Create required tables if they don't already exist. Exit if create fails
        for(int i = 0; i < Table.length; i++){
            if(Database.TableCreate(Table[i], TableSQL[i])){
            System.out.println("Database could not be created. Quitting");
            System.out.println(Database.Error());
            System.exit(1);
            }
        }
        //Set up the product table
        for(int i = 0; i < Product.length; i++){
            if(Database.Product(Product[i]) == null){
                if(!Database.InsertProduct(new Product(Product[i], Price[i]))){
                    System.out.println(Database.Error());
                    System.exit(1);
                }
            }
        }
        //Show GUI
        GUI Form = new GUI(Database);
        JFrame Window = new JFrame();
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Window.add(Form);
        Window.setSize(700, 550);
        Window.setVisible(true);
    }
    
}
