/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica4.clasificadoreseficiencia;

import static java.lang.Math.pow;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Métodos de eficiencia de los clasificadores
*/
public class MetodosEficiencia {

    //Para poder reutilizar los métodos de clasificación
    int numClases;
    int repClases;
    double coorx;
    double coory; 
    
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
    
    //Para crear la matriz de confusión
    int matrizConf[][];
    int totalClase[];

    public double[] matrizConfusion(Clase[] conjuntoC, int selectorMetoEf, int selectorMetoClas) {
        
        this.conjuntoC=conjuntoC;
        this.repClases=conjuntoC[0].getRepClases();
        numClases=conjuntoC.length;
        
        totalClase=new int[numClases];
        
        matrizConf=new int[numClases][numClases];
        
        int auxMitad;
        //Llenando primeramente a matriz de confusión con ceros y la suma total
        for(int i=0; i<numClases; i++){
        
            totalClase[i]=0;
            for(int j=0; j<numClases; j++){
            
                matrizConf[i][j]=0;
            }
        }
        /*
        Para definir las clases de entrenamiento y de prueba, se usa como entrenamiento la clase
        original que se genera automaticamente.
        Por otra parte, para los puntos de prueba, se crea una clase de prueba con mismo centroide y
        misma dispersión a la que se quiere comparar.
        */
        Clase test[]=new Clase[numClases]; //double test[][];
        //Construyendo las clases de prueba
        for(int i=0; i<numClases; i++){
       
            test[i]=new Clase(conjuntoC[i].getCoorx(), conjuntoC[i].getCoory(), conjuntoC[i].getDisp(), repClases);
            System.out.println("****TEST C"+(i+1)+"****");
            test[i].imprimeClase();
            System.out.print("\n");
        }
        //Clases de entrenamiento: conjuntoC
        
        
        switch(selectorMetoEf){
        
            case 1:
                //Metodo de restitución ---- Se toma la clase completa       
                for(int i=0; i<numClases; i++){
                
                    //***Clasificando los elementos de prueba
                    for(int j=0; j<repClases; j++){
                    
                        coorx=test[i].getClase()[0][j];
                        coory=test[i].getClase()[1][j];
                        if(selectorMetoClas==1){

                            //Se clasifica por euclidiano
                            medias();
                            resClase=distMinEuc();
                            //COMPROBACIÓN
                            if(resClase==i+1){

                                matrizConf[i][i]=matrizConf[i][i]+1;
                            }
                            else{
                                
                                matrizConf[i][resClase-1]=matrizConf[i][resClase-1]+1;
                            }
                        }
                        else if(selectorMetoClas==2){

                            //Se clasifica por mahalanobis
                            varianza=new Clase[numClases];
                            varianzaInv=new Clase[numClases];
                            medias();
                            //Calculando la matriz resultante y su transpuesta
                            matrizResta=new Clase[numClases];
                            auxM=new OpMatrices();
                            for(int k=0; k<numClases; k++){

                                matrizResta[k]=restaVecMed(k);
                                //System.out.println("Matriz resta "+(i+1));
                                //matrizResta[i].imprimeClase();
                                //Calculamos la matriz de varianza-covarianza
                                calculoVarianza(k);
                                calculoCovarianza(k);
                            }      
                            //Calculamos la distancia de Mahalanobis
                            resClase=distMinMH();
                            //COMPROBACIÓN
                            if(resClase==i+1){

                                matrizConf[i][i]=matrizConf[i][i]+1;
                            }
                            else{
                                
                                matrizConf[i][resClase-1]=matrizConf[i][resClase-1]+1;
                            }
                        }
                        else if(selectorMetoClas==3){

                            //Se clasifica por bayes                            
                            varianza=new Clase[numClases];
                            varianzaInv=new Clase[numClases];
                            medias();
                            //Calculando la matriz resultante y su transpuesta
                            matrizResta=new Clase[numClases];
                            auxM=new OpMatrices();
                            for(int k=0; k<numClases; k++){

                                matrizResta[k]=restaVecMed(k);
                                //System.out.println("Matriz resta "+(i+1));
                                //matrizResta[i].imprimeClase();
                                //Calculamos la matriz de varianza-covarianza
                                calculoVarianza(k);
                                calculoCovarianza(k);
                            }
                            //CALCULANDO EL EXPONENCIAL
                            exp=new double[numClases];
                            proba=new double[numClases];
                            exp=expMahalanobis();
                            //CALCULANDO LA PROBABILIDAD
                            for(int k=0; k<numClases; k++){

                                auxDet=auxM.determinanteMatriz(varianza[k].getClase(), varianza[k].getClase().length);
                                det=pow(auxDet, (1/2));//det=Math.sqrt(auxDet);
                                //System.out.println("Existe det? "+det);
                                proba[k]=calcProba(det, exp[k]); //Probabilidad de que (x,y) pertenece a la clase i
                            }
                            resClase=probaPert(proba);
        
                            //COMPROBACIÓN
                            if(resClase==i+1){

                                matrizConf[i][i]=matrizConf[i][i]+1;
                            }
                            else{
                                
                                matrizConf[i][resClase-1]=matrizConf[i][resClase-1]+1;
                            }
                        }
                    }
                }
                break;
            
            case 2:
                //Método de 50/50
                //Comprobando que la cantidad de representantes sea un número par
                if(repClases%2==0){
                
                    auxMitad=repClases/2; //test=new double[2][repClases/2];
                }
                else{
                    
                    auxMitad=(int)((repClases/2)+0.5);
                }
                for(int i=0; i<numClases; i++){
                
                    //***Clasificando los elementos de prueba
                    for(int j=0; j<auxMitad; j++){
                    
                        coorx=test[i].getClase()[0][j];
                        coory=test[i].getClase()[1][j];
                        if(selectorMetoClas==1){

                            //Se clasifica por euclidiano
                            medias();
                            resClase=distMinEuc();
                            //COMPROBACIÓN
                            if(resClase==i+1){

                                matrizConf[i][i]=matrizConf[i][i]+1;
                            }
                            else{
                                
                                matrizConf[i][resClase-1]=matrizConf[i][resClase-1]+1;
                            }
                        }
                        else if(selectorMetoClas==2){

                            //Se clasifica por mahalanobis
                            varianza=new Clase[numClases];
                            varianzaInv=new Clase[numClases];
                            medias();
                            //Calculando la matriz resultante y su transpuesta
                            matrizResta=new Clase[numClases];
                            auxM=new OpMatrices();
                            for(int k=0; k<numClases; k++){

                                matrizResta[k]=restaVecMed(k);
                                //System.out.println("Matriz resta "+(i+1));
                                //matrizResta[i].imprimeClase();
                                //Calculamos la matriz de varianza-covarianza
                                calculoVarianza(k);
                                calculoCovarianza(k);
                            }      
                            //Calculamos la distancia de Mahalanobis
                            resClase=distMinMH();
                            //COMPROBACIÓN
                            if(resClase==i+1){

                                matrizConf[i][i]=matrizConf[i][i]+1;
                            }
                            else{
                                
                                matrizConf[i][resClase-1]=matrizConf[i][resClase-1]+1;
                            }
                        }
                        else if(selectorMetoClas==3){

                            //Se clasifica por bayes                            
                            varianza=new Clase[numClases];
                            varianzaInv=new Clase[numClases];
                            medias();
                            //Calculando la matriz resultante y su transpuesta
                            matrizResta=new Clase[numClases];
                            auxM=new OpMatrices();
                            for(int k=0; k<numClases; k++){

                                matrizResta[k]=restaVecMed(k);
                                //System.out.println("Matriz resta "+(i+1));
                                //matrizResta[i].imprimeClase();
                                //Calculamos la matriz de varianza-covarianza
                                calculoVarianza(k);
                                calculoCovarianza(k);
                            }
                            //CALCULANDO EL EXPONENCIAL
                            exp=new double[numClases];
                            proba=new double[numClases];
                            exp=expMahalanobis();
                            //CALCULANDO LA PROBABILIDAD
                            for(int k=0; k<numClases; k++){

                                auxDet=auxM.determinanteMatriz(varianza[k].getClase(), varianza[k].getClase().length);
                                det=pow(auxDet, (1/2));//det=Math.sqrt(auxDet);
                                //System.out.println("Existe det? "+det);
                                proba[k]=calcProba(det, exp[k]); //Probabilidad de que (x,y) pertenece a la clase i
                            }
                            resClase=probaPert(proba);
        
                            //COMPROBACIÓN
                            if(resClase==i+1){

                                matrizConf[i][i]=matrizConf[i][i]+1;
                            }
                            else{
                                
                                matrizConf[i][resClase-1]=matrizConf[i][resClase-1]+1;
                            }
                        }
                    }
                }
                
                break;
                
            case 3:
                //Metodo de hold in one --- solo tomamos un representante
                int coorRandom;
                for(int i=0; i<numClases; i++){
                
                    //Tomando solo una coordenada ALEATORIA por clase al clasificar
                    coorRandom=(int)((Math.random()*(repClases-0+1)+0)); //Num random entre 0 y el total de representantes
                    
                    if(coorRandom>repClases){
                    
                        coorRandom=coorRandom-1;
                    }
                    
                    coorx=test[i].getClase()[0][coorRandom];
                    coory=test[i].getClase()[1][coorRandom];
                    if(selectorMetoClas==1){

                        //Se clasifica por euclidiano
                        medias();
                        resClase=distMinEuc();
                        //COMPROBACIÓN
                        if(resClase==i+1){

                            matrizConf[i][i]=matrizConf[i][i]+1;
                        }
                        else{
                                
                            matrizConf[i][resClase-1]=matrizConf[i][resClase-1]+1;
                        }
                    }
                    else if(selectorMetoClas==2){

                        //Se clasifica por mahalanobis
                        varianza=new Clase[numClases];
                        varianzaInv=new Clase[numClases];
                        medias();
                        //Calculando la matriz resultante y su transpuesta
                        matrizResta=new Clase[numClases];
                        auxM=new OpMatrices();
                        for(int k=0; k<numClases; k++){

                            matrizResta[k]=restaVecMed(k);
                            //System.out.println("Matriz resta "+(i+1));
                            //matrizResta[i].imprimeClase();
                            //Calculamos la matriz de varianza-covarianza
                            calculoVarianza(k);
                            calculoCovarianza(k);
                        }      
                        //Calculamos la distancia de Mahalanobis
                        resClase=distMinMH();
                        //COMPROBACIÓN
                        if(resClase==i+1){

                            matrizConf[i][i]=matrizConf[i][i]+1;
                        }
                        else{
                                
                            matrizConf[i][resClase-1]=matrizConf[i][resClase-1]+1;
                        }
                    }
                    else if(selectorMetoClas==3){

                        //Se clasifica por bayes                            
                        varianza=new Clase[numClases];
                        varianzaInv=new Clase[numClases];
                        medias();
                        //Calculando la matriz resultante y su transpuesta
                        matrizResta=new Clase[numClases];
                        auxM=new OpMatrices();
                        for(int k=0; k<numClases; k++){

                            matrizResta[k]=restaVecMed(k);
                            //System.out.println("Matriz resta "+(i+1));
                            //matrizResta[i].imprimeClase();
                            //Calculamos la matriz de varianza-covarianza
                            calculoVarianza(k);
                            calculoCovarianza(k);
                        }
                        //CALCULANDO EL EXPONENCIAL
                        exp=new double[numClases];
                        proba=new double[numClases];
                        exp=expMahalanobis();
                        //CALCULANDO LA PROBABILIDAD
                        for(int k=0; k<numClases; k++){

                            auxDet=auxM.determinanteMatriz(varianza[k].getClase(), varianza[k].getClase().length);
                            det=pow(auxDet, (1/2));//det=Math.sqrt(auxDet);
                            //System.out.println("Existe det? "+det);
                            proba[k]=calcProba(det, exp[k]); //Probabilidad de que (x,y) pertenece a la clase i
                        }
                        resClase=probaPert(proba);
        
                        //COMPROBACIÓN
                        if(resClase==i+1){

                            matrizConf[i][i]=matrizConf[i][i]+1;
                        }
                        else{
                                
                            matrizConf[i][resClase-1]=matrizConf[i][resClase-1]+1;
                        }
                    }
                }
                break;
        }
        System.out.println("***********MATRIZ DE CONFUSIÓN************");
        imprimirMatriz();
        System.out.print("\n");
        /*Para hacer el cálculo correcto de la presición, se deben comparar los elementos 
          clasificados contra el total de representantes de clase*/
        double precision[]=new double[numClases];
        double aux;
        for(int i=0; i<numClases; i++){
        
            for(int j=0; j<numClases; j++){
            
                if(i==j){
                    
                    aux=((double)matrizConf[i][j])/((double)totalClase[i]);//**********
                    precision[i]=(aux*100);
                    System.out.println("La precisión de en la clase "+(i+1)+" fue de: "+precision[i]+"%");
                }
            }
        }
        return precision;
    }
    
