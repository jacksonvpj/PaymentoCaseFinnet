package br.com.finnet


import grails.rest.*
import grails.converters.*
import grails.gorm.transactions.Transactional
import org.springframework.security.access.annotation.Secured

class PagamentoController {
	static responseFormats = ['json', 'xml']

	static allowedMethods = [solicitar:'POST', consultar:'GET', cancelar:'PUT' ]

	def pagamentoService

	@Transactional(readOnly = true)
	@Secured("ROLE_USER")
    def solicitar(Pagamento pagamento) {

    	if(pagamento.hasErrors()){
    		respond pagamento.errors
    		return
    	}
    	pagamentoService.solicitar(pagamento)

    	respond pagamento

    }

    @Secured("ROLE_USER")
    def consultar(PagamentoCommand paramsConsulta) { 
    	def lista = pagamentoService.consultar(paramsConsulta)

    	if(paramsConsulta.hasErrors()){
    		repond paramsConsulta.errors
    		return
    	}
    	if(!lista){
    		respond([:], status: 204)
    		return 
    	}

    	respond lista

    }

    @Secured("ROLE_USER")
    def cancelar(Long id) { 

    	if(!id){
    		id = request.getIntHeader("Pagamento-Id")
    	}
    	
    	def pagamento = pagamentoService.cancelar(id)
    	if(pagamento.hasErrors()){
    		respond pagamento.errors
    		return
    	}

    	def map = [message:"ok"]
    	render map as JSON
    }

}
