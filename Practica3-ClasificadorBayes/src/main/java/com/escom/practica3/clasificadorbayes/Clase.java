/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica3.clasificadorbayes;

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
    public Clase(int numClase){

        /*
        Se generarán los puntos de cada clase aleatoriamente de acuerdo a un 
        centroide y a una dispersión. Para hacer una muestra homogénea, se 
        establece una cantidad de 50 representantes de clase por cada clase.
        */
        Scanner leer = new Scanner(System.in);
        int coorx=0; int coory=0; int disp = 0;
        
        repClases = 50;
        clase = new double[2][repClases];
        
        do{
            
            System.out.println("Ingrese la coordenada x del centroide de la clase "+(numClase+1)+": ");
            coorx=leer.nextInt();
            System.out.println("Ingrese la coordenada y del centroide de la clase "+(numClase+1)+": ");
            coory=leer.nextInt();
            System.out.println("Ingrese la dispersión de la clase "+(numClase+1)+": ");
            disp=leer.nextInt();
            if(coorx>=100 || coory>=100 || coorx<=-100 || coory<=-100 ||disp==0){
                    
                System.out.println("*******ERROR. Uno o más datos no válidos. Reingrese datos*******");     
            }
        }while(coorx>=100 || coory>=100 || coorx<=-100 || coory<=-100 ||disp==0);
            
        //Generando el random y llenando la clase. El primer elemento siempre es el centroide
        //clase[0][0]=coorx;
        //clase[1][0]=coory;
        for(int j=0; j<2; j++){

            for(int k=0; k<repClases; k++){
      
                if(j==0){
                
                    //Para revisar que no es el centroide
                    if(j==0 && k==0){

                        clase[j][k]=coorx;
                    }
                    else{
                        
                        clase[j][k]=((Math.random()*(disp-0+1)+0))+coorx; //clase[j][k]=((int)(Math.random()*(disp-0+1)+0))+coorx;
                        // Valor entre 0 y la dispersion, ambos incluidos.
                        //Y se agrega el desplzamiento de su coordenada correspondiente
                    }
                }
                else{
                
                    if(j==1){
                    
                        //Para revisar que no es el centroide
                        if(j==1 && k==0){
                
                            clase[j][k]=coory;
                        }
                        else{
                        
                            clase[j][k]=((Math.random()*(disp-0+1)+0))+coory;
                            // Valor entre 0 y la dispersion, ambos incluidos.
                            //Y se agrega el desplzamiento de su coordenada correspondiente
                        }
                    }
                }
                
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
