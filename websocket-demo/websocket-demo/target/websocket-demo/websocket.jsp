<%@ page import="java.util.Random" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        var url = 'ws://' + window.location.host + '/myhandler';
        var sock = new WebSocket(url);
        sock.onopen = function() {
            console.log('Opening');
        };
        sock.onmessage = function(e) {
            $("#myInfoArea").append('<div style="float: left;clear: both">' + e.data +'</div>	');
        };
        sock.onclose = function() {
            console.log('退出连接');
        };

        function myClickHandler()
        {
            var message_to_send = $("#myInputText").val();
            sock.send(message_to_send);
            $("#myInfoArea").append('<div style="float: right;clear: both">' + message_to_send +'</div>	');
        }

    </script>

</head>
<body>
<div>

    <%=session.getAttribute("user")%>
    <%
        session.setAttribute("user",session.getAttribute("user"));
    %>

    <div id="myInfoArea" style="height: 400px;width: 600px;border: 1px solid">

    </div>
    <br>
    <input type="text" name="" id="myInputText">
    <button onclick="myClickHandler()">发送</button>
</div>
</body>
</html>
