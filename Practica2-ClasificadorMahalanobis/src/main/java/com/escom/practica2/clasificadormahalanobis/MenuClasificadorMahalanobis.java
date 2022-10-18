/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica2.clasificadormahalanobis;

import java.util.Scanner;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Menu del Clasificador de Mahalanobis
*/

public class MenuClasificadorMahalanobis {
    
    int numClases;
    int repClases;
    int coorx;
    int coory; 
    
    Clase conjuntoC[];
    MediaClase conjuntoM[];
    
    double auxTrans[][];//=new int[repClases][2];
    OpMatrices auxM;
    Clase matrizResta[], varianza[], varianzaInv[];
    
    int resClase;
    
    Grafica g;
    
    
    public MenuClasificadorMahalanobis(){
    
        //VACIO
    }
    
    public void ClasificadorMahalanobis(){
        
        Scanner leer = new Scanner(System.in);
        char condicion;
        //int contador=0;
        int selector=0;
        
        System.out.println("************CLASIFICADOR MAHALANOBIS-EUCLIDIANO************");
        System.out.println("Recordar que el umbral de pertenencia va de -100 a 100");
        //CAMBIAR EL MÉTODO PARA RECIBIR CENTROIDE Y DISPERSIÓN
        recibeDatosClases();
        crearClases();
        do{
            recibeVector();
            if(coorx>100 || coory>100 || coorx<(-100)|| coory<(-100)){
            
                System.out.println("El vector esta demasiado lejos del umbral, NO TIENE PERTENENCIA");
            }
            else{
                
                System.out.println("***** Euclidiano -- 1");
                System.out.println("***** Mahalanobis -- 2");
                System.out.println("Ingrese el método por el que desea calcular la distancia minima: ");
                selector = leer.nextInt();
                switch(selector){
                    
                    case 1:
                        
                        mostrarClases();
                        medias();
                        resClase=distMinEuc();
                        System.out.println("El vector ("+coorx+","+coory+") pertenece a la clase "+resClase);
                        //mandar a llamar al graficador y mandar de parametros las clases y el punto
                        g=new Grafica(coorx, coory, conjuntoC);
                        break;
                    
                    case 2:
                        
                        varianza=new Clase[numClases];
                        varianzaInv=new Clase[numClases];
                        mostrarClases();
                        medias();
                        //Calculando la matriz resultante y su transpuesta
                        matrizResta=new Clase[numClases];
                        auxM=new OpMatrices();
                        for(int i=0; i<numClases; i++){
                        
                            matrizResta[i]=restaVecMed(i);
                            //System.out.println("Matriz resta "+(i+1));
                            //matrizResta[i].imprimeClase();
                            //Calculamos la matriz de varianza-covarianza
                            calculoVarianza(i);
                            calculoCovarianza(i);
                        }      
                        //Calculamos la distancia de Mahalanobis
                        resClase=distMinMH();
                        System.out.println("El vector ("+coorx+","+coory+") pertenece a la clase "+resClase);        
                        g=new Grafica(coorx, coory, conjuntoC);
                        break;
                        
                    default:
                            
                        System.out.println("ERROR. Opción no valida. Reingrese sus datos de nuevo");
                        break;    
                }
            }
            System.out.println("¿Desea probar con otro vector? Y/N: ");
            condicion=leer.next().charAt(0);
            
        }while(condicion=='Y' || condicion=='y');
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

    private int distMinEuc(){

        double minimo;
        int auxClase;
        double cuadradoX;
        double cuadradoY;
        
        double distClase[];
        distClase=new double[numClases];

        //Calculamos la raiz cuadrada de la suma de cuadrados, los cuadrados son la resta del vector respecto a la media de la clase
        for(int i=0; i<numClases; i++){

            cuadradoX=(coorx-conjuntoM[i].getMedia()[0][0])*(coorx-conjuntoM[i].getMedia()[0][0]);
            cuadradoY=(coory-conjuntoM[i].getMedia()[1][0])*(coory-conjuntoM[i].getMedia()[1][0]);
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

    //*****************PARA MAHALANOBIS*********************
    
    private Clase restaVecMed(int numClase) {
        
        Clase aux=new Clase();
        double restaXY[][];
        restaXY=new double[2][repClases];
        for(int j=0; j<repClases; j++){
                
            restaXY[0][j]=(conjuntoC[numClase].getClase()[0][j]-conjuntoM[numClase].getMedia()[0][0]);
            restaXY[1][j]=(conjuntoC[numClase].getClase()[1][j]-conjuntoM[numClase].getMedia()[1][0]);
        }
        aux.setClase(restaXY);
        return aux; 
    }

    private void calculoVarianza(int numClase) {
        
        auxTrans=auxM.transpuesta(matrizResta[numClase]);
        /* System.out.println("Imprimiendo transpuesta clase " +(numClase+1));
        int row = auxTrans.length;
        int col = auxTrans[0].length;
        for(int i=0; i<row; i++){
        
            for(int j=0; j<col; j++){
            
                System.out.print(auxTrans[i][j]+" ");
            }
            System.out.print("\n");
        } */
        varianza[numClase]=auxM.multiMatriz(matrizResta[numClase], auxTrans);
        System.out.println("VARIANZA "+(numClase+1));
        varianza[numClase].imprimeClase();
        System.out.print("\n");
    }
        
    private void calculoCovarianza(int numClase) {
    
        varianzaInv[numClase]=auxM.inversa(varianza[numClase]);
        System.out.println("COVARIANZA "+(numClase+1));
        varianzaInv[numClase].imprimeClase();
        System.out.print("\n");
    }
    
    private int distMinMH() {
        
        double minimo;
        int auxClase;
        
        double distClase[];
        distClase=new double[numClases];

        //Calculando las distancias de clase
        Clase auxiliar1=new Clase();
        Clase auxiliar2=new Clase();
        Clase auxiliar3=new Clase();
        double vectorRes[][]=new double[1][2];
        double vectorResTrans[][]=new double[2][1];
        double restaX;
        double restaY;
        
        for(int i=0; i<numClases; i++){
        
            restaX=coorx-conjuntoM[i].getMedia()[0][0];
            restaY=coory-conjuntoM[i].getMedia()[1][0];

            //Creando manualmente los vectores
            vectorRes[0][0]=restaX;
            vectorRes[0][1]=restaY;
            vectorResTrans[0][0]=restaX;
            vectorResTrans[1][0]=restaY;
            auxiliar1.setClase(vectorRes);
            auxiliar2=auxM.multiMatriz(varianzaInv[i], vectorResTrans);
            
            auxiliar3=auxM.multiMatriz(auxiliar1, auxiliar2.getClase());
            
            distClase[i]=auxiliar3.getClase()[0][0];
            if(distClase[i]<0){
            
                distClase[i]=distClase[i]*(-1);
            }
            System.out.println("Distancia de Mahalanobis respecto a la clase "+(i+1)+" ="+distClase[i]);
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
