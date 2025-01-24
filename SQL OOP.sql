DROP SCHEMA IF EXISTS practica_poo;
CREATE SCHEMA practica_poo
DEFAULT CHARACTER SET utf8 
COLLATE utf8_spanish2_ci;

CREATE TABLE practica_poo.user_role (
	`values` VARCHAR(255) PRIMARY KEY
		);

CREATE TABLE practica_poo.user (
	email VARCHAR(255) PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
	role VARCHAR(255) NOT NULL,

	CONSTRAINT
    FOREIGN KEY (role) REFERENCES practica_poo.user_role(`values`)
    	);

CREATE TABLE practica_poo.admin (

	email VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,  
    role VARCHAR(255) NOT NULL, 
    CONSTRAINT
    FOREIGN KEY (email) REFERENCES practica_poo.user(email),
    CONSTRAINT
    FOREIGN KEY (role) REFERENCES practica_poo.user(role) 
    	);
        
CREATE TABLE practica_poo.player (
	DNI VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    score DOUBLE NOT NULL,
    winMatches INT NOT NULL,
    fault INT NOT NULL,
    assistancePoints DOUBLE NOT NULL,
    winTournamentInThePast DOUBLE NOT NULL,
    raisedMoneyByTournaments DOUBLE NOT NULL,
    
    creator VARCHAR(255) NOT NULL,
	CONSTRAINT
    FOREIGN KEY (creator) REFERENCES practica_poo.admin(email),
    
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,  
    role VARCHAR(255) NOT NULL, 
    CONSTRAINT
    FOREIGN KEY (email) REFERENCES practica_poo.user(email),
    CONSTRAINT
    FOREIGN KEY (role) REFERENCES practica_poo.user(role)  
    	);

CREATE TABLE practica_poo.match (
	id INT AUTO_INCREMENT PRIMARY KEY,
    firstPlayer VARCHAR(255) NOT NULL,
    secondPlayer VARCHAR(255) NOT NULL,
    
    CONSTRAINT
    FOREIGN KEY (firstPlayer) REFERENCES practica_poo.player(email),
    CONSTRAINT
    FOREIGN KEY (secondPlayer) REFERENCES practica_poo.player(email)
		);

CREATE TABLE practica_poo.team (
	TeamName VARCHAR(100) PRIMARY KEY,
    score DOUBLE NOT NULL,
    winMatches INT NOT NULL,
    faults INT NOT NULL,
    assistancePoints DOUBLE NOT NULL,
    winTournamentInThePast DOUBLE NOT NULL,
    raisedMoneyByTournaments DOUBLE NOT NULL,
    
    admin VARCHAR(255) NOT NULL,
	CONSTRAINT
    FOREIGN KEY (admin) REFERENCES practica_poo.admin(email)
		);
        
CREATE TABLE practica_poo.tournament_state (
	state VARCHAR(100) PRIMARY KEY
    	);

CREATE TABLE practica_poo.tournament (
	name VARCHAR(100) PRIMARY KEY,
    starDate DATE NOT NULL,
    endDate DATE NOT NULL,
    league VARCHAR(100) NOT NULL,
    sport VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    
    CONSTRAINT
    FOREIGN KEY (state) REFERENCES practica_poo.tournament_state(state)
    	);
