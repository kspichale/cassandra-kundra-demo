<!DOCTYPE html>

<html>
  <head>
    <title>Sign Up</title>
    <style>
	    .user { width:300px; padding:10px; border:2px solid #66a }
		.logout { float:right; }
		.followingName { border:0; padding-left:0.5cm }
		.hint{ font-weight:bold }
		.tweet { width:300px; padding:10px; border:2px solid #66a }
	</style>
  </head>

  <body>
    Already a user? <a href="index.html">Login</a><p>
    <h2>Signup</h2>
    
    ${model.error_message}
    <form name="user" action="signup.html" method="post">
   		<table>
   			<tr><td>Name:</td><td><input type="text" name="name" /></td></tr>
   			<tr><td>Password:</td><td><input type="password" name="password" /></td></tr>
   			<tr><td>Verify Password:</td><td><input type="password" name="verify" /></td></tr>
   			<tr><td>Email (optional):</td><td><input type="text" name="email" /></td></tr>
   		<table>
  	 	<input type="submit" value="   Login   " />
    </form>
    
  </body>

</html>
