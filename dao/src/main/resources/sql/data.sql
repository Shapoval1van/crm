insert into crm_helios.role values (1, 'Пользователь'), 
				   (2, 'Администратор');

insert into crm_helios.lang values (1, 'Украинский'), 
				   (2, 'Русский'),
				   (3, 'English');

insert into crm_helios.currencies values (1, 'гривна', TRUE), 
				         (2, 'доллар', FALSE),
				         (3, 'рубль', FALSE),
				         (4, 'тенге', FALSE),
				         (5, 'лей', FALSE),
				         (6, 'манат', FALSE),
				         (7, 'драма', FALSE),
				         (8, 'сума', FALSE),
				         (9, 'сомонна', FALSE),
				         (10, 'сома', FALSE);

insert into crm_helios.phone_type values (1, 'рабочий'), 
				         (2, 'рабочий прямой'),
				         (3, 'мобильный'),
				         (4, 'факс'),
				         (5, 'домашний'),
				         (6, 'другой');

insert into crm_helios.stage values (1, 'первичный контакт'), 
				    (2, 'переговоры'),
				    (3, 'принимают решение'),
				    (4, 'согласование договора'),
				    (5, 'успешно реализовано'),
				    (6, 'закрыто и не реализовано');

insert into crm_helios.person
values (1, 'Андрей Ярмоленко', 'yar', NULL, 'yar@ukr.net', '+380963451234', '+380440912938', 1, 1, 'сильные стороны: общение с клиентом', '2016-03-08 04:05:06'),
				   (2, 'Евгений Хачериди', 'hac', NULL, 'haceridi@gmail.com', '+380975451274', '+380441912638', 2, 1, 'сильные стороны: игра головой', '2016-03-09 09:15:12');

insert into crm_helios.company values (1, 'Елки', 1, 'elki.com.ua', 'info@elki.com.ua', 'Украина, Киев, Институтская-3', '+380440313936', 1, 1, '2016-03-10 08:10:00'), 
				      (2, 'Палки', 2, 'palki.com.ua', 'info@palki.com.ua', 'Украина, Одесса, Дерибасовская-15', '+380482343830', 2, 2, '2016-03-11 12:14:40');

insert into crm_helios.contact values (1, 'Андрей Пятов', '+380442343976', 'pyatov@ukr.net', 'pyatov84', 'менеджер', 1, 2, 1, 1, '2016-03-10 08:50:20'), 
				      (2, 'Тарас Степаненко', '+3804825353956', 'step@ukr.net', 'step89', 'маркетолог', 2, 2, 2, 2, '2016-03-11 13:52:21');

insert into crm_helios.deal values (1, 'поставка сумок', 25000.00, 1, 1, 1, 1, '2016-03-10 17:00:00'), 
				   (2, 'поставка кошельков', 15000.00, 2, 4, 2, 2, '2016-03-10 16:00:00');

insert into crm_helios.task values (1, 'Познакомиться', '2016-03-12 18:00:00', 1, 'лично встретиться с клиентом', 1, 2, 1, 1, 1, '2016-03-11 17:50:00', FALSE), 
				   (2, 'подготовить договор', '2016-03-12 18:00:00', 2, 'составить договор и отправить клиенту', 2, 2, 2, 2, 2, '2016-03-11 17:00:00', FALSE);

insert into crm_helios.note values (1, 'постараться в краткие сроки оформить сделку', 1, 1, 1, 1, '2016-03-11 12:20:00'), 
				   (2, 'заключить договор на максимальную сумму', 2, 2, 2, 2, '2016-03-11 11:10:00');

insert into crm_helios.file values (1, 'd:\crm\files\deal1\file1', NULL, 1, 1, 1, 1, 1, '2016-03-11 12:30:00'), 
				   (2, 'd:\crm\files\deal2\file1', NULL, 2, 2, 2, 2, 2, '2016-03-11 11:20:00');

insert into crm_helios.tag values (1, '#Елки', 1, '2016-03-11 13:30:00'), 
				  (2, '#маркетолог', 2, '2016-03-11 13:20:00'),
				  (3, '#сумок', 1, '2016-03-11 13:35:00'),
				  (4, '#кошельков', 2, '2016-03-11 13:25:00');

insert into crm_helios.tag_contact_company values (1, 1, 1),
						  (2, 2, 2);

insert into crm_helios.tag_deal values (3, 1),
				       (4, 2);

insert into crm_helios.deal_contact values (1, 1),
				           (2, 2);

SELECT setval('crm_helios.company_id_seq', (SELECT MAX(id) from crm_helios.company));
SELECT setval('crm_helios.contact_id_seq', (SELECT MAX(id) from crm_helios.contact));
SELECT setval('crm_helios.deal_id_seq', (SELECT MAX(id) from crm_helios.deal));
SELECT setval('crm_helios.file_id_seq', (SELECT MAX(id) from crm_helios.file));
SELECT setval('crm_helios.note_id_seq', (SELECT MAX(id) from crm_helios.note));
SELECT setval('crm_helios.person_id_seq', (SELECT MAX(id) from crm_helios.person));
SELECT setval('crm_helios.tag_id_seq', (SELECT MAX(id) from crm_helios.tag));
SELECT setval('crm_helios.task_id_seq', (SELECT MAX(id) from crm_helios.task));