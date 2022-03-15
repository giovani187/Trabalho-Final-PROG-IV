# Trabalho-Final-PROG-IV

## APLICATIVO: Sunset Discover

Aplicativo feito em java usando a platafoma Android. Esse aplicativo permite descobrir as informações sobre o pôr e nascer do sol.

## Tecnologias usadas

GPS - Usando o google-service

API - Sunset https://sunrise-sunset.org/api

Retrofit - Biblioteca facilitadora de requisições a API's

## Telas

MainActivity

SunsetActivity

## FLUXO APP

O aplicativo acessa o GPS por meio do google-service para obter dados como latitude e longitude, após captar esses dados é feito uma requisição a API do Sunset e após isso é retornado informações dos horários que ocorrem os eventos do pôr e nascer do sol para determinada localidade que é informada no cabeçalho do aplicativo.
