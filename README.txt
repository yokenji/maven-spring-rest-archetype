# maven-spring-mvc-archetype
Spring MVC archetype

# Fields to fill in 
file : resources/dev/database.properties
    fields : password

file : resources/prod/database.properties, resources/test/database.properties
    fields: password, database-name, server-ip/name, login
    
file : resources/prod/log4j2.properties, resources/test/log4j2.properties
    fields: graylogHost, graylogPort

file : resources/*/mail.properties
    fields: mailingHost, mailingFrom

For first run set hibernate.run.script = true in resources/dev/database.properties, disable afterwards