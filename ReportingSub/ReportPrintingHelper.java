package ReportingSub;

import Entity.RefillItem;
import Entity.ReportItem;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author Paige Barnes
 * CSIS 483-D02
 * 05/21
 */
public class ReportPrintingHelper {

    private static final String DELIMITER = ",";
    private Desktop desktop = Desktop.getDesktop();

    // https://www.w3schools.com/java/java_files_create.asp
    // https://stackabuse.com/reading-and-writing-csvs-in-java/
    public void createReportFile(ObservableList<ReportItem> reportItems, String reportName){

        try{
            // https://stackoverflow.com/questions/1080634/how-to-get-the-desktop-path-in-java
            String desktopPath = System.getProperty("user.home") + "/Desktop";

            File report = new File(desktopPath + "/report.csv");

            System.out.print(report.getAbsolutePath());

            report.setWritable(true);
            report.setReadable(true);
            FileWriter reportFileWriter = new FileWriter(report);

            reportFileWriter.append(reportName);
            reportFileWriter.append("\n");

            reportFileWriter.append("InventoryItemId");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("StoreId");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("UPC");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("ItemName");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("Size");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("Color");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("QuantityAvail");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("QuantitySold");
            reportFileWriter.append("\n");

            for(ReportItem item : reportItems){
                reportFileWriter.append(String.join(DELIMITER, item.toString()));
                reportFileWriter.append("\n");
            }

            desktop.open(report);

            reportFileWriter.flush();
            reportFileWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public void createRefillFile(ObservableList<RefillItem> refillItems, String reportName){

        try{
            // https://stackoverflow.com/questions/1080634/how-to-get-the-desktop-path-in-java
            String desktopPath = System.getProperty("user.home") + "/Desktop";

            File report = new File(desktopPath + "/RefillReport.csv");

            System.out.print(report.getAbsolutePath());
            report.setWritable(true);
            report.setReadable(true);
            FileWriter reportFileWriter = new FileWriter(report);

            reportFileWriter.append(reportName);
            reportFileWriter.append("\n");

            // header
            reportFileWriter.append("UPC");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("ItemName");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("Color");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("Size");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("RefillQuantity");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("RefillIsInStock");
            reportFileWriter.append(DELIMITER);
            reportFileWriter.append("Full Stack");
            reportFileWriter.append("\n");

            // data
            for(RefillItem item : refillItems){
                reportFileWriter.append(String.join(DELIMITER, item.toString()));
                reportFileWriter.append("\n");
            }


            desktop.open(report);

            reportFileWriter.flush();
            reportFileWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public void createBarChartFile(BarChart barChart, String chartName) throws IOException {

        // https://www.tutorialspoint.com/how-to-create-save-the-image-of-a-javafx-pie-chart-without-actually-showing-the-window
        Scene scene = barChart.getScene();
        WritableImage chartImage = scene.snapshot(null);
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        File chartReport = new File(desktopPath + "/BarChart.png");

        ImageIO.write(SwingFXUtils.fromFXImage(chartImage, null), "PNG", chartReport);

        desktop.open(chartReport);

    }
}
