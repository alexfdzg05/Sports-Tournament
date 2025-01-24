package upm.app.model;

import javax.persistence.*;

/**
 * Represents an admin user in the application.
 * Extends the {@link User} class with specific admin privileges.
 */

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "email")
public class Admin extends User {

    /**
     * Constructs a new admin user with the given email and password.
     *
     * @param email The email of the admin user. Must be a valid email address.
     * @param password The password of the admin user.
     */
    public Admin(String email, String password) {
        super(email, password, UserRole.ADMIN);
    }


    protected Admin() {
        // Constructor vacío requerido por JPA
    }

}
