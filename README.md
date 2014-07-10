# Sugar ORM

Insanely easy way to work with Android databases.

Official documentation can be found [here](http://satyan.github.io/sugar). The example application is provided in the **example** folder in the source.

## Features

Sugar ORM was built in contrast to other ORM's to have:

1. A simple, concise, and clean integration process with minimal configuration.
2. Automatic table and column naming through reflection.
3. Support for migrations between different schema versions.

## Installing

There are four ways to install Sugar:

#### As a Gradle dependency

This is the preferred way. Simply add:

```groovy
compile 'com.github.satyan:sugar:1.3'
```

to your project dependencies and run `gradle build` or `gradle assemble`.

#### As a Maven dependency

Declare the dependency in Maven:

```xml
<dependency>
    <groupId>com.github.satyan</groupId>
    <artifactId>sugar</artifactId>
    <version>1.3</version>
</dependency>
```

#### As a library project

Download the source code and import it as a library project in Eclipse. The project is available in the folder **library**. For more information on how to do this, read [here](http://developer.android.com/tools/projects/index.html#LibraryProjects).

#### Use a jar

Visit the [releases](https://github.com/satyan/sugar/releases) page to download jars directly. You can drop them into your `libs` folder and configure the Java build path to include the library. See this [tutorial](http://www.vogella.com/tutorials/AndroidLibraryProjects/article.html) for an excellent guide on how to do this.

===================

After installing, check out how to set up your first database and models [here](http://satyan.github.io/sugar/getting-started.html).

## Contributing

Please fork this repository and contribute back using [pull requests](https://github.com/satyan/sugar/pulls). Features can be requested using [issues](https://github.com/satyan/sugar/issues). All code, comments, and critiques are greatly appreciated.

## Changelog

#### v1.3 [[jar](https://github.com/satyan/sugar/releases/download/v1.3/sugar-1.3.jar)]

1. Transaction Support
2. Bulk Insert of records 
3. Encrypted datastore (branch : sugar-cipher using sqlcipher)
4. Removed Constructor with context parameter. Needs default constructor now.
5. Enhancements to QueryBuilder
6. Bug fixes and other improvements.

#### v1.2 [[jar](https://github.com/satyan/sugar/releases/download/v1.2/sugar-1.2.jar)]

1. package restriction for domain classes.
2. metadata caching
3. QueryBuilder v1
4. Database Migrations
5. Provision for Raw queries
6. Better and more organized api guide and usage instructions.

#### v1.1 [[jar](https://github.com/satyan/sugar/releases/download/v1.1/sugar-1.1.jar)]

1. Static api doesn't take context anymore. Hence

```java
Book.findById(context, Book.class, 1);
```

becomes

```java
Book.findById(Book.class, 1);
```

2. Some cleanup in the code.
