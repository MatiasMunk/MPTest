use pplace

insert into parkingspot 
       (number, basemonthlyfee) values
       (1, 100.00),
       (2, 150.0),
       --(4, 275.0),
       --(5, 200.0)
       (6, 50.0);

insert into employee
      (initials, fname, lname, employmentdate) values
	  ('aand', 'Anders', 'And', '2022-12-01'),
	  ('fml', 'Fedt', 'Mule', '2018-07-01'),
	  ('asa', 'Andersine', 'And', '2021-05-01'),
	  ('rand', 'Rap', 'And', '2021-09-01'), -- ingen bil, for ung!
	  ('rund', 'Rup', 'And', '2022-08-01'), -- knallert
	  ('rind', 'Rip', 'And', '2023-01-01'); -- knallert

insert into rentagreement
          (startdate, enddate, monthlyfee, employee_id, parkingspot_id) values
          ('2022-01-01', '2023-12-31', 250.0, 2, 1), -- fedtmule, spot 1
	      ('2022-07-01', '2029-01-01', 300.0, 3, 2),--, -- andersine, spot 2
	      ('2022-09-01', '2024-09-01', 50.0, 5, 3);--, -- rup, spot 2	      
	  --('2023-01-01', '2023-12-31', 300.0, 1, 2) -- anders, spot 1
	  

insert into vehicle 
          (numberplate, electric, registrationdate, employee_id) values
          ('AA13133', 0, '2022-12-01', 1), --anders' benzinbil
          ('AA13134', 1, '2023-02-01', 1), --anders' elbil
          ('FM12122', 1, '2022-01-15', 2), --fedtmules elbil
          ('FM12123', 1, '2023-01-15', 2), --fedtmules elbil
          ('AN69699', 1, '2022-05-01', 3), --andersines elbil
          ('CY8921', 0, '2022-08-01', 5), --rup 
          ('VS9433', 0, '2023-03-05', 6); --rip 