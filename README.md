# Assistência Técnica API: clientes e equipamentos

Adaptação acadêmica do projeto de autores e artigos fornecido pelo usuário, com assistência de IA.
Leia LEIA_PRIMEIRO.md antes de substituir arquivos. A estrutura original foi reaproveitada.

## Escopo
Cadastro, consulta, atualização e exclusão individual de clientes e equipamentos.
Um cliente pode ter zero ou mais equipamentos; cada equipamento pertence a um cliente.
Cliente com equipamentos não pode ser excluído. Não há exclusão em massa.
Não inclui ordens de serviço, orçamento, estoque, pagamentos ou interface visual.

## Estrutura e tecnologias
Mantidas as dependências do pom.xml original: Java 17 como alvo, Spring Boot 4.1.1,
Spring MVC, Spring Data JPA, PostgreSQL e Lombok. Nenhuma dependência nova.
Pacote dw.editora e classe EditoraApplication mantidos por compatibilidade estrutural.
entity: dados; repository: acesso ao banco; service: regras; control: rotas e erros.

## Campos
Cliente: id gerado; nome obrigatório (120); e-mail obrigatório e único (254); telefone obrigatório (30).
E-mail normalizado para minúsculas, com validação básica de formato, não de existência.
Telefone é texto obrigatório; não há validação de número telefônico real.
Equipamento: id gerado; tipo obrigatório (60); marca obrigatória (80); modelo obrigatório (120);
numeroSerie opcional (120); cliente obrigatório. Parênteses indicam limites de caracteres.
Número de série não é único, pois essa regra não foi definida para a Sprint.
JsonIgnore na lista de equipamentos evita recursão JSON. O vínculo é gerenciado pela API de equipamentos.
IDs enviados no cadastro são ignorados; o banco gera os identificadores.

## Rotas
- GET /api/clientes
- GET /api/clientes/{id}
- POST /api/clientes
- PUT /api/clientes/{id}
- DELETE /api/clientes/{id}
- GET /api/clientes/{id}/equipamentos
- GET /api/equipamentos
- GET /api/equipamentos?modelo=texto
- GET /api/equipamentos/{id}
- POST /api/equipamentos
- PUT /api/equipamentos/{id}
- DELETE /api/equipamentos/{id}

PUT substitui todos os campos editáveis e exige os obrigatórios. Pode transferir equipamento para outro cliente existente.
O filtro de modelo é parcial, sem distinguir maiúsculas/minúsculas.

## Exemplo de cliente
```json
{"nome":"Cliente de Teste","email":"cliente@example.com","telefone":"(42) 99999-0000"}
```

## Exemplo de equipamento
```json
{"tipo":"Notebook","marca":"Marca de Teste","modelo":"Modelo A","numeroSerie":"ABC123","cliente":{"id":1}}
```
Use o ID retornado no cadastro, não necessariamente 1.

## Respostas
200 para consulta/atualização; lista vazia é []. 201 para cadastro; 204 para exclusão;
400 para dados inválidos; 404 para registro inexistente; 409 para conflito de e-mail ou integridade/vínculo.
Erros tratados retornam status e mensagem em JSON.

## Execução e testes
Banco separado: assistencia_scrum. Porta: 8000. Senha: variável DB_PASSWORD.
Comandos e instruções completas em LEIA_PRIMEIRO.md.
Coleção Postman com 22 requisições sequenciais e verificações automáticas.
Testes Java: validação unitária e inicialização do contexto com banco.
Os testes ainda devem ser executados pelo grupo; não se declara aprovação.

## Limitações
API acadêmica local, sem autenticação. Não é pronta para produção.
O código não substitui os artefatos e eventos de Scrum da atividade.
