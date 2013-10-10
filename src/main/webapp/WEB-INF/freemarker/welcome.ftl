<!DOCTYPE html>

<html>
  <head>
    <title>Welcome</title>
    <style>
	    .user { width:300px; padding:10px; border:2px solid #66a }
		.logout { float:right; }
		.followingName { border:0; padding-left:0.5cm }
		.hint{ font-weight:bold }
		.tweet { width:300px; padding:10px; border:2px solid #66a }
	</style>
  </head>
<body>

<div class="logout">
	<a href="logout.html">Logout ${model.username}</a>
</div>

<div class="createTweet">
	<a href="tweet.html">Create new tweet</a>
</div>

<div class="following">
	<a href="following.html">Following</a>
</div>

<#if model["timeline"]?size&gt;0>
<p class="hint">This is your timeline:</p>
<#list model["timeline"] as tweet>
	<p class="tweet">${tweet.message}</p>
</#list>
<#else>
<p class="hint">Your timeline is empty.</p>
</#if>

</body>

</html>
