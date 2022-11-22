"""
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Objeto media de N dimensiones
"""

import numpy as np
import Descriptor

class MediaDescriptor:
    #Constructor
    def __init__(self, dimension):
        media = np.zeros((dimension, 1))
        divisor = 0
        sumaMediaX = 0
        sumaMediaY = 0
        sumaMediaZ = 0

    #Método para calcular la media de una clase dada (matriz)
    def calculoMedia(self, repClases, matriz):
        self.divisor = repClases
        self.auxClase = matriz
        self.media = np.zeros((self.auxClase.dimension, 1))
        self.sumaMediaX = 0
        self.sumaMediaY = 0
        self.sumaMediaZ = 0
        for i in range(self.auxClase.dimension):
            #Suma la primera fila, valores de X
            if i==0:
                for j in range(self.divisor):
                    self.sumaMediaX = self.sumaMediaX + self.auxClase.vector[i][j]

            #Suma la segunda fila, valores de Y
            if i==1:
                for j in range(self.divisor):
                    self.sumaMediaY = self.sumaMediaY + self.auxClase.vector[i][j] 
                    
            #Suma la segunda fila, valores de Z
            if i==2:
                for j in range(self.divisor):
                    self.sumaMediaZ = self.sumaMediaZ + self.auxClase.vector[i][j] 


        if self.auxClase.dimension==1:
            self.media = self.sumaMediaX / self.divisor                           

        if self.auxClase.dimension==2:
            self.media[0][0] = self.sumaMediaX / self.divisor
            self.media[1][0] = self.sumaMediaY / self.divisor

        if self.auxClase.dimension==3:
            self.media[0][0] = self.sumaMediaX / self.divisor
            self.media[1][0] = self.sumaMediaY / self.divisor
            self.media[2][0] = self.sumaMediaZ / self.divisor   

    #Método para imprimir la matriz de la media
    def imprimirMedia(self):
        print(self.media)
         