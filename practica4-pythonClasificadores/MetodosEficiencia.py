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
        self.matrizConf = np.zeros((1, 1))
        self.presicion = []

    def matrizConfusion(self, conjuntoClases, selectorMetodoC, selectorMetodoE):
        #Creando las clases de prueba, con la misma cantidad de representantes de clases y centroides
        conjuntoTest = []
        conjuntoClasesCross = []
        auxClasesCross = Descriptor.Descriptor()
        mitadRep = 0
        coorrandom = 0
        auxClase = Descriptor.Descriptor()
        self.matrizConf = np.zeros((len(conjuntoClases), len(conjuntoClases)))
        
        #Para restitución
        for i in range(len(conjuntoClases)):
            coorx = conjuntoClases[i].coorx
            coory = conjuntoClases[i].coory
            coorz = conjuntoClases[i].coorz
            disp = conjuntoClases[i].disp
            repClases = conjuntoClases[i].repClases
            dimension = conjuntoClases[i].dimension
            auxClase.crearDescriptor2(coorx, coory, coorz, disp, repClases, dimension)
            conjuntoTest.append(auxClase)

        #Para cross validation
        if (conjuntoClases[0].repClases % 2) == 0:    
            mitadRep = conjuntoClases[0].repClases / 2
        else:
            mitadRep = (conjuntoClases[0].repClases / 2) + 0.5

        auxClasesCross.vector = np.zeros((conjuntoClases[0].dimension, int(mitadRep)))
        for i in range(len(conjuntoClases)):
            for j in range(int(mitadRep)):
                if conjuntoClases[i].dimension == 1:
                    auxClasesCross.vector[0][j] = conjuntoClases[i].vector[0][j]
                if conjuntoClases[i].dimension == 2:
                    auxClasesCross.vector[0][j] = conjuntoClases[i].vector[0][j]
                    auxClasesCross.vector[1][j] = conjuntoClases[i].vector[1][j]
                if conjuntoClases[i].dimension == 3:
                    auxClasesCross.vector[0][j] = conjuntoClases[i].vector[0][j]
                    auxClasesCross.vector[1][j] = conjuntoClases[i].vector[1][j]
                    auxClasesCross.vector[2][j] = conjuntoClases[i].vector[2][j]              
            conjuntoClasesCross.append(auxClasesCross)

        #Para hold in one. Tomando solo una coordenada ALEATORIA por clase al clasificar
        coorrandom = int(r.uniform(0, conjuntoClases[0].repClases)) #Número random entre 0 y los rep de clases
        if coorrandom > conjuntoClases[0].repClases:
            coorrandom = coorrandom - 1

        #Determinando el método de eficiencia a usar
        xAux = 0
        yAux = 0
        zAux = 0
        if selectorMetodoE == 1:
            #Método de restitución ********************************************************
            if selectorMetodoC == 1:
                #Clasificador euclidiano
                clasificador = Clasificadores.Clasificadores()
                for i in range(len(conjuntoTest)):   
                    for j in range(conjuntoTest[i].repClases):
                        if conjuntoTest[i].dimension == 1:
                            xAux = conjuntoTest[i].vector[0][j]
                            clasificador.metodoEuclides2(conjuntoClases, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1   
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")    
                        if conjuntoTest[i].dimension == 2:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            clasificador.metodoEuclides2(conjuntoClases, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1 
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            
                        if conjuntoTest[i].dimension == 3:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            zAux = conjuntoTest[i].vector[2][j]
                            clasificador.metodoEuclides2(conjuntoClases, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1 
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
           
           
            if selectorMetodoC == 2:
                #Clasificador de mahalanobis
                clasificador = Clasificadores.Clasificadores()
                for i in range(len(conjuntoTest)):   
                    for j in range(conjuntoTest[i].repClases):
                        if conjuntoTest[i].dimension == 1:
                            xAux = conjuntoTest[i].vector[0][j]
                            clasificador.metodoMahalanobis2(conjuntoClases, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1  
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                        if conjuntoTest[i].dimension == 2:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            clasificador.metodoMahalanobis2(conjuntoClases, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")    
                            
                        if conjuntoTest[i].dimension == 3:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            zAux = conjuntoTest[i].vector[2][j]
                            clasificador.metodoMahalanobis2(conjuntoClases, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")

            if selectorMetodoC == 3:
                #Clasificador bayesiano
                clasificador = Clasificadores.Clasificadores()  
                for i in range(len(conjuntoTest)):   
                    for j in range(conjuntoTest[i].repClases):
                        if conjuntoTest[i].dimension == 1:
                            xAux = conjuntoTest[i].vector[0][j]
                            clasificador.metodoBayes2(conjuntoClases, xAux, yAux, zAux)
                            if clasificador.maxClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                            else:
                                self.matrizConf[i][clasificador.maxClase-1] = self.matrizConf[i][clasificador.maxClase-1] + 1 
                                print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                        if conjuntoTest[i].dimension == 2:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            clasificador.metodoBayes2(conjuntoClases, xAux, yAux, zAux)
                            if clasificador.maxClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                            else:
                                self.matrizConf[i][clasificador.maxClase-1] = self.matrizConf[i][clasificador.maxClase-1] + 1
                                print(f"Valor de minClase/maxClase = {clasificador.maxClase}")  
                            
                        if conjuntoTest[i].dimension == 3:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            zAux = conjuntoTest[i].vector[2][j]
                            clasificador.metodoBayes2(conjuntoClases, xAux, yAux, zAux)
                            if clasificador.maxClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                            else:
                                self.matrizConf[i][clasificador.maxClase-1] = self.matrizConf[i][clasificador.maxClase-1] + 1
                                print(f"Valor de minClase/maxClase = {clasificador.maxClase}")   

        if selectorMetodoE == 2:
            #Método de cross validation (se clasifican solo la mitad) ********************************************
            if selectorMetodoC == 1:
                #Clasificador euclidiano
                clasificador = Clasificadores.Clasificadores()
                for i in range(len(conjuntoTest)):   
                    for j in range(int(mitadRep)):
                        if conjuntoTest[i].dimension == 1:
                            xAux = conjuntoTest[i].vector[0][j]
                            clasificador.metodoEuclides2(conjuntoClasesCross, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1 
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}") 
                        if conjuntoTest[i].dimension == 2:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            clasificador.metodoEuclides2(conjuntoClasesCross, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1   
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            
                        if conjuntoTest[i].dimension == 3:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            zAux = conjuntoTest[i].vector[2][j]
                            clasificador.metodoEuclides2(conjuntoClasesCross, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1 
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
           
           
            if selectorMetodoC == 2:
                #Clasificador de mahalanobis
                clasificador = Clasificadores.Clasificadores()
                for i in range(len(conjuntoTest)):   
                    for j in range(int(mitadRep)):
                        if conjuntoTest[i].dimension == 1:
                            xAux = conjuntoTest[i].vector[0][j]
                            clasificador.metodoMahalanobis2(conjuntoClasesCross, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1 
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                        if conjuntoTest[i].dimension == 2:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            clasificador.metodoMahalanobis2(conjuntoClasesCross, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1 
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            
                        if conjuntoTest[i].dimension == 3:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            zAux = conjuntoTest[i].vector[2][j]
                            clasificador.metodoMahalanobis2(conjuntoClasesCross, xAux, yAux, zAux)
                            if clasificador.minClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")

            if selectorMetodoC == 3:
                #Clasificador bayesiano
                clasificador = Clasificadores.Clasificadores()  
                for i in range(len(conjuntoTest)):   
                    for j in range(int(mitadRep)):
                        if conjuntoTest[i].dimension == 1:
                            xAux = conjuntoTest[i].vector[0][j]
                            clasificador.metodoBayes2(conjuntoClasesCross, xAux, yAux, zAux)
                            if clasificador.maxClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                            else:
                                self.matrizConf[i][clasificador.maxClase-1] = self.matrizConf[i][clasificador.maxClase-1] + 1
                                print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                        if conjuntoTest[i].dimension == 2:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            clasificador.metodoBayes2(conjuntoClasesCross, xAux, yAux, zAux)
                            if clasificador.maxClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                            else:
                                self.matrizConf[i][clasificador.maxClase-1] = self.matrizConf[i][clasificador.maxClase-1] + 1 
                                print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                            
                        if conjuntoTest[i].dimension == 3:
                            xAux = conjuntoTest[i].vector[0][j]
                            yAux = conjuntoTest[i].vector[1][j]
                            zAux = conjuntoTest[i].vector[2][j]
                            clasificador.metodoBayes2(conjuntoClasesCross, xAux, yAux, zAux)
                            if clasificador.maxClase == (i+1):
                                self.matrizConf[i][i] = self.matrizConf[i][i]+1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            else:
                                self.matrizConf[i][clasificador.maxClase-1] = self.matrizConf[i][clasificador.maxClase-1] + 1
                                print(f"Valor de minClase/maxClase = {clasificador.minClase}")

        if selectorMetodoE == 3:
            #Método de hold in one *********************************************************
            if selectorMetodoC == 1:
                #Clasificador euclidiano
                clasificador = Clasificadores.Clasificadores()
                for i in range(len(conjuntoTest)):   
                    if conjuntoTest[i].dimension == 1:
                        xAux = conjuntoTest[i].vector[0][coorrandom]
                        clasificador.metodoEuclides2(conjuntoClases, xAux, yAux, zAux)
                        if clasificador.minClase == (i+1):
                            self.matrizConf[i][i] = self.matrizConf[i][i]+1
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                        else:
                            self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1 
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                    if conjuntoTest[i].dimension == 2:
                        xAux = conjuntoTest[i].vector[0][coorrandom]
                        yAux = conjuntoTest[i].vector[1][coorrandom]
                        clasificador.metodoEuclides2(conjuntoClases, xAux, yAux, zAux)
                        if clasificador.minClase == (i+1):
                            self.matrizConf[i][i] = self.matrizConf[i][i]+1
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                        else:
                            self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            
                    if conjuntoTest[i].dimension == 3:
                        xAux = conjuntoTest[i].vector[0][coorrandom]
                        yAux = conjuntoTest[i].vector[1][coorrandom]
                        zAux = conjuntoTest[i].vector[2][coorrandom]
                        clasificador.metodoEuclides2(conjuntoClases, xAux, yAux, zAux)
                        if clasificador.minClase == (i+1):
                            self.matrizConf[i][i] = self.matrizConf[i][i]+1
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                        else:
                            self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")

            if selectorMetodoC == 2:
                #Clasificador de mahalanobis
                clasificador = Clasificadores.Clasificadores()
                for i in range(len(conjuntoClases)):
                    if conjuntoTest[i].dimension == 1:
                        xAux = conjuntoTest[i].vector[0][coorrandom]
                        clasificador.metodoMahalanobis2(conjuntoClases, xAux, yAux, zAux)
                        if clasificador.minClase == (i+1):
                            self.matrizConf[i][i] = self.matrizConf[i][i]+1
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                        else:
                            self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                    if conjuntoTest[i].dimension == 2:
                        xAux = conjuntoTest[i].vector[0][coorrandom]
                        yAux = conjuntoTest[i].vector[1][coorrandom]
                        clasificador.metodoMahalanobis2(conjuntoClases, xAux, yAux, zAux)
                        if clasificador.minClase == (i+1):
                            self.matrizConf[i][i] = self.matrizConf[i][i]+1
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                        else:
                            self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1 
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                            
                    if conjuntoTest[i].dimension == 3:
                        xAux = conjuntoTest[i].vector[0][coorrandom]
                        yAux = conjuntoTest[i].vector[1][coorrandom]
                        zAux = conjuntoTest[i].vector[2][coorrandom]
                        clasificador.metodoMahalanobis2(conjuntoClases, xAux, yAux, zAux)
                        if clasificador.minClase == (i+1):
                            self.matrizConf[i][i] = self.matrizConf[i][i]+1
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
                        else:
                            self.matrizConf[i][clasificador.minClase-1] = self.matrizConf[i][clasificador.minClase-1] + 1
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")
            if selectorMetodoC == 3:
                #Clasificador bayesiano
                clasificador = Clasificadores.Clasificadores()
                for i in range(len(conjuntoClases)):
                    if conjuntoTest[i].dimension == 1:
                        xAux = conjuntoTest[i].vector[0][coorrandom]
                        clasificador.metodoBayes2(conjuntoClases, xAux, yAux, zAux)
                        if clasificador.maxClase == (i+1):
                            self.matrizConf[i][i] = self.matrizConf[i][i]+1
                            print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                        else:
                            self.matrizConf[i][clasificador.maxClase-1] = self.matrizConf[i][clasificador.maxClase-1] + 1 
                            print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                    if conjuntoTest[i].dimension == 2:
                        xAux = conjuntoTest[i].vector[0][coorrandom]
                        yAux = conjuntoTest[i].vector[1][coorrandom]
                        clasificador.metodoBayes2(conjuntoClases, xAux, yAux, zAux)
                        if clasificador.maxClase == (i+1):
                            self.matrizConf[i][i] = self.matrizConf[i][i]+1
                            print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                        else:
                            self.matrizConf[i][clasificador.maxClase-1] = self.matrizConf[i][clasificador.maxClase-1] + 1  
                            print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                            
                    if conjuntoTest[i].dimension == 3:
                        xAux = conjuntoTest[i].vector[0][coorrandom]
                        yAux = conjuntoTest[i].vector[1][coorrandom]
                        zAux = conjuntoTest[i].vector[2][coorrandom]
                        clasificador.metodoBayes2(conjuntoClases, xAux, yAux, zAux)
                        if clasificador.maxClase == (i+1):
                            self.matrizConf[i][i] = self.matrizConf[i][i]+1
                            print(f"Valor de minClase/maxClase = {clasificador.maxClase}")
                        else:
                            self.matrizConf[i][clasificador.maxClase-1] = self.matrizConf[i][clasificador.maxClase-1] + 1  
                            print(f"Valor de minClase/maxClase = {clasificador.minClase}")

        #Normalizando las presiciones
        print("\n***********MATRIZ DE CONFUSIÓN***********")
        #yolo = MetodosEficiencia()
        print(self.matrizConf)
        #yolo.imprimirMatrizC()
        auxPre = 0
        for i in range(len(conjuntoClases)):
            for j in range(len(conjuntoClases)):
                if i==j:
                    auxPre=self.matrizConf[i][j]/repClases
                    self.presicion.append(auxPre*100)
                    print(f"La precisión de en la clase {(i+1)} fue de: {self.presicion[i]}%")

        #Presicion del método
        presicionMetodo = 0           
        sumaPresicion = 0
        for i in range(len(self.presicion)):
            sumaPresicion = sumaPresicion + self.presicion[i]

        presicionMetodo = sumaPresicion / len(conjuntoClases)

        print(f"La presición del método {selectorMetodoC} por el método eficiente {selectorMetodoE} es de {presicionMetodo}%")

    def graficaEficiencia(self, conjuntoClases):
        #Creando un arreglo de colores
        colors = []
        for i in range(len(conjuntoClases)+1):
            colors.append('#%06X' % r.randint(0, 0xFFFFFF))
        
        leyendas = []
        tamaño = len(conjuntoClases)
        fig, ax = plt.subplots()
        for i in range(tamaño):
            ax.bar(i, self.presicion[i])
        plt.show()
