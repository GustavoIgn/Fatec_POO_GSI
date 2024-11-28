CREATE DATABASE agendadb;

USE agendadb;

-- Criação da tabela Animal
CREATE TABLE animal (
    id INT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    especie VARCHAR(100),
    raca VARCHAR(100),
    idade INT,
    cpf_dono CHAR(11),
    PRIMARY KEY (id)
);

-- Inserção de dados de exemplo na tabela Animal
INSERT INTO animal (nome, especie, raca, idade, cpf_dono)
VALUES 
('Rex', 'Cachorro', 'Labrador', 5, '12345678901'),
('Mimi', 'Gato', 'Siames', 3, '98765432100');

-- Criação da tabela Consulta
CREATE TABLE consulta (
    id INT AUTO_INCREMENT NOT NULL,
    animal_id INT NOT NULL,
    data VARCHAR(10) NOT NULL,
    descricao VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (animal_id) REFERENCES animal(id)
);

-- Inserção de dados de exemplo na tabela Consulta
INSERT INTO consulta (animal_id, data, descricao)
VALUES 
(1, '2024-11-01', 'Consulta de rotina'),
(2, '2024-11-15', 'Emergencia: ferimento na pata');
