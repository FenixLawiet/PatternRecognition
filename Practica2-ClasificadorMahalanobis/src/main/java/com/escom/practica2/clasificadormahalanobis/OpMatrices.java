/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica2.clasificadormahalanobis;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Operaciones básicas con matrices 
*/

public class OpMatrices {

    double calculoRes[][];
    
    public OpMatrices(){}
    
    double[][] transpuesta(Clase clase) {
        
        double trans[][];
        //Se necesita saber la cantidad de filas y columnas del arreglo
        int fila=clase.getClase().length;
        int columna=clase.getClase()[0].length;
        
        //Declarando el arreglo para transpuesta
        trans=new double[columna][fila];
        //System.out.println("Genera la matriz de la transpuesta.");
        for(int i=0; i<columna; i++){
        
            //System.out.println("Entro al primer for "+i);
            for(int j=0; j<fila; j++){
            
                //System.out.println("Entro al segundo for "+j);
                trans[i][j]=clase.getClase()[j][i];
            }
        }
        
        return trans;
    }

    Clase multiMatriz(Clase clase, double[][] auxTrans) {
        
        /*Para realizar la multiplicación correctamente, las matrices deben cumplir
          las siguientes condiciones; [nxm][mxn]=[mxm] donde m y n son el tamaño de
          la fila y columna
        */
        Clase mResultante=new Clase();
        //Definiendo la matriz resultando
        int fila=clase.getClase().length;
        int columna=auxTrans[0].length;
        calculoRes=new double[fila][columna];
        
        //System.out.println("Fila "+fila+"...Columna "+columna);
        //System.out.println("Multiplicando matrices...");
        
        //Haciendo la multiplicación finalmente
        for (int i = 0; i < fila; i++) {
            
            for (int j = 0; j < columna; j++) {
                
                for (int k = 0; k < fila; k++) {
                    // aquí se multiplica la matriz
                    calculoRes[i][j] += clase.getClase()[i][k] * auxTrans[k][j];
                }
            }
        }
        
        mResultante.setClase(calculoRes);
        return mResultante;
    }

    Clase inversa(Clase clase) {
        
        Clase invertida=new Clase();
        
        int n = clase.getClase().length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i){
            
            b[i][i]=1;
        }
        
        //Transformando la matriz en un tríangulo superior
        gaussiana(clase.getClase(), index);
        
        // Actualiza la matriz b[i][j] con las razones almacenadas
        for (int i=0; i<n-1; ++i){
        
            for (int j=i+1; j<n; ++j){
            
                for (int k=0; k<n; ++k){
                
                    b[index[j]][k]-= clase.getClase()[index[j]][i]*b[index[i]][k];
                }
            }
        }
                
        // Realiza sustituciones hacia atrás
            for (int i=0; i<n; ++i){
                x[n-1][i] = b[index[n-1]][i]/clase.getClase()[index[n-1]][n-1];
                for (int j=n-2; j>=0; --j){
                    x[j][i] = b[index[j]][i];
                    for (int k=j+1; k<n; ++k)
                    {
                        x[j][i] -= clase.getClase()[index[j]][k]*x[k][i];
                    }

                    x[j][i] /= clase.getClase()[index[j]][j];
                }
            }
        
        invertida.setClase(x);
        return invertida;
    }
    
    
    // Method to carry out the partial-pivoting Gaussian
    // elimination.  Here index[] stores pivoting order.

        public static void gaussiana(double a[][], int index[])  {

            int n = index.length;
            double c[] = new double[n];

     // Initialize the index
            for (int i=0; i<n; ++i)
                index[i] = i;

     // Find the rescaling factors, one from each row
            for (int i=0; i<n; ++i) {
                double c1 = 0;
                for (int j=0; j<n; ++j) {
                    double c0 = Math.abs(a[i][j]);
                    if (c0 > c1) c1 = c0;
                }
                c[i] = c1;
            }

     // Search the pivoting element from each column
            int k = 0;
            for (int j=0; j<n-1; ++j) {
                double pi1 = 0;
                for (int i=j; i<n; ++i)  {

                    double pi0 = Math.abs(a[index[i]][j]);
                    pi0 /= c[index[i]];
                    if (pi0 > pi1) {
                        pi1 = pi0;
                        k = i;
                    }
                }

     
       // Interchange rows according to the pivoting order
                int itmp = index[j];
                index[j] = index[k];
                index[k] = itmp;
                for (int i=j+1; i<n; ++i) {
                    double pj = a[index[i]][j]/a[index[j]][j];

     // Record pivoting ratios below the diagonal
                    a[index[i]][j] = pj;

     // Modify other elements accordingly
                    for (int l=j+1; l<n; ++l)
                        a[index[i]][l] -= pj*a[index[j]][l];
                }
            }
        }
}
