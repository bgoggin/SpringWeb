﻿drop table if exists contact;
create table contact (
    id  serial primary key,
    last_name varchar(40) not null,
    first_name varchar(40) not null,
    mi char(1),
    email varchar(80),
    date_created timestamp with time zone default current_timestamp,
    date_modified timestamp with time zone default current_timestamp
);

drop index if exists contact_idx1;
create unique index contact_idx1 on contact (last_name, first_name, mi);

drop function if exists update_contact_date_modified_column();

CREATE FUNCTION update_contact_date_modified_column() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  BEGIN
    NEW.date_modified = CURRENT_TIMESTAMP;
    RETURN NEW;
  END;
$$;

drop trigger if exists contact_updated_at_modtime on contact;
CREATE TRIGGER contact_updated_at_modtime
    BEFORE UPDATE
    ON public.contact
    FOR EACH ROW
    EXECUTE PROCEDURE public.update_contact_date_modified_column();