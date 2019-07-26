var express = require('express');

var app = express();
var server = app.listen(4000);

console.log("Socket is running");

var socket = require('socket.io');

var io = socket(server);

io.sockets.on('connection',newConnection);



function newConnection(socket){
	console.log("new connection created" + socket.id);
	socket.on('ChatMessage',MessageArrived);

	socket.on('RegisterToken',RegisterToken);


function MessageArrived(data)
{
		// to all except its own
	socket.broadcast.emit('ChatMessage',data);

		// to all including itself
		// io.sockets.emit('EmitUpdates',data);
	console.log(data);
}

function RegisterToken(data)
{
	console.log()
}
}