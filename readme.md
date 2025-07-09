# EDS API

API para gestão da gráfica e eletrônica EDS. 

## Objeto do projeto
O projeto tem como objetivo atender a algumas das necessidades da gráfica rápida e assistência técnica EDS. 
Para cumprir com este fim, algumas funcionalidades foram implementadas ao projeto, tais como:

**1. Funcionalidades para clientes:**
-
- Envio de formulários em forma de solicitação de novos serviços - mesmo sem cadastro
- Envio de arquivos como imagens e PDF's para serviços de design e consertos
- Busca dentro da plataforma por envios de pedidos de orçamentos com base no telefone e email inserido na solicitação
- Disponibilidade dos arquivos ainda presente no sistema. 

**2. Funcionalidades para a gestão:**
-
- Login e senha para acesso de dados sensíveis dos clientes
- Gestão dos status dos pedidos - atualizações dos status
- Busca por pedidos específicos dos clientes com base no email e telefone.

**3. Requisitos atendidos no projeto:**
- 
- A atualização de informações para serviços de impressão só é possível dentro de um período de duas horas;
- O cancelamento de impressões só é possíve dentro de um período de duas horas após a solicitação ter sido enviada

**4. Requisitos não funcionais atendidos:**
-
- Login e senha cadastrado para a gestão a fim de apresentação de dados ensíveis dos clientes;
- Gestão inteligente de arquivos: todos os arquivos inseridos pelos usuários - clientes - são excluídos automaticamente dentro de um período de 15 dias, garantindo assim a economia do espaço de armazenamento do programa no servidor.
- Tamanho limite para upload de arquivos para o sistema de 5MB a fim de garantir a integridade do sistema e evitar lentidão por conta de alta quantidade de dados no diretório.
- Os arquivos recebidos nos formulários são salvos em diferentes diretórios, de acordo com as entidades as quais pertencem, facilitando a gestão e organização dos documentos. 
- Ao acessar os arquivos no sistema através da web, a API envia para o navegador do usuário uma cópia do arquivo para download do usuário.

## Requisitos da aplicação

Para garantir que a aplicação funcione em seu dispositivo, é necessário ter
instalado o Java 17 (para compilação da aplicação) assim como o MySQL Server, assim como um banco de dados criado e acessível para que a aplicação
possa usá-lo de acordo com os scripts SQL que ela possui. Você pode configurar o nome do banco de dados para que seja de acordo com o criado em seu servidor
no arquivo application.properties no atritubo `spring.datasource.url` do programa Java, onde é necessário que se indique no lugar do nome do banco de dados onde se encontra o 
nome do banco de dados atualm seja inserido o nome do banco de dados que foi criado em seu servidor.

## Tecnologias usadas no projeto

As seguintes tecnologias foram empregadas nessa aplicação:

| Tecnologia           | Versão  |
|----------------------|---------|
| Java                 | 17.0.12 |
| MySQL                | 8.0.40  |
| Auth0 JWT            | 4.2.1   |
| Spring Boot Data JPA | 3.4.3   |
| Spring Security      | 3.4.3   |
| Spring Validation    | 3.4.3   |
| FlyWay Migration     | 10.20.1 |
| Jackson Databind     | 2.18.3  |

Agradeço imensamente a todos os desenvolvedores que disponibilizaram as tecnologias 
usadas nesse projeto.

## Documentação e regras de negócio aplicadas

Para fins de organização, a documentação será disponibilizada e orientada de acordo
com o ciclo CRUD (Create, Read, Update e Delete) de cada uma das entidades que a aplicação possui,
sendo as entidades principais: 
 - Conserto;
 - Software;
 - Impressão;
 - Criação Design;
 - Usuário.

Outras entidades também existentes no projeto e também possuem correlação com as principais, mas não serão amplamente
abordadas por serem servidas juntamente das principais supracitadas, e possuírem serviços auxiliares que fazem com que a aplicação 
funcione corretamente. São essas entidades: 
- Domiciliar;
- Cliente. 

