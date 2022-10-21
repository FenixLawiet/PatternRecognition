/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica3.clasificadorbayes;
/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Objeto media de dos dimensiones
*/
import java.util.*;

public class MediaClase{

    private double media[][];
    int divisor;
    double sumaMediaX;
    double sumaMediaY;
    
    Clase aux; 

    public MediaClase(){

        media = new double[2][1];
    }

    public void calculoMedia(int repClases, Clase matriz){

        divisor = repClases;
        aux = matriz;

        //Saca primeramente las sumas medias de los arreglos
        for(int i=0; i<2; i++){

            //Suma la primera fila, valores de X
            if(i==0){

                for(int j=0; j<divisor; j++){

                    sumaMediaX=(sumaMediaX+aux.getClase()[i][j]);
                }
            }
            
            //Suma la segunda fila, valores de Y
            if(i==1){

                for(int j=0; j<divisor; j++){

                    sumaMediaY=(sumaMediaY+aux.getClase()[i][j]);
                }
            }
        }

        //Divide las sumas medias por el divisor
        getMedia()[0][0]=(sumaMediaX/divisor);
        getMedia()[1][0]=(sumaMediaY/divisor);
    }

    public void imprimirMedia(){

        System.out.println(getMedia()[0][0]);
        System.out.println(getMedia()[1][0]);
    }
    
    /**
     * @return the media
     */
    public double[][] getMedia() {
        return media;
    }

    /**
     * @param media the media to set
     */
    public void setMedia(double[][] media) {
        this.media = media;
    }
    
}
