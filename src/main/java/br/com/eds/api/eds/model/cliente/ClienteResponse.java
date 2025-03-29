package br.com.eds.api.eds.model.cliente;

public record ClienteResponse(
        Long id,
        String nomeCliente,
        String contatoCliente,
        String emailCliente
) {
    public ClienteResponse(Cliente cliente) {
        this(cliente.getId(), cliente.getNomeCliente(), cliente.getContatoCliente(), cliente.getEmailCliente());
    }
}
