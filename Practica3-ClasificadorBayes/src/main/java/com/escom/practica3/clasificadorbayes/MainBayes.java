/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica3.clasificadorbayes;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Menú principal
*/
public class MainBayes {
 
    MenuClasificadorBayes menu;
    
    public MainBayes(){
        menu = new MenuClasificadorBayes();
        menu.ClasificadorBayes();
    }

    public static void main(String args[]) {
       
        MainBayes vista = new MainBayes();
    }
}
