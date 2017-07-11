var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/ws-root');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        
        $.ajax({ 
        	   type: "GET",
        	   dataType: "jsonp",
        	   url: "http://localhost:8080/async/all",
        	   success: function(data){        
        	     console.log("fetch users async call done "+data);
        	   }
        	});
        
        stompClient.subscribe('/ws-topic/user', function (greeting) {
        	console.log('Message from server: ' + greeting);
            showGreeting(JSON.parse(greeting.body));
        });
        
        stompClient.subscribe('/ws-topic/time', function (greeting) {
            showTime(greeting.body);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
	stompClient.send("/ws-in/add", {}, JSON.stringify({'name': $("#name").val(),'email':$("#email").val()}));
}

function showGreeting(message) {
	 $("#greetings").html("");
	for(var i = 0; i < message.length; i++) {
	    var obj = message[i];
	    $("#greetings").append("<tr><td>" + obj.name + "</td><td>" + obj.email + "</td></tr>");
	  
	}
	
}

function showTime(message) {
	$("#time").html(message);
}


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});