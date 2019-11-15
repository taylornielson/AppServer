package Response;
import Model.Person;
public class PersonsResponse extends Response{
    Person[] data;

    public PersonsResponse(Person[] data){
        this.data = data;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }
}
