var express = require('express');
var http = require('http');
var socketIo = require('socket.io');
var connect = require('connect');

var app = express();

app.configure(function () {
  app.use(connect.bodyParser());
  app.use(express.logger());
  app.use(express.cookieParser());
  app.use(express.session({ secret: "shhhhhhhhh!"}));
//  app.use(connect.static(__dirname + '/client'));
  app.use(app.router);
});

var server = http.createServer(app);
var io = socketIo.listen(server);

var port = 8081;

server.listen(port);

io.sockets.on('connection', function (socket) {

  socket.on('subscribe', function (userId) {
    setInterval(function () {
      socket.emit('anotherTopic', 'ready');
    }, 500);
  });
});

//A Route for Creating a 500 Error (Useful to keep around)
app.get('/500', function (req, res) {
  throw new Error('This is a 500 Error');
});

//The 404 Route (ALWAYS Keep this as the last route)
app.get('/*', function (req, res) {
  console.log(req.path);
  throw new NotFound;
});

function NotFound(msg) {
  this.name = 'NotFound';
  Error.call(this, msg);
  Error.captureStackTrace(this, arguments.callee);
}

console.log('Listening on http://0.0.0.0:' + port);
