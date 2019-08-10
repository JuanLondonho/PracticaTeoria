/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author juanclg
 */
public class ConvertirADeterminisco {
    public ArrayList<String> automata = new ArrayList<String>();
    int numEntradas;
    String[][] matriz;
    int estadoActual = -1;
    
    
    public ArrayList<String> ConvertirADeterminisco(String[][] matriz1, int operacion){
        matriz = new String[matriz1.length][matriz1[0].length];
        copiarArray(matriz1);
        numEntradas = matriz[0].length - 2;
        conversionRecursiva(estadosIniciales());
        return automataConEstados(operacion);
        
    }
    
    
    
    
    
    private void conversionRecursiva (String inicial){
        estadoActual++;
        if(!yaExiste(inicial)){
            automata.add(inicial);
            calcularUbicacion();
            for(int i=0; i<numEntradas;i++){
                String nuevoEstado = buscarEstados(i+1, inicial);
                automata.set(estadoActual , automata.get(estadoActual)+"/"+nuevoEstado);
                conversionRecursiva(nuevoEstado);
                estadoActual--;
            }  
        }
    } 
    
    
    private String estadosIniciales (){
        String iniciales = "";
        for(int i = 1; i < matriz.length; i++){
            if(matriz[i][0].indexOf('*') != -1){
                if(iniciales.isEmpty()){
                    iniciales = (matriz[i][0].substring(1));
                }else{
                    iniciales = iniciales.concat("-"+matriz[i][0].substring(1));
                }
                matriz[i][0] = matriz[i][0].substring(1);
            }
        }
        
        return iniciales;
    }
    
    
    private boolean yaExiste(String estado){
        estado = sortString(estado);
        for(int i = 0; i < automata.size(); i++){
            String[] partes = automata.get(i).split("/");
            if(sortString(partes[0]).equals(estado)){
                return true;
            }
        }
        return false;
    }
    
    public static String sortString(String inputString) { 
        char tempArray[] = inputString.toCharArray(); 
        Arrays.sort(tempArray); 
        return new String(tempArray); 
    } 
    
    public String buscarEstados(int entradaActual, String inicial){
        String[] partesEstados = inicial.split("/");
        String[] partes = partesEstados[0].split("-");
        String entradas = "";
        
        
        for(int i = 1; i < matriz.length; i++){
            for(int j = 0; j < partes.length; j++){
                if(matriz[i][0].equals(partes[j])){
                    if(entradas.isEmpty()){
                       entradas = matriz[i][entradaActual];
                    }else{
                       entradas = entradas.concat("-"+matriz[i][entradaActual]);
                    }
                    
                }
            }
        }
        
        return simplificarEntradas(entradas);
    }
    
    
    public String simplificarEntradas(String entrada){
        boolean encontro = false;
        String simplificado = "";
        String [] partes = entrada.split("-");
        for(int i = 0; i< partes.length; i++){
            for(int j = i+1; j< partes.length; j++){
                if(partes[i].equals(partes[j])){
                    encontro = true;
                    break;
                }
            }
            if(!encontro){
                if(simplificado.isEmpty()){
                    simplificado = partes[i];
                }else{
                    simplificado = simplificado.concat("-"+partes[i]);
                }
                
            }else{
                encontro = false;
            }
        }
        return simplificado;
    }
    
    public void calcularUbicacion(){
        if(estadoActual != -1){
            while((automata.get(estadoActual).split("/").length) == (numEntradas+1)){
                estadoActual++;
            }
        }
    }
    
    public ArrayList<String> automataConEstados(int operacion){
        String estadoAceptacion;
        for(int i = 0; i < automata.size(); i++){
            estadoAceptacion = ""+operacion;
            String[] a = automata.get(i).split("/");
            String[] b = a[0].split("-");
            for(int j = 1; j < matriz.length; j++){
                for(int k = 0; k < b.length; k++){
                    
                    if(matriz[j][0].equals(b[k])){
                        if(operacion == 0){
                           if(matriz[j][matriz[0].length-1].equals("1")){
                                estadoAceptacion = "1";
                                break;
                           }
                        }else{
                            estadoAceptacion = "1";
                            if(matriz[j][matriz[0].length-1].equals("0")){
                                estadoAceptacion = "0";
                                break;
                           }
                        }
                            
                    }
                }
                if(operacion == 0 ){
                    if(estadoAceptacion.equals("1")){
                        break;
                    }
                }else{
                    if(estadoAceptacion.equals("0")){
                        break;
                    }
                }
                    
            }
            automata.set(i, automata.get(i).concat("/"+estadoAceptacion));
        }
        return automata;
    }
    
    public void copiarArray(String[][] matriz){
        for(int i = 0; i <matriz.length; i++){
           for(int j = 0; j < matriz[0].length ; j++){
               this.matriz[i][j]= matriz[i][j];
            }
           
        }
    }
}

