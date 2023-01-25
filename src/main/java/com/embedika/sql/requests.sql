create table cars(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    person_id BIGINT NOT NULL,
    number VARCHAR(9) NOT NULL,
    brand VARCHAR(30) NOT NULL,
    color VARCHAR(30) NOT NULL,
    release VARCHAR(4) NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) REFERENCES person (id),
    UNIQUE (number)
)

create table person(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(30) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username)
)