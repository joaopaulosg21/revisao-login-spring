package revisao.api.crudrevisao.exceptions.user;

public class LoginException extends RuntimeException {

    public LoginException() {
        super("Wrong email or password");
    }
}
