(function () {
  'use strict';

  angular.module('socketio', [])
      .factory('socketio', function ($rootScope) {
                 var socket = io.connect('http://localhost:8081');
                 var _on = function (eventName, callback) {
                   socket.on(eventName, function () {
                     var args = arguments;
                     $rootScope.$apply(function () {
                       callback.apply(socket, args);
                     });
                   });
                 };
                 var _emit = function (eventName, data, callback) {
                   socket.emit(eventName, data, function () {
                     var args = arguments;
                     $rootScope.$apply(function () {
                       if (callback) {
                         callback.apply(socket, args);
                       }
                     });
                   })
                 };

                 return {
                   on: function (eventName, callback) {
                     _on(eventName, callback);
                   },
                   emit: function (eventName, data, callback) {
                     _emit(eventName, data, callback);
                   }
                 }
               });
})();
