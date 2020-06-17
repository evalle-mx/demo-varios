 CREATE TABLE track_item (
 id_track_item bigint NOT NULL,
 itunes_id_track varchar(8),
 file_path varchar(300),
 descripcion varchar(150),
 track_name varchar(150),
 track_artist varchar(150),
 track_number smallint,
 track_year smallint,
 disc_name varchar(150),
 disc_number smallint,
 album varchar(150),
 id_genero bigint,
 json varchar(1000)
 ) ; 

CREATE TABLE genero (
 id_genero bigint NOT NULL,
 descripcion varchar(50)
 ); 

INSERT INTO genero VALUES
(1,'Otro (N/D)'),
(2,'Acústico'),
(3,'Alternative & Punk'),
(4,'Balada'),
(5,'Banda'),
(6,'Blues/R&B'),
(7,'Boleros'),
(8,'Bossa Nova'),
(9,'Brazilian'),
(10,'Chillout & Lounge & Downtempo'),
(11,'Christmas'),
(12,'Clásica'),
(13,'Country'),
(14,'Cumbia'),
(15,'Dance'),
(16,'Easy Listening'),
(17,'Electrónica'),
(18,'Folk'),
(19,'Grunge'),
(20,'Hard Rock'),
(21,'Heavy Metal'),
(22,'Hip-Hop'),
(23,'House'),
(24,'Indie'),
(25,'Infantil'),
(26,'Instrumental'),
(27,'Jazz'),
(28,'Latin'),
(29,'Ligera'),
(30,'Mariachi'),
(31,'Meditación'),
(32,'Metal'),
(33,'Miscellaneous'),
(34,'New Age'),
(35,'Opera'),
(36,'Pop'),
(37,'Pop-Electrónico'),
(38,'Pop-Folk'),
(39,'Pop-Funk'),
(40,'Pop-Rock'),
(41,'Prehispanico'),
(42,'Progressive'),
(43,'Rap'),
(44,'Rock'),
(45,'Rock alternativo'),
(46,'Rock Argentino'),
(47,'Rock clásico'),
(48,'Rock Gothico'),
(49,'Rock Instrumental'),
(50,'Románticas'),
(51,'Salsa'),
(52,'Ska'),
(53,'Slow Jam'),
(54,'Soul'),
(55,'SoundTrack (OST)'),
(56,'Techno'),
(57,'Tejano'),
(58,'Tradicional Mexicana'),
(59,'Trance'),
(60,'Trova'),
(61,'Vocal'),
(62,'World Music');