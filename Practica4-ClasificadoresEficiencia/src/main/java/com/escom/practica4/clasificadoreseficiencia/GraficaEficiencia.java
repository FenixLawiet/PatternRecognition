/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica4.clasificadoreseficiencia;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Grafica de la eficiencia de clasificación
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 *
 * @author fenix
 */
public class GraficaEficiencia extends JFrame{
    
    double precision[];
    int metodo;
    String eficiencia;
    DefaultCategoryDataset dataset= new DefaultCategoryDataset();
     
     
    public GraficaEficiencia(double precision[], String clasificador,int metodo){
        
        this.precision=precision;
        this.metodo=metodo;
       
        //Llenando los datos
        switch(metodo){
            
            case 1:
                eficiencia="Método de restitución";
                break;
            
            case 2:
                eficiencia="Método de 50/50";
                break;
            
            case 3:
                eficiencia="Método de Hold in one";
                break;
        }
        for(int i=0; i<precision.length; i++){
            
            dataset.setValue(precision[i], clasificador, "Clase "+(i+1)); //Valor de y (precision), categoria, valor de x (clase perteneciente)
        }
             
        //Para la pantalla
        JFreeChart graficoBarras = ChartFactory.createBarChart("Gráfica de Eficiencia. "+eficiencia, "Clases", "Precisión %", dataset);
        
       //Agregando la gráfica al panel
        ChartPanel panel = new ChartPanel(graficoBarras);  
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
