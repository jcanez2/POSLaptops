//Assignment #6: Arizona Sate University CSE205 #6
// Name: Jesus Canez
// StudentID: 1207313779
// Lecture: Tue, Thur, 4:30PM
// Description: Your POSCart class creates a Tabbed Pane with
// two tabs, one for Laptop input and one for
// Laptop purchasing.

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent; //**Need to import to handle event
import javafx.event.EventHandler;  //**Need to import to handle event
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PurchasePane extends VBox
{
    //GUI components
    private static ArrayList<Laptop> laptopList, selectLists;

    //laptopLV for top ListView; selectedLV for bottom ListView
    private ListView<Laptop> laptopLV, selectedLV;

    private static ListView<String> listView = new ListView<>();
    private ListView<String> cartView = new ListView<>();
    private Button addBtn = new Button("Add to Cart");
    private Button removeBtn = new Button("Remove from Cart");
    private HBox btnBox = new HBox(40);
    private HBox infoBox = new HBox(40);
    public int qty = 0;
    private double total = 0.0;
    private Label qtyLabel;
    private Label totalLabel;

    //constructor
    public PurchasePane(ArrayList<Laptop> list)
    {
        //initialize instance variables
        this.laptopList = list;
        selectLists = new ArrayList<Laptop>();
        btnBox.setAlignment(Pos.CENTER);
        btnBox.getChildren().addAll(removeBtn,addBtn);

        DecimalFormat fmt3 = new DecimalFormat("$0.00");

        Label test = new Label("Available Laptops");
        test.setTextFill(Color.BLUE);
        Label selectedLabel = new Label("Selected Laptops");
        selectedLabel.setTextFill(Color.BLUE);
        qtyLabel = new Label("Qty Selected:" + qty);
        qtyLabel.setTextFill(Color.BLUE);
        totalLabel = new Label("Total Amt: " + fmt3.format(total));
        totalLabel.setTextFill(Color.BLUE);
        GridPane grid2 = new GridPane();

        infoBox.setAlignment(Pos.CENTER);
        infoBox.getChildren().addAll(qtyLabel,totalLabel);
        //listView.getItems().addAll(laptopList.toString(), "Titanic", "Contact", "The Social Network");
        this.getChildren().addAll(test, listView, btnBox, selectedLabel, cartView, infoBox);

        addBtn.setOnAction(new ButtonHandler2());
        removeBtn.setOnAction(new DeleteHandler());

    } //end of constructor

    public void updateQty()
    {
        qtyLabel.setText("Qty Selected:" + qty);
    }

    public void updateTotal()
    {
        DecimalFormat fmt4 = new DecimalFormat("$0.00");
        ObservableList<String> toTotal = cartView.getItems();
        double tempTotal = 0;
        for(String s : toTotal)
        {
            tempTotal += Double.parseDouble(s.substring((s.lastIndexOf("$")+ 1)));
        }
        total = tempTotal;
        totalLabel.setText("Total Amt:" + fmt4.format(total));

    }

    private class ButtonHandler2 implements EventHandler<ActionEvent>
    {
        //Override the abstract method handle()
        public void handle(ActionEvent e)
        {
            boolean isADuplicate = false;
            ObservableList<String> checkMe = cartView.getItems();
            if (checkMe.size() > 0)
            {
                for (String s : checkMe)
                {
                    isADuplicate = s.equals(listView.getSelectionModel().getSelectedItem());
                    if(isADuplicate) break;
                }
            }
            if (isADuplicate)
            {

            }
            else
            {
                if (listView.getItems().equals(null) || listView.getSelectionModel().isEmpty())
                {
                }
                else
                {
                    cartView.getItems().add(listView.getSelectionModel().getSelectedItem());
                    ObservableList<String> quantity;
                    quantity = cartView.getItems();
                    qty = quantity.size();
                    updateQty();
                    updateTotal();
                }
            }
        }
    } //end of ButtonHandler class

    private class DeleteHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            if(cartView.getItems().equals(null) || cartView.getSelectionModel().isEmpty() || listView.getSelectionModel().isEmpty())
            {
            }
            else
            {
                cartView.getItems().remove(cartView.getSelectionModel().getSelectedItem());
                ObservableList<String> quantity;
                quantity = cartView.getItems();
                qty = quantity.size();
                updateQty();
                updateTotal();
            }
        }
    }

    public static void convertToString()
    {
        if(laptopList.size() > 0 )
        {
            String s = laptopList.get(laptopList.size() - 1).toString();
            listView.getItems().add(s);
            System.out.println(listView);
        }
    }

} //end of PurchasePane class
