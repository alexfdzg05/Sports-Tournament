package upm.app.controler.structures.exceptions;

public class BadRequestException extends RuntimeException{
    private static final String DESCRIPTION = "Instancia invalida";
    public BadRequestException(String message){
        super(DESCRIPTION + ". " + message);
    }
}
