# duct-crud-practice

A CRUD application with using duct.

**Warning**

This application doesn't consider SQL Injection Prevention.
You need to consider it to use for production.

## Developing

### Setup

When you first clone this repository, run:

```sh
lein duct setup
```

This will create files for local configuration, and prep your system
for the project.

### Environment

To begin developing, start with a REPL.

```sh
lein repl
```

Then load the development environment.

```clojure
user=> (dev)
:loaded
```

Run `go` to prep and initiate the system.

```clojure
dev=> (go)
:duct.server.http.jetty/starting-server {:port 3000}
:initiated
```

By default this creates a web server at <http://localhost:3000>.

When you make changes to your source files, use `reset` to reload any
modified files and reset the server.

```clojure
dev=> (reset)
:reloading (...)
:resumed
```

## Execute without repl

You can execute system with passing database url.

```clojure
DATABASE_URL="jdbc:postgresql://localhost/duct_crud_practice?user=clojure_user&password=secret" lein run
```

## License

MIT

## References
- [clojureのductでcrudアプリを作る方法](http://asukiaaa.blogspot.jp/2017/12/clojureductcrud.html)
- [Advancing Duct](https://www.booleanknot.com/blog/2017/05/09/advancing-duct.html)
- [Building services with Duct and compojure-api](https://yogthos.net/posts/2015-10-01-Compojure-API.html)
- [How To Install and Use PostgreSQL on Ubuntu 14.04](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-postgresql-on-ubuntu-14-04)
