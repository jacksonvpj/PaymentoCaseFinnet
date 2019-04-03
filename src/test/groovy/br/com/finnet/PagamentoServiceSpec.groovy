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

import org.hibernate.SessionFactory

@Integration
@Rollback
class PagamentoServiceSpec extends Specification implements ServiceUnitTest<PagamentoService>{

	void setup() {
		Pagamento.withTransaction{
	    	new Usuario(username:'admin', password:'1wwwe23').save()
			new Cedente(nome:'Cedente 1', cpfCnpj:"12333").save()
		}

    }


    void "test sucessfull solicitar pagamento"() {
    	given:
			def mockService = Mock(SpringSecurityService)
			Pagamento.withTransaction{
				1 * mockService.getPrincipal() >> Usuario.findByUsername('admin')
			}
			service.springSecurityService = mockService

    	when:
    		def pagamento
    		Pagamento.withTransaction{
		    	///def usuario = Usuario.findByUsername('administrator')
				def cedente = Cedente.findByCpfCnpj("12333")

	    		pagamento = new Pagamento (
	    			cedente: cedente,
					valor: 12.3,
					numParcelas: 1
	    		)
	    		service.solicitar(pagamento)
    		}


    	then:
    		!pagamento.hasErrors()
    		pagamento.id
    		pagamento.dataCancelamento == null
    }

    void "test fails solicitar pagamento"() {
    	given:
			def mockService = Mock(SpringSecurityService)
			Pagamento.withTransaction{
				1 * mockService.getPrincipal() >> Usuario.findByUsername('admin')
			}
			service.springSecurityService = mockService

    	when:
    		def pagamento = new Pagamento (
				valor: 12.3
    		)
    		service.solicitar(pagamento)

    	then:
    		!pagamento.id
    		pagamento.hasErrors()

    }

    void "test cancelar pagamento"(){
    	given:
			def mockService = Mock(SpringSecurityService)
			Pagamento.withTransaction{
				2 * mockService.getPrincipal() >> Usuario.findByUsername('admin')
			}
			service.springSecurityService = mockService

		when: 
			def pagamento
			Pagamento.withTransaction{
				def cedente = Cedente.findByCpfCnpj("12333")
	    		pagamento = new Pagamento (
	    			cedente: cedente,
					valor: 12.3,
					numParcelas: 1
	    		)
	    		service.solicitar(pagamento)
	    		service.cancelar(pagamento?.id)
			}

		then: 
			pagamento.dataCancelamento != null

    }

    void "test pesquisar"(){
    	given:
			def mockService = Mock(SpringSecurityService)
			Pagamento.withTransaction{
				def user3 = new Usuario(username:'user3', password:'1wwwe23').save()
				4 * mockService.getPrincipal() >> user3
			}
			service.springSecurityService = mockService

    	when:
    		def lista = []
    		Pagamento.withTransaction{
	    		def cedente = Cedente.findByCpfCnpj("12333")
	    		[
	    			[cedente:cedente, valor: 12.3, numParcelas: 1],
	    			[cedente:cedente, valor: 1.3, numParcelas: 1],
	    			[cedente:cedente, valor: 10.3, numParcelas: 1]
	    		].each{
	    			service.solicitar(new Pagamento(it))
	    		} 
	    		lista = service.consultar()
    		}

    	then:    		
    		lista.size()==3	
    		
    }

}
