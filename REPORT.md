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

CREATE TABLE userapp (
    user_id INT NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(60) NOT NULL,
    user_email VARCHAR(30) NOT NULL,
    user_place VARCHAR(30),
    PRIMARY KEY (user_id)
);

CREATE TABLE fatura (
    fat_id INT NOT NULL AUTO_INCREMENT,
    fat_pdf BLOB,
    fat_name VARCHAR(60) NOT NULL,
    fat_user_id INT,
    fat_est_fat_id INT,
    PRIMARY KEY (fat_id)
);

CREATE TABLE estadofatura (
    est_fat_id INT NOT NULL AUTO_INCREMENT,
    est_fat_nome VARCHAR(60) NOT NULL,
    PRIMARY KEY (est_fat_id)
);

CREATE TABLE garantia (
    gar_id INT NOT NULL AUTO_INCREMENT,
    gar_endDate DATE NOT NULL,
    PRIMARY KEY (gar_id)
);

CREATE TABLE lembrete (
    lem_id INT NOT NULL AUTO_INCREMENT,
    lem_date DATE NOT NULL,
    lem_pfat_id INT,
    PRIMARY KEY (lem_id)
);

CREATE TABLE produto (
    prod_id INT NOT NULL AUTO_INCREMENT,
    prod_name VARCHAR(60) NOT NULL,
    PRIMARY KEY (prod_id)
);

CREATE TABLE loja (
    loja_id INT NOT NULL AUTO_INCREMENT,
    loja_name VARCHAR(30) NOT NULL,
    loja_place VARCHAR(30) NOT NULL,
    PRIMARY KEY (loja_id)
);

CREATE TABLE tipogarantia (
    tgar_id INT NOT NULL AUTO_INCREMENT,
    tgar_estado VARCHAR(30),
    PRIMARY KEY (tgar_id)
);

CREATE TABLE prodloja (
    ploja_id INT NOT NULL AUTO_INCREMENT,
    ploja_loja_id INT,
    ploja_prod_id INT,
    PRIMARY KEY (ploja_id)
);

CREATE TABLE prodfatura (
    pfat_id INT NOT NULL AUTO_INCREMENT,
    pfat_buyDate DATE NOT NULL,
    pfat_tgar_id INT,
    pfat_fat_id INT,
    pfat_ploja_id INT,
    PRIMARY KEY (pfat_id)
);

CREATE TABLE prodfatgarantia (
    pfatgar_id INT NOT NULL AUTO_INCREMENT,
    pfatgar_gar_id INT,
    pfatgar_pfat_id INT,
    PRIMARY KEY (pfatgar_id)
);

ALTER TABLE fatura ADD CONSTRAINT fatura_fk_userapp 
FOREIGN KEY (fat_user_id) REFERENCES userapp(user_id),
ADD CONSTRAINT fatura_fk_estadofatura 
FOREIGN KEY (fat_est_fat_id) REFERENCES estadofatura(est_fat_id) 
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE lembrete ADD CONSTRAINT lembrete_fk_fatura 
FOREIGN KEY (lem_pfat_id) REFERENCES prodfatura(pfat_id) 
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE prodloja ADD CONSTRAINT prodloja_fk_loja 
FOREIGN KEY (ploja_loja_id) REFERENCES loja(loja_id), 
ADD CONSTRAINT prodloja_fk_produto 
FOREIGN KEY (ploja_prod_id) REFERENCES produto(prod_id) 
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE prodfatura ADD CONSTRAINT prodfatura_fk_tipogarantia 
FOREIGN KEY (pfat_tgar_id) REFERENCES tipogarantia(tgar_id), 
ADD CONSTRAINT prodfatura_fk_fatura 
FOREIGN KEY (pfat_fat_id) REFERENCES fatura(fat_id), 
ADD CONSTRAINT prodfatura_fk_prodloja 
FOREIGN KEY (pfat_ploja_id) REFERENCES prodloja(ploja_id) 
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE prodfatgarantia ADD CONSTRAINT prodfatgarantia_fk_garantia 
FOREIGN KEY (pfatgar_gar_id) REFERENCES garantia(gar_id), 
ADD CONSTRAINT prodfatgarantia_fk_prodfatura 
FOREIGN KEY (pfatgar_pfat_id) REFERENCES prodfatura(pfat_id) 
ON DELETE NO ACTION ON UPDATE NO ACTION;





