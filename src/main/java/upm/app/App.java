package upm.app;

import upm.app.console.CLI;
import upm.app.controler.Seeder;
import upm.app.controler.command.Command;
import upm.app.controler.structures.*;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.*;

/**
 * Main class of the App
 *
 * @author David Peláez de la Osa
 * @author Óscar Arranz Álvez
 * @author David Gérard Méndez
 * @author Alejandro Fernández Guerrero
 */
public class App {
    public static void main(String[] args) {
        DependencyInjector.getDependencyInjector().run();
    }
}
