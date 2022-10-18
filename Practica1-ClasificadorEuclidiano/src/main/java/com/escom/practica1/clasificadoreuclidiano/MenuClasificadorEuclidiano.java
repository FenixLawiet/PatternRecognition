/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica1.clasificadoreuclidiano;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Menu del clasificador de distancia euclidiana (2 dimensiones)
    Umbral de distancia igual a 100
*/

/*
    uci machine learning; kaggle (plataformas de phyton)                     
*/
import java.lang.*;
import java.util.*;

public class MenuClasificadorEuclidiano{

    int numClases;
    int repClases;
    int coorx;
    int coory; 
    
    Clase conjuntoC[];
    MediaClase conjuntoM[];
    
    int resClase;
    
    Grafica g;
    
    //Constructor
    public MenuClasificadorEuclidiano(){
        //VACIO
    }

    public void ClasificadorEuclidiano(){

        Scanner leer = new Scanner(System.in);
        String condicion;
        int contador=0;
        do{

            if(contador>0){

                recibeVector();
                if(coorx>100 || coory>100){

                    System.out.println("El vector esta demasiado lejos del umbral, NO TIENE PERTENENCIA");
                }
                else{

                    resClase=distMin();
                    System.out.println("El vector ("+coorx+","+coory+") pertenece a la clase "+resClase);
                    //mandar a llamar al graficador y mandar de parametros las clases y el punto
                    g=new Grafica(coorx, coory, conjuntoC);
                }
            }
            else{

                recibeDatosClases();
                recibeVector();
                if(coorx>100 || coory>100){

                    System.out.println("El vector esta demasiado lejos del umbral, NO TIENE PERTENENCIA");
                }
                else{

                    crearClases();
                    mostrarClases();
                    medias();
                    resClase=distMin();
                    System.out.println("El vector ("+coorx+","+coory+") pertenece a la clase "+resClase);
                    //mandar a llamar al graficador y mandar de parametros las clases y el punto
                    g=new Grafica(coorx, coory, conjuntoC);
                }
            }
            contador++;
            System.out.println("¿Desea probar con otro vector? Y/N: ");
            condicion=leer.nextLine();
        } while(condicion.equals("Y") || condicion.equals("y"));
    }
    
    public void recibeDatosClases(){

        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el número de clases: ");
        numClases=leer.nextInt();
        System.out.println("Ingrese el número de representantes por clase: ");
        repClases=leer.nextInt();
    }

    public void recibeVector(){

        Scanner leer = new Scanner(System.in);
        //Umbral de tolerancia de menor a 100
        System.out.println("Ingrese coordenada x del vector a clasificar: ");
        coorx=leer.nextInt();
        System.out.println("Ingrese coordenada y del vector a clasificar: ");
        coory=leer.nextInt();
    }

    public void crearClases(){

        conjuntoC=new Clase[numClases];
        for(int i=0; i<numClases; i++){

            System.out.println("******CLASE "+(i+1)+"******");
            conjuntoC[i]= new Clase(repClases); 
        }
    }
    
    public void mostrarClases(){

        for(int i=0; i<numClases; i++){

            System.out.println("CLASE "+(i+1));
            conjuntoC[i].imprimeClase();
            System.out.print("\n");
        }
    }

    public void medias(){

        conjuntoM=new MediaClase[numClases];
        for(int i=0; i<numClases; i++){

            conjuntoM[i]= new MediaClase();
            conjuntoM[i].calculoMedia(repClases, conjuntoC[i]);
            System.out.println("Media de la clase "+(i+1));
            conjuntoM[i].imprimirMedia();
            System.out.print("\n");
        }
    }

    public int distMin(){

        double minimo;
        int auxClase;
        double cuadradoX;
        double cuadradoY;
        
        double distClase[];
        distClase=new double[numClases];

        //Calculamos la raiz cuadrada de la suma de cuadrados, los cuadrados son la resta del vector respecto a la media de la clase
        for(int i=0; i<numClases; i++){

            cuadradoX=(coorx-conjuntoM[i].media[0][0])*(coorx-conjuntoM[i].media[0][0]);
            cuadradoY=(coory-conjuntoM[i].media[1][0])*(coory-conjuntoM[i].media[1][0]);
            distClase[i]=Math.sqrt(cuadradoX+cuadradoY);
            System.out.println("Distancia del vector respecto al centroide "+(i+1)+" ="+distClase[i]);
        }

        //Obtenemos la distancia mínima
        minimo=distClase[0];
        auxClase=1;
        for(int i=0; i<numClases; i++){

            if(minimo>distClase[i]){
            
                minimo=distClase[i];
                auxClase=i+1;
            }
        }

        return auxClase;
    }
}
