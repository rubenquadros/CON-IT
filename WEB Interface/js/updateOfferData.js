function updateData(){

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

    //Get reference of parent node
    var ref = firebase.database().ref("OfferDetailsData");

    //Get unique key
    var uniqueKey = $('#productId').val();

    //Get values of input fields
    var macId = $('#macId').val();
    console.log(macId);

    var productId = $('#productId').val();
    console.log(productId);

    var offerId= $('#offerId').val();
    console.log(offerId);

    //Check for null
    if(macId=="" || productId=="" || offerId==""){
        alert("Enter all fields");
    }
    var jsonData = {
        "beaconId": macId,
        "offerValue":offerId
    };

    ref.child(uniqueKey).update(jsonData, response);
}

var response = function(error) {
    if (error) {
        alert('Operation failed');
    } else {
        alert('Offer Updated Successfully');
        window.location.href="updateOffer.html"
    }
};