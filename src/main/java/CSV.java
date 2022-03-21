import java.io.*;

public class CSV {
    public CSV(){}
    String line= null;
    String [] campos;

    public Table readTable(String nombreFichero) throws FileNotFoundException {
        BufferedReader br = initialize(nombreFichero);
        Table tabla= new Table();
        tabla.addHeader(campos);
        return readDocument(br, tabla);
    }

    public Table readTableWithLabels(String nombreFichero) throws FileNotFoundException {
        BufferedReader br = initialize(nombreFichero);
        Table tabla= new TableWithLabels();
        tabla.addHeader(campos);
        return readDocument(br, tabla);
    }

    private BufferedReader initialize(String nombreFichero) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(new File(nombreFichero)));
        try {
            line = br.readLine();
            campos = line.split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return br;
    }

    private Table readDocument(BufferedReader br, Table tabla){
        while(true){
            try {
                if (!((line =br.readLine()) !=null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            String [] num = line.split(",");
            tabla.addRow(num);
        }
        return tabla;
    }
}
