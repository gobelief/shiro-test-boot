<!DOCTYPE html>
<html lang="en" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>This is ftl</title>
</head>
<body>
<center>
    <h1><shiro:user><shiro:principal property="name"/></shiro:user>login success!</h1>
    <p shiro:user="">
        Welcome back <p>${account}</p>! Not <p>${account}</p>? Click
    </p>
    <p><a href="/c/logout">logout</a></p>
    <span id="data"></span>
</center>
</body>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script type="text/javascript">
    'use strict'
    $.get('/c/realm',function (data) {
        console.info(data);
        $('#data').html(data.user);
    })
</script>
</html>