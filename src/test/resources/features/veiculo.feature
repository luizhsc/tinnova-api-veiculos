# language: pt

Funcionalidade: Integração ponta a ponta para API Veículos
  Como administrador
  Quero autenticar, criar veículo, listar e detalhar
  Para garantir que a API Veículos esteja funciona corretamente

  Cenário: Criar veículo com sucesso
    Dado que eu possuo um JSON válido de veículo
    Quando eu enviar a requisição para criar o veículo
    Então o status da resposta deve ser 201

  Cenário: Buscar um veículo existente
    Dado que existe um veículo com ID 1
    Quando eu buscar o veículo pelo ID 1
    Então o status da resposta deve ser 200

  Cenário: Listar veículos filtrando por marca
    Dado que existem veículos cadastrados
    Quando eu listar veículos filtrando por marca "Toyota"
    Então o status da resposta deve ser 200

  Cenário: Atualizar veículo existente
    Dado que existe um veículo com ID 1
    Quando eu atualizar completamente o veículo com ID 1
    Então o status da resposta deve ser 204

  Cenário: Atualizar apenas a cor
    Dado que existe um veículo com ID 1
    Quando eu atualizar parcialmente o veículo com ID 1
    Então  o status da resposta deve ser 200

  Cenário: Excluir veículo existente
    Dado que existe um veículo com ID 1
    Quando eu excluir o veículo com ID 1
    Então o status da resposta deve ser 204