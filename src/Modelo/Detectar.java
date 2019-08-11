/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;



/**
 *
 * @author Juan Carlos Londoño-Carolina García
 */
public class Detectar {

    public Detectar() {

    }
    
    //recorre la matriz comparando sus caracteres para detectar cuando es una AFND
    public boolean reconocer(String[][] matriz) {
        String estado;
        int cont = 0;
        for (int i = 0; i < matriz[0].length - 1; i++) {
            for (int j = 1; j < matriz.length; j++) {
                estado = matriz[j][i];
                if (i == 0 && estado.startsWith("*")) {
                        cont++;
                        if (cont >= 2) {
                           return true;
                        }
                    
                } else if (i != 0 && estado.length() > 1) {
                    return validarEstadoTransicion(estado);
                }
            }
        }

        return false;
    }

    //valida la existencia de un - entre dos estados para determinar si una entrada puede ir a dos estados diferentes.
    public boolean validarEstadoTransicion(String estado) {
        if ((estado.indexOf("-")) != -1 && ((estado.startsWith("-") 
            && estado.endsWith("-")) == false)) {
             return true;
        }
        
        return false;
    }
    

        

}







