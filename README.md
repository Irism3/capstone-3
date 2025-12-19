# My EasyShop Capstone 

Overview

EasyShop is a Java-based e-commerce capstone project built using Spring Boot and MySQL. 
The application allows users to browse products by category, register and log in, and interact with the system based on 
their role (USER or ADMIN).
This project was created to practice building REST APIs, connecting to a database, and implementing authentication and
authorization in a real-world style application.

```text
Features

User registration and login

Role‑based authorization (USER vs ADMIN)

View products and categories

Add, update, and delete products (ADMIN only)

RESTful API endpoints

MySQL database integration
````

---

## Learning Outcomes
Through this capstone project, I strengthened my understanding of:
Building REST APIs with Spring Boot
DAO pattern and JDBC
SQL queries and database relationships

---
## UI Customization

In addition to backend functionality, I customized the front-end styling of the application. I updated the default green buttons (such as Add to Cart, Home, Profile, and View Cart) by modifying the CSS and Bootstrap button classes. This allowed me to better match the site’s branding and improve the overall visual consistency of the application.

These changes helped me practice working with existing front-end code, understanding class-based styling, and safely customizing UI elements without breaking functionality.

## Future Improvements

Shopping cart persistence

Order checkout functionality

Improved UI/UX styling

## Interesting Code 

```java
@PutMapping("/{id}")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public void updateProduct(@PathVariable int id, @RequestBody Product product) {
    try {
        productDao.update(id, product);
    } catch (Exception ex) {
        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unable to update product."
        );
    }
}
```
# Why this is interesting

- Shows role-based security
- Uses path variables
- Clean error handling
- This endpoint ensures only ADMIN users can update products and returns a proper HTTP error if the update fails.
