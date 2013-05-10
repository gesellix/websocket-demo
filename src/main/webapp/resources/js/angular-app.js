(function () {
  'use strict';

  angular.module('websocketDemo', ['atmosphere', 'socketio'])
      .controller('atmosphereController', function ($scope, $http, atmosphere) {

                    var initialEntriesReceived = function (initialEntries) {
                      $scope.entries = initialEntries;

                      atmosphere.onMessage(function (data) {
                        // force $digest
                        $scope.$apply(function () {
                          var message = $.parseJSON(data.responseBody);
//                          if (message.isPartialUpdate !== "undefined" && message.isPartialUpdate === true) {
                            $scope.entries.push(message.entry);
                        });
                      });
                      atmosphere.promise.then(function (data) {
                        $scope.connection = {"uuid": data.uuid, "transport": data.transport };
//                        atmosphere.emit({query: 'all'});
                      });
                    };

                    $scope.connection = {"uuid": "unknown", "transport": "connecting..." };
                    $scope.entries = $http.get('/entries').success(initialEntriesReceived);
                    $scope.triggerServerPush = function () {
                      $http.get('/entries/triggerAtmospherePush/' + $scope.connection.uuid);
                    }
                  })
      .controller('socketioController', function ($scope, $http, socketio) {

                    var initialEntriesReceived = function (initialEntries) {
                      $scope.entries = initialEntries;

                      socketio.emit('subscribe', "MeMyselfAndI");
                      socketio.on('subscribed', function (data) {
                        $scope.connection = {"uuid": data.uuid, "transport": data.transport };
                      });
                      socketio.on('addEntry', function (data) {
                        $scope.entries.push(data.entry);
                      });
                    };

                    $scope.connection = {"uuid": "unknown", "transport": "connecting..." };
                    $scope.entries = $http.get('/entries').success(initialEntriesReceived);
                    $scope.triggerServerPush = function () {
                      $http.get('http://localhost:8081/entries/triggerSocketioPush/' + $scope.connection.uuid);
                    }
                  })
})();
