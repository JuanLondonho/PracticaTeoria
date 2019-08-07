/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javax.swing.JOptionPane;

/**
 *
 * @author carolina
 */
public class Simplificar {
    
    int contA=0;
    int contN=0;
    

    public Simplificar() {
    }
    
    public void tamaño(String [][]matriz){
         for(int i=0;i<matriz.length;i++){
            int j=matriz[i].length;
            String estado=matriz[i][j];  
              if(estado.compareTo("0")==0){
                  contA=contA++;
              }else if(estado.compareTo("1")==0){
                  contN=contN++;
              }else
                JOptionPane.showMessageDialog(null, "Ingrese 1 si el estado es de aceptacion o 0 si no lo es");
        }
    
    }
    
    public void separarEstado(String[][] matriz) {
        String[] aceptacion = new String[contA];
        String[] noAceptacion = new String[contN];
        int cont1 = 0;
        int cont2 = 0;

        for (int i = 1; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                String estado = matriz[i][matriz[i].length];
                if (estado.compareTo("0") == 0) {
                    noAceptacion[cont1] = matriz[1][j];
                    cont1++;

                } else if (estado.compareTo("1") == 0) {
                    aceptacion[cont2] = matriz[1][j];
                    cont2++;

                } else {
                   JOptionPane.showMessageDialog(null, "Ingrese 1 si el estado es de aceptacion o 0 si no lo es");
                }

            }
        }
    }
    
    
}
