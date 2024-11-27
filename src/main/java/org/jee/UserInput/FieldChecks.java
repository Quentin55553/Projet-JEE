package org.jee.UserInput;


/**
 * This class' goal is to manage the user's inputs, not allowing them to enter invalid symbols and values in the app's fields
 */
public class FieldChecks {
    /**
     * This method verifies a 'firstname' field. Only accentuated and unaccentuated letters are allowed
     * @param firstname The String to check
     * @return true if the String is valid
     * @throws IncorrectFirstnameException If the String is invalid
     */
    public static boolean isValidFirstname(String firstname) throws IncorrectFirstnameException {
        String format = "\\p{L}+";
        String newFirstname = firstname.trim();

        if (newFirstname.isEmpty()) {
            throw new IncorrectFirstnameException("Le prénom ne peut pas être vide");
        }

        if (!newFirstname.matches(format)) {
            throw new IncorrectFirstnameException("Le format du prénom est incorrect");
        }

        return true;
    }


    /**
     * This method verifies a 'lastname' field. Only accentuated and unaccentuated letters are allowed
     * @param lastname The String to check
     * @return true if the String is valid
     * @throws IncorrectLastnameException If the String is invalid
     */
    public static boolean isValidLastname(String lastname) throws IncorrectLastnameException {
        String format = "\\p{L}+";
        String newLastname = lastname.trim();

        if (newLastname.isEmpty()) {
            throw new IncorrectLastnameException("Le nom ne peut pas être vide");
        }

        if (!newLastname.matches(format)) {
            throw new IncorrectLastnameException("Le format du nom est incorrect");
        }

        return true;
    }


    /**
     * This method verifies an 'email' field. Needs to be of the form XXXX@YYY.ZZZ
     * @param email The String to check
     * @return true if the String is valid
     * @throws IncorrectEmailException If the String is invalid
     */
    public static boolean isValidEmail(String email) throws IncorrectEmailException {
        String format = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        String newEmail = email.trim();

        if (newEmail.isEmpty()) {
            throw new IncorrectEmailException("L'email ne peut pas être vide");
        }

        if (!newEmail.matches(format)) {
            throw new IncorrectEmailException("Le format de l'adresse email est incorrect");
        }

        return true;
    }
}