### Conserto
1. **Novos registros de consertos**
As requisições são feitas usando o verbo post, e o envio do corpo da requisição é
um multi-part form, que deve conter o documento para, onde o JSON é chamado "data" e o documento deve se chamar "arquivo". 
Ambos são obrigatórios para um envio bem sucedido. O endpoint é http://localhost:8080/conserto.
A estrutura do JSON é:
````json lines
{
  "nomeCliente": "Yuri Silva",
  "contatoCliente": "40028922",
  "contatoAlternativoCliente": "(11) 98765-4321",
  "emailCliente": "email@email.com",
  "cpf": "123.456.789-00",
  "descricaoProblema": "O aparelho não liga",
  "tempoDeUso": "2 anos",
  "tipoAparelho": "Notebook",         
  "fabricante": "Samsung",           
  "domiciliar": {
    "logradouro": "Rua das Flores",
    "numeroCasa": "123",
    "cep": "01234-567",
    "complemento": "Apto 45",
    "periodo": "manhã",              
    "data": "17/04/2024"       
  }
}
````

2. **Atualização**
Ao contrário das requisições post, as put não precisam necessáriamente de um novo arquivo, apenas o arquivo JSON. O endpoint é http://localhost:8080/conserto/{id} Nela,
o documento se chama "arquivo" e o JSON "dados",como por exemplo nessa requisição:
````json lines
{	"tempoDeUso": "2 anos",
	"tipoAparelho": "Notebook",
	"fabricante": "Acer"
}
````

Note que tanto nessa como em outras requisições put não é possível atualizar os dados do cliente.

3. **Leitura dos conserto**
A leitura dos dados por parte dos usuários em geral pode ser realizada de duas diferentes maneiras a depender da estrutura em que deseja apresentar os dados:
- Apresentando apenas os dados do conserto, por meio da requisição:
  http://localhost:8080/conserto?contato={contatoCliente}
Onde o parâmetro para a busaca pode ser tanto contato quanto email ou id do conserto. A resposta desta requisição é:
````json lines
[
	{
		"id": 2,
		"descricaoProblema": "As imagens ficam trêmulas, além de não se conectar à internet. ",
		"arquivo": "/download/conserto/1747167231990_bleach8.jpg",
		"tempoDeUso": "2 anos",
		"tipoAparelho": "Televisão",
		"fabricante": "Tcl",
		"status": "cancelado",
		"dataSolicitacao": "13/05/2025 17:13",
		"dataAtualizacao": null,
		"domiciliar": {
			"id": 8,
			"logradouro": "",
			"cep": "",
			"complemento": "",
			"periodo": "indiferente",
			"data": null
		},
		"cliente": {
			"id": 5,
			"nomeCliente": "Cléber Souza",
			"contatoCliente": "40028922",
			"contatoAlternativo": "89224002",
			"emailCliente": "teste@email.com",
			"cpf": ""
		}
	}
]
````
- Apresentando os dados de conserto junto com outros pedidos de diversos pedidos que o cliente possa vir a ter no sistema, tanto dessa entidade quanto de outras,
através da requisição:
  http://localhost:8080/search?contato={contatoCliente}
