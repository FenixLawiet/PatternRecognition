/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica4.clasificadoreseficiencia;

import static java.lang.Math.pow;
import java.util.Scanner;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Menu de clasificadores y medidor de eficiencia
*/

public class MenuPrincipal {
    
    Scanner leer = new Scanner(System.in);
    
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
    
    //Para bayes
    double proba[];
    double exp[];
    double auxDet, det;
    
    GraficaClases g;
    
    //Para medir la eficiencia
    MetodosEficiencia obj=new MetodosEficiencia();
    GraficaEficiencia f;
    double precision[];
    double promedio=0;
    double auxProm=0;
    
    
    public MenuPrincipal(){
    
        //VACIO
    }
    
    public void ClasificadorMatrices(){
        
        char condicion;
        int selectorClasEf=0;
        int selectorMetoEf=0;
        int selectorMetoClas=0;
        
        
        System.out.println("************CLASIFICADORES BAYES-MAHALANOBIS-EUCLIDIANO************");
        System.out.println("Recordar que el umbral de pertenencia va de -100 a 100");
        //CAMBIAR EL MÉTODO PARA RECIBIR CENTROIDE Y DISPERSIÓN
        recibeDatosClases();
        crearClases();
        do{
            System.out.println("***** Ingresar un vector a clasificar --------- 1");
            System.out.println("***** Medir % de eficiencia del clasificador -- 2");
            selectorClasEf=leer.nextInt();
            if(selectorClasEf==1){
            
                recibeVector();
                if(coorx>100 || coory>100 || coorx<(-100)|| coory<(-100)){

                    System.out.println("El vector esta demasiado lejos del umbral, NO TIENE PERTENENCIA");
                }
                else{

                    System.out.println("***** Euclidiano --- 1");
                    System.out.println("***** Mahalanobis -- 2");
                    System.out.println("***** Bayes -------- 3");
                    System.out.println("Ingrese el método por el que desea clasificar: ");
                    selectorMetoEf = leer.nextInt();
                    switch(selectorMetoEf){

                        //MÉTODO EUCLIDIANO
                        case 1:

                            metodoEuclidiano();
                            break;

                        //MÉTODO MAHALANOBIS
                        case 2:

                            metodoMahalanobis();
                            break;
                        //MÉTODO DE BAYES
                        case 3:

                            metodoBayes();
                            break;
                        default:

                            System.out.println("ERROR. Opción no valida. Reingrese sus datos de nuevo");
                            break;    
                    }
                }
            }
            else if(selectorClasEf==2){
            
                System.out.println("***** Método de resititución ------- 1");
                System.out.println("***** Método de 50/50 -------------- 2");
                System.out.println("***** Método de Hold in one -------- 3");
                System.out.println("Ingrese el método que desea aplicar: ");
                selectorMetoEf = leer.nextInt();
                switch(selectorMetoEf){
                    
                    case 1:
                        System.out.println("***** Euclidiano --- 1");
                        System.out.println("***** Mahalanobis -- 2");
                        System.out.println("***** Bayes -------- 3");
                        System.out.println("Ingrese el método en donde desea aplicar: ");
                        selectorMetoClas = leer.nextInt();
                        switch(selectorMetoClas){
                        
                            case 1:
                                precision=new double[numClases];
                                precision=obj.matrizConfusion(conjuntoC, selectorMetoEf, selectorMetoClas);
                                for(int i=0; i<precision.length; i++){
                                    
                                    auxProm=auxProm+precision[i];
                                }
                                promedio=auxProm/((double)numClases);
                                System.out.println("La presición del clasificador Euclidiano por restitución es de: "+promedio+"%");
                                f=new GraficaEficiencia(precision, "Euclidiano",selectorMetoEf);
                                //Reiniciando el auxiliar
                                auxProm=0;
                                break;
                            
                            case 2:
                                precision=new double[numClases];
                                precision=obj.matrizConfusion(conjuntoC, selectorMetoEf, selectorMetoClas);
                                for(int i=0; i<precision.length; i++){
                                    
                                    auxProm=auxProm+precision[i];
                                }
                                promedio=auxProm/((double)numClases);
                                System.out.println("La presición del clasificador de Mahalanobis por restitución es de: "+promedio+"%");
                                f=new GraficaEficiencia(precision, "Mahalanobis",selectorMetoEf); 
                                auxProm=0;
                                break;
                            case 3:
                                precision=new double[numClases];
                                precision=obj.matrizConfusion(conjuntoC, selectorMetoEf, selectorMetoClas);
                                for(int i=0; i<precision.length; i++){
                                    
                                    auxProm=auxProm+precision[i];
                                }
                                promedio=auxProm/((double)numClases);
                                System.out.println("La presición del clasificador de Bayes por restitución es de: "+promedio+"%");
                                f=new GraficaEficiencia(precision, "Bayes",selectorMetoEf); 
                                auxProm=0;
                                break;
                            default:
                                System.out.println("ERROR. Opción de no válida. Ingrese nuevamente los datos.");    
                                break;
                        }
                        break;
                    
                    case 2:
                        System.out.println("***** Euclidiano --- 1");
                        System.out.println("***** Mahalanobis -- 2");
                        System.out.println("***** Bayes -------- 3");
                        System.out.println("Ingrese el método en donde desea aplicar: ");
                        selectorMetoClas = leer.nextInt();
                        switch(selectorMetoClas){
                        
                            case 1:
                                precision=new double[numClases];
                                precision=obj.matrizConfusion(conjuntoC, selectorMetoEf, selectorMetoClas);
                                for(int i=0; i<precision.length; i++){
                                    
                                    auxProm=auxProm+precision[i];
                                }
                                promedio=auxProm/((double)numClases);
                                System.out.println("La presición del clasificador Euclidiano por 50/50 es de: "+promedio+"%");
                                f=new GraficaEficiencia(precision, "Euclidiano",selectorMetoEf); 
                                auxProm=0;
                                break;
                            
                            case 2:
                                precision=new double[numClases];
                                precision=obj.matrizConfusion(conjuntoC, selectorMetoEf, selectorMetoClas);
                                for(int i=0; i<precision.length; i++){
                                    
                                    auxProm=auxProm+precision[i];
                                }
                                promedio=auxProm/((double)numClases);
                                System.out.println("La presición del clasificador de Mahalanobis por 50/50 es de: "+promedio+"%");
                                f=new GraficaEficiencia(precision, "Mahalanobis",selectorMetoEf); 
                                auxProm=0;
                                break;
                            case 3:
                                precision=new double[numClases];
                                precision=obj.matrizConfusion(conjuntoC, selectorMetoEf, selectorMetoClas);
                                for(int i=0; i<precision.length; i++){
                                    
                                    auxProm=auxProm+precision[i];
                                }
                                promedio=auxProm/((double)numClases);
                                System.out.println("La presición del clasificador de Bayes por 50/50 es de: "+promedio+"%");
                                f=new GraficaEficiencia(precision, "Bayes",selectorMetoEf); 
                                auxProm=0;
                                break;
                            default:
                                System.out.println("ERROR. Opción de no válida. Ingrese nuevamente los datos.");    
                                break;
                        }
                        break;
                    
                    case 3:
                        System.out.println("***** Euclidiano --- 1");
                        System.out.println("***** Mahalanobis -- 2");
                        System.out.println("***** Bayes -------- 3");
                        System.out.println("Ingrese el método en donde desea aplicar: ");
                        selectorMetoClas = leer.nextInt();
                        switch(selectorMetoClas){
                        
                            case 1:
                                precision=new double[numClases];
                                precision=obj.matrizConfusion(conjuntoC, selectorMetoEf, selectorMetoClas);
                                for(int i=0; i<precision.length; i++){
                                    
                                    auxProm=auxProm+precision[i];
                                }
                                promedio=auxProm/((double)numClases);
                                System.out.println("La presición del clasificador Euclidiano por hold in one es de: "+promedio+"%");
                                f=new GraficaEficiencia(precision, "Euclidiano",selectorMetoEf); 
                                auxProm=0;
                                break;
                            
                            case 2:
                                precision=new double[numClases];
                                precision=obj.matrizConfusion(conjuntoC, selectorMetoEf, selectorMetoClas);
                                for(int i=0; i<precision.length; i++){
                                    
                                    auxProm=auxProm+precision[i];
                                }
                                promedio=auxProm/((double)numClases);
                                System.out.println("La presición del clasificador de Mahalanobis por hold in one es de: "+promedio+"%");
                                f=new GraficaEficiencia(precision, "Mahalanobis",selectorMetoEf); 
                                auxProm=0;
                                break;
                            case 3:
                                precision=new double[numClases];
                                precision=obj.matrizConfusion(conjuntoC, selectorMetoEf, selectorMetoClas);
                                for(int i=0; i<precision.length; i++){
                                    
                                    auxProm=auxProm+precision[i];
                                }
                                promedio=auxProm/((double)numClases);
                                System.out.println("La presición del clasificador de Bayes por hold in one es de: "+promedio+"%");
                                f=new GraficaEficiencia(precision, "Bayes",selectorMetoEf); 
                                auxProm=0;
                                break;
                            default:
                                System.out.println("ERROR. Opción de no válida. Ingrese nuevamente los datos.");    
                                break;
                        }
                        break;
                    default:
                        System.out.println("ERROR. Opción de no válida. Ingrese nuevamente los datos.");    
                        break;
                }
            }
            else{
                
                System.out.println("ERROR. Opción de no válida. Ingrese nuevamente los datos.");
            }
            
            System.out.println("¿Desea probar con otra opción? Y/N: ");
            condicion=leer.next().charAt(0);
            
        }while(condicion=='Y' || condicion=='y');
    }
    
