# Java Repository 🚀

## Overview 🏗️
Java Repository is a project designed to abstract database interaction using the Repository Pattern. This project serves as a template for others who want to implement a structured and maintainable data access layer in their Java applications.

## Features ✨
- ✅ Provides a generic repository pattern for database operations.
- 🔄 Supports CRUD (Create, Read, Update, Delete) operations.
- 🏛️ Abstracts database logic from business logic.
- 📦 Enhances code reusability and maintainability.
- 🛠️ Uses Java's JDBC for database interaction with SQL queries.
- 🎯 Supports dynamic query building for flexibility.

## Installation 🛠️
1. Clone the repository:
   ```sh
   git clone https://github.com/whilmarbitoco/java-repository.git
   ```
2. Navigate to the project directory:
   ```sh
   cd Java-Repository
   ```
3. Build the project using Maven:
   ```sh
   mvn clean install
   ```

## Usage 📖
1. Define an entity class:
   ```java
   package org.whilmarbitoco.app.model;

   import org.whilmarbitoco.app.database.anotation.*;

   @Table(name = "User")
   public class User {
       @Column(name = "id")
       private int id;

       @Column(name = "name")
       private String name;

       @Column(name = "email")
       private String email;
       
       // Constructors
       public User() {}
       public User(String name, String email) {
           this.name = name;
           this.email = email;
       }
       
       // Getters and Setters
       public int getId() { return id; }
       public void setId(int id) { this.id = id; }
       
       public String getName() { return name; }
       public void setName(String name) { this.name = name; }
       
       public String getEmail() { return email; }
       public void setEmail(String email) { this.email = email; }
   }
   ```
2. Create a repository class:
   ```java
   package org.whilmarbitoco.app.database.repository;

   import org.whilmarbitoco.app.model.User;

   public class UserRepository extends Repository<User> {
       public UserRepository() {
           super(User.class);
       }
   }
   ```
3. Use the repository in your application:
      ```java
    public class Main {
   
    public static void main(String[] args) {
   
        UserRepository userRepository = new UserRepository();
   
        User user = new User("John Doe", "johndoe@gmail.com");
        userRepository.create(user);
        System.out.println("User created");
   
        userRepository.findAll().forEach(u -> {
            System.out.println("ID:: " + u.getId());
            System.out.println("Email:: " + u.getEmail());
            System.out.println("Name:: " + u.getName());
            System.out.println();
        });
   
        Optional<User> foundUser = userRepository.findByID(208);
        foundUser.ifPresent(value -> System.out.println("User found: " + value.getName()));
   
        List<User> filteredUsers = userRepository.findWhere("email", "=", "johnDoe@gmail.com");
        filteredUsers.forEach(u -> {
            System.out.println("ID:: " + u.getId());
            System.out.println("Email:: " + u.getEmail());
            System.out.println("Name:: " + u.getName());
            System.out.println();
        });
   
        if (foundUser.isPresent()) {
            User updatedUser = foundUser.get();
            updatedUser.setEmail("newemail@example.com");
            userRepository.update(updatedUser);
            System.out.println("User updated: " + updatedUser.getEmail());
        }
   
        userRepository.delete(foundUser.get().getId());
        System.out.println("User with ID 1 deleted.");
   
    }
   }
      ```

## Contributing 🤝
Contributions are welcome! Feel free to fork the repository and submit pull requests.

## License 📜
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact 📧
For any inquiries, you can reach out to `whlmrbitoco@gmail.com`.

