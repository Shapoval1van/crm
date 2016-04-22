-- -----------------------------------------------------
-- Schema crm_helios
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS crm_helios;

-- -----------------------------------------------------
-- Table `crm_helios`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.role (
  id        INT         NOT NULL,
  role_name VARCHAR(45) NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `crm_helios`.`stage`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS crm_helios.stage (
  id   INT          NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `crm_helios`.`currencies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.currencies (
  id              INT         NOT NULL,
  name            VARCHAR(45) NOT NULL,
  active_currency BOOLEAN,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `crm_helios`.`phone_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.phone_type (
  id        INT         NOT NULL,
  type_name VARCHAR(45) NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `crm_helios`.`lang`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS crm_helios.lang (
  id   INT         NOT NULL,
  lang VARCHAR(45) NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table `crm_helios`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.tag (
  id          SERIAL       NOT NULL CHECK (id > 0),
  name        VARCHAR(255) NOT NULL,
  created_by  INT          NOT NULL,
  date_create TIMESTAMP    NOT NULL,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX tag_name_UNIQUE ON crm_helios.tag (name);
CREATE INDEX fk_tag_person1_idx ON crm_helios.tag (created_by);

-- -----------------------------------------------------
-- Table `crm_helios`.`deal_contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.deal_contact (
  deal_id    INT NOT NULL,
  contact_id INT NOT NULL,
  PRIMARY KEY (deal_id, contact_id)
);

CREATE INDEX fk_deals_has_contact_contact1_idx ON crm_helios.deal_contact (contact_id);
CREATE INDEX fk_deals_has_contact_deals1_idx ON crm_helios.deal_contact (deal_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`tag_deal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.tag_deal (
  tag_id  INT NOT NULL,
  deal_id INT NOT NULL,
  PRIMARY KEY (tag_id, deal_id),
  CONSTRAINT fk_tags_has_deal_tags1
  FOREIGN KEY (tag_id)
  REFERENCES crm_helios.tag (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_tags_has_deal_deal1_idx ON crm_helios.tag_deal (deal_id);
CREATE INDEX fk_tags_has_deal_tags1_idx ON crm_helios.tag_deal (tag_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.company (
  id             SERIAL       NOT NULL,
  name           VARCHAR(255) NOT NULL,
  responsible_id INT          NULL,
  web            VARCHAR(255) NULL,
  email          VARCHAR(255) NOT NULL,
  adress         VARCHAR(255) NULL,
  phone          VARCHAR(45)  NOT NULL,
  phone_type_id  INT          NOT NULL,
  created_by     INT          NOT NULL,
  date_create    TIMESTAMP    NOT NULL,
  deleted        BOOLEAN      NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  CONSTRAINT fk_company_phone_types1
  FOREIGN KEY (phone_type_id)
  REFERENCES crm_helios.phone_type (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_company_persons1_idx ON crm_helios.company (responsible_id);
CREATE INDEX fk_company_phone_types1_idx ON crm_helios.company (phone_type_id);
CREATE INDEX fk_company_person1_idx ON crm_helios.company (created_by);

-- -----------------------------------------------------
-- Table `crm_helios`.`tag_contact_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.tag_contact_company (
  tag_id     INT NOT NULL,
  contact_id INT NOT NULL,
  company_id INT NOT NULL,
  PRIMARY KEY (tag_id, contact_id, company_id),
  CONSTRAINT fk_tags_has_contact_tags1
  FOREIGN KEY (tag_id)
  REFERENCES crm_helios.tag (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_tag_contact_company1
  FOREIGN KEY (company_id)
  REFERENCES crm_helios.company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_tags_has_contact_contact1_idx ON crm_helios.tag_contact_company (contact_id);
CREATE INDEX fk_tags_has_contact_tags1_idx ON crm_helios.tag_contact_company (tag_id);
CREATE INDEX fk_tag_contact_company1_idx ON crm_helios.tag_contact_company (company_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`file`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS crm_helios.file (
  id          SERIAL    NOT NULL,
  path        TEXT      NULL,
  blob_data   BYTEA     NULL,
  contact_id  INT       NULL,
  deal_id     INT       NULL,
  notes_id    INT       NULL,
  company_id  INT       NULL,
  created_by  INT       NOT NULL,
  date_create TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_files_company1
  FOREIGN KEY (company_id)
  REFERENCES crm_helios.company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_files_contact1_idx ON crm_helios.file (contact_id);
CREATE INDEX fk_files_deal1_idx ON crm_helios.file (deal_id);
CREATE INDEX fk_files_notes1_idx ON crm_helios.file (notes_id);
CREATE INDEX fk_files_persons1_idx ON crm_helios.file (created_by);
CREATE INDEX fk_files_company1_idx ON crm_helios.file (company_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`person`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS crm_helios.person (
  id            SERIAL       NOT NULL,
  name          VARCHAR(255) NOT NULL,
  password      VARCHAR(255) NOT NULL,
  photo_file_id INT          NULL,
  email         VARCHAR(255) NOT NULL,
  phone_mobile  VARCHAR(255) NULL,
  phone_work    VARCHAR(255) NULL,
  lang_id       INT          NOT NULL,
  role_id       INT          NOT NULL,
  note          TEXT         NULL,
  date_create   TIMESTAMP    NOT NULL,
  deleted       BOOLEAN      NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  CONSTRAINT fk_persons_role1
  FOREIGN KEY (role_id)
  REFERENCES crm_helios.role (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_persons_files1
  FOREIGN KEY (photo_file_id)
  REFERENCES crm_helios.file (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_person_lang1
  FOREIGN KEY (lang_id)
  REFERENCES crm_helios.lang (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_persons_role1_idx ON crm_helios.person (role_id);
CREATE INDEX fk_persons_files1_idx ON crm_helios.person (photo_file_id);
CREATE INDEX fk_person_lang1_idx ON crm_helios.person (lang_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`deal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.deal (
  id             SERIAL         NOT NULL,
  name           VARCHAR(255)   NOT NULL,
  budget         DECIMAL(20, 2) NULL,
  responsible_id INT            NOT NULL,
  stage_id       INT            NOT NULL,
  company_id     INT            NOT NULL,
  created_by     INT            NOT NULL,
  date_create    TIMESTAMP      NOT NULL,
  deleted        BOOLEAN        NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  CONSTRAINT fk_deal_persons1
  FOREIGN KEY (responsible_id)
  REFERENCES crm_helios.person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_deal_state1
  FOREIGN KEY (stage_id)
  REFERENCES crm_helios.stage (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_deals_company1
  FOREIGN KEY (company_id)
  REFERENCES crm_helios.company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_deal_person1
  FOREIGN KEY (created_by)
  REFERENCES crm_helios.person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_deal_persons1 ON crm_helios.deal (responsible_id);
CREATE INDEX fk_deal_state1 ON crm_helios.deal (stage_id);
CREATE UNIQUE INDEX deal_id_UNIQUE ON crm_helios.deal (id);
CREATE INDEX fk_deals_company1_idx ON crm_helios.deal (company_id);
CREATE INDEX fk_deal_person1_idx ON crm_helios.deal (created_by);

-- -----------------------------------------------------
-- Table `crm_helios`.`contact`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS crm_helios.contact (
  id             SERIAL       NOT NULL CHECK (id > 0),
  name           VARCHAR(255) NOT NULL,
  phone          VARCHAR(255) NULL,
  email          VARCHAR(255) NULL,
  skype          VARCHAR(255) NULL,
  position       VARCHAR(255) NOT NULL,
  responsible_id INT          NULL,
  phone_type_id  INT          NOT NULL,
  company_id     INT          NULL,
  created_by     INT          NOT NULL,
  date_create    TIMESTAMP    NULL,
  deleted        BOOLEAN      NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  CONSTRAINT fk_contact_persons1
  FOREIGN KEY (responsible_id)
  REFERENCES crm_helios.person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_contact_phone_types1
  FOREIGN KEY (phone_type_id)
  REFERENCES crm_helios.phone_type (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_contact_company1
  FOREIGN KEY (company_id)
  REFERENCES crm_helios.company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_contact_person1
  FOREIGN KEY (created_by)
  REFERENCES crm_helios.person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_contact_persons1_idx ON crm_helios.contact (responsible_id);
CREATE INDEX fk_contact_phone_types1_idx ON crm_helios.contact (phone_type_id);
CREATE INDEX fk_contact_company1_idx ON crm_helios.contact (company_id);
CREATE INDEX fk_contact_person1_idx ON crm_helios.contact (created_by);

-- -----------------------------------------------------
-- Table `crm_helios`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.task (
  id             SERIAL       NOT NULL,
  name           VARCHAR(255) NOT NULL,
  finish_date    TIMESTAMP    NOT NULL,
  responsible_id INT          NOT NULL,
  description    TEXT         NOT NULL,
  contact_id     INT          NULL,
  period         INT          NULL,
  deal_id        INT          NULL,
  company_id     INT          NULL,
  created_by     INT          NOT NULL,
  date_create    TIMESTAMP    NOT NULL,
  done           BOOLEAN,
  deleted        BOOLEAN      NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  CONSTRAINT fk_tasks_persons1
  FOREIGN KEY (responsible_id)
  REFERENCES crm_helios.person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_tasks_persons2
  FOREIGN KEY (created_by)
  REFERENCES crm_helios.person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_task_contact1
  FOREIGN KEY (contact_id)
  REFERENCES crm_helios.contact (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_task_deal1
  FOREIGN KEY (deal_id)
  REFERENCES crm_helios.deal (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_task_company1
  FOREIGN KEY (company_id)
  REFERENCES crm_helios.company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE INDEX fk_tasks_persons1_idx ON crm_helios.task (responsible_id);
CREATE INDEX fk_tasks_persons2_idx ON crm_helios.task (created_by);
CREATE UNIQUE INDEX task_id_UNIQUE ON crm_helios.task (id);
CREATE INDEX fk_task_contact1_idx ON crm_helios.task (contact_id);
CREATE INDEX fk_task_deal1_idx ON crm_helios.task (deal_id);
CREATE INDEX fk_task_company1_idx ON crm_helios.task (company_id);

-- -----------------------------------------------------
-- Table `crm_helios`.`note`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.note (
  id          SERIAL    NOT NULL,
  text        TEXT      NOT NULL,
  contact_id  INT       NOT NULL,
  deal_id     INT       NULL,
  company_id  INT       NULL,
  created_by  INT       NOT NULL,
  date_create TIMESTAMP NOT NULL,
  PRIMARY KEY (id, created_by),
  CONSTRAINT fk_notes_persons1
  FOREIGN KEY (created_by)
  REFERENCES crm_helios.person (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_notes_contact1
  FOREIGN KEY (contact_id)
  REFERENCES crm_helios.contact (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_notes_deal1
  FOREIGN KEY (deal_id)
  REFERENCES crm_helios.deal (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT fk_note_company1
  FOREIGN KEY (company_id)
  REFERENCES crm_helios.company (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

CREATE UNIQUE INDEX note_id_UNIQUE ON crm_helios.note (id);
CREATE INDEX fk_notes_persons1_idx ON crm_helios.note (created_by);
CREATE INDEX fk_notes_contact1_idx ON crm_helios.note (contact_id);
CREATE INDEX fk_notes_deal1_idx ON crm_helios.note (deal_id);
CREATE INDEX fk_note_company1_idx ON crm_helios.note (company_id);

--CREATE REST OF REFERENCES:
ALTER TABLE crm_helios.tag ADD CONSTRAINT fk_tag_person1 FOREIGN KEY (created_by) REFERENCES crm_helios.person (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE crm_helios.deal_contact ADD CONSTRAINT fk_deals_has_contact_deals1 FOREIGN KEY (deal_id) REFERENCES crm_helios.deal (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE crm_helios.deal_contact ADD CONSTRAINT fk_deals_has_contact_contact1 FOREIGN KEY (contact_id) REFERENCES crm_helios.contact (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE crm_helios.tag_deal ADD CONSTRAINT fk_tags_has_deal_deal1 FOREIGN KEY (deal_id) REFERENCES crm_helios.deal (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE crm_helios.company ADD CONSTRAINT fk_company_persons1 FOREIGN KEY (responsible_id) REFERENCES crm_helios.person (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE crm_helios.company ADD CONSTRAINT fk_company_person1 FOREIGN KEY (created_by) REFERENCES crm_helios.person (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE crm_helios.tag_contact_company ADD CONSTRAINT fk_tags_has_contact_contact1 FOREIGN KEY (contact_id) REFERENCES crm_helios.contact (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE crm_helios.file ADD CONSTRAINT fk_files_contact1 FOREIGN KEY (contact_id) REFERENCES crm_helios.contact (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE crm_helios.file ADD CONSTRAINT fk_files_deal1 FOREIGN KEY (deal_id) REFERENCES crm_helios.deal (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE crm_helios.file ADD CONSTRAINT fk_files_notes1 FOREIGN KEY (notes_id) REFERENCES crm_helios.note (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE crm_helios.file ADD CONSTRAINT fk_files_persons1 FOREIGN KEY (created_by) REFERENCES crm_helios.person (id) ON DELETE NO ACTION ON UPDATE NO ACTION;