    //Definición de los métodos de clasificación
    public void metodoEuclidiano(){
    
        mostrarClases();
        medias();
        resClase=distMinEuc();
        System.out.println("El vector ("+coorx+","+coory+") pertenece a la clase "+resClase);
        //mandar a llamar al graficador y mandar de parametros las clases y el punto
        g=new GraficaClases(coorx, coory, conjuntoC);
    }
    
    public void metodoMahalanobis(){
    
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
        g=new GraficaClases(coorx, coory, conjuntoC);
    }
    
    public void metodoBayes(){
    
        /*
        Dado que para calcular el exponente de Mahalanobis se
        requiere que realizar nuevamente el cálculo de la varianza
        y la covarianza, el primer paso repite el método de Mahalanobis
        */
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
        //CALCULANDO EL EXPONENCIAL
        exp=new double[numClases];
        proba=new double[numClases];
        exp=expMahalanobis();
        //CALCULANDO LA PROBABILIDAD
        for(int i=0; i<numClases; i++){
                            
            auxDet=auxM.determinanteMatriz(varianza[i].getClase(), varianza[i].getClase().length);
            det=pow(auxDet, (1/2));//det=Math.sqrt(auxDet);
            //System.out.println("Existe det? "+det);
            proba[i]=calcProba(det, exp[i]); //Probabilidad de que (x,y) pertenece a la clase i
        }
        resClase=probaPert(proba);
        System.out.println("El vector ("+coorx+","+coory+") tiene más probabilidad de pertenecer a la clase "+resClase);        
        g=new GraficaClases(coorx, coory, conjuntoC);
    }
    //*******************************************************************************
    public void recibeDatosClases(){

        //Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el número de clases: ");
        numClases=leer.nextInt();
    }

