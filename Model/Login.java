/*
 * Program: Login.java
 * Author: Alex Gool
 * Description: Class used to model a login object.
 */

package Model;

/**
 * This class models a login.
 *
 * @author Alex Gool
 */
public class Login {
    // variables are instantiated
    private static String username;

    //default constructor
    public Login() {
    }

    /**
     * Login is created.
     *
     * @param username The username.
     */
    public Login(String username) {
        this.username = username;
    }

    /* --------------------- Setters and getters --------------------- */

    /**
     * Set username.
     * @param username User's username as a string.
     */
    public static void setUsername(String username) {
        Login.username = username;
    }
    /**
     * Get username.
     * @return User's username as a string.
     */
    public static String getUsername() {
        return username;
    }
}