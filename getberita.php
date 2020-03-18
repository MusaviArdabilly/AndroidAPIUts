<?php

include("config.php");

$sql = "SELECT * FROM berita";
$result = array();
$query = mysqli_query($db, $sql);
 
while($row = mysqli_fetch_array($query)){
    array_push($result, array(
    	'id_berita' => $row['id_berita'],
	    'judul_berita' => $row['judul_berita'],
	    'waktu_berita' => $row['waktu_berita'],
	    'penulis_berita' => $row['penulis_berita'],
	    'isi_berita' => $row['isi_berita'],
	));
}
echo json_encode(array("result" => $result));
?>