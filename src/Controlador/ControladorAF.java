/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juanclg
 */
public class ControladorAF {
    int numEntradas;
    int numEstados;
    JTable matriz;
    
    public ControladorAF(){}
    
    public void entradasTabla(int x, int y){
        numEntradas = x;
        numEstados = y;
        matriz = new JTable(x, y);
    }
    
    public JTable crearMatriz(){
        for(int i = 0; i < numEntradas; i++){ //Limpiar tabla
            for(int j = 0; j < numEstados; j++){
                matriz.setValueAt("1", i, j);
            }
        }
        
        return matriz;
    }
    
    
}
