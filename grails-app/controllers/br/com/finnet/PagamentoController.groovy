package br.com.finnet


import grails.rest.*
import grails.converters.*
import grails.gorm.transactions.Transactional

class PagamentoController {
	static responseFormats = ['json', 'xml']

	static allowedMethods = [solicitar:'POST', consultar:'GET', cancelar:'PUT' ]

	def pagamentoService

	@Transactional(readOnly = true)
    def solicitar(Pagamento pagamento) {

    	if(pagamento.hasErrors()){
    		respond pagamento.errors
    		return
    	}
    	pagamentoService.solicitar(pagamento)
    	
    	respond pagamento

    }

    def consultar(PagamentoCommand paramsConsulta) { 
    	
    	def lista = pagamentoService.consultar(paramsConsulta)

    	if(paramsConsulta.hasErrors()){
    		repond paramsConsulta.errors
    		return
    	}

    	respond lista

    }

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
