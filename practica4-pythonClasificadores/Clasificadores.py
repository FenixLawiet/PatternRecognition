"""
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Métodos de clasificación
"""

import numpy as np
from mpl_toolkits.mplot3d import axes3d
import matplotlib.pyplot as plt
from math import *
import random as r
import Descriptor
import MediaDescriptor

class Clasificadores:

    def __init__(self):
        self.maxClase = 1
        self.minClase = 1
    
    #Método que grafica las clases y el punto a encontrar
    def graficaClases(self, conjuntoClases, coorx, coory, coorz):
        # Creamos la figura
        fig = plt.figure()

        # Creamos el plano 3D
        ax1 = fig.add_subplot(111, projection='3d')

        #Creando un arreglo de colores
        colors = []
        for i in range(len(conjuntoClases)+1):
            colors.append('#%06X' % r.randint(0, 0xFFFFFF))

        #Agregando los puntos en el plano
        ax1.scatter(coorx, coory, coorz, c=colors[0], marker='o')
        leyendas = []
        leyendas.append("Vector") 
        for i in range(len(conjuntoClases)):
            #Recibe en formato x, y, z
            if conjuntoClases[i].dimension == 1:
                ax1.scatter(conjuntoClases[i].vector[0], 0, 0, c=colors[i+1], marker='o')
            if conjuntoClases[i].dimension == 2:
                ax1.scatter(conjuntoClases[i].vector[0], conjuntoClases[i].vector[1], 0, c=colors[i+1], marker='o')
            if conjuntoClases[i].dimension == 3:        
                ax1.scatter(conjuntoClases[i].vector[0], conjuntoClases[i].vector[1], conjuntoClases[i].vector[2], c=colors[i+1], marker='o') 
            
            leyendas.append(f"Clase {i+1}") 
        ax1.legend(leyendas)
        plt.show() 
    
    #Método de clasificación por distancia Euclidiana
    def metodoEuclides(self, conjuntoClases, coorx, coory, coorz):
        
        conjuntoMedias = []
        distClase = []
        
        #Imprime las clases del conjunto
        for i in range(len(conjuntoClases)):
            print("***********Clase ", (i+1),"***********")
            conjuntoClases[i].imprimeDescriptor()
            print("\n")

        auxMedia = MediaDescriptor.MediaDescriptor(conjuntoClases[0].dimension) 

        #Calcula e imprime las medias por cada clases
        for i in range(len(conjuntoClases)):
            auxMedia.calculoMedia(conjuntoClases[0].repClases, conjuntoClases[i])
            print("***********Media de clase ", (i+1),"***********")
            auxMedia.imprimirMedia()
            conjuntoMedias.append(auxMedia.media)
            print("\n")

        #Calculando la distancia de las coordenadas a cada centroide de las clases
        for i in range(len(conjuntoClases)):
            #print(conjuntoMedias[i])
            if conjuntoClases[0].dimension == 1:
                cuadradoX=(coorx-conjuntoMedias[i])**2
                distClase.append(sqrt(cuadradoX))
            if conjuntoClases[0].dimension == 2:
                cuadradoX=(coorx-conjuntoMedias[i][0])**2
                cuadradoY=(coory-conjuntoMedias[i][1])**2
                distClase.append(sqrt(cuadradoX+cuadradoY))
            if conjuntoClases[0].dimension == 3:
                cuadradoX=(coorx-conjuntoMedias[i][0])**2
                cuadradoY=(coory-conjuntoMedias[i][1])**2
                cuadradoZ=(coorz-conjuntoMedias[i][2])**2
                distClase.append(sqrt(cuadradoX+cuadradoY+cuadradoZ))
            #print("Distancia del vector respecto al centroide "+str((i+1))+" ="+str(distClase[i]))
            print(f"Distancia del vector respecto al centroide {i+1} = {distClase[i]}")  

        #Obteniendo la distancia mínima
        minimo = distClase[0]
        for i in range(len(distClase)):
            if minimo > distClase[i]:
                minimo = distClase[i]
                self.minClase = i+1

        print(f"El vector ({coorx}, {coory}, {coorz}) pertenece a la clase {self.minClase}")


    #Método de clasificación por distancia de Mahalanobis
    def metodoMahalanobis(self, conjuntoClases, coorx, coory, coorz):
        conjuntoMedias = []
        distClase = []
        
        #Imprime las clases del conjunto
        for i in range(len(conjuntoClases)):
            print("***********Clase ", (i+1),"***********")
            conjuntoClases[i].imprimeDescriptor()
            print("\n")

        auxMedia = MediaDescriptor.MediaDescriptor(conjuntoClases[0].dimension) 

        #Calcula e imprime las medias por cada clases
        for i in range(len(conjuntoClases)):
            auxMedia.calculoMedia(conjuntoClases[0].repClases, conjuntoClases[i])
            print("***********Media de clase ", (i+1),"***********")
            auxMedia.imprimirMedia()
            conjuntoMedias.append(auxMedia.media)
            print("\n")

        #Calculando las varianzas y covarianzas de las clases
        varianzas = []
        covarianzas = []
        arregloRestas = []
        arregloTranspuestas = []
        restaClase = conjuntoClases[0]

        for i in range(len(conjuntoClases)):
            for j in range(conjuntoClases[0].repClases):
                if conjuntoClases[0].dimension == 1:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i]
                    continue
                if conjuntoClases[0].dimension == 2:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i][0][0]
                    restaClase.vector[1][j] = conjuntoClases[i].vector[1][j] - conjuntoMedias[i][1][0]
                    continue
                if conjuntoClases[0].dimension == 3:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i][0][0]
                    restaClase.vector[1][j] = conjuntoClases[i].vector[1][j] - conjuntoMedias[i][1][0]
                    restaClase.vector[2][j] = conjuntoClases[i].vector[2][j] - conjuntoMedias[i][2][0] 
            arregloRestas.append(restaClase.vector)
            arregloTranspuestas.append(np.transpose(restaClase.vector))

        for i in range(len(conjuntoClases)):
            auxVarianza = np.matmul(arregloRestas[i], arregloTranspuestas[i])
            varianzas.append(auxVarianza)
            print(f"****VARIANZA {i+1}****")
            print(varianzas[i])
            print("\n")

        for i in range(len(conjuntoClases)):
            auxCovarianza = np.linalg.inv(varianzas[i])
            covarianzas.append(auxCovarianza)
            print(f"****COVARIANZA {i+1}****")
            print(covarianzas[i]) 
            print("\n")

        #Calculando la distancia de mahalanobis 
        auxMult = np.zeros((1, 1))
        auxVector = np.zeros((conjuntoClases[0].dimension, 1))
        auxVectorTrans = np.zeros((1, conjuntoClases[0].dimension))

        for i in range(len(conjuntoClases)):
            if conjuntoClases[0].dimension == 1:
                auxVector[0][0] = coorx - conjuntoMedias[i]
            if conjuntoClases[0].dimension == 2:
                auxVector[0][0] = coorx - conjuntoMedias[i][0][0]
                auxVector[1][0] = coory - conjuntoMedias[i][1][0]
            if conjuntoClases[0].dimension == 3:     
                auxVector[0][0] = coorx - conjuntoMedias[i][0][0]
                auxVector[1][0] = coory - conjuntoMedias[i][1][0]
                auxVector[2][0] = coorz - conjuntoMedias[i][2][0]
            
            auxVectorTrans = np.transpose(auxVector)
            auxMult = np.matmul(auxVectorTrans, covarianzas[i]) 
            auxDistancia = np.matmul(auxMult, auxVector)
            distClase.append(auxDistancia)       
            print(f"Distancia del vector respecto al centroide {i+1} = {distClase[i]}")

        #Obteniendo la distancia mínima
        minimo = distClase[0]
        for i in range(len(distClase)):
            if minimo > distClase[i]:
                minimo = distClase[i]
                self.minClase = i+1

        print(f"El vector ({coorx}, {coory}, {coorz}) pertenece a la clase {self.minClase}")    

    #Método de clasificación por teorema de bayes
    def metodoBayes(self, conjuntoClases, coorx, coory, coorz):
        """
        Dado que para calcular el exponente de Mahalanobis se
        requiere que realizar nuevamente el cálculo de la varianza
        y la covarianza, el primer paso repite el método de Mahalanobis
        """
        conjuntoMedias = []
        
        #Imprime las clases del conjunto
        for i in range(len(conjuntoClases)):
            print("***********Clase ", (i+1),"***********")
            conjuntoClases[i].imprimeDescriptor()
            print("\n")

        auxMedia = MediaDescriptor.MediaDescriptor(conjuntoClases[0].dimension) 

        #Calcula e imprime las medias por cada clases
        for i in range(len(conjuntoClases)):
            auxMedia.calculoMedia(conjuntoClases[0].repClases, conjuntoClases[i])
            print("***********Media de clase ", (i+1),"***********")
            auxMedia.imprimirMedia()
            conjuntoMedias.append(auxMedia.media)
            print("\n")

        #Calculando las varianzas y covarianzas de las clases
        varianzas = []
        covarianzas = []
        arregloRestas = []
        arregloTranspuestas = []
        restaClase = conjuntoClases[0]

        for i in range(len(conjuntoClases)):
            for j in range(conjuntoClases[0].repClases):
                if conjuntoClases[0].dimension == 1:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i]
                    continue
                if conjuntoClases[0].dimension == 2:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i][0][0]
                    restaClase.vector[1][j] = conjuntoClases[i].vector[1][j] - conjuntoMedias[i][1][0]
                    continue
                if conjuntoClases[0].dimension == 3:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i][0][0]
                    restaClase.vector[1][j] = conjuntoClases[i].vector[1][j] - conjuntoMedias[i][1][0]
                    restaClase.vector[2][j] = conjuntoClases[i].vector[2][j] - conjuntoMedias[i][2][0] 
            arregloRestas.append(restaClase.vector)
            arregloTranspuestas.append(np.transpose(restaClase.vector))

        for i in range(len(conjuntoClases)):
            auxVarianza = np.matmul(arregloRestas[i], arregloTranspuestas[i])
            varianzas.append(auxVarianza)
            print(f"****VARIANZA {i+1}****")
            print(varianzas[i])
            print("\n")

        for i in range(len(conjuntoClases)):
            auxCovarianza = np.linalg.inv(varianzas[i])
            covarianzas.append(auxCovarianza)
            print(f"****COVARIANZA {i+1}****")
            print(covarianzas[i]) 
            print("\n")

        #Calculando el exponencial de mahalanobis
        auxMult = np.zeros((1, 1))
        auxVector = np.zeros((conjuntoClases[0].dimension, 1))
        auxVectorTrans = np.zeros((1, conjuntoClases[0].dimension))
        expMaha = []

        for i in range(len(conjuntoClases)):
            if conjuntoClases[0].dimension == 1:
                auxVector[0][0] = coorx - conjuntoMedias[i]
            if conjuntoClases[0].dimension == 2:
                auxVector[0][0] = coorx - conjuntoMedias[i][0][0]
                auxVector[1][0] = coory - conjuntoMedias[i][1][0]
            if conjuntoClases[0].dimension == 3:     
                auxVector[0][0] = coorx - conjuntoMedias[i][0][0]
                auxVector[1][0] = coory - conjuntoMedias[i][1][0]
                auxVector[2][0] = coorz - conjuntoMedias[i][2][0]
            
            auxVectorTrans = np.transpose(auxVector)
            auxMult = np.matmul(auxVectorTrans, covarianzas[i]) 
            auxDistancia = (-0.5)*(np.matmul(auxMult, auxVector))
            expMaha.append(auxDistancia)       

        numerador = []
        denominador = []
        #Elevando constante e al exponente de mahalnobis
        for i in range(len(conjuntoClases)):
            numerador.append(exp(expMaha[i])) 

        #Calculando el denominador       
        auxDen1 = 0
        auxDen2 = 0
        for i in range((len(conjuntoClases))):
            auxDen1 = pow((2*np.pi), ((conjuntoClases[0].dimension)/2))
            auxDen2 = pow(np.linalg.det(varianzas[i]), 0.5)
            denominador.append(auxDen1*auxDen2)

        #Calculando las probabilidades
        probabilidades = []
        auxProbabilidades = []
        sumProba = 0
        for i in range(len(conjuntoClases)):
            auxProbabilidades.append(numerador[i]/denominador[i])
            sumProba = sumProba + auxProbabilidades[i] 

        for i in range(len(auxProbabilidades)):
            probabilidades.append((auxProbabilidades[i]/sumProba)*100)
            print(f"Probabilidad de que el vector pertenezca a la clase {i+1}: {probabilidades[i]}%")

        #Obteniendo la probabilidad máxima
        maximo = probabilidades[0]
        for i in range(len(probabilidades)):
            if maximo < probabilidades[i]:
                maximo = probabilidades[i]
                self.maxClase = i+1

        print(f"El vector ({coorx}, {coory}, {coorz}) es más probable que pertenezca a la clase {self.maxClase}")


    #******************************PARA PROBAR LOS MÉTODOS DE EFICIENCIA*********************************
    def metodoEuclides2(self, conjuntoClases, coorx, coory, coorz):
        
        conjuntoMedias = []
        distClase = []
        
        """
        #Imprime las clases del conjunto
        for i in range(len(conjuntoClases)):
            print("***********Clase ", (i+1),"***********")
            conjuntoClases[i].imprimeDescriptor()
            print("\n")
        """

        auxMedia = MediaDescriptor.MediaDescriptor(conjuntoClases[0].dimension) 

        #Calcula e imprime las medias por cada clases
        for i in range(len(conjuntoClases)):
            auxMedia.calculoMedia(conjuntoClases[0].repClases, conjuntoClases[i])
            #print("***********Media de clase ", (i+1),"***********")
            #auxMedia.imprimirMedia()
            conjuntoMedias.append(auxMedia.media)
            #print("\n")

        #Calculando la distancia de las coordenadas a cada centroide de las clases
        for i in range(len(conjuntoClases)):
            #print(conjuntoMedias[i])
            if conjuntoClases[0].dimension == 1:
                cuadradoX=(coorx-conjuntoMedias[i])**2
                distClase.append(sqrt(cuadradoX))
            if conjuntoClases[0].dimension == 2:
                cuadradoX=(coorx-conjuntoMedias[i][0])**2
                cuadradoY=(coory-conjuntoMedias[i][1])**2
                distClase.append(sqrt(cuadradoX+cuadradoY))
            if conjuntoClases[0].dimension == 3:
                cuadradoX=(coorx-conjuntoMedias[i][0])**2
                cuadradoY=(coory-conjuntoMedias[i][1])**2
                cuadradoZ=(coorz-conjuntoMedias[i][2])**2
                distClase.append(sqrt(cuadradoX+cuadradoY+cuadradoZ))
            
            #print(f"Distancia del vector respecto al centroide {i+1} = {distClase[i]}")  

        #Obteniendo la distancia mínima
        minimo = distClase[0]
        for i in range(len(distClase)):
            if minimo > distClase[i]:
                minimo = distClase[i]
                self.minClase = i+1

        #print(f"El vector ({coorx}, {coory}, {coorz}) pertenece a la clase {self.minClase}")

    #Método de clasificación por distancia de Mahalanobis
    def metodoMahalanobis2(self, conjuntoClases, coorx, coory, coorz):
        conjuntoMedias = []
        distClase = []
        """
        #Imprime las clases del conjunto
        for i in range(len(conjuntoClases)):
            print("***********Clase ", (i+1),"***********")
            conjuntoClases[i].imprimeDescriptor()
            print("\n")
        """
        auxMedia = MediaDescriptor.MediaDescriptor(conjuntoClases[0].dimension) 

        #Calcula e imprime las medias por cada clases
        for i in range(len(conjuntoClases)):
            auxMedia.calculoMedia(conjuntoClases[0].repClases, conjuntoClases[i])
            #print("***********Media de clase ", (i+1),"***********")
            #auxMedia.imprimirMedia()
            conjuntoMedias.append(auxMedia.media)
            #print("\n")

        #Calculando las varianzas y covarianzas de las clases
        varianzas = []
        covarianzas = []
        arregloRestas = []
        arregloTranspuestas = []
        restaClase = conjuntoClases[0]

        for i in range(len(conjuntoClases)):
            for j in range(conjuntoClases[0].repClases):
                if conjuntoClases[0].dimension == 1:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i]
                    continue
                if conjuntoClases[0].dimension == 2:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i][0][0]
                    restaClase.vector[1][j] = conjuntoClases[i].vector[1][j] - conjuntoMedias[i][1][0]
                    continue
                if conjuntoClases[0].dimension == 3:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i][0][0]
                    restaClase.vector[1][j] = conjuntoClases[i].vector[1][j] - conjuntoMedias[i][1][0]
                    restaClase.vector[2][j] = conjuntoClases[i].vector[2][j] - conjuntoMedias[i][2][0] 
            arregloRestas.append(restaClase.vector)
            arregloTranspuestas.append(np.transpose(restaClase.vector))

        for i in range(len(conjuntoClases)):
            auxVarianza = np.matmul(arregloRestas[i], arregloTranspuestas[i])
            varianzas.append(auxVarianza)
            #print(f"****VARIANZA {i+1}****")
            #print(varianzas[i])
            #print("\n")

        for i in range(len(conjuntoClases)):
            auxCovarianza = np.linalg.inv(varianzas[i])
            covarianzas.append(auxCovarianza)
            #print(f"****COVARIANZA {i+1}****")
            #print(covarianzas[i]) 
            #print("\n")

        #Calculando la distancia de mahalanobis 
        auxMult = np.zeros((1, 1))
        auxVector = np.zeros((conjuntoClases[0].dimension, 1))
        auxVectorTrans = np.zeros((1, conjuntoClases[0].dimension))

        for i in range(len(conjuntoClases)):
            if conjuntoClases[0].dimension == 1:
                auxVector[0][0] = coorx - conjuntoMedias[i]
            if conjuntoClases[0].dimension == 2:
                auxVector[0][0] = coorx - conjuntoMedias[i][0][0]
                auxVector[1][0] = coory - conjuntoMedias[i][1][0]
            if conjuntoClases[0].dimension == 3:     
                auxVector[0][0] = coorx - conjuntoMedias[i][0][0]
                auxVector[1][0] = coory - conjuntoMedias[i][1][0]
                auxVector[2][0] = coorz - conjuntoMedias[i][2][0]
            
            auxVectorTrans = np.transpose(auxVector)
            auxMult = np.matmul(auxVectorTrans, covarianzas[i]) 
            auxDistancia = np.matmul(auxMult, auxVector)
            distClase.append(auxDistancia)       
            #print(f"Distancia del vector respecto al centroide {i+1} = {distClase[i]}")

        #Obteniendo la distancia mínima
        minimo = distClase[0]
        for i in range(len(distClase)):
            if minimo > distClase[i]:
                minimo = distClase[i]
                self.minClase = i+1

        #print(f"El vector ({coorx}, {coory}, {coorz}) pertenece a la clase {self.minClase}")    

    #Método de clasificación por teorema de bayes
    def metodoBayes2(self, conjuntoClases, coorx, coory, coorz):
        """
        Dado que para calcular el exponente de Mahalanobis se
        requiere que realizar nuevamente el cálculo de la varianza
        y la covarianza, el primer paso repite el método de Mahalanobis
        """
        conjuntoMedias = []
        
        """
        #Imprime las clases del conjunto
        for i in range(len(conjuntoClases)):
            print("***********Clase ", (i+1),"***********")
            conjuntoClases[i].imprimeDescriptor()
            print("\n")
        """
        auxMedia = MediaDescriptor.MediaDescriptor(conjuntoClases[0].dimension) 

        #Calcula e imprime las medias por cada clases
        for i in range(len(conjuntoClases)):
            auxMedia.calculoMedia(conjuntoClases[0].repClases, conjuntoClases[i])
            #print("***********Media de clase ", (i+1),"***********")
            #auxMedia.imprimirMedia()
            conjuntoMedias.append(auxMedia.media)
            #print("\n")

        #Calculando las varianzas y covarianzas de las clases
        varianzas = []
        covarianzas = []
        arregloRestas = []
        arregloTranspuestas = []
        restaClase = conjuntoClases[0]

        for i in range(len(conjuntoClases)):
            for j in range(conjuntoClases[0].repClases):
                if conjuntoClases[0].dimension == 1:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i]
                    continue
                if conjuntoClases[0].dimension == 2:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i][0][0]
                    restaClase.vector[1][j] = conjuntoClases[i].vector[1][j] - conjuntoMedias[i][1][0]
                    continue
                if conjuntoClases[0].dimension == 3:
                    restaClase.vector[0][j] = conjuntoClases[i].vector[0][j] - conjuntoMedias[i][0][0]
                    restaClase.vector[1][j] = conjuntoClases[i].vector[1][j] - conjuntoMedias[i][1][0]
                    restaClase.vector[2][j] = conjuntoClases[i].vector[2][j] - conjuntoMedias[i][2][0] 
            arregloRestas.append(restaClase.vector)
            arregloTranspuestas.append(np.transpose(restaClase.vector))

        for i in range(len(conjuntoClases)):
            auxVarianza = np.matmul(arregloRestas[i], arregloTranspuestas[i])
            varianzas.append(auxVarianza)
            #print(f"****VARIANZA {i+1}****")
            #print(varianzas[i])
            #print("\n")

        for i in range(len(conjuntoClases)):
            auxCovarianza = np.linalg.inv(varianzas[i])
            covarianzas.append(auxCovarianza)
            #print(f"****COVARIANZA {i+1}****")
            #print(covarianzas[i]) 
            #print("\n")

        #Calculando el exponencial de mahalanobis
        auxMult = np.zeros((1, 1))
        auxVector = np.zeros((conjuntoClases[0].dimension, 1))
        auxVectorTrans = np.zeros((1, conjuntoClases[0].dimension))
        expMaha = []

        for i in range(len(conjuntoClases)):
            if conjuntoClases[0].dimension == 1:
                auxVector[0][0] = coorx - conjuntoMedias[i]
            if conjuntoClases[0].dimension == 2:
                auxVector[0][0] = coorx - conjuntoMedias[i][0][0]
                auxVector[1][0] = coory - conjuntoMedias[i][1][0]
            if conjuntoClases[0].dimension == 3:     
                auxVector[0][0] = coorx - conjuntoMedias[i][0][0]
                auxVector[1][0] = coory - conjuntoMedias[i][1][0]
                auxVector[2][0] = coorz - conjuntoMedias[i][2][0]
            
            auxVectorTrans = np.transpose(auxVector)
            auxMult = np.matmul(auxVectorTrans, covarianzas[i]) 
            auxDistancia = (-0.5)*(np.matmul(auxMult, auxVector))
            expMaha.append(auxDistancia)       

        numerador = []
        denominador = []
        #Elevando constante e al exponente de mahalnobis
        for i in range(len(conjuntoClases)):
            numerador.append(exp(expMaha[i])) 

        #Calculando el denominador       
        auxDen1 = 0
        auxDen2 = 0
        for i in range((len(conjuntoClases))):
            auxDen1 = pow((2*np.pi), ((conjuntoClases[0].dimension)/2))
            auxDen2 = pow(np.linalg.det(varianzas[i]), 0.5)
            denominador.append(auxDen1*auxDen2)

        #Calculando las probabilidades
        probabilidades = []
        auxProbabilidades = []
        sumProba = 0
        for i in range(len(conjuntoClases)):
            auxProbabilidades.append(numerador[i]/denominador[i])
            sumProba = sumProba + auxProbabilidades[i] 

        for i in range(len(auxProbabilidades)):
            probabilidades.append((auxProbabilidades[i]/sumProba)*100)
            #print(f"Probabilidad de que el vector pertenezca a la clase {i+1}: {probabilidades[i]}%")

        #Obteniendo la probabilidad máxima
        maximo = probabilidades[0]
        for i in range(len(probabilidades)):
            if maximo < probabilidades[i]:
                maximo = probabilidades[i]
                self.maxClase = i+1

        #print(f"El vector ({coorx}, {coory}, {coorz}) es más probable que pertenezca a la clase {self.maxClase}")
    

#************************Para pruebas**********************************
"""
pruebaC = int(input("Ingrese la cantidad de clases: "))
pruebaR = int(input("Ingrese la cantidad de representates de clase: "))
pruebaD = int(input("Ingrese la dimensión de la clase: "))
conjuntoC = []
for i in range(pruebaC):
    x = Descriptor.Descriptor()
    print("\n************Clase ",(i+1),"************\n")
    x.crearDescriptor1(i, pruebaR, pruebaD)
    conjuntoC.append(x)  

pruebaX = int(input("Ingrese la coor x a clasificar: "))
pruebaY = int(input("Ingrese la coor y a clasificar: "))
pruebaZ = int(input("Ingrese la coor z a clasificar: "))
print("\n")
y = Clasificadores()
#y.metodoEuclides(conjuntoC, pruebaX, pruebaY, pruebaZ)
#y.metodoMahalanobis(conjuntoC, pruebaX, pruebaY, pruebaZ)
y.metodoBayes(conjuntoC, pruebaX, pruebaY, pruebaZ)
y.graficaClases(conjuntoC, pruebaX, pruebaY, pruebaZ)
"""