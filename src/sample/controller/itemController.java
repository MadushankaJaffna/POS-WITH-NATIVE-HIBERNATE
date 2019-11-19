package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import sample.bussiness.BOType;
import sample.bussiness.BoFactory;
import sample.bussiness.custom.ItemBO;
import sample.db.HibernateUtill;
import sample.dto.ItemDTO;
import sample.utill.ItemTM;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class itemController implements Initializable {
    public AnchorPane root;
    public ImageView home;
    public JFXButton btnAdd;
    public JFXTextField itemCode;
    public JFXTextField itemDescription;
    public JFXTextField QtyOnHand;
    public JFXTextField unitPrice;
    public Button btnsave;
    public Button btnDelete;
    public TableView <ItemTM>tableItem;
    public TextField txt_search;


    private ItemBO itemBO;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemBO= BoFactory.getInstance().getBO(BOType.Item);

        tableItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tableItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        tableItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tableItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        
        itemCode.setDisable(true);
        itemDescription.setDisable(true);
        QtyOnHand.setDisable(true);
        unitPrice.setDisable(true);


        tableItem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemTM> observable, ItemTM oldValue, ItemTM newValue) {
                ItemTM itemsselect = tableItem.getSelectionModel().getSelectedItem();

                if( itemsselect == null){
                    btnsave.setText("Save");
                    btnDelete.setDisable(true);


                }
                else{
                    btnsave.setText("Update");
                    btnsave.setDisable(false);
                    btnDelete.setDisable(false);
                    itemDescription.setDisable(false);
                    QtyOnHand.setDisable(false);
                    unitPrice.setDisable(false);
                    itemCode.setText(itemsselect.getItemCode());
                    itemDescription.setText(itemsselect.getItemDescription());
                    QtyOnHand.setText(itemsselect.getQtyOnHand());
                    unitPrice.setText(itemsselect.getUnitPrice());
                }
            }
        });

        txt_search.textProperty().addListener(observable -> {
            tableItem.getItems().clear();
            try {
                List<ItemDTO> itemDTOList = itemBO.searchAll(txt_search.getText() + "%");
                List<ItemTM> itemTMS=tableItem.getItems();

                for (ItemDTO itemDTO:itemDTOList){
                    itemTMS.add(new ItemTM(itemDTO.getCode(),itemDTO.getDescription(),
                            Integer.toString(itemDTO.getQuantityOnHand()),Double.toString(itemDTO.getUnitPrice())));
                }


            }catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            refreshmytable();
        } catch (SQLException e) {
        }
    }

    public void image_homeOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/sample/viwe/main.fxml"));
        Scene sence = new Scene(root);
        Stage primaryStage = (Stage)(this.root.getScene().getWindow());
        primaryStage.setScene(sence);
        primaryStage.centerOnScreen();
    }


    public void btn_addOnAction(ActionEvent actionEvent) throws SQLException {
        itemCode.clear();
        itemDescription.clear();
        QtyOnHand.clear();
        unitPrice.clear();
        itemDescription.setDisable(false);
        QtyOnHand.setDisable(false);
        unitPrice.setDisable(false);
        itemDescription.requestFocus();
        btnsave.setText("Save");

        try {
            String lastItemId = itemBO.getLastItemId();
            System.out.println(lastItemId+"-------");
            if(lastItemId ==null){
                itemCode.setText("I:001");
                return;
            }
            String[] split = lastItemId.split(":");
            int count = Integer.parseInt(split[1]);

            count+=1;
            System.out.println(count);
            itemCode.setText("I:00"+count);
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,"Can't create item code",ButtonType.OK).show();
        }


    }

    public void txtbox_itemCodeOnAction(ActionEvent actionEvent) { }

    public void txtbox_itemDescriptionOnAction(ActionEvent actionEvent) {
        QtyOnHand.requestFocus();
    }

    public void txtbox_qtyOnHandOnAction(ActionEvent actionEvent) {unitPrice.requestFocus();}

    public void txtbox_unitPriceOnAction(ActionEvent actionEvent) { }

    public void btn_saveOnAction(ActionEvent actionEvent) throws SQLException {
        String itemid = itemCode.getText();
        String description = itemDescription.getText();
        String quantity = QtyOnHand.getText();
        String unitprice1 = unitPrice.getText();

        if(!description.equals("")&&!quantity.equals("")&&!unitprice1.equals("")){
                if(!description.matches("^(.|\\s)*[a-zA-Z]+(.|\\s)*$")){
                    Alert newalaert = new Alert(Alert.AlertType.CONFIRMATION,"Please Fill valid Description with charactors",ButtonType.OK);
                    newalaert.showAndWait();
                    itemDescription.requestFocus();
                    return;
                }
                else if(!quantity.matches("[0-9]+")){
                    Alert newalert =new Alert(Alert.AlertType.CONFIRMATION,"Please use only numbers",ButtonType.OK);
                    newalert.showAndWait();
                    QtyOnHand.requestFocus();
                    return;
                }
                else if(!unitprice1.matches("([0-9]+.[0-9]{0,2})")){
                    Alert newalert = new Alert(Alert.AlertType.CONFIRMATION,"please use only numbers",ButtonType.OK);
                    newalert.showAndWait();
                    unitPrice.requestFocus();
                    return;
                }
                else {
                    if (btnsave.getText().equals("Save")) {

                        try {
                            itemBO.save(new ItemDTO(itemCode.getText(),description,Integer.parseInt(quantity),Double.parseDouble(unitprice1)));
                            refreshmytable();

                            itemDescription.clear();
                            QtyOnHand.clear();
                            unitPrice.clear();
                            itemDescription.setDisable(true);
                            QtyOnHand.setDisable(true);
                            unitPrice.setDisable(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        try {
                            itemBO.update(new ItemDTO(itemCode.getText(),description,Integer.parseInt(quantity),Double.parseDouble(unitprice1)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        refreshmytable();

                        itemCode.clear();
                        itemDescription.clear();
                        QtyOnHand.clear();
                        unitPrice.clear();
                        itemDescription.setDisable(true);
                        QtyOnHand.setDisable(true);
                        unitPrice.setDisable(true);
                        btnsave.setText("Save");
                    }
                }
        }
        else{
            if(description.equals("")){
                Alert newalart = new Alert(Alert.AlertType.WARNING, "Please Fill Item Description", ButtonType.OK);
                newalart.showAndWait();
                itemDescription.requestFocus();
                return;
            }
            else if(quantity.equals("")){
                Alert newalart = new Alert(Alert.AlertType.WARNING,"Plese Fill Quantity On Hand",ButtonType.OK);
                newalart.showAndWait();
                QtyOnHand.requestFocus();
                return;
            }
            else if(unitprice1.equals("")){
                Alert newalert = new Alert(Alert.AlertType.WARNING,"Please fill Unit Price",ButtonType.OK);
                newalert.showAndWait();
                unitPrice.requestFocus();
                return;
            }
        }

    }

    private void refreshmytable() throws SQLException {
        tableItem.getItems().clear();
        try {
            List<ItemDTO> itemDTOList = itemBO.findAll();
            List<ItemTM> itemTMList=tableItem.getItems();
            for (ItemDTO itemDTO:itemDTOList) {
                itemTMList.add(new ItemTM(itemDTO.getCode(),itemDTO.getDescription(),Integer.toString(itemDTO.getQuantityOnHand()),Double.toString(itemDTO.getUnitPrice())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void btn_DeleteOnAction(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this item?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            ItemTM selectedItem = tableItem.getSelectionModel().getSelectedItem();

            try {
                itemBO.delete(selectedItem.getItemCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
            refreshmytable();

            itemCode.clear();
            itemDescription.clear();
            QtyOnHand.clear();
            unitPrice.clear();
            itemDescription.setDisable(true);
            QtyOnHand.setDisable(true);
            unitPrice.setDisable(true);

        }
    }


    public void btn_reportOnAction(ActionEvent actionEvent) throws JRException {
        Session session = HibernateUtill.getSessionFactory().openSession();
        session.doWork(connection -> {
            JasperDesign load = null;
            try {
                load = JRXmlLoader.load(itemController.class.getResourceAsStream("/sample/report/itemReport.jrxml"));
                JasperReport jasperReport = JasperCompileManager.compileReport(load);
                Map<String,Object> params = new HashMap<>();
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,connection);
                JasperViewer.viewReport(jasperPrint,false);

            } catch (JRException e) {
                e.printStackTrace();
            }
        });
    }
}
