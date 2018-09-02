# Project initialization
```
lein new duct duct-crud-practice +ataraxy +example +postgres
cd ~/duct-crud-practice
lein duct setup
```

# Database setup on ubuntu17.10
```
sudo apt install postgresql postgresql-contrib
sudo -i -u postgres
createuser clojure_user -P
# Then enter `secret` as password of clojure_user
createdb duct_crud_practice -O clojure_user
exit
```

Then change `database-url` on `dev/resources/dev.edn`.
```
{:database-url "jdbc:postgresql://localhost/duct_crud_practice?user=clojure_user&password=secret"}
```
