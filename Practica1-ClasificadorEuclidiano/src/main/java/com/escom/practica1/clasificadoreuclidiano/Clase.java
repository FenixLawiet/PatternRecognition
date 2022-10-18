/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica1.clasificadoreuclidiano;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Objeto clase de dos dimensiones
*/

import java.util.*;


public class Clase{

    private int repClases;
    private int clase[][];

    //Constructor - Método de llenado automático
    public Clase(int repClas){

        Scanner leer = new Scanner(System.in);
        
        repClases = repClas;
        clase = new int[2][repClas];
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

        //método para imprimir
        for(int i=0; i<2; i++){

            for(int j=0; j<getRepClases(); j++){

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
    public int[][] getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(int[][] clase) {
        this.clase = clase;
    }
}