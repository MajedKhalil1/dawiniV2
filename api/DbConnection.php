<?php
$servername = "localhost";
$username = "incrowdprod";
$password = "Majed1122";
$dbname = "dawini";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


?>