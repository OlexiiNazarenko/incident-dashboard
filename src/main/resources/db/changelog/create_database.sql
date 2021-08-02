-- liquibase formatted sql
-- changeset olexiidev:create-initial-database splitStatements:true endDelimiter:;
CREATE TABLE IF NOT EXISTS `USER` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Username` varchar(255) NOT NULL DEFAULT '',
    `Role` enum('ROLE_ADMIN', 'ROLE_MANAGER') NOT NULL DEFAULT 'ROLE_ADMIN',
    `Email` varchar(255) NOT NULL DEFAULT '',
    `Password` varchar(255) NOT NULL DEFAULT '',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `REGION` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Name` varchar(255) NOT NULL DEFAULT '',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `PLATFORM` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Name` varchar(255) NOT NULL DEFAULT '',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `SERVICE` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Name` varchar(255) NOT NULL DEFAULT '',
    `PlatformID` bigint(20) NOT NULL DEFAULT '0',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    KEY `fk_service_platformid` (`PlatformID`),
    CONSTRAINT `fk_service_platformid` FOREIGN KEY (`PlatformID`) REFERENCES `PLATFORM` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `COMPONENT` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Name` varchar(255) NOT NULL DEFAULT '',
    `ServiceID` bigint(20) NOT NULL DEFAULT '0',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    KEY `fk_component_serviceid` (`ServiceID`),
    CONSTRAINT `fk_component_serviceid` FOREIGN KEY (`ServiceID`) REFERENCES `SERVICE` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `COMPONENT_REGIONS` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `ComponentID` bigint(20) NOT NULL,
    `RegionID` bigint(20) DEFAULT NULL,
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `fk_componentregiondetails_componentid_regionid` (`ComponentID`, `RegionID`),
    CONSTRAINT `fk_componentregiondetails_componentid` FOREIGN KEY (`ComponentID`) REFERENCES `COMPONENT` (`ID`) ON DELETE CASCADE,
    CONSTRAINT `fk_componentregiondetails_regionid` FOREIGN KEY (`RegionID`) REFERENCES `REGION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `INCIDENT_TYPE` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Name` varchar(255) NOT NULL DEFAULT '',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `INCIDENT` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `TypeID` bigint(20) DEFAULT NULL,
    `ComponentID` bigint(20) DEFAULT NULL,
    `UserID` bigint(20) DEFAULT NULL,
    `Description` text,
    `StartDate` timestamp,
    `EndDate` timestamp,
    `Status` enum('OPEN','CLOSED') NOT NULL DEFAULT 'OPEN',
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LastUpdateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    KEY `fk_incident_incidenttypeid` (`TypeID`),
    KEY `fk_incident_componentid` (`ComponentID`),
    KEY `fk_incident_userid` (`UserID`),
    CONSTRAINT `fk_incident_incidenttypeid` FOREIGN KEY (`TypeID`) REFERENCES `INCIDENT_TYPE` (`ID`),
    CONSTRAINT `fk_incident_userid` FOREIGN KEY (`UserID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `SUBSCRIPTION` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `Email` varchar(255) NOT NULL DEFAULT '',
    `PlatformID` bigint(20) DEFAULT NULL,
    `ServiceID` bigint(20) DEFAULT NULL,
    `ComponentID` bigint(20) DEFAULT NULL,
    `IncidentTypeID` bigint(20) DEFAULT NULL,
    `RegionID` bigint(20) DEFAULT NULL,
    `CreateTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    KEY `fk_subscrription_platformid` (`PlatformID`),
    KEY `fk_subscrription_serviceid` (`ServiceID`),
    KEY `fk_subscrription_componentid` (`ComponentID`),
    KEY `fk_subscrription_incidenttypeid` (`IncidentTypeID`),
    KEY `fk_subscrription_regionid` (`RegionID`),
    CONSTRAINT `fk_subscrription_platformid` FOREIGN KEY (`PlatformID`) REFERENCES `PLATFORM` (`ID`),
    CONSTRAINT `fk_subscrription_serviceid` FOREIGN KEY (`ServiceID`) REFERENCES `SERVICE` (`ID`),
    CONSTRAINT `fk_subscrription_componentid` FOREIGN KEY (`ComponentID`) REFERENCES `COMPONENT` (`ID`),
    CONSTRAINT `fk_subscrription_incidenttypeid` FOREIGN KEY (`IncidentTypeID`) REFERENCES `INCIDENT_TYPE` (`ID`),
    CONSTRAINT `fk_subscrription_regionid` FOREIGN KEY (`RegionID`) REFERENCES `REGION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
