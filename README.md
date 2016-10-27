#Sugar Cipher using Sugar ORM v2.4
 by Ethan Tam(sbhkin)


- Extract the libs and assets into your application. This dependency is not included in the sugar library.
- Clone this branch to you Project root folder
- Follow [How to use master version](#usemasterv)
- Add a meta property named DB_PASSWORD in your manifest file along with other Sugar configuration as follows:
```
          <meta-data
           android:name="DB_PASSWORD"
           android:value="your_DB_ENCRYPTION_KEY_here"/>
```

You're good to go. Use Sugar as usual and your database is encrypted now.

# Sugar ORM

###  <a link = "usemasterv"></a>How to use master version
First, download sugar repository
```
git clone git@github.com:satyan/sugar.git
```

include this in your **settings.gradle**
```gradle
include ':app' // your module app
include ':sugar'

def getLocalProperty(prop) {
	Properties properties = new Properties()
	properties.load(new File(rootDir.absolutePath + '/local.properties').newDataInputStream())
	return properties.getProperty(prop, '')
}

project(':sugar').projectDir = new File(getLocalProperty('sugar.dir'))

```

include this in your **local.properties**
```
sugar.dir=/path/to/sugar/library
```

add sugar project to the dependencies of your main project (build.gradle)
```gradle
dependencies {
    compile project(':sugar')
}
```

You should also comment this line just comment this line (library/build.gradle): https://github.com/satyan/sugar/blob/master/library%2Fbuild.gradle#L2

```gradle
// apply from: '../maven_push.gradle'
```
===================

After installing, check out how to set up your first database and models [here](http://satyan.github.io/sugar/getting-started.html) **Outdated**. Check examples of 1.4 and master below:

## Examples
### SugarRecord
```java
public class Book extends SugarRecord {
  @Unique
  String isbn;
  String title;
  String edition;

  // Default constructor is necessary for SugarRecord
  public Book() {

  }

  public Book(String isbn, String title, String edition) {
    this.isbn = isbn;
    this.title = title;
    this.edition = edition;
  }
}
```
or
```java
@Table
public class Book { ... }
```

### Save Entity
```java
Book book = new Book("isbn123", "Title here", "2nd edition")
book.save();
```

or
```java
SugarRecord.save(book); // if using the @Table annotation
```

### Load Entity
```java
Book book = Book.findById(Book.class, 1);
```

### Update Entity
```java
Book book = Book.findById(Book.class, 1);
book.title = "updated title here"; // modify the values
book.edition = "3rd edition";
book.save(); // updates the previous entry with new values.
```


### Delete Entity
```java
Book book = Book.findById(Book.class, 1);
book.delete();
```

or
```java
SugarRecord.delete(book); // if using the @Table annotation
```

### Update Entity based on Unique values
```java
Book book = new Book("isbn123", "Title here", "2nd edition")
book.save();

// Update book with isbn123
Book sameBook = new Book("isbn123", "New Title", "5th edition")
sameBook.update();

book.getId() == sameBook.getId(); // true
```

or
```java
SugarRecord.update(sameBook); // if using the @Table annotation
```

### Bulk Insert
```java
List<Book> books = new ArrayList<>();
books.add(new Book("isbn123", "Title here", "2nd edition"))
books.add(new Book("isbn456", "Title here 2", "3nd edition"))
books.add(new Book("isbn789", "Title here 3", "4nd edition"))
SugarRecord.saveInTx(books);
```

### When using ProGuard
```java
# Ensures entities remain un-obfuscated so table and columns are named correctly
-keep class com.yourpackage.yourapp.domainclasspackage.** { *; }
```

## [CHANGELOG](https://github.com/satyan/sugar/blob/master/CHANGELOG.md)

## Contributing

Please fork this repository and contribute back using [pull requests](https://github.com/satyan/sugar/pulls). Features can be requested using [issues](https://github.com/satyan/sugar/issues). All code, comments, and critiques are greatly appreciated.
