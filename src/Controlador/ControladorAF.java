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
    JTable tblMatriz;

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
        numEntradas = x;
        numEstados = y;
    }

    public JTable crearTabla(){

        tblMatriz = new JTable(numEntradas+1,numEstados+2);
        tblMatriz.getTableHeader().setVisible(false);

        return tblMatriz;

    }

     public void crearMatriz(JTable tblAutomata, int operacion){
        String [][] matriz = new String[numEntradas+1][numEstados+2];

        for(int i = 0; i <numEntradas+1; i++){
           for(int j = 0; j <numEstados+2 ; j++){
               matriz[i][j]=(String)tblAutomata.getValueAt(i, j);
            }
           
        }


        ConvertirADeterminisco m = new ConvertirADeterminisco();
        
        ArrayList<String> a = m.ConvertirADeterminisco(matriz, operacion);
        
        
        System.out.println("SIN SIMPLIFICAR: \n");
        
        for(int i = 0; i<a.size(); i++){
            System.out.println(a.get(i));
        }
        
        
        Simplificar n = new Simplificar();
        
        System.out.println("SIMPLIFICADA: \n");
        a = n.simplificarAutomata(a, n.matrizDeterministico(a));
        a = n.estadosIniciales(matriz, a);
        
        for(int i = 0; i<a.size(); i++){
            System.out.println(a.get(i));
        }
        
        
        
     }
}
