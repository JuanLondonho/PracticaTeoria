/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
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

    public void particionar(ArrayList<String> deterministico) {
        String[]estado;
        if (particiones.isEmpty()) {
                particiones.add("0");
                particiones.add("1");
            for (int i = 0; i < deterministico.size(); i++) {
                estado=deterministico.get(i).split("/");
                    if(estado[estado.length-1].equals(1)){
                        particiones.set(1, particiones.get(1).concat("/"+estado[0]));
                    }else
                        particiones.set(0, particiones.get(0).concat("/"+estado[0]));
            }

            for(int j=1;j>=0;j--){
                if((particiones.get(j).split("/")).length==1){
                    particiones.remove(j);
                }

            }

        } else
            System.out.println("bloop"); ////////////

        }

    }
