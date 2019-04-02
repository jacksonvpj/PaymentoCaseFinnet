package br.com.finnet

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import grails.testing.mixin.integration.Integration
import org.springframework.test.annotation.Rollback
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared
import spock.lang.Specification
import grails.test.hibernate.HibernateSpec


import grails.plugin.springsecurity.SpringSecurityService

@Integration
@Rollback
class PagamentoServiceSpec extends Specification implements DataTest, ServiceUnitTest<PagamentoService>{

	@Autowired PagamentoService pagamentoService
	@Autowired SpringSecurityService springSecurityService 

	@Shared Usuario usuario

	def setupSpec() {
		mockDomain Cedente
		mockDomain Usuario
	}

    def setup() {
    	usuario = new Usuario(username:'administrator', password:'1wwwe23')
    }

    def cleanup() {
    }

    void "test sucessfull solicitar pagamento"() {
    	when:
			def cedente = new Cedente(nome:'Cedente_1', cpfCnpj:"12333")
    		def pagamento = new Pagamento (
    			usuario: usuario,
    			cedente: cedente,
				valor: 12.3,
				numParcelas: 1
    		)
    		pagamentoService.solicitar(pagamento)

    	then:
    		pagamento.id
    		pagamento.dataCancelamento == null
    }

    void "test fails solicitar pagamento"() {
    	when:
    		def pagamento = new Pagamento (
				valor: 12.3
    		)
    		pagamentoService.solicitar(pagamento)

    	then:
    		!pagamento.id
    		pagamento.hasErrors()

    }

    void "test cancelar pagamento"(){
		when: 
			def cedente = new Cedente(nome:'Cedente_1', cpfCnpj:"12333")
    		def pagamento = new Pagamento (
    			usuario: usuario,
    			cedente: cedente,
				valor: 12.3,
				numParcelas: 1
    		)
    		pagamentoService.solicitar(pagamento)
    		pagamentoService.cancelar(pagamento?.id)

		then: 
			pagamento.dataCancelamento != null

    }

    // void "test pesquisar"(){
    // 	when:
    // 		[
    // 			[valor: 12.3, numParcelas: 1],
    // 			[valor: 1.3, numParcelas: 1]
    // 			[valor: 10.3, numParcelas: 1]
    // 		].each{
    // 			new Pagamento()
    // 		}
    // 	then:
    // }

}
