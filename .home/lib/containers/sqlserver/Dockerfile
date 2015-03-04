# docker build -t sqlserver .
# docker run -p 1433:1433 -it sqlserver
FROM mcr.microsoft.com/mssql/server:2019-latest
ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=SQLSERVERPASS
WORKDIR /usr/src/app
USER root
RUN apt-get update && apt-get install -y curl apt-transport-https gnupg2
RUN curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add -
RUN curl https://packages.microsoft.com/config/debian/9/prod.list > /etc/apt/sources.list.d/mssql-release.list
RUN apt-get update
RUN ACCEPT_EULA=Y apt-get install -y mssql-tools unixodbc-dev
USER mssql
COPY SQLSERVERDB.bak /var/opt/mssql/backups/
EXPOSE 1433
CMD /opt/mssql/bin/sqlservr & sleep 20 && /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P 'SQLSERVERPASS' -Q "RESTORE DATABASE [SQLSERVERDB] FROM DISK = '/var/opt/mssql/backups/SQLSERVERDB.bak' WITH MOVE 'SQLSERVERDB' TO '/var/opt/mssql/data/SQLSERVERDB.mdf', MOVE 'SQLSERVERDB_log' TO '/var/opt/mssql/data/SQLSERVERDB_log.ldf'" && tail -f /dev/null
