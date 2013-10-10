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

<div>
	<a href="addFollower.html">Add Follower</a>
</div>

<#if model["following"]?size&gt;0>
<p class="hint">You are following these users:</p>
<#list model["following"] as user>
	<form name="following" action="following.html" method="post">
		<input type="text" name="name" value="${user.name}" class="followingName" readonly/>
		<input type="submit" value="   Remove   " />
	</form>
</#list>
<#else>
<p class="hint">You are not following anyone.</p>
</#if>

<br/>
<div>
	<a href="welcome.html">Back</a>
</div>

</body>

</html>
