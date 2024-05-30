Virtual Assistant Invoice System

This Java program serves as a virtual assistant for managing clients, services, invoices, and providing business-related analytics.
DATABASE CONNECTIVITY

The application connects to a MySQL database using JDBC.
CLIENT MANAGEMENT

    Adding Clients: Insert new clients into the database.
    Viewing Clients: Retrieve and display all clients.
    Updating Clients: Modify existing client details.
    Deleting Clients: Remove clients from the database.

SERVICE MANAGEMENT

    Adding Services: Insert new services into the database with specified rates.
    Viewing Services: Retrieve and display all services.
    Updating Services: Modify existing service details, such as name and rate.
    Deleting Services: Remove services from the database.

INVOICE MANAGEMENT

    Creating Invoices: Generate invoices for clients, allowing services and hours to be added for billing.
    Viewing Invoices: Retrieve and display all invoices for a specific client.
    Deleting Invoices: Remove invoices from the database.

ANALYTICS

    Most Popular Service: Identify the service that appears most frequently in invoices.
    Top Client: Determine the client who has spent the most.
    Total Income: Calculate the total revenue generated from all invoices.

Instructions for Running the Program

    Ensure you have a MySQL database running on your local machine.
    Update the database connection details (URL, username, password) in the DatabaseConnector.java file.
    Compile and run the program using Java.
    Before running the program, set up the necessary database tables (clients, services, invoices, invoice_details) as per the requirements."# VAIS" 
