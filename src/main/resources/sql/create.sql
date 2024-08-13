CREATE TABLE usuario (
    id BIGINT,
    username VARCHAR(60) NOT NULL,
    nome VARCHAR(60) NOT NULL,
    telefone VARCHAR(30) NOT NULL,
    senha_hash VARCHAR(512) NOT NULL,
    status VARCHAR(30),
    data_nasc DATE,
    data_cad DATE,
    CONSTRAINT usuario_pk PRIMARY KEY(id)
);

CREATE TABLE perfil (
    id BIGINT,
    nome VARCHAR(60) NOT NULL,
    CONSTRAINT perfil_pk PRIMARY KEY(id)  
);

CREATE TABLE usuario_perfil(
    id_usuario BIGINT,
    id_perfil BIGINT,
    CONSTRAINT pk_usuario_perfil PRIMARY KEY (id_usuario,id_perfil),
    CONSTRAINT fk_u_perfil FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    CONSTRAINT fk_usuario_perfil FOREIGN KEY (id_perfil) REFERENCES perfil(id)
);

CREATE SEQUENCE usuario_seq
  START WITH 1
  INCREMENT BY 1
  CACHE 1
  NO CYCLE;
 
 CREATE SEQUENCE perfil_seq
 START WITH     1
 INCREMENT BY   1
 CACHE 1
 NO CYCLE;