function register() {
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
    var ref = firebase.database().ref("UserDetailsData");

    //Get unique key
    ref.push().getKey();

    //Get values of input fields
    var orgName = $('#org_name').val();
    console.log(orgName);

    var custEmail = $('#cust_email').val();
    console.log(custEmail);

    var mobileNo= $('#mob_no').val();
    console.log(mobileNo);

    var custPass= $('#cust_pass').val();
    console.log(custPass);
    var confPass= $('#conf_pass').val();
    console.log(config);

    //Check for null
    if(orgName=="" || custEmail=="" || mobileNo=="" || custPass=="" || confPass==""){
        alert("Enter all fields");
    }
    else if(custPass!=confPass){
        alert("Passwords do not match");
    }

    //take the values from the form, and put them in an object
    var dataJson= {
        "orgName": orgName,
        "custMail": custEmail,
        "mobileNo": mobileNo
    };

    //create new user
    firebase.auth().createUserWithEmailAndPassword(custEmail, custPass)
    .catch(function (err) {
        if (err) {
            alert('Please check your details');
        }
        else{
            alert('hello');
        }
        });

    //Send to db
    ref.push(dataJson, getCallBack);

    return false;
}

var getCallBack =  function(error) {
    if (error) {
        alert('Could not register user');
    } else {
        alert('User registered successfully');
        window.location.href="index.html"
    }
};
