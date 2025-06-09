package com.project.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.project.Database.HistoryDatabase;
import com.project.Database.ProductsDatabase;
import com.project.Model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Pair;

public class DashboardController implements Initializable{
    @FXML
    private BarChart<String, Number> barchart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private PieChart piechart;

    @FXML
    private ListView<Product> mylist;

    private ObservableList<Product> l = FXCollections.observableArrayList();

    //List<Pair<String, Integer>> list;
    public static class ProductInfo {
        public final String name;
        public final Integer quantity;
        public final Double price;

        public ProductInfo(String name, Integer quantity, Double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }
        public int getQuantity() { return quantity; }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //list = new ArrayList<>();
        List<ProductInfo> list = new ArrayList<>();
        XYChart.Series <String, Number>series = new XYChart.Series<>();
        HistoryDatabase.getTopSellingProducts(list);
        
        //list.sort(Comparator.comparing(Pair<String, Integer>::getValue).reversed());
        list.sort(Comparator.comparingInt(ProductInfo::getQuantity).reversed());
        for(int i = 0; i<5 ; i++){
            if(i < list.size()){
                series.getData().add(new XYChart.Data<>(list.get(i).name, list.get(i).quantity));
            }else{
                series.getData().add(new XYChart.Data<>("Null"+i, 0));
            }
        }
        ArrayList<PieChart.Data> qrt = new ArrayList<>();
        for (ProductInfo p : list) {
            qrt.add(new PieChart.Data(p.name, p.price));
        }
        
        barchart.setCategoryGap(50);
        //barchart.setBarGap(50);

        barchart.getData().add(series); 
        piechart.getData().addAll(qrt);

        ProductsDatabase.getLowOnStock(l);

        //l.add(new Product("Apple", "first", 3, 0, 0, null, 0, 0, null));

        mylist.setPlaceholder(new Label("Stock is good"));

        mylist.setCellFactory(listView -> new DashboardCellController());
        
        mylist.setItems(l);

        
    }
    
}
