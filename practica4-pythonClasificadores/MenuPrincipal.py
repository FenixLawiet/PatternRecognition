"""
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Menú principal
"""

import Descriptor
import MediaDescriptor
import Clasificadores
import MetodosEficiencia

numClases = 0
numDimension = 0
numRepClases = 0
conjuntoC = []
selector1 = 0
selector2 = 0
selector3 = 0
coorx = 0
coory = 0 
coorz = 0
selector4 = "y"


print("***********************SISTEMA DE CLASIFICADORES********************")
print("UMBRAL MÁXIMO DE PERTENENCIA DE -100 A 100 EN LOS 3 EJES")
print("Nota: No se puede gráficar en más de 3 dimensiones")
while True:

    numClases = int(input("Ingrese la cantidad de clases: "))
    numRepClases = int(input("Ingrese la cantidad de representates de clase: "))
    numDimension = int(input("Ingrese la dimensión de la clase: "))
    if (numClases <= 0) or (numRepClases <= 10) or (numDimension <= 0) or (numDimension > 3):
        print("ERROR. Uno o más datos incorrectos o insuficientes. Ingrese nuevamente")
    else:
        for i in range(numClases):
            x = Descriptor.Descriptor()
            print("\n************Clase ",(i+1),"************\n")
            x.crearDescriptor1(i, numRepClases, numDimension)
            conjuntoC.append(x)  
        break
    

while selector4=="y" or selector4=="Y":    
    y = Clasificadores.Clasificadores()
    z = MetodosEficiencia.MetodosEficiencia()
    print("\n**************Clasificar vector ------- 1")
    print("**************Medir % de eficiencia ------- 2")
    selector1 = int(input("Ingrese una opción: "))
    if selector1 == 1:
        coorx = int(input("Ingrese la coordenada x a clasificar: "))
        coory = int(input("Ingrese la coordenada y a clasificar: "))
        coorz = int(input("Ingrese la coordenada z a clasificar: "))
        if coorx > 100 or coory > 100 or coorz > 100 or coorx < -100 or coory < -100 or coorz < -100:
            print("El vector esta fuera del umbral de pertenencia, no pertenece a ninguna clase.")
        else:     
            print("\n********Euclidiano-------1")
            print("********Mahalanobis------2")
            print("********Bayes------------3")
            selector2 = int(input("Ingrese el método a utilizar: "))
            print("\n")
            if selector2 == 1:
                y.metodoEuclides(conjuntoC, coorx, coory, coorz)
                y.graficaClases(conjuntoC, coorx, coory, coorz)
            elif selector2 == 2: 
                y.metodoMahalanobis(conjuntoC, coorx, coory, coorz)
                y.graficaClases(conjuntoC, coorx, coory, coorz)
            elif selector2 == 3:
                y.metodoBayes(conjuntoC, coorx, coory, coorz)
                y.graficaClases(conjuntoC, coorx, coory, coorz)
            else:
                print("ERROR. Operación no válida.")

    if selector1 == 2:
        print("\n************Método de restitución----------1")
        print("************Método de cossvalidation-------2")
        print("************Método de hold in one----------3")
        selector3 = int(input("Ingrese el método eficiente: "))
        if selector3 != 1 and selector3 != 2 and selector3 != 3:
            print("ERROR. Operación no válida")        
        else:
            print("\n********Euclidiano-------1")
            print("********Mahalanobis------2")
            print("********Bayes------------3")
            selector2 = int(input("Ingrese el método a utilizar: "))
            print("\n")
            if selector2 != 1 and selector2 != 2 and selector2 != 3:
                print("ERROR. Operación no válida")
            else:
                z.matrizConfusion(conjuntoC, selector2, selector3)
                z.graficaEficiencia(conjuntoC)
                y.graficaClases(conjuntoC, 0, 0, 0)
    else:
        print("ERROR. Operación no válida")

    selector4 = input("Desea realizar otra operación? Y/N: ")    
