@echo OFF

cd "C:\Users\ceban\Desktop\Claude\db-derby-10.14.1.0-bin\db-derby-10.14.1.0-bin\bin"

start cmd /k call startNetworkServer.bat


cd "C:\Users\ceban\Desktop\Claude\db-derby-10.14.1.0-bin\db-derby-10.14.1.0-bin\bin"

start cmd /k call ij.bat  
  
  
   ::Create the database and open a connection to the database using 

:: CONNECT 'jdbc:derby://localhost:1527/iam-core;create=true' USER 'root' PASSWORD 'password';

	

   ::  run sql command and import the database schema
   
:: run 'C:\Users\ceban\Desktop\Claude\2017s2_fundamental_b\Epita Java Project\iam-core\sql\init.sql'


cd "C:\Users\ceban\Desktop\"
java -jar  -Dconf=testConfiguration.properties iamcore.jar






