-- -----------------------------------------------------
-- Table `taskmanager`.`endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taskmanager`.`endereco` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `bairro` VARCHAR(30) NOT NULL,
  `cep` VARCHAR(8) NOT NULL,
  `cidade` VARCHAR(30) NOT NULL,
  `complemento` VARCHAR(255) NULL DEFAULT NULL,
  `estado` VARCHAR(2) NOT NULL,
  `lograudouro` VARCHAR(120) NOT NULL,
  `numero` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `taskmanager`.`foto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taskmanager`.`foto` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `content_length` BIGINT NOT NULL,
  `content_type` VARCHAR(255) NOT NULL,
  `filename` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_j50xfvc2tejm9ep6t4jkytegu` (`filename` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `taskmanager`.`telefone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taskmanager`.`telefone` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `numero` VARCHAR(255) NOT NULL,
  `whatsapp` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `taskmanager`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taskmanager`.`usuario` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ativo` BIT(1) NULL DEFAULT NULL,
  `cpf_cnpj` VARCHAR(14) NULL DEFAULT NULL,
  `email` VARCHAR(255) NOT NULL,
  `nascimento` DATE NULL DEFAULT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `sobrenome` VARCHAR(255) NOT NULL,
  `tipo_usuario` ENUM('ADMINISTRADOR', 'USUARIO') NULL DEFAULT NULL,
  `endereco_id` BIGINT NULL DEFAULT NULL,
  `foto_documento` BIGINT NULL DEFAULT NULL,
  `telefone_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_hocok4g75kwir3tsqbjlhaxci` (`cpf_cnpj` ASC) VISIBLE,
  UNIQUE INDEX `UK_t7mwqqaisjlrg6yoj4dtwh0vd` (`endereco_id` ASC) VISIBLE,
  UNIQUE INDEX `UK_2kb2ou27bpxptfeiyvk0vtdtt` (`foto_documento` ASC) VISIBLE,
  UNIQUE INDEX `UK_gw5i1rdjp6xfqm9d1okcukgl9` (`telefone_id` ASC) VISIBLE,
  CONSTRAINT `FK8fl5dxscva53gw12f19q6qxf8`
    FOREIGN KEY (`endereco_id`)
    REFERENCES `taskmanager`.`endereco` (`id`),
  CONSTRAINT `FKnarnicygxevka17ka017vxylt`
    FOREIGN KEY (`telefone_id`)
    REFERENCES `taskmanager`.`telefone` (`id`),
  CONSTRAINT `FKsm1de2ua58pvy2oj3jnalbu27`
    FOREIGN KEY (`foto_documento`)
    REFERENCES `taskmanager`.`foto` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `taskmanager`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taskmanager`.`task` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ativa` BIT(1) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `priority_task` ENUM('HIGH', 'MEDIUM', 'LOW') NULL DEFAULT NULL,
  `status_task` ENUM('TODO', 'IN_PROGRESS', 'DONE', 'CANCELED') NULL DEFAULT NULL,
  `title` VARCHAR(32) NOT NULL,
  `usuario_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `UK_ieybpna9brl1ypirh49b1640i` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `FK7er7x6v90hyklkxprpoavav1n`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `taskmanager`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;