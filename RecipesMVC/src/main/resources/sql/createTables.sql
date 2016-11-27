create table IF NOT EXISTS Ingredient (
    id number(18) IDENTITY (1,1) PRIMARY KEY,
    name varchar2(50) not null,

    constraint uqi_name unique(name)
);
create table IF NOT EXISTS Recipe (
    id number(18) IDENTITY(1,1) PRIMARY KEY,
    name varchar2(50) not null,
    description varchar2(50),

    constraint uqr_name unique(name)
);
create table IF NOT EXISTS RecipeToIngredients (
    r_id number(18) NOT NULL ,
    i_id number(18) NOT NULL ,
    i_count number(18) CHECK (i_count>0),

    PRIMARY KEY (r_id,i_id),
    FOREIGN KEY (r_id) REFERENCES Recipe(id),
    FOREIGN KEY (i_id) REFERENCES Ingredient(id)
);