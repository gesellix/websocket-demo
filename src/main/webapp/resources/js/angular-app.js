(function () {
  'use strict';

  angular.module('websocketDemo', [])
      .controller('entryController', function ($scope, $timeout, atmosphere) {
                    var onMessageReceived = function (data) {
                      // force $digest
                      $scope.$apply(function () {
                        var message = $.parseJSON(data.responseBody);
                        if (message.isPartialUpdate !== "undefined" && message.isPartialUpdate === true) {
                          $scope.entries.push(message.entry);
                        }
                        else {
                          $scope.entries = message;
                        }
                      });
                    };

                    $scope.entries = [];
                    atmosphere.onMessage(onMessageReceived);
                    atmosphere.promise.then(function () {
                      atmosphere.emit({query: 'all'});
                    });
                  })
//      .factory('socketio', function ($rootScope) {
//                 var socket = io.connect('http://localhost');
//                 return {
//                   on: function (eventName, callback) {
//                     socket.on(eventName, function () {
//                       var args = arguments;
//                       $rootScope.$apply(function () {
//                         callback.apply(socket, args);
//                       });
//                     });
//                   },
//                   emit: function (eventName, data, callback) {
//                     socket.emit(eventName, data, function () {
//                       var args = arguments;
//                       $rootScope.$apply(function () {
//                         if (callback) {
//                           callback.apply(socket, args);
//                         }
//                       });
//                     })
//                   }
//                 }
//               })
      .factory('atmosphere', function ($q, $rootScope) {

                 var _defer = $q.defer();

                 var socket = $.atmosphere;
                 var subscribedSocket = null;

                 var _request = {
                   url: '/atmosphere',
                   contentType: "application/json",
                   logLevel: 'debug',
                   transport: 'websocket',
                   fallbackTransport: 'long-polling'};

                 _request.onMessage = function (response) {
                   if (typeof _request.onMessageCallback !== "undefined") {
                     _request.onMessageCallback(response);
                   }
                 };

                 _request.onOpen = function (response) {
                   _request.isOpen = response.request.isOpen;
                   if (_request.isOpen) {
                     // force $digest
                     $rootScope.$apply(function () {
                       _defer.resolve("onOpen");
                     });
                   }
                 };
                 _request.onReconnect = function (request, response) {
                   _request.isOpen = response.request.isOpen;
                 };
                 _request.onClose = function (response) {
                   _request.isOpen = false;
                 };
                 _request.onError = function (response) {
                   console.log("onError: " + $.stringifyJSON(response));
                 };

                 var _subscribe = function () {
                   subscribedSocket = socket.subscribe(_request);
                 };

                 var _emit = function (data) {
                   var dataAsString = $.stringifyJSON(data);
                   if (_request.isOpen) {
                     subscribedSocket.push(dataAsString);
                   }
                   else {
                     console.log("cannot push yet, connection is not open.");
//                     _subscribe();
                   }
                 };

                 _subscribe();

                 return {
                   promise: _defer.promise,
                   onMessage: function (callback) {
                     _request.onMessageCallback = callback;
                   },
                   emit: function (data) {
                     _emit(data);
                   }
                 }
               });
})();
