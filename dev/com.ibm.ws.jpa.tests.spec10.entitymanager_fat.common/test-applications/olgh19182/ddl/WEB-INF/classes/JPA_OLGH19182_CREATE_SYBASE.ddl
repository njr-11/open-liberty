CREATE TABLE ${schemaname}.HelmetEntityOLGH19182 (id INT NOT NULL, color VARCHAR(255) NULL, SHELF_ID NUMERIC(38) NULL, UNQ_INDEX NUMERIC IDENTITY UNIQUE, PRIMARY KEY (id));
CREATE TABLE ${schemaname}.JPA10EntityManagerEntityA (id INT NOT NULL, strData VARCHAR(255) NULL, UNQ_INDEX NUMERIC IDENTITY UNIQUE, PRIMARY KEY (id));
CREATE TABLE ${schemaname}.JPA10ENTITYMANAGERENTITYA_JPA1 (ENTITYALIST_ID INT NULL, ENTITYBLIST_ID INT NULL, UNQ_INDEX NUMERIC IDENTITY UNIQUE);
CREATE TABLE ${schemaname}.JPA10EntityManagerEntityB (id INT NOT NULL, strData VARCHAR(255) NULL, UNQ_INDEX NUMERIC IDENTITY UNIQUE, PRIMARY KEY (id));
CREATE TABLE ${schemaname}.JPA10EntityManagerEntityC (id INT NOT NULL, strData VARCHAR(255) NULL, ENTITYA_ID INT NULL, ENTITYALAZY_ID INT NULL, UNQ_INDEX NUMERIC IDENTITY UNIQUE, PRIMARY KEY (id));
CREATE TABLE ${schemaname}.JPA_HELMET_PROPERTIES (HELMET_ID INT NULL, PROPERTY_NAME VARCHAR(255) NOT NULL, PROPERTY_VALUE VARCHAR(255) NULL, UNQ_INDEX NUMERIC IDENTITY UNIQUE);
CREATE TABLE ${schemaname}.ShelfEntityOLGH19182 (id NUMERIC(38) NOT NULL, name VARCHAR(255) NULL, UNQ_INDEX NUMERIC IDENTITY UNIQUE, PRIMARY KEY (id));
CREATE INDEX ${schemaname}.I_HLMT182_SHELF ON ${schemaname}.HelmetEntityOLGH19182 (SHELF_ID);
CREATE INDEX ${schemaname}.I_JP10JP1_ELEMENT ON ${schemaname}.JPA10ENTITYMANAGERENTITYA_JPA1 (ENTITYBLIST_ID);
CREATE INDEX ${schemaname}.I_JP10JP1_ENTITYALIST_ID ON ${schemaname}.JPA10ENTITYMANAGERENTITYA_JPA1 (ENTITYALIST_ID);
CREATE INDEX ${schemaname}.I_JP10TYC_ENTITYA ON ${schemaname}.JPA10EntityManagerEntityC (ENTITYA_ID);
CREATE INDEX ${schemaname}.I_JP10TYC_ENTITYALAZY ON ${schemaname}.JPA10EntityManagerEntityC (ENTITYALAZY_ID);
CREATE INDEX ${schemaname}.I_JP_HRTS_HELMET_ID ON ${schemaname}.JPA_HELMET_PROPERTIES (HELMET_ID);