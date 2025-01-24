package upm.app.model.exceptions;

public class InvalidAtributeException extends IllegalArgumentException{
    private static final String DESCRIPTION = "Atributo invalido. El atributo no se encuentra dentro de los limites";
    public InvalidAtributeException(String message){
        super(DESCRIPTION + ". " + message);
    }
}
