package fileTools;

public class InvalidFileTypeException extends Exception{
    public InvalidFileTypeException(String expected,String actual){
        super("El tipo de archivo esperado era " + expected + ". El archivo recibido es " + actual);
    }
}
