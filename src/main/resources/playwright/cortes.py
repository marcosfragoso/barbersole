import csv
from playwright.sync_api import sync_playwright
import time

def ler_cortes():
    abrindo_csv = open('D:\\Users\\Marcos\\Documents\\barbersole\\src\\main\\resources\\playwright\\cortes.csv', 'r')
    cortes = csv.reader(abrindo_csv, delimiter=';')
    cortes_lista = []
    
    for row in cortes:
        if len(row) != 0:
            servico = row[0]
            barbeiro = row[1]
            data = row[2]
            hora = row[3]
            cortes_lista.append([servico, barbeiro, data, hora])
    return cortes_lista


with sync_playwright() as p:
    cortes = ler_cortes()

    navegador = p.chromium.launch(headless=False)
    pagina = navegador.new_page()
    pagina.goto("http://localhost:8080/login")
    pagina.fill('//*[@id="username"]', 'fragoso.ufcg@gmail.com')
    pagina.fill('//*[@id="password"]', '123456')
    pagina.locator('xpath=/html/body/section/div/div/div/div[2]/div[1]/form/div[3]/button').click()
    pagina.goto("http://localhost:8080/agendamentos/agendar")
    for corte in cortes:
        pagina.locator('//*[@id="servico"]').select_option(label=f'{corte[0]}')
        pagina.locator('//*[@id="barbeiro"]').select_option(label=f'{corte[1]}')
        pagina.fill('//*[@id="data"]', corte[2])
        pagina.locator('//*[@id="hora"]').select_option(label=f'{corte[3]}')
        pagina.locator('xpath=/html/body/div/section/div/div/form/div[5]/button').click()
    pagina.goto("http://localhost:8080/agendamentos/")
    time.sleep(3)