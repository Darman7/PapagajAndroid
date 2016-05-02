<?php
 
/*
 * Stolovi:
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '\db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$id=$_REQUEST['id'];
$result = mysql_query("SELECT sto_id,naziv FROM sto where region_id='$id'") or die(mysql_error());
 
// check for empty result

if (mysql_num_rows($result) > 0) 
{
    $response["sto"] = array();
 
    while ($row = mysql_fetch_array($result)) 
	{
        $product = array(
          'sto_id' => utf8_decode($row['sto_id']),
          'naziv'=>utf8_decode($row['naziv']),
         );
        array_push($response["sto"], $product);
    }
    // success
    //$response["success"] = 1;
    echo json_encode($response);
	
} 
else 
{
    // no products found
    //$response["success"] = 0;
    //$response["message"] = "No groups found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>