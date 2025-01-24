package upm.app.controler.structures.exceptions;

public class DuplicateException extends RuntimeException{
    private static final String DESCRIPTION = "Objeto duplicado";
    public DuplicateException(String message){
        super(DESCRIPTION + ". " + message);
    }
}
