package Users;

/**
 * Created by Ao on 2017-10-15.
 */

public interface User {

    public enum UserType{
        STUDENT, TA, PROFESSOR
    }
    UserType getUserType();
    String getToken();
    String getName();
    String getUsername();
    String getEmail();
}
