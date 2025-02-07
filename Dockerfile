FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=password1234
ENV MYSQL_DATABASE=testdb
ENV MYSQL_USER=alo
ENV MYSQL_PASSWORD=commonpass123

EXPOSE 3306

COPY ./init.sql /docker-entrypoint-initdb.d/

CMD ["mysqld"]
