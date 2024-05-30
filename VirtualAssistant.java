
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

//PONTILLAS, CHRISTIAN REY T.
//ITCC 11.1 B

public class VirtualAssistant {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "sambadetoriko1";

    private static Connection connection;
    private static double getServiceRate(String serviceName) {
    try {
        PreparedStatement statement = connection.prepareStatement("SELECT rate FROM service WHERE name = ?");
        statement.setString(1, serviceName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("rate");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; 
}
private static void updateClientTotalBilled(int clientId, double totalBilledAmount) {
    try {
        PreparedStatement statement = connection.prepareStatement("UPDATE client SET total_billed = ? WHERE id = ?");
        statement.setDouble(1, totalBilledAmount);
        statement.setInt(2, clientId);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Client's total billed amount updated successfully!");
        } else {
            System.out.println("No client found with ID: " + clientId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database.");

            displayMenu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
    
        do {
            System.out.println("\nWhat can I help you with?\n");
            System.out.println("======================");
            System.out.println("Client Management:");
            System.out.println("1. Add client");
            System.out.println("2. View clients");
            System.out.println("3. Update client");
            System.out.println("4. Delete client\n");
    
            System.out.println("======================");
            System.out.println("Service Management:");
            System.out.println("5. Add service");
            System.out.println("6. View services");
            System.out.println("7. Update service");
            System.out.println("8. Delete service\n");
    
            System.out.println("======================");
            System.out.println("Invoice Management:");
            System.out.println("9. Generate invoice");
            System.out.println("10. View invoices by client");
            System.out.println("11. Delete invoice\n");
    
            System.out.println("======================");
            System.out.println("Analytics:");
            System.out.println("12. View most popular service");
            System.out.println("13. View top client");
            System.out.println("14. View total income\n");
    
            System.out.println("======================");
            System.out.println("15. Exit");
            System.out.print("Enter your choice: ");
    
            choice = scanner.nextInt();
            scanner.nextLine(); 
    
            switch (choice) {
                case 1:
                    addClient();
                    break;
                case 2:
                    viewClients();
                    break;
                case 3:
                    updateClient();
                    break;
                case 4:
                    deleteClient();
                    break;
                case 5:
                    addService();
                    break;
                case 6:
                    viewServices();
                    break;
                case 7:
                    updateService();
                    break;
                case 8:
                    deleteService();
                    break;
                case 9:
                    generateInvoice();
                    break;
                case 10:
                    viewInvoicesByClient();
                    break;
                case 11:
                    deleteInvoice();
                    break;
                case 12:
                    viewMostPopularService();
                    break;
                case 13:
                    viewTopClient();
                    break;
                case 14:
                    viewTotalIncome();
                    break;
                case 15:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 15);
    
        scanner.close();
    }
    

    private static void addClient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO client (name) VALUES (?)");
            statement.setString(1, clientName);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Client added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewClients() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client");

            while (resultSet.next()) {
                System.out.println("Client ID: " + resultSet.getInt("id") + ", Name: " + resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateClient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter client ID to update: ");
        int clientId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?");
            statement.setString(1, newName);
            statement.setInt(2, clientId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client updated successfully!");
            } else {
                System.out.println("No client found with ID: " + clientId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteClient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter client ID to delete: ");
        int clientId = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM client WHERE id = ?");
            statement.setInt(1, clientId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Client deleted successfully!");
            } else {
                System.out.println("No client found with ID: " + clientId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addService() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter service name: ");
        String serviceName = scanner.nextLine();
        System.out.print("Enter rate for the service: ");
        double rate = scanner.nextDouble();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO service (name, rate) VALUES (?, ?)");
            statement.setString(1, serviceName);
            statement.setDouble(2, rate);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Service added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewServices() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM service");

            while (resultSet.next()) {
                System.out.println("Service ID: " + resultSet.getInt("id") + ", Name: " + resultSet.getString("name") + ", Rate: $" + resultSet.getDouble("rate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateService() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter service ID to update: ");
        int serviceId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new rate: ");
        double newRate = scanner.nextDouble();

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE service SET name = ?, rate = ? WHERE id = ?");
            statement.setString(1, newName);
            statement.setDouble(2, newRate);
            statement.setInt(3, serviceId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Service updated successfully!");
            } else {
                System.out.println("No service found with ID: " + serviceId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteService() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter service ID to delete: ");
        int serviceId = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM service WHERE id = ?");
            statement.setInt(1, serviceId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Service deleted successfully!");
            } else {
                System.out.println("No service found with ID: " + serviceId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void generateInvoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter client ID for the invoice: ");
        int clientId = scanner.nextInt();
        System.out.print("Enter invoice number: ");
        int invoiceNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        List<String> services = new ArrayList<>();
        List<Double> hoursList = new ArrayList<>();
        double totalAmount = 0.0;
        
        // Allow user to add services to the invoice
        while (true) {
            System.out.print("Enter service name (or type 'done' to finish adding services): ");
            String serviceName = scanner.nextLine();
            if (serviceName.equalsIgnoreCase("done")) {
                break;
            }
            double rate = getServiceRate(serviceName);
            if (rate == -1) {
                System.out.println("Service not found. Please try again.");
                continue;
            }
            services.add(serviceName);
            System.out.print("Enter hours for service '" + serviceName + "': ");
            double hours = scanner.nextDouble();
            scanner.nextLine(); // Consume newline character
            hoursList.add(hours);
            totalAmount += rate * hours; // Add the rate * hours to the total amount
        }
        
        try {
            // Insert invoice
            PreparedStatement invoiceStatement = connection.prepareStatement(
                "INSERT INTO invoice (invoice_number, client_id, total_amount, date) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            invoiceStatement.setInt(1, invoiceNumber);
            invoiceStatement.setInt(2, clientId);
            invoiceStatement.setDouble(3, totalAmount);
            invoiceStatement.setString(4, date);
            int rowsInserted = invoiceStatement.executeUpdate();
            
            // Retrieve the generated invoice ID
            int invoiceId = -1;
            ResultSet generatedKeys = invoiceStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                invoiceId = generatedKeys.getInt(1);
            }
            
            // Insert invoice details
            if (rowsInserted > 0 && invoiceId != -1) {
                PreparedStatement detailsStatement = connection.prepareStatement(
                    "INSERT INTO invoice_details (invoice_id, service_id, hours) VALUES (?, ?, ?)"
                );
                for (int i = 0; i < services.size(); i++) {
                    double hours = hoursList.get(i);
                    String serviceName = services.get(i);
                    double rate = getServiceRate(serviceName);
                    
                    PreparedStatement serviceStatement = connection.prepareStatement(
                        "SELECT id FROM service WHERE name = ?"
                    );
                    serviceStatement.setString(1, serviceName);
                    ResultSet serviceResultSet = serviceStatement.executeQuery();
                    if (serviceResultSet.next()) {
                        int serviceId = serviceResultSet.getInt("id");
                        detailsStatement.setInt(1, invoiceId);
                        detailsStatement.setInt(2, serviceId);
                        detailsStatement.setDouble(3, hours);
                        detailsStatement.addBatch();
                    }
                }
                // Execute batch insert for invoice details
                int[] batchResult = detailsStatement.executeBatch();
                int successfulInserts = 0;
                for (int result : batchResult) {
                    if (result >= 0) {
                        successfulInserts++;
                    }
                }
                if (successfulInserts == services.size()) {
                    // Update client's total billed amount
                    updateClientTotalBilled(clientId, totalAmount);
                    System.out.println("Invoice generated successfully!");
                } else {
                    System.out.println("Some services could not be added to the invoice details.");
                }
            } else {
                System.out.println("Failed to generate invoice.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private static void viewInvoicesByClient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter client ID to view invoices: ");
        int clientId = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM invoice WHERE client_id = ?");
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Invoice ID: " + resultSet.getInt("id") + ", Invoice Number: " + resultSet.getInt("invoice_number") +
                        ", Total Amount: $" + resultSet.getDouble("total_amount") + ", Date: " + resultSet.getString("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteInvoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter invoice ID to delete: ");
        int invoiceId = scanner.nextInt();
    
        try {
            // Delete associated records in invoice_details table first
            PreparedStatement deleteDetailsStatement = connection.prepareStatement("DELETE FROM invoice_details WHERE invoice_id = ?");
            deleteDetailsStatement.setInt(1, invoiceId);
            int rowsDeletedDetails = deleteDetailsStatement.executeUpdate();
    
            // Then, delete the invoice
            PreparedStatement deleteInvoiceStatement = connection.prepareStatement("DELETE FROM invoice WHERE id = ?");
            deleteInvoiceStatement.setInt(1, invoiceId);
            int rowsDeletedInvoice = deleteInvoiceStatement.executeUpdate();
    
            if (rowsDeletedInvoice > 0) {
                System.out.println("Invoice deleted successfully!");
            } else {
                System.out.println("No invoice found with ID: " + invoiceId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void viewMostPopularService() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT service_id, COUNT(*) AS count FROM invoice_details " +
                "GROUP BY service_id ORDER BY count DESC LIMIT 1"
            );
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                int serviceId = resultSet.getInt("service_id");
                PreparedStatement serviceStatement = connection.prepareStatement(
                    "SELECT name FROM service WHERE id = ?"
                );
                serviceStatement.setInt(1, serviceId);
                ResultSet serviceResultSet = serviceStatement.executeQuery();
    
                if (serviceResultSet.next()) {
                    String serviceName = serviceResultSet.getString("name");
                    System.out.println("Most popular service: " + serviceName);
                } else {
                    System.out.println("Service name not found for ID: " + serviceId);
                }
            } else {
                System.out.println("No data available for the most popular service.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    private static void viewTopClient() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT client_id, SUM(total_amount) AS total_spent FROM invoice GROUP BY client_id ORDER BY total_spent DESC LIMIT 1");

            if (resultSet.next()) {
                int clientId = resultSet.getInt("client_id");
                PreparedStatement clientStatement = connection.prepareStatement("SELECT * FROM client WHERE id = ?");
                clientStatement.setInt(1, clientId);
                ResultSet clientResultSet = clientStatement.executeQuery();
                if (clientResultSet.next()) {
                    System.out.println("Top client: " + clientResultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewTotalIncome() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT SUM(total_amount) AS total_income FROM invoice");

            if (resultSet.next()) {
                double totalIncome = resultSet.getDouble("total_income");
                System.out.println("Total income: $" + totalIncome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}