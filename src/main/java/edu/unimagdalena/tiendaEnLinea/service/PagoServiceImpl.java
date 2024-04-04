package edu.unimagdalena.tiendaEnLinea.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoMapper;
import edu.unimagdalena.tiendaEnLinea.dto.pago.PagoToSaveDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoMapper;
import edu.unimagdalena.tiendaEnLinea.entity.Pago;
import edu.unimagdalena.tiendaEnLinea.entity.Pedido;
import edu.unimagdalena.tiendaEnLinea.entity.enumEntity.MetodoPago;
import edu.unimagdalena.tiendaEnLinea.excepción.NotAbleToDeleteException;
import edu.unimagdalena.tiendaEnLinea.excepción.PagoNotFoundException;
import edu.unimagdalena.tiendaEnLinea.repository.PagoRepository;

@Service
public class PagoServiceImpl implements PagoService {
    private final PagoMapper pagoMapper;
    private final PagoRepository pagoRepository;

    public PagoServiceImpl(PagoMapper pagoMapper, PagoRepository pagoRepository) {
        this.pagoMapper = pagoMapper;
        this.pagoRepository = pagoRepository;
    }

    // Implementación de los métodos de la interfaz PagoService
    @Override
    public PagoDto  guardarPago(PagoToSaveDto pagoDto) {
        Pago pago = pagoMapper.pagoToSaveDtoToEntity(pagoDto);
        Pago pagoGuardado = pagoRepository.save(pago);
        return pagoMapper.pagoEntityToDto(pagoGuardado);
    }

    @Override
    public PagoDto buscarPagoPorId(Long id) throws PagoNotFoundException {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado"));
        return pagoMapper.pagoEntityToDto(pago);
    }

    @Override
    public List<PagoDto> buscarTodosPagos() {
        List<Pago> pagos = pagoRepository.findAll();
        return pagos.stream()
                .map(pago -> pagoMapper.pagoEntityToDto(pago))
                .toList();
    }

    @Override
    public PagoDto actualizarPagoPorId(Long id, PagoToSaveDto pagoDto) {

        Pago pagoInDb = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado"));

        pagoInDb.setTotalPago(pagoDto.totalPago());
        Pago pagoDtoFecha=pagoMapper.pagoDtoToEntity(pagoMapper.pagoToSaveDtoToDto(pagoDto));
        LocalDate fechaPago=pagoDtoFecha.getFechaPago();
        pagoInDb.setFechaPago(LocalDate.of(fechaPago.getYear(), fechaPago.getMonthValue(), fechaPago.getDayOfMonth()));
        pagoInDb.setMetodoPago(pagoDtoFecha.getMetodoPago());

        Pago pagoGuardado = pagoRepository.save(pagoInDb);

        return pagoMapper.pagoEntityToDto(pagoGuardado);
    }

    @Override
    public void removerPago(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotAbleToDeleteException("Pago no encontrado"));
        pagoRepository.delete(pago);
    }

    @Override
    public List<PagoDto> recuperarPagosDentroDeUnRangoDeFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Pago> pagos = pagoRepository.findByFechaPagoBetween(fechaInicio, fechaFin);
        if (pagos.isEmpty()) {
            throw new PagoNotFoundException("No se encontraron pagos en el rango de fechas especificado");
        }
        return pagos.stream()
                .map(pago -> pagoMapper.pagoEntityToDto(pago))
                .toList();
    }

    @Override
    public List<PagoDto> recuperarPagosPorUnIdentificadorDeUnaOrdenYMétodoDePago(PedidoDto idPedido, String metodoPago) {
        Pedido pedido=PedidoMapper.instancia.pedidoDtoToEntity(idPedido);
        MetodoPago metodoPagoN=pedido.getPago().getMetodoPago();
        List<Pago> pagos = pagoRepository.findByPedidoIdAndMetodoPagoIs(pedido, metodoPagoN);
        if (pagos.isEmpty()) {
            throw new PagoNotFoundException("No se encontraron pagos con el id de pedido y método de pago especificados");
        }
        return pagos.stream()
                .map(pago -> pagoMapper.pagoEntityToDto(pago))
                .toList();
    }
}