Pode ser usado tanto o parâmetro contato quanto email na busca. O objeto devolvido será dessa maneira:
````json lines
{
	"consertos": [
		{
			"id": 1,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"descricaoProblema": "O aparelho não liga",
			"arquivo": "/download/conserto/1747337892628_shingekiNoKyojin.jpg",
			"tempoDeUso": "2 anos",
			"tipoAparelho": "Notebook",
			"fabricante": "Samsung",
			"status": "novo",
			"dataSolicitacao": "15/05/2025 16:38",
			"dataAtualizacao": null,
			"tipoEntidade": "conserto",
			"domicilio": {
				"id": 1,
				"logradouro": "Rua das Flores",
				"numeroCasa": "123",
				"cep": "01234-567",
				"complemento": "Apto 45",
				"periodo": "manhã",
				"data": "17/04/2024",
				"tipoEntidade": "emDomicilio"
			}
		}
	],
	"softwares": [
		{
			"id": 1,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"detalhesServico": "Lentidão extrema ao iniciar a máquina",
			"dispositivo": "PC",
			"servicos": [
				"atualização do sistema"
			],
			"status": "novo",
			"dataSolicitacao": "15/05/2025 16:38",
			"dataAtualizacao": null,
			"tipoEntidade": "software",
			"tempoUso": "3 anos",
			"fabricante": "Lenovo",
			"domicilio": {
				"id": 3,
				"logradouro": "Rua das Araras",
				"numeroCasa": "321",
				"cep": "01234-567",
				"complemento": "casa b",
				"periodo": "manhã",
				"data": "15/05/2025",
				"tipoEntidade": "emDomicilio"
			}
		}
	],
	"criacoesDesign": [
		{
			"id": 2,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"arquivoImpressao": "/download/criacao_design/1747337986422_bleach8.jpg",
			"materialImpressao": "Papel sulfite 90g",
			"status": "novo",
			"unidades": 80,
			"ladosImpressao": "frente e verso",
			"coresImpressao": "colorido",
			"dataSolicitacao": "15/05/2025 16:39",
			"dataAtualizacao": null,
			"produto": "Cartão de visitas",
			"ideiasDesign": "mais cores",
			"arquivoReferencia": "/download/criacao_design/1747337986422_bleach8.jpg",
			"tipoEntidade": "criacaodesign"
		}
	],
	"impressoes": [
		{
			"id": 1,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"arquivoImpressao": "/download/impressao_design/1747337794468_onePiece.jpg",
			"materialImpressao": "Papel opaline 240g",
			"status": "novo",
			"unidades": 500,
			"ladosImpressao": "frente e verso",
			"coresImpressao": "colorido",
			"dataSolicitacao": "15/05/2025 16:36",
			"dataAtualizacao": null,
			"produto": "Cartão de visitas",
			"tipoEntidade": "impressao"
		}
	]
}
````
4. **Apagar conserto**
O aplicativo usa o conceito de soft delete em suas exclusões, pelo que o serviço terá seu status alterado para para cancelado
nos registros. A requisição com o verbo delete é feita para o end point http://localhost:8080/conserto/{id}, onde se usa apenas o id do serviço de conserto a ser deletado.

### Software

1. **Criar novo registro**
Os serviços de software por padrão não recebem arquivos em suas requisições post, apenas objetos JSON, para o endpoint http://localhost:8080/software
com o JSON tendo o corpo:
````json lines
{
  "nomeCliente": "Carlos Silva",
  "contatoCliente": "40028922",
	"contatoAlternativoCliente": "(11) 99876-5432",
  "emailCliente": "padraomaximo@email.com",
  "cpf": "050.456.987-00",
  "detalhesServico": "Lentidão extrema ao iniciar a máquina",
  "dispositivo": "PC",
  "servicos": ["atualização do sistema"],
  "tempoUso": "3 anos",
  "domicilio": {
    "logradouro": "Rua das Araras",
    "numeroCasa": "321",
    "cep": "01234-567",
    "complemento": "casa b",
    "periodo": "manhã",
    "data": "15/05/2025"
  },
  "fabricante": "Lenovo"
}
````

