<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>This is ftl</title>
</head>
<body>
<center>
    <h1>login success!</h1>
    <span id="data"></span>
</center>
</body>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script type="text/javascript">
    'use strict'
    $.get('/c/realm',function (data) {
        console.info(data);
        $('#data').html(data);
    })
</script>
</html>