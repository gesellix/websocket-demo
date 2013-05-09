## Websocket Demo for AngularJS

*beware of the buzzword parade!*

This project is a playground showing examples with some features of [Atmosphere](https://github.com/Atmosphere/atmosphere) and [Socket.IO](http://socket.io/) with integration in [AngularJS](http://angularjs.org/) and a [Spring Framework](http://www.springsource.org/) based backend... well, for Socket.IO you might expect a [node.js](http://nodejs.org/) backend.

*/buzzword parade*

The initial code comes from the Atmosphere [samples](https://github.com/Atmosphere/atmosphere/tree/master/samples), especially the jQuery focused chat modules. Keeping the Spring backend in mind you might expect an example of [Socket.IO integration in Atmopshere](https://github.com/Atmosphere/atmosphere/wiki/Getting-Started-with-Socket.IO).

### Using the Atmosphere example
- start the webapp by using Jetty: ``mvn org.mortbay.jetty:jetty-maven-plugin:8.1.10.v20130312:run``
- navigate to ``http://localhost:8080/index.html`` to show the initial data, including the uuid for the next step
- open another browser tab or send an HTTP GET to the URL: ``http://localhost:8080/broadcast/`` and append the uuid from above.
- you should see a new entry in the inital data list

