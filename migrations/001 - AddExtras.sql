CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reportId` int(11) NOT NULL,
  `body` text NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reportId` int(11) NOT NULL,
  `name` text NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reportId` int(11) NOT NULL,
  `name` text NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `voicenote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reportId` int(11) NOT NULL,
  `name` text NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;