package br.com.finnet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Subject

class CedenteSpec extends Specification implements DomainUnitTest<Cedente> {
	
	@Subject
    Cedente domain

    def setup() {
    	domain = new Cedente()
    }

    def cleanup() {
    }

    void "test cedent"() {
		when:
        	domain.nome = nome
        	domain.cpfCnpj = cnpj

        then:
	        expectedNome    == domain.validate(['nome'])
	        expectedCpfCnpj == domain.validate(['cpfCnpj'])
	    where:
		    nome      |  cnpj   | expectedNome	| expectedCpfCnpj
		    null      |  null   | false		    | false
		    "Jackson" |  null   | true			| false
		    null	  |  "1234" | false			| true
		    "Jackson" |  "1234" | true			| true
    }

}
