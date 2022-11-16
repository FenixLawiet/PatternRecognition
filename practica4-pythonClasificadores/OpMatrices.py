"""
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Operaciones básicas con matrices 
"""

import numpy as np
import Descriptor

class OpMatrices:
    
    #Constructor
    def __init__(self):
        calculoRes = np.zeros((1, 1))

    #Método para transponer una matriz
    def transpuesta(self, matriz):
        self.calculoRes = np.transpose(matriz)
        return self.calculoRes

    #Método para multiplicar matrices
    def multiMatriz(self, descriptor, descriptorTranspuesto):
        self.calculoRes = np.matmul(descriptor, descriptorTranspuesto)
        return self.calculoRes

    #Método para calcular la matriz inversa
    def inversaMatriz(self, matriz):
        self.calculoRes = np.linalg.inv(matriz)
        return self.calculoRes


    #Método para calcular la determinante de una matriz  
    def determinanteMatriz(self, matriz):
        self.calculoRes = np.linalg.det(matriz)
        return self.calculoRes     
