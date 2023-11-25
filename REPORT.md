# Faturantia

## Mudanças realizadas

### Geral
Adicionamos uma toolbar com um Navigation Menu e o Botão do perfil em vez de ser em separado esta dentro do menu criado.

### Main Page
Fizemos alteraçoões tanto visuais como funcionais na nossa main page, juntamos as garantias com as faturas, ou seja, agora ao adicionar fatura temos opção para juntar a garantia que desejamos a fatura.

### Pagina Garantia

Na página da garantia fizemos alterações dos textbox, como juntamos as faturas as garantias achamos desnecessário o utilizador ter de colocar denovo certas informações (Loja, Local da Loja, Data de compra) apenas tendo o nome do produto com garantia, a Data que expira a garantia, se considera a garantia importante e a data para o lembrete da garantia.

## Modelo Entidade-Relação

![diagrama](imagens/Imagens%20Github/Diagrama.png)

## Dicionário

### Tabela `userapp`

| Coluna      | Tipo        | Restrições                       |
|-------------|-------------|----------------------------------|
| user_id     | int         | Not Null, Auto-Increment, Primary Key |
| user_name   | VARCHAR(60) | Not Null                         |
| user_email  | VARCHAR(30) | Not Null                         |
| user_place  | VARCHAR(30) |                                  |

### Tabela `fatura`

| Coluna         | Tipo        | Restrições                       |
|----------------|-------------|----------------------------------|
| fat_id         | int         | Not Null, Auto-Increment, Primary Key |
| fat_pdf        | BLOB        |                                  |
| fat_name       | VARCHAR(60) | Not Null                         |
| fat_user_id    | int         |                                  |
| fat_est_fat_id | int         |                                  |

### Tabela `estadofatura`

| Coluna        | Tipo        | Restrições                       |
|---------------|-------------|----------------------------------|
| est_fat_id    | int         | Not Null, Auto-Increment, Primary Key |
| est_fat_nome  | VARCHAR(60) | Not Null                         |

### Tabela `garantia`

| Coluna         | Tipo        | Restrições                       |
|----------------|-------------|----------------------------------|
| gar_id         | int         | Not Null, Auto-Increment, Primary Key |
| gar_endDate    | date        | Not Null                         |

### Tabela `lembrete`

| Coluna         | Tipo        | Restrições                       |
|----------------|-------------|----------------------------------|
| lem_id         | int         | Not Null, Auto-Increment, Primary Key |
| lem_date       | date        | Not Null                         |
| lem_pfat_id    | int         |                                  |

### Tabela `produto`

| Coluna         | Tipo        | Restrições                       |
|----------------|-------------|----------------------------------|
| prod_id        | int         | Not Null, Auto-Increment, Primary Key |
| prod_name      | VARCHAR(60) | Not Null                         |

### Tabela `loja`

| Coluna         | Tipo        | Restrições                       |
|----------------|-------------|----------------------------------|
| loja_id        | int         | Not Null, Auto-Increment, Primary Key |
| loja_name      | VARCHAR(30) | Not Null                         |
| loja_place     | VARCHAR(30) | Not Null                         |

### Tabela `tipogarantia`

| Coluna         | Tipo        | Restrições                       |
|----------------|-------------|----------------------------------|
| tgar_id        | int         | Not Null, Auto-Increment, Primary Key |
| tgar_estado    | VARCHAR(30) |                                  |

### Tabela `prodloja`

| Coluna         | Tipo        | Restrições                       |
|----------------|-------------|----------------------------------|
| ploja_id       | int         | Not Null, Auto-Increment, Primary Key |
| ploja_loja_id  | int         |                                  |
| ploja_prod_id  | int         |                                  |

### Tabela `prodfatura`

| Coluna         | Tipo        | Restrições                       |
|----------------|-------------|----------------------------------|
| pfat_id        | int         | Not Null, Auto-Increment, Primary Key |
| pfat_buyDate   | date        | Not Null                         |
| pfat_tgar_id   | int         |                                  |
| pfat_fat_id    | int         |                                  |
| pfat_ploja     | int         |                                  |

### Tabela `prodfatgarantia`

| Coluna         | Tipo        | Restrições                       |
|----------------|-------------|----------------------------------|
| pfatgar_id     | int         | Not Null, Auto-Increment, Primary Key |
| pfatgar_gar_id | int         |                                  |
| pfatgar_pfat_id| int         |                                  |

### Foreign Keys

- **Tabela `fatura`**
    - fat_user_id- Chave para unir fatura como refencia a userapp
    - fat_est_fat_id- Chave para unir fatura como refencia a estadofatura

- **Tabela `lembrete`**
    - lem_pfat_id- Chave para unir lembrete como refencia a prodfatura

