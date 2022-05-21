package csv;

import fileTools.FileType;
import fileTools.InvalidFileTypeException;
import table.Table;
import table.TableWithLabels;

import java.io.*;

public class CSV {
    public CSV(){}
    String line= null;
    String [] encabezadosCSV;

    public Table readTable(String nombreFichero) throws FileNotFoundException {
        try {
            BufferedReader bufferedReader = initialize(nombreFichero);
            Table tabla= new Table();
            tabla.addHeader(encabezadosCSV);
            return readDocument(bufferedReader, tabla);
        } catch(InvalidFileTypeException invalidFileTypeException){
            // invalidFileTypeException.printStackTrace();
            // System.out.println("El tipo de fichero esperado era csv!");
        }
        return null;
    }

    public Table readTableWithLabels(String nombreFichero) throws FileNotFoundException {
        try {
            BufferedReader bufferedReader = initialize(nombreFichero);
            Table tabla= new TableWithLabels();
            tabla.addHeader(encabezadosCSV);
            return readDocument(bufferedReader, tabla);
        } catch(InvalidFileTypeException invalidFileTypeException){
            // invalidFileTypeException.printStackTrace();
            // System.out.println("El tipo de fichero esperado era csv!");
        }
        return null;
    }

    private BufferedReader initialize(String nombreFichero) throws FileNotFoundException, InvalidFileTypeException {
        FileType fileType = new FileType(nombreFichero);
        if (fileType.getExtension().compareTo("csv") != 0)
               throw new InvalidFileTypeException("csv", fileType.getExtension());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(nombreFichero));
        try {
            line = bufferedReader.readLine();
            encabezadosCSV = line.split(",");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return bufferedReader;
    }

    private Table readDocument(BufferedReader bufferedReader, Table tabla){
        while(true){
            try {
                if ((line = bufferedReader.readLine()) == null) break;
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            String [] splittedLine = this.line.split(",");
            tabla.addRow(splittedLine);
        }
        return tabla;
    }
}
