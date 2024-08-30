import csv
from playwright.sync_api import sync_playwright
import time

def ler_cortes():
    abrindo_csv = open('D:\\Users\\Marcos\\Documents\\barbersole\\src\\main\\resources\playwright\\corte.csv', 'r')
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

    # Implementar
    pagina.fill('//*[@id="inputEmail"]', 'admin@suap.edu.br')
    pagina.fill('//*[@id="inputPassword"]', 'admin')
    pagina.keyboard.press('Enter')
    pagina.locator('//html/body/div/aside/nav/ul[2]/li[2]/a/span').click()
    for corte in cortes:
        pagina.fill('//*[@id="nome"]', corte[0])
        pagina.fill('//*[@id="area"]', corte[1])
        pagina.locator('//*[@id="cadastro"]/form/button').click()
    pagina.locator('//*[@id="navbarsExampleDefault"]/a').click()
    pagina.screenshot(path='screenshots/cadastro-cortes.png', full_page=True)
    time.sleep(3)