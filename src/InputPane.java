//Assignment #6: Arizona Sate University CSE205 #6
// Name: Jesus Canez
// StudentID: 1207313779
// Lecture: Tue, Thur, 4:30PM
// Description: Your POSCart class creates a Tabbed Pane with
// two tabs, one for Laptop input and one for
// Laptop purchasing.

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class InputPane extends HBox
{
    //GUI components
    private ArrayList<Laptop> laptopList;
    private TextField brandTextbox;
    private TextField modelTextbox;
    private TextField cpuTextbox;
    private TextField ramTextbox;
    private TextField priceTextbox;
    private TextArea inputArea;
    private Label messageLabel = new Label("");

    //The relationship between InputPane and PurchasePane is aggregation
    private PurchasePane purchasePane;
    //----

    public InputPane(ArrayList<Laptop> list, PurchasePane pPane)
    {
        this.laptopList = list;
        this.purchasePane = pPane;

        //create a GridPane hold those labels & text fields
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,5,5,15));

        //Add the empty message level to the grid
        messageLabel.setTextFill(Color.FIREBRICK);
        grid.add(messageLabel,0,0,2,1);

        Label brandLabel = new Label("Brand");
        grid.add(brandLabel, 0,1);
        brandTextbox = new TextField();
        grid.add(brandTextbox, 1, 1);

        Label modelLabel = new Label("Model");
        grid.add(modelLabel, 0, 2);
        modelTextbox = new TextField();
        grid.add(modelTextbox, 1, 2);

        Label cpuLabel = new Label("CPU(GHz)");
        grid.add(cpuLabel, 0,3);
        cpuTextbox = new TextField();
        grid.add(cpuTextbox, 1, 3);

        Label ramLabel = new Label("RAM(GB)");
        grid.add(ramLabel, 0,4);
        ramTextbox = new TextField();
        grid.add(ramTextbox, 1, 4);

        Label priceLabel = new Label("Price($)");
        grid.add(priceLabel, 0,5);
        priceTextbox = new TextField();
        grid.add(priceTextbox, 1, 5);


        Button enterLaptopBtn = new Button("Enter a Laptop Info");
        HBox btnBox = new HBox(10);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.getChildren().add(enterLaptopBtn);
        grid.add(btnBox,0,7, 2, 1);
        //button action
        enterLaptopBtn.setOnAction(new ButtonHandler());

        inputArea = new TextArea("No Laptops");

        this.getChildren().add(grid);
        this.getChildren().add(inputArea);

    } //end of constructor

    private class ButtonHandler implements EventHandler<ActionEvent>
    {
         //Override the abstract method handle()
        public void handle(ActionEvent e)
        {
            String tempBrand = "";
            String tempModel = "";
            double tempCPU = 0;
            double tempRAM = 0;
            double tempPrice = 0;
            boolean checkBrand = false;
            boolean checkModel = false;
            boolean checkCPU = false;
            boolean checkRAM = false;
            boolean checkPrice = false;

            if (brandTextbox.textProperty().get().equals("") || modelTextbox.textProperty().get().equals("") || cpuTextbox.textProperty().get().equals("") || ramTextbox.textProperty().get().equals("") || priceTextbox.textProperty().get().equals(""))
            {
                messageLabel.setText("Please fill all fields");
            }

            if (brandTextbox.textProperty().get().equals("") || brandTextbox.textProperty().get().equals("Enter A Brand"))
            {
                brandTextbox.textProperty().set("Enter A Brand");
            }
            else
            {
                tempBrand = brandTextbox.textProperty().get();
                checkBrand = true;
            }

            if (modelTextbox.textProperty().get().equals("") || modelTextbox.textProperty().get().equals("Enter A Model"))
            {
                modelTextbox.textProperty().set("Enter A Model");
            }
            else
            {
                tempModel = modelTextbox.textProperty().get();
                checkModel = true;
            }

            try
            {
                tempCPU = Double.parseDouble(cpuTextbox.textProperty().get());
                checkCPU = true;

            }
            catch (NumberFormatException error)
            {
                cpuTextbox.textProperty().set("Invalid");
                messageLabel.setTextFill(Color.FIREBRICK);
                messageLabel.setText("Incorrect data format");
            }

            try
            {
                tempRAM = Double.parseDouble(ramTextbox.textProperty().get());
                checkRAM = true;
            }
            catch (NumberFormatException error)
            {
                ramTextbox.textProperty().set("Invalid");
                messageLabel.setTextFill(Color.FIREBRICK);
                messageLabel.setText("Incorrect data format");
            }

            try
            {
                tempPrice = Double.parseDouble(priceTextbox.textProperty().get());
                checkPrice = true;
            }
            catch (NumberFormatException error)
            {
                priceTextbox.textProperty().set("Invalid");
                messageLabel.setTextFill(Color.FIREBRICK);
                messageLabel.setText("Incorrect data format");
            }

            System.out.println(
                    "\nBrand format is: "+ checkBrand +
                    "\nModel format is: "+ checkModel +
                    "\nCPU format is: " +  checkCPU +
                    "\nRAM format is: "+   checkRAM +
                    "\nPrice format is: " +checkPrice);
            if(checkBrand && checkModel && checkCPU && checkRAM && checkPrice)
            {
                System.out.println("input is valid");
                boolean myChecker = false;
                Laptop checker = new Laptop(tempBrand,tempModel,tempCPU,tempRAM,tempPrice);
                for(Laptop x : laptopList)
                {
                    if (x.getBrand().equals(checker.getBrand()) && x.getModel().equals(checker.getModel()) && x.getCPU() == checker.getCPU() && x.getRAM() == checker.getRAM() && x.getPrice() == checker.getPrice())
                    {
                        messageLabel.setTextFill(Color.FIREBRICK);
                        messageLabel.setText("Laptop not added - duplicate");
                        myChecker = true;
                    }

                }

                if(!myChecker)
                {
                    laptopList.add(checker);
                    PurchasePane.convertToString();
                    brandTextbox.textProperty().set("");
                    modelTextbox.textProperty().set("");
                    cpuTextbox.textProperty().set("");
                    ramTextbox.textProperty().set("");
                    priceTextbox.textProperty().set("");
                    messageLabel.setTextFill(Color.BLACK);
                    messageLabel.setText("New laptop added");

                    StringBuilder builder = new StringBuilder();
                    for(Laptop i : laptopList)
                    {
                        builder.append(i.toString());
                    }
                    inputArea.setText(builder.toString());
                }
            }
        }//end of handle() method
    }//end of ButtonHandler class
}
