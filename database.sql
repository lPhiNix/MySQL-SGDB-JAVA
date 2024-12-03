CREATE DATABASE busdriver;

USE busdriver;

CREATE TABLE bus (
    register VARCHAR(15) NOT NULL PRIMARY KEY,
    licence VARCHAR(10),
    type VARCHAR(50)
);

CREATE TABLE driver(
    numdriver INTEGER NOT NULL PRIMARY KEY ,
    name VARCHAR(50),
    surname VARCHAR(50)
);

CREATE TABLE place(
    idplace INTEGER PRIMARY KEY AUTO_INCREMENT,
    cp INTEGER,
    city VARCHAR(50),
    site VARCHAR(50)
);

CREATE TABLE routes(
    register VARCHAR(15) NOT NULL,
    numdriver INTEGER NOT NULL,
    idplace INTEGER NOT NULL,
    dayWeek VARCHAR(20),

    CONSTRAINT bdp_pk PRIMARY KEY (register, numdriver, idplace),
    CONSTRAINT id_bus_fk FOREIGN KEY (register) REFERENCES bus(register) ON DELETE RESTRICT,
    CONSTRAINT id_numdriver_fk FOREIGN KEY (numdriver) REFERENCES driver(numdriver) ON DELETE RESTRICT,
    CONSTRAINT idplace_fk FOREIGN KEY (idplace) REFERENCES place (idplace)  ON DELETE RESTRICT,
    CONSTRAINT bdp_unique UNIQUE (register, numdriver, idplace),
    CONSTRAINT dayWeek_chk CHECK (dayWeek IN ('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'))
);
COMMIT;
