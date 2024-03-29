/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Arrays;

/**
 *
 * @author Juan Carlos Londoño-Carolina García
 */
public class DetectarSecuencia {
    
    public DetectarSecuencia(){
        
    }
    
    //Recibe una secuencia y realiza las transiciones entre estados y retorna 
    //el estado final y el estado de aceptación.
    public String detectar(String[][] a, String secuencia, String inicial){
        boolean bandera = false;
        String estadoActual = inicial;
        for(int i = 0; i < secuencia.length(); i++){
            char c = secuencia.charAt(i);
            for(int j = 1; j < a.length; j++){
                if(sortString(a[j][0]).equals(sortString(estadoActual))){
                    for(int k = 1; k < a[0].length-1; k++){
                        if(a[0][k].equals(""+c)){
                            estadoActual= a[j][k];
                            bandera= true;
                            break;
                        }
                    }
                    if(!bandera){
                        return null;
                    }
                }
                if(bandera){
                    break;
                }
                
            }
            bandera = false;
        }
        for(int i = 1; i < a.length; i++){
            if(sortString(a[i][0]).equals(sortString(estadoActual))){
                estadoActual = "Estado de finalizacion: "+estadoActual+" - Estado aceptacion: "+a[i][a[0].length-1];
            }
        }
        return estadoActual;
    }
    
    
    //Ordena un String.
    public static String sortString(String inputString) { 
        char tempArray[] = inputString.toCharArray(); 
        Arrays.sort(tempArray); 
        return new String(tempArray); 
    } 
}
