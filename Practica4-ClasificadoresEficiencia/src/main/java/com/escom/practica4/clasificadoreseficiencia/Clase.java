/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica4.clasificadoreseficiencia;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Objeto clase de dos dimensiones
*/

import java.util.*;


public class Clase{

    private int repClases;
    private double clase[][];
    private int coorx=0; 
    private int coory=0; 
    private int disp = 0;

    //Generico
    public Clase(){}

    //Constructor - Método de llenado automático
    public Clase(int numClase, int repClases){

        /*
        Se generarán los puntos de cada clase aleatoriamente de acuerdo a un 
        centroide y a una dispersión. Esta clase recibe la cantidad de n 
        representantes de clase y establece esa cantidad para todas la clases 
        generadas.
        */
        Scanner leer = new Scanner(System.in);
        this.repClases=repClases;
        
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

    //Para construir automaticamente una clase con centroide y dispersión dadas
    public Clase(int coorx, int coory, int disp, int repClases){
    
        //Generando el random y llenando la clase. El primer elemento siempre es el centroide
        clase = new double[2][repClases];
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
    
    /**
     * @return the coorx
     */
    public int getCoorx() {
        return coorx;
    }

    /**
     * @param coorx the coorx to set
     */
    public void setCoorx(int coorx) {
        this.coorx = coorx;
    }

    /**
     * @return the coory
     */
    public int getCoory() {
        return coory;
    }

    /**
     * @param coory the coory to set
     */
    public void setCoory(int coory) {
        this.coory = coory;
    }

    /**
     * @return the disp
     */
    public int getDisp() {
        return disp;
    }

    /**
     * @param disp the disp to set
     */
    public void setDisp(int disp) {
        this.disp = disp;
    }
}