2. **Leitura de objetos da entidade software**
Assim como os consertos, estes objetos podem ser vistos em dois contextos gerais
- Retorna apenas o serviço de software em específico, usando o link http://localhost:8080/software?id={id}, onde a busca pode ser feita
através do id (como no exemplo), telefone ou email inserido. A resposta esperada retorna apenas este tipo de objeto:
````json lines
{
	"id": 3,
	"detalhesServico": "Provavelmente tenho um vírus no computador. ",
	"dispositivo": "Televisão",
	"servicos": [
		"redes"
	],
	"status": "novo",
	"tempoUso": "1 ano ",
	"fabricante": "Tcl",
	"dataSolicitacao": "08/05/2025 19:03",
	"dataAtualizacao": "08/05/2025 19:13",
	"domiciliar": {
		"id": 7,
		"logradouro": "",
		"cep": "",
		"complemento": "",
		"periodo": "indiferente",
		"data": null
	},
	"cliente": {
		"id": 6,
		"nomeCliente": "teste",
		"contatoCliente": "40028922",
		"contatoAlternativo": "89224002",
		"emailCliente": "padraomaximo@email.com",
		"cpf": ""
	}
}
````
- Caso apropriado, pode ser retornado o software junto com outros serviços que o cliente solicitou anteriormente usado o link http://localhost:8080/search?contato=40028922
onde a busca pode usar tanto o parâmetro contato ou email. Esta é a reposta da API:
````json lines
{
	"consertos": [
		{
			"id": 1,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"descricaoProblema": "O aparelho não liga",
			"arquivo": "/download/conserto/1747337892628_shingekiNoKyojin.jpg",
			"tempoDeUso": "2 anos",
			"tipoAparelho": "Notebook",
			"fabricante": "Samsung",
			"status": "novo",
			"dataSolicitacao": "15/05/2025 16:38",
			"dataAtualizacao": null,
			"tipoEntidade": "conserto",
			"domicilio": {
				"id": 1,
				"logradouro": "Rua das Flores",
				"numeroCasa": "123",
				"cep": "01234-567",
				"complemento": "Apto 45",
				"periodo": "manhã",
				"data": "17/04/2024",
				"tipoEntidade": "emDomicilio"
			}
		}
	],
	"softwares": [
		{
			"id": 1,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"detalhesServico": "Lentidão extrema ao iniciar a máquina",
			"dispositivo": "PC",
			"servicos": [
				"atualização do sistema"
			],
			"status": "novo",
			"dataSolicitacao": "15/05/2025 16:38",
			"dataAtualizacao": null,
			"tipoEntidade": "software",
			"tempoUso": "3 anos",
			"fabricante": "Lenovo",
			"domicilio": {
				"id": 3,
				"logradouro": "Rua das Araras",
				"numeroCasa": "321",
				"cep": "01234-567",
				"complemento": "casa b",
				"periodo": "manhã",
				"data": "15/05/2025",
				"tipoEntidade": "emDomicilio"
			}
		}
	],
	"criacoesDesign": [
		{
			"id": 2,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"arquivoImpressao": "/download/criacao_design/1747337986422_bleach8.jpg",
			"materialImpressao": "Papel sulfite 90g",
			"status": "novo",
			"unidades": 80,
			"ladosImpressao": "frente e verso",
			"coresImpressao": "colorido",
			"dataSolicitacao": "15/05/2025 16:39",
			"dataAtualizacao": null,
			"produto": "Cartão de visitas",
			"ideiasDesign": "mais cores",
			"arquivoReferencia": "/download/criacao_design/1747337986422_bleach8.jpg",
			"tipoEntidade": "criacaodesign"
		}
	],
	"impressoes": [
		{
			"id": 1,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"arquivoImpressao": "/download/impressao_design/1747337794468_onePiece.jpg",
			"materialImpressao": "Papel opaline 240g",
			"status": "novo",
			"unidades": 500,
			"ladosImpressao": "frente e verso",
			"coresImpressao": "colorido",
			"dataSolicitacao": "15/05/2025 16:36",
			"dataAtualizacao": null,
			"produto": "Cartão de visitas",
			"tipoEntidade": "impressao"
		}
	]
}
````
3. **Atualização do objeto software**
Para atualização do objeto é necessário enviar para a API através do endpoint http://localhost:8080/software/{id} usando o verbo put um Json com as 
seguintes características características, por exemplo:
````json lines
{
  "detalhesDoServico": "Remoção do windows, por favor, instalar Linux",
  "dispositivo": "PC",
  "servicos": ["segurança"]
}
````
Lembrando que todas as informações podem ser atualizadas, menos os dados do cliente.