    public void recibeVector(){

        //Scanner leer = new Scanner(System.in);
        //Umbral de tolerancia de menor a 100
        System.out.println("Ingrese coordenada x del vector a clasificar: ");
        coorx=leer.nextInt();
        System.out.println("Ingrese coordenada y del vector a clasificar: ");
        coory=leer.nextInt();
    }

    public void crearClases(){

        conjuntoC=new Clase[numClases];
        do{
            
            System.out.println("Ingrese la cantidad de representantes de la clase: ");
            repClases=leer.nextInt();
            if(repClases==0){
                
                System.out.println("ERROR. Dato no valido, ingrese nuevamente.");
            }
            else if(repClases>=1 && repClases<10){
                
                System.out.println("ERROR. Muestra insuficiente, ingrese un número mayor.");
            }
        }while(repClases==0 || (repClases>=1 && repClases<10));
        
        for(int i=0; i<numClases; i++){

            System.out.println("******CLASE "+(i+1)+"******");
            conjuntoC[i]= new Clase(i, repClases); 
        }
        //Reasignar aquí el número de representantes de cada clase
        //repClases=conjuntoC[0].getClase()[0].length;
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
    
    //*****************PARA BAYES*********************
    public double[] expMahalanobis(){
    
        double exp[]=new double[numClases];
        //**********************************REPITIENDO MAHALANOBIS***********************
    
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
        }    
        //*******************************************************************************
        for(int i=0; i<numClases; i++){
            
            exp[i]=(-0.5)*distClase[i];
            //System.out.println("Existe el coef? "+exp[i]);
        }
        return exp;
    }

    private double calcProba(double det, double coef) {
    
        double probabilidad=0;
        double exp, divisor;
        int dimension=2; //Dimensión del vector
        
        //Calculando primeramente el exponencial
        exp=pow(Math.E,coef); //Math.E = Número de Euler
        
        //Calculando el divisor
        divisor=(pow((2*Math.PI),(dimension/2)))*det;
        
        //Calculando la probabilidad
        probabilidad=(1/divisor)*exp;
        
        //System.out.println("Existe la proba? "+probabilidad);
        return probabilidad;
    }

    private int probaPert(double[] proba) {
    
        int probMax=0;
        double sumProb=0;
        int tam=proba.length;
        double probNorm[]=new double[tam];
        
        //CORROBORANDO LA MATRIZ
        /*for(int i=0; i<proba.length; i++){
        
            System.out.println(proba[i]);
        }*/
        
        for(int i=0; i<tam; i++){
        
            sumProb=sumProb+proba[i];
        }
        //System.out.println("Existe la suma? "+sumProb);
        for(int i=0; i<tam; i++){
        
            probNorm[i]=(proba[i]/sumProb)*100;
            System.out.println("La probablidad de que pertenezca a la clase "+(i+1)+" es de "+probNorm[i]+"%");
        }
        //Obtenemos la probabilidad máxima
        int maximo=(int)probNorm[0];
        int auxClase=1;
        for(int i=0; i<numClases; i++){

            if(maximo<probNorm[i]){
            
                maximo=(int)probNorm[i];
                auxClase=i+1;
            }
        }
        
        return auxClase;
    }
}