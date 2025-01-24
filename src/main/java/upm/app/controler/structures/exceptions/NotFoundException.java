package upm.app.controler.structures.exceptions;

public class NotFoundException extends RuntimeException{
    private static final String DESCRIPTION = "Object not found";
    public NotFoundException(String message){
        super(DESCRIPTION + ". " + message);
    }
}
