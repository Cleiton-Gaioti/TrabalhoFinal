import pandas as pd
import json
import string
from random import choice
from unidecode import unidecode
import sqlite3


""" Registros gerados aleatóriamente no site https://www.4devs.com.br/gerador_de_pessoas """

def inserir_registros(conn, max_id):
    with open("data.json", "r") as datas:
        data = json.load(datas)

    df = pd.DataFrame(data)

    df = (
        df.
        filter(["nome", "email", "data_nasc"]).
        assign(
            id=range(max_id, max_id + len(df)),
            username=lambda x: x.nome.replace(" ", "-"), 
            password=lambda x: [f"{nome}@{id}" for nome, id in zip(x.nome.apply(lambda y: unidecode(y[0:y.find(" ")])), x.id)], 
            admin=[choice([0, 1]) for i in range(len(df))], 
            authorized=lambda x: x.admin.apply(lambda y: 1 if y == 1 else choice([0, 1]))
        ).rename(columns={"nome": "name", "data_nasc": "dateRegister"})
    )

    df.to_sql(con=conn, name="user", index=False, if_exists="append")


if __name__ == '__main__':
    try:
        conn = sqlite3.connect('db/users.sqlite')
        cursor = conn.cursor()

        max_id = cursor.execute("SELECT MAX(id) FROM user").fetchall()[0][0]

        inserir_registros(conn, max_id + 1)

    except sqlite3.Error as error:
            print("Error while connecting to sqlite", error)
    finally:
        if conn:
            conn.close()
            print("The SQLite connection is closed")


