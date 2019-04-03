# Projeto PaymentCase
Projeto criado em grails, framework de desenvolvimento web em groovy, com profile específico em rest-api. O framework grails utililza metodologia de programação ágil Convention Over Configuration, e utiliza subconjuntos de frameworks nativos, dentre eles: 
- Gradle
- Spring boot, 
- Hibernate, 
- GSON view 
- Spock framework de teste 

Para solução da proposta foi adicionado Framewoks de segurança:

 - Spring Security Core 
 - Spring Security REST 

## Desenvolvimento

Instalação do Grails
- Prerequisito
	- JDK 8

- For Linux e MACOS
Utilizar script SDKMAN de instação do grails https://grails.org/download.html 

- For Windows
Baixar Binario, unzip e definir variável de ambiente GRAILS_HOME=path_home_grails
https://github.com/grails/grails-core/releases/download/v3.3.9/grails-3.3.9.zip

Adicione GRAILS_HOME/bin na variável de ambiente PATH

Por ser um projeto baseado no Gradle é possivel utiliza-lo no IntelliJ Community Edition

Após a instalação, abra o terminal na raiz do projeto e execute

> `grails run-app`

Use postman para execução do projeto:

 1. Obtenha o token rest do usuario de exempli
    http://localhost:8080/api/login
    json login
    {
      "username":"admin",
      "password":"admin"
    }
    
 2. Execute um dos seviços abaixo com toke Bearer <token>:

> http://localhost:8080/api/pagamento/solicitar
> http://localhost:8080/api/pagamento/consultar
> http://localhost:8080/api/pagamento/cancelar