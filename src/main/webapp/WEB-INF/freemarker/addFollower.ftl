<!DOCTYPE html>

<html>
  <head>
    <title>Add follower</title>
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

<form name="searchFollower" action="searchFollower.html" method="post">
	<input type="text" name="name" value="${model.searchText}">
  	<input type="submit" value="   Search User   " />
</form>

<#if model["users"]?size&gt;0>
<p>Found users:</p>
<#list model["users"] as user>
	<form name="following" action="addNewFollower.html" method="post">
		<input type="text" name="name" value="${user.name}" class="followingName" readonly/>
		<input type="submit" value="   Add   " />
	</form>
</#list>
</#if>

<br/>
<div>
	<a href="following.html">Back</a>
</div>

</body>

</html>
