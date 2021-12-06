package ISMS;

import BackstockSub.BackstockController;
import Entity.Employee;
import ReportingSub.ReportingController;
import StockInterface.POSController;
import StockInterface.SARAController;
import Entity.Finder;
import WaitlistSub.WaitlistController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 04/21
 */
public class ISMSController implements Initializable {

    // Controls home screen and redirects for ISMS

    @FXML
    private Button posButton;

    @FXML
    private Button saraButton;

    @FXML
    private Button stockCheckButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button waitlistButton;

    @FXML
    private Label message;

    @FXML
    private TextField associateId;

    @FXML
    private Label associate;

    ISMSContext ISMSContext = new ISMSContext();
    Finder Finder = new Finder();

    public void initialize(URL url, ResourceBundle rb){
        posButton.setDisable(true);
        saraButton.setDisable(true);
        stockCheckButton.setDisable(true);
        reportButton.setDisable(true);
        waitlistButton.setDisable(true);
    }

    @FXML
    public void login(ActionEvent event) throws SQLException {
        posButton.setDisable(true);
        saraButton.setDisable(true);
        stockCheckButton.setDisable(true);
        reportButton.setDisable(true);
        waitlistButton.setDisable(true);

        if(this.associateId.getText().isEmpty()){
            this.message.setText("Please enter your associate ID");
        }
        else{
            Employee employee = this.Finder.employeeFinder(Integer.parseInt(this.associateId.getText()));
            if(employee != null){
                String employeeRank = "Associate";

                this.ISMSContext.setLoggedInEmployee(employee);
                this.ISMSContext.setCurrentStore(Finder.storeFinder(employee.getStoreID()));

                if(this.ISMSContext.getLoggedInEmployee().getIsManager()){
                    employeeRank = "Manager";
                    saraButton.setDisable(false);
                    reportButton.setDisable(false);
                }
                posButton.setDisable(false);
                stockCheckButton.setDisable(false);
                waitlistButton.setDisable(false);
                associate.setText("Logged in as " + employee.getFirstName() + " " + employee.getLastName() + " - " + employeeRank);
            }
            else{
                this.message.setText("Invalid associate ID");
            }

        }

    }

    @FXML
    public void openPOS(ActionEvent event){

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/StockInterface/POSTest.fxml").openStream());

            POSController posController = (POSController)loader.getController();
            posController.setContext(ISMSContext);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("POS Test");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openSARA(ActionEvent event){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/StockInterface/SARATest.fxml").openStream());

            SARAController saraController = (SARAController) loader.getController();
            saraController.setContext(ISMSContext);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("SARA Test");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openStockReports(ActionEvent event){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/ReportingSub/Reporting.fxml").openStream());

            ReportingController reportingController = (ReportingController) loader.getController();
            reportingController.setContext(ISMSContext);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Reporting Subsystem");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openWaitlist(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/WaitlistSub/Waitlist.fxml").openStream());

            WaitlistController waitlistController = (WaitlistController) loader.getController();
            waitlistController.setContext(ISMSContext);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Waitlist Subsystem");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openStockCheck(ActionEvent event){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/BackstockSub/Backstock.fxml").openStream());

            BackstockController backstockController = (BackstockController) loader.getController();
            backstockController.setContext(ISMSContext);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Backstock Check System");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
