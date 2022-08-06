-- CREATE TABLE IF NOT EXISTS users (
--      user_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
--      login varchar(64) NOT NULL,
--      name varchar(64) NOT NULL,
--      email varchar(64) NOT NULL,
--      birthday date NOT NULL,
--      CONSTRAINT user_email UNIQUE (email),
--      CONSTRAINT user_login UNIQUE (login)
-- );
--
-- CREATE TABLE IF NOT EXISTS MPA (
--                                    MPA_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
--                                    MPA_name varchar NOT NULL,
--                                    CONSTRAINT rating_name UNIQUE (MPA_name)
-- );
--
-- CREATE TABLE IF NOT EXISTS films (
--     film_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
--     name varchar(64) NOT NULL,
--     description varchar(1024) NOT NULL,
--     release_date date NOT NULL,
--     duration int NOT NULL,
--     MPA_id int NOT NULL,
--     CONSTRAINT film_rating_id_fk FOREIGN KEY(MPA_id) REFERENCES MPA (MPA_id)
--
-- );
--
-- CREATE TABLE IF NOT EXISTS friends (
--     user_id int REFERENCES users (user_id) NOT NULL,
--     friend_id int NOT NULL,
--     friends_status boolean NOT NULL,
--     CONSTRAINT friends_pk PRIMARY KEY (user_id, friend_id),
--     CONSTRAINT friends_user_id_fk FOREIGN KEY(user_id) REFERENCES users (user_id),
--     CONSTRAINT friends_friend_id_fk FOREIGN KEY(friend_id) REFERENCES users (user_id)
--     );
--
-- CREATE TABLE IF NOT EXISTS likes (
--     film_id Integer NOT NULL,
--     user_id Integer NOT NULL,
--     CONSTRAINT film_likes_pk PRIMARY KEY (film_id,user_id),
--     CONSTRAINT film_likes_film_id_fk FOREIGN KEY(film_id) REFERENCES films (film_id),
--     CONSTRAINT film_likes_user_id_fk FOREIGN KEY(user_id) REFERENCES users (user_id)
--     );
--
-- CREATE TABLE IF NOT EXISTS genre_id (
--     film_id INTEGER REFERENCES films (film_id) NOT NULL,
--     genre_id INTEGER REFERENCES genre (genre_id) NOT NULL,
--     CONSTRAINT films_genres_pk PRIMARY KEY (film_id, genre_id),
--     CONSTRAINT films_genre_film_id_fk FOREIGN KEY(film_id) REFERENCES films (film_id),
--     CONSTRAINT films_genre_genre_id_fk FOREIGN KEY(genre_id) REFERENCES genre (genre_id)
--     );
--
-- CREATE TABLE IF NOT EXISTS genre (
--     genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
--     genre_name varchar NOT NULL,
--     CONSTRAINT genre_name UNIQUE (genre_name)
-- );
--
-- CREATE TABLE IF NOT EXISTS ratings
-- (
--     rating_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
--     name      varchar(10) NOT NULL,
--     CONSTRAINT uc_rating_name UNIQUE (name)
-- );

CREATE TABLE IF NOT EXISTS genres
(
    genre_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name     varchar(30) NOT NULL,
    CONSTRAINT uc_genre_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS films
(
    film_id      bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         varchar(50)  NOT NULL,
    description  varchar(200) NOT NULL,
    release_date date         NOT NULL,
    duration     integer      NOT NULL,
    rating_id    bigint       NOT NULL,
    CONSTRAINT fk_film_rating_id FOREIGN KEY (rating_id) REFERENCES ratings (rating_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id  bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    varchar(50) NOT NULL,
    login    varchar(50) NOT NULL,
    name     varchar(50) NOT NULL,
    birthday date        NOT NULL,
    CONSTRAINT uc_user_email UNIQUE (email),
    CONSTRAINT uc_user_login UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS films_likes
(
    film_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT pk_films_likes PRIMARY KEY (film_id, user_id),
    CONSTRAINT fk_films_likes_film_id FOREIGN KEY (film_id) REFERENCES films (film_id),
    CONSTRAINT fk_films_likes_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS friendship
(
    user_id1  bigint  NOT NULL,
    user_id2  bigint  NOT NULL,
    confirmed boolean NOT NULL,
    CONSTRAINT pk_friendship PRIMARY KEY (user_id1, user_id2),
    CONSTRAINT fk_friendship_user_id1 FOREIGN KEY (user_id1) REFERENCES users (user_id),
    CONSTRAINT fk_friendship_user_id2 FOREIGN KEY (user_id2) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS films_genres
(
    film_id  bigint NOT NULL,
    genre_id bigint NOT NULL,
    CONSTRAINT pk_films_genres PRIMARY KEY (film_id, genre_id),
    CONSTRAINT fk_films_genres_film_id FOREIGN KEY (film_id) REFERENCES films (film_id),
    CONSTRAINT fk_films_genres_genre_id FOREIGN KEY (genre_id) REFERENCES genres (genre_id)
);