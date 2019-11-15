package Response;

public class LoadResponse extends Response{
    String message;
    Integer x, y, z;

    public LoadResponse(String message, Integer x, Integer y, Integer z) {
        this.message = message;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }
}
