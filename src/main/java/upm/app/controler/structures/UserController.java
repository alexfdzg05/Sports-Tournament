package upm.app.controler.structures;

import upm.app.controler.structures.exceptions.BadRequestException;
import upm.app.controler.structures.exceptions.DuplicateException;
import upm.app.controler.structures.exceptions.NotFoundException;
import upm.app.model.Admin;
import upm.app.model.User;
import upm.app.model.mySQL.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private final UserDAO userDAO;
    private User loggedUser;

    public UserController() {
        this.userDAO = new UserDAO();
        this.loggedUser = null;
    }

    public String login(String email, String password) {
        User found = null;

        if (loggedUser == null) {
            int i = 0;
            List<User> listUsers = userDAO.findAll();
            for (User actual : listUsers) {
                if (actual.getEmail().equals(email) && actual.getPassword().equals(password)) {
                    found = actual;
                }
                i++;
            }
        } else {
            throw new DuplicateException("Ya hay un usuario conectado");
        }

        if (found != null) {
            loggedUser = found;
            return "Usuario conectado correctamente, bienvenido " + (loggedUser instanceof Admin ? "admin" : "jugador");
        } else {
            throw new NotFoundException("Email o contraseña incorrecta");
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public String logOut() {
        if (loggedUser != null) {
            loggedUser = null;
            return "Goodbye, " + (loggedUser instanceof Admin ? "admin" : "player");
        } else {
            throw new BadRequestException("No hay un usuario conectado");
        }
    }

    public void addUser(User user){
        userDAO.save(user);
    }
    public void deleteUser(User user){
        userDAO.delete(user);
    }
}
