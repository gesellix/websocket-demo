var express = require('express');
var http = require('http');
var socketIo = require('socket.io');
var connect = require('connect');
//var pouchdbserver = require("pouchdb-server");
//var pouchdbclient = require("pouchdb");
//pouchdbserver.listen(5985);
//var db = Pouch("pouchtest");

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
    var uuid = socket.id;
    socket.emit('subscribed', {"uuid": uuid, "transport": "websocket"});
  });
});

app.get('/entries/triggerSocketioPush/:uuid', function (req, res) {
  var uuid = req.params.uuid;
  res.header('Access-Control-Allow-Origin', '*');
  res.end();

  var socket = io.sockets.socket(uuid);
  socket.emit("addEntry", {"entry": {"id": 23, "text": "pushed via Socket.IO"}})
});

//A Route for Creating a 500 Error (Useful to keep around)
app.get('/500', function (req, res) {
  res.end("500");
});

//The 404 Route (ALWAYS Keep this as the last route)
app.get('/*', function (req, res) {
  console.log(req.path);
  res.end("404");
});

console.log('App listening on http://0.0.0.0:' + port);
