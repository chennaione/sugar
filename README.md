Please follow the documentation at
http://satyan.github.com/sugar

# What's new in 1.1:

1. Static api doesn't take context anymore. Hence
        Book.findById(context, Book.class, 1);
        becomes
        Book.findById(Book.class, 1);

2. Some cleanup in the code.