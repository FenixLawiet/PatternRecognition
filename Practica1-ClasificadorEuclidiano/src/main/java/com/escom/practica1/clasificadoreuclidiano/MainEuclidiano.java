/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica1.clasificadoreuclidiano;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Menú principal
*/
public class MainEuclidiano{

    MenuClasificadorEuclidiano menu;
    
    public MainEuclidiano(){
        menu = new MenuClasificadorEuclidiano();
        menu.ClasificadorEuclidiano();
    }

    public static void main(String args[]) {
       
        MainEuclidiano vista = new MainEuclidiano();
    }
} 