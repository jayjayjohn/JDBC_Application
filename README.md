# JDBC_Application
a java program that perform query against a database. 

# Core Java
    -  Source_code 
    
We will use Eclipse IDE to execute the application.
It is assumed you have basic knowledge of Eclipse or any IDE, so no detailed steps provided. 
To run the application, import entire project zip file to Eclipse using ‘Archive File’ import. Add mysql-connector jar file to the library by right click on the project and click ‘configure build path’, add connector as external jar. Ensure login.property file is located in the project directory (same level as src folder). This is the file that contain database information that you want to connect. If it is located at other directory, ensure to specify full path in OperationUI class. getConnection Method.
You may change the password or username in the file according to your database.  

# RDBMS/mySQL Description:  
    -	CDW_SAPP.sql  
mySQL database in used in the core java application. The CDW_SAPP sql script is attached in the db folder along with jave applicatipn folder.  To run the script, open cmd prompt. First, cd to the folder that contain CDW_SAPP.sql. 
Second, login to your db server by running ‘mysql -r <username> -p’ and enter your password . Then, type command ‘source CDW_SAPP.sql’ . This will create all database and tables required. This is the database that connected to java application through JDBC.  
Tables: 
  CDW_SAPP_BRANCH
  CDW_SAPP_CUSTOMER
  CDW_SAPP_CREDITCARD 
