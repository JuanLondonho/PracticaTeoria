/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import javax.swing.JTable;
import Modelo.*;
import java.util.ArrayList;
/**
 *
 * @author juanclg
 */
public class ControladorAF {

    private static  ControladorAF ctr;
    int numEntradas;
    int numEstados;
    String iniciales;
    JTable tblMatriz;
    String [][] matriz;
    ArrayList<String> a;
    ConvertirADeterminisco m = new ConvertirADeterminisco();
    
    
    Simplificar n = new Simplificar();

    private ControladorAF() {}


    private synchronized static void crearCtr() {
        if (ctr == null) {
            ctr = new ControladorAF();
        }
    }

     public static ControladorAF getCtr() {
        crearCtr();

        return ctr;
    }


    public void entradasTabla(int x, int y){
        numEstados = x;
        numEntradas = y;
        matriz = new String[numEstados+1][numEntradas+2];
    }

    public JTable crearTabla(){

        tblMatriz = new JTable(numEstados+1,numEntradas+2);
        tblMatriz.getTableHeader().setVisible(false);

        return tblMatriz;

    }

     public void crearMatriz(JTable tblAutomata, int operacion){
        for(int i = 0; i <numEstados+1; i++){
           for(int j = 0; j <numEntradas+2 ; j++){
               matriz[i][j]=(String)tblAutomata.getValueAt(i, j);
            }
           
        }
        a = m.ConvertirADeterminisco(matriz, operacion);
        String[][] matrizb = n.matrizDeterministico(a);
        iniciales = n.estadosIniciales(matriz);
        
        //Actualizar la tabla JTable de la vista con matrizb y desabilitarla;
        
//        for(int i =0; i < matrizb.length; i++){
//            for(int j = 0; j < matrizb[0].length; j++){
//                if(i == 0){
//                    table.add(matriz[i][j], i, j);
//                }else{
//                    if(matrizb[i][j].equals(iniciales)){
//                        table.add("*"+matrizb[i][j], i, j);
//                    }else{
//                        table.add(matrizb[i][j], i, j);
//                    }
//                }
//            }
//        }
     }
     
     
     public void simplificarMatriz(){
        System.out.println("SIMPLIFICADA: \n");
        String[][] matrizb = n.matrizDeterministico(a);
        a = n.simplificarAutomata(a, matrizb);
        matrizb = n.convertirMatrizFinal(a, matrizb, matriz);
        iniciales = n.estadosIniciales(matriz);
        iniciales = n.nuevoEstadoInicial(iniciales, matrizb);
        
        //Actualizar la tabla  de la vista con matrizb y desabilitarla;
     }
}
