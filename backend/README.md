# fBook API
The API will be available at `localhost:8080/api/` after building

## Manual Build
**at first**, you need to install [git](https://git-scm.com/downloads), [java 21](https://www.oracle.com/cis/java/technologies/downloads/#java21) and [postgresql](https://www.postgresql.org/download/).
if you want to run tests, you also may need to install and run [docker](https://docs.docker.com/engine/install/).
<br/>
now, lets **clone** repository using `git`:

```bash
git clone https://github.com/fairygel/fBook.git
cd fBook/backend
```
before building, you can set up the **data source**:
```bash
nano src/main/resources/application.yml
```
you will see something like this:
```yaml
spring:
  application:
    name: fBook

  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: 141306
    hikari:
      schema: fbook

  flyway:
    schemas: fbook

  jpa:
    hibernate:
      ddl-auto: update
```
we need only `username: postgres` and `password: 141306`. change it to what's your database uses.
<br/>
also you can change port number, in `url: jdbc:postgres://localhost:5432/postgres` replace `5432` with what you need


let's **build** it:

### Windows:
```shell
.\gradlew.bat clean build
```

or if you want to **ignore tests**:

```shell
.\gradlew.bat clean build -x test
```

### Linux:
```bash
./gradlew clean build
```

to **ignore tests**:

```bash
./gradlew clean build -x test
```

now let's **start** our service!

```bash
cp build/libs/fBook-0.0.1-SNAPSHOT.jar fBook.jar
java -jar fBook.jar
```
## EndPoints
### list of your books:
GET `/books`
<br/>
returns a list of your books
### get a single book:
GET `/books/{id}`
<br/>
returns a more detailed info about book
### create book:
POST `/books`
<br/>
this will create a book with your data.
<br/>
the request body needs to be in JSON format, it can include the following properties:
* `name` - string, required
* `authorId` - int, not required
* `genreIds` - array of integers, not required
* `annotation` - string, not required
* `bookTypeId` - int, not required

author id you can get [here](#list-of-your-authors)

genres are [here](#list-of-your-genres)

book types - [here](#get-book-types)

for example:
```json
{
    "name": "11/22/63",
    "authorId": 2,
    "genreIds": [1, 2],
    "annotation": "nice",
    "bookTypeId": 1
}
```
### delete book:
DELETE `/books/{id}`
<br/>
delete a book, nothing more
### update book:
PATCH `/books/{id}`
<br/>
will update the book and return updated result. you can update book using next properties(all of them are **not required**):
* `name` - string
* `authorId` - int
* `genreIds` - array of int
* `bookStatusId` - int
* `startedReadDate` - date
* `endedReadDate` - date
* `annotation` - string
* `bookTypeId` - int

author id you can get [here](#list-of-your-authors)

genres are [here](#list-of-your-genres)

book types - [here](#get-book-types)

book statuses - [here](#get-book-statuses)

example:
```json
{ 
    "name": "It",
    "authorId": 3,
    "genreIds": [4],
    "bookStatusId": 2,
    "startedReadDate": "2024-01-02",
    "endedReadDate": "2024-04-11",
    "annotation": "clown kills people lol",
    "bookTypeId": 3
}
```

### list of your authors:
GET `/authors`
<br/>
returns a list of your authors

### get a single author:
GET `/authors/{id}`
<br/>
returns a more detailed info about author

### create author:
POST `/authors`
<br/>
this will create a new author with your data.
<br/>
the request body needs to be in JSON format, it can include the following properties:
* `firstName` - string, required
* `lastName` - string, not required

<br/>
for example:
```json
{
  "firstName": "Stephen",
  "lastName": "King"
}
```
### delete author:
DELETE `/authors/{id}`
<br/>
delete an author
### update author:
PATCH `/authors/{id}`
<br/>
will update the author and return updated result. you can update author using next properties(all of them are **not required**):
* `firstName` - string
* `lastName` - string
  <br/>
  example:
```json
{
  "firstName": "Anna",
  "lastName": "Jane"
}
```
### list of your genres:
GET `/genres`
<br/>
returns a list of your genres

### get a single genre:
GET `/genres/{id}`
<br/>
returns a more detailed info about genre

### create genre:
POST `/genres`
<br/>
this will create a new genre with your data.
<br/>
the request body needs to be in JSON format, it can include the following properties:
* `name` - string, required
<br/>
for example:

```json
{
    "name": "detective"
}
```

### delete genre:
DELETE `/genres/{id}`
<br/>
delete a genre
### update genre:
PATCH `/genres/{id}`
<br/>
will update the genre and return updated result. you can update genre using next properties(all of them are **not required**):
* `name` - string
<br/>
example:

```json
{
    "name": "horror"
}
```
### get book types:
GET `/book-types`
<br/>
returns a list of book types

### get a single book type:
GET `/book-types/{id}`
<br/>
returns book type to use
### get book statuses:
GET `/book-statuses`
<br/>
returns a list of available book statuses

### get a single book status:
GET `/book-statuses/{id}`
<br/>
returns book status to use

### list of all grades:
GET `/grades`
<br/>
returns a list of grades

### get a single grade:
GET `/grades/{id}`
<br/>
returns a more detailed grade

### create grade:
POST `/grades`
<br/>
this will create a new grade with your data.
<br/>
the request body needs to be in JSON format, it can include the following properties:
* `bookId` - int, required
* `rating` - int, required
* `comment` - string, not required
  <br/>
  for example:
```json
{
  "bookId": 1,
  "rating": 5,
  "comment": "nice book"
}
```
book id you can find [here](#list-of-your-books)
### delete grade:
DELETE `/grades/{id}`
<br/>
delete a grade
### update grade:
PATCH `/grades/{id}`
<br/>
will update the grade and return updated result. you can update grade using next properties(all of them are **not required**):
* `rating` - int
* `comment` - string
  <br/>
  example:
```json
{
  "rating": 1,
  "comment": "just bad"
}
```
