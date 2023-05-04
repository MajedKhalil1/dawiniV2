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

// Define fields to insert into the database
$email = $data['Email'];
$password = $data['Password'];

$token = 'success';
header('Content-Type: application/json');
header('Authorization: Bearer ' . $token);

// Create the SQL query string
$sql = "SELECT User_Id, Email, Password FROM users WHERE Email = ? AND Password = ?";
// Prepare the SQL query
$stmt = $conn->prepare($sql);
if (!$stmt) {
  http_response_code(404);
  echo json_encode(['message' => 'Database Error']);
  exit();
}
$stmt->bind_param('ss', $email, $password);
if (!$stmt->execute()) {
  http_response_code(404);
  echo json_encode(['message' => 'Database Error']);
  exit();
}
// Execute the SQL query
$stmt->execute();
// Get the result set
$result = $stmt->get_result();

if ($result->num_rows > 0) {
  $row = $result->fetch_assoc();
  echo json_encode(['token' => $token, 'User_Id'=> $row['User_Id']]);
} else {
  http_response_code(401);
  echo json_encode(['message' => 'Invalid email or password']);
}

// Close the prepared statement and the database connection
$stmt->close();
$conn->close();
?>