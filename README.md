1. Команда для запуска контейнера:
   ``docker run --name JavaSqlPostgresDatabase -p 5442:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=user -d postgres:15``
2. SQL-скрипт для создания таблиц:
```
create table account
(
    id        integer generated always as identity constraint account_pk primary key,
    balance   integer default 0  not null,
    full_name varchar default '' not null
);
```
