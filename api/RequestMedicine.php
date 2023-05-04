<?php
require_once 'DbConnection.php';

// Read JSON data from POST request body
$json = file_get_contents('php://input');

// Check if the JSON data is valid
if (json_last_error() != JSON_ERROR_NONE) {
  http_response_code(400);
  echo json_encode(['message' => 'Invalid JSON data']);
  exit();
}

// Parse JSON data into an associative array
$data = json_decode($json, true);

// Check if the data is empty
if (empty($data)) {
  http_response_code(400);
  echo json_encode(['message' => 'Empty JSON Data']);
  exit();
}

// Extract values from the JSON data
$requestId = $data['Request_Id'];
$userId = $data['User_Id'];
$status = $data['Status'];
$adresseId = $data['Adresse_ID'];

// Prepare the SQL query string
$sql = "INSERT INTO request_medicine (Request_Id, User_Id, Status, Adresse_ID) VALUES (?, ?, ?, ?)";

// Prepare the SQL query
$stmt = $conn->prepare($sql);

// Check if the SQL query is valid
if (!$stmt) {
  http_response_code(404);
  echo json_encode(['message' => 'Database Error']);
  exit();
}

// Bind the parameters
$stmt->bind_param('iiii', $requestId, $userId, $status, $adresseId);

// Execute the SQL query
if (!$stmt->execute()) {
  http_response_code(404);
  echo json_encode(['message' => 'Database Error']);
  exit();
}

// Return a success response
http_response_code(201);
echo json_encode(['message' => 'Data inserted successfully']);

// Close the prepared statement and the database connection
$stmt->close();
$conn->close();

?>