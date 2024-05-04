package edu.unimagdalena.tiendaEnLinea.serviceSecurity;

import edu.unimagdalena.tiendaEnLinea.dto.cliente.ClienteDto;
import edu.unimagdalena.tiendaEnLinea.dto.pedido.PedidoMapper;
import edu.unimagdalena.tiendaEnLinea.entity.*;
import edu.unimagdalena.tiendaEnLinea.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInfoService implements UserDetailsService {
    private final ClienteRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoService(ClienteRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<Cliente> userDetail = userRepository.findByEmail(email);
        return userDetail.stream().map(UserInfoDetail::new)
                .findAny().orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ClienteDto addUser(ClienteDto userInfo) {
        List<Pedido> pedidos=userInfo.pedidos().stream().map(p->PedidoMapper.instancia.pedidoDtoToEntity(p)).collect(Collectors.toList());
        Cliente user = new Cliente(null, userInfo.nombre(), userInfo.email(), userInfo.direccion(), passwordEncoder.encode(userInfo.password()), userInfo.rol(),pedidos);
        user = userRepository.save(user);
        return new ClienteDto(user.getId(),user.getNombre(), user.getEmail(), user.getDireccion(), userInfo.password(), user.getRol(),userInfo.pedidos());
    }
}
