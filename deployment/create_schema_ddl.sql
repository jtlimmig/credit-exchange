



CREATE TABLE `partner` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(128) DEFAULT NULL,
  `Description` text,
  `BaseRevenueShare` tinyint(3) DEFAULT NULL,
  `TierRevShare` tinyint(1) DEFAULT NULL,
  `ImageUrl` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `service` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `PartnerID` int(11) unsigned NOT NULL,
  `Name` varchar(128) DEFAULT NULL,
  `Description` text,
  `ApiKey` varchar(128) DEFAULT NULL,
  `InvokeUrl` varchar(256) DEFAULT NULL,
  `RevokeUrl` varchar(256) DEFAULT NULL,
  `Published` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `PartnerID` (`PartnerID`),
  CONSTRAINT `service_ibfk_1` FOREIGN KEY (`PartnerID`) REFERENCES `partner` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `package` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `MigmeProductCode` varchar(128) DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `CurrencyCode` varchar(3) DEFAULT '',
  `Name` varchar(128) DEFAULT NULL,
  `Description` text,
  `ImageUrl` varchar(256) DEFAULT NULL,
  `DiscountPercentage` tinyint(3) DEFAULT NULL,
  `IndexOrder` int(11) DEFAULT NULL,
  `Published` tinyint(1) DEFAULT NULL,
  `CreatedAt` datetime DEFAULT NULL,
  `UpdatedAt` datetime DEFAULT NULL,
  `AllowGifting` tinyint(1) DEFAULT NULL COMMENT 'to support gifting',
  `ContentMimeType` varchar(11) DEFAULT NULL COMMENT 'to support gifting',
  `ContentUrl` varchar(11) DEFAULT NULL COMMENT 'to support gifting',
  `PartnerID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MigmeProductID` (`MigmeProductCode`),
  CONSTRAINT `fk_package_to_partner` FOREIGN KEY (`ID`) REFERENCES `partner` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_service_binding` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ServiceID` int(11) unsigned NOT NULL,
  `MigmeUserID` int(11) unsigned NOT NULL,
  `ServiceUserID` varchar(128) NOT NULL DEFAULT '',
  `CreatedAt` datetime DEFAULT NULL,
  `BoundAt` datetime DEFAULT NULL,
  `UnboundAt` datetime DEFAULT NULL,
  `Suspended` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ServiceID` (`ServiceID`),
  KEY `MigmeUserID` (`MigmeUserID`),
  CONSTRAINT `user_service_binding_ibfk_1` FOREIGN KEY (`ServiceID`) REFERENCES `service` (`ID`),
  CONSTRAINT `user_service_binding_ibfk_2` FOREIGN KEY (`MigmeUserID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pending_deliveries` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `TransactionCode` varchar(128) DEFAULT NULL,
  `ServiceID` int(11) unsigned NOT NULL,
  `MigmeUserID` int(11) unsigned NOT NULL,
  `MigmeRecipientUserID` int(11) DEFAULT NULL COMMENT 'to support gifting',
  `ServiceUserID` varchar(128) NOT NULL DEFAULT '',
  `ServiceRecipientUserID` varchar(128) DEFAULT NULL COMMENT 'to support gifting',
  `ServiceCharacterServer` varchar(128) DEFAULT NULL,
  `ServiceCharacterID` varchar(128) NOT NULL DEFAULT '',
  `PackageID` int(11) unsigned NOT NULL,
  `CurrencyCode` varchar(3) DEFAULT NULL,
  `ReceiptPayload` text,
  `StatusCode` tinyint(4) DEFAULT NULL,
  `ErrorCodeFromService` varchar(128) DEFAULT NULL,
  `CreatedAt` datetime DEFAULT NULL,
  `UpdatedAt` datetime DEFAULT NULL,
  `RetryCount` tinyint(4) DEFAULT NULL,
  `TransactionType` tinyint(3) unsigned DEFAULT NULL COMMENT 'to support gifting',
  PRIMARY KEY (`ID`),
  KEY `TransactionCode` (`TransactionCode`),
  KEY `ServiceID` (`ServiceID`),
  KEY `PackageID` (`PackageID`),
  KEY `MigmeUserID` (`MigmeUserID`),
  CONSTRAINT `pending_deliveries_ibfk_1` FOREIGN KEY (`ServiceID`) REFERENCES `service` (`ID`),
  CONSTRAINT `pending_deliveries_ibfk_2` FOREIGN KEY (`PackageID`) REFERENCES `package` (`ID`),
  CONSTRAINT `pending_deliveries_ibfk_3` FOREIGN KEY (`MigmeUserID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `transaction_history` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `TransactionCode` varchar(128) DEFAULT NULL,
  `ServiceID` int(11) unsigned NOT NULL,
  `MigmeUserID` int(11) unsigned NOT NULL,
  `MigmeRecipientUserID` int(11) DEFAULT NULL,
  `ServiceUserID` varchar(128) NOT NULL DEFAULT '',
  `ServiceRecipientUserID` varchar(128) DEFAULT NULL,
  `ServiceCharacterServer` varchar(128) DEFAULT NULL,
  `ServiceCharacterID` varchar(128) NOT NULL DEFAULT '',
  `PackageID` int(11) unsigned NOT NULL,
  `AmountDeducted` double DEFAULT NULL,
  `CurrencyCode` varchar(3) DEFAULT NULL,
  `ReceiptPayload` text,
  `StatusCode` tinyint(4) DEFAULT NULL,
  `ErrorCodeFromService` varchar(128) DEFAULT NULL,
  `CreatedAt` datetime DEFAULT NULL,
  `UpdatedAt` datetime DEFAULT NULL,
  `TransactionType` tinyint(3) unsigned DEFAULT NULL COMMENT 'to support gifting',
  `PartnerReference` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `TransactionCode` (`TransactionCode`),
  KEY `ServiceID` (`ServiceID`),
  KEY `PackageID` (`PackageID`),
  KEY `MigmeUserID` (`MigmeUserID`),
  CONSTRAINT `transaction_history_ibfk_1` FOREIGN KEY (`ServiceID`) REFERENCES `service` (`ID`),
  CONSTRAINT `transaction_history_ibfk_2` FOREIGN KEY (`PackageID`) REFERENCES `package` (`ID`),
  CONSTRAINT `transaction_history_ibfk_3` FOREIGN KEY (`MigmeUserID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `gift_record` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `TransactionHistoryID` int(11) unsigned NOT NULL,
  `TransactionCode` varchar(128) DEFAULT NULL,
  `ServiceID` int(11) unsigned NOT NULL,
  `MigmeUserID` int(11) unsigned NOT NULL,
  `MigmeRecipientUserID` int(11) DEFAULT NULL,
  `ServiceUserID` varchar(128) NOT NULL DEFAULT '',
  `ServiceRecipientUserID` varchar(128) DEFAULT NULL,
  `ServiceCharacterServer` varchar(128) DEFAULT NULL,
  `ServiceCharacterID` varchar(128) NOT NULL DEFAULT '',
  `PackageID` int(11) unsigned NOT NULL,
  `ReceiptPayload` text,
  `StatusCode` tinyint(4) DEFAULT NULL,
  `ErrorCodeFromService` varchar(128) DEFAULT NULL,
  `CreatedAt` datetime DEFAULT NULL,
  `UpdatedAt` datetime DEFAULT NULL,
  `RetryCount` tinyint(2) DEFAULT NULL,
  `Claimed` tinyint(1) DEFAULT NULL,
  `PartnerReference` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `TransactionCode` (`TransactionCode`),
  KEY `ServiceID` (`ServiceID`),
  KEY `PackageID` (`PackageID`),
  KEY `MigmeUserID` (`MigmeUserID`),
  KEY `TransactionHistoryID` (`TransactionHistoryID`),
  CONSTRAINT `gift_record_ibfk_1` FOREIGN KEY (`ServiceID`) REFERENCES `service` (`ID`),
  CONSTRAINT `gift_record_ibfk_2` FOREIGN KEY (`PackageID`) REFERENCES `package` (`ID`),
  CONSTRAINT `gift_record_ibfk_3` FOREIGN KEY (`MigmeUserID`) REFERENCES `user` (`ID`),
  CONSTRAINT `gift_record_ibfk_4` FOREIGN KEY (`TransactionHistoryID`) REFERENCES `transaction_history` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;