package Response;

public class ClearResponse extends Response {
    String message;

    public ClearResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        message = message;
    }
}