4. **Deletar objeto software**
Usando também o conceito de soft delete, o verdo delet deve ser enviado para o endpoint http://localhost:8080/software/{id} usando 
o id do objeto a se excluir.

### Impressão
1. **Criando objetos da entidade Impressão**
É necessário enviar para o link http://localhost:8080/print uma requisição post com o corpo sendo um multipart também,
onde há um Json (chamado "data") e um outro documento, que contém o design, que deve se denominar "arquivo" para que a API
processo a solicitação corretamente, ambos são obrigatórios para uma reuisição bem sucedida. Este é um exempli de Json a se enviar para a requisição:
````json lines
{
  "nomeCliente": "Augusto Timão Vieira",
  "contatoCliente": "40028922",
	"contatoAlternativoCliente":"123456789",
  "emailCliente": "padraomaximo@email.com",
	"cpf":"08255500",
  "materialImpressao": "Papel opaline 240g",
  "unidades": 500,
	"ladosImpressao":"frente e verso",
	"coresImpressao":"colorido",
	"produto":"Cartão de visitas"
}
````
2. **Leitura dos objetos da entidade Impressão**
Assim como anteriormente, pode-se encontrar os objetos de impressão individualmente ou entre outros serviços que o mesmo cliente solicitou.
- Apenas impressões, podem ser obtidas no link http://localhost:8080/print?contato={contatoCliente}, onde pode ser o parâmetro o id do objeto, contato ou email do cliente.A resposta é:
````json lines
[
	{
		"id": 9,
		"arquivoImpressao": "src\\main\\resources\\static\\impressao_design\\1746931358024_bleach8.jpg",
		"materialImpressao": "Papel monolúcido 90g",
		"produto": "Folhas avulsas",
		"status": "cancelado",
		"unidades": 100,
		"dataSolicitacao": "10/05/2025 23:42",
		"dataAtualizacao": null,
		"ladosImpressao": "Frente e verso",
		"coresImpressao": "Colorido",
		"cliente": {
			"id": 5,
			"nomeCliente": "Cléber Souza",
			"contatoCliente": "40028922",
			"contatoAlternativo": "89224002",
			"emailCliente": "teste@email.com",
			"cpf": ""
		}
	},
	{
		"id": 10,
		"arquivoImpressao": "src\\main\\resources\\static\\impressao_design\\1746933143487_bleach8.jpg",
		"materialImpressao": "Papel offset 180g",
		"produto": "Fotografias",
		"status": "cancelado",
		"unidades": 10,
		"dataSolicitacao": "11/05/2025 00:12",
		"dataAtualizacao": "11/05/2025 00:12",
		"ladosImpressao": "Frente e verso",
		"coresImpressao": "colorido",
		"cliente": {
			"id": 5,
			"nomeCliente": "Cléber Souza",
			"contatoCliente": "40028922",
			"contatoAlternativo": "89224002",
			"emailCliente": "teste@email.com",
			"cpf": ""
		}
	},
]
````
- Para serviços que o cliente pode ter feito, como indicado anteriormente, use o link http://localhost:8080/search?contato={contatoCliente}
  onde a busca pode usar tanto o parâmetro contato ou email. Esta é a reposta da API:
