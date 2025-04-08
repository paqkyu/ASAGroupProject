# ASAGroupProject

##Overview
The ASA Ticketing system is a Java-based application for managing events, bookings, and user accounts.

#The System has 4 different roles which are the Admin, Organizer, User, and root each with specific functionalities

#Features

#User role:
- Book tickets for events
- View their bookings
-Update their username and password
-Delete their account

#Organizer role
-Create, Edit and delete events.
-Manage events that they create.

#Admin Role
-Promote users to Organizers
-Demote Organizers to Users
-Delete user accounts (excluding admins)

#Root role
- Manage all users, including promoting/demoting admins
- Delete all users and their associated tickets.

#How to run
1. Clone the repository to your local machine
2. Open the project with your preferred IDE
3. Ensure you have Java installed (JDK 8 or higher)
4. Run the "Main.java" file located in the 'src' directory

#Test Accounts
 - User account: Username: User1 Password: UserPass123
 - Admin account: Username: Admin1 Password: AdminPass123
 - Organizer account: Username: Organizer1 Password: OrgPass123
 - Root account: Username: Root Password BOOM

#File descriptions
- **`Accounts.csv`**: Stores user account information (username, hashed password, role).
- **`RootPass.csv`**: Stores the root account password.
- **`Tickets.csv`**: Stores ticket booking information.
- **`events.csv`**: Stores event details.

## Dependencies
- Java Swing for GUI.
- Java I/O for file handling.

#Repository link:
https://github.com/paqkyu/ASAGroupProject
