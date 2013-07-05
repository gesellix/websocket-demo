(function () {
  'use strict';

  angular.module('pubnub', [])
    .factory('pubnub', function ($rootScope) {

      // Subscribe Only mode
//      var pubnub = PUBNUB.init({ subscribe_key: 'demo' });
      // Publish enabled mode
      var pubnub = PUBNUB.init({
        publish_key: 'demo',
        subscribe_key: 'demo'
      });

      var _on = function (eventName, messageCallback) {
        pubnub.subscribe({
          channel: eventName,
          restore: true,
          message: function (message, env, channel) {
            console.log("message received: " + message);
            $rootScope.$apply(function () {
              messageCallback(message, env, channel);
            });
          },
          presence: function (message, env, channel) {
            console.log("presence detected: '" + message + "' on channel: '" + channel + "'");
          },
          connect: function () {
            console.log("connect");
          },
          disconnect: function () {
            console.log("disconnect");
          },
          reconnect: function () {
            console.log("reconnect");
          },
          error: function (data) {
            console.log("error: " + data);
          }
        });
      };
      var _emit = function (eventName, data) {
        pubnub.publish({
          channel: eventName,
          message: data
        });
      };

      return {
        on: function (eventName, callback) {
          _on(eventName, callback);
        },
        emit: function (eventName, data) {
          _emit(eventName, data);
        }
      };
    });
})();
