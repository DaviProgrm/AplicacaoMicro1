Alunos: Filipe Maia, Davi Cruz, Rafael Barros, Gabriel Adler


para rodas a aplicacao eh necessario mudar as credenciais de acesso do postgres

adicionar um cliente no banco de dados de cliente

acessar a rota de estoque com o metodo POST

http://localhost:8082/estoque/

esta acao cria um produto na base de dados do estoque com o codigo de barras "1234"

para registrar uma venda acesse a seguinte rota com o metodo POST:
http://localhost:8083/vendas/realizar-venda

passanndo um JSON nesse formato

{
    "cpf": "70338022422",
    "codigo": "1234",
    "quantidade": 5
}
