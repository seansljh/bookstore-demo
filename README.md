# Bookstore Microservice
A simple web microservice implementation of a bookstore integrated with a MySQL RDBMS

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project
- A simple web microservice implementation of a bookstore integrated with a MySQL RDBMS
- Uses REST to make API calls to the database for CRUD operations 

### Built With
* Spring Boot
* MySQL
* Java 17


<!-- GETTING STARTED -->
## Getting Started
1. Ensure that you have JDK [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) installed
```shell
    java --version # should show the appropriate version
```
2. Clone this repository to your local environment
```shell
    git clone https://github.com/seansljh/bookstore-demo.git
```
3. Open the cloned repository in your preferred IDE. Build the Project
```shell
    ./mvnw clean install
```
4. Launch and create a MySQL DB instance. For my case, I used a [docker container](https://www.docker.com/) for isolation and ease of deployment. Note down the root password and update `resources/application.properties` should you desire to change it
```shell
  docker run -d -p 3306:3306 --name book_db2 -e 'MYSQL_ROOT_PASSWORD=testing123' mysql:latest
```
5. Locate the main application class in `src/main/java/com.example.bookstoredemo/BookstoreDemoApplication.java` and run it. You should be able to use tools such as [Postman](https://www.postman.com/) to send requests to this webservice now. The webservice should be accessible at localhost:8080.

<!-- USAGE EXAMPLES -->

## Usage
This web service supports various functions, as specified below. 
1. Adding new books to the inventory 
   - **POST** http://localhost:8080/add with a JSON message body, e.g.
     - ```json 
       {
         "book_title": "big black cute cat",
         "author": "sean lee",
         "isbn": "1522287382256",
         "price": 12.99,
         "stock": 51
         }
       ```
2. Retrieve all books in the inventory
- **GET** http://localhost:8080/search/all
- Pagination is supported; parameters such as `page` and `size` can be used in the request parameters to control the size of the result set
<br/><br/>
3. Search books by isbn/author/title
- **GET** http://localhost:8080/search/isbn,author,title, e.g http://localhost:8080/search/isbn
- Add search term as a request parameter
<br/><br/>
4. Query books by stock, price 
- **GET** http://localhost:8080/filter/column, e.g http://localhost:8080/search/stock
- Add min, max as search parameters 
- If min and max are supplied, we do a between query
- Else if only min is supplied, we find all books that have values higher than min; and vice versa for max
<br/><br/>
5. Get the stock of a particular book
- **GET** http://localhost:8080/search/{book_isbn}/stock
- E.g. GET http://localhost:8080/search/12344555/stock
<br/><br/>
6. Update the stock of a particular book
- **PUT** http://localhost:8080/update/{book_isbn}
- Add new stock in request parameters, this input is validated to ensure it is not negative  
<br/><br/>
7. Delete a book from the inventory
- **DELETE** http://localhost:8080/book/{book_isbn}