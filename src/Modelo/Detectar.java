/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javax.swing.JOptionPane;


/**
 *
 * @author juanclg
 */
public class Detectar {

    public Detectar() {

    }

    public boolean reconocer(String[][] matriz) {
        String estado;
        int cont = 0;
        for (int i = 0; i < matriz[0].length-1; i++) {
            for (int j = 1; j < matriz.length; j++) {
                estado = matriz[j][i];
                if (i==0 && (estado.startsWith("*")== true)) {
                       if(validarEstadoIncial(estado)==true){
                           cont = cont++;
                            if (cont >= 2)
                                return true;         
                       }              
                }
//                else if(i==0 && (estado.startsWith("*")== false)){
//                    JOptionPane.showMessageDialog(null, "Señale con *al menos uno de los estados para indicar el estado inicial");             
//                
//                }
                else if(i!=0 && estado.length()>1){
                    return validarEstadoTransicion(estado);}             
            }
        }

        return false;
    }

    public boolean validarEstadoTransicion(String estado) {
        if ((estado.indexOf("-")) != -1 && ((estado.startsWith("-") 
            && estado.endsWith("-")) == false)) {
            for (int n = 1; n < estado.length() ; n++) {
                if (estado.startsWith("*")== true||estado.charAt(n)=='/'||
                   (estado.charAt(n) == '-' && estado.charAt(n + 1) == '-')) {
                    return false; 
                }else 
                    return true;
            }
        }
        
        return false;
    }
    
    public boolean validarEstadoIncial(String estado){ 
        for (int n = 1; n < estado.length() ; n++) { 
            if (estado.charAt(n) == '-'||estado.charAt(n)=='/'||
                   estado.charAt(1) == '*'){
                return false;    
            }        
        }
     return true;
    }
    
//    public boolean validarEstadoAceptacion(String[][] matriz){
//        
//        for(int i=0;i<matriz.length;i++){
//            if(matriz[i][matriz[i].length]!="0"||matriz[i][matriz[i].length]!="1"){
//                return false;
//            }
//        }
//        
//    return true;
//    }
//    
    
    

}







