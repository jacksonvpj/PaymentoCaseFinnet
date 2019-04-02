package br.com.finnet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Subject

class PagamentoSpec extends Specification implements DomainUnitTest<Pagamento> {

	@Subject
    Pagamento domain

    def setup() {
    	domain = new Pagamento()
    }

    def cleanup() {
    }

    void "test pagamento"() {
    	when:
    		domain.cedente = cedente
			domain.usuario = usuario
			domain.valor = valor
			domain.numParcelas = numParcelas
			domain.dataVencimento = dataVencimento
			domain.dataPagamento = dataPagamento
			domain.dataCancelamento = dataCancelamento

    	then:
			hasErrors == domain.hasErrors()

    	where:
    		cedente | usuario | valor | numParcelas | dataVencimento | dataPagamento | dataCancelamento | hasErrors
    		new Cedente() | new Usuario() | 12 | 1 | null | null | null | false
    	}
}
