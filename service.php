<?php

function reArrayFiles(&$file_post) {

	$file_ary = array();
	$file_count = count($file_post['name']);
	$file_keys = array_keys($file_post);

	for ($i=0; $i<$file_count; $i++) {
		foreach ($file_keys as $key) {
			$file_ary[$i][$key] = $file_post[$key][$i];
		}
	}

	return $file_ary;
}

if(isset($_GET['action']) || isset($_POST['action'])) {
	$action = isset($_GET['action']) ? $_GET['action'] : $_POST[action];
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
				$categoryId = $_POST['categoryId'];
				$userId = $_POST['userId'];
				$longitude = $_POST['longitude'];
				$latitude = $_POST['latitude'];
				$status = $_POST['status'];
				$query = "INSERT INTO `report`(`categoryId`, `userId`, `longitude`, `latitude`, `status`) VALUES ($categoryId, '$userId', $longitude, $latitude, $status)";
				$result = $db->exec($query);
				header('Content-type: application/json');
				if ($result == 0)
				{
					echo -1;
					break;
				}
				
				$reportId = $db->lastInsertId();
				
				echo json_encode(array('id'=>$reportId));
				
				$comments = $_POST['comments'];
				$query = "INSERT INTO comment (reportId, body) VALUES ";
				foreach ($comments as $comment) {
					$query = $query . "($reportId, '$comment'),";
				}
				$query = rtrim($query,",");
				$result = $db->exec($query);
				
				if ($_FILES['images']) {
					$images = array();
					$file_ary = reArrayFiles($_FILES['images']);
					$query = "INSERT INTO image (reportId, name) VALUES ";
					foreach ($file_ary as $file) {
						$tmp_name = $file["tmp_name"];
						$name = $reportId . "_" . $file["name"];
						
						array_push($images, $name);
						move_uploaded_file($tmp_name, "./images/$name");
						$query = $query . "($reportId, '$name'),";
					}
					$query = rtrim($query,',');
					$db->exec($query);
				}
				
				if ($_FILES['files']) {
					$files = array();
					$file_ary = reArrayFiles($_FILES['files']);
					$query = "INSERT INTO file (reportId, name) VALUES ";
					foreach ($file_ary as $file) {
						$tmp_name = $file["tmp_name"];
						$name = $reportId . "_" . $file["name"];
						
						array_push($files, $name);
						move_uploaded_file($tmp_name, "./files/$name");
						$query = $query . "($reportId, '$name'),";
					}
					$query = rtrim($query,',');
					$db->exec($query);
				}
				
				if ($_FILES['voicenotes']) {
					$voicenotes = array();
					$file_ary = reArrayFiles($_FILES['voicenotes']);
					$query = "INSERT INTO voicenote (reportId, name) VALUES ";
					foreach ($file_ary as $file) {
						$tmp_name = $file["tmp_name"];
						$name = $reportId . "_" . $file["name"];
						
						array_push($voicenotes, $name);
						move_uploaded_file($tmp_name, "./voicenotes/$name");
						$query = $query . "($reportId, '$name'),";
					}
					$query = rtrim($query,',');
					$db->exec($query);
				}
				
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
				
			case "getImage":
				$name = $_GET['name'];
				echo file_get_contents("./images/$name");
				
			case "getVoicenote":
				$name = $_GET['name'];
				echo file_get_contents("./voicenotes/$name");
				
			case "getFile":
				$name = $_GET['name'];
				echo file_get_contents("./files/$name");
			
		}
	} catch (PDOException $e) {
    	echo $e;
	}
}
?>