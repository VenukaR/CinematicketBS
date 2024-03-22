import java.io.FileNotFoundException; //For the file not found exception
import java.io.IOException; //for IO exception
import java.util.ArrayList; //to make the dynamic arrays
import java.util.Scanner; //the utility needed to take in user inputs
import java.io.File; //the utility needed for the file handling
import java.io.FileWriter; //the utility needed to write to the file
import java.util.InputMismatchException; //for the input mismatch exception

public class Theatre { //the class that contains the MAIN method as specified in task 1
    static final int row1_price = 20;
    static final int row2_price = 15;
    static final int row3_price = 10;
    //the prices for each row displayed above. The closer to the stage, the more expensive it is. It's constant as there is no reason to change it
    static int[] row1 = new int[12]; //initialising row 1 with all the seats vacant (at 0)
    static int[] row2 = new int[16]; //initialising row 2 with all the seats vacant (at 0)
    static int[] row3 = new int[20]; //initialising row 3 with all the seats vacant (at 0)
    static Scanner input = new Scanner(System.in); //creating the scanner object
    static int row_num; //initialising the global row_num variable to be used in the buy_ticket method
    static int seat_num; //initialising the global seat_num variable to be used in the buy_ticket method
    static int option; //initialising the global option variable to be used in the MENU
    static File ticket_file = new File("row_info.txt");
    //creating the global file object to be used in different methods
    static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    static ArrayList<String> ticket_names = new ArrayList<String>();
    static ArrayList<String> ticket_surnames = new ArrayList<String>();
    static ArrayList<String> ticket_emails = new ArrayList<String>();
    static ArrayList<String> ticket_rows = new ArrayList<String>();
    static ArrayList<String> ticket_seats = new ArrayList<String>();
    //creating the above 6 global dynamic arrays to be used

    public static void main(String[] args) {
        while (true) { //loop will run forever until it meets a break function

            System.out.println();
            System.out.println("Welcome to the New Theatre");//The beginning message
            System.out.println("-------------------------------------------------");
            System.out.println("MENU");
            System.out.println("1.\tBuy a ticket");
            System.out.println("2.\tPrint seating area");
            System.out.println("3.\tCancel ticket");
            System.out.println("4.\tList available seats");
            System.out.println("5.\tSave to file");
            System.out.println("6.\tLoad from file");
            System.out.println("7.\tPrint ticket information and total price");
            System.out.println("8.\tSort tickets by price");
            System.out.println("\t0.\tQuit");
            //the different options that can be used
            System.out.println("-------------------------------------------------");
            //menu section as specified in task 2

            option = input_taker("Please enter what option you want\t: ", 8, 0,"Error. Please enter an integer between 0 and 8");
            //using the input_taker method

            if (option == 0) {
                System.out.println("\n" +
                        "QUITTING... THANK YOU FOR YOU USING THIS PROGRAM!"); //thanks the user
                break; //breaks out of the outer main loop, ending the program
            } else if (option == 1) {
                buy_ticket(); //calls the buy_ticket method as specified in task 3
            } else if (option == 2) {
                print_seating_area(); //calls the print_seating_area method as specified in task 4
            } else if (option == 3) {
                cancel_ticket(); //calls the cancel_ticket method as specified in task 5
            } else if (option == 4) {
                show_available(); //calls the show_available method as specified in task 4
            } else if (option == 5) {
                save(); //calls the save method as specified in task 7
            } else if (option == 6) {
                load(); //calls the load method as specified in task 8
            } else if (option == 7) {
                show_tickets_info(); //calls the show_tickets_info method as specified in task 13
            } else { //the only other option it can be is 8
                sort_tickets();
                print_tickets();
                //calls the sort_tickets method and then the print_tickets method as specified in task 14
            }
        }
    }

    public static void sort_tickets() { //the sort_tickets method as specified in task 14
        int length = 0; //initializes the length variable, the index is this value -1
        for (Ticket z:tickets) {
            length ++; //for each element in tickets, length increases by 1
        }//the bubble sort method is used here
        int passes = length - 2; //the number of passes is calculated here from the length
        Ticket temporary_value; //initializes a temporary variable used in the bubble sort method
        boolean exchange = true; //shows if there is an exchange happening
        while (exchange) { //while true, runs repeatedly until there is a break function or exchange=false
            exchange = false; //is false until set to true depending on the condition
            for (int f= 0; f<= passes; f++) {
                if (tickets.get(f).getPrice() > tickets.get(f+ 1).getPrice()) { //if a value in the array is more than the value after it
                    temporary_value = tickets.get(f);
                    tickets.set(f, tickets.get(f+1));
                    tickets.set(f+1,temporary_value);
                    //here, using the temporary variable the two values swap positions in the array
                    exchange = true;
                    //exchange is set to true if the if-condition is met
                }
            }
            passes--; //decreases the number of passes by 1
        }
    }

