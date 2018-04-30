// Retrieve
var MongoClient = require('mongodb').MongoClient;

// Connect to the db
MongoClient.connect("mongodb://localhost:27017/sensores", function(err, db) {
  if(!err) {
    console.log("We are connected");
  }
  
  var myCursor = db.info.find();
while (myCursor.hasNext()) {
	jsonObject=myCursor.next(); //todo o objecto
	print(jsonObject.sensor); //apenas sensor
	printjson(myCursor.next());
}

});