````json lines
{
	"consertos": [
		{
			"id": 1,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"descricaoProblema": "O aparelho não liga",
			"arquivo": "/download/conserto/1747337892628_shingekiNoKyojin.jpg",
			"tempoDeUso": "2 anos",
			"tipoAparelho": "Notebook",
			"fabricante": "Samsung",
			"status": "novo",
			"dataSolicitacao": "15/05/2025 16:38",
			"dataAtualizacao": null,
			"tipoEntidade": "conserto",
			"domicilio": {
				"id": 1,
				"logradouro": "Rua das Flores",
				"numeroCasa": "123",
				"cep": "01234-567",
				"complemento": "Apto 45",
				"periodo": "manhã",
				"data": "17/04/2024",
				"tipoEntidade": "emDomicilio"
			}
		}
	],
	"softwares": [
		{
			"id": 1,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"detalhesServico": "Lentidão extrema ao iniciar a máquina",
			"dispositivo": "PC",
			"servicos": [
				"atualização do sistema"
			],
			"status": "novo",
			"dataSolicitacao": "15/05/2025 16:38",
			"dataAtualizacao": null,
			"tipoEntidade": "software",
			"tempoUso": "3 anos",
			"fabricante": "Lenovo",
			"domicilio": {
				"id": 3,
				"logradouro": "Rua das Araras",
				"numeroCasa": "321",
				"cep": "01234-567",
				"complemento": "casa b",
				"periodo": "manhã",
				"data": "15/05/2025",
				"tipoEntidade": "emDomicilio"
			}
		}
	],
	"criacoesDesign": [
		{
			"id": 2,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"arquivoImpressao": "/download/criacao_design/1747337986422_bleach8.jpg",
			"materialImpressao": "Papel sulfite 90g",
			"status": "novo",
			"unidades": 80,
			"ladosImpressao": "frente e verso",
			"coresImpressao": "colorido",
			"dataSolicitacao": "15/05/2025 16:39",
			"dataAtualizacao": null,
			"produto": "Cartão de visitas",
			"ideiasDesign": "mais cores",
			"arquivoReferencia": "/download/criacao_design/1747337986422_bleach8.jpg",
			"tipoEntidade": "criacaodesign"
		}
	],
	"impressoes": [
		{
			"id": 1,
			"cliente": {
				"id": 1,
				"nomeCliente": "Augusto Timão Vieira",
				"contatoCliente": "40028922",
				"contatoAlternativo": "123456789",
				"emailCliente": "padraomaximo@email.com",
				"cpf": "08255500",
				"dataCadastro": "15/05/2025 16:36",
				"dataAtualizacao": null,
				"tipoEntidade": "cliente"
			},
			"arquivoImpressao": "/download/impressao_design/1747337794468_onePiece.jpg",
			"materialImpressao": "Papel opaline 240g",
			"status": "novo",
			"unidades": 500,
			"ladosImpressao": "frente e verso",
			"coresImpressao": "colorido",
			"dataSolicitacao": "15/05/2025 16:36",
			"dataAtualizacao": null,
			"produto": "Cartão de visitas",
			"tipoEntidade": "impressao"
		}
	]
}
````
3. **Atualizando objetos da entidade Impressão**
De acordo com as regras de negócio, é possível atualizar os dados deste tipo de objeto apenas dentro de um período de duas horas
após a criação do objeto. A requisição deve ser feita para o link http://localhost:8080/print/{id} com um multipart form onde o Json
deve ser chamado de "dados" e o design enviado de "file". O arquivo do design não é de envio obrigatório. Um exemplo do Json a se enviar para a requisição pode ser:
````json lines
{
  "materialImpressao": "PAPEL_OFFSET_180G",
  "dimensao": "A4",
  "unidades": 900
}
````
4. **Deletando um objeto Impressão**
Aqui também é usado o conceito de soft delete, usando o endpoint http://localhost:8080/print/{id} com o verbo delete
para a API.

