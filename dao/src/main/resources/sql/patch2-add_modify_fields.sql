ALTER TABLE crm_helios.company ADD date_modify TIMESTAMP NULL,
ADD user_modify_id INT NULL;

ALTER TABLE crm_helios.company
ADD CONSTRAINT fk_company_type1
FOREIGN KEY (user_modify_id)
REFERENCES crm_helios.person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;
CREATE INDEX fk_company_persons2_idx ON crm_helios.company (user_modify_id);

ALTER TABLE crm_helios.contact ADD date_modify TIMESTAMP NULL,
ADD user_modify_id INT NULL;

ALTER TABLE crm_helios.contact
ADD CONSTRAINT fk_contact_type1
FOREIGN KEY (user_modify_id)
REFERENCES crm_helios.person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;
CREATE INDEX fk_contact_persons2_idx ON crm_helios.contact (user_modify_id);