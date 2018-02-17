create schema "ROOT";

CREATE TABLE IDENTITIES
    (IDENTITY_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY
    CONSTRAINT IDENTITY_PK PRIMARY KEY, 
    DISPLAY_NAME VARCHAR(255),
    EMAIL VARCHAR(255),
    UID VARCHAR(255)
    );
    
    CREATE TABLE USERS (
    userid INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(32) NOT NULL,
    pass_salt VARCHAR(255) NOT NULL,
            -- a string of 16 random bytes
    pass_md5 VARCHAR(255) NOT NULL
            -- binary MD5 hash of pass_salt concatenated with the password
);