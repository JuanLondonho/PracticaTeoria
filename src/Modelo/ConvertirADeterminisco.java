/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Juan Carlos Londoño-Carolina García
 */
public class ConvertirADeterminisco {
    
    //Declaración de variables globales
    public ArrayList<String> automata = new ArrayList<String>();
    int numEntradas;
    String[][] matriz;
    int estadoActual = -1;
    
    
    //convierte un automata a determinisco en caso no serlo y si es deterministico 
    //simplemente le elimina estados extraños.
    public ArrayList<String> ConvertirADeterminisco(String[][] matriz1, int operacion){
        matriz = new String[matriz1.length][matriz1[0].length];
        copiarArray(matriz1);
        numEntradas = matriz[0].length - 2;
        conversionRecursiva(estadosIniciales());
        return automataConEstados(operacion);
        
    }
 
    //Este se encarga de hacer el recorrido para encontrar los estados 
    //e ir navegando por ellos y lograr eliminar todos los extraños
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
    
    //Encuentra el estado inicial, en caso de tener varios, los une y lo retorna como
    //el estado inicial
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
    
    //Verifica si al estado al que se realizará una trasición ya existe
    //en el array donde los estamos almacenando.
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
    
    //ordena un string, nos sirve para validar que por ejemplo los estados A-B,
    //sea igual que B-A
    public static String sortString(String inputString) { 
        char tempArray[] = inputString.toCharArray(); 
        Arrays.sort(tempArray); 
        return new String(tempArray); 
    } 
    
    
    //Busca el estado al que se dirige cuando le llega un entrada.
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
    
    //simplifica el estado en casi de que este repetido, por ejemplo al 
    //hacer una fusión de estados, si ambos se dirijen en una entrada
    //al mismo estado entonces que este solo aparezca una vez en su fusión.
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
    
    //se usa para saber que posicion es la que debe llenar actualmente el algoritmo recursivo.
    public void calcularUbicacion(){
        if(estadoActual != -1){
            while((automata.get(estadoActual).split("/").length) == (numEntradas+1)){
                estadoActual++;
            }
        }
    }
    //luego de haber convertido el  estado a deterministico, necesitamos saber cuales
    //son los nuevos estados de aceptación, que a su vez dependen de una operación, si hay multiples
    //estados iniciales.
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
    
    //hace una copia de un una matriz
    public void copiarArray(String[][] matriz){
        for(int i = 0; i <matriz.length; i++){
           for(int j = 0; j < matriz[0].length ; j++){
               this.matriz[i][j]= matriz[i][j];
            }
           
        }
    }
}

