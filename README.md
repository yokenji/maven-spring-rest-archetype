# Maven Spring MVC Archetype [![NPM version][npm-image]][npm-url] [![Build Status][travis-image]][travis-url] [![Dependency Status][daviddm-image]][daviddm-url]
> Generate Spring MVC Project

## maven-spring-mvc-archetype
Spring MVC archetype

## Installation 
First make sure you have maven installed, then go into the directory of the generator and install the archetype using this command.

```bash
mvn install
```

Then generate a project by going into your workspace and executing the bash script found at : 
    https://github.com/yokenji/spring-mvc-gen.sh 

with a groupId (-g) and an artifact id (-a)



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
