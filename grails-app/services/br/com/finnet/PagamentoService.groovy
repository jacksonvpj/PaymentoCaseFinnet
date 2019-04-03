package br.com.finnet

import grails.gorm.transactions.Transactional

@Transactional
class PagamentoService {
	def springSecurityService

    def solicitar(Pagamento pagamento) {

    	if(!pagamento.usuario){
    		pagamento.usuario = springSecurityService.principal	
    	}
    	
    	if(!pagamento.hasErrors()){
    		pagamento.save(flush:true)	
    	} 
    	
    }

    def consultar(PagamentoCommand params = null) { 
    	def lista = []	

    	use(groovy.time.TimeCategory) {
    		if(params){
	    		def duration
	    		if(params.dataSolicitacaoInicio && params.dataSolicitacaoFim){
					duration = params.dataSolicitacaoFim - params.dataSolicitacaoInicio
					if (duration.days > 30) {
						params.errors.reject('consulta.periodo.invalido', ['Data de Solicitação'] as Object[], 'Período da {0} não pode ser maior que 90 dias')
					}	    			
	    		}
	    		if(params.dataVencimentoInicio && params.dataVencimentoFim){
					duration = params.dataVencimentoFim - params.dataVencimentoInicio
					if (duration.days > 30) {
						params.errors.reject('consulta.periodo.invalido', ['Data de Vencimento'] as Object[], 'Período da {0} não pode ser maior que 90 dias')
					}	    			
	    		}
	    		if(params.dataPagamentoInicio && params.dataPagamentoFim){
					duration = params.dataPagamentoFim - params.dataPagamentoInicio
					if (duration.days > 30) {
						params.errors.reject('consulta.periodo.invalido', ['Data de Pagamento'] as Object[], 'Período da {0} não pode ser maior que 90 dias')
					}	    			
	    		}
	    		if(params.dataPagamentoInicio && params.dataPagamentoFim){
					duration = params.dataCancelamentoFim - params.dataCancelamentoInicio
					if (duration.days > 30) {
						params.errors.reject('consulta.periodo.invalido', ['Data de Cancelamento'] as Object[], 'Período da {0} não pode ser maior que 90 dias')
					}	    			
	    		}
    		}
		}

		if(!params || !params.hasErrors()){
	    	lista = Pagamento.withCriteria {

	    		createAlias('cedente', 'c')

	    		usuario{
	    			eq 'id', springSecurityService.principal.id	
	    		}
	    		

	    		if (params?.pagamentoId){
	    			eq 'id', params.pagamentoId
	    		} else {

		    		if (params?.cedenteId){
		    			eq 'c.id', params.cedenteId
		    		}
		    		if (params?.dataSolicitacaoInicio && params?.dataSolicitacaoFim){
		    			between 'dataSolicitacao', params.dataSolicitacaoInicio, params.dataSolicitacaoFim
		    		}
		    		if (params?.dataVencimentoInicio && params?.dataVencimentoFim){
		    			between 'dataVencimento', params.dataVencimentoInicio, params.dataVencimentoFim
		    		}
		    		if (params?.dataPagamentoInicio && params?.dataPagamentoFim){
		    			between 'dataPagamento', params.dataPagamentoInicio, params.dataPagamentoFim
		    		}
		    		if (params?.dataCancelamentoInicio && params?.dataCancelamentoFim){
		    			between 'dataCancelamento', params.dataCancelamentoInicio, params.dataCancelamentoFim
		    		}

	    		}
	    	}

		}

		return lista

    }

	def cancelar(Long id) { 
		if(!id){
			throw new RuntimeException("Parametro id obrigatório para Cancelamento")
		}
    	def pagamento = Pagamento.get(id)

    	if (pagamento.dataPagamento){
    		// Error: Pagamento já faturado
    		pagamento.errors.reject('pagamento.cancelar.finalizado.error', 'Não é permitido cancelar pagamento(s) finalizados(s)')

    	} else if (pagamento.dataCancelamento){
    		// Error: Pagamento já cancelado
    		pagamento.errors.reject('pagamento.cancelar.cancelado.error', 'Pagamento já está cancelado ')

    	} else if (pagamento.usuario != springSecurityService.principal){
    		// Error: Pagamento nao encontrado
    		pagamento.errors.reject('pagamento.cancelar.invalido', 'Pagamento não encontrado ')
    	}

    	if (!pagamento.hasErrors()) {
	    	pagamento.dataCancelamento = new Date()
    	}

    	return pagamento

    }

}