### Criação de design
1. **Criando registros da entidade criaçã de design**
Para criar objetos dessa entidade,é necessário enviar uma requisição com o verbo post para o link http://localhost:8080/design, onde 
a API espera por um multipart form com um objeto Json que deve se denominar "data" e outro documento que deverá ser denominado "arquivo". 
A estrutura do Json deve ser semelhante a esta:
````json lines
{
  "ideiasDesign": "mais cores",
  "dadosImpressao": {
    "nomeCliente": "Teste rápido",
    "contatoCliente": "40028922",
		"contatoAlternativoCliente":"014745625",
		"emailCliente": "padraomaximo@email.com",
		"cpf":"",
    "materialImpressao": "Papel sulfite 90g",
    "unidades": 80,
    "ladosImpressao": "frente e verso",
		"coresImpressao":"colorido",
		"produto":"Cartão de Visitas"
  }
}
````
2. **Leitura de dados de Criação de Design**
Semelhantemete as outras entidades, a busca pode ser feita tanto por meio do link http://localhost:8080/search?contato={contatoCliente} que pode encontrar todos os objetos
do cliente através do email ou contato do cliente, ou através do link http://localhost:8080/design?id={id}, que pode usar como parêmetro email, contato ou id do serviço de de criação de design.
O objeto devolvido da requisição get é:
````json lines
{
	"id": 2,
	"materialImpressao": "Papel sulfite 90g",
	"produto": "Cartão de visitas",
	"status": "novo",
	"unidades": 80,
	"ladosImpressao": "frente e verso",
	"coresImpressao": "colorido",
	"dataSolicitacao": "15/05/2025 16:39",
	"dataAtualizacao": null,
	"ideiasDesign": "mais cores",
	"arquivoReferencia": "/download/criacao_design/1747337986422_bleach8.jpg",
	"cliente": {
		"id": 1,
		"nomeCliente": "Augusto Timão Vieira",
		"contatoCliente": "40028922",
		"contatoAlternativo": "123456789",
		"emailCliente": "padraomaximo@email.com",
		"cpf": "08255500"
	}
}
````

3. **Atualização de objetos da entidade Criação de design**
Assim como para a entidade de impressão, há um período limite para a atualização do objeto de cinco horas desde sua criação no sistema.
A requisição put para a atualização do objeto é feita usando o endpoint http://localhost:8080/design/{id}, e pode ou não conter um novo documento junto ao Json, ou receber apenas um novo arquivo.
O corpo do Json na requisição deve ser semelhante ao seguinte:
````json lines
{
"ideiasDesign": "Design moderno com cores vibrantes",
"novosDadosImpressao" : {
"materialImpressao": "Papel opaline 240g",
"produto": "Cartão de visitas",
"unidades": 500,
"ladosImpressao": "frente e verso",
"coresImpressao": "colorido"
    }
}
````
4. **Exclusão de objetos Criação de Design**
Os objetos podem ser excluídos através do link http://localhost:8080/design/{id} para a API. Seguindo as regras de negócio para as quais a API
foi elaborada, não há um limite de tempo para a exclusão de serviços de design, no entanto essa lógiga pode ser facilmente implementada.

### Outras entidades
Outras entidades também importantes para o projeto mas possuem o seu ciclo CRUD diretamente conectado com as entidades principais são as entidades Domicilar e Cliente.

- Objetos da entidade Cliente - Novos objetos da entidade cliente são criados automaticamene junto com as outras informações fornecidas no momento em que as solicitações post para qualquer das entidades é feito.
Foi implementada um lógica específica ao programa que garante que desde que um novo objeto esteja sendo criado com o mesmo email e contato, o novo objeto será atribuído ao mesmo cliente já registrado no sistema, através do serviço obterOuCriarCliente() que recebe as informações e as trata.
- Objetos da entidade Domiciliar - Este objeto também é criado através de requisições post para as entidades Conserto e Software, mas não são obrigatórias para que quaisquer requisições funcionem. 
As informações recebidas são tratadas através do método saveDomicilar() contido no programa, e é executado sempre que as citadas requisições post são feitas.

### Recursos para usuários a nível de gestão 
Os usuários a nível de gestão da aplicação devem ter seus acessos - login e senha - cadastrados manualmente no sistema. Recursos para cadastro de email e senha para login não foram disponibilizados no ciclo CRUD da aplicação, 
a fim de preservar a integridade dos acessos a mais informações da aplicação. Como indicado, o recurso adotado para o login e autenticação é o JWT, juntamente com o Spring Security e Auth0. A criptografia para adotada para adotada para a aplicação é a BCrypt.
Sendo assim, o cadastro de novos usuários de gestão da aplicação deve ser feito manualmente de maneira direta no banco de dados - tanto o login quanto a senha.

