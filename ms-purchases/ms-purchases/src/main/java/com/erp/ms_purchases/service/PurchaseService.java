package com.erp.ms_purchases.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.ms_purchases.client.ProductClient;
import com.erp.ms_purchases.client.ProviderClient;
import com.erp.ms_purchases.dto.ProviderDTO;
import com.erp.ms_purchases.dto.PurchaseRequestDTO;
import com.erp.ms_purchases.model.Purchase;
import com.erp.ms_purchases.repository.PurchaseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductClient productClient;
    private final ProviderClient providerClient;
    
    @Transactional(readOnly = true)
    public List<Purchase> getAll() {
        log.info("Consultando el historial completo de compras en la base de datos.");
        return purchaseRepository.findAll();
    }

    @Transactional
    public Purchase processPurchase(PurchaseRequestDTO dto){
        log.info("Iniciando proceso de compra para el producto sku " + dto.getProductSku() + " desde el proveedor rut " + dto.getProviderRut());

        ProviderDTO provider = providerClient.getByRut(dto.getProviderRut());

        if(provider == null){
            log.error("El proveedor rut " + dto.getProviderRut() + " no existe en la base");
            throw new RuntimeException("Proveedor no encontrado");
        }

        Purchase purchase = new Purchase();
        purchase.setProductSku(dto.getProductSku());
        purchase.setProviderRut(dto.getProviderRut());
        purchase.setCantidad(dto.getCantidad());
        purchase.setPrecioUnitario(dto.getPrecioUnitario());
        purchase.setTotal(dto.getCantidad() * dto.getPrecioUnitario());

        Purchase savedPurchase = purchaseRepository.save(purchase);

        try {
            productClient.addStock(dto.getProductSku(), dto.getCantidad());
            log.info("Stock actualizado correctamente para sku " + dto.getProductSku());
        } catch (Exception e) {
            log.error("No se pudo actualizar el stock para el SKU " + dto.getProductSku());
            throw new RuntimeException("Error en comunicación con microservicio de productos.");
        }

        return savedPurchase;
    }
}
