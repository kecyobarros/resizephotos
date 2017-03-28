# Resize photos – SkyHub challenge

Application responsible for resizing in three formats:
SMALL (320x240), MEDIUM (384x288), LARGE (640x480)

# To Execute
A compose file has been made available to create the mongoDB container.

Archive: **infra/compose/docker-compose.yml**

1. Run the file with the following **docker-compose up -d** command inside the infra/compose.
2. Run **mvn clean install** in the project root folder.
3. Run the command **java -jar target/resizephotos.jar**
4. If you do not have the **JAVA_HOME** variable in your path then run the class **ResizephotosApplication.java** for your ide.

# Endpoint documentation
http://localhost:8090/resizephotos/swagger-ui.html

# Choice of language
- I choose Java because of the experience and domain, addition to the features available in Java 8 what facilitate development.