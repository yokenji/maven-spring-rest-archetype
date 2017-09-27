# Maven Spring MVC Archetype 
> Generate Spring MVC REST Project

## Installation 
First make sure you have [maven](https://maven.apache.org/) installed, then go into the directory of the generator and install the archetype using this command.

```bash
mvn install
```

Then generate a project by going into your workspace and executing the bash script found at : 
    https://github.com/yokenji/spring-mvc-gen.sh 

with a groupId (-g) and an artifact id (-a).   
This will generate an eclipse spring mvc project



## Empty Fields
file : resources/dev/database.properties  
    fields : password  

file : resources/prod/database.properties, resources/test/database.properties  
    fields: password, database-name, server-ip/name, login  
    
file : resources/prod/log4j2.properties, resources/test/log4j2.properties  
    fields: graylogHost, graylogPort  

file : resources/*/mail.properties  
    fields: mailingHost, mailingFrom  

For first run set hibernate.run.script = true in resources/dev/database.properties, disable afterwards
