/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica2.clasificadormahalanobis;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Menú principal
*/
public class MainMahalanobis {
 
    MenuClasificadorMahalanobis menu;
    
    public MainMahalanobis(){
        menu = new MenuClasificadorMahalanobis();
        menu.ClasificadorMahalanobis();
    }

    public static void main(String args[]) {
       
        MainMahalanobis vista = new MainMahalanobis();
    }
}
