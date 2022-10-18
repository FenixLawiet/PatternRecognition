/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica2.clasificadormahalanobis;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Objeto clase de dos dimensiones
*/

import java.util.*;


public class Clase{

    private int repClases;
    private double clase[][];

    //Generico
    public Clase(){}

    //Constructor - Método de llenado automático
    public Clase(int repClas){

        Scanner leer = new Scanner(System.in);
        
        repClases = repClas;
        clase = new double[2][repClas];
        for(int i=0; i<2; i++){

            for(int j=0; j<repClases; j++){

                if(i>0){
                    
                    System.out.println("Ingrese el valor de  Y"+(j+1)+": ");
                    clase[i][j]=leer.nextInt();
                }
                else{
                
                    System.out.println("Ingrese el valor de X"+(j+1)+": ");
                    clase[i][j]=leer.nextInt();
                }
                //clase[i][j]=(int)(Math.random()*(100-0+1)+0); // Valor entre 0 y 100, ambos incluidos.
                //De no ser automático, leer desde teclado los valores para cada casilla
            }
        }
    }

    public void imprimeClase(){

        //Obteniendo tamaño de la clase
        int row = clase.length;
        int col = clase[0].length;

        //método para imprimir
        for(int i=0; i<row; i++){

            for(int j=0; j<col; j++){

                System.out.print(getClase()[i][j]+" ");
            }
            System.out.print("\n");
        }
    }
    
    /**
     * @return the repClases
     */
    public int getRepClases() {
        return repClases;
    }

    /**
     * @param repClases the repClases to set
     */
    public void setRepClases(int repClases) {
        this.repClases = repClases;
    }

    /**
     * @return the clase
     */
    public double[][] getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(double[][] clase) {
        this.clase = clase;
    }
}