Please follow the sugar documentation at
http://satyan.github.io/sugar

The example application is provided in the "example" folder in the source.

# Sugar Cipher Changes

Add a meta property named ENCRYPTION_KEY in your manifest file along with other Sugar configuration as follows:

        <meta-data android:name="ENCRYPTION_KEY" android:value="<your own key here>" />
        
Include the SQLCipher libraries and assets from this link:
https://s3.amazonaws.com/sqlcipher/SQLCipher+for+Android+v3.1.0.zip

Extract the libs and assets into your application. This dependency is not included in the sugar library.

You're good to go. Use Sugar as usual and your database is encrypted now.


