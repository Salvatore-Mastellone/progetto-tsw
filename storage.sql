DROP DATABASE IF EXISTS progettoTSW;
CREATE DATABASE progettoTSW;
USE progetto TSW;

CREATE TABLE Prodotto (
idProdotto int(3) primary key AUTO_INCREMENT,
nome varchar(100) not null,
categoria char(10) not null, --Se è un oggetto oppure un corso
descrizione varchar(200) not null,
stato boolean not null, --True se è ancora in vendita, False se non è più in vedita
lingua varchar(20) not null, --Lingua del prodotto
iva int(2) not NULL,
prezzo decimal(6,2) not null,
stock int(3),
linkAccesso varchar(200)
);

CREATE TABLE Utenti (
id int primary key AUTO_INCREMENT,
email varchar(50) not null,
nome varchar(15) not null,
cognome varchar(15) not null,
indirizzo varchar(50) not null,
citta varchar(10) not null,
provincia char(2) not null,
cap int(4) not null,
password varchar(100) not null
);

CREATE TABLE Amministratore (
idAdmin int primary key AUTO_INCREMENT,
email varchar(50) not null,
nome varchar(15) not null,
cognome varchar(15) not null,
password varchar(100) not null
);

INSERT INTO Prodotto (nome, categoria, descrizione, stato, lingua, iva, prezzo, stock, linkAccesso) VALUES
-- Corsi di lingua
('Corso Base Cinese', 'corso', 'Corso online per principianti di lingua cinese.', TRUE, 'cinese', 22, 129.99, NULL, 'https://www.youtube.com/watch?v=HO2BB-w_6jg'),
('Corso Base Giapponese', 'corso', 'Corso online per principianti di lingua giapponese.', TRUE, 'giapponese', 22, 129.99, NULL, 'https://www.youtube.com/watch?v=OV0zSQYHnY0'),
('Corso Base Coreano', 'corso', 'Corso online per principianti di lingua coreana.', TRUE, 'coreano', 22, 129.99, NULL, 'https://www.youtube.com/watch?v=J6hzMJx8qL8'),

-- Manuali e materiali didattici
('Manuale Cinese HSK1', 'oggetto', 'Manuale cartaceo per la preparazione al test HSK1.', TRUE, 'cinese', 10, 24.50, 50, NULL),
('Manuale Giapponese JLPT N5', 'oggetto', 'Libro per la preparazione al JLPT N5.', TRUE, 'giapponese', 10, 27.90, 40, NULL),
('Manuale Coreano TOPIK I', 'oggetto', 'Libro per la preparazione al TOPIK I.', TRUE, 'coreano', 10, 26.00, 45, NULL),

-- Flashcard
('Flashcard Kanji', 'oggetto', 'Set di 100 flashcard per memorizzare i kanji base.', TRUE, 'giapponese', 10, 15.99, 60, NULL),
('Flashcard Hangul', 'oggetto', 'Set di 100 flashcard per imparare il sistema Hangul.', TRUE, 'coreano', 10, 15.99, 55, NULL),
('Flashcard Hanzi', 'oggetto', 'Set di 100 flashcard per memorizzare i caratteri Hanzi.', TRUE, 'cinese', 10, 15.99, 50, NULL),

-- Corsi intermedi
('Corso Intermedio Cinese', 'corso', 'Corso online per livello intermedio di lingua cinese.', TRUE, 'cinese', 22, 159.99, NULL, 'https://www.youtube.com/playlist?list=PLfjpNHFrqU1vmTULV-QtsH9OkZo9ErNnc'),
('Corso Intermedio Giapponese', 'corso', 'Corso online per livello intermedio di lingua giapponese.', TRUE, 'giapponese', 22, 159.99, NULL, 'https://www.youtube.com/playlist?list=PLjmSlqmrTdIQzmpMfEzxCJX2WALmREtGp'),
('Corso Intermedio Coreano', 'corso', 'Corso online per livello intermedio di lingua coreana.', TRUE, 'coreano', 22, 159.99, NULL, 'https://www.youtube.com/playlist?list=PL7AhQ8iqblcR7Jk5wu4csEcP_eXbp2H_s'),

-- Dizionari
('Dizionario Cinese-Italiano', 'oggetto', 'Dizionario tascabile cinese-italiano.', TRUE, 'cinese', 10, 19.90, 30, NULL),
('Dizionario Giapponese-Italiano', 'oggetto', 'Dizionario tascabile giapponese-italiano.', TRUE, 'giapponese', 10, 19.90, 35, NULL),
('Dizionario Coreano-Italiano', 'oggetto', 'Dizionario tascabile coreano-italiano.', TRUE, 'coreano', 10, 19.90, 30, NULL);