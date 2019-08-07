/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


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
                 if (i==0 && (estado.indexOf("*")) != -1) {
                    cont = cont++;
                    if (cont >= 2) 
                       return true;
                    
                 }else if(i!=0 && estado.length()>1)
                    return validarEstado(estado);            
                
            }
        }
        
        return false;
    }
    

    public boolean validarEstado(String estado) {
        if ((estado.indexOf("-")) != -1 && ((estado.startsWith("-") 
            && estado.endsWith("-")) == false)) {
            for (int n = 1; n < estado.length() - 1; n++) {
                if (estado.charAt(n) == '-' && estado.charAt(n + 1) == '-') {
                    return false;
                    
                }else 
                    return true;
            }
        }
        
        return false;
    }

}







