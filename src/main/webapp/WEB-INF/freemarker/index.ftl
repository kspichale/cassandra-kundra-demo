<!DOCTYPE html>

<html>
  <head>
    <title>Index</title>
    <style>
	    .user { width:300px; padding:10px; border:2px solid #66a }
		.logout { float:right; }
		.followingName { border:0; padding-left:0.5cm }
		.hint{ font-weight:bold }
		.tweet { width:300px; padding:10px; border:2px solid #66a }
	</style>
  </head>

  <body>
    Need to Create an account? <a href="signup.html">Signup</a><p>
    <h2>Login</h2>
    
    ${model.error_message}
    <form name="user" action="login.html" method="post">
       	<table>
   			<tr><td>Name:</td><td><input type="text" name="name" /></td></tr>
   			<tr><td>Password:</td><td><input type="password" name="password" /></td></tr>
   		<table>
  	 	<input type="submit" value="   Login   " />
    </form>
  
  </body>

</html>
