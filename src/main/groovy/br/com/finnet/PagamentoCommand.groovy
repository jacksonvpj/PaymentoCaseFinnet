package br.com.finnet

import grails.validation.Validateable

class PagamentoCommand implements Validateable {
	Long pagamentoId
	Long cedenteId
	Long usuarioId

	Date dataSolicitacaoInicio
	Date dataSolicitacaoFim

	Date dataVencimentoInicio
	Date dataVencimentoFim

	Date dataPagamentoInicio
	Date dataPagamentoFim

	Date dataCancelamentoInicio
	Date dataCancelamentoFim

}