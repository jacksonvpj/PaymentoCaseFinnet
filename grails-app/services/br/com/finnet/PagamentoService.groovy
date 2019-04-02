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

    def consultar(PagamentoCommand params) { 
    	def lista = []
    	use(groovy.time.TimeCategory) {
    		def duration
			duration = params.dataSolicitacaoFim - params.dataSolicitacaoInicio
			if (duration.days > 30) {
				params.errors.reject('consulta.periodo.invalido', ['Data de Solicitação'] as Object[], 'Período da {0} não pode ser maior que 90 dias')
			}
			duration = params.dataVencimentoFim - params.dataVencimentoInicio
			if (duration.days > 30) {
				params.errors.reject('consulta.periodo.invalido', ['Data de Vencimento'] as Object[], 'Período da {0} não pode ser maior que 90 dias')
			}
			duration = params.dataPagamentoFim - params.dataPagamentoInicio
			if (duration.days > 30) {
				params.errors.reject('consulta.periodo.invalido', ['Data de Pagamento'] as Object[], 'Período da {0} não pode ser maior que 90 dias')
			}
			duration = params.dataCancelamentoFim - params.dataCancelamentoInicio
			if (duration.days > 30) {
				params.errors.reject('consulta.periodo.invalido', ['Data de Cancelamento'] as Object[], 'Período da {0} não pode ser maior que 90 dias')
			}
		}

		if(!params.hasErrors()){
	    	lista = Pagamento.withCriteria {

	    		createAlias('cedente', 'c')

	    		eq 'usuario', springSecurityService.principal

	    		if (param.pagamentoId){
	    			eq 'id', param.pagamentoId
	    		} else {

		    		if (param.cedenteId){
		    			eq 'c.id', param.cedenteId
		    		}
		    		if (param.dataSolicitacaoInicio && param.dataSolicitacaoFim){
		    			between 'dataSolicitacao', param.dataSolicitacaoInicio, param.dataSolicitacaoFim
		    		}
		    		if (param.dataVencimentoInicio && param.dataVencimentoFim){
		    			between 'dataVencimento', param.dataVencimentoInicio, param.dataVencimentoFim
		    		}
		    		if (param.dataPagamentoInicio && param.dataPagamentoFim){
		    			between 'dataPagamento', param.dataPagamentoInicio, param.dataPagamentoFim
		    		}
		    		if (param.dataCancelamentoInicio && param.dataCancelamentoFim){
		    			between 'dataCancelamento', param.dataCancelamentoInicio, param.dataCancelamentoFim
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