    public static void print_tickets() { //calls the other print method that was mentioned in task 11
        for (Ticket b:tickets) { //calls the print method for each value in the tickets array
            b.print(); //the print method specified in task 11 and defined in the Ticket class
        }
    }

    public static void show_tickets_info() { //the show_tickets_info method as specified in task 13
        System.out.println("SHOWING TICKETS INFORMATION");
        System.out.println("Row 1 ticket price : £"+row1_price);
        System.out.println("Row 2 ticket price : £"+row2_price);
        System.out.println("Row 3 ticket price : £"+row3_price);
        System.out.println();
        //prints the ticket price depending on the row, the closer it is to the stage the more expensive it is
        print_tickets();
        //prints the ticket information
        int row1_tickets = 0;
        int row2_tickets = 0;
        int row3_tickets = 0;
        //initializes the above variable
        System.out.println();

        for (int x : row1) {
            if (x == 1) {
                row1_tickets++;
            }
        }
        for (int y : row2) {
            if (y == 1) {
                row2_tickets++;
            }
        }
        for (int z : row3) {
            if (z == 1) {
                row3_tickets++;
            }
        }
        //if there is a ticket bought in each row the row counter increases by 1
        int total_price = (row1_tickets*row1_price) + (row2_tickets*row2_price) + (row3_tickets*row3_price);
        //calculates the total price
        System.out.println("Total price of all tickets is £"+total_price); //prints the total price
    }

    public static void save() { //the save method as specified in task 7
        try {
            System.out.println("\nSAVING TO EXTERNAL FILE...\n");
            FileWriter ticket_writer = new FileWriter("row_info.txt");
            //creates the file_writer objects
            if (ticket_file.exists()) {
                ticket_file.delete();
                ticket_file.createNewFile();
            }
            /*
            if the file exists it is deleted.
            So that entirely new data can be written
            on a now new created file
            */
            else {
                ticket_file.createNewFile();//if the file doesn't exist it is created
            }
            for (int p : row1) {
                if (p == 1) {
                    ticket_writer.write("1");
                } else { //the only other value it can be is 0
                    ticket_writer.write("0");
                }
            }
            ticket_writer.write("\n"); //to go to a new line
            for (int p : row2) {
                if (p == 1) {
                    ticket_writer.write("1");
                } else { //the only other value it can be is 0
                    ticket_writer.write("0");
                }
            }
            ticket_writer.write("\n"); //to go to a new line
            for (int p : row3) {
                if (p == 1) {
                    ticket_writer.write("1");
                } else { //the only other value it can be is 0
                    ticket_writer.write("0");
                }
            }
            //the above 3 for loops write on a line one after the other with 1s or 0s to represent and empty seat
            ticket_writer.write("\n"); //to go to a new line
            for (Ticket b:tickets) {
                ticket_writer.write(b.person.getName());
                ticket_writer.write(" ");
            } //the above for loops get all the names from the ticket array and writes it onto the file, seperated by a single space
            ticket_writer.write("\n"); //to go to a new line
            for (Ticket b:tickets) {
                ticket_writer.write(b.person.getSurname());
                ticket_writer.write(" ");
            } //the above for loops get all the surnames from the ticket array and writes it onto the file, seperated by a single space
            ticket_writer.write("\n"); //to go to a new line
            for (Ticket b:tickets) {
                ticket_writer.write(b.person.getEmail());
                ticket_writer.write(" ");
            } //the above for loops get all the emails from the ticket array and writes it onto the file, seperated by a single space
            ticket_writer.write("\n"); //to go to a new line
            for (Ticket b:tickets) {
                ticket_writer.write(Integer.toString(b.getRow()));
                ticket_writer.write(" ");
            } //the above for loops get all the rows (converted to string first) from the ticket array and writes it onto the file, seperated by a single space
            ticket_writer.write("\n"); //to go to a new line
            for (Ticket b:tickets) {
                ticket_writer.write(Integer.toString(b.getSeat()));
                ticket_writer.write(" ");
            } //the above for loops get all the seats (converted to string first) from the ticket array and writes it onto the file, seperated by a single space
            ticket_writer.close();
            System.out.println("SAVED TO FILE");
        }
        catch (IOException e) {
            System.out.println("ERROR. Please try again");
        } //catches any IO exception
    }

