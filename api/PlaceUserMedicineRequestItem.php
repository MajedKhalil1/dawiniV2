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
$requestMedicineId = $data['requestMedicineId'];
$medicineId = $data['medicineId'];
$medicineQty = $data['medicineQty'];

// Create the SQL query string
$sql = "INSERT INTO request_medicine_items (request_medicine_id, medicine_id, medicine_qty) 
        VALUES (?, ?, ?)";
// Prepare the SQL query
$stmt = $conn->prepare($sql);
if (!$stmt) {
  http_response_code(404);
  echo json_encode(['message' => 'Database Error - Query Failed']);
  exit();
}
$stmt->bind_param('iii', $requestMedicineId, $medicineId, $medicineQty);
if (!$stmt->execute()) {
  http_response_code(404);
  echo json_encode(['message' => 'Database Error']);
  exit();
}

// Return the success response with the inserted ID
http_response_code(201);
echo json_encode(['message' => 'Request medicine item created successfully']);

// Close the prepared statement and the database connection
$stmt->close();
$conn->close();
?>
