Please follow the documentation at
http://satyan.github.com/sugar

The example application is provided in the "example" folder in the source.


# Getting Started:

Sugar is now available as a library project also. Add it to your project and utilize the latest changes.
The project is available in the folder "library"
http://developer.android.com/tools/projects/index.html#LibraryProjects

# New in version 1.2

1. package restriction for domain classes.
2. metadata caching
3. QueryBuilder v1
4. Database Migrations
5. Provision for Raw queries
6. Better and more organized api guide and usage instructions.

# What's new in 1.1:

1. Static api doesn't take context anymore. Hence

        Book.findById(context, Book.class, 1);

        becomes

        Book.findById(Book.class, 1);


2. Some cleanup in the code.

