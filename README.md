# Medicare Plus+ Hospital Management System

Medicare Plus+ is a Java Swing-based application designed to streamline hospital management operations. The system provides role-based access for Admin, Doctor, Receptionist, and Pharmacist, enabling efficient management of patients, doctors, prescriptions, inventory, and reports.

---

## 🚀 Features

### **Role-Based Access Control**
- **Admin:** Full access to manage patients, doctors, channels, prescriptions, inventory, users, and monthly reports.
- **Doctor:** View and manage channels, prescriptions, and doctor information.
- **Receptionist:** Manage patients, create/view channels, inventory, and user accounts.
- **Pharmacist:** View prescriptions, manage inventory, and doctor information.

### **Core Functionalities**
- **Patient Management**: Add and manage patient details.
- **Doctor Management**: Add, edit, and view doctor details.
- **Channel Management**: Create and view doctor-patient channels.
- **Prescription Management**: View and manage prescriptions.
- **Inventory Management**: Create and manage medical items.
- **User Management**: Add and manage user accounts.
- **Reports**: Generate and view monthly reports.
- **Logout**: Secure session termination for all users.

---

## 🛠️ Technologies Used

### **Frontend**
- **Java Swing**: For building the graphical user interface.

### **Backend**
- **Java (JDK 11)**: Core programming language for application logic.

### **Database**
- **MySQL**: Relational database to store user, patient, doctor, prescription, and inventory data.
- **JDBC**: For database connectivity.

---

## 🎯 Setup and Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/medicare-plus.git
2. Set Up the Database
Import the provided SQL dump file medicare_plus.sql into MySQL.
Update the database configuration in the DatabaseConnection class:

-String url = "jdbc:mysql://localhost:3306/medicare_plus";
-String username = "your-database-username";
-String password = "your-database-password";

3. Build and Run the Application
Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
Compile and run the Main class to launch the application.


## 📂 Project Structure
src/
├── medicareplus/
│   ├── Main.java          # Application entry point

│   ├── Patient.java       # Patient management module

│   ├── Doctor.java        # Doctor management module

│   ├── Channel.java       # Channel creation module

│   ├── ViewChannel.java   # Channel viewing module

│   ├── ViewPrescription.java # Prescription viewing module

│   ├── Items.java         # Inventory management module

│   ├── User.java          # User management module

│   ├── MonthlyReport.java # Monthly report generation

│   ├── Login.java         # Login system

│   └── DatabaseConnection.java # Database connectivity

## 🔒 Role-Based Access Logic
Role	&  Accessible Modules
Admin	- All modules
Doctor	- View Channel, View Prescription, View Doctor
Receptionist	- Patient, Create Channel, View Channel, Create Item, Create User, View Doctor
Pharmacist	- View Prescription, Create Item, View Doctor
