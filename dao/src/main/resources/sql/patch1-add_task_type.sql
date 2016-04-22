-- -----------------------------------------------------
-- Table `crm_helios`.`task_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS crm_helios.task_type (
  id        SERIAL        NOT NULL,
  type_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
);

insert into crm_helios.task_type VALUES (1, 'Follow-up');
insert into crm_helios.task_type VALUES (2, 'Meeting');

ALTER TABLE crm_helios.task ADD task_type_id INT NOT NULL DEFAULT 1;

ALTER TABLE crm_helios.task
ADD CONSTRAINT fk_task_type1
FOREIGN KEY (task_type_id)
REFERENCES crm_helios.task_type (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;


SELECT setval('crm_helios.task_type_id_seq', (SELECT MAX(id) from crm_helios.task_type));