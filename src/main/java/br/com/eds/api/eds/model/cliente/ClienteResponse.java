package br.com.eds.api.eds.model.cliente;

public record ClienteResponse(
        Long id,
        String nomeCliente,
        String contatoCliente,
        String contatoAlternativo,
        String emailCliente,
        String cpf
) {
    public ClienteResponse(Cliente cliente) {
        this(cliente.getId(), cliente.getNomeCliente(), cliente.getContatoCliente(), cliente.getContatoAlternativo(), cliente.getEmailCliente(), cliente.getCpf());
    }
}
