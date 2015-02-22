var wsUri = "ws://127.0.0.1:8080/AppServer/end";
var websocket = new WebSocket(wsUri);
alert(wsUri);
websocket.onerror = function(evt) { onError(evt) };

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}