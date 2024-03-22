public class Ticket { //the 'Ticket' as specified in task 10
    private int row;
    private int seat;
    private int price;
    public Person person;
    //getting the person object to be added in the below constructor

    public Ticket(int row, int seat, int price, Person person) { //the constructor for ticket
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public int getPrice() {
        return price;
    }

    //the three above methods that returns the row, seat and price

    public void print() { //the print method as specified in task 11
        System.out.println("\n-");
        System.out.println("Printing ticket "+this.getSeat()+" of row "+this.getRow()+":-");
        System.out.println("Name of ticket holder: "+this.person.getName());
        System.out.println("Surname of ticket holder: "+this.person.getSurname());
        System.out.println("Email of ticket holder: "+this.person.getEmail());
        System.out.println("Ticket row: "+this.getRow());
        System.out.println("Ticket number (ticket seat): "+this.getSeat());
        System.out.println("Ticket price: £"+this.getPrice());
        System.out.println("-\n");
    }
}
