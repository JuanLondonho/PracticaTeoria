/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import javax.swing.JTable;
import Modelo.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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
    String[][] matrizb;
    
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
        tblMatriz.setValueAt("Estados/Entradas", 0, 0);
        tblMatriz.setValueAt("Estados de Aceptacion", 0, numEntradas+1);
        tblMatriz.getTableHeader().setVisible(false);

        return tblMatriz;

    }

   public JTable crearMatriz(JTable tblAutomata, int operacion){
       String dato;
       int cont=0;
        for(int i = 0; i <numEstados+1; i++){
           for(int j = 0; j <numEntradas+2 ; j++){
               
               
               
//                dato=(String)tblAutomata.getValueAt(i, j);
//               if(i!=0 && j==0){ 
//                for(int k=0; k<dato.length();k++){
//                    if(dato.charAt(0)=='*'){
//                        cont=cont++;
//                        if(dato.charAt(j)=='*'&& dato.charAt(j)=='*'){
//                        
//                        }
//                    }  
//                 }
//                if(cont==0){
//                JOptionPane.showMessageDialog(null,"Indique con * al menos un estado inicial" );
//                //return null;   
//                }
//               }else if(j==numEntradas+1){
//                  for(int k=0; k<dato.length();k++){
//                    if(dato.charAt(j)!=0 ||dato.charAt(j)!=1){
//                       JOptionPane.showMessageDialog(null,"Ingrese solo 0 y 1" );
//                       //return null; 
//                        
//                    }  
//                 }
//               
//               }
//               





               
               matriz[i][j]=(String)tblAutomata.getValueAt(i, j);
            }
           
        }
        
        a = m.ConvertirADeterminisco(matriz, operacion);
        String[][] matrizb = n.matrizDeterministico(a);
        iniciales = n.estadosIniciales(matriz);
        System.out.println("NO SIMPLI");
        tblMatriz=new JTable(matrizb.length+1,matrizb[0].length);
        tblMatriz.getTableHeader().setVisible(false);
        tblMatriz.disable();
        //Actualizar la tabla JTable de la vista con matrizb y desabilitarla;  
        
         for(int m=0;m<matrizb.length;m++){
            for(int n=0; n<matrizb[0].length;n++){
                System.out.println("fila::"+m+"col::"+n+"---"+matrizb[m][n]);
                
            }
        }
        
        for(int i =0; i < matrizb.length+1; i++){
            for(int j = 0; j < matrizb[0].length; j++){
              if(i == 0){
                  tblMatriz.setValueAt(matriz[i][j], i, j);
                }else if(i!=0 && j==0){
                    if(matrizb[i-1][j].equals(iniciales)){
                         tblMatriz.setValueAt("*"+matrizb[i-1][j], i, j);
                    }else
                        tblMatriz.setValueAt(matrizb[i-1][j], i, j);
                        
                }else
                        tblMatriz.setValueAt(matrizb[i-1][j], i, j);
                    
            
            }
        }
        return tblMatriz;
     }
     
     
     public JTable simplificarMatriz(){
        System.out.println("SIMPLIFICADA: \n");
        matrizb = n.matrizDeterministico(a);
        a = n.simplificarAutomata(a, matrizb);
        matrizb = n.convertirMatrizFinal(a, matrizb, matriz);
        iniciales = n.estadosIniciales(matriz);
        iniciales = n.nuevoEstadoInicial(iniciales, matrizb);
       
        
        for(int m=0;m<matrizb.length;m++){
            for(int n=0; n<matrizb[0].length;n++){
                System.out.println("fila::"+m+"col::"+n+"---"+matrizb[m][n]);
                
            }
        }
        
        
        tblMatriz=new JTable(matrizb.length,matrizb[0].length);
        tblMatriz.getTableHeader().setVisible(false);
        tblMatriz.disable();
        
        //Actualizar la tabla JTable de la vista con matrizb y desabilitarla;  
        for(int i =0; i < matrizb.length; i++){
            for(int j = 0; j < matrizb[0].length; j++){
              if(i!=0 && j==0){
                    if(matrizb[i][j].equals(iniciales)){
                         tblMatriz.setValueAt("*"+matrizb[i][j], i, j);
                    }else
                        tblMatriz.setValueAt(matrizb[i][j], i, j);
                        
                }else
                        tblMatriz.setValueAt(matrizb[i][j], i, j);
                    
            
            }
        }
        
        return tblMatriz;
        
     }
     
     public void validarDatos(String dato){
     
     
     
     }
     
     public String detectar(String secuencia){
         DetectarSecuencia d = new DetectarSecuencia();
         return d.detectar(matrizb, secuencia, iniciales);
     }
    
}

