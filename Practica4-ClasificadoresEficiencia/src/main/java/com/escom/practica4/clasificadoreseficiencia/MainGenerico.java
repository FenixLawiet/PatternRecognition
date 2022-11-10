/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escom.practica4.clasificadoreseficiencia;

/*
    Pérez Bravo Isaac Ulises
    Grupo: 3CV12. Pattern recognition
    
    Menú principal
*/
public class MainGenerico {
 
    MenuPrincipal menu;
    
    public MainGenerico(){
        menu = new MenuPrincipal();
        menu.ClasificadorMatrices();
    }

    public static void main(String args[]) {
       
        MainGenerico vista = new MainGenerico();
    }
}
