## Websocket Demo for AngularJS

*beware of the buzzword parade!*

This project is a playground showing examples with some features of [Atmosphere](https://github.com/Atmosphere/atmosphere) and [Socket.IO](http://socket.io/) with integration in [AngularJS](http://angularjs.org/) and a [Spring Framework](http://www.springsource.org/) based backend... well, for Socket.IO you might expect a [node.js](http://nodejs.org/) backend.

*/buzzword parade*

The initial code comes from the Atmosphere [samples](https://github.com/Atmosphere/atmosphere/tree/master/samples), especially the jQuery focused chat modules. Keeping the Spring backend in mind you might see an example of [Socket.IO integration in Atmopshere](https://github.com/Atmosphere/atmosphere/wiki/Getting-Started-with-Socket.IO) soon.

### Using the examples
Both frameworks live on the same web page, but have dedicated Angular controllers. You'll see two columns for Atmosphere and Socket.IO, respectively.

Therefore, the codebase is tightly coupled (evil me), so that you have to configure and run at least the Java backend. It contains the Atmosphere example code and is used to deliver the static content for both frameworks.

You can run the Java backend by using Jetty:
```mvn org.mortbay.jetty:jetty-maven-plugin:8.1.10.v20130312:run
```

The Socket.IO backend can be started using the provided server.js file:
```node /src/main/js/socketio/server.js
```

After running the backend(s),  navigate to
```http://localhost:8080/index.html
```
You should see an overview on both websocket connections and initial data with 4 entries for each connection. Above the entry lists, you can use the buttons to trigger a server side push, so that new entries should appear in the lists.

### Notes
The code isn't clean.
The code isn't stable.


There is no error handling.
There aren't any tests.

Both will be added soon.

Useful hints are always welcome!
