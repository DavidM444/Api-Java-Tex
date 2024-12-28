package ctxt.textil.api.domain.proveedor;

import ctxt.textil.api.application.dto.request.DatosProveedor;
import ctxt.textil.api.application.dto.response.DatosProvName;
import ctxt.textil.api.application.dto.response.DtoResP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProveedorService {

    @Autowired
    private ProveedorRpty proveedorRepository;

    public DtoResP saveProveedor(DatosProveedor datos){
        Proveedor proveedor = proveedorRepository.save(new Proveedor(datos));
        return mapToDto(proveedor);
    }

    public List<DatosProvName> listarProveedores() {
        return proveedorRepository.findAll().stream()
                .map(proveedor -> new DatosProvName(
                        proveedor.getPrNombre(),
                        proveedor.getPrId()))
                .toList();
    }

    public DtoResP obtenerProveedorPorId(Long id) {
        return proveedorRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
    }

/*    public DtoResP actualizarProveedor(Long id, DatosProveedor datos) {
        log.info("Actualizando proveedor ID: {}", id);

        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));

        proveedor.actualizarDatos(datos);
        proveedor = proveedorRepository.save(proveedor);

        return mapToDto(proveedor);
    }*/

    public void eliminarProveedor(Long id) {


        if (!proveedorRepository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + id);
        }

        proveedorRepository.deleteById(id);
    }

    private DtoResP mapToDto(Proveedor proveedor) {
        return new DtoResP(
                proveedor.getPrId(),
                proveedor.getPrNombre(),
                proveedor.getPrEmpresa(),
                proveedor.getPrTelefono(),
                proveedor.getPrDireccion()
        );
    }




}
