package Response;
import Model.Event;
public class EventsResponse extends Response{
    Event[] data;

    public EventsResponse(Event[] data) {
        this.data = data;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }
}