Pensando-se ainda na camada de segurança da aplicação, algumas requisições necessitam do envio de um Bearer Token gerado a partir da autenticação através de login e senha pela aplicação para que sejam bem sucedidas.
As requisições que precisam de um token são estritamente ligadas a gestão de status da ssolicitações dos clientes, 
e do acesso a estatísticas geradas pela aplicação (ainda em desenvolvimento - em fase avançada no momento). 

As requisições plenamente disponíveis a nível de gestão com o verbo get que possuem um período envolvido devem ter o mês escrito com apenas 1 dígito para os meses menores do que 10, e o ano escrito com quatro dígitos:
- http://localhost:8080/gestao/findallinatstatus?status={status} - encontra pedidos com o mesmo status no momento;
- http://localhost:8080/gestao/findbyrepairstatus?status={status}&mes={mesParaBusca}&ano={anoParaBusca} - encontra consertos com o mesmo status dentro de um mês e ano específicos;
- http://localhost:8080/gestao/findbysoftwarestatus?status={status}&mes={mês}&ano={ano} - encontra serviços de software com o mesmo status dentro de um mês e ano específicos;
- http://localhost:8080/gestao/findbydesignstatus?status={status}&mes={mês}&ano={ano} - encontrs objetos de Criação de Design em um mesmo status dentro de mês e ano específicos;
- http://localhost:8080/gestao/findbyprintstatus?status={status}&mes={mês}&ano={ano} - encontra objetos da entidade impressão em um determinado status dentro mês e anos indicados.

Há uma requisição put que também utiliza o token gerado na autenticação do usuário. Esta serve para atualizar o status de um pedido. O endpoint para a API a se usar é:

http://localhost:8080/gestao/changestatus/{tipoEntidade}/{idEntidade}

Como observado, cada entidade possui um atribudo denominado tipoEntidade, e isso garante com que em casos onde o mesmo id está a ser usado por mais de uma entidade 
(dado que o atributo "id" na aplicação é do tipo long) não seja processado e interpretado erroneamente, já que o endpoint atualiza mais de um tipo de objeto.

Na requisição, é necessário enviar um corpo com um objeto Json simples para se efetuar a atualização do status do objeto no banco de dados. O corpo a se enviar é:
````json lines
{
	"status":"novo"
}
````

A API está preparada para receber dados que não respeitam plenamente alguns aspectos que podem comprometer as solicitações que ela recebe, como por exemplo:
- tratamento para situações onde o Java pode encontrar dificuldades com o case sensitive;
- tratamento de espaços antes e depois dos status nos corpos json, através do método .trim().

Os diferentes status que um objeto Conserto, Software, Impressão e Criação de Design podem conter são:

````text
novo;
cliente contatato;
em espera;
pedido confirmado;
processando;
aguarda retirada;
entrega solicitada;
finalizado;
cancelado
````

Vale ressaltar que **os status dos pedidos estão contidos em um enum, e não são Strings.** Caso os nomes não sejam devidamente 
respeitados nas requisições, a API não poderá reconhecer os dados recebidos na requisição, o que irá gera um erro em tempo de execução do programa.

### Recursos para usuários a nível de gestão ainda em desenvolvimento
No momento, se encontram em desenvolvimento a geração de estatísticas para a gestão com base nos status dos pedidos e as solicitações dos cliente.
Vale ressaltar que esses recursos estarão plenamente disponpiveis apenas para os usuários autenticados na plataforma - a gestão.


## Contato do desenvolvedor:
[LinkedIn](https://www.linkedin.com/in/lorrannazareth/)  do desenvolvedor.

Email acadêmico do desenvolvedor: lorran.nazareth@gmail.com

Email comercial do desenvolvedor: lorranbarrosnazareth@gmail.com

Caso queira discutir o projeto ou outros assuntos relacionados, sinta-se a vontade para entrar em contato!


