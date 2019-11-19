package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import sample.bussiness.BOType;
import sample.bussiness.BoFactory;
import sample.bussiness.custom.OrderBO;
import sample.dao.custom.QueryDAO;
import sample.dto.CustomDTO1;
import sample.utill.summeryTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;



public class summeryController implements Initializable {
    public Text date;
    public Text summeryBalance;
    public Button back;
    public Button print;
    public TableView<summeryTM> summeryTable;
    public AnchorPane root;

    LocalDate dateNow = LocalDate.now();

    private OrderBO orderBO = BoFactory.getInstance().getBO(BOType.Order);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        summeryTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemId"));
        summeryTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("desccription"));
        summeryTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        summeryTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        summeryTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        try {

            Double total=0.00;
            ObservableList<summeryTM> summery = summeryTable.getItems();
            List<CustomDTO1> customDTO1s = orderBO.summaryDetails();
            for(CustomDTO1 data:customDTO1s){
                total = total + data.getTotal();
                Integer increment = -1;
                Boolean stats = false;
                if(!summery.isEmpty()) {
                    for (summeryTM checking : summery) {
                        increment++;
                        if(checking.getItemId().equals(data.getItemId())){
                            stats=true;
                            summery.set(increment, new summeryTM(data.getItemId(),
                                    data.getItemDescription(),
                                    data.getUnitPrice(),
                                    data.getQty()+checking.getQty(),
                                    data.getTotal()+checking.getTotal()));
                        }

                    }
                    if(stats.equals(false)){
                        summery.add(new summeryTM(
                                data.getItemId(),
                                data.getItemDescription(),
                                data.getUnitPrice(),
                                data.getQty(),
                                data.getTotal()));
                    }
                }
                else {
                    summery.add(new summeryTM(
                            data.getItemId(),
                            data.getItemDescription(),
                            data.getUnitPrice(),
                            data.getQty(),
                            data.getTotal()));
                }
            }
            summeryBalance.setText(String.valueOf(total));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        date.setText(String.valueOf(dateNow));
    }

    public void btn_BackOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/sample/viwe/main.fxml"));
        Scene scene = new Scene(root);
        Stage primarystage = (Stage) (this.root.getScene().getWindow());
        primarystage.setScene(scene);
        primarystage.centerOnScreen();
    }

    public void btn_PrintOnAction(ActionEvent actionEvent) throws JRException {
        JasperDesign load = JRXmlLoader.load(customerController.class.getResourceAsStream("/sample/report/summeryReport.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        Map<String,Object> params = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,new JRBeanCollectionDataSource(summeryTable.getItems()));
        JasperViewer.viewReport(jasperPrint,false);
    }
}
