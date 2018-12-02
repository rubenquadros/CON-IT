var key;

function pushData() {

    //Get Config of app
    var config = {
        apiKey: "AIzaSyAldLcsawstw64hp3CeyMlhOy0BSM4mFpk",
        authDomain: "con-it.firebaseapp.com",
        databaseURL: "https://con-it.firebaseio.com",
        projectId: "con-it",
        storageBucket: "con-it.appspot.com",
        messagingSenderId: "514080071006"
    };
    firebase.initializeApp(config);
    //make a variable to keep track of data coming from firebase
    var data= [];

    //create new connection to firebase
    var ref = firebase.database().ref("OfferDetailsData");
    key = getRandomNum();

    //get values to send to Firebase
    var beaconId = $('#beaconId').val();
    console.log(beaconId);

    var beaconName = $('#beaconName').val();
    console.log(beaconName);

    var shopName= $('#ShopName').val();
    console.log(shopName);

     var productName= $('#ProductName').val();
     console.log(productName);
     var offerValue= $('#offervalue').val();
     console.log(offerValue);

      //Check for null
      if(beaconId=="" || beaconName=="" || shopName=="" || productName=="" || offerValue==""){
            alert("Enter all fields");
       }
       //take the values from the form, and put them in an object
       var dataJson= {
            "beaconId": beaconId,
            "beaconName": beaconName,
            "shopName": shopName,
            "productName": productName,
            "offerValue": offerValue
       };
       //put new object in data array
       ref.child(key).set(dataJson,onComplete);
    
       console.log(data);
       return false;
}
var onComplete =  function(error) {
    if (error) {
        alert('Operation failed');
    } else {
        alert('Offer Added. Your Product ID is:'+ key);
        window.location.href="offers.html"
    }
};

function getRandomNum() {
    return Math.floor(Math.random() * 10000)+1;
}
function logOut(){
    window.location.href="../index.html";
}
