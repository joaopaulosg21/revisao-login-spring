package revisao.api.crudrevisao.exceptions;

public class LoginException extends RuntimeException {

    public LoginException() {
        super("Wrong email or password");
    }
}
