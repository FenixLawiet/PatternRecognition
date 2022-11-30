clc % limpia pantalla
clear all % limpia todo
close all %cierra todo
warning off all  % apaga los warnings

while (true)
    opcion = input('Que desea probar?\n,1-AND\n2-Cubo\n3-Salir\n');
    if(opcion==1)
        AND();
    end
    if(opcion==2)
        CUBO();
    end
    if(opcion==3)
        break;
    end
end
disp('fin de proceso...');

function [] = AND()
    % A B | F
    % 0 0 | 0
    % 0 1 | 0
    % 1 0 | 0
    % 1 1 | 1
    entradas = [0 0;0 1;1 0;1 1];
    correccion = true;
    vector = [];
    Ws = input('introduzca los pesos en forma de vector [w1 w2 w3]');
    r = input('introduzca el valor de correción');
    Ws =  Ws';
    j = 1;
     while (correccion)
         correccion = false;
         for i=1:1:4
             vector = entradas(i,:);
             vector(3) = 1;
             if ((i < 4) && ((vector*Ws)>=0))
                 Ws =  Ws-(r*vector');
                 correccion = true;
             end
             if ((i == 4) && ((vector*Ws)<=0))
                 Ws =  Ws+(r*vector');
                 correccion = true;
             end         
         end
         fprintf('iteración %d \n',j);
         Ws
         j = j + 1 ;
     end
     fprintf('la funcion de salida es: %dx + %dy + %d = 0 \n',Ws(1),Ws(2),Ws(3));
     x = -0.5:0.01:1.5;
     y = ((x*(-1)*Ws(1))/Ws(2))+((Ws(3)*(-1))/Ws(2));
     figure(1)
     plot(entradas(1:3,1),entradas(1:3,2),'bo','MarkerSize',10,'MarkerFaceColor','b'); 
     grid on
     hold on
     plot(entradas(4,1),entradas(4,2),'ro','MarkerSize',10,'MarkerFaceColor','r');
     plot(x,y);
     legend("clase 1","clase 2","recta");
end

function [] = CUBO()
    entradas = [1 0 1;0 0 0;1 0 0;1 1 0;0 0 1;0 1 1;1 1 1;0 1 0]
    correccion = true;
    vector = [];
    Ws = input('introduzca los pesos en forma de vector [w1 w2 w3 w4]');
    r = input('introduzca el valor de correción');
    Ws =  Ws';
    j = 1;
     while (correccion)
         correccion = false;
         for i=1:1:8
             vector = entradas(i,:);
             vector(4) = 1;
             if ((i < 5) && ((vector*Ws)>=0))
                 Ws =  Ws-(r*vector');
                 correccion = true;
             end
             if ((i >= 5) && ((vector*Ws)<=0))
                 Ws =  Ws+(r*vector');
                 correccion = true;
             end         
         end
         fprintf('iteración %d \n',j);
         Ws
         j = j + 1 ;
     end
     entradas = entradas';
     fprintf('la funcion de salida es: %dx + %dy + %dz + %d = 0 \n',Ws(1),Ws(2),Ws(3),Ws(4));
     [x,y] = meshgrid(-2:0.5:2);
     z = ((x*(-1)*Ws(1))/Ws(3))+((y*(-1)*Ws(2))/Ws(3))+((Ws(4)*(-1))/Ws(3));
     figure(2)
     plot3(entradas(1,1:4),entradas(2,1:4),entradas(3,1:4),'bo','MarkerSize',10,'MarkerFaceColor','b'); 
     grid on
     hold on
     plot3(entradas(1,5:end),entradas(2,5:end),entradas(3,5:end),'ro','MarkerSize',10,'MarkerFaceColor','r');
     surf(x,y,z);
     legend("clase 1","clase 2","plano");
end