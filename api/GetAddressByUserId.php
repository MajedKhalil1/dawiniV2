<?php
require_once 'DbConnection.php';

// Get the userId from the URL parameter
$userId = $_GET['userId'];

if (!isset($_GET['userId'])) {
    http_response_code(400);
    echo json_encode(['message' => 'userId parameter is missing or invalid json format']);
    exit();
  }
// Create the SQL query string
$sql = "SELECT * FROM adress WHERE User_id = ?";
// Prepare the SQL query
$stmt = $conn->prepare($sql);
if (!$stmt) {
  http_response_code(404);
  echo json_encode(['message' => 'Database Error - Query Failed']);
  exit();
}

// Bind the userId parameter to the prepared statement
$stmt->bind_param('s', $userId);

// Execute the SQL query
$stmt->execute();
// Get the result set
$result = $stmt->get_result();

// Create an empty array to hold the records
$address = [];

// Loop through the result set and add each record to the array
while ($row = $result->fetch_assoc()) {
  $address[] = $row;
}


// Set the Content-Type header to indicate that we're returning JSON
header('Content-Type: application/json');
http_response_code(200);
echo json_encode(['address' => $address]);

$stmt->close();
$conn->close();
?>
