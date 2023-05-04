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
$userId = $data['userId'];
$addressId = $data['addressId'];
$status = $data['status'];

// Prepare the SQL query string
$sql = "INSERT INTO request_medicine (User_Id, Status, Adresse_ID) VALUES (?, ?, ?)";

// Prepare the SQL query
$stmt = $conn->prepare($sql);

// Check if the SQL query is valid
if (!$stmt) {
  http_response_code(404);
  echo json_encode(['message' => 'Database Error SQL Query Invalid']);
  exit();
}

// Bind the parameters
$stmt->bind_param('isi', $userId, $status, $addressId);

// Execute the SQL query
if (!$stmt->execute()) {
  http_response_code(404);
  echo json_encode(['message' => 'Database Error']);
  exit();
}

// Get the request ID
$requestId = $stmt->insert_id;

// Return a success response with the request ID
http_response_code(201);
echo json_encode(['message' => 'Request inserted successfully', 'requestId' => $requestId]);

// Close the prepared statement and the database connection
$stmt->close();
$conn->close();

?>
