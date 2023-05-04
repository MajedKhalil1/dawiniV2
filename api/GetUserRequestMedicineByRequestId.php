<?php
require_once 'DbConnection.php';

// Check if the Request_Id parameter is set in the URL
if (!isset($_GET['request_Id'])) {
  http_response_code(400);
  echo json_encode(['message' => 'RequestId parameter is missing']);
  exit();
}

// Sanitize the Request_Id parameter to prevent SQL injection
$requestId = mysqli_real_escape_string($conn, $_GET['request_Id']);

// Create the SQL query string
$sql = "SELECT rm.Request_Id, CONCAT(u.FirstName, ' ', u.LastName) AS 'To', CONCAT(u2.FirstName, ' ', u2.LastName) AS 'From', 
               medicine.Medicine_Name as 'Requested Medicine', CONCAT(a.City, ' ' , a.Street, ' ', a.Bldg, ' ' , a.Floor)  as Addresse
        FROM request_medicine rm
            INNER JOIN request_medicine_items rmi ON rmi.request_medicine_id = rm.request_id
            INNER JOIN medicine ON medicine.Medicine_Id = rmi.medicine_id
            INNER JOIN deliver_medicine dm ON dm.Request_id = rm.Request_Id
            INNER JOIN users u ON u.User_Id = rm.User_Id
            INNER JOIN users u2 ON u2.User_Id = dm.User_Id
            INNER JOIN adress a on a.Adress_Id = rm.Adresse_ID 
        WHERE rm.Request_Id = $requestId";

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
