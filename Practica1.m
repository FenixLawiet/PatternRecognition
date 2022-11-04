clc %limpiar pantalla
close all %cierra todo
clear all %limpia todo
warning off all 
disp('Welcome to Pattern Recognition')

%Diseno de un clasificador dde distancia Euclideana
%variable de ciclo

prg=input('quieres meter una coordenada? 1..si  2..no')
while prg==1
  
  %Metiendo las clases de pertenencia 
  c1=[1 2 3 3 4; 1 2 1 3 2];
  c2=[6 7 6 8 8; 5 6 7 4 7];
  c3=[3 4 6 6 9; 15 16 14 16 13];
  c4=[12 13 14 15 17; 10 8 11 9 11]; 
  c5=[14 15 17 18 19; 20 18 15 20 18];
  c6=[20 21 23 24 26; 2 4 1 5 2]; 
  vx=input('dame la coordenada de X= ')
  vy=input('dame la coordenada de Y= ')
  
  sprintf('El rango de la coordenada es de 30 en x y 30 en y')
   if vx<30&&vx>0
   if vy<30&&vy>0
    vector=[vx;vy]; 

    %Graficando las clases  
    figure(1)
    plot(c1(1, :),c1(2, :),'ro','MarkerFaceColor','r','MarkerSize', 10)
    grid on
    hold on
    plot(c2(1, :),c2(2, :),'ro','MarkerFaceColor','b','MarkerSize', 10)
    plot(c3(1, :),c3(2, :),'ko','MarkerFaceColor','k','MarkerSize', 10)
    %duda sobre que se refiere a r
    plot(c4(1, :),c4(2, :),'ro','MarkerFaceColor','y','MarkerSize', 10)
    plot(c5(1, :),c5(2, :),'ro','MarkerFaceColor','g','MarkerSize', 10)
    plot(c6(1, :),c6(2, :),'ro','MarkerFaceColor','w','MarkerSize', 10)
    plot(vector(1, :),vector(2, :),'go','MarkerFaceColor','g','MarkerSize', 10)
    legend('clase1','clase2','clase3','clase4','clase5','clase6','vector')

    %parametros de la media
    media1=mean(c1,2);
    media2=mean(c2,2);
    media3=mean(c3,2);
    media4=mean(c4,2);
    media5=mean(c3,2);
    media6=mean(c4,2);
   
    
    distancia1=media1*transpose(media1)
    distancia2=norm(media2-vector)
    distancia3=norm(media3-vector)
    distancia4=norm(media4-vector)
    distancia5=norm(media3-vector)
    distancia6=norm(media4-vector)

    dist_total=[distancia1,distancia2,distancia3,distancia4,distancia5,distancia4]
    minima=min(min(dist_total))
    encuentra=find(dist_total==minima)
    sprintf('el vector desconocido pertenece a la clase %d',encuentra)
    prg=input('quieres meter otra coordenada? 1..si  2..no')
   end
   end
   
end

sprintf('Hasta pronto')