package com.erp.ms_products.service;

import com.erp.ms_products.dto.ProductDTO;
import com.erp.ms_products.model.Product;
import com.erp.ms_products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private ProductDTO convertToDTO(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setSku(entity.getSku());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecio(entity.getPrecio());
        dto.setStock(entity.getStock());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        log.info("Listando todos los productos del inventario");
        return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getBySku(String sku) {
        log.info("Buscando producto con sku " + sku);
        return productRepository.findBySku(sku).map(this::convertToDTO)
                .orElseThrow(() -> {
                    log.error("Producto con sku " + sku + " no encontrado");
                    return new RuntimeException("Producto no encontrado");
                });
    }

     @Transactional(readOnly = true)
    public ProductDTO getById(String id) {
        log.info("Buscando producto con id " + id);
        return productRepository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> {
                    log.error("Producto con id " + id + " no encontrado");
                    return new RuntimeException("Producto no encontrado");
                });
    }

    @Transactional
    public ProductDTO create(ProductDTO dto) {
        log.info("Registrando nuevo producto con sku " + dto.getSku());
        if (productRepository.existsBySku(dto.getSku())) {
            log.warn("El sku " + dto.getSku() + " ya existe");
            throw new RuntimeException("Ya existe un producto registrado con el sku " + dto.getSku());
        }

        Product entity = new Product();
        entity.setSku(dto.getSku().toUpperCase().trim());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setStock(dto.getStock());

        Product saved = productRepository.save(entity);
        log.info("Producto guardado con id " + saved.getId() + " y sku " + saved.getSku());
        return convertToDTO(saved);
    }

    @Transactional
    public ProductDTO updateStock(String sku, int cantidadARestar) {
        log.info("Iniciando descuento de stock para SKU: " + sku);

        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> {
                    log.error("Producto con SKU " + sku + " no encontrado");
                    return new RuntimeException("Producto con SKU " + sku + " no encontrado");
                });

        int nuevoStock = product.getStock() - cantidadARestar;

        if (nuevoStock < 0) {
            log.error("Stock insuficiente para SKU {}. Disponible: {}, Solicitado: {}",
                    sku, product.getStock(), cantidadARestar);
            throw new RuntimeException("Stock insuficiente para el producto: " + product.getNombre());
        }

        product.setStock(nuevoStock);
        Product updated = productRepository.save(product);

        log.info("Stock actualizado para {} (SKU: {}). Nuevo stock: {}",
                updated.getNombre(), sku, updated.getStock());

        return convertToDTO(updated);
    }

    @Transactional
    public ProductDTO addStock(String sku, int cantidadASumar) {
        log.info("Sumando stock por compra a sku " + sku);
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto con sku " + sku + " no encontrado"));

        product.setStock(product.getStock() + cantidadASumar);
        Product updated = productRepository.save(product);

        log.info("Stock aumentado del producto " + updated.getNombre() + " sku " + updated.getSku() + ". Nuevo stock: "
                + updated.getStock() + " unidades");

        return convertToDTO(updated);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        log.info("Actualizando producto con id " + id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se puede actualizar. Producto con id " + id + " no existe");
                    return new RuntimeException("Producto con id " + id + " no encontrado");
                });
        String newSku = dto.getSku().toUpperCase().trim();
        if (!product.getSku().equals(newSku)) {
            if (productRepository.existsBySku(newSku)) {
                throw new RuntimeException("El sku " + newSku + " ya pertenece a otro producto");
            }
            product.setSku(newSku);
        }
        product.setNombre(dto.getNombre());
        product.setDescripcion(dto.getDescripcion());
        product.setPrecio(dto.getPrecio());
        product.setStock(dto.getStock());

        Product updated = productRepository.save(product);
        log.info("Producto id " + updated.getId() + " actualizado exitosamente");

        return convertToDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando producto id " + id);
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto con id " + id + " no encontrado");
        }
        productRepository.deleteById(id);
    }
}
