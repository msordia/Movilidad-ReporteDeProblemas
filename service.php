<?php

if(isset($_GET['action'])) {
	$action = $_GET['action'];
	try {
		$db = new PDO('mysql:host=localhost:3306;dbname=reporte6_movilidad', "reporte6_user", "pass");
		switch ($action) {
			case "getReports":
				$query = "SELECT * FROM report";
				$result = $db->query($query);
				if (!$result) {
					die(print_r($db->errorInfo()));
				}
				$result = $result->fetchAll(PDO::FETCH_ASSOC);
				$reports = array();
				foreach ($result as $report) {
					$reports[] = array('report'=>$report);
				 } 
				header('Content-type: application/json');
				echo json_encode(array('reports'=>$reports));
				break;

			case "getReport":
				$id = $_GET['id'];
				$query = "SELECT * FROM report where id = $id";
				$result = $db->query($query);
				if (!$result) {
					die(print_r($db->errorInfo()));
				}
				$result = $result->fetchAll(PDO::FETCH_ASSOC);
				header('Content-type: application/json');
				echo json_encode(array('report'=>$result[0]));
				break;

			case "deleteReport":
				$id = $_GET['id'];
				$query = "DELETE FROM report where id = $id";
				$result = $db->exec($query);
				header('Content-type: application/json');
				echo json_encode(array('rows'=>$result));
				break;

			case "addReport":
				$categoryId = $_GET['categoryId'];
				$userId = $_GET['userId'];
				$longitude = $_GET['longitude'];
				$latitude = $_GET['latitude'];
				$status = $_GET['status'];
				$query = "INSERT INTO `report`(`categoryId`, `userId`, `longitude`, `latitude`, `status`) VALUES ($categoryId, '$userId', $longitude, $latitude, $status)";
				$result = $db->exec($query);
				header('Content-type: application/json');
				if ($result == 0)
				{
					echo -1;
					break;
				}
				echo json_encode(array('id'=>$db->lastInsertId()));
				break;

			case "getCategories":
				$query = "SELECT * FROM category";
				$result = $db->query($query);
				if (!$result) {
					die(print_r($db->errorInfo()));
				}
				$result = $result->fetchAll(PDO::FETCH_ASSOC);
				$categories = array();
				foreach ($result as $category) {
					$categories[] = array('category'=>$category);
				 } 
				header('Content-type: application/json');
				echo json_encode(array('categories'=>$categories));
				break;

			case "getCategory":
				$id = $_GET['id'];
				$query = "SELECT * FROM category where id = $id";
				$result = $db->query($query);
				if (!$result) {
					die(print_r($db->errorInfo()));
				}
				$result = $result->fetchAll(PDO::FETCH_ASSOC);
				header('Content-type: application/json');
				echo json_encode(array('category'=>$result[0]));
				break;

			case "deleteCategory":
				$id = $_GET['id'];
				$query = "DELETE FROM category where id = $id";
				$result = $db->exec($query);
				header('Content-type: application/json');
				echo json_encode(array('rows'=>$result));
				break;

			case "addCategory":
				$name = $_GET['name'];
				$query = "INSERT INTO `category`(`name`) VALUES ('$name')";
				$result = $db->exec($query);
				header('Content-type: application/json');
				if ($result == 0)
				{
					echo -1;
					break;
				}
				echo json_encode(array('id'=>$db->lastInsertId()));
				break;

			case "reportsForUser":
				$user = $_GET['userId'];
				$query = "SELECT * FROM report where userId = '$user'";
				$result = $db->query($query);
				if (!$result) {
					die(print_r($db->errorInfo()));
				}
				$result = $result->fetchAll(PDO::FETCH_ASSOC);
				$reports = array();
				foreach ($result as $report) {
					$reports[] = array('report'=>$report);
				 } 
				header('Content-type: application/json');
				echo json_encode(array('reports'=>$reports));
				break;

			case "isAdmin":
				$user = $_GET['userId'];
				$query = "SELECT COUNT(*) FROM admin where id = '$user'";
				$nRows = $db->query($query)->fetchColumn();
				echo json_encode(array('isAdmin'=>$nRows > 0));
				break;

			case "makeAdmin":
				$userId = $_GET['userId'];
				$query = "INSERT INTO `admin`(`id`) VALUES ('$userId')";
				$result = $db->exec($query);
				header('Content-type: application/json');
				if ($result == 0)
				{
					echo -1;
					break;
				}
				echo json_encode(array('id'=>$db->lastInsertId()));
				break;

			case "removeAdmin":
				$userId = $_GET['userId'];
				$query = "DELETE FROM admin where id = '$userId'";
				$result = $db->exec($query);
				header('Content-type: application/json');
				echo json_encode(array('rows'=>$result));
				break;

			case "commentsForReport":
				$report = $_GET['reportId'];
				$query = "SELECT * FROM comment where reportId = '$report'";
				$result = $db->query($query);
				if (!$result) {
					die(print_r($db->errorInfo()));
				}
				$result = $result->fetchAll(PDO::FETCH_ASSOC);
				$comments = array();
				foreach ($result as $comment) {
					$comments[] = array('comment'=>$comment);
				 } 
				header('Content-type: application/json');
				echo json_encode(array('comments'=>$comments));
				break;

			case "voicenotesForReport":
				$report = $_GET['reportId'];
				$query = "SELECT * FROM voicenote where reportId = '$report'";
				$result = $db->query($query);
				if (!$result) {
					die(print_r($db->errorInfo()));
				}
				$result = $result->fetchAll(PDO::FETCH_ASSOC);
				$voicenotes = array();
				foreach ($result as $voicenote) {
					$voicenotes[] = array('voicenote'=>$voicenote);
				 } 
				header('Content-type: application/json');
				echo json_encode(array('voicenotes'=>$voicenotes));
				break;

			case "imagesForReport":
				$report = $_GET['reportId'];
				$query = "SELECT * FROM image where reportId = '$report'";
				$result = $db->query($query);
				if (!$result) {
					die(print_r($db->errorInfo()));
				}
				$result = $result->fetchAll(PDO::FETCH_ASSOC);
				$images = array();
				foreach ($result as $image) {
					$images[] = array('image'=>$image);
				 } 
				header('Content-type: application/json');
				echo json_encode(array('images'=>$image));
				break;

			case "filesForReport":
				$report = $_GET['reportId'];
				$query = "SELECT id, reportId, bytes FROM file where reportId = '$report'";
				$result = $db->query($query);
				if (!$result) {
					die(print_r($db->errorInfo()));
				}
				$result = $result->fetchAll(PDO::FETCH_ASSOC);
				$files = array();
				foreach ($result as $file) {
					$files[] = array('file'=>$file);
				 } 
				header('Content-type: application/json');
				echo json_encode(array('files'=>$files));
				break;

			case "addComment":
				$reportId = $_GET['reportId'];
				$body = $_GET['body'];
				$query = "INSERT INTO `comment`(`reportId`, `body`) VALUES ($reportId,'$body')";
				$result = $db->exec($query);
				header('Content-type: application/json');
				if ($result == 0)
				{
					echo -1;
					break;
				}
				echo json_encode(array('id'=>$db->lastInsertId()));
				break;

			case "addImage":
				$reportId = $_GET['reportId'];
				$bytes = $_GET['bytes'];
				$query = "INSERT INTO `image`(`reportId`, `bytes`) VALUES ($reportId,'$bytes')";
				$result = $db->exec($query);
				header('Content-type: application/json');
				if ($result == 0)
				{
					echo -1;
					break;
				}
				echo json_encode(array('id'=>$db->lastInsertId()));
				break;

			case "addVoicenote":
				$reportId = $_GET["reportId"];
				$bytes = $_GET['bytes'];
				$query = "INSERT INTO `voicenote`(`reportId`, `bytes`) VALUES ($reportId,'$bytes')";
				$result = $db->exec($query);
				header('Content-type: application/json');
				if ($result == 0)
				{
					echo -1;
					break;
				}
				echo json_encode(array('id'=>$db->lastInsertId()));
				break;

			case "addFile":
				$reportId = $_GET["reportId"];
				$bytes = $_GET['bytes'];
				$name = $_GET['name'];
				$query = "INSERT INTO `file`(`reportId`, `bytes`, `name`) VALUES ($reportId,'$bytes', '$name')";
				$result = $db->exec($query);
				header('Content-type: application/json');
				if ($result == 0)
				{
					echo -1;
					break;
				}
				echo json_encode(array('id'=>$db->lastInsertId()));
				break;

			case "getFile":
				$id = $_GET['id'];
				$query = "SELECT * FROM file where id = $id";
				$result = $db->query($query);
				if (!$result) {
					die(print_r($db->errorInfo()));
				}
				$result = $result->fetchAll(PDO::FETCH_ASSOC);
				header('Content-type: application/json');
				echo json_encode(array('file'=>$result[0]));
				break;
				
			case "updateStatus":
				$id = $_GET['id'];
				$status = $_GET['status'];
				$query = "UPDATE report set status = $status where id = $id";
				$result = $db->exec($query);
				header('Content-type: application/json');
				if ($result == 0)
				{
					echo -1;
				} else {
					echo 1;
				}
				break;
		}
	} catch (PDOException $e) {
    	echo $e;
	}
}
?>