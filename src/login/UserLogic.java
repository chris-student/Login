package login;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;

/**
 * Created by Christopher on 26/04/2017.
 */
public class UserLogic {

    public User validateUser(User unverified) {
        User verified = null;
        User candidate = null;

        try {
            UserData data = new UserData();
            candidate = data.retrieveUser(unverified.getName());
            System.out.println("Candidate user:\n" + candidate);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (candidate != null) {

            if (BCrypt.checkpw(unverified.getPass(), candidate.getPass())) {
                verified = candidate;
            }
        }

        return verified;
    }

    public User hashPassword(User newUser) {
        String plaintext = newUser.getPass();

        String salt = BCrypt.gensalt();
        System.out.println("Salt = " + salt);

        String hash = BCrypt.hashpw(plaintext, salt);
        System.out.println("Hash = " + hash);

        if (BCrypt.checkpw(plaintext, hash)) {
            System.out.println("Check pw verified.");
        }
        else {
            System.out.println("Check pw failed.");
        }

        return new User(newUser.getName(), hash);
    }
}
