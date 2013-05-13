(function () {
  'use strict';

  angular.module('websocketDemo', ['atmosphere', 'socketio'])
      .controller('atmosphereController', function ($scope, $http, atmosphere) {

                    var initialEntriesReceived = function (initialEntries) {
                      $scope.entries = initialEntries;

//                        atmosphere.emit({query: 'all'});

                      atmosphere.onMessage(function (data) {
                        // force $digest
                        $scope.$apply(function () {
                          var message = $.parseJSON(data.responseBody);
                          $scope.entries.push(message.entry);
                        });
                      });

                      atmosphere.onConnect(function (data) {
                        $scope.connection = {"uuid": data.uuid, "transport": data.transport };
                      });
                    };

                    $scope.connection = {"uuid": "unknown", "transport": "connecting..." };
                    $scope.entries = $http.get('/entries').success(initialEntriesReceived);

                    var triggerUrl = '/entries/triggerAtmospherePush/';
                    var getTargetUuid = function () {
                      return $scope.uuid || $scope.connection.uuid;
                    };
                    $scope.triggerServerPush = function () {
                      $http.get(triggerUrl + getTargetUuid() + "?message=" + $scope.message);
                    }
                  })
      .controller('socketioController', function ($scope, $http, socketio) {

                    var initialEntriesReceived = function (initialEntries) {
                      $scope.entries = initialEntries;

                      socketio.on('subscribed', function (data) {
                        $scope.connection = {"uuid": data.uuid, "transport": data.transport };
                      });
                      socketio.emit('subscribe', "MeMyselfAndI");

                      socketio.on('addEntry', function (data) {
                        $scope.entries.push(data.entry);
                      });
                    };

                    $scope.connection = {"uuid": "unknown", "transport": "connecting..." };
                    $scope.entries = $http.get('/entries').success(initialEntriesReceived);

                    var triggerUrl = 'http://localhost:8081/entries/triggerSocketioPush/';
                    var getTargetUuid = function () {
                      return $scope.uuid || $scope.connection.uuid;
                    };
                    $scope.triggerServerPush = function () {
                      $http.get(triggerUrl + getTargetUuid() + "?message=" + $scope.message);
                    }
                  })
})();
