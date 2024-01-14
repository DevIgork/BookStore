


![Book store text](https://lmcst.ac.in/wp-content/uploads/2017/01/bookstall-banner.jpg)
# Book_Store 📚

This project is the back-end part of the bookstore using spring, it implements security, roles, categories, the ability to add books to the cart, place an order, project have tests, Swagger, Tomcat and uses such a programming pattern as rest api more below.



## Tech Stack 💎
**Language** Java

**Accessing Data** Spring Data JPA, Hibernate, MySQL

**Web Development** Spring MVC, Servlets, JSP, Tomcat

**Application Configuration** Spring Boot, Spring, Maven

**Testing and Documentation** JUnit, Mockito, Swagger, Test

**Version Control** Git

**Data Formats** XML, JSON

**Security** Spring Security


## Deployment 💻

To deploy this project simple install doker to your pc pull this git hub project and add your dependency to .env file and to application.properties and run commands
```bash
mvn clean package
```
```bash
docker-compose up
```


## API Reference 📄

#### Get all book (ADMIN, USER)

```code
  GET "/api/books"
```
return all books in database

*************************

#### Get book by id(ADMIN, USER)

```code
  GET "/api/books/{id}"
```

| Parameter  | Description                       |
| :-------- | :-------------------------------- |
| `id`      | **Required** Id of book |

return book by book id

*************************

#### Save book (ADMIN)

```code
  POST "/api/books"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `title`       | **Required**. title of the book |
| `author`       | **Required**. author of the book |
| `isbn`      | **Required**. isbn of the book |
| `price`       | **Required**. price of book |
| `description`       | description  of book |
| `coverImage`      | coverImage of book |

```code
  {
  "title": "interesting book",
  "author": "Igor Korolevich",
  "isbn": "9781408880722",
  "price": 500,
  "description": "interesting book description",
  "coverImage": "https://image.jpg"
}

```
save book to database
*****
#### Delete book  (ADMIN)

```code
  DELETE "/api/books/{id}"
```
| Parameter  | Description                       |
| :-------- | :-------------------------------- |
| `id`      | **Required** Id of book |

delete book from database

*****
#### Update book  (ADMIN)

```code
  PUT "/api/books/{id}"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `id`      | **Required** Id of book |
| `title`       | **Required**. title of the book |
| `author`       | **Required**. author of the book |
| `isbn`      | **Required**. isbn of the book |
| `price`       | **Required**. price of book |
| `description`       | description  of book |
| `coverImage`      | coverImage of book |
| `categoryIds`      | ids of categories |

```code
  {
  "title": "interesting book",
  "author": "Igor Korolevich",
  "isbn": "9781408880722",
  "price": 500,
  "description": "interesting book description",
  "coverImage": "https://image.jpg"
}

```
update book data in database
*****

#### Search books  (USER, ADMIN)

```code
  GET "/api/books/search?title=titles&author=authors"
```
get books by book params like title or author
*****
#### Get categories  (USER, ADMIN)

```code
  GET "/api/categories"
```
get all categories from database
*****
#### Create category  (ADMIN)

```code
  POST "/api/categories"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `name`      | **Required** name of category |
| `description`       |  description of the category |

```code
{
    "name" : "fantasy",
    "description" : "fantasy book"
}

```
save new category to database
*****
#### Get category  (USER, ADMIN)

```code
  GET "/api/categories/{id}"
```
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `id`      | **Required** id of category |

get category by id in database

*****
#### Update category by id  (ADMIN)

```code
  PUT "/api/categories/{id}"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `id`      | **Required** id of category |
| `name`      | **Required** name of category |
| `description`       |  description of the category |

```code
{
    "name" : "fantasy",
    "description" : "fantasy book"
}

```
update category data in database
*****
#### DELETE category  (ADMIN)

```code
  DELETE "/api/categories/{id}"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `id`      | **Required** id of category |

delete category by id

*****
#### Get books  (USER, ADMIN)

```code
  GET "/api/categories/{id}/books"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `id`      | **Required** id of category |

get books by category id
********
#### Create order (USER)

```code
  POST "/api/orders"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `shippingAddress`      | **Required** your shipping address |

```code
{
  "shippingAddress": "Kyiv, Shevchenko ave, 1"
}

```
create order
*****
#### Get orders (USER)

```code
  GET "/api/orders"
```
get user orders
*****
#### Patch status (ADMIN)

```code
  PATCH "/api/orders/{id}"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `id`      | **Required** id of order|

```code
  status : "new status"
```
change status of order
*****
#### Get Order Items (USER)

```code
  GET "/api/orders/{orderId}/items"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `orderId`      | **Required** order id |

get order items by order id
*****
#### Get Order Item in order (USER)

```code
  GET "/api/orders{orderId}/items/{itemId}"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `orderId`      | **Required** order id |
| `itemId`      | **Required** order item id |

get order item by order id and order item id

*****
#### add item to cart (USER, ADMIN)

```code
  POST "/api/cart"
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `bookId`      | **Required** id of book |
| `quantity`      | **Required** quantity of books |

```code
{
    "bookId":1,
    "quantity":2
}
```
post book to cart

*******
#### Get cart (USER, ADMIN)

```code
  GET "/api/cart"
```

get user cart

*******
#### update cartItem (USER, ADMIN)

```code
  PUT "/api/cart/cart-items/{cartItemId}""
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `cartItemId`      | **Required** id of cart |
| `bookId`      | **Required** id of book |
| `quantity`      | **Required** quantity of books |

```code
{
    "bookId":1,
    "quantity":2
}
```

update cart item data

*******
#### Delete cartItem (USER, ADMIN)

```code
  DELETE "/api/cart/cart-items/{cartItemId}""
```
body:
| Parameter  | Description                       |
| :--------  | :-------------------------------- |
| `cartItemId`      | **Required** id of cart |

delete cartItem from cart

*******
## 📜How project was developed 📜
![App Screenshot](https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExYW9lNWU1N3plaHdvM2NpaDBoYmZhbjA4N2ozeHd5cnN1YXg2NHZsaiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/UQ84N6dIWObZWILqsE/giphy.gif)
It was my first back-end project, and it was very interesting to implement it, and during the development I encountered some blockers, but thanks to my mentors I was able to overcome them, and I am very grateful to them.

#### spring security
When adding a security token, namely jwt token, there was a problem that the user could not submit a request on the token because the Expiration in the token was not set correctly and the user could not log in, but together with the mentor we found the reason in the Value annotation and fixed it
#### Test
When I added tests to my project, there was also a problem at the controller level that further (User) authentication.getPrincipal(); it did not go in and threw an error, but after an hour of googling, I found a solution that I needed to add user to Authentication and then to SecurityContextHolder before the test.
## Possible improvements

- add more test

- add telegram api

- add Stripe API


## 🔗 Links

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/igor-korolevich-2b3b25175/)
