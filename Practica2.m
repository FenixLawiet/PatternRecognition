clc 
close all 
clear all 
warning off all 
disp('welcome to pattern recognition')
count = 0;
while count < 1
  
  disp('Clase 1\n')
  c1=[1 2 3 3 4; 1 2 1 3 2]
  disp('Clase 2\n')
  c2=[6 7 6 8 8; 5 6 7 4 7]
  disp('Clase 3\n')
  c3=[3 4 6 6 9; 15 16 14 16 13]
  disp('Clase 4\n')
  c4=[12 13 14 15 17; 10 8 11 9 11]
  disp('Clase 5\n')
  c5=[14 15 17 18 19; 20 18 15 20 18]
  disp('Clase 6\n')
  c6=[20 21 23 24 26; 2 4 1 5 2]
    %introduciendo vector desconocido:
    disp('Margen de pertenencia de 0 a 30')
    vx=input('dame la coord del vector en x=')
    vy=input('dame la coord del vector en y=')
    vector=[vx;vy];
 if vx<30&&vx>0
 if vy<30&&vy>0
    %GRAFICANDO LAS CLASES
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

    
    n = input('Introduce el clasificador a usar: 1=Euclidiano 2=Mahalanobis');
    switch n
        case 1
            disp('Distancia Euclidiana')
            %%% obteniendo parámetros de cada clase
            disp('Media 1\n')
            media1=mean(c1,2)
            disp('Media 2\n')
            media2=mean(c2,2)
            disp('Media 3\n')
            media3=mean(c3,2)
            disp('Media 4\n')
            media4=mean(c4,2)
            disp('Media 5\n')
            media5=mean(c5,2)
            disp('Media 6\n')
            media6=mean(c6,2)

            disp('Distancia 1\n')
            distancia1=norm(media1-vector)
            disp('Distancia 2\n')
            distancia2=norm(media2-vector)
            disp('Distancia 3\n')
            distancia3=norm(media3-vector)
            disp('Distancia 4\n')
            distancia4=norm(media4-vector)
            disp('Distancia 5\n')
            distancia5=norm(media5-vector)
            disp('Distancia 6\n')
            distancia6=norm(media6-vector)

            dist_total=[distancia1,distancia2,distancia3,distancia4,distancia5,distancia6];
            minima=min(min(dist_total));
            encuentra=find(dist_total==minima);
            if(minima>1000)
               fprintf('el vector desconocido esta fuera de rango %d\n',minima)
            else   
                fprintf('el vector desconocido pertenece a la clase %d\n',encuentra)
            end
        case 2
            disp('Distancia por mahalanobis')
            %%% obteniendo parámetros de cada clase
            
            disp('Media 1\n')
            media1=mean(c1,2)
            disp('Media 2\n')
            media2=mean(c2,2)
            disp('Media 3\n')
            media3=mean(c3,2)
            disp('Media 4\n')
            media4=mean(c4,2)
            disp('Media 5\n')
            media5=mean(c5,2)
            disp('Media 6\n')
            media6=mean(c6,2)
            
            disp('Varianza 1\n')
            matrix_cov1=(c1-media1)*(c1-media1)'
            disp('Covarianza 1\n')
            inv_matrix_cov1=inv(matrix_cov1)

            disp('Varianza 2\n')
            matrix_cov2=(c2-media2)*(c2-media2)'
            disp('Covarianza 2\n')
            inv_matrix_cov2=inv(matrix_cov2)

            disp('Varianza 3\n')
            matrix_cov3=(c3-media3)*(c3-media3)'
            disp('Covarianza 3\n')
            inv_matrix_cov3=inv(matrix_cov3)
            
            disp('Varianza 4\n')
            matrix_cov4=(c4-media4)*(c4-media4)'
            disp('Covarianza 4\n')
            inv_matrix_cov4=inv(matrix_cov4)
            
            disp('Varianza 5\n')
            matrix_cov5=(c5-media5)*(c5-media5)'
            disp('Covarianza 5\n')
            inv_matrix_cov5=inv(matrix_cov5)
            
            disp('Varianza 6\n')
            matrix_cov6=(c6-media6)*(c6-media6)'
            disp('Covarianza 6\n')
            inv_matrix_cov6=inv(matrix_cov6)

            disp('Distancia 1\n')
            dist1=(vector-media1)'*inv_matrix_cov1*(vector-media1)
            disp('Distancia 2\n')
            dist2=(vector-media2)'*inv_matrix_cov2*(vector-media2)
            disp('Distancia 3\n')
            dist3=(vector-media3)'*inv_matrix_cov3*(vector-media3)
            disp('Distancia 4\n')
            dist4=(vector-media4)'*inv_matrix_cov4*(vector-media4)
            disp('Distancia 5\n')
            dist5=(vector-media5)'*inv_matrix_cov5*(vector-media5)
            disp('Distancia 6\n')
            dist6=(vector-media6)'*inv_matrix_cov6*(vector-media6)
            
            dist_total=[dist1 dist2 dist3 dist4 dist5 dist6];
            minimo=min(min(dist_total));
            dato1=find(minimo==dist_total);
            
            if(minimo>1000)
               fprintf('el vector desconocido esta fuera de rango %d\n',minimo)
            else   
                fprintf('el vector desconocido pertenece a la clase %d\n',dato1)
            end
        case 3
            disp('3')
        otherwise
            disp('other value')
    end
    count = input('Desea salir? 0=No 1=Si');
 end 
 end
end
sprintf('Hasta pronto')


