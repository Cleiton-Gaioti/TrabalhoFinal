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
            username=lambda x: x.nome.apply(lambda y: unidecode(y).replace(" ", "-")), 
            password=passwords, 
            data_nasc=lambda x: x.data_nasc.astype("datetime64[ns]"),
            admin=[choice([0, 1]) for i in range(len(df))], 
            authorized=lambda x: x.admin.apply(lambda y: 1 if y == 1 else choice([0, 1]))
        ).rename(columns={"nome": "name", "data_nasc": "dateRegister"})
    )

    df.to_sql(con=conn, name="user", index=False, if_exists="append")
    print(df)


passwords = [
    'A460112AE9E232866F23B58C7A40078DF5D6ED3906BEF32E9574CA6F00216CBB', '878CD14B3E2E6D23C267994BC60136DD9082044E4AEA25B41A1A54A5779308EA', '8E924F5CF037B2046BB44A89EDAA29B60787C0F9E459D1D70A15CAEDC6B1EE43', '36A906D0DBEAE3A28B0DBFB5B1B88CC7D1B2CBB1F14AAFA3987FA6BE52BB69D3', '596608F22C5B27B9F05AAFCBA79B1028EC4A4D6388F07572332DC27ECB26CD1C', '5AEE3927EC1072DA1459FC753099B47A5EF2E8C2EE12D3B3B5F8E7011316C153', '7E55A917517923E1B453296D768C56325885263942A187CF785C4FE4BA4A950A', '79244DD1B110AACC0599029007B2C0535EA9A2C267546B95DE6E76B634A8CF83', '2578242A9BB678C14B6584CD60CCF4C25C2BD08082AD25C70640AB77A53FA471', 'B5CBA16F5D3443FF3F5F25E3E8ABB9A0A56E735E9B3BE03F4F0A3262D686C3A4', 'BC0A8877775E440F1CD4023EF21D0B741455B8031749328EB8C2D241098D5CD4', 'B8D0A946E0126F04F1E997A192050CCE69E50D3F0A490DA8C47AE4BAB87F73F4', 'E816EEC3FC1749E684AF0E07EC764227F1DC63FABD547BAE4CE3DF6B3ED6E2E0', 'C3E8ADF56AC6E0BD1847DCA7CB7585781CE89562CC744C6900DD3A4A7831F8EC', '671EBBEC94FA7BD7073C34805B84FEE5273B2ABF5A9C061BAFA159DF36916E4F', '9C66C1F99A2B2465AC38886C064FE92044573C10912A3FAE193E1BA4EF4ED79C', '42D72EC1CC76D58BE734351D46B538CAC0A6A01DB6BDF26497A35249FD056787', '109540D02F5E8A2431038705166209144AE4D8422173B4210DC607CE898DF595', 'CA22CCABFDA16D28AE95AC33B65502A0E24202A9297B9DBB27279C6DD4ECA643', '616BF20BE1D55BB80973B7B606057D328A5664651C860106FB9A8782DA60F866', 'D86871A9B9E9DAFD5051ACCC295D643F7791A638DD770F3CD476A4C39143FBFC', 'B858DE155F87A71E1850ACF92F724C032693667AB4B9DE67BB9CFD2DF39320FA', '36BB53BABCA397B73D3A1BC2E632485DC647A5E4FFA04933A053E9E289670C3F', '2728452E4B6A751F81E7F1A856A3EAB45272EB1405CA5F7FED39DB10A5D68BBF', '81278E58AA5CCAD8FB9A56B2DF24C7AA7BF975D74C8701B74971C46FD4408A5B', 'DC08927A594C40ACC89B51B17A24E01F9552CDDAB5FEC44F230D64A692CBAFFA', '36B7FD97956A5421196C06714EEE2077907B4214A095B4BABC2005384522BE29', 'E9B63879BDB9830F45F41027599CB0FE86D27F465EE247F0D3C1B63A5C20D6E2', '58915AC1226931443F5032A46DAC1B4514980DD2FD8E0DCAD4F07E475804E22F', 'C243F71EBB13DF4209006C051FFB27F016B162C0F1884CBDFC4BACB590C51E36', '001E304E7BC3970CD64888508F1EBEA97A80598FB268E5F9DB4EA925871C4DEA', '19BF673384EF076CB2FDE6940E264D14BAC81EAEC3ED6766095696DEE602675C', 'C7725D4E9208653D25A78FD401555C9EE47147651C1511172F964177A5AC4F95', '57149BDE59140491D96AF4F8C2C0E5449FBFBF04CF5DC86E3A9A6594F92711C9', '9580D2F94DD8E5AAABACA08F7F18F1D44A923727DB5E228351CB2602EAA0E58A', '67AF74F78D2C7A4066F53674917EFBA64A8CEB3CA599E6D9DEC955383AE345BB', '0C051B74844AFD5BF6D071382016C855387C974CC3D7A0DD411B09779105E534', 'C38693DEED0F4403C691EC157817A53BA21E95E9D852DD4553F50F1FF00CDCC8', '3B3BF9D662E27901E50857E744F941D38AD8BDB4D1E3B9D92A6B65B00CFB6530', 'D712CF999762F4C9844E0E16247EE74AACE8C123104EAFE54C2BAA91353EABEE', '4E8F712E51232A300FFD6698FDF5B846295A23ECE81077756493F2125A90B186', '7642E2184E7BB417678064069FE399FBA3F21887A3A72263ECE7C9FC3DCA83A3', '203576D52430180D17FDC04B0A363ED72D9A6718C1D9F30720E15400050A98BB', '3ED93843E3CC636984A4E88677D46C14C51338EA5811C7889133DC5D0FA0FD45', '30195F5ED14C21289FAF5034DFAEA835BBC8BBD9EF609DFDFD43B8D2417632EC', 'ACDBE047CE2A29A39DA948FB1950BD6D1F60819B43571CFFBFBFEB47EDBDF68E', '405D5EB42E479BCE11FE5FD91F6E61DAC5D676470FAF50574BACC3A6852FF044', 'B0FEAD28154AAF0DF434520189A956D2BEA4449E539EAAEA01EF4D0B2A066643', 'C1F3D350F077179584128ABB95B06FB0F96AC14336A64F347818F52F68D1F874', '04C3C4386256BB16F925661B88C556492BEE303E326F2054837B818BAF426EE2', '200872D3C188F630227852569DD78CB5B4AA6207CFD339C44A7C444353B03D41', '88BB04DEE95D861AA87A30CE54D58650BF8D6E876120E563EDBBBB59D9FC3FD4', '1326EB3702D04AD8298909A762B2DB0BEB740084FDEAF91BA5147567BB7B7146', 'D3F38D37A11719DCD2FDE558A2DFEAFF910278E0BE84E2A2B33E08CC0F56D2E4', 'E2CE2EC03690BC34D03282EB0161D21F5115DF9A64F1E2225E8B4BCC18A12679', '48ECF4A3F2324E23717F7B54179EC8898F98197E8721C0C401D96E99FFA79AD2', 'B9C492CA4966FF34200A390D0EAE33D9258F5AB675AF9332038697324CA98266', 'C60BD86AD7BD9D8E0CCF4715BBB8BFF3B4BDC732FF391ED92DA525E33E2D338B', '63DE38AFA9678115AD3F492249917048F7CD80C872BB3FC26DB5252DC4D01582', 'AF135AE9DEE467A9B0DC7F42B0DF25717FD8658B2539CD6FEE284AB1E706E1C5']


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


