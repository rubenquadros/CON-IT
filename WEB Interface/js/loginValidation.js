
function validate() {

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
    var email = $('#user_email').val();
    var pass = $('#user_pass').val();
    var auth = null;

    firebase.auth().signInWithEmailAndPassword(email, pass)
        .then(function(user){
            alert("Successful Login");
            window.location.href = "pages/offers.html";
            auth = user;
        })
        .catch(function(error){
            alert("Could not authenticate user");
            window.location.href = "index.html"
        });
/*    var check = firebase.auth().signInWithEmailAndPassword(email, pass);
    alert(check);*/
}

