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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import sample.bussiness.BOType;
import sample.bussiness.BoFactory;
import sample.bussiness.custom.CustomerBO;
import sample.dto.CustomerDTO;
import sample.utill.CustomerTM;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class customerController implements Initializable {
    public JFXTextField customerName;
    public JFXTextField customerId;
    public JFXTextField customerAddress;
    public ImageView home;
    public JFXButton newCustomer;
    public Button save;
    public Button delete;
    public TableView <CustomerTM>table;
    public AnchorPane root;

    public TextField txt_searchBox;

    private CustomerBO customerBO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        customerBO = BoFactory.getInstance().getBO(BOType.Customer);


        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerID"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

        customerName.setDisable(true);
        customerAddress.setDisable(true);
        customerId.setDisable(true);
        save.setDisable(true);

    txt_searchBox.textProperty().addListener(observable ->  {
        table.getItems().clear();
        try {
            List<CustomerDTO> customerDTOS = customerBO.searchAll(txt_searchBox.getText()+"%");
            ObservableList<CustomerTM> searchItem =  table.getItems();

            for (CustomerDTO data:customerDTOS) {
                searchItem.add(new CustomerTM(data.getId(),data.getName(),data.getAddress()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
        @Override
        public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {
            CustomerTM selectedItem = table.getSelectionModel().getSelectedItem();
            if(selectedItem==null){
                save.setText("Save");
                delete.setDisable(true);
                return;
            }
            save.setText("Update");
            delete.setDisable(false);
            save.setDisable(false);
            customerName.setDisable(false);
            customerAddress.setDisable(false);
            customerId.setText(selectedItem.getCustomerID());
            customerName.setText(selectedItem.getCustomerName());
            customerAddress.setText(selectedItem.getCustomerAddress());
        }
    });

        try {
            refreshmytable();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void image_homeClick(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/sample/viwe/main.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage)(this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

    }


    public void btn_newcustomerClick(ActionEvent actionEvent) throws SQLException {
        customerId.clear();
        customerAddress.clear();
        customerName.clear();
        customerName.setDisable(false);
        customerAddress.setDisable(false);
        save.setDisable(false);
        customerName.requestFocus();

        table.getSelectionModel().clearSelection();


        try {

            String resultSet = customerBO.getLastCustomerId();

            if(resultSet==null){
                customerId.setText("C:001");
            }
            else {
                String[] split = resultSet.split(":");
                int val = Integer.parseInt(split[1]);
                val++;
                customerId.setText("C:00"+val);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,"Cannot create next Customer ID",
                    ButtonType.OK).show();
        }

    }



    public void btn_saveClick(ActionEvent actionEvent) throws SQLException {
        String id =customerId.getText();
        String name = customerName.getText();
        String address = customerAddress.getText();

        if( !name.equals("")&& !address.equals(""))
                if(!name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")){
                    Alert alert = new Alert(Alert.AlertType.WARNING,"Plese enter valueble name with only use charactors",
                            ButtonType.OK);
                    alert.showAndWait();
                    customerName.requestFocus();
                    return;
                }
                else if(!address.matches("\\S+[a-zA-Z0-9/.,:\\sa-zA-Z0-9]+\\S+")){
                    Alert alert = new Alert(Alert.AlertType.WARNING,"Plese enter valueble address with only use charactors and numbers ",
                            ButtonType.OK);
                    alert.showAndWait();
                    customerAddress.requestFocus();
                    return;
                }
                else {
                    if(save.getText().equals("Save")){
                        try {
                            customerBO.save(new CustomerDTO(id,name,address));
                        } catch (Exception e) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Cannot Save Customer",
                                    ButtonType.OK).show();
                        }
                    }
                    else{
                        try {
                            customerBO.update(new CustomerDTO(id,name,address));
                        } catch (Exception e) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Cannot Update Customer",
                                    ButtonType.OK).show();
                        }
                    }


                    refreshmytable();
                }


        else{
            if(name.equals("")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Enter Customer Name",
                        ButtonType.OK);
                alert.showAndWait();
                customerName.requestFocus();
                return;

            }
            if(address.equals("")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Enter Customer address",
                        ButtonType.OK);
                alert.showAndWait();
                customerAddress.requestFocus();
                return;

            }
        }
        customerId.clear();
        customerAddress.clear();
        customerName.clear();
        customerName.setDisable(true);
        customerAddress.setDisable(true);

    }

    private void refreshmytable() throws SQLException {
        table.getItems().clear();
        ObservableList<CustomerTM> customer = table.getItems();
        try {
            List<CustomerDTO> all = customerBO.findAll();
            if (all !=null){
                for (CustomerDTO data: all) {
                    customer.add(new CustomerTM(data.getId(),data.getName(),data.getAddress()));
                }
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.CONFIRMATION, "Cant refresh table",
                    ButtonType.OK).show();
        }
    }


    public void btn_deleteClick(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this customer?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES){
            CustomerTM selected = table.getSelectionModel().getSelectedItem();

            try {
                customerBO.delete(selected.getCustomerID());
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Cant Delete Customer",ButtonType.OK).show();
            }

            refreshmytable();
        }

    }


    public void customerIdOnAction(ActionEvent actionEvent) {

    }

    public void customerNameOnAction(ActionEvent actionEvent) {
        customerAddress.requestFocus();
    }

    public void customerAddressOnAction(ActionEvent actionEvent) {

    }
    public void btn_reportOnAction(ActionEvent actionEvent) throws JRException {

        JasperDesign load = JRXmlLoader.load(customerController.class.getResourceAsStream("/sample/report/customer.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        Map<String,Object> params = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,new JRBeanCollectionDataSource(table.getItems()));
        JasperViewer.viewReport(jasperPrint,false);
    }
}
