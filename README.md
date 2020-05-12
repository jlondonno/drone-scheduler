# drone-scheduler
drone-scheculer is an app which is responsible for automate the deliver of requests using drones.  This application reads instructions for plain files and triggers a drone that delivers n requests or packets to the destinations defined on the noted files.

### Prerequisites

To build and run this small app you just need to have **Java 8** installed.

To check that you have Java installed just run this command:

```
javac -version
```

It should show something like: 
`javac 1.8.0_72`

### Execution

To execute the application, please go to the src/main/java/co/com/kimera/dronescheduler/ folder and execute DroneScheduler.java file.  The user has to pass the file with the instructions as parameter.  For instance:

```json
java DroneScheduler /Users/javier/in01.txt /Users/javier/in02.txt /Users/javier/in03.txt
```

Keep in mind you have to compile all the project to execute the previous command.  To do that, please go to the root folder and execute the following command (you have to install Apache Maven if you don't have it)
```json
mvn clean install
```
