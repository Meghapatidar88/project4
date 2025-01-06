# Use the Tomcat 9 image as a base
FROM tomcat:9

# Copy the WAR file into the Tomcat webapps directory
COPY target/ORS_Project4.war /usr/local/tomcat/webapps/

# Expose port 8080 to the outside world
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
