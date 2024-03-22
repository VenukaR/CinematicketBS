
public class Person { //the 'Person' class as specified in task 9
    private String name;
    private String surname;
    private String email;

    public Person(String Email, String Name, String Surname) { //setting the constructor for person
        email = Email;
        surname = Surname;
        name = Name;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
//the three above public methods to get the name, surname and email respectively
}
