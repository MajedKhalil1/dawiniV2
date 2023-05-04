<?php
require_once 'DbConnection.php';

$User_Id=$_GET["User_Id"];

$sql="Select * from users where User_Id=" .$User_Id;

$result=$conn -> query($sql);

if($result ->num_rows>0){
    $row=$result ->fetch_assoc();
    echo json_encode($row);
}
else {
     echo "No record found";
}




// Close the database connection
$conn->close();
?>
