package br.com.finnet

import grails.validation.Validateable

class PagamentoCommand implements Validateable {
	Long pagamentoId
	Long cedenteId

	Date dataSolicitacaoInicio
	Date dataSolicitacaoFim

	Date dataVencimentoInicio
	Date dataVencimentoFim

	Date dataPagamentoInicio
	Date dataPagamentoFim

	Date dataCancelamentoInicio
	Date dataCancelamentoFim

	static constraints = {
		pagamentoId nullable:true
		cedenteId nullable:true
		dataSolicitacaoInicio nullable:true
		dataSolicitacaoFim nullable:true
		dataVencimentoInicio nullable:true
		dataVencimentoFim nullable:true
		dataPagamentoInicio nullable:true
		dataPagamentoFim nullable:true
		dataCancelamentoInicio nullable:true
		dataCancelamentoFim nullable:true
	}

}