    public static void load() { //the load method from task 8
        try {
            System.out.println("\nLOADING FROM FILE...\n");
            Scanner ticket_reader = new Scanner(ticket_file); //creates the ticket reader that reads from the file
            int count = 1;
            while (ticket_reader.hasNextLine()) { //while the reader as the next line
                if (count == 1) {
                    String ticket_text = ticket_reader.nextLine();
                    for (int t = 0; t < row1.length; t++) {
                        row1[t] = Character.getNumericValue(ticket_text.charAt(t));
                    }
                } else if (count == 2) {
                    String ticket_text = ticket_reader.nextLine();
                    for (int u = 0; u < row2.length; u++) {
                        row2[u] = Character.getNumericValue(ticket_text.charAt(u));
                    }
                } else if (count == 3) {
                    String ticket_text = ticket_reader.nextLine();
                    for (int v = 0; v < row3.length; v++) {
                        row3[v] = Character.getNumericValue(ticket_text.charAt(v));
                    }
                    /*
                    depending on which value count is (always starting from 1) the if options happen.
                    The above 3 options take strings from the first 3 rows respectively
                    and initializes row array valuable to the values in the string
                     */
                } else if (count == 4) {
                    String ticket_text = ticket_reader.nextLine();
                    String[] names = ticket_text.split(" ");
                    for (String n:names) {
                        ticket_names.add(n);
                    }
                } else if (count == 5) {
                    String ticket_text = ticket_reader.nextLine();
                    String[] surnames = ticket_text.split(" ");
                    for (String n:surnames) {
                        ticket_surnames.add(n);
                    }
                } else if (count == 6){
                    String ticket_text = ticket_reader.nextLine();
                    String[] emails = ticket_text.split(" ");
                    for (String n:emails) {
                        ticket_emails.add(n);
                    }
                } else if (count == 7) {
                    String ticket_text = ticket_reader.nextLine();
                    String[] rows = ticket_text.split(" ");
                    for (String n:rows) {
                        ticket_rows.add(n);
                    }
                } else { //the only other option it can be is 8
                    String ticket_text = ticket_reader.nextLine();
                    String[] seats = ticket_text.split(" ");
                    for (String n:seats) {
                        ticket_seats.add(n);
                    }
                    /*
                    the above 5 options take the last 5 rows respectively and split the text strings into string arrays
                    and assign them temporarily to 5 dynamic arrays
                     */
                }
                if (count >= 8) {
                    break;
                } //if the count is more than 8
                count++; //increases the count by 1
            }
            ticket_reader.close(); //closes the ticket reader
            int length = 0;
            for (String l:ticket_names) {
                length++; /*
                since the length of the ticket names, surnames, emails,rows, seats etc. are all the same,
                anyone one of them can be used to get the length of the dynamic array
                           */
            }
            tickets.clear(); //removes all the old values from ticket to take all the new values
            for (int index=0;index<length;index++) {
                int price = 0;
                if (ticket_rows.get(index).equals("1")) {
                    price = row1_price;
                } else if (ticket_rows.get(index).equals("2")) {
                    price = row2_price;
                } else { //the only other value it can be is "3"
                    price = row3_price;
                }   //gets the price depending on the rows
                Person person = new Person(ticket_emails.get(index), ticket_names.get(index), ticket_surnames.get(index));
                //sets the data for person
                Ticket ticket = new Ticket(Integer.parseInt(ticket_rows.get(index)), Integer.parseInt(ticket_seats.get(index)), price, person);
                //sets the data for ticket with row and seat converted to integers before doing so
                tickets.add(ticket); //adds the ticket to the now empty tickets array
            }
            ticket_names.clear();
            ticket_surnames.clear();
            ticket_emails.clear();
            ticket_rows.clear();
            ticket_seats.clear();
            //clears the above temporary variables to be used again good as new if load is called again
            System.out.println("FILE LOADED"); //informs the user that the process is complete
        } catch (FileNotFoundException e) {
            System.out.println("ERROR. File not found. Please try again");
            //handles if the file is not there or deleted
        }
    }

    public static void seat_printer(int[] r) {
        //seat printer method to be used in the print_seating_area method
        for (int i = 0; i < r.length; i++) {
            if (i == (r.length/2)) { //if the index is in the middle of the length....
                System.out.print(" ");
            } //prints a space for an aisle if in the middle of the row
            if (r[i] == 1) {
                System.out.print("X"); //prints 'X' if occupied
            } else { //the only other value it can be is 0
                System.out.print("0"); //prints 'O' if occupied
            }
        }
    }

