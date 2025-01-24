package upm.app.controler.command.exceptions;

public class RoleException extends RuntimeException{
    private static final String DESCRIPTION = "Nivel de permisos insuficiente";
    public RoleException(String message){
        super(DESCRIPTION + ". " + message);
    }
}
