/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import javax.swing.JTable;
import Modelo.*;
import java.util.ArrayList;
import javax.swing.JLabel;


/**
 *
 * @author Juan Carlos Londoño-Carolina García
 */
public class ControladorAF {

    //Definición de variables globales e instancias de clases.
    private static ControladorAF ctr;
    int numEntradas;
    int numEstados;
    String iniciales;
    JTable tblMatriz;
    String[][] matriz;
    ArrayList<String> a;
    ConvertirADeterminisco m = new ConvertirADeterminisco();
    String[][] matrizb;
    Detectar dec = new Detectar();
    Simplificar n = new Simplificar();

    private ControladorAF() {
    }
    
    //Creación de una instacia unica de la clase controlador
    private synchronized static void crearCtr() {
        if (ctr == null) {
            ctr = new ControladorAF();
        }
    }

    public static ControladorAF getCtr() {
        crearCtr();

        return ctr;
    }
    
    //Tamaño para la creación de la tabla para la matriz de transición.
    public void entradasTabla(int x, int y) {
        numEstados = x;
        numEntradas = y;
        matriz = new String[numEstados + 1][numEntradas + 2];
    }
    
    //Creación del JTable para el ingreso de los datos para la matriz de transición
    public JTable crearTabla() {

        tblMatriz = new JTable(numEstados + 1, numEntradas + 2);//se crea una instacia de la clase JTable
        tblMatriz.setValueAt("Estados/Entradas", 0, 0);//Se nombra la posición 0,0 de la tabla
        tblMatriz.setValueAt("Estados de Aceptacion", 0, numEntradas + 1);//Se nombra la posición (0,ultima columna) de la tabla
        tblMatriz.getTableHeader().setVisible(false);

        return tblMatriz;

    }

    //Se crea la matriz con la que más adelante se realizarán todas las operaciones de conversión y simplificación
    public JTable crearMatriz(JTable tblAutomata, int operacion,JLabel detectar) {
        for (int i = 0; i < numEstados + 1; i++) {//Recorre el JTable inicial
            for (int j = 0; j < numEntradas + 2; j++) {
                matriz[i][j] = (String) tblAutomata.getValueAt(i, j);//Llenar la matriz con los datos ingresados en el jTable
            }
        }

        if (dec.reconocer(matriz) == false) {
            detectar.setText("Automata deterministico");
        } else {
            detectar.setText("Automata no deterministico");
        }

        a = m.ConvertirADeterminisco(matriz, operacion);//Si el automata es no deterministico se convierte a deterministico y se eliminan estados extraños, si es deterministico se eliminan los estados extraños.
        String[][] matrizb = n.matrizDeterministico(a);//Se pasan los datos retornados en el array a a una matriz para luego mostrarlos en un jTable
        iniciales = n.estadosIniciales(matriz);//despues de eliminar los estados extraños o ser convertido a deterministico se guarda el nuevo estado inicial del automata
        tblMatriz = new JTable(matrizb.length + 1, matrizb[0].length);//se instacia el Jtable para mostrar el automata convertido a determinsitico y sin estados extaños, o solo sin estados extraños en caso de ser deterministico
        tblMatriz.getTableHeader().setVisible(false);
        tblMatriz.disable();//se deshabilita la edición del jTable
        //se Llena el Jtable para mostrar el automata convertido a deterministico y sin estados extaños,y en caso de que hubiera sido deterministico se muestra sin estados extaños 
        for (int i = 0; i < matrizb.length + 1; i++) {
            for (int j = 0; j < matrizb[0].length; j++) {
                if (i == 0) {
                    tblMatriz.setValueAt(matriz[i][j], i, j);
                } else if (i != 0 && j == 0) {
                    if (matrizb[i - 1][j].equals(iniciales)) {
                        tblMatriz.setValueAt("*" + matrizb[i - 1][j], i, j);
                    } else {
                        tblMatriz.setValueAt(matrizb[i - 1][j], i, j);
                    }

                } else {
                    tblMatriz.setValueAt(matrizb[i - 1][j], i, j);
                }

            }
        }
        return tblMatriz;
    }
    
    //Despues de eliminar estados extraños y convertir a deterministico en caso de ser necesario, se procede a simplicar el automata
    public JTable simplificarMatriz() {
        matrizb = n.matrizDeterministico(a);//Se convierte el array a, a matriz para luego proceder a su simplificación.
        a = n.simplificarAutomata(a, matrizb);//se almacena el automata simplificado en un array
        matrizb = n.convertirMatrizFinal(a, matrizb, matriz); //se convierte el array que contiene el automata a una matriz para luego mostrarlo en un Jtable
        iniciales = n.estadosIniciales(matriz);
        iniciales = n.nuevoEstadoInicial(iniciales, matrizb);//se almacena el estado inicial del automata simplificado
        tblMatriz = new JTable(matrizb.length, matrizb[0].length);//se crea un Jtable con el tamaño de la matriz final
        tblMatriz.getTableHeader().setVisible(false);
        tblMatriz.disable();
        //se llena el Jtable con el automata simplificado para ser mostrado
        for (int i = 0; i < matrizb.length; i++) {
            for (int j = 0; j < matrizb[0].length; j++) {
                if (i != 0 && j == 0) {
                    if (matrizb[i][j].equals(iniciales)) {
                        tblMatriz.setValueAt("*" + matrizb[i][j], i, j);
                    } else {
                        tblMatriz.setValueAt(matrizb[i][j], i, j);
                    }

                } else {
                    tblMatriz.setValueAt(matrizb[i][j], i, j);
                }

            }
        }

        return tblMatriz;

    }
    
    //Para evaluar la secuencia en el automata se debe verificar que los caracteres sean validos respecto a las entradas de la matriz de transicion
    public String detectar(String secuencia) {
        DetectarSecuencia d = new DetectarSecuencia();

        String a = d.detectar(matrizb, secuencia, iniciales);
        if (a == null) {
            return "Entrada invalida";
        } else {
            return a;
        }
    }

}
