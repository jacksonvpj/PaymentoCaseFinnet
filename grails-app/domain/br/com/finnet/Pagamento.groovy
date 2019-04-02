package br.com.finnet

class Pagamento {
	Cedente cedente
	Usuario usuario
	BigDecimal valor
	Integer numParcelas
	Date dateCreated

	Date dataVencimento
	Date dataPagamento
	Date dataCancelamento

    static constraints = {
		dataPagamento nullable:true
		dataVencimento nullable:true
		dataCancelamento nullable:true
    }
}
