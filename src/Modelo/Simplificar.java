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
public class Simplificar {

    ArrayList<String> particiones = new ArrayList<String>();

    public Simplificar() {
        
    }

    public String[][] matrizDeterministico(ArrayList<String> deterministico) {
        String[] estado = deterministico.get(0).split("/");
        String matrizDeterministico[][] = new String[deterministico.size()][estado.length];

        for (int i = 0; i < deterministico.size(); i++) {
            estado = (deterministico.get(i)).split("/");
            for (int j = 0; j < estado.length; j++) {
                matrizDeterministico[i][j] = estado[j];
            }
        }
        return matrizDeterministico;

    }

    public ArrayList<String> particionar(ArrayList<String> deterministico) {
        String[]estado;
        particiones.add("0");
        particiones.add("1");
        for (int i = 0; i < deterministico.size(); i++) {
            estado=deterministico.get(i).split("/");
                if(estado[estado.length-1].equals("1")){
                    particiones.set(1, particiones.get(1).concat("/"+estado[0]));
                }else
                    particiones.set(0, particiones.get(0).concat("/"+estado[0]));
        }

        for(int j=1;j>=0;j--){
            if((particiones.get(j).split("/")).length==1){
                particiones.remove(j);
            }else{
                particiones.set(j, particiones.get(j).substring(2));
            }
        }
        return particiones;
    }
    
    public ArrayList<String> simplificarAutomata(ArrayList<String> a, String[][] matriz){
        a = particionar(a);
        ArrayList<String> ayuda = new ArrayList<>();
        boolean bandera = false;
        boolean bandera2 = false;
        for(int i = 0; i < matriz[0].length-2; i++){
            for(int j = 0; j < a.size(); j++){
                String[] partes = a.get(j).split("/");
                for(int k = 0; k < partes.length; k++){
                    for(int l = 0; l < matriz.length; l++){
                        if(sortString(matriz[l][0]).equals(sortString(partes[k]))){
                            for(int m = 0; m < a.size(); m++){
                                String[] partes1 = a.get(m).split("/");
                                for(int n = 0; n < partes1.length; n++){
                                    if(sortString(matriz[l][i+1]).equals(sortString(partes1[n]))){
                                        if(ayuda.isEmpty()){
                                            ayuda.add(m+"/"+partes[k]);
                                            bandera = true;
                                        }else{
                                            for(int z = 0; z < ayuda.size(); z++){
                                                if(ayuda.get(z).split("/")[0].equals(String.valueOf(m))){
                                                    ayuda.set(z, ayuda.get(z).concat("/"+partes[k]));
                                                    bandera2 = true;
                                                    break;
                                                }
                                            }
                                            if(!bandera2){
                                                ayuda.add(m+"/"+partes[k]);
                                            }
                                            bandera = true;
                                        }
                                        break;
                                    }
                                    if(bandera){
                                        break;
                                    }
                                }
                                if(bandera){
                                    break;
                                }
                            }
                        }
                        if(bandera){
                            break;
                        }
                    }
                    bandera = false;
                    bandera2 = false;
                }
               if(ayuda.size() > 1){
                   a.set(j, ayuda.get(0).substring(2));
                   for(int x = 1; x < ayuda.size(); x++){
                       a.add(ayuda.get(x).substring(2));
                       
                    }
                    i = -1;
                    ayuda = new ArrayList<>();
                    break;
               }
               ayuda = new ArrayList<>();
            } 
        }
        for(int i = 0; i < a.size(); i++){
           String s = a.get(i).split("/")[0];
           for(int j = 0; j < matriz.length; j++){
               if(sortString(matriz[j][0]).equals(sortString(s))){
                   a.set(i, a.get(i).concat("/"+matriz[j][matriz[0].length-1]));
               }
           }
        }
        return a;
    }
    
    public String estadosIniciales (String[][] matriz){
        String iniciales = "";
        for(int i = 1; i < matriz.length; i++){
            if(matriz[i][0].indexOf('*') != -1){
                if(iniciales.isEmpty()){
                    iniciales = (matriz[i][0].substring(1));
                }else{
                    iniciales = iniciales.concat("-"+matriz[i][0].substring(1));
                }
                matriz[i][0] = matriz[i][0];
            }
        }   
    return iniciales;
    }
    
    
    public static String sortString(String inputString) { 
        char tempArray[] = inputString.toCharArray(); 
        Arrays.sort(tempArray); 
        return new String(tempArray); 
    } 
    
    public String[][] convertirMatrizFinal(ArrayList<String> a, String[][] matriz, String[][] b){
        boolean bandera= false;
        String[][] matrizFinal = new String[a.size()+1][matriz[0].length];
        for(int i = 0; i < b[0].length; i++){
            matrizFinal[0][i] = b[0][i];
        }
        for(int i=0; i<a.size(); i++){
            String parte = a.get(i).split("/")[0];
            for(int k = 0; k < matriz.length; k++){
                if(sortString(matriz[k][0]).equals(sortString(parte))){
                    for(int m = 1; m < matriz[0].length-1; m++){
                        for(int n = 0; n < a.size(); n++){
                            String[] partes = a.get(n).split("/");
                            for(int x = 0; x < partes.length-1; x++){
                                if(sortString(matriz[k][m]).equals(sortString(partes[x]))){
                                    matrizFinal[i+1][0] = a.get(i).substring(0, a.get(i).length()-2);
                                    matrizFinal[i+1][m] = a.get(n).substring(0, a.get(n).length()-2);
                                    bandera = true;
                                    break;
                                }else{
                                    bandera = false;
                                }
                            }
                            
                            if(bandera){
                                break;
                            }
                            
                        }
                    }
                    matrizFinal[i+1][matriz[0].length-1] = a.get(i).split("/")[a.get(i).split("/").length-1];
                }
                if(bandera){
                    break;
                }
            }
            
            bandera = false;
        }
        return matrizFinal;
    }
    
    public String nuevoEstadoInicial(String iniciales, String[][] a){
        for(int i = 1; i < a.length; i++){
            String[] partes = a[i][0].split("/");
            for(int j = 0; j < partes.length; j++){
                if(sortString(partes[j]).equals(sortString(iniciales))){
                    return a[i][0];
                }
            }
          
        }
        return null;
    }
}
