import java.io.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.ChartUtilities; 
public class LinePlot {  
      public static void main(String[] args){
         try {
                
                /* Step - 1: Define the data for the line chart  */
                DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
                for(int x = 0; x < 20; x++) {
                	line_chart_dataset.addValue(120+(int)(Math.random() *((180-120)+1)), "heart rate", String.valueOf(x));
                }
                
                                /* Step -2:Define the JFreeChart object to create line chart */
                JFreeChart lineChartObject=ChartFactory.createLineChart("Heart Rate vs Time","Time (seconds)","Heart Rate (BPM)",line_chart_dataset,PlotOrientation.VERTICAL,true,true,false);                
                          
                
                /* Step -3 : Write line chart to a file */               
                 int width=640; /* Width of the image */
                 int height=480; /* Height of the image */                
                 File lineChart=new File("testGraph.png");              
                 ChartUtilities.saveChartAsPNG(lineChart,lineChartObject,width,height);
                 System.out.println("printed the picture");
         }
         catch (Exception i)
         {
             System.out.println(i);
         }
     }
 }