INSERT INTO Ingredient VALUES
  (1, 'morkov'),
  (2, 'svekla'),
  (3, 'look'),
  (4, 'kartofel'),
  (5, 'kapusta'),
  (6, 'pomidor'),
  (7, 'chicken'),
  (8,'sugar'),
  (9,'flour');
INSERT INTO Recipe VALUES
  (1,'borstch','tasty soop'),
  (2,'schshi','description'),
  (3,'chicken soop','best choice'),
  (4,'cake','sweet cake');
INSERT into RecipeToIngredients VALUES
  (1,1,3),
  (1,2,1),
  (1,3,3),
  (1,4,6),
  (2,1,2),
  (2,3,3),
  (2,4,1),
  (2,5,3),
  (3,7,3),
  (3,4,5),
  (4,8,10),
  (4,9,3);