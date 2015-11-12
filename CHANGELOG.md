# Sugar Releases

## [Unreleased]

## v1.4 Beta [[jar](https://github.com/satyan/sugar/releases/download/v1.4_beta/sugar-1.4_beta.jar)]
### Added
* [#78](https://github.com/satyan/sugar/pull/78) @HiddenCleverde capability to specify primary key

### Fixed
* [#113](https://github.com/satyan/sugar/pull/113) @whoshuu override findById to support int
* [#106](https://github.com/satyan/sugar/issues/106) @whoshuu add documentation to onTerminate
* [#54](https://github.com/satyan/sugar/issues/54) @whoshuu simplify count interface
* [#43](https://github.com/satyan/sugar/issues/43) @whoshuu return id on save
* [#72](https://github.com/satyan/sugar/issues/72) @whoshuu allow null values to Date and Calendar objects
* [#96](https://github.com/satyan/sugar/issues/96) @whoshuu roboeletric fallback

## v1.3 [[jar](https://github.com/satyan/sugar/releases/download/v1.3/sugar-1.3.jar)]

- Transaction Support
- Bulk Insert of records 
- Encrypted datastore (branch : sugar-cipher using sqlcipher)
- Removed Constructor with context parameter. Needs default constructor now.
- Enhancements to QueryBuilder
- Bug fixes and other improvements.

## v1.2 [[jar](https://github.com/satyan/sugar/releases/download/v1.2/sugar-1.2.jar)]

- package restriction for domain classes.
- metadata caching
- QueryBuilder v1
- Database Migrations
- Provision for Raw queries
- Better and more organized api guide and usage instructions.

## v1.1 [[jar](https://github.com/satyan/sugar/releases/download/v1.1/sugar-1.1.jar)]

- Static api doesn't take context anymore. Hence

```java
Book.findById(context, Book.class, 1);
```

becomes

```java
Book.findById(Book.class, 1);
```

- Some cleanup in the code.