    public static void print_seating_area() { //this is the print_seating_area method, called when the user selects '2'
        System.out.println(); //prints the area along with information
        System.out.println("SEATING AREA\n");
        System.out.println("'X' represents a seat that has been bought, 'O' represents a seat that is vacant\n");
        System.out.println("     ***********");
        System.out.println("     *  STAGE  *");
        System.out.print("     ***********");
        System.out.println();
        System.out.print("    "); //this space is to align the coming lines to the centre
        //these above lines print the stage and above notice
        seat_printer(row1); //calls the seat_printer method for row1
        System.out.println(); //an empty line to make a gap between the rows
        System.out.print("  "); //this space is to align the coming lines to the centre
        seat_printer(row2); //calls the seat_printer method for row2
        System.out.println();
        seat_printer(row3); //calls the seat_printer method for row3
        System.out.println(); //extra line to make everything look neat and tidy for the user
    }

    public static void remove_ticket() { //used to remove tickets from the tickets array
        int index = 0;
        for (Ticket a:tickets) {
            index++; //increases by one to get the index
            if ((a.getRow() == row_num) && (a.getSeat() == seat_num)) {
                tickets.remove(index - 1);//removes the desired ticket
                break; //quickly breaks to prevent a ConcurrentModificationException
            }
        }
    }

    public static int input_taker(String prompt, int upper_range, int lower_range, String error_message) {
        //a method to take in an input
        int answer;
        while (true) {
            try {
                System.out.print(prompt);
                answer = input.nextInt();
            }
            catch (InputMismatchException exception) {
                System.out.println("Error. You should enter an Integer. Please try again.");
                input.nextLine();
                continue;
                //to prevent the user entering anything other than an integer
            }
            if (answer > upper_range || answer < lower_range) {
                System.out.println(error_message);
                System.out.println();
                continue;
                //to prevent the user entering an integer out of the desired range
            }
            break; //break if correct
        }
        return answer;
        //return the answer
    }

    public static void cancel_ticket() { //the cancel_ticket method, specified in task 5
        System.out.println("CANCELLING TICKET...");
        while (true) {
            row_num = input_taker("Please enter the row number\t: ", 3, 1,"Error. There are only three rows, please enter a number between 1 and 3");
            System.out.println();
            if (row_num == 1) {
                seat_num = input_taker("Please enter your desired seat number\t: ", 12, 1,"Error. The seat number you entered is out of bounds. Please enter a seat number between 1 and 12");
                if (row1[(seat_num - 1)] == 0) {
                    System.out.print("Error. This seat is already vacant. Please try again\n");
                    break; //to prevent the user entering a seat number already vacant (can't be cancelled)
                }
                remove_ticket(); //calls the remove_ticket function
                row1[(seat_num - 1)] = 0; //removes the ticket from the array
            } else if (row_num == 2) {
                seat_num = input_taker("Please enter your desired seat number\t: ", 16, 1,"Error. The seat number you entered is out of bounds. Please enter a seat number between 1 and 16");
                if (row2[(seat_num - 1)] == 0) {
                    System.out.print("Error. This seat is already vacant. Please try again\n");
                    break; //to prevent the user entering a seat number already vacant (can't be cancelled)
                }
                remove_ticket(); //calls the remove_ticket function
                row2[(seat_num - 1)] = 0; //removes the ticket from the array
            } else { //the only other row it can be is 3
                seat_num = input_taker("Please enter your desired seat number\t: ", 20, 1,"Error. The seat number you entered is out of bounds. Please enter a seat number between 1 and 20");
                if (row3[(seat_num - 1)] == 0) {
                    System.out.print("Error. This seat is already vacant. Please try again\n");
                    break; //to prevent the user entering a seat number already vacant (can't be cancelled)
                }
                remove_ticket(); //calls the remove_ticket function
                row3[(seat_num - 1)] = 0; //removes the ticket from the array
            }
            System.out.println("TICKET CANCELLED"); //prints that the ticket has been cancelled
            break; //breaks out of the loop
        }
    }

