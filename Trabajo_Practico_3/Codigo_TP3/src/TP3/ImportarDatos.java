package TP3;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ImportarDatos {

        public ImportarDatos(){}

    public int[] creaVector(String nombreArchivo, String rutaArchivo , int cant_p  ) {

        int[] vector = new int[cant_p];
        String rutaFinal = rutaArchivo + nombreArchivo;

        try (FileInputStream file = new FileInputStream(new File(rutaFinal))) {
            // leer archivo excel
            HSSFWorkbook archivo = new HSSFWorkbook(file);
            //obtener la hoja que se va leer
            HSSFSheet hoja = archivo.getSheetAt(0);
            //obtener todas las filas de la hoja excel
            Iterator<Row> fila_iterador = hoja.iterator();
            Row fila = fila_iterador.next();

            //se obtiene las celdas por fila
            Iterator<Cell> columna_iterador = fila.cellIterator();
            Cell columna;
            //se recorre cada celda
            int j = 0;
            while(columna_iterador.hasNext()){
                   // se obtiene la celda en específico y se la imprime
            	columna = columna_iterador.next();
                    vector[j] = (int) columna.getNumericCellValue();
                    j ++;
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return vector;
    }

    public int[][] creaMatriz(String nombreArchivo, String rutaArchivo , int cant_t , int cant_p  ) {

        int[][] matriz = new int[cant_p][cant_t];
        String rutaFinal = rutaArchivo + nombreArchivo;

        try (FileInputStream file = new FileInputStream(new File(rutaFinal))) {
            // leer archivo excel
            HSSFWorkbook archivo = new HSSFWorkbook(file);
            //obtener la hoja que se va leer
            HSSFSheet hoja = archivo.getSheetAt(0);
            //obtener todas las filas de la hoja excel
            Iterator<Row> fila_iterador = hoja.iterator();
            Row fila;
            // se recorre cada fila hasta el final

            int i = 0;
            while(fila_iterador.hasNext()){

                fila = fila_iterador.next();
                //se obtiene las celdas por fila
                Iterator<Cell> columna_iterador = fila.cellIterator();
                Cell columna;
                //se recorre cada celda
                int j = 0;
                while(columna_iterador.hasNext()){

                    // se obtiene la celda en específico y se la imprime
                	columna = columna_iterador.next();
                    matriz[i][j] = (int) columna.getNumericCellValue();
                    j ++;
                }
                i++;
            }

        } catch (Exception e) {
            e.getMessage();
        }

        return matriz;
    }
}