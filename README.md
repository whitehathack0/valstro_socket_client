# Valstro Socket Client

#### This is my solution to the Valstro take-home Project.

#### I am using the Socket.IO Java client Documention: https://socketio.github.io/socket.io-client-java/installation.html


#### To run:
```
git clone https://github.com/whitehathack0/valstro_socket_client.git
docker run -p 3000:3000 clonardo/socketio-backend
```
- Ensure backend server is running on port 3000
- Ensure JDk 19 is setup as Project JDK (19.0.1)
- Run ```mvn clean install``` in root to install dependencies

- Run the src/main/java/org/valstro/Main.java file in IDE
  
- Open 'Run' tab in IDE to view console to enter input
- To exit program enter "-c"