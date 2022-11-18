"""
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Métodos de eficiencia
"""

import numpy as np
from mpl_toolkits.mplot3d import axes3d
import matplotlib.pyplot as plt
from math import *
import random as r
import Descriptor
import MediaDescriptor
import Clasificadores

class MetodosEficiencia:
    
    def __init__(self):
        matrizConf = np.zeros((1, 1))

    def matrizConfusion(self, conjuntoClases, selectorMetodoC, selectorMetodoE):
        #Creando las clases de prueba, con la misma cantidad de representantes de clases y centroides
        conjuntoTest = []
        auxClase = Descriptor.Descriptor()
        self.matrizConf = np.zeros((len(conjuntoClases), len(conjuntoClases)))
        
        for i in range(len(conjuntoClases)):
            coorx = conjuntoClases[i].coorx
            coory = conjuntoClases[i].coory
            coorz = conjuntoClases[i].coorz
            disp = conjuntoClases[i].disp
            repClases = conjuntoClases[i].repClases
            dimension = conjuntoClases[i].dimension
            auxClase.crearDescriptor2(coorx, coory, coorz, disp, dimension)
            conjuntoTest.append(auxClase)

        #Determinando el método de eficiencia a usar
        if selectorMetodoE == 1:
            #Método de restitución
            if selectorMetodoC == 1:
                #Clasificador euclidiano
                clasificador = Clasificadores.Clasificadores()
                for i in range(len(conjuntoTest)):
                    clasificador.metodoEuclides(conjuntoClases, conjuntoTest[i].coorx, conjuntoTest[i].coory, conjuntoTest[i].coorz)
                    if clasificador.minClase == (i+1):
                        self.matrizConf[i][i] = self.matrizConf[i][i]+1
                    else:
                        self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1       

            if selectorMetodoC == 2:
                #Clasificador de mahalanobis
                clasificador = Clasificadores.Clasificadores()
                for i in range(len(conjuntoTest)):
                    clasificador.metodoMahalanobis(conjuntoClases, conjuntoTest[i].coorx, conjuntoTest[i].coory, conjuntoTest[i].coorz)
                    if clasificador.minClase == (i+1):
                        self.matrizConf[i][i] = self.matrizConf[i][i]+1
                    else:
                        self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1 

            if selectorMetodoC == 3:
                #Clasificador bayesiano
                clasificador = Clasificadores.Clasificadores()  
                for i in range(len(conjuntoTest)):
                    clasificador.metodoBayes(conjuntoClases, conjuntoTest[i].coorx, conjuntoTest[i].coory, conjuntoTest[i].coorz)  
                    if clasificador.maxClase == (i+1):
                        self.matrizConf[i][i] = self.matrizConf[i][i]+1
                    else:
                        self.matrizConf[i][clasificador.maxClase-1] = self.matrizConf[i][clasificador.maxClase-1] + 1 

        if selectorMetodoE == 2:
            #Método de cross validation
            if selectorMetodoC == 1:
                #Clasificador euclidiano
                clasificador = Clasificadores.Clasificadores()

            if selectorMetodoC == 2:
                #Clasificador de mahalanobis
                clasificador = Clasificadores.Clasificadores()

            if selectorMetodoC == 3:
                #Clasificador bayesiano
                clasificador = Clasificadores.Clasificadores()

        if selectorMetodoE == 3:
            #Método de hold in one
            if selectorMetodoC == 1:
                #Clasificador euclidiano
                clasificador = Clasificadores.Clasificadores()

            if selectorMetodoC == 2:
                #Clasificador de mahalanobis
                clasificador = Clasificadores.Clasificadores()

            if selectorMetodoC == 3:
                #Clasificador bayesiano
                clasificador = Clasificadores.Clasificadores()       

    def imprimirMatrizC(self):
        pass

    def graficaEficiencia(self):
        pass