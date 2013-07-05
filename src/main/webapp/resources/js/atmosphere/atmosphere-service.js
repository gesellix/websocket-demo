(function () {
  'use strict';

  angular.module('atmosphere', ['endpointcontextpaths'])
    .factory('atmosphere', function ($q, $rootScope, $location, websocketDemoContextPath) {

      var _defer = $q.defer();

      var params = $location.search();
      console.log('params: ' + JSON.stringify(params));

      // url=//localhost:8080/websocket-demo/atmosphere
      var url = params.url || websocketDemoContextPath + '/atmosphere';
      // transport=long-polling
      var transport = params.transport || 'websocket';

      var socket = $.atmosphere;
      var subscribedSocket = null;

      var _request = {
        url: url,
        contentType: "application/json",
        logLevel: 'debug',
        transport: transport,
        fallbackTransport: 'long-polling',
        enableProtocol: true};

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
            _defer.resolve({
              "uuid": response.request.uuid,
              "transport": response.transport
            });
          });
        }
      };
      _request.onReconnect = function (request, response) {
        console.log("reconnecting. request: " + JSON.stringify(request));
        if (response.request !== null) {
          _request.isOpen = response.request.isOpen;
        }
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
        onConnect: function (callback) {
          _defer.promise.then(function (data) {
            callback(data);
          });
        },
        onMessage: function (callback) {
          _request.onMessageCallback = callback;
        },
        emit: function (data) {
          _emit(data);
        }
      };
    });
})();
