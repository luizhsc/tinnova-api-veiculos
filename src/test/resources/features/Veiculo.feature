Feature: Integração ponta a ponta para API Veículos
  Como administrador
  Quero autenticar, criar veículo, listar e detalhar
  Para garantir que a API Veículos esteja funciona corretamente

  Scenario: Criar e Autenticar Usuario Admin
    Given criar usuario admin
    When autenticar com usuario admin válido
    Then retornar o token

  Scenario: Criar Veiculo
    Given um veiculo request com placa "ABC1234" e nome "Teste"
    When salvar veículo
    Then a resposta deve conter os dados do veiculo salvo

  Scenario: Listar Veiculo por marca
    When listar veiculos filtrando por marca "Honda"
    Then a lista deve conter o veículo com placa "ABC1234"

  Scenario: Listar Veiculo por Id
    When buscar veículo por id "1"
    Then os dados devem incluir cor Preto e ano 2025
