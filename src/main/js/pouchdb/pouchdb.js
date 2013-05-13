var pouchdbserver = require("pouchdb-server");
//var pouchdb = require("pouchdb");

var printJson = function (data) {
  return JSON.stringify(data, undefined, 2);
};

var pouchPort = 5985;
pouchdbserver.listen(pouchPort);
console.log('Pouch lying on http://0.0.0.0:' + pouchPort);

// API: http://pouchdb.com/api.html
var testdata = Pouch('http://localhost:' + pouchPort + '/testdata', {});

testdata.info(function (err, info) {
  console.log("db info:\n" + printJson(info));
});

testdata.compact({}, function () {
  console.log(printJson(arguments));
});

testdata.query(
    {
      map: function (doc) {
        if (doc.title) {
          emit(doc.title, null);
        }
      }
    },
    {
      reduce: false
    },
    function (err, response) {
      console.log("query response:\n" + printJson(response));
      for (var i = 0; i < response.total_rows; i++) {
        testdata.get(response.rows[i].id, function (err, response) {
          console.log("get response:\n" + printJson(response));
          testdata.remove(response, function (err, response) {
            console.log("remove response:\n" + printJson(response));
          });
        });
      }
    });

//testdata.post({ title: 'Cony Island Baby' }, function (err, response) {
//  console.log("post response:\n" + printJson(response));
//});
