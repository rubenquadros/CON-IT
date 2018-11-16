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
    //var newKey = ref.child(key).set(dataJson,onComplete);

    //listen to data updates from firebase
 /*   ref.on("value", function (snapshot){
        console.log(snapshot.val());
        //when the data updates at firebase, put it in the data variable
        data= snapshot.val();
    })*/
/*//Entire Form (handler)
    $('#newActivity').submit(function(event) {

        var $form = $(this);
        console.log("Submit to Firebase");

        //disable submit button
        $form.find("#saveForm").prop('disabled', true);*/

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

        /*//send the new data to Firebase
        ref.set(data, function(err){
            if(err){
                alert("Could not update offers");
            }
            else{
                alert("Successfully updated the offers");
            }
        });*/

        return false;

    /*///Make a login form submission handler
    $('#login').submit(function(event){
        var $form = $(this);
        $form.find('#registerForm').prop('disabled', true);

        //get the value of the login email
        var login = $("#loginInput").val();
        //get the value of the password
        var password = $("#passwordInput").val();

        console.log(login, password);
        register(login, password);

        return false;
    })

    ////Detect the authication state
    var reg = new Firebase("https://data-application1.firebaseio.com");
    reg.onAuth(function(authData){
        console.log("info on authentication");
        if(authData){
            //you are logged in

        }else{
            //you are not logged in

        }
    })
    //////Let a user Register in Firebase
    //////with password/email
    function register(email, password){
        reg.createUser({
            email: email,
            password: password
        }, function(error, userData){
            if(error){
                alert("You did not register");
            }else{
                alert("You registered"+userData.uid);
            }
        })
    }*/
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