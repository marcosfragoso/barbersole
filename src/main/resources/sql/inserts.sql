INSERT INTO usuario VALUES (1,'barbeiro@gmail.com', 'Barbeiro', '$2a$10$Ckqz5CKBEMXDu19vES8SCuOXspzairum4wWjmbOaq.pckZ6nUzvBq','Ativo', '1997-03-17', CURRENT_DATE);
INSERT INTO usuario VALUES (2,'cliente@gmail.com', 'Cliente', '$2a$10$Ckqz5CKBEMXDu19vES8SCuOXspzairum4wWjmbOaq.pckZ6nUzvBq','Ativo', '1996-08-05', CURRENT_DATE);

insert into perfil values(1, 'BARBEIRO');
insert into perfil values(2, 'CLIENTE');
insert into perfil values(3, 'ADMIN');


INSERT INTO usuario_perfil VALUES (1, 1);
INSERT INTO usuario_perfil VALUES (2, 2);