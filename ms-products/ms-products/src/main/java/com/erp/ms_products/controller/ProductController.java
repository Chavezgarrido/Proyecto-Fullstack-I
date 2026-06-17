package com.erp.ms_products.controller;

import com.erp.ms_products.dto.ProductDTO;
import com.erp.ms_products.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Catálogo de Productos", description = "Endpoints de control de inventario y catálogo")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Listar catálogo completo", description = "Retorna un arreglo con todos los productos registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Catálogo obtenido con éxito (puede retornar vacío `[]`)")
    public ResponseEntity<List<ProductDTO>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Buscar por SKU", description = "Busca los detalles de un producto utilizando su código SKU comercial único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto localizado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún producto con el SKU especificado")
    })
    public ResponseEntity<ProductDTO> getBySku(
        @PathVariable("sku") @Parameter(description = "Código SKU único del artículo", example = "TECL-MEC-RGB") String sku
    ){
        return ResponseEntity.ok(productService.getBySku(sku));
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar por ID", description = "Busca un producto a través de su identificador alfanumérico único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto localizado con éxito"),
        @ApiResponse(responseCode = "404", description = "No existe ningún artículo con el ID ingresado")
    })
    public ResponseEntity<ProductDTO> getById(
        @PathVariable("id") @Parameter(description = "ID alfanumérico del producto", example = "PROD-A39B2") String id
    ){
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo producto", description = "Crea un producto en el catálogo. Las restricciones de SKU y campos obligatorios son validadas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Estructura de JSON inválida o error en las validaciones de entrada")
    })
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO dto){
        return new ResponseEntity<>(productService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update-stock")
    @Operation(summary = "Establecer stock absoluto", description = "Reemplaza el stock total actual de un producto por una nueva cifra utilizando su SKU.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock absoluto modificado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductDTO> updateStock(
        @RequestParam("sku") @Parameter(description = "SKU del artículo", example = "TECL-MEC-RGB") String sku, 
        @RequestParam("cantidad") @Parameter(description = "Cantidad exacta final que quedará en bodega", example = "50") int cantidad
    ){
        return ResponseEntity.ok(productService.updateStock(sku, cantidad));
    }

    @PutMapping("/add-stock")
    @Operation(summary = "Incrementar stock", description = "Añade de forma incremental una cantidad de unidades al stock existente de un producto.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Unidades sumadas al inventario con éxito"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductDTO> addStock(
        @RequestParam("sku") @Parameter(description = "SKU del artículo", example = "TECL-MEC-RGB") String sku, 
        @RequestParam("cantidad") @Parameter(description = "Unidades que se van a sumar al stock actual", example = "10") int cantidad
    ){
        return ResponseEntity.ok(productService.addStock(sku, cantidad));
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Modificar producto completo", description = "Actualiza los campos de un producto existente utilizando su ID único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del JSON no superan las validaciones de negocio"),
        @ApiResponse(responseCode = "404", description = "No existe un producto con el ID especificado")
    })
    public ResponseEntity<ProductDTO> update(
        @PathVariable("id") @Parameter(description = "ID del producto a modificar", example = "PROD-A39B2") String id, 
        @Valid @RequestBody ProductDTO dto
    ){
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Remueve permanentemente el registro de un producto de la base de datos a través de su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto removido con éxito (Sin contenido de retorno)"),
        @ApiResponse(responseCode = "404", description = "No se encontró el producto a eliminar")
    })
    public ResponseEntity<Void> delete(
        @PathVariable("id") @Parameter(description = "ID del producto a eliminar", example = "PROD-A39B2") String id
    ){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}