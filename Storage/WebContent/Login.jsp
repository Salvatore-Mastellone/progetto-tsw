<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
</head>
<body>

<form action="UserControl?action=login" method="post" onsubmit="return validationEmail()">

  <h1>Login page</h1>
  email: <input type="text" id="email" name="email" placeholder="insert your email here..." onfocus="myFunction(this)"><br><br> 
  password: <input type="password" name="password" placeholder="insert your password here..." onfocus="myFunction(this)"><br><br> 
  <input type="submit" value="Accedi">
</form>
<p> Per registrarti premi qui! <a href ="Registration.jsp">Registrati</a><p>

<script>
function validationEmail() {
  var email = document.getElementById("email").value;
  var chiocciolaPosizione = email.indexOf("@");
  var puntoPosizione = email.lastIndexOf(".");

  if (chiocciolaPosizione < 1 || puntoPosizione < chiocciolaPosizione + 2 || puntoPosizione + 2 >= email.length) {
    alert("Email non valida. Riprova.");
    return false; // ❌ blocca l'invio del form
  }

  return true; // ✅ email valida, invia il form
}

function myFunction(x) {
  x.style.background = "lightblue";
}
</script>

</body>
</html>
