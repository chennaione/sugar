Please follow the documentation at
http://satyan.github.com/sugar

The example application is here
https://github.com/satyan/sugarexample

# Getting Started:

For standard builds, look for dist jar files in dist directory.
Make your own build by using the ant build files provided. (Update java and android path in the build.properties)

Sugar is now available as a library project also. Add it to your project and utilize the latest changes.
http://developer.android.com/tools/projects/index.html#LibraryProjects

# What's new in 1.1:

1. Static api doesn't take context anymore. Hence

        Book.findById(context, Book.class, 1);

        becomes

        Book.findById(Book.class, 1);


2. Some cleanup in the code.