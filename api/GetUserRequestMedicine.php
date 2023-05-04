<?php
require_once 'DbConnection.php';

// Create the SQL query string
$sql = "SELECT
        RM.request_id,
        CONCAT(U.FirstName, ' ', U.LastName) AS `To`,
        CONCAT(GROUP_CONCAT(CONCAT(RMI.medicine_qty, ' ',M.Medicine_name) SEPARATOR ', ')) AS Medicine_details
        FROM Request_medicine RM
           JOIN Users U ON RM.User_id = U.User_id
           JOIN Request_medicine_items RMI ON RM.request_id = RMI.request_medicine_id
           JOIN Medicine M ON RMI.medicine_id = M.medicine_id
      WHERE
      RM.Status = 1
      GROUP BY
      RM.request_id,
      'To';";

// Execute the SQL query
$result = $conn->query($sql);

// Check if the query was successful
if (!$result) {
  http_response_code(404);
  echo json_encode(['message' => 'Database Error - Query Failed']);
  exit();
}

// Convert the result set into an associative array
$rows = [];
while ($row = $result->fetch_assoc()) {
  $rows[] = $row;
}

$response = ['developer_message' => 'Query successful', 
             'user_message' => 'Pending requests retrieved successfully',
             'pendingRequests' => $rows];

// Return the data as JSON
header('Content-Type: application/json');
http_response_code(200);
echo json_encode($response);

// Close the database connection
$conn->close();
?>
