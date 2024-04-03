package edu.unimagdalena.tiendaEnLinea.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.unimagdalena.tiendaEnLinea.repository.DetalleEnvioRepository;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioDto;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioMapper;
import edu.unimagdalena.tiendaEnLinea.dto.detalleEnvio.DetalleEnvioToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoMapper;
import edu.unimagdalena.tiendaEnLinea.excepción.DetalleEnvioNotFoundException;
import edu.unimagdalena.tiendaEnLinea.excepción.NotAbleToDeleteException;
import edu.unimagdalena.tiendaEnLinea.entity.DetalleEnvio;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;

@Service
public class DetalleEnvioServiceImpl implements DetalleEnvioService{
    private final DetalleEnvioMapper detalleEnvioMapper;
    private final DetalleEnvioRepository detalleEnvioRepository;

    public DetalleEnvioServiceImpl(DetalleEnvioMapper detalleEnvioMapper, DetalleEnvioRepository detalleEnvioRepository) {
        this.detalleEnvioMapper = detalleEnvioMapper;
        this.detalleEnvioRepository = detalleEnvioRepository;
    }


    @Override
    public DetalleEnvioDto guardarDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioDto) {
        DetalleEnvio detalleEnvio = detalleEnvioMapper.detalleEnvioToSaveDtoToEntity(detalleEnvioDto);
        DetalleEnvio detalleEnvioGuardado = detalleEnvioRepository.save(detalleEnvio);
        return detalleEnvioMapper.detalleEnvioEntityToDto(detalleEnvioGuardado);
    }

    @Override
    public DetalleEnvioDto actualizarDetalleEnvioPorId(Long id, DetalleEnvioToSaveDto detalleEnvioDto) {
        DetalleEnvio detalleEnvioInDb = detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DetalleEnvioNotFoundException("Detalle de envío no encontrado"));

        detalleEnvioInDb.setDireccion(detalleEnvioDto.direccion());
        detalleEnvioInDb.setTransportadora(detalleEnvioDto.transportadora());
        detalleEnvioInDb.setNumeroGuia(detalleEnvioDto.numeroGuia());

        DetalleEnvio detalleEnvioGuardado = detalleEnvioRepository.save(detalleEnvioInDb);

        return detalleEnvioMapper.detalleEnvioEntityToDto(detalleEnvioGuardado);
    }

    @Override
    public DetalleEnvioDto buscarDetalleEnvioPorId(Long id) throws DetalleEnvioNotFoundException {
        DetalleEnvio detalleEnvio = detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DetalleEnvioNotFoundException("Detalle de envío no encontrado"));
        return detalleEnvioMapper.detalleEnvioEntityToDto(detalleEnvio);
    }

    @Override
    public void removerDetallePedido(Long id) {
        DetalleEnvio detalleEnvio = detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new NotAbleToDeleteException("Detalle de envío no encontrado"));
        detalleEnvioRepository.delete(detalleEnvio);
    }

    @Override
    public List<DetalleEnvioDto> buscarTodosDetallesEnvio() {
        List<DetalleEnvio> detallesEnvio = detalleEnvioRepository.findAll();
        return detallesEnvio.stream()
                .map(detalleEnvio -> detalleEnvioMapper.detalleEnvioEntityToDto(detalleEnvio))
                .toList();
    }


    //Otros metodos
    @Override
    public List<DetalleEnvioDto> buscarLosDetallesDelEnvíoPorPedidoId(PedidoDto idPedido) {
        Pedido pedido=PedidoMapper.instancia.pedidoDtoToEntity(idPedido);

        List<DetalleEnvio> detallesEnvio = detalleEnvioRepository.findByPedidoId(pedido);
        if (detallesEnvio.isEmpty())
            throw new DetalleEnvioNotFoundException("No se encontraron detalles de envío para el pedido con id " + idPedido);
        return detallesEnvio.stream()
                .map(detalleEnvio -> detalleEnvioMapper.detalleEnvioEntityToDto(detalleEnvio))
                .toList();
    }

    @Override
    public List<DetalleEnvioDto> buscarLosDetallesDelEnvíoParaUnaTrasportadora(String transportadora) {
        List<DetalleEnvio> detallesEnvio = detalleEnvioRepository.findByTransportadora(transportadora);
        if (detallesEnvio.isEmpty())
            throw new DetalleEnvioNotFoundException("No posse detalles de envio la trasportadora " + transportadora);
        return detallesEnvio.stream()
                .map(detalleEnvio -> detalleEnvioMapper.detalleEnvioEntityToDto(detalleEnvio))
                .toList();
    }

    @Override
    public List<DetalleEnvioDto> buscarLosDetallesDelEnvioPorStatus( String statusPedido) {
        List<DetalleEnvio> detallesEnvio = detalleEnvioRepository.findByStatus(statusPedido);
        if (detallesEnvio.isEmpty()){
            throw new DetalleEnvioNotFoundException("No se pudo encontrar detalles de envío para el estado " + statusPedido);
        }else{return detallesEnvio.stream()
                .map(detalleEnvio -> detalleEnvioMapper.detalleEnvioEntityToDto(detalleEnvio))
                .toList();
        }
    }

}
