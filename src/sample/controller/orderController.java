package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import sample.bussiness.BOType;
import sample.bussiness.BoFactory;
import sample.bussiness.custom.CustomerBO;
import sample.bussiness.custom.ItemBO;
import sample.bussiness.custom.OrderBO;
import sample.db.HibernateUtill;
import sample.dto.*;
import sample.entity.OrderDetail;
import sample.utill.CustomerSubTM;
import sample.utill.CustomerTM;
import sample.utill.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Integer.parseInt;

public class orderController implements Initializable {
    public AnchorPane root;
    public ImageView home;
    public JFXButton newoder;
    public JFXTextField customerName;
    public JFXTextField itemDescription;
    public JFXTextField unitPrice;
    public JFXTextField qtyOnHand;
    public JFXTextField qty;
    public JFXButton adddata;
    public TableView<OrderTM> tableOrder;
    public JFXButton remove;
    public JFXComboBox<String> customerId;
    public JFXComboBox<String> ItemId;
    public JFXTextField orderId;
    public Label date;
    public TableView<CustomerSubTM> tableSubOrder;
    public TextField txt_searchBox;
    public Text total;
    public JFXButton deletebtn;

    LocalDate localDate = LocalDate.now();
    String inputuser;
    String inputitem;

    private OrderBO orderBO;
    private ItemBO itemBO;
    private CustomerBO customerBO;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        orderBO = BoFactory.getInstance().getBO(BOType.Order);
        itemBO  =BoFactory.getInstance().getBO(BOType.Item);
        customerBO = BoFactory.getInstance().getBO(BOType.Customer);

        tableOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        tableOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tableOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tableOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("ItemId"));
        tableOrder.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        tableOrder.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tableOrder.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tableOrder.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        tableSubOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tableSubOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        tableSubOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Qty"));
        tableSubOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));
        tableSubOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("btn"));


        adddata.setDisable(true);
        orderId.setEditable(false);
        customerName.setEditable(false);
        itemDescription.setEditable(false);
        unitPrice.setEditable(false);
        qty.setEditable(false);
        qtyOnHand.setEditable(false);
        customerId.setDisable(true);
        ItemId.setDisable(true);
        newoder.requestFocus();


        try {
            //ObservableList<String> abs =customerId.getItems();
            List<String> abs=customerId.getItems();
            List<CustomerDTO> all = customerBO.findAll();
            for (CustomerDTO data:all) {
                abs.add(data.getId());
            }
            customerId.setItems(FXCollections.observableArrayList(abs));

            System.out.println(abs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ObservableList<String> itemlist = ItemId.getItems();
            List<ItemDTO> all = itemBO.findAll();
            for (ItemDTO data: all) {
                itemlist.add(data.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        date.setText(String.valueOf(localDate));

//////////////////////////////////////////////////////////////////////////////////////////

        txt_searchBox.textProperty().addListener(observable -> {
            tableOrder.getItems().clear();
                try {
                    List<CustomDTO1> customDTO1s = orderBO.searchOrderDetails(txt_searchBox.getText() + "%");
                    ObservableList<OrderTM> searchItems = tableOrder.getItems();

                    for (CustomDTO1 data:customDTO1s) {
                        searchItems.add(new OrderTM(
                                data.getDate(),
                                data.getOrderId(),
                                data.getCustomerId(),
                                data.getCustomerName(),
                                data.getItemId(),
                                data.getItemDescription(),
                                data.getUnitPrice(),
                                data.getQty(),
                                data.getQtyOnHand()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
        });

        //////////////////////////////////////////////////////////////////

        try {
            refreshtable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectedId(ActionEvent actionEvent) throws SQLException {
        inputuser = (String) customerId.getSelectionModel().getSelectedItem();
        try {
            CustomerDTO customerDTO = customerBO.find(inputuser);
                customerName.setText(customerDTO.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectedItem(ActionEvent actionEvent) throws SQLException {
        inputitem = (String) ItemId.getSelectionModel().getSelectedItem();
        try {
            ItemDTO itemDTO = itemBO.find(inputitem);

                String descripton = itemDTO.getDescription();
                String string = String.valueOf(itemDTO.getQuantityOnHand());
                String string1 = String.valueOf(itemDTO.getUnitPrice());

                itemDescription.setText(descripton);
                qtyOnHand.setText(string);
                unitPrice.setText(string1);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void image_homeOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/sample/viwe/main.fxml"));
        Scene scene = new Scene(root);
        Stage primarystage = (Stage) (this.root.getScene().getWindow());
        primarystage.setScene(scene);
        primarystage.centerOnScreen();
    }

    public void btn_neworderOnAction(MouseEvent mouseEvent) throws SQLException {
        mytotal = 0.00;
        qty.setEditable(true);
        tableSubOrder.getItems().clear();
        onCart.clear();
        total.setText("0.00");

        try {
            String lastOrderId = orderBO.getLastOrderId();
            if(lastOrderId==null){
                orderId.setText("OD:001");
                return;
            }
            String[] split = lastOrderId.split(":");
            int val = parseInt(split[1]);
            val++;
            orderId.setText("OD:00" + val);

        } catch (Exception e) {
            e.printStackTrace();
        }

        adddata.setDisable(false);
        customerId.setDisable(false);
        ItemId.setDisable(false);
        qty.setDisable(false);

        itemDescription.clear();
        customerName.clear();
        unitPrice.clear();
        qtyOnHand.clear();
        qty.clear();

        customerId.getSelectionModel().clearSelection();
        ItemId.getSelectionModel().clearSelection();
    }

    public void btn_adddataOnAction(MouseEvent mouseEvent) throws SQLException, JRException {

        if (parseInt(qty.getText()) <= parseInt(qtyOnHand.getText())) {
            try {
                orderBO.save(new OrderDTO(orderId.getText(),String.valueOf(localDate),customerBO.find(inputuser)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (OrderTM buyItem : onCart) {

                try {
                    orderBO.saveOrderDetail(new OrderDetailsDTO(buyItem.getOrderId(),buyItem.getItemId(),buyItem.getQty(),buyItem.getUnitPrice()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    itemBO.updateQty(buyItem.getItemId(),buyItem.getQty());
                    itemBO.descQty(buyItem.getItemId(),buyItem.getQty());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Session session = HibernateUtill.getSessionFactory().openSession();
            session.doWork(connection -> {
                JasperReport load = null;
                try {

                    load = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/sample/report/placeOrder.jasper"));
                    HashMap<String, Object> objectObjectHashMap = new HashMap<>();
                    System.out.println(orderId.getText());
                    objectObjectHashMap.put("orderId", orderId.getText());
                    JasperPrint jasperPrint = JasperFillManager.fillReport(load, objectObjectHashMap, connection);
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (JRException e) {
                    e.printStackTrace();
                }

            });


            onCart.clear();
            refreshtable();
            adddata.setDisable(true);
        } else {
            Alert newalert = new Alert(Alert.AlertType.CONFIRMATION, "Sorry We Have NOT This Much Of Item To Sell", ButtonType.OK);
            newalert.showAndWait();
            qty.requestFocus();
        }
    }

    public void refreshtable() throws SQLException {
        tableOrder.getItems().clear();
        ObservableList<OrderTM> myOrderTable = tableOrder.getItems();
        try {
            List<CustomDTO1> allOrderDetails = orderBO.getAllOrderDetails();
            for (CustomDTO1 orderDTO:allOrderDetails){

                myOrderTable.add(new OrderTM(
                         orderDTO.getDate()
                        ,orderDTO.getOrderId()
                        ,orderDTO.getCustomerId()
                        ,orderDTO.getCustomerName()
                        ,orderDTO.getItemId()
                        ,orderDTO.getItemDescription()
                        ,orderDTO.getUnitPrice()
                        ,orderDTO.getQty()
                        ,orderDTO.getQtyOnHand()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btn_removeOnAction(MouseEvent mouseEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You Wish To Delete The Selected Order", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)) {
            OrderTM selectedItem = tableOrder.getSelectionModel().getSelectedItem();
            try {
                orderBO.delete(selectedItem.getItemId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*descqty.setObject(1, selectedItem.getQty());
            descqty.setObject(2, selectedItem.getItemId());
            descqty.executeUpdate();*/
            refreshtable();
        }
    }


    Double mytotal = 0.00;
    ObservableList<OrderTM> onCart = FXCollections.observableArrayList();


    public void addarraydata(){
        onCart.add(new OrderTM(date.getText(), orderId.getText(), inputuser, customerName.getText(), inputitem,
                itemDescription.getText(), Double.parseDouble(unitPrice.getText()), Integer.parseInt(qty.getText()),parseInt(qtyOnHand.getText())));
    }
    public void btn_addToCartOnAction(ActionEvent actionEvent) {
        Boolean stats = false;
        Integer increment = -1;
        tableSubOrder.getItems().clear();
            if(!onCart.isEmpty()) {
                for (OrderTM fixerror:onCart) {
                    increment++;
                    if (fixerror.getItemId().equals(inputitem)) {
                        stats=true;
                       Integer qtyfix= fixerror.getQty()+ Integer.valueOf(qty.getText());

                        onCart.set(increment, new OrderTM(date.getText(), orderId.getText(), inputuser, customerName.getText(), inputitem,
                                itemDescription.getText(), Double.valueOf(unitPrice.getText()), qtyfix, parseInt(qtyOnHand.getText())));
                    }
                }
                if(stats.equals(false)){
                    addarraydata();
                }

            }
            else{
                addarraydata();
            }

        ObservableList<CustomerSubTM> oderCart = tableSubOrder.getItems();
        mytotal = mytotal + (Double.valueOf(qty.getText()) * Double.valueOf(unitPrice.getText()));

        for (OrderTM oncartItem : onCart) {
            Double count = Double.valueOf(oncartItem.getQty()) * Double.valueOf(oncartItem.getUnitPrice());
            total.setText(String.valueOf(mytotal));
            oderCart.add(new CustomerSubTM(oncartItem.getCustomerId(), oncartItem.getItemDescription(), oncartItem.getQty(), count,new JFXButton("DELETE")));

        }

    }

    public void btn_reportOnAction(ActionEvent actionEvent) throws JRException {


    }

}