- **Tabela `prodloja`**
    - ploja_loja_id- Chave para unir prodloja como refencia a loja
    - ploja_prod_id- Chave para unir prodloja como refencia a produto

- **Tabela `prodfatura`**
    - pfat_tgar_id- Chave para unir prodfatura como refencia a tipogarantia
    - pfat_fat_id- Chave para unir prodfatura como refencia a fatura
    - pfat_ploja- Chave para unir prodfatura como refencia a prodloja

- **Tabela `prodfatgarantia`**
    - pfatgar_gar_id- Chave para unir prodfatgarantia como refencia a garantia
    - pfatgar_pfat_id- Chave para unir prodfatgarantia como refencia a prodfatura


## Código da Base de Dados

create table userapp(
				user_id int not null auto_increment,
                user_name VARCHAR(60) not null,
                user_email VARCHAR(30) not null,
                user_place VARCHAR(30),
                primary key (user_id)
);

create table fatura(
				fat_id int not null auto_increment,
				fat_pdf BLOB,
                fat_name VARCHAR(60) not null,	
                fat_user_id int,
                fat_est_fat_id int,
                primary key (fat_id)
);

create table estadofatura(
				est_fat_id int not null auto_increment,
                est_fat_nome VARCHAR(60) not null,
                primary key (est_fat_id)
);

create table garantia(
				gar_id int not null auto_increment,
                gar_endDate date not null,
                primary key(gar_id)
);

create table lembrete(
				lem_id int not null auto_increment,
                lem_date date not null,
                lem_pfat_id int,
                primary key(lem_id)
);

create table produto(
				prod_id int not null auto_increment,
                prod_name VARCHAR(60) not null,
                primary key(prod_id)
);

create table loja(
				loja_id int not null auto_increment,
                loja_name VARCHAR(30) not null,
                loja_place VARCHAR(30) not null,
                primary key(loja_id)
);

create table tipogarantia(
				tgar_id int not null auto_increment,
                tgar_estado VARCHAR(30),
                primary key(tgar_id)
);

create table prodloja(
				ploja_id int not null auto_increment,
                ploja_loja_id int,
                ploja_prod_id int,
                primary key(ploja_id)
);

create table prodfatura(
				pfat_id int not null auto_increment,
                pfat_buyDate date not null,
                pfat_tgar_id int,
                pfat_fat_id int,
                pfat_ploja int,
                primary key(pfat_id)
);

create table prodfatgarantia(
				pfatgar_id int not null auto_increment,
                pfatgar_gar_id int,
                pfatgar_pfat_id int,
                primary key(pfatgar_id)
);

ALTER TABLE fatura
ADD CONSTRAINT fatura_fk_user
FOREIGN KEY (fat_user_id) REFERENCES user(user_id),
ADD CONSTRAINT fatura_fk_estadofatura 
FOREIGN KEY (fat_est_fat_id) REFERENCES estadofatura(est_fat_id)
ON DELETE NO ACTION ON UPDATE NO ACTION; 

ALTER TABLE lembrete
ADD CONSTRAINT lembrete_fk_fatura
FOREIGN KEY (lem_pfat_id) REFERENCES fatura(fat_id)
ON DELETE NO ACTION ON UPDATE NO ACTION; 

ALTER TABLE prodloja
ADD CONSTRAINT prodloja_fk_loja
FOREIGN KEY (ploja_loja_id) REFERENCES loja(loja_id),
ADD CONSTRAINT prodloja_fk_produto 
FOREIGN KEY (ploja_prod_id) REFERENCES produto(prod_id)
ON DELETE NO ACTION ON UPDATE NO ACTION; 

ALTER TABLE prodfatura
ADD CONSTRAINT prodfatura_fk_tipogarantia 
FOREIGN KEY (pfat_tgar_id) REFERENCES tipogarantia(tgar_id),
ADD CONSTRAINT prodfatura_fk_fatura 
FOREIGN KEY (pfat_fat_id) REFERENCES fatura(fat_id),
ADD CONSTRAINT prodfatura_fk_prodloja 
FOREIGN KEY (pfat_ploja) REFERENCES pordloja(ploja_id)
ON DELETE NO ACTION ON UPDATE NO ACTION; 

ALTER TABLE prodfatgarantia
ADD CONSTRAINT prodfatgarantia_fk_garantia 
FOREIGN KEY (pfatgar_gar_id) REFERENCES garantia(gar_id),
ADD CONSTRAINT prodfatgarantia_fk_prodfatura 
FOREIGN KEY (pfatgar_pfat_id) REFERENCES prodfatura(pfat_id)
ON DELETE NO ACTION ON UPDATE NO ACTION; 





