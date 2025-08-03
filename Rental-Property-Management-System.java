import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Property {
    String address;
    String landlord;
    String tenant;
    double rentPrice;
    boolean isRented;

    Property(String address, String landlord, String tenant, double rentPrice) {
        this.address = address;
        this.landlord = landlord;
        this.tenant = tenant;
        this.rentPrice = rentPrice;
        this.isRented = !tenant.equalsIgnoreCase("None");
    }

    void updateStatus(boolean status, String tenant) {
        this.isRented = status;
        this.tenant = status ? tenant : "None";
    }

    double monthlyRevenue() {
        return isRented ? rentPrice : 0;
    }

    @Override
    public String toString() {
        return "Address: " + address +
               " | Landlord: " + landlord +
               " | Tenant: " + tenant +
               " | Rent Price: $" + rentPrice +
               " | Status: " + (isRented ? "Rented" : "Available");
    }
}

public class RentalPropertyManagementSystem {
    static List<Property> properties = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n========= RENTAL PROPERTY MANAGEMENT =========");
            System.out.println("1. Add Property");
            System.out.println("2. View All Listings");
            System.out.println("3. Update Property Status");
            System.out.println("4. Check Property Rental Status");
            System.out.println("5. View Monthly Revenue");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            String input = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from the menu.");
                continue;
            }

            switch (choice) {
                case 1:
                    addProperty();
                    break;
                case 2:
                    viewProperties();
                    break;
                case 3:
                    updateStatus();
                    break;
                case 4:
                    checkRentalStatus();
                    break;
                case 5:
                    viewRevenue();
                    break;
                case 6:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
                    continue;
            }

            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("\nDo you want to return to the main menu? (yes/no): ");
                String restart = sc.nextLine();
                if (restart.equalsIgnoreCase("yes")) {
                    validResponse = true;
                } else if (restart.equalsIgnoreCase("no")) {
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    validResponse = true;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }
        }
    }

    static void addProperty() {
        System.out.print("Enter Property Address: ");
        String address = sc.nextLine();
        System.out.print("Enter Landlord Name: ");
        String landlord = sc.nextLine();
        System.out.print("Enter Tenant Name (or 'None' if not rented): ");
        String tenant = sc.nextLine();
        System.out.print("Enter Rent Price: $");
        double price = sc.nextDouble();
        sc.nextLine();

        properties.add(new Property(address, landlord, tenant, price));
        System.out.println("Property added successfully.");
    }

    static void viewProperties() {
        if (properties.isEmpty()) {
            System.out.println("No properties to show.");
            return;
        }
        System.out.println("\n--- Property Listings ---");
        for (Property p : properties) {
            System.out.println(p);
        }
    }

    static void updateStatus() {
        System.out.print("Enter Property Address to update: ");
        String address = sc.nextLine();
        for (Property p : properties) {
            if (p.address.equalsIgnoreCase(address)) {
                System.out.print("Is the property rented? (yes/no): ");
                String statusInput = sc.nextLine();
                boolean status = statusInput.equalsIgnoreCase("yes");
                String tenant = "None";
                if (status) {
                    System.out.print("Enter Tenant Name: ");
                    tenant = sc.nextLine();
                }
                p.updateStatus(status, tenant);
                System.out.println("Property status updated.");
                return;
            }
        }
        System.out.println("Property not found.");
    }

    static void checkRentalStatus() {
        System.out.print("Enter Property Address to check rental status: ");
        String address = sc.nextLine();
        for (Property p : properties) {
            if (p.address.equalsIgnoreCase(address)) {
                System.out.println("Rental Status: " + (p.isRented ? "Rented" : "Not Rented"));
                return;
            }
        }
        System.out.println("Property not found.");
    }

    static void viewRevenue() {
        double total = 0;
        for (Property p : properties) {
            total += p.monthlyRevenue();
        }
        System.out.println("\nTotal Monthly Revenue: $" + total);
    }
}
