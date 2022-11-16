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

    #Método que grafica las clases y el punto a encontrar
    def grafica(self, conjuntoClases, coorx, coory, coorz):
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
            ax1.scatter(conjuntoClases[i].vector[0], conjuntoClases[i].vector[1], conjuntoClases[i].vector[2], c=colors[i+1], marker='o') #cambiar el formato del vector
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
            cuadradoX=(coorx-conjuntoMedias[i][0])**2
            cuadradoY=(coory-conjuntoMedias[i][1])**2
            cuadradoZ=(coorz-conjuntoMedias[i][2])**2
            distClase.append(sqrt(cuadradoX+cuadradoY+cuadradoZ))
            #print("Distancia del vector respecto al centroide "+str((i+1))+" ="+str(distClase[i]))
            print(f"Distancia del vector respecto al centroide {i+1} = {distClase[i]}")  

        #Obteniendo la distancia mínima
        minimo = distClase[0]
        minClase = 1
        for i in range(len(distClase)):
            if minimo > distClase[i]:
                minimo = distClase[i]
                minClase = i+1

        print("El vector (",coorx, ", ",coory,", ",coorz,") pertenece a la clase ", minClase)

#************************Para pruebas**********************************
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
y.metodoEuclides(conjuntoC, pruebaX, pruebaY, pruebaZ)
y.grafica(conjuntoC, pruebaX, pruebaY, pruebaZ)