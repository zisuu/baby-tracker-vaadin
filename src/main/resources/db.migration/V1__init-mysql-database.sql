
    alter table baby
       drop
       foreign key FKd2pr45e345wjhv4h6fg6r054i;

    alter table event
       drop
       foreign key FKfso3pt1na1qo4mtdov8331e7r;

    drop table if exists baby;

    drop table if exists event;

    drop table if exists user_account;

    create table baby (
        birthday date not null,
        version integer not null,
        id varchar(36) not null,
        user_account_id varchar(36) not null,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table event (
        version integer not null,
        end_date datetime(6),
        start_date datetime(6),
        baby_id varchar(36) not null,
        id varchar(36) not null,
        event_type enum ('BATHING','BEDTIME','BOTTLE_FEEDING','BREAST_FEEDING','CRYING','DIAPER','SLEEPING') not null,
        notes varchar(255),
        status enum ('COMPLETED','INTERRUPTED','STARTED'),
        primary key (id)
    ) engine=InnoDB;

    create table user_account (
        version integer not null,
        id varchar(36) not null,
        email varchar(50),
        password varchar(255) not null,
        role enum ('ADMIN','USER'),
        primary key (id)
    ) engine=InnoDB;

    alter table baby
       add constraint FKd2pr45e345wjhv4h6fg6r054i
       foreign key (user_account_id)
       references user_account (id);

    alter table event
       add constraint FKfso3pt1na1qo4mtdov8331e7r
       foreign key (baby_id)
       references baby (id);
