package upm.app.controler.command;

public abstract class Command {
    // Metodo que sirve para ejecutar los distintos comandos en sus respectivas clases. Tengo que cambiar el método para que
    // devuelva un String
    public abstract String execute(String[] params);

    // Metodo que comprueba que el metodo que escriben es el mismo del metodo que quieres
    public String testparams(String commandIn, String desiredCommand, int paramsNumberIn, int desiredParams) {
        String string = null;

        if (commandIn.equalsIgnoreCase(desiredCommand)) {
            if (paramsNumberIn == desiredParams) {
                string = "";
            } else {
                throw new IllegalArgumentException("Numero de parametros inorrecto: esperados " + desiredParams + ", encontrados " + paramsNumberIn + "\nEjemplo: " + this);
            }
        }
        return string;
    }

    public String testParamsMin(String commandIn, String desiredCommand, int paramsNumberIn, int desiredParams) {
        String string = null;
        if (commandIn.equalsIgnoreCase(desiredCommand)) {
            if (paramsNumberIn >= desiredParams) {
                string = "";
            } else {
                throw new IllegalArgumentException("Numero de parametros inorrecto: esperados " + desiredParams + ", encontrados " + paramsNumberIn + "\nEjemplo: " + this);
            }
        }
        return string;
    }

    // Metdodo que mostrará por pantalla como se debería poner el metodo. Servirá para hacer el metodo help de manera eficiente
    public abstract String toString();
}
