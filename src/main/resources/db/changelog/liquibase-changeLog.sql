--changeset olexii-dev:1
CREATE TABLE IF NOT EXISTS `Component` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Name` varchar(255) NOT NULL DEFAULT '',
    `ServiceID` bigint(20) NOT NULL DEFAULT '0',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    KEY `fk_component_serviceid` (`ServiceID`),
    CONSTRAINT `fk_component_serviceid` FOREIGN KEY (`ServiceID`) REFERENCES `Service` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ComponentRegionDetails` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `ComponentID` bigint(20) NOT NULL,
    `RegionID` bigint(20) DEFAULT NULL,
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `fk_componentregiondetails_componentid` (`ComponentID`),
    KEY `fk_componentregiondetails_regionid` (`RegionID`),
    CONSTRAINT `fk_componentregiondetails_componentid` FOREIGN KEY (`ComponentID`) REFERENCES `Component` (`ID`) ON DELETE CASCADE,
    CONSTRAINT `fk_componentregiondetails_regionid` FOREIGN KEY (`RegionID`) REFERENCES `Region` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Incident` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `TypeID` bigint(20) DEFAULT NULL,
    `UserID` bigint(20) DEFAULT NULL,
    `ComponentID` bigint(20) DEFAULT NULL,
    `Description` text,
    `StartDate` timestamp,
    `EndDate` timestamp,
    `Status` enum('OPEN','CLOSED') NOT NULL DEFAULT 'OPEN',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    KEY `fk_incident_incidenttypeid` (`TypeID`),
    KEY `fk_incident_userid` (`UserID`),
    KEY `fk_incident_componentid` (`ComponentID`),
    CONSTRAINT `fk_incident_incidenttypeid` FOREIGN KEY (`TypeID`) REFERENCES `IncidentType` (`ID`),
    CONSTRAINT `fk_incident_userid` FOREIGN KEY (`UserID`) REFERENCES `User` (`ID`),
    CONSTRAINT `fk_incident_componentid` FOREIGN KEY (`ComponentID`) REFERENCES `Component` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `IncidentType` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Name` varchar(255) NOT NULL DEFAULT '',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Platform` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Name` varchar(255) NOT NULL DEFAULT '',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Region` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Name` varchar(255) NOT NULL DEFAULT '',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Service` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Name` varchar(255) NOT NULL DEFAULT '',
    `PlatformID` bigint(20) NOT NULL DEFAULT '0',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    KEY `fk_service_platformid` (`PlatformID`),
    CONSTRAINT `fk_service_platformid` FOREIGN KEY (`PlatformID`) REFERENCES `Platform` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `User` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Username` varchar(255) NOT NULL DEFAULT '',
    `Role` enum('ADMIN', 'MANAGER') NOT NULL DEFAULT 'ADMIN',
    `Email` varchar(255) NOT NULL DEFAULT '',
    `Password` varchar(255) NOT NULL DEFAULT '',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
