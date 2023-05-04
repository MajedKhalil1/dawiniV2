<?php
require_once 'DbConnection.php';

    // Read JSON data from POST request body
$json = file_get_contents('php://input');
// Parse JSON data into an associative array
$data = json_decode($json, true);
// Define fields to insert into the database
$fields = array('FirstName', 'LastName', 'Email', 'Password');
// Initialize an array to hold the field values
$values = array();
$token='success';
// Loop through each field and get its value from the data array
foreach ($fields as $field) {
    if (isset($data[$field])) {
        $values[] = $data[$field];
    } else {
        $values[] = '';
    }
}
// Create the SQL query string
$sql = 'INSERT INTO users (' . implode(', ', $fields) . ') VALUES ("' . implode('", "', $values) . '")';
// Execute the SQL query
if ($conn->query($sql) === TRUE) {
    echo json_encode(['token' => $token]);
} else {
    echo 'Error: ' . $sql . '<br>' . $conn->error;
}
// Close the database connection
$conn->close();


?>











