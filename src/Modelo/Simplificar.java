/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author carolina
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
    
    public ArrayList<String> estadosIniciales (String[][] matriz, ArrayList<String> a){
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
        boolean salir = false;
        for(int i = 0; i < a.size(); i++){
            String[] partes = a.get(i).split("/");
            for(int j=0; j < partes.length; j++){
                if(sortString(partes[j]).equals(sortString(iniciales))){
                    a.set(i, "*"+a.get(i));
                    salir = true;
                    break;
                }
            }
            if(salir){
                break;
            }
        }
        
        return a;
    }
    
    
    public static String sortString(String inputString) { 
        char tempArray[] = inputString.toCharArray(); 
        Arrays.sort(tempArray); 
        return new String(tempArray); 
    } 
    
}
