SET foreign_key_checks = 0;

ALTER TABLE FORM_TRANSFER_NUMS DROP FOREIGN KEY FK_FORM_TRANSFER_NUMS_FormTransfer_ID;
ALTER TABLE A7_FORM CHANGE CREATED_BY CREATED_BY VARCHAR(50);
ALTER TABLE A7_FORM CHANGE ID ID VARCHAR(50);
ALTER TABLE A7_FORM CHANGE MODIFIED_BY MODIFIED_BY VARCHAR(50);
ALTER TABLE A7_FORM CHANGE OWNER_ID OWNER_ID VARCHAR(50);
ALTER TABLE COMPANY DROP FULL_NAME;
ALTER TABLE COMPANY CHANGE ID ID VARCHAR(50);
CREATE TABLE COMPANY_OWNER
(
  COMPANY_ID VARCHAR(50) NOT NULL,
  OWNER_ID   VARCHAR(50) NOT NULL,
  PRIMARY KEY (COMPANY_ID, OWNER_ID)
);
ALTER TABLE CONTACT CHANGE AFFILIATION_ID AFFILIATION_ID VARCHAR(50);
ALTER TABLE CONTACT CHANGE CELL_PHONE CELL_PHONE VARCHAR(20);
ALTER TABLE CONTACT CHANGE CITY CITY VARCHAR(30);
ALTER TABLE CONTACT CHANGE CREATED_BY CREATED_BY VARCHAR(50);
ALTER TABLE CONTACT CHANGE EMAIL EMAIL VARCHAR(50);
ALTER TABLE CONTACT CHANGE ID ID VARCHAR(50);
ALTER TABLE CONTACT CHANGE MODIFIED_BY MODIFIED_BY VARCHAR(50);
ALTER TABLE CONTACT CHANGE NAME NAME VARCHAR(50);
ALTER TABLE CONTACT CHANGE POST_INDEX POST_INDEX VARCHAR(6);
ALTER TABLE CONTACT CHANGE REGION REGION VARCHAR(50);
ALTER TABLE CONTACT ADD WWW VARCHAR(50) NULL;
ALTER TABLE CONTACT_CODE CHANGE CODE CODE VARCHAR(35);
ALTER TABLE CONTACT_CODE CHANGE CONTACT_ID CONTACT_ID VARCHAR(50);
ALTER TABLE CONTACT_CODE CHANGE CREATED_BY CREATED_BY VARCHAR(50);
ALTER TABLE CONTACT_CODE CHANGE ID ID VARCHAR(50);
ALTER TABLE CONTACT_CODE CHANGE MODIFIED_BY MODIFIED_BY VARCHAR(50);
ALTER TABLE CONTACT_CODE CHANGE TYPE TYPE VARCHAR(20);
CREATE TABLE CONTACT_EMPLOYEE
(
  CONTACT_ID  VARCHAR(50) NOT NULL,
  EMPLOYEE_ID VARCHAR(50) NOT NULL,
  PRIMARY KEY (CONTACT_ID, EMPLOYEE_ID)
);
ALTER TABLE FORM_TRANSFER CHANGE CREATED_BY CREATED_BY VARCHAR(50);
ALTER TABLE FORM_TRANSFER CHANGE FROMCONTACT_ID FROMCONTACT_ID VARCHAR(50);
ALTER TABLE FORM_TRANSFER CHANGE ID ID VARCHAR(50);
ALTER TABLE FORM_TRANSFER CHANGE MODIFIED_BY MODIFIED_BY VARCHAR(50);
ALTER TABLE FORM_TRANSFER CHANGE TOCONTACT_ID TOCONTACT_ID VARCHAR(50);
ALTER TABLE FORM_TRANSFER_NUMS ADD FORM_TRANSFER_ID VARCHAR(50) NULL;
ALTER TABLE FORM_TRANSFER_NUMS DROP FormTransfer_ID;
ALTER TABLE INSURANCE CHANGE CLIENT_ID CLIENT_ID VARCHAR(50);
ALTER TABLE INSURANCE ADD COVER_TIME INT NULL;
ALTER TABLE INSURANCE CHANGE CREATED_BY CREATED_BY VARCHAR(50);
ALTER TABLE INSURANCE CHANGE DEALER_ID DEALER_ID VARCHAR(50);
ALTER TABLE INSURANCE CHANGE ID ID VARCHAR(50);
ALTER TABLE INSURANCE CHANGE MODIFIED_BY MODIFIED_BY VARCHAR(50);
ALTER TABLE INSURANCE CHANGE MOTOR_BRAND MOTOR_BRAND VARCHAR(50);
ALTER TABLE INSURANCE CHANGE MOTOR_MODEL MOTOR_MODEL VARCHAR(50);
ALTER TABLE INSURANCE CHANGE MOTOR_TYPE MOTOR_TYPE VARCHAR(50);
ALTER TABLE INSURANCE CHANGE PREMIUM PREMIUM DECIMAL(32, 4);
ALTER TABLE INSURANCE CHANGE RISK_SUM RISK_SUM DECIMAL(32, 4);
CREATE TABLE LEAD
(
  ID            VARCHAR(50) PRIMARY KEY NOT NULL,
  COMMENT       VARCHAR(255),
  CONTACT_EMAIL VARCHAR(255),
  CONTACT_NAME  VARCHAR(255),
  CONTACT_PHONE VARCHAR(255),
  CREATED_AT    DATETIME,
  CREATED_BY    VARCHAR(50),
  MODIFIED_AT   DATETIME,
  MODIFIED_BY   VARCHAR(50),
  MOTOR_BRAND   VARCHAR(255),
  MOTOR_MODEL   VARCHAR(255),
  MOTOR_PRICE   DECIMAL(32, 4),
  MOTOR_TYPE    VARCHAR(255),
  POINT_OF_SALE VARCHAR(255),
  PROCESS_ID    VARCHAR(255),
  REGION        VARCHAR(255),
  RESULT        VARCHAR(255),
  STATUS        VARCHAR(255),
  VERSION       INT,
  CLIENT_ID     VARCHAR(50),
  VENDOR_ID     VARCHAR(50)
);
CREATE TABLE LEGAL_ENTITY
(
  ID          VARCHAR(50) PRIMARY KEY NOT NULL,
  INN         VARCHAR(15),
  OGRN_OGRIP  VARCHAR(15),
  COMPANY_ID  VARCHAR(50),
  DIRECTOR_ID VARCHAR(50)
);
CREATE TABLE LEGAL_ENTITY_MOTOR_BRAND
(
  LegalEntity_ID VARCHAR(50),
  MOTORBRANDS    VARCHAR(255)
);
CREATE TABLE LEGAL_ENTITY_PROD_CREDIT
(
  LEGAL_ENTITY_ID VARCHAR(50) NOT NULL,
  PROD_CREDIT_ID  VARCHAR(50) NOT NULL,
  PRIMARY KEY (LEGAL_ENTITY_ID, PROD_CREDIT_ID)
);
ALTER TABLE PAY_ACCOUNT CHANGE BANK_CODE BANK_CODE VARCHAR(35);
ALTER TABLE PAY_ACCOUNT CHANGE BANK_NAME BANK_NAME VARCHAR(50);
ALTER TABLE PAY_ACCOUNT CHANGE CONTACT_ID CONTACT_ID VARCHAR(50);
ALTER TABLE PAY_ACCOUNT CHANGE CREATED_BY CREATED_BY VARCHAR(50);
ALTER TABLE PAY_ACCOUNT CHANGE ID ID VARCHAR(50);
ALTER TABLE PAY_ACCOUNT CHANGE LORO_ACCOUNT LORO_ACCOUNT VARCHAR(35);
ALTER TABLE PAY_ACCOUNT CHANGE MODIFIED_BY MODIFIED_BY VARCHAR(50);
ALTER TABLE PAY_ACCOUNT CHANGE SETTLEMENT_ACCOUNT SETTLEMENT_ACCOUNT VARCHAR(35);
ALTER TABLE PERSON ADD HOME_PHONE VARCHAR(20) NULL;
ALTER TABLE PERSON CHANGE ID ID VARCHAR(50);
ALTER TABLE PERSON CHANGE JOB_DEPARTMENT JOB_DEPARTMENT VARCHAR(50);
ALTER TABLE PERSON CHANGE JOB_POSITION JOB_POSITION VARCHAR(15);
ALTER TABLE PERSON CHANGE PASS_ISSUED_BY_NUM PASS_ISSUED_BY_NUM VARCHAR(10);
ALTER TABLE PERSON CHANGE PASS_NUM PASS_NUM VARCHAR(30);
ALTER TABLE PERSON ADD PASS_REG_ADRESS VARCHAR(255) NULL;
ALTER TABLE PERSON CHANGE SEX SEX VARCHAR(6);
ALTER TABLE PERSON ADD WORK_PHONE VARCHAR(20) NULL;
ALTER TABLE POLICY CHANGE CREATED_BY CREATED_BY VARCHAR(50);
ALTER TABLE POLICY CHANGE ID ID VARCHAR(50);
ALTER TABLE POLICY CHANGE MODIFIED_BY MODIFIED_BY VARCHAR(50);
CREATE TABLE PROD_CREDIT
(
  ID              VARCHAR(50) PRIMARY KEY NOT NULL,
  DEALER_SUBSIDY  DECIMAL(32, 4),
  MAX_DOWNPAYMENT DECIMAL(32, 4),
  MAX_PERIOD      INT,
  MAX_SUM         DECIMAL(32, 4),
  MIN_DOWNPAYMENT DECIMAL(32, 4),
  MIN_PERIOD      INT,
  MIN_SUM         DECIMAL(32, 4),
  PROGRAM_TYPE    VARCHAR(255),
  STEP            INT
);
CREATE TABLE PROD_CREDIT_DOC
(
  ID          VARCHAR(50) PRIMARY KEY NOT NULL,
  CREATED_AT  DATETIME,
  CREATED_BY  VARCHAR(50),
  MODIFIED_AT DATETIME,
  MODIFIED_BY VARCHAR(50),
  NAME        VARCHAR(255),
  IS_REQUIRED BIT DEFAULT 0,
  VERSION     INT,
  PRODUCT_ID  VARCHAR(50)
);
CREATE TABLE PROD_CREDIT_PERCENT
(
  ID          VARCHAR(50) PRIMARY KEY NOT NULL,
  CREATED_AT  DATETIME,
  CREATED_BY  VARCHAR(50),
  DOWNPAYMENT DECIMAL(32, 4),
  MODIFIED_AT DATETIME,
  MODIFIED_BY VARCHAR(50),
  PERCENT     DECIMAL(32, 4),
  PERIOD      INT,
  VERSION     INT,
  PRODUCT_ID  VARCHAR(50)
);
CREATE TABLE PROD_INSTALLMENTS
(
  ID              VARCHAR(50) PRIMARY KEY NOT NULL,
  MAX_PERIOD      INT,
  MAX_SUM         DECIMAL(32, 4),
  MIN_DOWNPAYMENT DECIMAL(32, 4)
);
CREATE TABLE PROD_INSURANCE
(
  ID      VARCHAR(50) PRIMARY KEY NOT NULL,
  PERCENT DECIMAL(32, 4)
);
CREATE TABLE PRODUCT
(
  ID          VARCHAR(50) PRIMARY KEY NOT NULL,
  TYPE        VARCHAR(31),
  IS_ACTIVE   BIT DEFAULT 0,
  COMMENT     VARCHAR(255),
  CREATED_AT  DATETIME,
  CREATED_BY  VARCHAR(50),
  MODIFIED_AT DATETIME,
  MODIFIED_BY VARCHAR(50),
  NAME        VARCHAR(255),
  VERSION     INT,
  VENDOR_ID   VARCHAR(50)
);
CREATE TABLE PRODUCT_IN_SALE
(
  ID          VARCHAR(50) PRIMARY KEY NOT NULL,
  CREATED_AT  DATETIME,
  CREATED_BY  VARCHAR(50),
  DOWNPAYMENT DECIMAL(32, 4),
  MODIFIED_AT DATETIME,
  MODIFIED_BY VARCHAR(50),
  PERIOD      INT,
  SUMM        DECIMAL(32, 4),
  VERSION     INT,
  SALE_ID     VARCHAR(50),
  PRODUCT_ID  VARCHAR(50)
);
CREATE TABLE SALE
(
  ID          VARCHAR(50) PRIMARY KEY NOT NULL,
  COMMENT     VARCHAR(255),
  CREATED_AT  DATETIME,
  CREATED_BY  VARCHAR(50),
  MODIFIED_AT DATETIME,
  MODIFIED_BY VARCHAR(50),
  MOTOR_BRAND VARCHAR(255),
  MOTOR_MODEL VARCHAR(255),
  MOTOR_PRICE DECIMAL(32, 4),
  MOTOR_TYPE  VARCHAR(255),
  PROCESS_ID  VARCHAR(255),
  REGION      VARCHAR(255),
  RESULT      VARCHAR(255),
  STATUS      VARCHAR(255),
  VERSION     INT,
  CLIENT_ID   VARCHAR(50),
  DEALER_ID   VARCHAR(50)
);
CREATE TABLE SALE_POINT
(
  ID           VARCHAR(50) PRIMARY KEY NOT NULL,
  ALPHA_CODE   VARCHAR(15),
  EXTA_CODE    VARCHAR(15),
  HOME_CODE    VARCHAR(15),
  SETELEM_CODE VARCHAR(15),
  COMPANY_ID   VARCHAR(50)
);
CREATE TABLE SALEPOINT_LEGALENTITY
(
  SALEPOINT_ID   VARCHAR(50) NOT NULL,
  LEGALENTITY_ID VARCHAR(50) NOT NULL,
  PRIMARY KEY (SALEPOINT_ID, LEGALENTITY_ID)
);
ALTER TABLE USER_GROUP CHANGE CREATED_BY CREATED_BY VARCHAR(50);
ALTER TABLE USER_GROUP CHANGE ID ID VARCHAR(50);
ALTER TABLE USER_GROUP CHANGE MODIFIED_BY MODIFIED_BY VARCHAR(50);
ALTER TABLE USER_GROUP CHANGE NAME NAME VARCHAR(50);
ALTER TABLE USER_GROUP_LINK CHANGE groupList_ID groupList_ID VARCHAR(50);
ALTER TABLE USER_GROUP_LINK CHANGE UserProfile_ID UserProfile_ID VARCHAR(50);
ALTER TABLE USER_GROUP_PERMISSION CHANGE UserGroup_ID UserGroup_ID VARCHAR(50);
ALTER TABLE USER_PROFILE CHANGE BLOCKED BLOCKED BIT;
ALTER TABLE USER_PROFILE CHANGE CHANGE_PASSWORD CHANGE_PASSWORD BIT;
ALTER TABLE USER_PROFILE CHANGE CONTACT_ID CONTACT_ID VARCHAR(50);
ALTER TABLE USER_PROFILE CHANGE CREATED_BY CREATED_BY VARCHAR(50);
ALTER TABLE USER_PROFILE CHANGE ID ID VARCHAR(50);
ALTER TABLE USER_PROFILE CHANGE LOGIN LOGIN VARCHAR(50);
ALTER TABLE USER_PROFILE CHANGE MODIFIED_BY MODIFIED_BY VARCHAR(50);
ALTER TABLE USER_PROFILE CHANGE PASSWORD_SALT PASSWORD_SALT VARCHAR(30);
CREATE INDEX INDEX_A7_FORM_OWNER_ID_STATUS_REG_NUM ON A7_FORM (OWNER_ID, STATUS, REG_NUM);
ALTER TABLE COMPANY_OWNER ADD FOREIGN KEY (COMPANY_ID) REFERENCES CONTACT (ID);
ALTER TABLE COMPANY_OWNER ADD FOREIGN KEY (OWNER_ID) REFERENCES CONTACT (ID);
CREATE INDEX INDEX_CONTACT_NAME ON CONTACT (NAME);
CREATE INDEX INDEX_CONTACT_TYPE_NAME ON CONTACT (TYPE, NAME);
CREATE UNIQUE INDEX INDEX_CONTACT_CODE_TYPE_CODE ON CONTACT_CODE (TYPE, CODE);
ALTER TABLE CONTACT_EMPLOYEE ADD FOREIGN KEY (EMPLOYEE_ID) REFERENCES CONTACT (ID);
ALTER TABLE CONTACT_EMPLOYEE ADD FOREIGN KEY (CONTACT_ID) REFERENCES CONTACT (ID);
ALTER TABLE FORM_TRANSFER_NUMS
ADD CONSTRAINT FK_FORM_TRANSFER_NUMS_FORM_TRANSFER_ID
FOREIGN KEY (FORM_TRANSFER_ID) REFERENCES FORM_TRANSFER (ID);
CREATE INDEX INDEX_FORM_TRANSFER_NUMS_FORM_TRANSFER_ID_FORMNUMS ON FORM_TRANSFER_NUMS (FORM_TRANSFER_ID, FORMNUMS);
CREATE INDEX INDEX_INSURANCE_A7_NUM ON INSURANCE (A7_NUM);
CREATE INDEX INDEX_INSURANCE_REG_NUM ON INSURANCE (REG_NUM);
ALTER TABLE LEAD ADD FOREIGN KEY (CLIENT_ID) REFERENCES CONTACT (ID);
ALTER TABLE LEAD ADD FOREIGN KEY (VENDOR_ID) REFERENCES CONTACT (ID);
ALTER TABLE LEGAL_ENTITY ADD FOREIGN KEY (COMPANY_ID) REFERENCES CONTACT (ID);
ALTER TABLE LEGAL_ENTITY ADD FOREIGN KEY (DIRECTOR_ID) REFERENCES CONTACT (ID);
ALTER TABLE LEGAL_ENTITY ADD FOREIGN KEY (ID) REFERENCES CONTACT (ID);
ALTER TABLE LEGAL_ENTITY_MOTOR_BRAND ADD FOREIGN KEY (LegalEntity_ID) REFERENCES CONTACT (ID);
ALTER TABLE LEGAL_ENTITY_PROD_CREDIT ADD FOREIGN KEY (LEGAL_ENTITY_ID) REFERENCES CONTACT (ID);
ALTER TABLE LEGAL_ENTITY_PROD_CREDIT ADD FOREIGN KEY (PROD_CREDIT_ID) REFERENCES PRODUCT (ID);
CREATE INDEX INDEX_POLICY_ISSUE_DATE_BOOK_TIME_REG_NUM ON POLICY (ISSUE_DATE, BOOK_TIME, REG_NUM);
ALTER TABLE PROD_CREDIT ADD FOREIGN KEY (ID) REFERENCES PRODUCT (ID);
ALTER TABLE PROD_CREDIT_DOC ADD FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID);
ALTER TABLE PROD_CREDIT_PERCENT ADD FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID);
ALTER TABLE PROD_INSTALLMENTS ADD FOREIGN KEY (ID) REFERENCES PRODUCT (ID);
ALTER TABLE PROD_INSURANCE ADD FOREIGN KEY (ID) REFERENCES PRODUCT (ID);
ALTER TABLE PRODUCT ADD FOREIGN KEY (VENDOR_ID) REFERENCES CONTACT (ID);
ALTER TABLE PRODUCT_IN_SALE ADD FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID);
ALTER TABLE PRODUCT_IN_SALE ADD FOREIGN KEY (SALE_ID) REFERENCES SALE (ID);
ALTER TABLE SALE ADD FOREIGN KEY (DEALER_ID) REFERENCES CONTACT (ID);
ALTER TABLE SALE ADD FOREIGN KEY (CLIENT_ID) REFERENCES CONTACT (ID);
ALTER TABLE SALE_POINT ADD FOREIGN KEY (ID) REFERENCES CONTACT (ID);
ALTER TABLE SALE_POINT ADD FOREIGN KEY (COMPANY_ID) REFERENCES CONTACT (ID);
ALTER TABLE SALEPOINT_LEGALENTITY ADD FOREIGN KEY (SALEPOINT_ID) REFERENCES CONTACT (ID);
ALTER TABLE SALEPOINT_LEGALENTITY ADD FOREIGN KEY (LEGALENTITY_ID) REFERENCES CONTACT (ID);

SET foreign_key_checks = 1;