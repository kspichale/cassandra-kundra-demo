<!DOCTYPE html>

<html>
  <head>
    <title>Post New Tweet</title>
    <style>
	    .user { width:300px; padding:10px; border:2px solid #66a }
		.logout { float:right; }
		.followingName { border:0; padding-left:0.5cm }
		.hint{ font-weight:bold }
		.tweet { width:300px; padding:10px; border:2px solid #66a }
	</style>
   </head>

<body>

<div class="logout"><a href="logout.html">Logout ${model.username}</a></div>

<form name="tweet" action="postTweet.html" method="post">
	<input type="hidden" name="author" value="${model.username}"/>
	<textarea class="tweet" name="message" ></textarea>
	<br/>
	<input type="submit" value="   Post a new tweet   " />
</form>

<br/>
<div>
	<a href="welcome.html">Back</a>
</div>

</body>

</html>
