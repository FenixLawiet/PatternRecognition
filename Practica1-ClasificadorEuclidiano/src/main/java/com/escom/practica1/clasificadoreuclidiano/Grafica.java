/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica1.clasificadoreuclidiano;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Grafica de las clases
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;
import org.jfree.chart.ChartPanel;
/**
 *
 * @author fenix
 */
public class Grafica extends JFrame{
    
    int coorx, coory;
    Clase rasgos[];
    XYSeriesCollection dataset = new XYSeriesCollection();     
    XYSeries clases[]; 
     
    public Grafica(int coorx, int coory, Clase rasgos[]){
        
        this.coorx=coorx;
        this.coory=coory;
        this.rasgos=rasgos;
       
        int aux=rasgos[0].getRepClases();
        //int dimX = 0; 
        int dimY = 0;
        
        //Inicializo el arreglo de series
        clases=new XYSeries[(rasgos.length+1)];
        
        //Llenando las series con las clases y sus representantes de clase
        for(int i=0; i<rasgos.length; i++){
        
            clases[i]=new XYSeries("Clase "+(i+1));
            
            for(int j=0; j<aux; j++){
            
                int temp1=rasgos[i].getClase()[0][j];
                int temp2=rasgos[i].getClase()[1][j];
                //System.out.println("VALORES: temp1: "+temp1+" temp2:"+temp2);
                clases[i].add(temp1, temp2);
            }
            
            dataset.addSeries(clases[i]);
        }
        
        clases[rasgos.length]=new XYSeries("Vector de posicion");
        clases[rasgos.length].add(coorx, coory);
        dataset.addSeries(clases[rasgos.length]);
        
        //Para la pantalla
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                "Gráfica del clasificador", // Chart title
                "Dimensión X", // X-Axis Label
                "Dimensión Y", // Y-Axis Label
                dataset // Dataset for the Chart
                );
        
       //Agregando la gráfica al panel
        ChartPanel panel = new ChartPanel(scatterPlot);  
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(500, 500));
        
        JPanel jPanel1=new JPanel();
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(panel, BorderLayout.NORTH);   
        jPanel1.validate();
        add(jPanel1);
        
        //Definiendo la ventana
        setSize(600, 600); //Dimensiones del JFrame
        setResizable(true); //Redimensionable
        setVisible(true);
    }   
}