    //Para imprimir la matriz de confusión
    public void imprimirMatriz(){
            
        System.out.print("------");
        for(int i=0; i<matrizConf.length; i++){
        
            System.out.print("| C"+(i+1)+" ");
        }
        System.out.print("\n");
        for(int i=0; i<matrizConf.length; i++){
        
            System.out.print("| C"+(i+1)+"| ");
            for(int j=0; j<matrizConf[0].length; j++){
            
                System.out.print(" "+matrizConf[i][j]+" ");
                totalClase[i]=totalClase[i]+matrizConf[i][j];
            }
            System.out.print("Suma="+totalClase[i]);
            System.out.print("\n");
        }
    }
    
    //*************METODOS AUXILIARES PARA CLASIFICAR************************************************
    public void medias(){

        conjuntoM=new MediaClase[numClases];
        for(int i=0; i<numClases; i++){

            conjuntoM[i]= new MediaClase();
            conjuntoM[i].calculoMedia(repClases, conjuntoC[i]);
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
            //System.out.println("Distancia del vector respecto al centroide "+(i+1)+" ="+distClase[i]);
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
    }
        
    private void calculoCovarianza(int numClase) {
    
        varianzaInv[numClase]=auxM.inversa(varianza[numClase]);
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
            //System.out.println("Distancia de Mahalanobis respecto a la clase "+(i+1)+" ="+distClase[i]);
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
            //System.out.println("La probablidad de que pertenezca a la clase "+(i+1)+" es de "+probNorm[i]+"%");
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
