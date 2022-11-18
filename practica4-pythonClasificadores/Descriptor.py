"""
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Objeto descriptor de caracteristicas. N dimensiones
"""

import numpy as np
import random

class Descriptor:
    
    #Constructor
    def __init__(self):
        vector = np.zeros((1, 1))
        repClases = 0
        coorx = 0 
        coory = 0
        coorz = 0  
        disp = 0
        dimension = 0 

    #Creando descriptor con id de clase, cantidad de representantes y la dimensión
    def crearDescriptor1(self, numClase, repClases, dimension):
        """
        Se generarán los puntos de cada clase aleatoriamente de acuerdo a un 
        centroide y a una dispersión. Esta clase recibe la cantidad de n 
        representantes de clase y establece esa cantidad para todas la clases 
        generadas.
        
        """
        self.repClases = repClases
        self.coorx = 0 
        self.coory = 0 
        self.coorz = 0
        self.disp = 0
        self.dimension = dimension
        self.vector = np.zeros((int(dimension), int(repClases)))        
        while True:
            if dimension==1:
                self.coorx = input("\nIngresa la coordenada x del centroide de la clase "+str(numClase+1)+": ")
            if dimension==2:
                self.coorx = input("\nIngresa la coordenada x del centroide de la clase "+str(numClase+1)+": ")
                self.coory = input("\nIngresa la coordenada y del centroide de la clase "+str(numClase+1)+": ")
            if dimension==3:
                self.coorx = input("\nIngresa la coordenada x del centroide de la clase "+str(numClase+1)+": ")
                self.coory = input("\nIngresa la coordenada y del centroide de la clase "+str(numClase+1)+": ")
                self.coorz = input("\nIngresa la coordenada z del centroide de la clase "+str(numClase+1)+": ")
            
            self.disp = input("\nIngresa la dispersión de la clase "+str(numClase+1)+": ")
            if (int(self.coorx)>=100) or (int(self.coory)>=100) or (int(self.coorz)>=100) or (int(self.coorx)<=-100) or (int(self.coory)<=-100) or (int(self.coorz)<=-100) or (int(self.disp)<=0):
                print("\n*******ERROR. Uno o más datos no válidos. Reingrese datos*******")
            else:
                break    

        #Generando el random y llenando la clase. El primer elemento siempre es el centroide
        for i in range(dimension): 
            for j in range(repClases):
                if i==0 :
                    if i==0 and j==0:
                        self.vector[i][j]=int(self.coorx)
                    else:
                        self.vector[i][j]=(random.uniform(0, int(self.disp))+int(self.coorx))
                if i==1 :
                    if i==1 and j==0:
                        self.vector[i][j]=self.coory
                    else:
                        self.vector[i][j]=(random.uniform(0, int(self.disp))+int(self.coory))
                if i==2 :
                    if i==2 and j==0:
                        self.vector[i][j]=self.coorz
                    else:
                        self.vector[i][j]=(random.uniform(0, int(self.disp))+int(self.coorz))    

    #Método para construir automáticamente un descriptor con centroide, dispersión y dimension dadas
    def crearDescriptor2(self, coorx, coory, coorz, disp, repClases, dimension):
        self.repClases = repClases
        self.coorx = coorx 
        self.coory = coory
        self.coorz = coorz 
        self.disp = disp
        self.dimension = dimension   
        self.vector = np.zeros((int(dimension), int(repClases)))

        #Generando el random y llenando la clase. El primer elemento siempre es el centroide
        for i in range(self.dimension): 
            for j in range(self.repClases):
                if i==0 :
                    if i==0 and j==0:
                        self.vector[i][j]=self.coorx
                    else:
                        self.vector[i][j]=(random.uniform(0, int(self.disp))+int(self.coorx))
                if i==1 :
                    if i==1 and j==0:
                        self.vector[i][j]=self.coory
                    else:
                        self.vector[i][j]=(random.uniform(0, int(self.disp))+int(self.coory))
                if i==2 :
                    if i==2 and j==0:
                        self.vector[i][j]=self.coorz
                    else:
                        self.vector[i][j]=(random.uniform(0, int(self.disp))+int(self.coorz))                

    #Método para imprimir el contenido de la clase
    def imprimeDescriptor(self):
        print(self.vector)
        """
        for i in range(len(self.vector)): 
            for j in range(len(self.vector[0])):
                print(self.vector[i][j], sep=', ')  
            print("\n")"""

#*********************************Para pruebas*********************************************

"""
pruebaC = int(input("Ingrese la cantidad de clases: "))
pruebaR = int(input("Ingrese la cantidad de representates de clase: "))
pruebaD = int(input("Ingrese la dimensión de la clase: "))
x = Descriptor()
for i in range(pruebaC):
    print("\n************Clase ",(i+1),"************\n")
    x.crearDescriptor1(i, pruebaR, pruebaD)
    x.imprimeDescriptor()                                   
"""