    public static void add_tickets ( int row_num, int seat_num){ //adds tickets to the tickets array
        String email;
        String name;
        String surname;
        int price;
        //initializes the above variables
        while (true) { //runs forever until the correct input format is gotten
            System.out.print("Please enter your first name: ");
            name = input.next();
            if (!(Character.isUpperCase(name.charAt(0)))) { //if the inputted string does not start with caps....
                System.out.println("\nPlease capitalize the first letter of your name");
                continue; // restarts the loop after error message
            }
            break; //breaks when the correct format is gotten
        }
        while (true) { //runs forever until the correct input format is gotten
            System.out.print("Please enter your surname: ");
            surname = input.next();
            if (!(Character.isUpperCase(surname.charAt(0)))) { //if the inputted string does not start with caps....
                System.out.println("\nPlease capitalize the first letter of your surname");
                continue;// restarts the loop after error message
            }
            break; //breaks when the correct format is gotten
        }

        System.out.println();
        while (true) { //runs forever until the correct input format is gotten
            System.out.print("Please enter your email: ");
            email = input.next();
            if (!(email.contains("@"))) { //if the inputted email does not contain an '@' like emails are supoosed to....
                System.out.println("\nERROR. An email should contain an '@', please enter the email in the correct format");
                continue;// restarts the loop after error message
            }
            break; //breaks when the correct format is gotten
        }

        if (row_num == 1) {
            price = row1_price;
        } else if (row_num == 2) {
            price = row2_price;
        } else { //the only other row it can be is 3
            price = row3_price;
        } //gets the price depending on the row

        Person person = new Person(email, name, surname); //sets the person object
        Ticket ticket = new Ticket(row_num, seat_num, price, person); //sets the ticket object
        tickets.add(ticket); //adds the ticket to the tickets array
    }

    public static void buy_ticket () {
        System.out.println("BUYING TICKET...");
        while (true) {
            System.out.println("-------------------------------------------------");
            row_num = input_taker("Please enter the row number\t: ", 3, 1,"Error. There are only three rows, please enter a number between 1 and 3");
            //gets the row number
            System.out.println();
            if (row_num == 1) {
                seat_num = input_taker("Please enter your desired seat number\t: ", 12, 1,"Error. The seat number you entered is out of bounds. Please enter a seat number between 1 and 12");
                if (row1[(seat_num - 1)] == 1) {
                    System.out.print("Error. This seat is already occupied. Please try again\n");
                    break;//to prevent the user entering a seat number already occupied (can't be bought)
                }
                add_tickets(row_num, seat_num);//calls the adds_ticket function
                row1[(seat_num - 1)] = 1;//adds the ticket from the array
            } else if (row_num == 2) {
                seat_num = input_taker("Please enter your desired seat number\t: ", 16, 1,"Error. The seat number you entered is out of bounds. Please enter a seat number between 1 and 16");
                if (row2[(seat_num - 1)] == 1) {
                    System.out.print("Error. This seat is already occupied. Please try again\n");
                    break;//to prevent the user entering a seat number already occupied (can't be bought)
                }
                add_tickets(row_num, seat_num);//calls the adds_ticket function
                row2[(seat_num - 1)] = 1;//adds the ticket from the array
            } else { //the only other option it can be is 3
                seat_num = input_taker("Please enter your desired seat number\t: ", 20, 1,"Error. The seat number you entered is out of bounds. Please enter a seat number between 1 and 20");
                if (row3[(seat_num - 1)] == 1) {
                    System.out.print("Error. This seat is already occupied. Please try again\n");
                    break;//to prevent the user entering a seat number already occupied (can't be bought)
                }
                add_tickets(row_num, seat_num);//calls the adds_ticket function
                row3[(seat_num - 1)] = 1;//adds the ticket from the array
            }
            System.out.println("TICKET BOUGHT");
            break; //breaks when successful
        }
    }

    public static void list_writer(int[] r,int num){ //used to write to the list
        System.out.print("Seats available in Row " + num + ":\t");
        int count = 0;
        for (int l = 0; l < r.length; l++) {
            if (r[l] == 0) {
                count++;
                if (count == 1) {
                    System.out.print((l + 1));
                } else if (count > 1) {
                    System.out.print(", ");//adds a comma if not the first item on the list
                    System.out.print((l + 1));
                }
            }
        }
        if (count == 0) {
            System.out.print("Sorry, but there are no available seats in this row");
        }//after loop if there are no item it prints the above message
        System.out.println();
    }

    public static void show_available () { //the show_available method, specified in task 6
        System.out.println("SHOWING AVAILABLE SEATS...");
        list_writer(row1, 1);
        list_writer(row2, 2);
        list_writer(row3, 3);
        //calls the list_writer method for each row
        System.out.println("-------------------------------------------------");
    }
}